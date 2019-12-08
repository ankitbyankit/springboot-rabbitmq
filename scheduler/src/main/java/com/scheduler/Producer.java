package com.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.concurrent.Future;

@Component
public class Producer {
	
    private static final Logger log = LoggerFactory.getLogger(Producer.class);
    
    private static final String EXCHANGE = "currency_exchange";
    private static final String ROUTING_KEY = "forex.change";
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    private ObjectMapper objMap;
    
    @Async("threadPoolTaskExecutor")
    public Future<CurrencyData> testProducer(String url) {
    	log.info("Execute method asynchronously - "+ Thread.currentThread().getName());
    	
    	RestTemplate restTemplate = new RestTemplate();
    	CurrencyData cd = null;
    	try {
    		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    		cd = new ObjectMapper().readValue(response.getBody(), CurrencyData.class);
    		
//    		HashMap myMap = objMap.readValue(response.getBody(), HashMap.class);
//    		System.out.println("My hash map:::"+myMap);    		
    		
    		if(!response.getStatusCode().is2xxSuccessful()) {
        		log.info("No currency data for "+url);
        		return null;
        	}
    		if(cd.getChange() != 0) {
    			rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, response.getBody());
    		}
//    		rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, response.getBody());
    		rabbitTemplate.convertAndSend("test.topic.exchange", "success.email", response.getBody());
    		return new AsyncResult<CurrencyData>(cd);
    	}catch(Exception ex) {
    		//rabbitTemplate.convertAndSend("test.topic.exchange", "fail.email", ex.getMessage());
    		log.error("Error in testProducer::"+ex);
    		return null;
    	}
    }
}
    /*Main Method
    @Scheduled(fixedRate = 60000)
    @Async
    public void produce() {
    	for(int i=0; i<currencyList.size();i++){
    		log.info("Inside Producer:::"+currencyList.get(i));
    		String fooResourceUrl = DOMAIN+currencyList.get(0)+API+apiToken+FORMAT;
    		log.info("Inside produce:check point 1");
        	RestTemplate restTemplate = new RestTemplate();
        	ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
        	restTemplate.getForObject(fooResourceUrl,CurrencyData.class);
        	try {
        		Thread.sleep(1000L);
        	}catch(Exception ex) {
        		log.error("ahkadhaksjldhajlkshd:::"+ex);
        	}
    		log.info("Inside produce: check point 2");
    		rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, response.getBody());
    		log.info("Inside produce: check point 3"+response.getBody());
    	}
    	
//    	String fooResourceUrl = DOMAIN+currencyList.get(0)+API+res.getAPI()+FORMAT;
    	
//    	log.info("Inside produce:check point 1");
//    	RestTemplate restTemplate = new RestTemplate();
//		String fooResourceUrl = "https://eodhistoricaldata.com/api/real-time/EUR.FOREX?api_token=OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX&fmt=json";
		
		
		
		
		
//		ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
//		log.info("Inside produce: check point 2");
//		rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, response.getBody());
//		log.info("Inside produce: check point 3"+response.getBody());
    }
}
/*
log.info("1:"+currencyData.getChange());
log.info("2:"+currencyData.getChnage_p());
log.info("3:"+currencyData.getClose());
log.info("4:"+currencyData.getCode());
log.info("5:"+currencyData.getGmtoffset());
log.info("6:"+currencyData.getHigh());
log.info("7:"+currencyData.getLow());
log.info("8:"+currencyData.getOpen());
log.info("9:"+currencyData.getPreviousClose());
log.info("10:"+currencyData.getTimestamp());
log.info("11:"+currencyData.getVolume());
*/
//ClassPathResource res = new ClassPathResource("test.properties");
//Properties prop = new Properties();
//List<String> lst = new ArrayList<String>();
//try {
//	prop.load(Producer.class.getClassLoader().getResourceAsStream("test.properties"));
//}catch(Exception ex) {
//	log.error("Error while reading the test property:"+ex);
//}
//log.info("properrty::::"+prop.get("CURR.AUDNZD"));
//
//Set keys = prop.keySet();
//for(Object key:keys) {
//	log.info("properrty::::"+prop.get(key));
//}

//List<String> currencyList = res.readProperties();