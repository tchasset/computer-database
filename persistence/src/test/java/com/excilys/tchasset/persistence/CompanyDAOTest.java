package com.excilys.tchasset.persistence;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.tchasset.config.PersistenceConfig;
import com.excilys.tchasset.model.Company;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( classes = { PersistenceConfig.class })
@Transactional
public class CompanyDAOTest {

	@Resource private CompanyDAO companyDAO;
	@Resource private ComputerDAO computerDAO;
	
	@Test
    public void getCompanies() {
        int nbComputers = companyDAO.getCompanies().size();
        assertEquals(10, nbComputers);
    }
	
	@Test
	public void getById() {
		Optional<Company> company = companyDAO.getById(1);
		assertEquals(company.get(), new Company.Builder("Apple Inc.").setId(1).build());
	}
	
	@Test
	public void getByName() {
		Optional<Company> company = companyDAO.getByName("Apple Inc.");
		assertEquals(company.get(), new Company.Builder("Apple Inc.").setId(1).build());
	}
	
	@Test
	public void deleteCompany() {
		companyDAO.deleteCompany(1);
		assertEquals(9, companyDAO.getCompanies().size());
		assertEquals(17, computerDAO.getNbComputers());
	}
	
}
