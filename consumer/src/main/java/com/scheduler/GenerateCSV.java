package com.scheduler;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.opencsv.CSVWriter;

@Component
public class GenerateCSV {
	
	public File generateCSV(CurrencyData currencyData) throws Exception{
		
		//a filename of obsval_YYYYMMDD_HHMM.csv e.g. obsval_20191015_0800.csv
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd_hhmm");
		String filename = "obsval_"+sdf.format(date)+".csv";
		File file = new File(filename);
		CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
		
		String[] header = {"Currency", "Change"};
		csvWriter.writeNext(header);
		
		String [] record = {currencyData.getCode(),String.valueOf(currencyData.getChange())};
	    csvWriter.writeNext(record);
	    csvWriter.close();
	      
	    return file;
	}
	
}