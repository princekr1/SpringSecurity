package com.example.springSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class ProjectSecurityConfig {

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

		/*http.authorizeHttpRequests().anyRequest().authenticated();
		http.formLogin();
		http.httpBasic();
		*/

		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/myAccount","/myBalance","/myCards","/myLoans").authenticated()
				.requestMatchers("/contact","/notices").permitAll());
		http.formLogin(Customizer.withDefaults());
		http.httpBasic(Customizer.withDefaults());

		//Deprecated Approach to authenticate and permit request
		/*http.authorizeHttpRequests()
				.requestMatchers("/myAccount","/myBalance","/myCards","/myLoans").authenticated()
				.requestMatchers("/contact","/notices").permitAll()
				.and().formLogin()
				.and().httpBasic();*/

		//Approach to permit all request
		/*http.authorizeHttpRequests().anyRequest().permitAll().and().formLogin().and().httpBasic();*/

		//Approach to deny all requests
		/*http.authorizeHttpRequests().anyRequest().denyAll().and().formLogin().and().httpBasic();*/

		return (SecurityFilterChain)http.build();
	}

	/*@Bean
	public InMemoryUserDetailsManager userDetailsService() {

		*//**
		 * Approach 1 : Here we are using withDefaultPasswordEncoder while creating the user details
		 *//*
		*//*UserDetails admin= User.withDefaultPasswordEncoder()
				.username("admin").password("12345").authorities("admin").build();

		UserDetails user= User.withDefaultPasswordEncoder()
				.username("user").password("12345").authorities("read").build();
		*//*

		*//**
		 * Approach 2 :  where we use NoOpPasswordEncoder
		 * while creating user details
		 *//*
		UserDetails admin= User.withUsername("admin").password("12345").authorities("admin").build();
		UserDetails user= User.withUsername("user").password("12345").authorities("read").build();

		return new InMemoryUserDetailsManager(admin,user);
	}*/

	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource){
		return new JdbcUserDetailsManager(dataSource);
	}

	/**
	 *
	 * NoOpPasswordEncoder is not recommended for production usage.
	 * Use only for non prod
	 *
	 * @return PasswordEncoder
	 */

	@Bean
	public PasswordEncoder passwordEncoder(){
		return NoOpPasswordEncoder.getInstance();
	}

}