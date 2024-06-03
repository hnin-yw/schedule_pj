package com.example.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationProvider;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import com.example.schedule.service.CustomOAuth2UserServiceImpl;
import com.example.schedule.service.UserDetailsServiceImpl;
import static org.springframework.security.config.Customizer.withDefaults;


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
	
//	@Bean
//	public OAuth2UserService oauth2UserService() {
//		return new CustomOAuth2UserServiceImpl();
//	}
    @Autowired
    private CustomOAuth2UserServiceImpl oauth2UserService;
    
    //@Autowired
    //private CustomOAuth2UserServiceImpl oauth2UserService;

    @Bean
    public OAuthLoginSuccessHandler oauthLoginSuccessHandler() {
        return new OAuthLoginSuccessHandler();
    }
    
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}


	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				auth -> auth
				.requestMatchers("/WEB-INF/jsp/**","/oauth/**").permitAll()
				.anyRequest().authenticated()
				)
				.formLogin(login -> login
		                .loginPage("/login")
		                .loginProcessingUrl("/doLogin")
		                .failureUrl("/login?error=true")
		                .defaultSuccessUrl("/schedules").permitAll()
		        )

	            .oauth2Login(oauth2Login ->
	                oauth2Login
	                	.loginPage("/login")
	                    .userInfoEndpoint()
	                    .userService(oauth2UserService)
	                    .and()
	                    .successHandler(oauthLoginSuccessHandler())
	            )
		        .logout(logout -> logout.permitAll());

		return http.build();
	}
}

//	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//		return httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth
//				.requestMatchers("/login").permitAll()
//				.requestMatchers("/schedule/schedules").hasAnyRole("ROLE_USER", "ROLE_CREATOR", "ROLE_EDITOR", "ROLE_ADMIN")
//				.requestMatchers("/schedule/groups").hasAnyRole("ROLE_USER", "ROLE_CREATOR", "ROLE_EDITOR", "ROLE_ADMIN")
//				.requestMatchers("/schedule/users").hasAnyRole("ROLE_USER", "ROLE_CREATOR", "ROLE_EDITOR", "ROLE_ADMIN")
//				.requestMatchers("/schedule/groups/create").hasAnyRole("ROLE_ADMIN", "ROLE_CREATOR")
//				.requestMatchers("/schedule/users/create").hasAnyRole("ROLE_ADMIN", "ROLE_CREATOR")
//				.requestMatchers("/schedule/groups/edit/**").hasAnyRole("ROLE_ADMIN", "ROLE_EDITOR")
//				.requestMatchers("/schedule/groups/delete").hasAnyRole("ROLE_ADMIN").requestMatchers("/schedule/users")
//				.hasAnyRole("ROLE_USER", "ROLE_CREATOR", "ROLE_EDITOR", "ROLE_ADMIN").anyRequest().authenticated())
//				.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/schedule/sschedules", true).permitAll()).build();
//	}
