package com.excilys.tchasset.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.excilys.tchasset.dto.CompanyDTO;
import com.excilys.tchasset.log.Logging;
import com.excilys.tchasset.model.Company;

public class CompanyMapper {
	
	private static CompanyMapper instance;
	
	public static final CompanyMapper getInstance() {
		if (CompanyMapper.instance == null) {
			synchronized(CompanyMapper.class) {
				if (CompanyMapper.instance == null) {
					CompanyMapper.instance = new CompanyMapper();
	            }
	        }
		}
	    return CompanyMapper.instance;
    }
	
	public CompanyDTO toDTO(Company company) {
		CompanyDTO companyDTO=new CompanyDTO();
		
		if(company!=null) {
			String id = String.valueOf(company.getId());
			String name = company.getName();
			companyDTO = new CompanyDTO.Builder()	.setId(id)
													.setName(name).build();
		}
		
		return companyDTO;
	}
	
	public Company fromDTO(CompanyDTO companyDTO) {
		Company company = null;
		
		if(companyDTO!=null) {
			int id = Integer.valueOf(companyDTO.getId());
			String name = companyDTO.getName();
			company = new Company.Builder()	.setId(id)
											.setName(name).build();
		}
		
		return company;
	}
	
	public List<Company> sorted(List<Company> company) {
		company.sort((Company c1, Company c2)->c1.getName().toLowerCase().compareTo(c2.getName().toLowerCase()));
		return company;
	}
	
	public Company getCompany(ResultSet res) {
		Company comp=new Company();
		try {
			comp.setId(res.getInt("id"));
			comp.setName(res.getString("name"));
		} catch (SQLException e) {
			Logging.writeFile(e.getMessage());
		}
		return comp;
	}
}
