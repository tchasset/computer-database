package com.excilys.tchasset.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.tchasset.log.Logging;
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
		String query = EnumQuery.SELECTCOMPANY.toString();
		try(Connection conn = Dao.getInstance().getConn();
			Statement statement = conn.createStatement()) {
			
			ResultSet res = statement.executeQuery(query);
			while(res.next()) {
				companies.add(CompanyMapper.getInstance().getCompany(res));
			}
			
		} catch (SQLException e) {
			Logging.writeFile(e.getMessage());
		}
		return companies;
	}
	
	public List<Company> getCompaniesOrderBy(String order) {
		List<Company> companies = new ArrayList<Company>();
		String query = EnumQuery.SELECTCOMPANY
					+ " ORDER BY name "+order+";";
		try(Connection conn = Dao.getInstance().getConn();
			Statement statement = conn.createStatement()) {
			
			ResultSet res = statement.executeQuery(query);
			while(res.next()) {
				companies.add(CompanyMapper.getInstance().getCompany(res));
			}
			
		} catch (SQLException e) {
			Logging.writeFile(e.getMessage());
		}
		return companies;
	}
	
	public Optional<Company> getById(int id) {
		Optional<Company> company = Optional.empty();
		String query = EnumQuery.SELECTCOMPANY
					+ " WHERE id=?;";
		try(Connection conn =Dao.getInstance().getConn();
			PreparedStatement statementCompany = conn.prepareStatement(query)) {
			
			statementCompany.setInt(1,id);
			ResultSet res = statementCompany.executeQuery();
			while(res.next()) {
				company = Optional.of(CompanyMapper.getInstance().getCompany(res));
			}
		} catch (SQLException e) {
			Logging.writeFile(e.getMessage());
		}
		return company;
	}
	
	public Optional<Company> getByName(String name) {
		Optional<Company> company = Optional.empty();
		String query = EnumQuery.SELECTCOMPANY
					 + " WHERE name=?;";
		try(Connection conn = Dao.getInstance().getConn();
			PreparedStatement statementCompany = conn.prepareStatement(query)) {
			
			statementCompany.setString(1,name);
			ResultSet res = statementCompany.executeQuery();
			while(res.next()) {
				company = Optional.of(CompanyMapper.getInstance().getCompany(res));
			}
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
		return company;
	}
	
	public void deleteCompany(int id) {
		String query1="DELETE computer "
					+ "FROM computer "
					+ "WHERE company_id=?;",
			   query2="DELETE company "
			   		+ "FROM company "
			   		+ "WHERE id=?;";
		try (	Connection conn = Dao.getInstance().getConn();
				PreparedStatement deleteComputer = conn.prepareStatement(query1);
				PreparedStatement deleteCompany  = conn.prepareStatement(query2)) {
			conn.setAutoCommit(false);
			
			deleteComputer.setInt(1, id);
			deleteComputer.execute();
			
			deleteCompany.setInt(1, id);
			deleteCompany.execute();
			
			conn.commit();
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
	}
}
