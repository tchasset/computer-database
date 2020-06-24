package com.excilys.tchasset.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.excilys.tchasset")
public class SpringConfig{
	
	private static ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

	public static ApplicationContext getContext() {
		return context;
	}
}
