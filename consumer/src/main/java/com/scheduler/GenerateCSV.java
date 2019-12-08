package com.scheduler;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opencsv.CSVWriter;

@Component
public class GenerateCSV {
	
private static SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd_HHmm");
		
public String generateCSV(Map map) throws Exception{
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("Forex");
		sb.append(",");
		sb.append("Change");
		sb.append("\n");
		sb.append(map.get("code"));
		sb.append(",");
		sb.append(map.get("change"));
		sb.append("\n");
		
		String csvFile = sb.toString();
		
	    return csvFile;
	}

public String getName(Long timeInMillis, TimeZone tz) throws Exception{
	
	Calendar clientCal  = Calendar.getInstance(tz);
	clientCal.setTimeInMillis(timeInMillis*1000);
	
	Calendar localCal = Calendar.getInstance();
	localCal.set(Calendar.YEAR, clientCal.get(Calendar.YEAR));
	localCal.set(Calendar.MONTH, clientCal.get(Calendar.MONTH));
	localCal.set(Calendar.DAY_OF_MONTH, clientCal.get(Calendar.DAY_OF_MONTH));
	localCal.set(Calendar.HOUR_OF_DAY, clientCal.get(Calendar.HOUR_OF_DAY));
	localCal.set(Calendar.MINUTE, clientCal.get(Calendar.MINUTE));
	localCal.set(Calendar.SECOND, clientCal.get(Calendar.SECOND));
	
	//obsval_YYYYMMDD_HHMM.csv
	return "obsval_"+sdf.format(localCal.getTime()).toString()+".csv";
	
	//****Another way to get date and time in the recipients timezone******
//	Date date = new Date(timeInMillis * 1000L);
//	sdf.setTimeZone(tz);
//	return "obsval_"+sdf.format(date).toString()+".csv";
	
}

/*Commented code but working */
//	@Autowired
//	private Date date;
//	
//	@Autowired
//	private Calendar calendar;
	
//	public File generateCSV(CurrencyData currencyData) throws Exception{
//		
//		//a filename of obsval_YYYYMMDD_HHMM.csv e.g. obsval_20191015_0800.csv
//		date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd_hhmm");
//		String filename = "obsval_"+sdf.format(date)+".csv";
//		File file = new File(filename);
//		CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
//		
//		
//		
//		String[] header = {"Currency", "Change"};
//		csvWriter.writeNext(header);
//		
//		String [] record = {currencyData.getCode(),String.valueOf(currencyData.getChange())};
//	    csvWriter.writeNext(record);
//	    csvWriter.close();
//	      
//	    return file;
//	}
	
}