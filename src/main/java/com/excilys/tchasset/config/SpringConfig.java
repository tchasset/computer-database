package com.excilys.tchasset.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import com.excilys.tchasset.persistence.HikariCP;

@Configuration
@ComponentScan(basePackages = "com.excilys.tchasset")
public class SpringConfig {
	
	@Bean
	public DataSource dataSource() {
		return HikariCP.getDatasource();
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
