package com.bluewater.utilities.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {
	
	//round double to 2 decimal places
   public static double round( double d ) {
	   BigDecimal bd = new BigDecimal(d);
	   bd = bd.setScale(2,BigDecimal.ROUND_HALF_DOWN);
	   return bd.doubleValue();
   }
   /**
    * perform BigDecimal division, rounded to two places
    * @param numerator
    * @param denominator
    * @return
    */
   public static double divide(double numerator, double denominator ){
      BigDecimal a = new BigDecimal(numerator);
	  BigDecimal b = new BigDecimal(denominator);
	  return a.divide(b, RoundingMode.HALF_DOWN).doubleValue();

   }
   
   public static double multiply(double d1, double d2 ){
	  BigDecimal a = new BigDecimal(d1);
      BigDecimal b = new BigDecimal(d2);
	  return a.multiply(b).doubleValue();

	}
}
