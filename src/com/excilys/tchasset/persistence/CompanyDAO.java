package com.excilys.tchasset.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.tchasset.mapper.CompanyMapper;
import com.excilys.tchasset.model.Company;

public class CompanyDAO {
	
	private static CompanyDAO instance=null;
	
	public static final CompanyDAO getInstance() {
		if (CompanyDAO.instance == null) {
			synchronized(CompanyDAO.class) {
				if (CompanyDAO.instance == null) {
					CompanyDAO.instance = new CompanyDAO();
	            }
	        }
		}
	    return CompanyDAO.instance;
    }

	public List<Company> getCompanies() {
		List<Company> companies = new ArrayList<Company>();
		String query = "SELECT id,name FROM company;";
		try(Statement statement = Dao.getInstance().getConn().createStatement()) {
			ResultSet res = statement.executeQuery(query);
			while(res.next()) {
				companies.add(CompanyMapper.getInstance().getCompany(res));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companies;
	}
	
	public Optional<Company> getById(int id) {
		Optional<Company> company = Optional.empty();
		String query = "SELECT id,name FROM company WHERE id=?;";
		try (PreparedStatement statementCompany = Dao.getInstance().getConn().prepareStatement(query)) {
			statementCompany.setInt(1,id);
			ResultSet res = statementCompany.executeQuery();
			while(res.next()) {
				company = Optional.of(CompanyMapper.getInstance().getCompany(res));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}
}
