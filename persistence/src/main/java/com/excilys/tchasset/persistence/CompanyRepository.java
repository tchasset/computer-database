package com.excilys.tchasset.persistence;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.excilys.tchasset.model.Company;

public interface CompanyRepository extends PagingAndSortingRepository<Company, Integer>, QuerydslPredicateExecutor<Company>{}