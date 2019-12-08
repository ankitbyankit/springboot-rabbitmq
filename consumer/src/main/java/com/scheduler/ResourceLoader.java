package com.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:demo.properties")
public class ResourceLoader {

	@Autowired
	private Environment env;
	
	public String getReceiver() {
		String receiver = env.getProperty("email.receiver");
		return receiver;
	}
	
	public String getSubject() {
		String subject = env.getProperty("email.subject");
		return subject;
	}
	
	public String getContent() {
		String text = env.getProperty("email.text");
		return text;
	}
	
	public String getCsvCols() {
		String cols = env.getProperty("csv.columns");
		return cols;
	}
}
