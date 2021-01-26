package com.a101.ssafy.project.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class UserDto {
	private Long id;
    private String email;
    private String username;
    private String nickname;
    private String address;
    private String phone;
    
    public UserDto() {
	}

	public UserDto(Long id, String email, String username, String nickname, String address, String phone) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.nickname = nickname;
		this.address = address;
		this.phone = phone;
	}
}
