package com.a101.ssafy.project.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

// 토큰의 생성과 유효성 검증 등을 담당하는 클래스
@Component
public class TokenProvider implements InitializingBean {

   private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

   private static final String AUTHORITIES_KEY = "auth";

   private final String secret;
   private final long tokenValidityInMilliseconds;
   private final long rfeshTokenValidityInMilliseconds;

   private Key key;


   public TokenProvider(
           @Value("${jwt.secret}") String secret,
           @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds,
           @Value("${jwt.refreshToken-validity-in-seconds}") long rfeshTokenValidityInMilliseconds) {
      this.secret = secret;
      this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
      this.rfeshTokenValidityInMilliseconds = rfeshTokenValidityInMilliseconds * 1000;
   }

   // 주입받은 secret 값을 BASE64 디코드를 한 다음에 key변수에 할당
   @Override
   public void afterPropertiesSet() {
      byte[] keyBytes = Decoders.BASE64.decode(secret);
      this.key = Keys.hmacShaKeyFor(keyBytes);
   }

   // Authentication의 권한정보를 이용해서 토큰을 생성하는 메소드
   public String createToken(Authentication authentication, String tokenType) {
      String authorities = authentication.getAuthorities().stream()
         .map(GrantedAuthority::getAuthority)
         .collect(Collectors.joining(","));

      long now = (new Date()).getTime();
      Date validity;
      if("access".equals(tokenType)){
         validity = new Date(now + this.tokenValidityInMilliseconds); // application 파일에 설정한 토큰의 만료시간
      }else{
         validity = new Date(now + this.rfeshTokenValidityInMilliseconds); // application 파일에 설정한 토큰의 만료시간
      }

      // jwt 토큰 생성 후 리턴
      return Jwts.builder()
         .setSubject(authentication.getName())
         .claim(AUTHORITIES_KEY, authorities)
         .signWith(key, SignatureAlgorithm.HS512)
         .setExpiration(validity) // 토큰 만료시간 설정
         .compact();
   }

   // 토큰에 담겨있는 정보를 이용해 Authentication객체 리턴
   public Authentication getAuthentication(String token) {
      Claims claims = Jwts // 토큰을 이용해서 claims 생성
              .parserBuilder()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(token)
              .getBody();

      Collection<? extends GrantedAuthority> authorities =
         Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(",")) // 클레임에서 권한 정보를 얻는다
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

      User principal = new User(claims.getSubject(), "", authorities); // 권한정보를 이용해서 User 객체 생성

      return new UsernamePasswordAuthenticationToken(principal, token, authorities); // User객체, 토큰, 권한정보를 이용해서 Authentication 객체 리턴
   }

   // 토큰을 전달받아서 토큰의 유효성 검사
   public boolean validateToken(String token) {
      try {
         Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
         return true;
      } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
         logger.info("잘못된 JWT 서명입니다.");
      } catch (ExpiredJwtException e) {
         logger.info("만료된 JWT 토큰입니다.");
      } catch (UnsupportedJwtException e) {
         logger.info("지원되지 않는 JWT 토큰입니다.");
      } catch (IllegalArgumentException e) {
         logger.info("JWT 토큰이 잘못되었습니다.");
      }
      return false;
   }
}