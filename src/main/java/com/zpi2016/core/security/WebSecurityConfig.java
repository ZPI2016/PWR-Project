package com.zpi2016.core.security;

import com.zpi2016.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by filip on 24.04.2016.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService userService;

	@Value("${com.zpi2016.m33tme.salt}")
	private String salt;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.formLogin().loginPage("/").permitAll()
				.and().logout()	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.and().authorizeRequests()
					.antMatchers("/admin/**").access("hasRole('ADMIN')").anyRequest().authenticated()
					.antMatchers("/users/**").access("hasAnyRole('ADMIN','USER')").anyRequest().authenticated()
					.anyRequest().permitAll()
				.and().csrf().disable().addFilterBefore(new StatelessCSRFFilter(), CsrfFilter.class);
	}

	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(new StandardPasswordEncoder(salt));
	}

	public static class StatelessCSRFFilter extends OncePerRequestFilter {

		private static final String CSRF_TOKEN = "CSRF-TOKEN";
		private static final String X_CSRF_TOKEN = "X-CSRF-TOKEN";
		private final RequestMatcher requireCsrfProtectionMatcher = new DefaultRequiresCsrfMatcher();
		private final AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();

		@Override
		protected void doFilterInternal(HttpServletRequest request,
		                                HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			if (processTokenValidation(request, response)) return;
			filterChain.doFilter(request, response);
		}

		private boolean processTokenValidation(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
			if (requireCsrfProtectionMatcher.matches(request)) {
				final String csrfTokenValue = request.getHeader(X_CSRF_TOKEN);
				final Cookie[] cookies = request.getCookies();

				String csrfCookieValue = getCsrfCookieValue(cookies);
				if (handleBadToken(request, response, csrfTokenValue, csrfCookieValue)) return true;
			}
			return false;
		}

		private String getCsrfCookieValue(final Cookie[] cookies) {
			String csrfCookieValue = null;
			if (cookies != null)
				for (Cookie cookie : cookies)
					if (cookie.getName().equals(CSRF_TOKEN))
						csrfCookieValue = cookie.getValue();
			return csrfCookieValue;
		}

		private boolean handleBadToken(final HttpServletRequest request, final HttpServletResponse response, final String csrfTokenValue, final String csrfCookieValue) throws IOException, ServletException {
			if (csrfTokenValue == null || !csrfTokenValue.equals(csrfCookieValue)) {
				accessDeniedHandler.handle(request, response, new AccessDeniedException(
						"Missing or non-matching CSRF-token"));
				return true;
			}
			return false;
		}

		public static final class DefaultRequiresCsrfMatcher implements RequestMatcher {
			private final Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

			@Override
			public boolean matches(HttpServletRequest request) {
				return !allowedMethods.matcher(request.getMethod()).matches();
			}
		}
	}
}
