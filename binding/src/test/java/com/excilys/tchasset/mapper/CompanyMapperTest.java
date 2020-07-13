package com.excilys.tchasset.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.excilys.tchasset.dto.CompanyDTO;
import com.excilys.tchasset.model.Company;

public class CompanyMapperTest {
	
	@Test
	public void companyFromDTO_true() {
		Company company = new Company.Builder()	.setId(69)
												.setName("UneCompanie")
												.build();
		
		CompanyDTO companyDTO = new CompanyDTO.Builder().setCompanyId("69")
														.setCompanyName("UneCompanie")
														.build();
														
		assertEquals(company, CompanyMapper.fromDTO(companyDTO).get());
	}
	
	@Test
	public void companyFromDTO_false() {
		Company company = new Company.Builder()	.setId(70)
												.setName("UneFausseCompanie")
												.build();
		
		CompanyDTO companyDTO = new CompanyDTO.Builder().setCompanyId("69")
														.setCompanyName("UneCompanie")
														.build();
								
		assertNotEquals(company, CompanyMapper.fromDTO(companyDTO).get());
	}
	
	@Test
	public void companyToDTO_true() {
		Company company = new Company.Builder()	.setId(69)
												.setName("UneCompanie")
												.build();
		
		CompanyDTO companyDTO = new CompanyDTO.Builder().setCompanyId("69")
														.setCompanyName("UneCompanie")
														.build();
														
		assertEquals(companyDTO, CompanyMapper.toDTO(company).get());
	}
	
	@Test
	public void companyToDTO_false() {
		Company company = new Company.Builder()	.setId(70)
												.setName("UneFausseCompanie")
												.build();
		
		CompanyDTO companyDTO = new CompanyDTO.Builder().setCompanyId("69")
														.setCompanyName("UneCompanie")
														.build();
								
		assertNotEquals(companyDTO, CompanyMapper.toDTO(company).get());
	}
}
