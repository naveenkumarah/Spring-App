package com.naveen.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class MultiHttpSecurityConfig {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private Md5PasswordEncoder md5PasswordEncoder;
	
	@Bean(name="md5PasswordEncoder")
	public Md5PasswordEncoder md5PasswordEncoder(){
		return new Md5PasswordEncoder();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	  auth.jdbcAuthentication().passwordEncoder(md5PasswordEncoder).dataSource(dataSource);
	}

	@Configuration
	@Order(1)                                                        
	public static class ServiceWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		protected void configure(HttpSecurity http) throws Exception {
			http
				.antMatcher("/services/**")                               
				.authorizeRequests().anyRequest().authenticated()
				.and().httpBasic()
				.and().csrf().disable();
		}
	}

	@Configuration                                                   
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests().anyRequest().authenticated()
				.and().formLogin()
				.and().csrf().disable();
		}
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.debug(false);
			super.configure(web);
		}
	}
	
}
