package com.bluewater.utilities.quotes;

import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bluewater.utilities.date.JavaDate;


/**
 * @author Jeff Comer
 *
 */
public class YahooQuote  {
    private Date date;
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;
    private double adjClose;
    
    
	/**
	 * Constructor for YahooQuoteService.
	 */
	public YahooQuote( String quoteresult )  {
		super();
		parseResults( quoteresult ) ;
	}
	
    /**
     * parses CSV results from GET to Yahoo and calls appropriate setters
     * 
     */
     
    private void parseResults( String s ) {
        String[] rows = (s.split("\n"));
    	//Date 	Open 	High 	Low 	Close 	Volume 	Adj Close*
        StringTokenizer st = new StringTokenizer( (s.split("\n"))[rows.length-1], ",", false );
        String origDateString = st.nextToken();
        String datestring = padDateString(replaceQuotes(origDateString));
        s.replaceFirst(origDateString, datestring);
        this.date = JavaDate.getDate( datestring );
        this.open = new Double(st.nextToken()).doubleValue();
        this.high = new Double(st.nextToken()).doubleValue();
        this.low = new Double(st.nextToken()).doubleValue();
        this.close = new Double(st.nextToken()).doubleValue();
        this.volume = new Double(st.nextToken()).doubleValue();
        this.adjClose = new Double(st.nextToken()).doubleValue();
    }

    public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getAdjClose() {
		return adjClose;
	}

	public void setAdjClose(double adjClose) {
		this.adjClose = adjClose;
	}

	private static String replaceQuotes(String temp) {
    	Pattern p = null;
    	Matcher m = null;
    	p = Pattern.compile("\"");
    	m = p.matcher(temp);
    	temp = m.replaceAll("");
    	m.reset();
    	return temp;
    }
    
    private static String padDateString( String d ) {
       	if ( d.indexOf("/") > 0  ) {
       		String[] items = d.split("/");
       		if ( items[0].length() == 1 )
       	       return "0"+d;	
       	}
       	if ( d.indexOf("-") > 0 ) {
       		String[] items = d.split("-");
       		if ( items[0].length() == 1 )
        	       return "0"+d;	
       	}
       	return d;
       }
}
