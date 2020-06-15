package com.excilys.tchasset.persistence;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.tchasset.log.Logging;
import com.excilys.tchasset.mapper.ComputerMapper;
import com.excilys.tchasset.model.Computer;

public class ComputerDAO {

	private static ComputerDAO instance;
	
	public static final ComputerDAO getInstance() {
		if (ComputerDAO.instance == null) {
			synchronized(ComputerDAO.class) {
				if (ComputerDAO.instance == null) {
					ComputerDAO.instance = new ComputerDAO();
	            }
	        }
		}
	    return ComputerDAO.instance;
    }
	
	public List<Computer> getComputers(){
		List<Computer> computers = new ArrayList<Computer>();
		String query = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name "
					 + "FROM computer "
					 + "LEFT JOIN company "
					 + "ON computer.company_id = company.id;";
		try (Statement statement = Dao.getInstance().getConn().createStatement()) {
			ResultSet res = statement.executeQuery(query);
			while(res.next()) {
				computers.add(ComputerMapper.getInstance().getComputer(res));
			}
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
		return computers;
	}
	
	public List<Computer> getComputersOrderByComputer(String order){
		List<Computer> computers = new ArrayList<Computer>();
		String query = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name "
					 + "FROM computer "
					 + "LEFT JOIN company "
					 + "ON computer.company_id = company.id "
					 + "ORDER BY computer.name "+order+";";
		try (Statement statement = Dao.getInstance().getConn().createStatement()) {
			ResultSet res = statement.executeQuery(query);
			while(res.next()) {
				computers.add(ComputerMapper.getInstance().getComputer(res));
			}
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
		return computers;
	}
	
	public List<Computer> getComputersOrderByCompany(String order){
		List<Computer> computers = new ArrayList<Computer>();
		String query = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name "
					 + "FROM computer "
					 + "LEFT JOIN company "
					 + "ON computer.company_id = company.id "
					 + "ORDER BY company.name IS NULL, company.name "+order+";";
		try (Statement statement = Dao.getInstance().getConn().createStatement()) {
			ResultSet res = statement.executeQuery(query);
			while(res.next()) {
				computers.add(ComputerMapper.getInstance().getComputer(res));
			}
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
		return computers;
	}
	
	public List<Computer> getComputersPaginate(int current, int sizeByPage) {
		List<Computer> computers = new ArrayList<Computer>();
		String query = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name "
					 + "FROM computer "
					 + "LEFT JOIN company "
					 + "ON computer.company_id = company.id "
					 + "LIMIT ?, ?;";
		try (PreparedStatement statementComputer = Dao.getInstance().getConn().prepareStatement(query)) {
			statementComputer.setInt(1,current);
			statementComputer.setInt(2,sizeByPage);
			ResultSet res = statementComputer.executeQuery();
			while(res.next()) {
				computers.add(ComputerMapper.getInstance().getComputer(res));
			}
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
		return computers;
	}
	
	public Optional<Computer> getById(int id) {
		Optional<Computer> computer = Optional.empty();
		String query = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
					 + "FROM computer "
					 + "LEFT JOIN company "
					 + "ON computer.company_id = company.id "
					 + "WHERE computer.id=?;";
		try (PreparedStatement statementComputer = Dao.getInstance().getConn().prepareStatement(query)) {
			statementComputer.setInt(1,id);
			ResultSet res = statementComputer.executeQuery();
			while(res.next()) {
				computer = Optional.of(ComputerMapper.getInstance().getComputer(res));
			}
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
		return computer;
	}
	
	public Optional<Computer> getByName(String name) {
		Optional<Computer> computer = Optional.empty();
		String query = "SELECT computer.id, computer.name, introduced, discontinued, company.id, company.name "
					 + "FROM computer "
					 + "LEFT JOIN company "
					 + "ON computer.company_id = company.id "
					 + "WHERE computer.name=?;";
		try (PreparedStatement statementComputer = Dao.getInstance().getConn().prepareStatement(query)) {
			statementComputer.setString(1,name);
			ResultSet res = statementComputer.executeQuery();
			while(res.next()) {
				computer = Optional.of(ComputerMapper.getInstance().getComputer(res));
			}
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
		return computer;
	}
	
	public List<Computer> getByCompany(String name){
		List<Computer> computers = new ArrayList<Computer>();
		String query = "SELECT computer.id,computer.name,introduced,discontinued,company.id,company.name "
					 + "FROM computer "
					 + "LEFT JOIN company "
					 + "ON computer.company_id = company.id "
					 + "WHERE company.name=?;";
		try (PreparedStatement statementComputer = Dao.getInstance().getConn().prepareStatement(query)) {
			statementComputer.setString(1,name);
			ResultSet res = statementComputer.executeQuery();
			while(res.next()) {
				computers.add(ComputerMapper.getInstance().getComputer(res));
			}
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
		return computers;
	}
	
	public void addComputer(Computer computer) {
		String query = "INSERT INTO computer (name, introduced, discontinued, company_id, id) VALUES (?,?,?,?,?);";
		manageComputer(computer, query);
	}
	
	
	public void updateComputer(Computer computer) {
		String query = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
		manageComputer(computer, query);
	}
	
	/*
	 * Gère les cas, ou les champs, lors de l'ajout ou la modification d'un ordinateur
	 * sont à null puis execute la requete SQL
	 */
	private void manageComputer(Computer computer, String query) {
		if(!computer.getName().isEmpty()) {
			try (PreparedStatement statementComputer = Dao.getInstance().getConn().prepareStatement(query)) {
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
	 * Verifie si les dates sont null ou non pour effectuer l'ajout/modif en base
	 */
	private void manageDate(PreparedStatement statementComputer, Computer computer) throws SQLException {
		if (computer.getIntroduced()!=null)
			statementComputer.setDate(2,Date.valueOf(computer.getIntroduced()));
		else
			statementComputer.setNull(2, java.sql.Types.TIMESTAMP);
		if (computer.getDiscontinued()!=null)
			statementComputer.setDate(3,Date.valueOf(computer.getDiscontinued()));
		else
			statementComputer.setNull(3, java.sql.Types.TIMESTAMP);
	}
	
	/*
	 * Verifie si la compagnie est null ou non pour effectuer l'ajout/modif en base
	 */
	private void manageCompany(PreparedStatement statementComputer, Computer computer) throws SQLException {
		if(computer.getCompany()!=null)
			statementComputer.setInt(4,computer.getCompany().getId());
		else
			statementComputer.setNull(4, java.sql.Types.BIGINT);
	}
	
	public void deleteComputer(int id) {
		String query = "DELETE FROM computer where id=?;";
		try (PreparedStatement statementComputer = Dao.getInstance().getConn().prepareStatement(query)) {
			statementComputer.setInt(1,id);
			statementComputer.executeUpdate();
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
	}
	
	public int getNbComputers() {
		int nb=0;
		String query = "SELECT COUNT(*) as 'Computers' FROM computer;";
		try (Statement statement = Dao.getInstance().getConn().createStatement()) {
			ResultSet res = statement.executeQuery(query);
			while(res.next()) {
				nb=res.getInt(1);
			}
		} catch (SQLException e) {
			Logging.error(e.getMessage());
		}
		return nb;
	}
}
