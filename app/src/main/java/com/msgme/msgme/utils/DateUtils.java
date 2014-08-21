package com.msgme.msgme.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.content.Context;


public class DateUtils {
	
	public static String getMessageDate(Date inputDate)
	{		
		try {
			 //current time.
			Date now = new Date();
		    
		    int days = (int)( (inputDate.getTime() - now.getTime()) / (1000 * 60 * 60 * 24));
		    days = days * -1;
		    
		    SimpleDateFormat formatter =  null;
		    
		    if (days <= 0)
		    	formatter = new SimpleDateFormat("HH:mm",Locale.US);
		    else if (days > 0 && days <= 7)
		    	formatter = new SimpleDateFormat("d MMM",Locale.US);
		    else if (days > 7)
		    	formatter = new SimpleDateFormat("d MMM yyyy",Locale.US);
		    
		     String result = formatter.format(inputDate);
		     
		     return result;
		} catch (Exception e) 
		{
			return "";
		}
	   
	}
}
