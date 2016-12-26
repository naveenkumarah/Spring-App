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
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration
@EnableWebSecurity
public class MultiHttpSecurityConfig {
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
			http.authorizeRequests()
			.antMatchers("/js/**","/libs/**").permitAll();
			
			http
				.authorizeRequests().antMatchers("/signin").anonymous()
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/signin")
				.loginProcessingUrl("/sign-in-process.html")
				.failureUrl("/signin?error")
				.usernameParameter("username")
				.passwordParameter("password")
				.and().logout().logoutSuccessUrl("/signin?logout")
				.and().csrf().disable();
		}
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.debug(false);
			super.configure(web);
		}
	}
	
}
