package com.excilys.tchasset.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

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
