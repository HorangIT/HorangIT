package com.a101.ssafy.project.service;

import com.a101.ssafy.project.model.user.LoginDto;
import com.a101.ssafy.project.model.user.SignupDto;

public interface UserService {
	
	public Object login(LoginDto loginDto);
	
	public Object signup(SignupDto signupDto);
}
