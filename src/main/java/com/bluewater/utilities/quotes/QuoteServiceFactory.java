package com.bluewater.utilities.quotes;

public class QuoteServiceFactory {

   public QuoteServiceIF getQuoteService(QuoteServiceTypes quoteServiceType ){
	   switch ( quoteServiceType ) {
	   case YAHOO_QUOTE:
		   return new YahooQuoteService();
	   }
	   return null;
   }
}
