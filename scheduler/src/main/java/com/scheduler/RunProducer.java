package com.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class RunProducer {
	
	private static final Logger log = LoggerFactory.getLogger(RunProducer.class);
	
	@Autowired
	private ResourceLoader res;
	
	@Autowired
	private Producer pro;
	
	private static final String DOMAIN = "https://eodhistoricaldata.com/api/real-time/";
    private static final String API = ".FOREX?api_token=";
    private static final String FORMAT = "&fmt=json";

	@Scheduled(fixedRate = 60000)
	public void test() throws Exception{
		
		List<String> lst = res.readProperties();
		final List<Future<CurrencyData>> futures = new ArrayList<Future<CurrencyData>>();
		String token = res.getAPI();
		log.info("Checking everying minute");
		for(int i=0; i<lst.size();i++){
			String url = DOMAIN+lst.get(i)+API+token+FORMAT;
//			Future<CurrencyData>future = pro.testProducer(url);
			futures.add(pro.testProducer(url));
		}	
		
		log.info("Size of futures::"+futures.size());
		for(int i=0; i<futures.size(); i++) {
			if(futures.get(i).isDone())
				log.info("get the value::"+futures.get(i).get().getCode());
		}
	}
}


/*
//try {
//for (Future<CurrencyData> future : futures) {
//	if(future.isDone()) {
//		log.info(("Inside future loop::"+future.get().getCode()));
//	}
//     // do anything you need, e.g. isDone(), ...
//}
//} catch (Exception e) {
//e.printStackTrace();
//}
*/