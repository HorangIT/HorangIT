package com.a101.ssafy.project.service;

import com.a101.ssafy.project.model.user.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a101.ssafy.project.repository.UserRepository;
import com.a101.ssafy.project.jwt.TokenProvider;
import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.redis.RedisUtil;

import java.util.Collections;

@Service
public class UserServiceImpl {
	@Autowired
	RedisUtil redisUtil;

	private final long rfeshTokenValidityInMilliseconds;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserServiceImpl(@Value("${jwt.refreshToken-validity-in-seconds}") long rfeshTokenValidityInMilliseconds, UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider,
						   AuthenticationManagerBuilder authenticationManagerBuilder) {
		super();
		this.rfeshTokenValidityInMilliseconds = rfeshTokenValidityInMilliseconds;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}
    
    @SuppressWarnings("unchecked")
	public Object login(LoginDto loginDto) {
		BasicResponse result = new BasicResponse();
    	User user = userRepository.findByEmail(loginDto.getEmail());
    	
		if(user == null || !passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
			result.status = false;
	        result.data = "로그인 실패";
	        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		
		// 이메일과, 비밀번호를 통해서 authenticationToken 생성
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// 인증정보를 통해서 JWT 토큰생성
		String accessToken = tokenProvider.createToken(authentication, "access");
		String refreshToken = tokenProvider.createToken(authentication, "refresh");
		redisUtil.setDataExpire(loginDto.getEmail(), refreshToken, rfeshTokenValidityInMilliseconds * 1000);

		UserDto userDto = new UserDto(user.getId(), user.getEmail(), user.getUsername(), user.getNickname(), user.getAddress(), user.getPhone());
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("accessToken", accessToken);
        jsonObject.put("refreshToken", refreshToken);
        jsonObject.put("user", userDto);
        
        result.status = true;
        result.data = "로그인 성공";
        result.object = jsonObject;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
	@Transactional
    public Object signup(SignupDto signupDto) {
    	BasicResponse result = new BasicResponse();
    	
    	if(userRepository.findByEmail(signupDto.getEmail()) != null) {
    		result.status = false;
    		result.data = "회원가입 실패 (이메일 중복)";
    		return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    	}

        //빌더 패턴의 사용
        Authority authority = Authority.builder()
                					   .authorityName("ROLE_USER")
                					   .build();

        User user = User.builder()
                		.email(signupDto.getEmail())
                		.password(passwordEncoder.encode(signupDto.getPassword()))
                		.nickname(signupDto.getNickname())
                		.authorities(Collections.singleton(authority))
                		.build();
        
        userRepository.save(user);
        
		UserDto userDto = new UserDto(user.getId(), user.getEmail(), user.getUsername(), user.getNickname(), user.getAddress(), user.getPhone());
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", userDto);
        
        redisUtil.setHdata("user", userDto.getId()+"", userDto.getNickname());
        
        result.status = true;
		result.data = "회원가입 성공";
        result.object = jsonObject;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

	public Object createAccessToken(AccessTokenDto accessTokenDto) {
		BasicResponse result = new BasicResponse();
		result.status = false;

		if(accessTokenDto == null) {
			result.data = "토큰 생성 실패";
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}

		String refreshToken = redisUtil.getData(accessTokenDto.getEmail());
		if(refreshToken == null || !accessTokenDto.getRefreshToken().equals(refreshToken)) {
			result.data = "토큰 생성 실패";
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(accessTokenDto.getEmail(), accessTokenDto.getPassword());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String accessToken = tokenProvider.createToken(authentication, "access");

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("accessToken", accessToken);

		result.status = true;
		result.data = "토큰 생성 성공";
		result.object = jsonObject;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}