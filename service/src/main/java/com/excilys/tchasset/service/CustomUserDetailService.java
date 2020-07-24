package com.excilys.tchasset.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.excilys.tchasset.dto.UserDTO;
import com.excilys.tchasset.model.User;

public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	UserService userService;
	
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		throw new UsernameNotFoundException("no password given => no username searched");
	}
	
	@Transactional
	public UserDetails loadUserByUsername(String username, String password) throws UsernameNotFoundException{
		
		if (username.trim().isEmpty()) {
			throw new UsernameNotFoundException("username is empty");
		}
		
		Optional<User> userOpt = userService.getUser(new UserDTO.Builder().setUsername(username).setPassword(password).build());
		
		if(userOpt.isEmpty()) {
			throw new UsernameNotFoundException("User " + username + " not found");
		}
		
		User user = userOpt.get();
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user));
	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		String role = user.getRole().toString();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}
}
