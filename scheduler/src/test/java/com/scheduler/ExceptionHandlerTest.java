package com.scheduler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//import org.junit.Test;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SchedulerApplication.class)
public class ExceptionHandlerTest {

	@Test
	public void testPageNotFound() {
		try {
			RestTemplate rest = new RestTemplate();
			String url = "https://eodhistoricaldata.com/api/real-time/EURO.FOREX?api_token=OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX&fmt=json";
			ResponseEntity<String> response = rest.getForEntity(url, String.class);			
			fail("should result in HTTP 403");
		}catch(HttpClientErrorException ex) {
			assertEquals(HttpStatus.FORBIDDEN, ex.getStatusCode());
		}
	}
}
