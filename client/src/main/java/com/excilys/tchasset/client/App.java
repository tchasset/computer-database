package com.excilys.tchasset.client;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.tchasset.config.PersistenceConfig;

public class App {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
		 context.getBean(Client.class).affiche();
		 context.close();
	}
}