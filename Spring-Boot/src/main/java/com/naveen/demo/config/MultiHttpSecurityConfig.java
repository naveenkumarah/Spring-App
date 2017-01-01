package com.naveen.demo.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.context.request.RequestContextListener;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration
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
	@EnableOAuth2Client
	@Order(2)                                                        
	public static class Outh2WebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Autowired
		OAuth2ClientContext oauth2ClientContext;
		//@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http.antMatcher("/login/**") 
					.authorizeRequests().anyRequest().authenticated().and().exceptionHandling()
					.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and().logout()
					.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
					.and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
			// @formatter:on
		}

		@Bean
		public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
			FilterRegistrationBean registration = new FilterRegistrationBean();
			registration.setFilter(filter);
			registration.setOrder(-100);
			return registration;
		}

		private Filter ssoFilter() {
			OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter(
					"/login/facebook");
			OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
			facebookFilter.setRestTemplate(facebookTemplate);
			facebookFilter.setTokenServices(
					new UserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId()));
			return facebookFilter;
		}
		@Bean 
		public RequestContextListener requestContextListener(){
		    return new RequestContextListener();
		} 

		@Bean
		@ConfigurationProperties("facebook.client")
		public AuthorizationCodeResourceDetails facebook() {
			return new AuthorizationCodeResourceDetails();
		}

		@Bean
		@ConfigurationProperties("facebook.resource")
		public ResourceServerProperties facebookResource() {
			return new ResourceServerProperties();
		}}

	@Configuration   
	@Order(3)                                                   
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
			.antMatchers("/js/**","/libs/**","/login**").permitAll();
			
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
	}
}
