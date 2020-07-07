package com.excilys.tchasset.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.excilys.tchasset.dto.CompanyDTO;
import com.excilys.tchasset.log.Logging;
import com.excilys.tchasset.model.Company;

public class CompanyMapper {
	
	/*
	 * @param company	bean to convert into DTO
	 * 
	 * @return 			The company bean to a company DTO
	 */
	public static CompanyDTO toDTO(Company company) {
		CompanyDTO companyDTO=new CompanyDTO();
		
		if(company!=null) {
			String id = String.valueOf(company.getId());
			String name = company.getName();
			companyDTO = new CompanyDTO.Builder()	.setCompanyId(id)
													.setCompanyName(name).build();
		}
		
		return companyDTO;
	}
	
	/*
	 * @param companyDTO	DTO to convert into bean
	 * 
	 * @return 				The company bean from a company DTO
	 */
	public static Company fromDTO(CompanyDTO companyDTO) {
		Company company = null;
		
		if(companyDTO!=null) {
			int id = Integer.valueOf(companyDTO.getCompanyId());
			String name = companyDTO.getCompanyName();
			company = new Company.Builder()	.setId(id)
											.setName(name).build();
		}
		
		return company;
	}
	
	@Deprecated
	public List<Company> sorted(List<Company> company) {
		company.sort((Company c1, Company c2)->c1.getName().toLowerCase().compareTo(c2.getName().toLowerCase()));
		return company;
	}
	
	/* @param res	result of a SQL request 
	 * @return 		The company found by the request
	 */
	@Deprecated
	public Company getCompany(ResultSet res) {
		Company comp=new Company();
		try {
			comp.setId(res.getInt("id"));
			comp.setName(res.getString("name"));
		} catch (SQLException e) {
			Logging.error(e.getMessage(),CompanyMapper.class);
		}
		return comp;
	}
}