package com.scheduler;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opencsv.CSVWriter;

@Component
public class GenerateCSV {
	
private static SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd_HHmm");
		
public String generateCSV(String cols,Map map) throws Exception{
	
// Main method: Using springwriter and csvwriter to genrate the csv content
		StringWriter sw = new StringWriter();
		CSVWriter csvWriter = new CSVWriter(sw);
		
		String[] header = cols.split(",");
//		String[] header = {"Currency", "Change"};
		csvWriter.writeNext(header);

		String[] record = new String[header.length];
//		String [] record = {"EUR","-0.02"};
		for(int i=0; i<header.length;i++) {
			record[i] = map.get(header[i]).toString();
		}
	    csvWriter.writeNext(record);
	    csvWriter.close();
	
/*-- #1: Following is another way to generate CSV in memory using stringBuilder ----  */
//		StringBuilder sb = new StringBuilder();
//	    String[] header = cols.split(",");
//		sb.append("code");  //code = header[0];
//		sb.append(",");
//		sb.append("change");   //header[1];
//		sb.append("\n");
//		sb.append(map.get("code"));   //map.get(header[0])
//		sb.append(",");
//		sb.append(map.get("change"));  //map.get(header[1])
//		sb.append("\n");
//	    String csvFile = sb.toString();
		
		String csvFile = sw.toString().replaceAll("\"", "");
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
	
	//*--  #2: Another way to get date and time in the recipients timezone -----
//	Date date = new Date(timeInMillis * 1000L);
//	sdf.setTimeZone(tz);
//	return "obsval_"+sdf.format(date).toString()+".csv";
	
}

/*Commented code but working 
 * #3: Using simple springwriter without CSW writer to generate a csv file
 * */
//		Using Springwriter
//		sw.append("Forex");
//		sw.append(",");
//		sw.append("Change");
//		sw.append("\n");
//		sw.append((String)map.get("code"));
//		sw.append(",");
//		sw.append((String)map.get("change"));
//		sw.append("\n");
//		String str = sw.toString().replaceAll("\"", "");


// --- #4: Generate csv file using a file writer ----
//@Autowired
//private Date date;
//
//@Autowired
//private Calendar calendar;

//public File generateCSV(CurrencyData currencyData) throws Exception{
//	
//	//a filename of obsval_YYYYMMDD_HHMM.csv e.g. obsval_20191015_0800.csv
//	date = new Date();
//	SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd_hhmm");
//	String filename = "obsval_"+sdf.format(date)+".csv";
//	File file = new File(filename);
//	CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
//	
//	
//	
//	String[] header = {"Currency", "Change"};
//	csvWriter.writeNext(header);
//	
//	String [] record = {currencyData.getCode(),String.valueOf(currencyData.getChange())};
//    csvWriter.writeNext(record);
//    csvWriter.close();
//      
//    return file;
//}
	
}