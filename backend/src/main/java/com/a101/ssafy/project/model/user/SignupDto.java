package com.a101.ssafy.project.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class SignupDto {
	private String nickname;
	private String email;
	private String password;
	
	public SignupDto() {
	}

	public SignupDto(String nickname, String email, String password) {
		super();
		this.nickname = nickname;
		this.email = email;
		this.password = password;
	}
}
