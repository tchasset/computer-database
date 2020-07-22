package com.excilys.tchasset.rest;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.tchasset.dto.ComputerDTO;
import com.excilys.tchasset.mapper.ComputerMapper;
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

	@GetMapping
	public ResponseEntity<List<Computer>> getComputers (@QueryParam("page") int page) {

		pages.setCurrentPage(page);
		List<Computer> computers = computerService.getAllComputers(pages);
		if (!computers.isEmpty()) {
			return new ResponseEntity<>(computers, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<Computer> getComputerById (@PathVariable("id") int id) {

		Optional<Computer> computer = computerService.getById(id);
		if (computer.isPresent()) {
			return new ResponseEntity<>(computer.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(path = "/search")
	public ResponseEntity<List<Computer>> getComputerByName (@QueryParam("name") String name, @QueryParam("page") int page) {

		pages.setCurrentPage(page);
		List<Computer> computers = computerService.getByName(pages, name);
		if (!computers.isEmpty()) {
			return new ResponseEntity<>(computers, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<Computer> addComputer (@RequestBody ComputerDTO computerDTO) {

		computerService.addComputer(ComputerMapper.fromDTO(computerDTO));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<Computer> editComputer (@RequestBody ComputerDTO computerDTO) {

		computerService.updateComputer(ComputerMapper.fromDTO(computerDTO));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Computer> deleteComputer (@PathVariable("id") int id) {

		computerService.deleteComputer(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
