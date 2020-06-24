package com.excilys.tchasset.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.tchasset.log.Logging;
import com.excilys.tchasset.mapper.ComputerMapper;
import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.model.Page;

@Repository
public class ComputerDAO {

	@Autowired
	private ComputerMapper computerMapper;

	/*
	 * @param page 		paginate request if not null
	 * 
	 * @return			ALL computers with/without pagination
	 */
	public List<Computer> getAllComputers(Page page){
		String query = EnumQuery.SELECTCOMPUTER.toString();
		if(page!=null)
			return getComputersPaginate(query, page.getCurrentPage(), page.getSizePage());
		return getComputers(query);
	}

	/*
	 * @param page 		paginate request if not null
	 * @param order 	choose order type (ASC or DESC)
	 * 
	 * @return			ALL computers alphebetical ordered by name ASC or DESC
	 */
	public List<Computer> getComputersOrderByComputer(Page page, String order){
		String query = EnumQuery.SELECTCOMPUTER
				+ " ORDER BY computer.name "+order;

		return getComputers(page, query);
	}

	/*
	 * @param page 		paginate request if not null
	 * @param order 	choose order type (ASC or DESC)
	 * 
	 * @return 			ALL computers alphebetical ordered by company name ASC or DESC
	 */
	public List<Computer> getComputersOrderByCompany(Page page, String order){
		String query = EnumQuery.SELECTCOMPUTER
				+ " ORDER BY company.name IS NULL, company.name "+order;

		return getComputers(page, query);
	}

	/*
	 * @param id	searching computer by it
	 *  
	 * @return 		computer with given ID if it exists
	 */
	public Optional<Computer> getById(int id) {
		Optional<Computer> computer = Optional.empty();
		String query = EnumQuery.SELECTCOMPUTER
				+ " WHERE computer.id=?;";

		try(Connection conn = Dao.getInstance().getConn();
			PreparedStatement statementComputer = conn.prepareStatement(query)) {

			statementComputer.setInt(1,id);
			ResultSet res = statementComputer.executeQuery();
			while(res.next()) {
				computer = Optional.of(computerMapper.getComputer(res));
			}
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
		return computer;
	}

	/*
	 * @param page 		paginate request if not null
	 * @param name		searching computer by it
	 *  
	 * @return 			computers with given name (company or computer name)
	 */
	public List<Computer> getByAllName(Page page, String name) {
		List<Computer> computer = new ArrayList<>();
		String query = EnumQuery.SELECTCOMPUTER
				+ " WHERE computer.name LIKE ?"
				+ " OR company.name LIKE ?";
		if(page!=null)
		    query+=EnumQuery.LIMIT;
		try(Connection conn = Dao.getInstance().getConn();
			PreparedStatement statementComputer = conn.prepareStatement(query)) {

			statementComputer.setString(1,"%"+name+"%");
			statementComputer.setString(2,"%"+name+"%");
			if(page!=null) {
				return getComputersPaginateWithParam(statementComputer,
						statementComputer.getParameterMetaData().getParameterCount(),
						page.getCurrentPage(),
						page.getSizePage());
			}
			ResultSet res = statementComputer.executeQuery();
			while(res.next()) {
				computer.add(computerMapper.getComputer(res));
			}
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
		return computer;
	}
	
	/*
	 * @param page 		paginate request if not null
	 * @param name		searching computer by it
	 *  
	 * @return 			computers with given name
	 */
	public List<Computer> getByName(Page page, String name) {
		List<Computer> computer = new ArrayList<>();
		String query = EnumQuery.SELECTCOMPUTER
				+ " WHERE computer.name LIKE ?";
		if(page!=null)
		    query+=EnumQuery.LIMIT;
		try(Connection conn = Dao.getInstance().getConn();
			PreparedStatement statementComputer = conn.prepareStatement(query)) {

			statementComputer.setString(1,"%"+name+"%");
			if(page!=null) {
				return getComputersPaginateWithParam(statementComputer,
						statementComputer.getParameterMetaData().getParameterCount(),
						page.getCurrentPage(),
						page.getSizePage());
			}
			ResultSet res = statementComputer.executeQuery();
			while(res.next()) {
				computer.add(computerMapper.getComputer(res));
			}
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
		return computer;
	}

	/*
	 * @param page 		paginate request if not null
	 * @param name		searching computer by it
	 *  
	 * @return 			computers with given company name
	 */
	public List<Computer> getByCompany(Page page, String name){
		List<Computer> computers = new ArrayList<Computer>();
		String query = EnumQuery.SELECTCOMPUTER
				+ " WHERE company.name=?";
		if(page!=null)
			query+=EnumQuery.LIMIT;
		try(Connection conn = Dao.getInstance().getConn();
			PreparedStatement statementComputer = conn.prepareStatement(query)) {

			statementComputer.setString(1,name);
			if(page!=null) {
				return getComputersPaginateWithParam(statementComputer,
						statementComputer.getParameterMetaData().getParameterCount(),
						page.getCurrentPage(),
						page.getSizePage());
			}
			ResultSet res = statementComputer.executeQuery();
			while(res.next()) {
				computers.add(computerMapper.getComputer(res));
			}
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
		return computers;
	}

	/*
	 * Check if the request should be paginate or not
	 * 
	 * @param page 		paginate request if not null
	 * @param query		query of the desired SQL request 
	 */
	private List<Computer> getComputers(Page page, String query){
		if(page!=null)
			return getComputersPaginate(query, page.getCurrentPage(), page.getSizePage());
		return getComputers(query);
	}

	/*
	 * @param query		query of the desired SQL request 
	 *  
	 * @return 			computers for the given query
	 */
	private List<Computer> getComputers(String query) {
		List<Computer> computers = new ArrayList<>();
		try(Connection conn = Dao.getInstance().getConn();
			Statement statement = conn.createStatement() ) {

			ResultSet res = statement.executeQuery(query);
			while(res.next()) {
				computers.add(computerMapper.getComputer(res));
			}
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
		return computers;
	}

	/*
	 * @param query			query of the desired SQL request 
	 * @param current		index of the first computer of the page
	 * @param sizeByPage	size of computers to take by page
	 * 
	 * @return 				computers for the given query (paginated)
	 */
	private List<Computer> getComputersPaginate(String query, int current, int sizeByPage) {
		List<Computer> computers = new ArrayList<>();
		query += EnumQuery.LIMIT;
		try(Connection conn = Dao.getInstance().getConn();
			PreparedStatement statementComputer = conn.prepareStatement(query)) {

			statementComputer.setInt(1,(current-1)*sizeByPage);
			statementComputer.setInt(2,sizeByPage);
			ResultSet res = statementComputer.executeQuery();
			while(res.next()) {
				computers.add(computerMapper.getComputer(res));
			}
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
		return computers;
	}

	/*
	 * @param statement		statement with parameters already prepared
	 * @param nbParam		number of desired parameters  
	 * @param current		index of the first computer of the page
	 * @param sizeByPage	size of computers to take by page
	 * 
	 * @return 				computers for the given statement (paginated)
	 */
    private List<Computer> getComputersPaginateWithParam(PreparedStatement statement, int nbParam, int current, int sizeByPage) throws SQLException {
		List<Computer> computers = new ArrayList<>();
		statement.setInt(nbParam-1,(current-1)*sizeByPage);
		statement.setInt(nbParam,sizeByPage);
		ResultSet res = statement.executeQuery();
		while(res.next()) {
			computers.add(computerMapper.getComputer(res));
		}
        return computers;
    }
	
    /*
     * Add the computer to the Database
     * 
     * @param computer		computer to add
     */
	public void addComputer(Computer computer) {
		String query = EnumQuery.INSERTCOMPUTER.toString();
		manageComputer(computer, query);
	}
	
	/*
	 * Update the computer 
	 * 
	 * @param computer		computer to update
	 */
	public void updateComputer(Computer computer) {
		String query = EnumQuery.UPDATECOMPUTER.toString();
		manageComputer(computer, query);
	}
	
	/*
	 * Delete the computer with given ID
	 * 
	 * @param id 			id of the computer to delete
	 */
	public void deleteComputer(int id) {
		String query = EnumQuery.DELETECOMPUTER.toString();
		try(Connection conn = Dao.getInstance().getConn();
			PreparedStatement statementComputer = conn.prepareStatement(query) ) {
			
			statementComputer.setInt(1,id);
			statementComputer.executeUpdate();
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
	}
	
	/*
	 * @return	Number of computers in the database
	 */
	public int getNbComputers() {
		int nb=0;
		String query = EnumQuery.COUNTCOMPUTER.toString(); 
		try(Connection conn = Dao.getInstance().getConn();
			Statement statement = conn.createStatement() ) {

			ResultSet res = statement.executeQuery(query);
			while(res.next()) {
				nb=res.getInt(1);
			}
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
		return nb;
	}
	
	/*
	 * Manage cases where fields are NULL
	 */
	private void manageComputer(Computer computer, String query) {
		if(!computer.getName().isEmpty()) {
			try(Connection conn = Dao.getInstance().getConn();
				PreparedStatement statementComputer = conn.prepareStatement(query)) {
				
				statementComputer.setString(1,computer.getName());
				manageDate(statementComputer, computer);
				manageCompany(statementComputer, computer);
				statementComputer.setInt(5,computer.getId());
				statementComputer.executeUpdate();
			} catch (SQLException e) {
				Logging.error(e.getMessage());
			}
		}
	}
	
	/*
	 * Check if dates are NULL to execute the good function
	 */
	private void manageDate(PreparedStatement statementComputer, Computer computer) throws SQLException {
		if (computer.getIntroduced()!=null) {
			Timestamp intro = Timestamp.valueOf(LocalDateTime.of(computer.getIntroduced(),LocalTime.of(12, 0)));
			statementComputer.setTimestamp(2, intro);
		}
		else
			statementComputer.setNull(2, java.sql.Types.TIMESTAMP);
		if (computer.getDiscontinued()!=null) {	
			Timestamp disco = Timestamp.valueOf(LocalDateTime.of(computer.getDiscontinued(),LocalTime.of(12, 0)));
			statementComputer.setTimestamp(3, disco);
		}
		else
			statementComputer.setNull(3, java.sql.Types.TIMESTAMP);
	}
	
	/*
	 * Check if company is NULL to execute the good function
	 */
	private void manageCompany(PreparedStatement statementComputer, Computer computer) throws SQLException {
		if(computer.getCompany()!=null)
			statementComputer.setInt(4,computer.getCompany().getId());
		else
			statementComputer.setNull(4, java.sql.Types.BIGINT);
	}
}