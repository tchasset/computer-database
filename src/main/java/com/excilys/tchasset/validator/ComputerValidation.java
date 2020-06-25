package com.excilys.tchasset.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.tchasset.dto.ComputerDTO;
import com.excilys.tchasset.log.Logging;

public class ComputerValidation {

	public static List<String> messageError;
	
	public static void checkValidity(ComputerDTO computer) {
		messageError = new ArrayList<String>();
		checkId(computer);
		checkName(computer);
		checkIntroduced(computer);
		checkDiscontinued(computer);
		checkDate(computer);
		checkCompanyDTO(computer);
	}
	
	private static void checkId(ComputerDTO computer) {
		try {
			if (computer.getId().isEmpty()) 
				  throw new Exception("Computer id can't be empty");
		} catch(NullPointerException e) {
			Logging.error("Computer id can't be NULL", ComputerValidation.class);
			messageError.add("Computer id can't be NULL");
		} catch (Exception e) {
			Logging.error(e.getMessage(), ComputerValidation.class);
			messageError.add(e.getMessage());
		}
	}
	
	private static void checkName(ComputerDTO computer) {
		try {
			if (computer.getName().isEmpty()) 
				  throw new Exception("Computer name can't be empty");
		} catch(NullPointerException e) {
			Logging.error("Computer name can't be NULL", ComputerValidation.class);
			messageError.add("Computer name can't be NULL");
		} catch (Exception e) {
			Logging.error(e.getMessage(), ComputerValidation.class);
			messageError.add(e.getMessage());
		}
	}
	
	private static void checkIntroduced(ComputerDTO computer) {
		try {
			if (computer.getIntroduced()!=null && !computer.getIntroduced().isEmpty())
				LocalDate.parse(computer.getIntroduced());
		} catch (DateTimeParseException e) {
			Logging.error("Introduced date is not in format (YYYY-MM-DD) or is invalid", ComputerValidation.class);	
			messageError.add("Introduced date is not in format (YYYY-MM-DD) or is invalid");
		} 
	}
	
	private static void checkDiscontinued(ComputerDTO computer) {
		try {
			if (computer.getDiscontinued()!=null && !computer.getDiscontinued().isEmpty())
				LocalDate.parse(computer.getDiscontinued());
		} catch (DateTimeParseException e) {
			Logging.error("Discontinued date is not in format (YYYY-MM-DD) or is invalid", ComputerValidation.class);		
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
			Logging.error("Company id and name can't be NULL if a company is declared for a computer", ComputerValidation.class);
			messageError.add("Company id and name can't be NULL if a company is declared for a computer");
		}catch (Exception e) {
			Logging.error(e.getMessage(), ComputerValidation.class);
			messageError.add(e.getMessage());
		}
	}
	
	private static void checkDate(ComputerDTO computer) {
		String intro = computer.getIntroduced().isEmpty() ? null : computer.getIntroduced(),
			   disco = computer.getDiscontinued().isEmpty() ? null : computer.getDiscontinued();
				
		if(intro!=null && disco!=null)
			if(LocalDate.parse(disco).isBefore(LocalDate.parse(intro)))
				messageError.add("Discontinued date can't be before introduced date");
		
		if(	intro!=null && 
			(LocalDate.parse(intro).isBefore(LocalDate.of(1970,1,1)) || LocalDate.parse(intro).isAfter(LocalDate.of(2038,1,18))) ){
			messageError.add("Introduced date can't be before 01/01/1970 or afeter 18/01/2038");
		}
		
		if(	disco!=null && 
			(LocalDate.parse(disco).isBefore(LocalDate.of(1970,1,1)) || LocalDate.parse(disco).isAfter(LocalDate.of(2038,1,18))) ) {
			messageError.add("Discontinued date can't be before 01/01/1970 or afeter 18/01/2038");
		}
	}
}
