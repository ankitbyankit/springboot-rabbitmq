package com.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableScheduling
public class SchedulerApplication {

	private static final Logger log = LoggerFactory.getLogger(SchedulerApplication.class);

	public static void main(String[] args) {
		log.info("inside application class");
		SpringApplication.run(SchedulerApplication.class, args);
	}
}
