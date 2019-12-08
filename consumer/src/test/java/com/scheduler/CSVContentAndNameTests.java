package com.scheduler;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.junit.Test;
//import org.junit.jupiter.api.Test;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SchedulerApplication.class)
public class CSVContentAndNameTests {
	
	@Autowired
	private GenerateCSV generateCSV;
	
	@Test
	public void testCSVContent() {
		Map map = new HashMap();
		map.put("code", "EUR.FOREX");
		map.put("timestamp", 1575574800);
		map.put("gmtoffset", 0);
		map.put("open", 1575574800);
		map.put("high", 0.9024);
		map.put("low", 0.8998);
		map.put("close", 0.9008);
		map.put("previousClose", 0.9027);
		map.put("change", -0.002);
		map.put("change_p", -0.21);
		
		StringBuilder sb = new StringBuilder();
		sb.append("code");
		sb.append(",");
		sb.append("change");
		sb.append("\n");
		sb.append("EUR.FOREX");
		sb.append(",");
		sb.append(-0.002);
		sb.append("\n");
		
		String expected = sb.toString();
		
		try {
			String result = generateCSV.generateCSV("code,change",map);
			assertEquals(expected, result);
		}catch(Exception ex) {}
	}
	
	@Test
	public void testCsvName() {
		Long timeInMillis = 1575343380L;
		TimeZone tz = TimeZone.getTimeZone("Australia/Sydney");
		Date date = new Date(timeInMillis * 1000);
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd_HHmm");
		sdf.setTimeZone(tz);
		String expected = "obsval_"+sdf.format(date)+".csv";
		
		//OR
//		String expected = "obsval_"+"20191203_1123"+".csv";
		/*For this you have to convert the date in milliseconds and
		 * assign it to timeInMillis
		 * Note: Expected should have values as per recipients timezone
		 */
		
		try {
			assertEquals(expected,generateCSV.getName(timeInMillis, tz));
		}catch(Exception ex) {}
	}
}
