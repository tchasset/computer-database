package com.excilys.tchasset.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.excilys.tchasset.log.Logging;
import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.model.Page;

@Repository
public class ComputerDAO {

	private static class ComputerRowMapper implements RowMapper<Computer> {
		@Override
		public Computer mapRow(final ResultSet rs, final int rowNum) throws SQLException {
			Company company = new Company.Builder().setId(rs.getInt("company.id")).setName(rs.getString("company.name")).build();
			LocalDate intro = null;
			LocalDate disco = null;
			if(rs.getDate("introduced")!=null)
				intro = rs.getDate("introduced").toLocalDate();
			if(rs.getDate("discontinued")!=null)
				disco = rs.getDate("discontinued").toLocalDate();
			return new Computer.Builder()	.setId(rs.getInt("id"))
											.setName(rs.getString("name"))
											.setIntroduced(intro)
											.setDiscontinued(disco)
											.setCompany(company)
											.build();
		}
	}
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private ComputerRowMapper rowMapper = new ComputerRowMapper();

	/* @param page 		paginate request if not null
	 * @return			ALL computers with/without pagination
	 */
	public List<Computer> getAllComputers(Page page){
		String query = EnumQuery.SELECTCOMPUTER.toString();
		if(page!=null) {
			Object[] obj = {(page.getCurrentPage()-1)*page.getSizePage(), page.getSizePage()};
			return getComputersPaginate(query, obj);
		}
		return getComputers(query);
	}

	/* @param page 		paginate request if not null
	 * @param order 	choose order type (ASC or DESC)
	 * @return			ALL computers alphebetical ordered by name ASC or DESC
	 */
	public List<Computer> getComputersOrderByComputer(Page page, String order){
		String query = EnumQuery.SELECTCOMPUTER
				+ " ORDER BY computer.name "+order;

		return getComputers(page, query);
	}

	/* @param page 		paginate request if not null
	 * @param order 	choose order type (ASC or DESC) 
	 * @return 			ALL computers alphebetical ordered by company name ASC or DESC
	 */
	public List<Computer> getComputersOrderByCompany(Page page, String order){
		String query = EnumQuery.SELECTCOMPUTER
				+ " ORDER BY company.name IS NULL, company.name "+order;

		return getComputers(page, query);
	}

	/* @param id	searching computer by it 
	 * @return 		computer with given ID if it exists
	 */
	public Optional<Computer> getById(int id) {
		String query = EnumQuery.SELECTCOMPUTER
				+ " WHERE computer.id=?;";
		try {
			return Optional.of(jdbcTemplate.queryForObject(query, rowMapper, id));
		} catch (DataAccessException e) {
			return Optional.empty();
		}
	}

	/* @param page 		paginate request if not null
	 * @param name		searching computer by it  
	 * @return 			computers with given name (company or computer name)
	 */	
	public List<Computer> getByAllName(Page page, String name) {
		String query = EnumQuery.SELECTCOMPUTER
				+ " WHERE computer.name LIKE ?"
				+ " OR company.name LIKE ?";
		Object[] obj;
		if(page!=null) {
			obj = new Object[] {"%"+name+"%", "%"+name+"%", (page.getCurrentPage()-1)*page.getSizePage(), page.getSizePage()};
			return getComputersPaginate(query, obj);
		}   
		obj = new Object[] {"%"+name+"%", "%"+name+"%"};
		return getComputersWithParam(query, obj);
	}
	
	/* @param page 		paginate request if not null
	 * @param name		searching computer by it  
	 * @return 			computers with given name
	 */
	public List<Computer> getByName(Page page, String name) {
		String query = EnumQuery.SELECTCOMPUTER
				+ " WHERE computer.name LIKE ?";
		Object[] obj;
		if(page!=null) {
			obj = new Object[] {"%"+name+"%", (page.getCurrentPage()-1)*page.getSizePage(), page.getSizePage()};
			return getComputersPaginate(query, obj);
		} 
		obj = new Object[] {"%"+name+"%"};
		return getComputersWithParam(query, obj);
	}

	/* @param page 		paginate request if not null
	 * @param name		searching computer by it  
	 * @return 			computers with given company name
	 */
	public List<Computer> getByCompany(Page page, String name){
		String query = EnumQuery.SELECTCOMPUTER
				+ " WHERE company.name=?";
		Object[] obj;
		if(page!=null) {
			obj = new Object[] {"%"+name+"%", (page.getCurrentPage()-1)*page.getSizePage(), page.getSizePage()};
			return getComputersPaginate(query, obj);
		} 
		obj = new Object[] {"%"+name+"%"};
		return getComputersWithParam(query, obj);
	}

	/* Check if the request should be paginate or not
	 * 
	 * @param page 		paginate request if not null
	 * @param query		query of the desired SQL request 
	 */
	private List<Computer> getComputers(Page page, String query){
		if(page!=null) {
			Object[] obj = {(page.getCurrentPage()-1)*page.getSizePage(), page.getSizePage()};
			return getComputersPaginate(query, obj);
		}
		return getComputers(query);
	}

	/* @param query		query of the desired SQL request  
	 * @return 			computers for the given query
	 */
	private List<Computer> getComputers(String query) {
		try {
			return jdbcTemplate.query(query, rowMapper);
		} catch (DataAccessException e) {
		    Logging.error(e.getMessage(), ComputerDAO.class);
		    return new ArrayList<>();
		}
	}
	
	/* @param query		query of the desired SQL request 
	 * @param obj		list of parameters for the query
	 * @return 			computers for the given query
	 */
	private List<Computer> getComputersWithParam(String query, Object[] obj) {
		try {
			return jdbcTemplate.query(query, obj, rowMapper);
		} catch (DataAccessException e) {
		    Logging.error(e.getMessage(), ComputerDAO.class);
		    return new ArrayList<>();
		}
	}

	/* @param query		query of the desired SQL request 
	 * @param obj		list of parameters for the query
	 * @return 			computers for the given query (paginated)
	 */
	private List<Computer> getComputersPaginate(String query, Object[] obj) {
		query += EnumQuery.LIMIT;
		try {
			return jdbcTemplate.query(query, obj, rowMapper);
		} catch (DataAccessException e) {
		    Logging.error(e.getMessage(), ComputerDAO.class);
		    return new ArrayList<>();
		}
	}
	
    /* Add the computer to the Database
     * 
     * @param computer		computer to add
     */
	public void addComputer(Computer computer) {
		String query = EnumQuery.INSERTCOMPUTER.toString();
		manageComputer(computer, query);
	}
	
	/* Update the computer 
	 * 
	 * @param computer		computer to update
	 */
	public void updateComputer(Computer computer) {
		String query = EnumQuery.UPDATECOMPUTER.toString();
		manageComputer(computer, query);
	}
	
	/* Delete the computer with given ID
	 * 
	 * @param id 			id of the computer to delete
	 */
	public void deleteComputer(int id) {
		String query = EnumQuery.DELETECOMPUTER.toString();
		try {
			jdbcTemplate.update(query, id);
		} catch (DataAccessException e) {
			Logging.error(e.getMessage(), ComputerDAO.class);
		}
	}
	
	/* @return	Number of computers in the database
	 */
	public int getNbComputers() {
		String query = EnumQuery.COUNTCOMPUTER.toString();
		return jdbcTemplate.queryForObject(query, Integer.class);
	}
	

	/* Manage paramater for INSERT/UPDATE queries
	 */
	private void manageComputer(Computer computer, String query) {
		if(!computer.getName().isEmpty()) {
			Object[] obj = new Object[5];
			obj[0] = computer.getName();
			manageNull(obj,computer);
			obj[4] = computer.getId();
			try {
				jdbcTemplate.update(query, obj);
			} catch (DataAccessException e) {
				e.getMessage();
			}
		}
	}

	/* Manage cases where fields are NULL
	 */
	private void manageNull(Object[] obj, Computer computer) {
		obj[1]=null;
		if (computer.getIntroduced()!=null) {
			Timestamp intro = Timestamp.valueOf(LocalDateTime.of(computer.getIntroduced(),LocalTime.of(12, 0)));
			obj[1] = intro;
		}
		obj[2]=null;
		if (computer.getDiscontinued()!=null) {
			Timestamp disco = Timestamp.valueOf(LocalDateTime.of(computer.getDiscontinued(),LocalTime.of(12, 0)));
			obj[2] = disco;
		}
		obj[3]=null;
		if (computer.getCompany()!=null)
			obj[3] = computer.getCompany().getId();
	}
}