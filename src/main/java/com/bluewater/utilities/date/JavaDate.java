package com.bluewater.utilities.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @author Jeff Comer
 * a utility class to encapsulate commonly performed functions with java.util.Date
 */
public final class JavaDate {

    /**
     * input is a string s in a format understood by java's SimpleDateFormat
     * e.g "dd/mm/yyyy"
     * and the Date format string f
     * e.g. "dd/MM/yyyy"
     */
    
    public static Date getDate( String s, String f ) {
       java.util.Date d = null;
       SimpleDateFormat sdf = new SimpleDateFormat( f, Locale.US );
	   sdf.setCalendar(Calendar.getInstance());
	   sdf.setLenient(false);
       try {
       	  d = sdf.parse(padDateString(s));
       }
       catch ( ParseException e ) { e.printStackTrace(); } 
       return d;	
    }
    /**
     * get date, try to determine format from string
     * @param s
     * @return
     */
    public static Date getDate( String s ) {
    	String f = getFormat( s );
    	
    	Date d = getDate( s, f );
    	SimpleDateFormat sdf=new SimpleDateFormat(f);
    	try {
    		return(sdf.parse(sdf.format(d)));
    	} catch (ParseException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return d;
    	//return getDate( s, f );
    }
    public static Date getCurrentDate() {
    	String s = getTodaysDateString();
    	String f = DateConstants.YAHOO_QUOTE_DATE_FORMAT;
    	Date d = getDate( s, f );
    	SimpleDateFormat sdf=new SimpleDateFormat(DateConstants.YAHOO_QUOTE_DATE_FORMAT);
    	try {
    		return(sdf.parse(sdf.format(d)));
    	} catch (ParseException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return d;
    	//return getDate( s, f );
    }
    
    public static Date getCurrentDate(String format) {
    	String s = getTodaysDateString();
    	String f = DateConstants.YAHOO_QUOTE_DATE_FORMAT;
    	Date d = getDate( s, f );
    	SimpleDateFormat sdf=new SimpleDateFormat(format);
    	try {
    		return(sdf.parse(sdf.format(d)));
    	} catch (ParseException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return d;
    	//return getDate( s, f );
    }
	/**
	 * Get today's date as a string.
	 * uses default format MM-dd-yyyy
	 */
	public static String getTodaysDateString() {
		Date ts=new Date();
		SimpleDateFormat f=new SimpleDateFormat("MM-dd-yyyy");
		return(f.format(ts));
	}
	
	public static Date getToday() {
		return new Date();
	}
	
	public static String getTodaysDateString(String format) {
		Date ts=new Date();
		SimpleDateFormat f=new SimpleDateFormat(format);
		return(f.format(ts));
	}
	
   // gets year as String from a Java date object representing an integer
   public static String getYearFromDate ( Date d ) {
	  return new Integer(getYear(d)).toString();
   }
   
   // gets month as String from a Java date object representing an integer
   public static String getMonthFromDate ( Date d ) {
	  int month = getMonth(d);
	  String monthStr;
	  if ( month < 10 ) monthStr = "0" + intToString(month);
	  else
		  monthStr = intToString(month);
	  return monthStr;
   }
   
   // gets day as String from a Java date object representing an integer
	public static String getDayFromDate(Date d) {
		int day = getDay(d);
		String dayStr;
		if (day < 10)
			dayStr = "0" + intToString(day);
		else
			dayStr = intToString(day);
		return dayStr;
	}
  
   public static int getYear( Date d ) {
	   Calendar calendar = Calendar.getInstance();
	   calendar.setTime(d);
	   return calendar.get(Calendar.YEAR);
   }
   
   private static String intToString( int i ){
	   return new Integer(i).toString();
   }
   
   public static int getDay( Date d ) {
	  Calendar calendar = Calendar.getInstance();
	  calendar.setTime(d);
      return calendar.get(Calendar.DAY_OF_MONTH);
   }
   
   public static int getMonth( Date d ) {
      Calendar calendar = Calendar.getInstance();
	  calendar.setTime(d);
      return calendar.get(Calendar.MONTH);
   }
   
   // gets name of month as String from a Java date object 
   public static String getMonthAbbrevFromDate ( Date d ) {
	   Calendar calendar = Calendar.getInstance();
	   calendar.setTime(d);
	   String name="";
	   int month = calendar.get(Calendar.MONTH);
	   switch (month) {
	   case 0:  name= "JAN"; break;
	   case 1:  name= "FEB"; break;
	   case 2:  name= "MAR"; break;
	   case 3:  name= "APR"; break;
	   case 4:  name= "MAY"; break;
	   case 5:  name= "JUN"; break;
	   case 6:  name= "JUL"; break;
	   case 7:  name= "AUG"; break;
	   case 8:  name= "SEP"; break;
	   case 9:  name= "OCT"; break;
	   case 10: name= "NOV"; break;
	   case 11: name= "DEC"; break;
	   default: name= "INVALID_MONTH"; break;
	   }
	   return name;

   }
   public static String getMonthNameFromDate ( Date d ) {
	   Calendar calendar = Calendar.getInstance();
	   calendar.setTime(d);
	   String name="";
	   int month = calendar.get(Calendar.MONTH);
	   switch (month) {
	   case 0:  name= "January"; break;
	   case 1:  name= "February"; break;
	   case 2:  name= "March"; break;
	   case 3:  name= "April"; break;
	   case 4:  name= "May"; break;
	   case 5:  name= "June"; break;
	   case 6:  name= "July"; break;
	   case 7:  name= "August"; break;
	   case 8:  name= "September"; break;
	   case 9:  name= "October"; break;
	   case 10: name= "November"; break;
	   case 11: name= "December"; break;
	   default: name= "INVALID_MONTH"; break;
	   }
	   return name;

   }
   
   public static String getDateString( java.util.Date d, String f ) {
   	   SimpleDateFormat sdf = new SimpleDateFormat ( f ); 
   	   return ( sdf.format( d ) );
   }
    
    /**
     * Compare two dates passed in as strings
     * @param d1  - the first date 
     * @param d2  - second date
     * @param format - format for creating dates from strings
     * @return true if d2 comes after d1, false otherwise
     */
    public static boolean isDateAfter( String d1, String d2, String format) {
        Date date1 = getDate( d1, format );
        Date date2 = getDate( d2, format );
      
        return date2.after(date1) ? true: false;
  
    }
    
    /**
     * Compare two dates passed in as strings
     * @param d1  - the first date 
     * @param d2  - second date
     * @param format - format for creating dates from strings
     * @return true if d2 equals d1, false otherwise
     */
    public static boolean isDateEqual( String d1, String d2, String format) {
        Date date1 = getDate( d1, format );
        Date date2 = getDate( d2, format );
      
        return date2.equals(date1) ? true: false;
  
    }
    /** determine number of days between two dates
     */
    public static int getDifference(Date d1, Date d2) {
    	if ( (d1==null) || (d2 == null) ) {
    		return -1;
    	}

       Calendar calendar = new GregorianCalendar();
       calendar.setTime(d2);
       int year2 = calendar.get(Calendar.YEAR);
       int day2 = calendar.get(Calendar.DAY_OF_YEAR);
       int hour2 = calendar.get(Calendar.HOUR_OF_DAY);
       int min2  = calendar.get(Calendar.MINUTE);
        
       calendar.setTime(d1);
       int year1 = calendar.get(Calendar.YEAR);
       int day1 = calendar.get(Calendar.DAY_OF_YEAR);
       int hour1 = calendar.get(Calendar.HOUR_OF_DAY);
       int min1  = calendar.get(Calendar.MINUTE);
       
       int leftDays = (day1-day2)+(year1-year2)*365;
       int leftHours = hour1-hour2;
       int leftMins  = min1 - min2;
        
       if(leftMins < 0) {
           leftMins += 60;
           --leftHours;
       }
       if(leftHours < 0) {
           leftHours += 24;
           --leftDays;
       }
        
       return leftDays;
   } 
   
   /**
    * Get the nth weekday of a given month. e.g. The third Friday of the first month of 2000
    * @param year  - year month is in
    * @param month - month , 1= Jan, etc
    * @param n     - number of the the weekday to look for
    * @param dayofweek - int from Calendar.DAY_OF_WEEK, e.g. Friday = 6
    * @return
    */ 
   public static Date getNthWeekDayofMonth(int year, int month, int n, int dayofweek){
	 
	   Calendar calendar = new GregorianCalendar(year, month, 1);
	   int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	   int count=0;
	   
	   for ( int i=1; i <= days; i++ ) {
		  calendar = new GregorianCalendar(year, month, i);
		  if (calendar.get(Calendar.DAY_OF_WEEK) == dayofweek) count++;
		  if ( count == n ) return calendar.getTime();
	   }
       
	   return null;
   }
   /**
    * Get the next specified weekday of a given month. e.g. The next Friday from the given date ( if Thursday skip to next week )
    * @param year  - year month is in
    * @param month - month , 1= Jan, etc
    * @param dayofweek - int from Calendar.DAY_OF_WEEK, e.g. Friday = 6
    * @return
    */ 
   public static Date getNextDayOfWeek(int year, int month, int day, int dayofweek){
		 
	   Calendar calendar = new GregorianCalendar(year, month, day);
	   calendar.setTime(calendar.getTime());    
		
	   int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	   
	   for ( int i=day; i <= days; i++ ) {
		  if (calendar.get(Calendar.DAY_OF_WEEK) == dayofweek) return calendar.getTime();
		  calendar = new GregorianCalendar(year, month, i);
	   }
	   // month ended without a match; go into following month
	    month = month +1;
		Calendar calendar2 = new GregorianCalendar(year, month, 1);
		calendar2.setTime(calendar2.getTime());
		days = calendar2.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 1; i <= days; i++) {
			if (calendar2.get(Calendar.DAY_OF_WEEK) == dayofweek) return calendar2.getTime();
			calendar2 = new GregorianCalendar(year, month, i);
		}
		return null;
   }
  
   /**
    * determines whether given day falls on Saturday or Sunday
    * @param year
    * @param month
    * @param day
    * @return
    */
   public static boolean isWeekendDay(Date d) {
	   Calendar calendar = Calendar.getInstance();
	   calendar.setTime(d);
	   int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
	   return ( (dayofweek == Calendar.SATURDAY ) || (dayofweek == Calendar.SUNDAY));
   }
  
   /**
    * find date that occurs n days before today, e.g, 3 days before today
    * @param n - number of days to go back
    * @return
    */
   public static Date getDateNDaysBeforeToday(int n ) {
	   Calendar calendar = new GregorianCalendar();
       calendar.setTime(new Date());
       calendar.add(Calendar.DATE, -1*n);
       return calendar.getTime();
   }
   
   public static Date getDateNDaysBeforeDate(Date d, int n ) {
	   Calendar calendar = new GregorianCalendar();
       calendar.setTime(d);
       calendar.add(Calendar.DATE, -1*n);
       return calendar.getTime();
   }
   
   public static boolean isSpecifiedDay(Date d, int dayofweek) {
	   Calendar calendar = Calendar.getInstance();
	   calendar.setTime(d);
	   int dayofweek_indate = calendar.get(Calendar.DAY_OF_WEEK);
	   return dayofweek_indate == dayofweek;
   }
   // check to see if two dates are same based on day month and year, ignoring the timestamp etc
   public static boolean isSameDate( Date d1, Date d2 ) {
	   Calendar cal1 = Calendar.getInstance();
	   cal1.setTime(d1);
	   Calendar cal2 = Calendar.getInstance();
	   cal2.setTime(d2);
	   return ( (cal1.get(Calendar.MONTH)==cal2.get(Calendar.MONTH)) && (cal1.get(Calendar.DAY_OF_MONTH)==cal2.get(Calendar.DAY_OF_MONTH)) && (cal1.get(Calendar.YEAR)==cal2.get(Calendar.YEAR)));
   }
   /**
    * guess format of date based on date string. Hack because of Yahoo returning inconsistent 
    * date formats in its historical quote datamanager.quotes.
    * @param d - the date string
    * @return
    */
   private static String getFormat( String s ) {
	String d = padDateString( s );
   	if ( d.indexOf("/") > 0 ) {
   		String[] items = d.split("/");
   		if ( ( items[0].length() == 2 ) && ( items[1].length() == 2 ) && ( items[2].length() == 4 ) )
   			return "MM/dd/yyyy";
   	}
   	if ( d.indexOf("-") > 0 ) {
   		String[] items = d.split("-");
   		if ( ( items[0].length() == 2 ) && ( items[1].length() == 3 ) && ( items[2].length() == 2 ) )
   			return "dd-MMM-yy";
   		if ( ( items[0].length() == 4 ) && ( items[1].length() == 2 ) && ( items[2].length() == 2 ) )
   			return "yyyy-MM-dd";
   		if ( ( items[0].length() == 2 ) && ( items[1].length() == 2 ) && ( items[2].length() == 4 ) )
   			return "MM-dd-yyyy";
   	}
   	
   	return DateConstants.YAHOO_QUOTE_DATE_FORMAT;
   }
   
   /*
    * get integer number of days between dates
    */
   public static int getDaysBetween(Date start,Date end) throws ParseException
   {
	   return (int)((end.getTime() - start.getTime())/(1000*60*60*24));
   }

   private static String padDateString(String d) {
	   
		StringBuffer sb = new StringBuffer();

		if ((d.indexOf("/") > 0) || (d.indexOf("-") > 0)) {
			String separator = "";
			if (d.indexOf("/") > 0)
				separator = "/";
			else if (d.indexOf("-") > 0)
				separator = "-";
			String[] items = d.split(separator);

			if (items[0].length() == 1)
				sb.append("0");
			sb.append(items[0]);
			sb.append(separator);
			if (items[1].length() == 1)
				sb.append("0");
			sb.append(items[1]);
			sb.append(separator);
			sb.append(items[2]);

			return sb.toString();
		} else
			return d;
	}
   
    public static Date getNearestWeekDay(Date d) {
       Calendar calendar = Calendar.getInstance();
 	   calendar.setTime(d);
 	   int dayofweek= calendar.get(Calendar.DAY_OF_WEEK);
 	   switch ( dayofweek ) {
 	   case Calendar.SATURDAY:
 		   return getDateNDaysBeforeDate(d, 1);
 	   case Calendar.SUNDAY:
 		  return getDateNDaysBeforeDate(d, 2);
 		  default:
 			  return d;
 	   }
    }
}
