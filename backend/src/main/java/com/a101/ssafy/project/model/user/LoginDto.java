package com.a101.ssafy.project.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class LoginDto {
	private String email;
	private String password;
	
	public LoginDto() {
	}

	public LoginDto(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
}
