package com.zpi2016;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.zpi2016.model"})
@EnableJpaRepositories(basePackages = {"com.zpi2016.repository"})
@SpringBootApplication
@EnableTransactionManagement
public class Application{

	/**
	 *	Starts the whole Springboot application
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}


}
