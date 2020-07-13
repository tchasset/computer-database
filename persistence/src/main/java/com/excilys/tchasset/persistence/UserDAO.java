package com.excilys.tchasset.persistence;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.tchasset.model.User;
import com.excilys.tchasset.persistence.interfaces.UserRepository;

@Repository
public class UserDAO {
	
	@Autowired
	private UserRepository repo;
	
	public List<User> getUsers() {
		Iterable<User> iterable = repo.findAll();
		List<User> companies = StreamSupport.stream(iterable.spliterator(), false)
	                                      		.collect(Collectors.toList());
		return companies;
	}
}
