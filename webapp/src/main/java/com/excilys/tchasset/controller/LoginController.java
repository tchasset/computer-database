package com.excilys.tchasset.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.tchasset.dto.UserDTO;
import com.excilys.tchasset.log.Logging;
import com.excilys.tchasset.model.User;
import com.excilys.tchasset.service.UserService;


@Controller
public class LoginController {
	
	@Autowired
	private UserService userServ;
	
	@GetMapping("/loginAttempt")
	public ModelAndView credentialCheck (@RequestParam(name="username", required=true) String username,
										 @RequestParam(name="password", required=true) String password) {
		
		Optional<User> resCredential = userServ.getUser(new UserDTO.Builder().setUsername(username).setPassword(password).build());
		if(!resCredential.isPresent()) {
			return new ModelAndView("login").addObject("error", true);
		}
		Logging.info("connection succesfull, redirect to dashboard with user id : " + resCredential, LoginController.class);
		return new ModelAndView("dashboard");
	}
	
	@GetMapping("/login")
	public ModelAndView loginScreenLoad() {
		return new ModelAndView("login");
	}
}