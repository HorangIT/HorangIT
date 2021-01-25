package com.a101.ssafy.project.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.a101.ssafy.project.dao.UserRepository;
import com.a101.ssafy.project.model.user.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
   private final UserRepository userDao;

   public CustomUserDetailsService(UserRepository userDao) {
      this.userDao = userDao;
   }

   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String email) {
	   Optional<User> user =  userDao.findOneWithAuthoritiesByEmail(email);
	   if(user.isPresent()) {
		   return createUser(email, user.get());
	   }
	   
	   throw new UsernameNotFoundException("데이터베이스에서 찾을 수 없습니다.");
   }

   private org.springframework.security.core.userdetails.User createUser(String username, User user) {
      List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
              .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
              .collect(Collectors.toList());
      return new org.springframework.security.core.userdetails.User(user.getEmail(),
              user.getPassword(),
              grantedAuthorities);
   }
}