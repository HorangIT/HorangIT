package com.a101.ssafy.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.a101.ssafy.project.dao.UserDao;
import com.a101.ssafy.project.jwt.TokenProvider;
import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.user.TokenDto;
import com.a101.ssafy.project.model.user.User;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
						@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
						@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
						@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RequestMapping("/account")
@RestController
public class UserController {
	
    @Autowired
    UserDao userDao;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
	private final TokenProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	public UserController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}

	@ApiOperation(value = "로그인")
	@GetMapping("/login")
	public Object login(@RequestParam(required = true) final String email,
						@RequestParam(required = true) final String password) {
		final BasicResponse result = new BasicResponse();
		User user = userDao.findByEmail(email);
		
		if(user == null || !passwordEncoder.matches(password, user.getPassword())) {
			result.status = false;
	        result.data = "로그인 실패";
	        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		
		// 이메일과, 비밀번호를 통해서 authenticationToken 생성
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// 인증정보를 통해서 JWT 토큰생성
		String jwt = tokenProvider.createToken(authentication);
        
        result.status = true;
        result.data = "로그인 성공";
        result.object = new TokenDto(jwt);
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
