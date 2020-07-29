package com.excilys.tchasset.persistence.interfaces;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.excilys.tchasset.model.User;

public interface UserRepository extends CrudRepository<User, String>, QuerydslPredicateExecutor<User>{
    User findByUsername(String username);
}
