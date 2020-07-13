package com.excilys.tchasset.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.tchasset.model.User;
import com.excilys.tchasset.persistence.UserDAO;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	public List<User> getUsers() {
		return userDAO.getUsers();
	}
}
