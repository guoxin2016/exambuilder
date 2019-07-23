package com.jason.exam.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EntityScan({"com.jason.exam.model"})
@EnableJpaRepositories({"com.jason.exam.dao.repository"})
@SpringBootApplication(scanBasePackages = {"com.jason.exam"})
@ServletComponentScan
public class App 
{
	public static Map<String, String> CHANNEl_MAP = new HashMap<String, String>();
	
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
    }
}
