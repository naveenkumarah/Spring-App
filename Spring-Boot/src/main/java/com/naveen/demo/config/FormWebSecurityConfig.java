package com.naveen.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration
@Order(6)
public class FormWebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private Md5PasswordEncoder md5PasswordEncoder;
	
	@Bean(name="md5PasswordEncoder")
	public Md5PasswordEncoder md5PasswordEncoder(){
		return new Md5PasswordEncoder();
	}
	
	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService customUserDetailsService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailsService).passwordEncoder(md5PasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
				.antMatchers("/js/**","/libs/**","/login**","/signin**").permitAll()
				.anyRequest().authenticated()
			.and().formLogin().
					loginPage("/signin")
					.loginProcessingUrl("/sign-in-process.html")
					.failureUrl("/signin?error")
					.usernameParameter("username")
					.passwordParameter("password")
			.and().logout().
					logoutSuccessUrl("/signin?logout")
			.and().
				csrf().disable();
	}
	
}
