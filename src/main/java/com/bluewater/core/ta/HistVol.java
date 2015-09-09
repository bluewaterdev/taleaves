package com.bluewater.core.ta;

import com.bluewater.utilities.math.MathUtil;

public class HistVol {

	/*HISTORICAL VOLATILITY (20-day):

		STEP 1
		For the past 20 days, calculate: today's close / previous close (requires 21 days of data)

		STEP 2
		Calculate the natural log of the results calculated in STEP 1.

		STEP 3
		Calculate the sum of the natural logs over the past 20 days.
		Calculate the sum of the squares of the natural logs over the past 20 days.

		STEP 4
		Divide the sum of the natural logs by 20.......................#1
		Divide the sum of the squares of the natural logs by 20........#2
		Calculate: RESULT 2 - the square of RESULT 1...................#3
		Calculate the (square root of RESULT 3) x (sq. root of 252) x 100
		This is the 20-day historic volatility for today. */
	
	public static void main(String[] args) {
		double closingPrices[] = { 306.73, 308.43, 300.5, 301.59, 307.04,
				308.03, 316.65, 318.03, 316.08, 318.62, 317.13, 318.27, 312.8,
				309.36, 304.18, 300.98, 305.24, 307.83, 308.05, 308.84, 307.47,
				309.52, 310.53, 309.49, 318, 314.74, 302.31, 300.14, 298.54,
				295.36, };

		double hVol = calcHistoricalVolatility(30, closingPrices);
		System.out.println( hVol);
		
	}
   /**
    * 
    * @param days           day range to calculate volatility ( eg 10, 20, 30 )
    * @param closingPrices  closing prices, first element is todays adj close. 
    * @return               historical volatility
    */
	private static double calcHistoricalVolatility(int days, double[] closingPrices) {
		int period = days-1;
		double sumLogs=0;
		double sumLogSquare=0;
		
		for (int i = 0; i < period; i++){
			double sumLog = Math.log(closingPrices[i] / closingPrices[i + 1]);
			sumLogs+= sumLog;
			sumLogSquare += (sumLog*sumLog);
		}
        double hVol = Math.sqrt((sumLogSquare/period) - (( sumLogs/period)*( sumLogs/period))) * Math.sqrt(252) * 100;
		return MathUtil.round(hVol);
	} 
	
}
