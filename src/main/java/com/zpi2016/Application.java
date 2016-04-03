package com.zpi2016;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
@EnableTransactionManagement(proxyTargetClass = true)
public class Application{

	/**
	 *	Starts the whole Springboot application
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * The SecurityConfiguration class enables route access configuration
	 */
	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.httpBasic().and()
					.authorizeRequests()
					.antMatchers("/index.html", "/home.html", "/login.html", "/").permitAll().anyRequest()
					.authenticated().and()
					.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
					.csrf().csrfTokenRepository(csrfTokenRepository());;
		}

		private CsrfTokenRepository csrfTokenRepository() {
			HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
			repository.setHeaderName("X-XSRF-TOKEN");
			return repository;
		}
	}

	public class CsrfHeaderFilter extends OncePerRequestFilter {
		@Override
		protected void doFilterInternal(HttpServletRequest request,
										HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
					.getName());
			if (csrf != null) {
				Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
				String token = csrf.getToken();
				if (cookie==null || token!=null && !token.equals(cookie.getValue())) {
					cookie = new Cookie("XSRF-TOKEN", token);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
			filterChain.doFilter(request, response);
		}
	}
}