package com.naveen.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.naveen.demo.security.CustomAuthenticationProvider;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration
@Order(6)
public class FormWebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
    private CustomAuthenticationProvider authProvider;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		 auth.authenticationProvider(authProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
				.antMatchers("/js/**","/libs/**","/login**","/signin**").permitAll()
				.antMatchers("/users/{userId}").access("@authz.check(#userId,principal)")
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
				csrf().disable()
				.headers()
				.contentSecurityPolicy("default-src 'self' " +
						//"https://ajax.googleapis.com " +
						//"https://cdnjs.cloudflare.com; " +
						"style-src 'self' 'unsafe-inline'");
	}
	
}
