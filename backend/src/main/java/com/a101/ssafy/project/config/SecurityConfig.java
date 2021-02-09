package com.a101.ssafy.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.a101.ssafy.project.jwt.JwtAccessDeniedHandler;
import com.a101.ssafy.project.jwt.JwtAuthenticationEntryPoint;
import com.a101.ssafy.project.jwt.JwtSecurityConfig;
import com.a101.ssafy.project.jwt.TokenProvider;


@EnableWebSecurity // 기본적인 web 보안을 활성화
@EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize 어노테이션을 메소드 단위로 사용하기 위해 추가
public class SecurityConfig extends WebSecurityConfigurerAdapter { // 추가적인 설정을 위해서 상속
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    // 비밀번호 encoder로 BCryptPasswordEncoder 사용
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers(
                		"/favicon.ico" // favicon관련 요청은 모두 무시
                        ,"/error"
                        ,"/v2/api-docs" // 스웨거
                        ,"/swagger-resources/**"
                        ,"/swagger-ui.html"
                        ,"/webjars/**"
                        ,"/swagger/**"
                        ,"/index.html"
                        ,"/api/**"
                        
                );
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .csrf().disable()

                // Exception을 핸들링할때 우리가 만듣 클래스로 처리
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests() // HttpServletRequest를 사용하는 모든 요청들에 대한 접근 제한
//                .antMatchers("/account/signup").permitAll() // /account/signup요청은 인증없이 접근 허용
//                .antMatchers("/account/login").permitAll() 
//                .antMatchers("/item").permitAll()
              //나중에 이렇게 위처럼 수정하기
                .antMatchers("/**").permitAll() //나중에 수정!
                .anyRequest().authenticated() // 나머지 요청은 모두 인증을 받아야한다

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
    }
}