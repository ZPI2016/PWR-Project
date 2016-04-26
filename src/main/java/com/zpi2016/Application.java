package com.zpi2016;

import com.zpi2016.location.domain.Location;
import com.zpi2016.user.domain.User;
import com.zpi2016.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.Date;

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

	@Autowired
	UserService userService;

	/**
	 *	Starts the whole Spring boot application
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void init() {
		userService.save(
				new User.Builder("nazwa", "haslo", "filip@ja.com",
						new Date(), new Location(10.0f, 5.0f), 15.0f)
						.withRole("ROLE_ADMIN").build());
		userService.save(
				new User.Builder("nazwa2", "haslo2", "filip2@ja.com",
						new Date(), new Location(10.0f, 5.0f), 15.0f).build());
	}

}
