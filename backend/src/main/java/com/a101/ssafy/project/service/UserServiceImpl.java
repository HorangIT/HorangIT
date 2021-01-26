package com.a101.ssafy.project.service;

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
import com.a101.ssafy.project.model.user.Authority;
import com.a101.ssafy.project.model.user.LoginDto;
import com.a101.ssafy.project.model.user.SignupDto;
import com.a101.ssafy.project.model.user.TokenDto;
import com.a101.ssafy.project.model.user.User;

import java.util.Collections;

@Service
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider,
					   AuthenticationManagerBuilder authenticationManagerBuilder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}
    
    public Object login(LoginDto loginDto) {
    	final BasicResponse result = new BasicResponse();
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
		String jwt = tokenProvider.createToken(authentication);
		
        result.status = true;
        result.data = "로그인 성공";
        result.object = new TokenDto(jwt);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Transactional
    public Object signup(SignupDto signupDto) {
    	final BasicResponse result = new BasicResponse();
    	
    	System.out.println(signupDto);
    	
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
        
        result.status = true;
		result.data = "회원가입 성공";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}