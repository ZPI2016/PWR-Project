package com.zpi2016;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring boot starting application class
 * It's the main configuration class at the same time
 *
 * Annotations explained:
 * > EntityScan - tells Hibernate where to look for Entities
 * > EnableJpaRepositories - enables auto configuration of Spring Data JPA (the 'interface related magic')
 * > EnableTransactionManagement - tells Spring to perform annotation driven transactions
 */

@EntityScan(basePackages = {"com.zpi2016.model"})
@EnableJpaRepositories(basePackages = {"com.zpi2016.repository"})
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
public class Application{

	/**
	 *	Starts the whole Springboot application
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
