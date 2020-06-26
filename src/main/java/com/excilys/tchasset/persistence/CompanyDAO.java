package com.excilys.tchasset.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.tchasset.log.Logging;
import com.excilys.tchasset.model.Company;

@Repository
public class CompanyDAO {
	
	private static class CompanyRowMapper implements RowMapper<Company> {
		@Override
		public Company mapRow(final ResultSet rs, final int rowNum) throws SQLException {
			return new Company.Builder().setId(rs.getInt("id"))
										.setName(rs.getString("name"))
										.build();
		}
	}
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private CompanyRowMapper rowMapper = new CompanyRowMapper();

	public List<Company> getCompanies() {
		String query = EnumQuery.SELECTCOMPANY.toString();
		try {
			return jdbcTemplate.query(query, rowMapper);
		} catch (DataAccessException e) {
		    Logging.error(e.getMessage(), CompanyDAO.class);
		    return new ArrayList<>();
		}
	}
	
	public List<Company> getCompaniesOrderBy(String order) {
		String query = EnumQuery.SELECTCOMPANY
					+ " ORDER BY name "+order+";";
		try {
			return jdbcTemplate.query(query, rowMapper);
		} catch (DataAccessException e) {
		    Logging.error(e.getMessage(), CompanyDAO.class);
		    return new ArrayList<>();
		}
	}
	
	public Optional<Company> getById(int id) {
		String query = EnumQuery.SELECTCOMPANY
					+ " WHERE id=?;";
		try {
			return Optional.of(jdbcTemplate.queryForObject(query, rowMapper, id));
		} catch (DataAccessException e) {
			return Optional.empty();
		}
	}
	
	public Optional<Company> getByName(String name) {
		String query = EnumQuery.SELECTCOMPANY
					 + " WHERE name=?;";
		try {
			return Optional.of(jdbcTemplate.queryForObject(query, rowMapper, name));
		} catch (DataAccessException e) {
			return Optional.empty();
		}
	}
	
	@Transactional
	public void deleteCompany(int id) {
		String query1="DELETE computer "
					+ "FROM computer "
					+ "WHERE company_id=?;",
			   query2="DELETE company "
			   		+ "FROM company "
			   		+ "WHERE id=?;";
		try {
			jdbcTemplate.update(query1, id);
			jdbcTemplate.update(query2, id);
		} catch (DataAccessException e) {
			Logging.error(e.getMessage(), CompanyDAO.class);
		}
	}
}