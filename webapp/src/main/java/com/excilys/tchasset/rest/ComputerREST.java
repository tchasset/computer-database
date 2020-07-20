package com.excilys.tchasset.rest;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.tchasset.model.Computer;
import com.excilys.tchasset.model.Page;
import com.excilys.tchasset.service.ComputerService;

@RestController
@RequestMapping(value = "/computers")
public class ComputerREST {

	private ComputerService computerService;
	private Page pages;

	@Autowired
	public ComputerREST(ComputerService computerService, Page pages) {
		this.computerService = computerService;
		this.pages = pages;
	}

	@RequestMapping
	public ResponseEntity<List<Computer>> getComputers (@QueryParam("page") int page) {

		pages.setCurrentPage(page);
		List<Computer> computers = computerService.getAllComputers(pages);
		if (!computers.isEmpty()) {
			return new ResponseEntity<>(computers, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/{id}")
	public ResponseEntity<Computer> getComputerById (@PathVariable("id") int id) {

		Optional<Computer> computer = computerService.getById(id);
		if (computer.isPresent()) {
			return new ResponseEntity<>(computer.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<Computer> addComputer (@RequestBody Computer computer) {

		computerService.addComputer(computer);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public ResponseEntity<Computer> editComputer (@RequestBody Computer computer) {

		computerService.updateComputer(computer);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity<Computer> deleteComputer (@QueryParam("id") int id) {

		computerService.deleteComputer(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
