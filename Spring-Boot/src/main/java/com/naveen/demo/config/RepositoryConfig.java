package com.naveen.demo.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SuppressWarnings("deprecation")
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.naveen.demo.domain"})
@EnableJpaRepositories(basePackages = {"com.naveen.demo.repository"})
@EnableTransactionManagement
public class RepositoryConfig {

}
