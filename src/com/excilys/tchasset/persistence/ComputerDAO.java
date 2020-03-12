package com.excilys.tchasset.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		String query = "SELECT computer.id,computer.name,introduced,discontinued,company_id FROM computer LEFT JOIN company ON company_id;";
		try (Statement statement = Dao.getInstance().getConn().createStatement()) {
			ResultSet res = statement.executeQuery(query);
			while(res.next()) {
				computers.add(ComputerMapper.getInstance().getComputer(res));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}
	
	public List<Computer> getComputersPaginate(int current, int sizeByPage) {
		List<Computer> computers = new ArrayList<Computer>();
		String query = "SELECT computer.id,computer.name,introduced,discontinued,company_id FROM computer LEFT JOIN company ON company_id LIMIT ?, ?;";
		try (PreparedStatement statementComputer = Dao.getInstance().getConn().prepareStatement(query)) {
			statementComputer.setInt(1,current);
			statementComputer.setInt(2,sizeByPage);
			ResultSet res = statementComputer.executeQuery();
			while(res.next()) {
				computers.add(ComputerMapper.getInstance().getComputer(res));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}
	
	public Optional<Computer> getById(int id) {
		Optional<Computer> computer = Optional.empty();
		String query = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id=?;";
		try (PreparedStatement statementComputer = Dao.getInstance().getConn().prepareStatement(query)) {
			statementComputer.setInt(1,id);
			ResultSet res = statementComputer.executeQuery();
			while(res.next()) {
				computer = Optional.of(ComputerMapper.getInstance().getComputer(res));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computer;
	}
	
	public void addComputer(Computer computer) {
		String query = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?,?,?,?);";
		ComputerMapper.getInstance().manageComputer(computer, query);
	}
	
	public void deleteComputer(int id) {
		String query = "DELETE FROM computer where id=?;";
		try (PreparedStatement statementComputer = Dao.getInstance().getConn().prepareStatement(query)) {
			statementComputer.setInt(1,id);
			statementComputer.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateComputer(Computer computer) {
		String query = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id="+computer.getId()+";";
		ComputerMapper.getInstance().manageComputer(computer, query);
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
			e.printStackTrace();
		}
		return nb;
	}
}
