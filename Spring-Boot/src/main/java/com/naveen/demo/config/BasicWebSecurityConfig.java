package com.naveen.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration
@Order(1)
public class BasicWebSecurityConfig extends WebSecurityConfigurerAdapter{
	/*
	 * Web service security using http basic authentication.
	 * 
	 */
	protected void configure(HttpSecurity http) throws Exception {
		http
			.antMatcher("/services/**")                               
			.authorizeRequests().anyRequest().authenticated()
			.and().httpBasic()
			.and().csrf().disable();
	}

}
