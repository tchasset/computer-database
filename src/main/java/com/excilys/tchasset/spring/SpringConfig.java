package com.excilys.tchasset.spring;

import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.excilys.tchasset.persistence.HikariCP;

@Configuration
@ComponentScan(basePackages = "com.excilys.tchasset")
public class SpringConfig extends AbstractContextLoaderInitializer{
	
	@Bean
	public DataSource dataSource() {
		return HikariCP.getDatasource();
	}
	
	@Bean
	JdbcTemplate jdbcTemplate(DataSource datasource) {
		return new JdbcTemplate(datasource);
	}

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(SpringConfig.class);
		return context;
	}
}
