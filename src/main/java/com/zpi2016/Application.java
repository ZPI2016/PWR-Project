package com.zpi2016;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring boot starting application class
 * It's the main configuration class at the same time
 *
 * Annotations explained:
 * > EnableTransactionManagement - tells Spring to perform annotation driven transactions
 */

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
public class Application{

	/**
	 *	Starts the whole Spring boot application
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
 
}
