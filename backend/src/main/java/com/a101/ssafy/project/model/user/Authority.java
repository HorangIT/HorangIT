package com.a101.ssafy.project.model.user;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@ToString
@Setter
@Getter
@Entity
public class Authority {

	@Id
	@Column(name = "authority_name", length = 50)
	private String authorityName;

	public Authority() {
	}

	public Authority(String authorityName) {
		super();
		this.authorityName = authorityName;
	}
}