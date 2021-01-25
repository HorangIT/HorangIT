package com.a101.ssafy.project.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class SignupDto {
	private String email;
	private String password;
	private String address;
	private String userName;
	private String nickName;
	private String phone;
	
	public SignupDto() {
	}

	public SignupDto(String email, String password, String address, String userName, String nickName, String phone) {
		super();
		this.email = email;
		this.password = password;
		this.address = address;
		this.userName = userName;
		this.nickName = nickName;
		this.phone = phone;
	}
}
