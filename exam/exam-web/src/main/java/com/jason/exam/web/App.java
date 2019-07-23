package com.jason.exam.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EntityScan({"com.jason.exam.model"})
@EnableJpaRepositories({"com.jason.exam.dao.repository"})
@SpringBootApplication(scanBasePackages = {"com.jason.exam","com.jason.exam.security"})
public class App 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
    }
}
