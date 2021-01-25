package com.a101.ssafy.project.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.a101.ssafy.project.model.user.User;
import java.lang.String;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
	
	@EntityGraph(attributePaths = "authorities")
	Optional<User> findOneWithAuthoritiesByEmail(String email);
}
