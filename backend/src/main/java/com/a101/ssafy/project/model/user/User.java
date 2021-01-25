package com.a101.ssafy.project.model.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@ToString
@Setter
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    
    @Column(name = "user_name")
    private String userName;
    
    @Column(name = "nickname")
    private String nickName;
    
    private String address;
    private String phone;
    
    @Column(name = "authority_name")
    private String authorityName;
    
    @ManyToMany
    @JoinTable(
       name = "user_authority",
       joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
       inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
    
    public User() {
	}

	public User(Long id, String email, String password, String userName, String nickName, String address, String phone,
			String authorityName, Set<Authority> authorities) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.userName = userName;
		this.nickName = nickName;
		this.address = address;
		this.phone = phone;
		this.authorityName = authorityName;
		this.authorities = authorities;
	}
}
