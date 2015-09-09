/*
 * Created on Aug 7, 2005
 *
 */
package com.bluewater.utilities.date;

import java.util.Calendar;
import java.util.Date;

/**
 * @author jc
 * 
 */
public class JavaDateTestDriver {

    /**
     * 
     */
    public JavaDateTestDriver() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
      //System.out.println(JavaDate.isWeekendDay(JavaDate.getDate("01-06-2007", "mm-dd-yyyy")));
      Date d1 = JavaDate.getDate("03-22-2011", "MM-dd-yyyy");
     
      int year = JavaDate.getYear(d1);
      int month = JavaDate.getMonth(d1);
      int day = JavaDate.getDay(d1);
      System.out.println(JavaDate.getNextDayOfWeek(year, month, day, 6));
      //System.out.println(JavaDate.getDifference(today, d1));
    
    }
    
    public static void isSameDay( Date d1, Date d2 ) {
 	   Calendar cal1 = Calendar.getInstance();
	   cal1.setTime(d1);
 	   Calendar cal2 = Calendar.getInstance();
	   cal2.setTime(d2);
	   System.out.println(cal1.get(Calendar.DAY_OF_MONTH));
	   System.out.println(cal1.get(Calendar.YEAR));
	   System.out.println(cal1.get(Calendar.MONTH));
    }
}
