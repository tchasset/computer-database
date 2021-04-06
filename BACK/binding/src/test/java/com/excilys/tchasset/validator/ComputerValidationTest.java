package com.excilys.tchasset.validator;

import com.excilys.tchasset.dto.CompanyDTO;
import com.excilys.tchasset.dto.ComputerDTO;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class ComputerValidationTest {

    private CompanyDTO companyDTO = new CompanyDTO.Builder("A")
            .setId("1").build();
    private ComputerDTO computerDTO = new ComputerDTO.Builder("B")
            .setId("1")
            .setIntroduced("2000-01-01")
            .setDiscontinued("2000-01-02")
            .setCompanyDTO(companyDTO).build();

    @Test
    public void checkValidity() {
        assertTrue(ComputerValidation.checkValidity(computerDTO));
    }

    @Test
    public void checkValidityId() {
        computerDTO.setId(null);
        ComputerValidation.checkValidity(computerDTO);
        assertEquals("Computer id can't be NULL", ComputerValidation.messageError.get(0));

        computerDTO.setId("");
        ComputerValidation.checkValidity(computerDTO);
        assertEquals("Computer id can't be empty", ComputerValidation.messageError.get(0));
    }

    @Test
    public void checkValidityName() {
        computerDTO.setName("");
        ComputerValidation.checkValidity(computerDTO);
        assertEquals("Computer name can't be empty", ComputerValidation.messageError.get(0));

        computerDTO.setName("fè_&'_hé");
        ComputerValidation.checkValidity(computerDTO);
        assertEquals("Computer name contains invalid character", ComputerValidation.messageError.get(0));

        computerDTO.setName(null);
        ComputerValidation.checkValidity(computerDTO);
        assertEquals("Computer name can't be NULL", ComputerValidation.messageError.get(0));
    }

//    @Test
//    public void checkValidityIntroduced() {
//        computerDTO.setIntroduced("0");
//        ComputerValidation.checkValidity(computerDTO);
//        assertEquals("Introduced date is not in format (YYYY-MM-DD) or is invalid", ComputerValidation.messageError.get(0));
//    }
}