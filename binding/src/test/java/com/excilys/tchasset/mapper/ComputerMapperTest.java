package com.excilys.tchasset.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDate;

import org.junit.Test;

import com.excilys.tchasset.dto.ComputerDTO;
import com.excilys.tchasset.mapper.ComputerMapper;
import com.excilys.tchasset.model.Computer;

public class ComputerMapperTest {
	
	@Test
	public void computerFromDTO_true() {
		Computer computer = new Computer.Builder()	.setId(69)
													.setName("PC1")
													.setIntroduced(LocalDate.of(2000, 1, 1))
													.setDiscontinued(LocalDate.of(2010, 6, 15))
													.build();
		
		ComputerDTO computerDTO = new ComputerDTO.Builder()	.setId("69")
															.setComputerName("PC1")
															.setIntroduced("2000-01-01")
															.setDiscontinued("2010-06-15")
															.build();

		assertEquals(computer, ComputerMapper.fromDTO(computerDTO));
	}
	
	@Test
	public void computerFromDTO_false() {
		Computer computer = new Computer.Builder()	.setId(79)
													.setName("PC12")
													.setIntroduced(LocalDate.of(2000, 1, 2))
													.setDiscontinued(LocalDate.of(2010, 6, 16))
													.build();
		
		ComputerDTO computerDTO = new ComputerDTO.Builder()	.setId("69")
															.setComputerName("PC1")
															.setIntroduced("2000-01-01")
															.setDiscontinued("2010-06-15")
															.build();
		
		assertNotEquals(computer, ComputerMapper.fromDTO(computerDTO));
	}
	
	@Test
	public void computerToDTO_true() {
		Computer computer = new Computer.Builder()	.setId(69)
													.setName("PC1")
													.setIntroduced(LocalDate.of(2000, 1, 1))
													.setDiscontinued(LocalDate.of(2010, 6, 15))
													.build();
		
		ComputerDTO computerDTO = new ComputerDTO.Builder()	.setId("69")
															.setComputerName("PC1")
															.setIntroduced("2000-01-01")
															.setDiscontinued("2010-06-15")
															.build();

		assertEquals(computerDTO, ComputerMapper.toDTO(computer));
	}
	
	@Test
	public void computerToDTO_false() {
		Computer computer = new Computer.Builder()	.setId(79)
													.setName("PC12")
													.setIntroduced(LocalDate.of(2000, 1, 2))
													.setDiscontinued(LocalDate.of(2010, 6, 16))
													.build();
		
		ComputerDTO computerDTO = new ComputerDTO.Builder()	.setId("69")
															.setComputerName("PC1")
															.setIntroduced("2000-01-01")
															.setDiscontinued("2010-06-15")
															.build();
		
		assertNotEquals(computerDTO, ComputerMapper.toDTO(computer));
	}
}
