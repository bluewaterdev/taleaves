package com.bluewater.utilities.quotes;

import java.util.Date;
import java.util.List;

/**
 * @author Jeff Comer
 *  QuoteDataIF defines methods for fetching quote data
 */
public interface QuoteServiceIF {

    // get last quote for symbol
	public QuoteDataDTO getQuote(String symbol) throws Exception;
	
	public QuoteDataDTO getQuote(String symbol, Date date) throws Exception;
	
	public List<QuoteDataDTO> getQuote(String symbol, Date startDate, Date endDate ) throws Exception;

	public List<QuoteDataDTO> getWeeklyHistoricalData(String symbol, Date startdate, Date enddate) throws Exception;

	

}
