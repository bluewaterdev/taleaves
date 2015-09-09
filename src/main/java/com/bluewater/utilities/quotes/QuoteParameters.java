package com.bluewater.utilities.quotes;

/**
 * @author Jeff Comer
 *
 */
public interface QuoteParameters {
	public static String SYMBOL_PLACEHOLDER="STOCKSYMBOL";
	public static String SD ="SD"; //start day
	public static String SM ="SM"; //start month
	public static String SY ="SY"; // start year
	public static String ED ="ED"; // end day
	public static String EM ="EM"; // end month
	public static String EY ="EY"; //end year
	public static String YAHOO_RT_QUOTE="http://quote.yahoo.com/d/quotes.csv?s="+SYMBOL_PLACEHOLDER+"&f=st5l9c6p4b1a3&e=.csv";
    public static String YAHOO_PRICE_HISTORY="http://ichart.finance.yahoo.com/table.csv?s="+SYMBOL_PLACEHOLDER+"&d="+EM+"&e="+ED+"&f="+EY+"&g=d&a="+SM+"&b="+SD+"&c="+SY+"&ignore=.csv";
    public static String YAHOO_WEEKLY_PRICE_HISTORY="http://ichart.finance.yahoo.com/table.csv?s="+SYMBOL_PLACEHOLDER+"&a="+SM+"&b="+SD+"&c="+SY+"&d="+EM+"&e="+ED+"&f="+EY+"&g=w&ignore=.csv";
    public static String YAHOO_LAST_QUOTE="http://finance.yahoo.com/d/quotes.csv?s="+SYMBOL_PLACEHOLDER+"&f=d1ohgl1vl1&e=.csv";

}
