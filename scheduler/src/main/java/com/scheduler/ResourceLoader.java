package com.scheduler;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;


@Configuration
@PropertySource("classpath:demo.properties")
public class ResourceLoader {
	
	@Autowired
	private Environment env;
	
	public List<String> readProperties () {
		
		String currencies = env.getProperty("currencies");		
		String[] arr = currencies.split(",");
		
//		System.out.println("::::::::"+arr[0]+">>"+arr[1]+">>"+t2);
		return Arrays.asList(arr);
	}
	
	public String getAPI() {
		String apiToken = env.getProperty("apiToken");
		return apiToken;
	}
}
