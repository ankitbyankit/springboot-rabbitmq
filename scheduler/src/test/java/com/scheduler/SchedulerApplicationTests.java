package com.scheduler;

//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SchedulerApplication.class)
public class SchedulerApplicationTests {
	
	private static final Logger log = LoggerFactory.getLogger(SchedulerApplicationTests.class);
		
	@Autowired
	private Producer produce;
	
	@Autowired
	private ResourceLoader res;

	@Test
	public void producerServiceTests() {
				
		String url = "https://eodhistoricaldata.com/api/real-time/EUR.FOREX?api_token=OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX&fmt=json";
//		String url = res.getUrl();
		Future<CurrencyData> output = produce.testProducer(url);		
		try {
			while(true) {
				if(output != null && output.isDone()) {
					CurrencyData cd = output.get();
					assertNotNull(cd);
					assertEquals("EUR.FOREX",cd.getCode());
					break;
				}
			}
		}catch(Exception ex) {
			log.error("Error in ProducerTests:"+ex);
		}
	}
	
	@Test
	public void contextLoads() {
		
	}

}
