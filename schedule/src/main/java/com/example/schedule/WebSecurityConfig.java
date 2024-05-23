package com.example.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.schedule.service.UserDetailsServiceImpl;

@Configuration
public class WebSecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new MessageDigestPasswordEncoder("SHA-256");
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth
				.requestMatchers("/login").permitAll()
				.requestMatchers("/schedule/schedules").hasAnyRole("ROLE_USER", "ROLE_CREATOR", "ROLE_EDITOR", "ROLE_ADMIN")
				.requestMatchers("/schedule/groups").hasAnyRole("ROLE_USER", "ROLE_CREATOR", "ROLE_EDITOR", "ROLE_ADMIN")
				.requestMatchers("/schedule/users").hasAnyRole("ROLE_USER", "ROLE_CREATOR", "ROLE_EDITOR", "ROLE_ADMIN")
				.requestMatchers("/schedule/groups/create").hasAnyRole("ROLE_ADMIN", "ROLE_CREATOR")
				.requestMatchers("/schedule/users/create").hasAnyRole("ROLE_ADMIN", "ROLE_CREATOR")
				.requestMatchers("/schedule/groups/edit/**").hasAnyRole("ROLE_ADMIN", "ROLE_EDITOR")
				.requestMatchers("/schedule/groups/delete").hasAnyRole("ROLE_ADMIN").requestMatchers("/schedule/users")
				.hasAnyRole("ROLE_USER", "ROLE_CREATOR", "ROLE_EDITOR", "ROLE_ADMIN").anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/schedule/chedules", true).permitAll()).build();
	}
}

//package com.example.schedule;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
////import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import com.example.schedule.service.UserDetailsServiceImpl;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class WebSecurityConfig {
//
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new UserDetailsServiceImpl();
//	}
//
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new MessageDigestPasswordEncoder("SHA-256");
//	}
//
//	@Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//		authProvider.setUserDetailsService(userDetailsService());
//		authProvider.setPasswordEncoder(passwordEncoder());
//
//		return authProvider;
//	}
//
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//		return httpSecurity.csrf(AbstractHttpConfigurer::disable)
//				.authorizeHttpRequests(auth -> auth.requestMatchers("/login").permitAll()
//						.requestMatchers("/schedule/schedules").hasAnyRole("USER", "CREATOR", "EDITOR", "ADMIN")
//						.requestMatchers("/schedule/groups").hasAnyRole("USER", "CREATOR", "EDITOR", "ADMIN")
//						.requestMatchers("/schedule/groups/create").hasRole("ADMIN").requestMatchers("/schedule/users")
//						.hasAnyRole("USER", "CREATOR", "EDITOR", "ADMIN").anyRequest().authenticated())
//				.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/", true).permitAll()).build();
//	}
//}
