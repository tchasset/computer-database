package com.excilys.tchasset.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.excilys.tchasset.dto.CompanyDTO;
import com.excilys.tchasset.model.Company;

public class CompanyMapperTest {
	
	@Test
	public void companyFromDTO_true() {
		Company company = new Company.Builder("UneCompanie").setId(69).build();
		
		CompanyDTO companyDTO = new CompanyDTO.Builder("UneCompanie").setId("69").build();
														
		assertEquals(company, CompanyMapper.fromDTO(companyDTO).get());
	}
	
	@Test
	public void companyFromDTO_false() {
		Company company = new Company.Builder("UneFausseCompanie").setId(70).build();
		
		CompanyDTO companyDTO = new CompanyDTO.Builder("UneCompanie").setId("69").build();
								
		assertNotEquals(company, CompanyMapper.fromDTO(companyDTO).get());
	}
	
	@Test
	public void companyToDTO_true() {
		Company company = new Company.Builder("UneCompanie").setId(69).build();
		
		CompanyDTO companyDTO = new CompanyDTO.Builder("UneCompanie").setId("69").build();
														
		assertEquals(companyDTO, CompanyMapper.toDTO(company).get());
	}
	
	@Test
	public void companyToDTO_false() {
		Company company = new Company.Builder("UneFausseCompanie").setId(70).build();
		
		CompanyDTO companyDTO = new CompanyDTO.Builder("UneCompanie").setId("69").build();
								
		assertNotEquals(companyDTO, CompanyMapper.toDTO(company).get());
	}
}
