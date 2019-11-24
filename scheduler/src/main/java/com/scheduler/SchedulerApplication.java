package com.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SchedulerApplication {

	private static final Logger log = LoggerFactory.getLogger(SchedulerApplication.class);

	public static void main(String[] args) {
		log.info("inside application class");
		SpringApplication.run(SchedulerApplication.class, args);
	}
	
	@Bean(name = "threadPoolTaskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("Scheduler-");
        executor.initialize();
        return executor;
    }
}
