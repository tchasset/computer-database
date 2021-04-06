package com.excilys.tchasset.persistence.interfaces;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.excilys.tchasset.model.Computer;

public interface ComputerRepository extends PagingAndSortingRepository<Computer, Integer>, QuerydslPredicateExecutor<Computer>{}