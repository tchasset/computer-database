package com.excilys.tchasset.rest;

import java.util.List;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.tchasset.model.Company;
import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.service.CompanyService;

@RestController
@RequestMapping(value = "/company")
public class CompanyREST {

	private CompanyService companyService;
	
	@Autowired
	public CompanyREST(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@RequestMapping
	public ResponseEntity<List<Company>> getComputers () {
		
		List<Company> companies = companyService.getCompanies();
        if (!companies.isEmpty()) {
            return new ResponseEntity<>(companies, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Computer> deleteComputer (@QueryParam("id") int id) {
		
		companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
