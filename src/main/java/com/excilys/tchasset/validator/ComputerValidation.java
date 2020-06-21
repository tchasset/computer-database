package com.excilys.tchasset.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.tchasset.dto.ComputerDTO;
import com.excilys.tchasset.log.Logging;

public class ComputerValidation {

	public static List<String> messageError;
	
	public static boolean checkValidity(ComputerDTO computer) {
		messageError = new ArrayList<String>();
		checkId(computer);
		checkName(computer);
		checkIntroduced(computer);
		checkDiscontinued(computer);
		checkCompanyDTO(computer);
		return true;
	}
	
	private static void checkId(ComputerDTO computer) {
		try {
			if (computer.getId().isEmpty()) 
				  throw new Exception("Computer id can't be empty");
		} catch(NullPointerException e) {
			Logging.error("Computer id can't be NULL");
			messageError.add("Computer id can't be NULL");
		} catch (Exception e) {
			Logging.error(e.getMessage());
			messageError.add(e.getMessage());
		}
	}
	
	private static void checkName(ComputerDTO computer) {
		try {
			if (computer.getName().isEmpty()) 
				  throw new Exception("Computer name can't be empty");
		} catch(NullPointerException e) {
			Logging.error("Computer name can't be NULL");
			messageError.add("Computer name can't be NULL");
		} catch (Exception e) {
			Logging.error(e.getMessage());
			messageError.add(e.getMessage());
		}
	}
	
	private static void checkIntroduced(ComputerDTO computer) {
		try {
			LocalDate.parse(computer.getIntroduced());
		} catch (DateTimeParseException e) {
			Logging.error("Introduced date is not in format (YYYY-MM-DD) or is invalid");	
			messageError.add("Introduced date is not in format (YYYY-MM-DD) or is invalid");
		} 
	}
	
	private static void checkDiscontinued(ComputerDTO computer) {
		try {
			LocalDate.parse(computer.getDiscontinued());
		} catch (DateTimeParseException e) {
			Logging.error("Discontinued date is not in format (YYYY-MM-DD) or is invalid");		
			messageError.add("Discontinued date is not in format (YYYY-MM-DD) or is invalid");
		} 
	}
	
	private static void checkCompanyDTO(ComputerDTO computer) {
		try {
			if(computer.getCompanyDTO()!=null) {
				if(computer.getCompanyDTO().getId().isEmpty() || computer.getCompanyDTO().getName().isEmpty()) {
					throw new Exception("Company id and name can't be empty if a company is declared for a computer");
				}
			}
		} catch(NullPointerException e) {
			Logging.error("Company id and name can't be NULL if a company is declared for a computer");
			messageError.add("Company id and name can't be NULL if a company is declared for a computer");
		}catch (Exception e) {
			Logging.error(e.getMessage());
			messageError.add(e.getMessage());
		}
	}
}
