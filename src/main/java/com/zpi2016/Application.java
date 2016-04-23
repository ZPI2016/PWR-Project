package com.zpi2016;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Springboot starting application class
 * It's the main configuration class at the same time
 *
 * Annotations explained:
 * > Configuration - tells Spring that it is configuration class
 * > EnableAutoConfiguration - tells Springboot to perform automatic configuration
 * > EntityScan - tells Hibernate where to look for Entities
 * > EnableJpaRepositories - enables auto configuration of Spring Data JPA (the 'interface related magic')
 * > EnableTransactionManagement - tells Spring to perform annotation driven transactions
 */

@SpringBootApplication
public class Application{

	/**
	 *	Starts the whole Springboot application
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}