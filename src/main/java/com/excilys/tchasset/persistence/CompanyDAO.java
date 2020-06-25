package com.excilys.tchasset.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.tchasset.log.Logging;
import com.excilys.tchasset.mapper.CompanyMapper;
import com.excilys.tchasset.model.Company;

@Repository
public class CompanyDAO {
	
	@Autowired
	private CompanyMapper companyMapper;

	public List<Company> getCompanies() {
		List<Company> companies = new ArrayList<Company>();
		String query = EnumQuery.SELECTCOMPANY.toString();
		try(Connection conn = Dao.getInstance().getConn();
			Statement statement = conn.createStatement()) {
			
			ResultSet res = statement.executeQuery(query);
			while(res.next()) {
				companies.add(companyMapper.getCompany(res));
			}
			
		} catch (SQLException e) {
			Logging.error(e.getMessage(), CompanyDAO.class);
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
				companies.add(companyMapper.getCompany(res));
			}
			
		} catch (SQLException e) {
			Logging.error(e.getMessage(), CompanyDAO.class);
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
				company = Optional.of(companyMapper.getCompany(res));
			}
		} catch (SQLException e) {
			Logging.error(e.getMessage(), CompanyDAO.class);
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
				company = Optional.of(companyMapper.getCompany(res));
			}
		} catch (SQLException e) {
			Logging.error(e.getMessage(), CompanyDAO.class);
		}
		return company;
	}
	
	@Transactional
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
			Logging.error(e.getMessage(), CompanyDAO.class);
		}
	}
}