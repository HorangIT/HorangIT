package com.a101.ssafy.project.service;

import com.a101.ssafy.project.model.user.LoginDto;
import com.a101.ssafy.project.model.user.SignupDto;
import com.a101.ssafy.project.model.user.AccessTokenDto;

public interface UserService {

    public Object login(LoginDto loginDto);

    public Object signup(SignupDto signupDto);

    public Object createAccessToken(AccessTokenDto accessTokenDto);

    public Object logout(LoginDto logoutDto);
}
