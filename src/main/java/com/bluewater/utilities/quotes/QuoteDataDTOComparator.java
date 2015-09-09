package com.bluewater.utilities.quotes;

import java.util.Comparator;
import java.util.Date;

import com.bluewater.utilities.date.JavaDate;



public class QuoteDataDTOComparator implements Comparator<QuoteDataDTO>{

	public int compare(QuoteDataDTO dto1, QuoteDataDTO dto2) {
		Date d1=dto1.getDataDate();
		Date d2=dto2.getDataDate();
		
		if (JavaDate.isSameDate(d1, d2))
			return 0;
		else
		    return d1.compareTo(d2);
	}
	

}
