package com.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Component
//public class ScheduledTasks {
//
//	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
//
//	@Scheduled(fixedRate = 60000)
//	public void scheduleTaskWithFixedRate() {
//		
//		System.out.println("inside Scheduled Tasks: Entry Point");
// 		RestTemplate restTemplate = new RestTemplate();
//		String fooResourceUrl = "https://eodhistoricaldata.com/api/real-time/EUR.FOREX?api_token=OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX&fmt=json";
//
//		//EURO, AUDUSD
//
//
//		ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
//		System.out.println("Status code:::"+response.getStatusCode());
//		System.out.println("Response Body:::"+response.getBody());
//		log.info("End of scheduled tasks");
//	}
//}
