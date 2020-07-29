package com.excilys.tchasset.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.tchasset.dto.UserDTO;
import com.excilys.tchasset.log.Logging;
import com.excilys.tchasset.model.User;

@Service("UserDetailService")
public class CustomUserDetailService implements UserDetailsService {
	@Autowired
	UserService userService;
	
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		Logging.info("LoadUser "+ username, CustomUserDetailService.class);
		
		if (username.trim().isEmpty()) {
			throw new UsernameNotFoundException("username is empty");
		}

		User user;
		try {
			user = userService.findByUsername(username);
		} catch(DataAccessException ex) {
			user = null;
		}
		
		return user;
	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		String role = user.getRole();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}
}
