package com.a101.ssafy.project.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class TokenDto {
    private String token;
    
    public TokenDto() {
	}

	public TokenDto(String token) {
		super();
		this.token = token;
	}
}
