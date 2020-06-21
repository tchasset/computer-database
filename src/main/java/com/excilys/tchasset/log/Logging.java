package com.excilys.tchasset.log;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {
	
	public static Logger LOGGER = LoggerFactory.getLogger(Logging.class);

	public static void writeFile(String message) {
		try (FileWriter file = new FileWriter("src/main/resources/file.log",true)) {
			file.write(Calendar.getInstance().getTime()+" : "+message+"\n");
		} catch (IOException e) {
			LOGGER.error("Error with file : " + e.getMessage());
		}
	}
	
	public static void error(String message) {
		LOGGER.error(message);
		writeFile(message);
	}
}
