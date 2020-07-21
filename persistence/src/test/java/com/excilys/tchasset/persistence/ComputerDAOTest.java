package com.excilys.tchasset.persistence;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.tchasset.config.PersistenceConfig;
import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.model.Page;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( classes = { PersistenceConfig.class })
@Transactional
public class ComputerDAOTest {

	@Resource private ComputerDAO computerDAO;
	@Autowired private Page page;

	@Test
	public void getComputers() {
		int nb = computerDAO.getAllComputers(null).size();
		assertEquals(30, nb);
	}

	@Test
	public void getAllComputers() {
		int nb = computerDAO.getAllComputers(page).size();
		assertEquals(10, nb);
	}

	@Test
	public void getById() {
		Computer computer =  new Computer.Builder().setId(1)
				.setName("MacBook Pro 15.4 inch")
				.setCompany(new Company.Builder().setId(1).setName("Apple Inc.").build())
				.build();
		assertEquals(computer, computerDAO.getById(1).get());
	}

	@Test
	public void getByAllByName() {
		int nb = computerDAO.getByAllName(page,"CM").size();
		assertEquals(5, nb);
	}

	@Test
	public void getByName() {
		int nb = computerDAO.getByName(page,"CM").size();
		assertEquals(5, nb);
	}

	@Test
	public void getByCompany() {
		int nb = computerDAO.getByCompany(page,"net").size();
		assertEquals(1, nb);
	}

	@Test
	public void getNbBySearch() {
		int nb = computerDAO.getNbBySearch("ap");
		assertEquals(13, nb);
	}

	@Test
	public void getNbComputer() {
		int nb = computerDAO.getNbComputers();
		assertEquals(30, nb);
	}

	@Test
	public void addComputer() {
		Computer computer = new Computer.Builder().setName("test").build();
		assertEquals(30, computerDAO.getNbComputers());
		computerDAO.addComputer(computer);
		assertEquals(31, computerDAO.getNbComputers());
		assertEquals(computer, computerDAO.getAllComputers(null).get(30));
	}

	@Test
	public void updateComputer() {
		Computer computerNotEdited =  new Computer.Builder().setId(1)
				.setName("MacBook Pro 15.4 inch")
				.setCompany(new Company.Builder().setId(1).setName("Apple Inc.").build())
				.build();
		Computer computerEdited = new Computer.Builder().setId(1).setName("test").build();
		assertEquals(computerNotEdited, computerDAO.getAllComputers(null).get(0));
		computerDAO.updateComputer(computerEdited);
		assertEquals(computerEdited, computerDAO.getAllComputers(null).get(0));
	}

	@Test
	public void deleteCompany() {
		Computer computerNumberOneBeforeDelete =  new Computer.Builder().setId(1)
				.setName("MacBook Pro 15.4 inch")
				.setCompany(new Company.Builder().setId(1).setName("Apple Inc.").build())
				.build();
		Computer computerNumberOneAfterDelete =  new Computer.Builder().setId(2)
				.setName("CM-2a")
				.setCompany(new Company.Builder().setId(2).setName("Thinking Machines").build())
				.build();
		
		assertEquals(computerNumberOneBeforeDelete, computerDAO.getAllComputers(null).get(0));
		
		computerDAO.deleteComputer(1);
		
		assertEquals(29, computerDAO.getNbComputers());
		assertEquals(computerNumberOneAfterDelete, computerDAO.getAllComputers(null).get(0));
	}
}
