package com.excilys.tchasset.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.tchasset.log.Logging;
import com.excilys.tchasset.model.QUser;
import com.excilys.tchasset.model.User;
import com.excilys.tchasset.persistence.interfaces.UserRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Repository
public class UserDAO {
	
	@Autowired
	private UserRepository repo;
	
	public List<User> getUsers() {
		Iterable<User> iterable = repo.findAll();
		List<User> users = StreamSupport.stream(iterable.spliterator(), false)
	                                      		.collect(Collectors.toList());
		return users;
	}
	
	public Optional<User> getUser (String username, String password){
		BooleanExpression testPassword = QUser.user.password.eq(password);
		BooleanExpression testGlobal = QUser.user.username.eq(username).and(testPassword);
		Optional<User> res = repo.findOne(testGlobal);
		Logging.info("User "+ username +" with pass starting by "+ password.substring(0,3) +" found and fetch from the db", this.getClass());
		return res;
	}
	
	/**
	 * Add a User with the given caracteristics to the db
	 * @param user
	 * @return user correctly added or not
	 */
	@Transactional
	public boolean addUser(User user) {
		try {
			repo.save(user);
			Logging.info("User added on the db : "+ user.toString(), this.getClass());
			return true;
		} catch (Exception e) {
			Logging.info("Error when inserting a new user in the db for : "+ user.toString(), this.getClass());
			return false;
		}
	}
}
