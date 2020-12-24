package com.ticketmonster.moviebeast.util.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DataSourceConfig implements WebMvcConfigurer {

    @Value("${spring.datasource.driver-class-name}")
    private String dbDriver;

    @Value("${spring.datasource.url}")
    private String dbURL;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(dbDriver);
        driverManagerDataSource.setUrl(dbURL);
        driverManagerDataSource.setUsername(dbUsername);
        driverManagerDataSource.setPassword(dbPassword);
        return driverManagerDataSource;
    }
}
