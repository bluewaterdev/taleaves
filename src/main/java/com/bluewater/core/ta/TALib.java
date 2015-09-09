package com.bluewater.core.ta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.bluewater.utilities.math.MathUtil;



/**
 * @author jc
 *
 */
public class TALib  {
	
	
	
	private TALib() {
		
	}
	
	private static boolean isValidSize(double[] vals, int minbars) {
		if ( vals.length < minbars )
			return false;
		return true;
	}

	public static double SMA(double[] vals, int n) {
        if ( !isValidSize( vals, n ))
        	return 0;
        
		double avg = 0;

		for (int i = 0; i < n; i++) {
			avg += vals[i];
		}
		avg = (avg / n);
		return avg;
		// return round(avg, 2 );
	}

	public static double EMA(double P,double C, int n ) {
		//X = (K x (C - P)) + P
		double ema_percent=2.0/(n+1);
		
	    double ema=0;
	    ema = (ema_percent * ( C - P )) + P;
	    //ema = P*ema_percent + (1-ema_percent)*C;
		return ema;
	}
	
	
	public static double EMA(double[] vals, int n ) {
        if ( !isValidSize( vals, n ))
        	return 0;
        
		double curr_ema=0;

		curr_ema = SMA(vals, n);
		int len = vals.length-1;
		
		for ( int j = 0; j < vals.length; j++ ) {
			curr_ema = EMA(curr_ema, vals[len-j], n );
		}
		return curr_ema;
	}
	

	public static  double MACD_DL(double[] data, int p1, int p2) {
	    double diff_line = EMA(data, p1 ) - EMA(data, p2 );
	    return diff_line;
	}
	

	
	public static double MACD_SL(double[] data, int p1, int p2, int p3) {
	// since EMA is derived from an inital SMA estimate of the data
	// we need to use more data than the number of periods ... used a factor of 4 here
	   int calc_periods = p3*4;
	   
       if ( !isValidSize( data, calc_periods ))
       	return 0;
       
	   double signal_line=0;

	   double[] dls = new double[calc_periods ];
	   
	   for (int i=0; i< calc_periods; i++ ) {
	   	  double[] sublist = getSubList( data, i, data.length );
	   	  dls[i] = MACD_DL(sublist, p1, p2);
	   }
	   signal_line = EMA(dls, p3) ;
       return signal_line;
       
    }
	

	public static double MACD_HIST(double[] data, int p1, int p2, int p3 ) {
	    double hight_val = 0;
	    hight_val = MACD_DL(data, p1, p2 ) - MACD_SL(data, p1, p2, p3 ) ;
	    return hight_val;
	}
	

	public static double SLOW_STOCHASTIC( double close, double[] high, double[] low ){
		/*%K = 100[(C - L14)/(H14 - L14)] 
		         C = the most recent closing price 
		         L14 = the low of the 14 previous trading sessions 
		         H14 = the highest price traded during the same 14-day period.

		         %D = 3-period moving average of %K 
		 
*/       double L14 = SMA(low, 14);
         double H14 = SMA(high, 14 );
		 return 100*((close - L14)/(H14 - L14));
	}
	
	

	public static double[] ADX_DIS(double[] close, double[] high, double[] low, int p){
        
		int n = (2*p)-1;
	    int calc_range =n*5;
	    
	    double[] results = new double[2];
	    
	    if ( !isValidSize( high, calc_range+1 ))
	       	return results;
        	
	    
		double[] p_dms = new double[calc_range];
        double[] m_dms = new double[calc_range];
        double[] trs   = new double[calc_range];
        
        double highDiff=0;
        double lowDiff =0;
        
        double plus_DM=0;
        double minus_DM=0;
        
        double pDM_EMA=0; // the p period EMA of plus_DM's
        double mDM_EMA=0; // the p period EMA of minus_DM's
        double TR_EMA=0; // the p period EMA of True Range
        double pDI =0;   // +DI in Wilder's notation
        double mDI =0;   // -DI in Wilder's notation
        
        
     
        for ( int i=0; i< calc_range; i++ ) {
          // System.out.println("i is " + i );
           highDiff = high[i]-high[i+1];
           lowDiff  = low[i+1] - low[i];
           
		    if (((highDiff < 0) && (lowDiff < 0)) || (highDiff == lowDiff)) {
                plus_DM = 0;
                minus_DM = 0;
            }
            if (highDiff > lowDiff) {
                plus_DM = highDiff;
                minus_DM = 0;
            }
            if (highDiff < lowDiff) {
                plus_DM = 0;
                minus_DM = lowDiff;
            }
           p_dms[i]= plus_DM;
           m_dms[i]= minus_DM;
           trs[i] = TRUE_RANGE(close[i+1], high[i], low[i]);
        }
	   
        pDM_EMA = EMA(p_dms, n);
	    mDM_EMA = EMA(m_dms, n);
	    TR_EMA  = EMA(trs, n);
	    pDI = (pDM_EMA/ TR_EMA)*100;
	    mDI = (mDM_EMA/ TR_EMA)*100;
	    
	    results[0] = pDI;
	    results[1] = mDI;
	    return results;
	}
	

    public static double ADX( double[] close, double[] high, double[] low, int p ) {
        int n = (2*p)-1;
        int calc_range =5*n;
         
        if ( !isValidSize( close, calc_range )){
        	return 0;
        }
    	double[] dxs = new double[calc_range];
    	double[] DI =null;
    	double dx = 0;
    	
    	for ( int i=0; i< calc_range; i++ ) {
    		DI = ADX_DIS(getSubList( close, i, close.length ),getSubList( high, i, high.length ), getSubList( low, i, low.length ), p );
    		if ( DI[0] == 0 ) return 0;
    		dx = ((Math.abs(DI[0]-DI[1]))/(DI[0]+DI[1]))*100;
    		dxs[i] = dx;
    	}

   
    	return EMA(dxs, n );
    }
    

    
	public static double TRUE_RANGE(double previous_close, double todays_high, double todays_low){
	   
	    double range1=0;
	    double range2=0;
	    double range3=0;
	    double true_range =0;
	   
	    
	    range1 = todays_high - todays_low;
	    range2 = todays_high - previous_close;
	    range3 = todays_low  - previous_close;
	    
	    true_range = range1;
	    if (range2 > true_range ) true_range=range2;
	    if (range3 > true_range ) true_range=range3;
	    
		return true_range;
	
	}
	

	public static double ATR(double[] close_data, double[] high_data, double[] low_data, int p ) {
	    double[] trs = new double[p];
	    for ( int i=0; i< trs.length; i++ ) {
	        trs[i] = TRUE_RANGE(close_data[i+1], high_data[i], low_data[i] );
	    }
	    return EMA(trs, p );
	}
	

	public static double RSI(double[] close_data, int period ) {

	    int p = (period*2)-1; // Wilder's 14 day averaging technique equates to a 27 day EMA
	    int calc_range =5*p<100 ? 100: 5*p;
	    
        if ( !isValidSize( close_data, calc_range )){
        	return 0;
        }
        
	    double[] upMovements = new double[calc_range];
	    double[] downMovements = new double[calc_range];
	    double aveUp=0;
	    double aveDown=0;
	    double RS=0;
	    
	    double priceDiff = 0 ;
	    
		for ( int i=0; i< calc_range; i++ ) {
		    priceDiff = close_data[i] - close_data[i+1];
		    if ( priceDiff > 0 ) upMovements[i] = priceDiff;
		    else upMovements[i] =0;
		    if ( priceDiff < 0 ) downMovements[i] = Math.abs(priceDiff);
		    else downMovements[i] =0;
		    
		}
	    aveUp = EMA( upMovements, p );
	    aveDown= EMA(downMovements,p );
	    RS = aveUp/aveDown;
	    
	    return 100 - 100 / ( 1 + RS );
	}
	
	
	
	private static double[] getSubList(double[] list ,int startpos, int endpos){
		int listsize=0;
		listsize = endpos - startpos;
		double[] tmp = new double[listsize];
		int j = 0;
		
		for (int i=startpos; i< endpos; i++ ) {
			tmp[j++] = list[i];
		}
		return tmp;
	}
  

	public static double historicalVolatility(int days, double[] closingPrices) {
		int period = days - 1;
		double sumLogs = 0;
		double sumLogSquare = 0;

		for (int i = 0; i < period; i++) {
			double sumLog = Math.log(closingPrices[i] / closingPrices[i + 1]);
			sumLogs += sumLog;
			sumLogSquare += (sumLog * sumLog);
		}
		double hVol = Math.sqrt((sumLogSquare / period) - ((sumLogs / period) * (sumLogs / period))) * Math.sqrt(252) * 100;
		return MathUtil.round(hVol);
	} 
	
	private static double[] reverse(double[] inArray) {
		double[] array = new double[inArray.length];
	    System.arraycopy( inArray, 0, array, 0, inArray.length );

		int i = 0;
		int j = array.length - 1;
		double tmp;
		while (j > i) {
			tmp = array[j];
			array[j] = array[i];
			array[i] = tmp;
			j--;
			i++;
		}
		return array;
	}
	
	public static double SAR(double[] inHigh, double[] inLow, double afInc,double afMax) {
		/**
		 * Computes the Parabolic SAR, as originally developed by Welles Wilder.
		 *
		 * @param array high Extreme Points: The highest high
		 * @param array low Extreme Points: The lowest low
		 * @param float AFInc Acceleration Factor Increment, for each time point
		 *   Lower increment = less sensitive (indicitive)
		 * @param float AFMax Acceleration Factor Max, rno matter how long the trend
		 *   Lower max step = less sensitive (reactive)
		 * @param bool withDir Indicates if trend direction should be returned.
		 * @return array 
		 *  If withDir is true, result is tuple of length 2.
		 *    Tuple contains ( array of SAR values, array of trend direction )
		 *  Else if withDir is false, result is simply an array of SAR values.
		 */
	     
		 double[] high = reverse( inHigh );
		 double[] low   = reverse( inLow );
		 // Initialize trend 
		 int trend = (high[1]>=high[0] || low[0]<=low[1]) ? 1 : -1;
		 
		 // Previous SAR: Use first data point's extreme value, depending on trend
		 double pSAR = (trend>0) ? low[0] : high[0];
		 double nSAR=0;
		 // Extreme point: Highest during uptrend || lowest during downtrend
		 double EP = (trend>0) ? high[0] : low[0];
		 
		 // Acceleration factor
		 double AF = afInc;
		 
//		 // Initialize results based on trend guess
//		 double r = array(keys[0] => pSAR);   // SAR Results
//		 d = array(keys[0] => trend);  // Trend Directions
		 
		 // Compute "tomorrow" SAR
		 for(int i=1; i< high.length-1; i++) {
		   // Do for uptrend
		   if( trend > 0 ) { 
		 
		     // Making higher highs: accelerate
		     if( high[i] > EP ) {
		       EP = high[i];
		       AF = min(afMax,AF+afInc);
		     }
		 
		     // Tomorrow';s SAR based on today's price action.
		     nSAR = pSAR + AF * (EP - pSAR);
		 
		     // Rule: SAR can never be above prior period's low or the current low.
		     nSAR = (i>0) ? min(low[i],low[i-1],nSAR) : min(low[i],nSAR);
		 
		     // Rule: If SAR crosses tomorrow';s price range, the trend switches.
		     if( nSAR > low[i+1] ) { 
		       trend = -1;
		       nSAR = EP;      // set to the last EP recorded on the previous trend
		       EP = low[i+1]; // reset accordingly to thigh period';s maximum
		       AF = afInc;     // reset to its initial value of 0.02.
		     }
		 
		   } else {
		   // Do for downtrend
		 
		     // Making lower lows: accelerate
		     if( low[i] < EP ) { 
		       EP = low[i];
		       AF = min(afMax,AF+afInc);
		     }
		 
		     // Tomorrow';s SAR based on today';s price action.
		     nSAR = pSAR + AF * (EP - pSAR);
		 
		     // Rule: SAR can never be below prior period's highs or the current high.
		     nSAR = (i>0) ? max(high[i],high[i-1],nSAR) : max(high[i],nSAR);
		 
		     // Rule: If SAR crosses tomorrow's price range, the trend switches.
		     if( nSAR < high[i+1] ) {
		       trend = +1;
		       nSAR = EP;      // set to the last EP recorded on the previous trend
		       EP = high[i+1]; // reset accordingly to high period's maximum
		       AF = afInc;     // reset to its initial value of 0.02.
		     }
		 
		   } // end: if uptrend else downtrend
		 
		   
		   pSAR = nSAR;

		 } 
		 
		 return pSAR;
		} 
	
	

	private static double max(double a, double b, double c) {
		// TODO Auto-generated method stub
		return Math.max(Math.min(a, b), c);
	}

	private static double max(double a, double b) {
		return (a > b) ? a : b;
	}

	private static double min(double a, double b, double c) {
		return Math.min(Math.min(a, b), c);
	}

	private static double min(double a, double b) {
		return (a < b) ? a : b;

	}
	
	
}
