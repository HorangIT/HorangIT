package com.a101.ssafy.project.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a101.ssafy.project.jwt.TokenProvider;
import com.a101.ssafy.project.model.BasicResponse;
import com.a101.ssafy.project.model.user.LoginDto;
import com.a101.ssafy.project.model.user.SignupDto;
import com.a101.ssafy.project.model.user.TokenDto;
import com.a101.ssafy.project.model.user.User;
import com.a101.ssafy.project.repository.UserRepository;
import com.a101.ssafy.project.service.UserServiceImpl;

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
    UserServiceImpl userService;

	@ApiOperation(value = "로그인")
	@PostMapping("/login")
	public Object login(@RequestBody LoginDto loginDto) {
		return userService.login(loginDto);
	}
	
	@ApiOperation(value = "회원가입")
	@PostMapping("/signup")
	public Object signup(@Valid @RequestBody SignupDto signupDto) {
		return userService.signup(signupDto);
	}
}
