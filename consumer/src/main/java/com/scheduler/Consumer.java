package com.scheduler;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RabbitListener(queues = {"test_queue"})
public class Consumer{

	private static final Logger log = LoggerFactory.getLogger(Consumer.class);
	
	@Autowired
	private GenerateCSV getCSV;
	
	@Autowired
    public JavaMailSender emailSender;
	
	@Autowired
	public ResourceLoader res;
	
	@Autowired
	private ObjectMapper objMap;
	
	@RabbitHandler
    public void receiveMessage(String message)
    {
        log.info(" receive message::"+message);
      
        try {
        	 //CurrencyData currencyData = objMap.readValue(message, CurrencyData.class);
        	 Map map = objMap.readValue(message, HashMap.class);
        	         	 
        	 String csvStr = getCSV.generateCSV(res.getCsvCols(),map);
        	 //String attachmentName = getCSV.getName(currencyData.getTimestamp(),TimeZone.getDefault());
        	 
        	 TimeZone tz = TimeZone.getDefault();
        	 /*Above statement to get timezone can be changed to
        	  * TimeZone tz = TimeZone.getTimeZone(ID) <ID: can be obtained from persistence layer>
        	  * depending on the implementation required.
        	  * But, yes using timezone we can get the attachment name as recipients timezone 
        	  */
        	 Long timeInMillis = Long.valueOf((Integer)map.get("timestamp"));
        	 String attachmentName = getCSV.getName(timeInMillis,tz);
        	
        	 MimeMessage email = emailSender.createMimeMessage();
        	 MimeMessageHelper helper = new MimeMessageHelper(email, true);
        	 helper.setTo(res.getReceiver());
        	 helper.setSubject(res.getSubject());
        	 helper.setText(res.getContent());
        	 helper.addAttachment(attachmentName,new ByteArrayResource(csvStr.getBytes()),"text/csv");
        	 emailSender.send(email);
        }catch(JsonMappingException e) {
        	log.error("Error in Json Mapping into a map:"+e);
        }catch(Exception ex) {
        	log.error("Error in getting CSV file:" + ex);
        }
        
    }
	
//	@RabbitHandler
//    public void receiveMessage(String message)
//    {
//        log.info(" receive message::"+message);
//        File csv;
//        try {
//        	 CurrencyData currencyData = new ObjectMapper().readValue(message, CurrencyData.class);
//        	         	 
////        	 csv = getCSV.generateCSV(currencyData);
//        	 
//        	 String csvStr = getCSV.generateCSV(currencyData);
//        	
//        	 MimeMessage email = emailSender.createMimeMessage();
//        	 MimeMessageHelper helper = new MimeMessageHelper(email, true);
//        	 helper.setTo(res.getReceiver());
//        	 helper.setSubject(res.getSubject());
//        	 helper.setText(res.getContent());
////        	 helper.addAttachment(csv.getName(), csv);
//        	 helper.addAttachment("test.csv",new ByteArrayResource(csvStr.getBytes()),"text/csv");
//        	 emailSender.send(email);
//        	 
//        }catch(JsonMappingException e) {
//        	
//        }catch(Exception ex) {
//        	log.error("Error in getting CSV file:" + ex);
//        }
//        
//    }
	
	
}