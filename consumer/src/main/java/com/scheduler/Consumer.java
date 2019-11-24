package com.scheduler;

import java.io.File;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RabbitHandler
    public void receiveMessage(String message)
    {
        log.info(" receive message::"+message);
        File csv;
        try {
        	 CurrencyData currencyData = new ObjectMapper().readValue(message, CurrencyData.class);
        	         	 
        	 csv = getCSV.generateCSV(currencyData);
        	
        	 MimeMessage email = emailSender.createMimeMessage();
            
        	 MimeMessageHelper helper = new MimeMessageHelper(email, true);
        	 helper.setTo(res.getReceiver());
        	 helper.setSubject(res.getSubject());
        	 helper.setText(res.getContent());
        	 helper.addAttachment(csv.getName(), csv);
            
        	 emailSender.send(email);
        }catch(JsonMappingException e) {
        	
        }catch(Exception ex) {
        	log.error("Error in getting CSV file:" + ex);
        }
        
    }
	
	
}