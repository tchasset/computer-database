package com.excilys.tchasset.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.excilys.tchasset.log.Logging;
import com.excilys.tchasset.model.QUser;
import com.excilys.tchasset.model.User;
import com.excilys.tchasset.persistence.interfaces.UserRepository;
import com.querydsl.core.types.dsl.BooleanExpression;

@Repository
@Transactional
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
		BooleanExpression testUsername = QUser.user.username.eq(username);
		Optional<User> res = repo.findOne(testUsername);
		if(!res.isPresent()) {
			Logging.info("no User found with username : " + username, this.getClass());
			return res;
		}
		if(BCrypt.checkpw(password, res.get().getPassword())) {
			Logging.info("User "+ username +" with pass starting by "+ password +" found and fetch from the db", this.getClass());
			return res;
		}
		Logging.info("Found user with username : " + username + " but wrong password", this.getClass());
		return res;
	}
	
	/**
	 * Add a User with the given caracteristics to the db
	 * @param user
	 * @return user correctly added or not
	 */
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
