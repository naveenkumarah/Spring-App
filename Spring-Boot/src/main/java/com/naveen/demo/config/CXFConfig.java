package com.naveen.demo.config;

import java.util.Arrays;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.naveen.demo.service.UserService;

@Configuration
@ComponentScan(basePackageClasses=UserService.class)
public class CXFConfig {
	
	@Autowired
    private Bus bus;
	
	@Autowired
	UserService userService;
	
	@Bean
    public Server rsServer() {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(bus);
        endpoint.setAddress("/");
        // Register 2 JAX-RS root resources supporting "/sayHello/{id}" and "/sayHello2/{id}" relative paths
        endpoint.setServiceBeans(Arrays.<Object>asList(userService));
       // endpoint.setFeatures(Arrays.asList(new Swagger2Feature()));
        endpoint.setProvider(new JacksonJsonProvider());
        return endpoint.create();
    }
}
