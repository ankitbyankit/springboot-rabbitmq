package com.scheduler;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:demo.properties")
public class ResourceLoader {
	
	@Autowired
	private Environment env;
	
	public List<String> readProperties () {
		
		String currencies = env.getProperty("currencies");		
		String[] arr = currencies.split(",");
		
		return Arrays.asList(arr);
	}
	
	public String getAPI() {
		String apiToken = env.getProperty("apiToken");
		return apiToken;
	}
	
	public String getUrl() {
		return env.getProperty("cft.url");
	}
}
