package com.excilys.tchasset.service;

import com.excilys.tchasset.log.Logging;
import com.excilys.tchasset.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("UserDetailService")
public class CustomUserDetailService implements UserDetailsService {

	private final UserService userService;

	@Autowired
	public CustomUserDetailService(UserService userService) {
		this.userService = userService;
	}

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

}
