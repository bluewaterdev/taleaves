package com.bluewater.utilities.quotes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bluewater.utilities.date.JavaDate;


/**
 * @author Jeff Comer
 *
 */
public abstract class AbstractQuoteService implements QuoteServiceIF {

	private static final Object DEFAULT_DATA_DIR = "C:/projects/code/csreports/data";

    protected Properties properties;

	private Properties defaults;
    
	/**
	 * Constructor for QuoteDataService.
	 */
	public AbstractQuoteService() {
		super();
		defaults = new Properties();
		defaults.put("data.dir", DEFAULT_DATA_DIR);
		properties = new Properties(defaults);
	}
	
	public  String getQuoteUrl(String basestring, String symbol ) {
		 return ( basestring.replaceFirst( QuoteParameters.SYMBOL_PLACEHOLDER, encodeQuoteSymbol(symbol) ) );
	}
	
	public  String getQuoteUrl(String basestring, String symbol, java.util.Date startdate, java.util.Date enddate ) {
         String url="";
         url=  basestring.replaceFirst( QuoteParameters.SYMBOL_PLACEHOLDER, encodeQuoteSymbol(symbol) );
		 url = url.replaceFirst(QuoteParameters.ED,JavaDate.getDayFromDate( enddate ));
		 url = url.replaceFirst(QuoteParameters.EM,JavaDate.getMonthFromDate( enddate ) );
		 url = url.replaceFirst(QuoteParameters.EY,JavaDate.getYearFromDate( enddate ) );
		 url = url.replaceFirst(QuoteParameters.SD,JavaDate.getDayFromDate( startdate ) );
		 url = url.replaceFirst(QuoteParameters.SM,JavaDate.getMonthFromDate( startdate ) );
		 url = url.replaceFirst(QuoteParameters.SY,JavaDate.getYearFromDate( startdate ) );
	
		 return url;
	}
    
    private String encodeQuoteSymbol( String symbol ) {
        try {
            String encodedSymbol = URLEncoder.encode( symbol, "UTF-8" );
            return encodedSymbol;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
       
    }

    protected List<QuoteDataDTO> parseCSVDataString( String[] csvData ) {
    	
    	ArrayList<QuoteDataDTO> l = new ArrayList<QuoteDataDTO>();
    	int i=0;
		for (String csv:csvData){
			if (i++ > 0 ) 
			   l.add( parseResults( csv ));
			
		}
		return l;
    }
    
    protected QuoteDataDTO parseResults( String s ) {
    	QuoteDataDTO dto = new QuoteDataDTO();
        String[] rows = (s.split("\n"));
    	//Date 	Open 	High 	Low 	Close 	Volume 	Adj Close*
        StringTokenizer st = new StringTokenizer( (s.split("\n"))[rows.length-1], ",", false );
        String origDateString = st.nextToken();
        String datestring = padDateString(replaceQuotes(origDateString));
        s.replaceFirst(origDateString, datestring);
        dto.setDataDate(JavaDate.getDate( datestring));
        Double openPrice = new Double(st.nextToken()).doubleValue();
        dto.setOpenPrice(openPrice);
        Double highPrice = new Double(st.nextToken()).doubleValue();
		dto.setHighPrice(highPrice);
        Double lowPrice = new Double(st.nextToken()).doubleValue();
        dto.setLowPrice(lowPrice);
        Double closePrice = new Double(st.nextToken()).doubleValue();
        dto.setClosePrice(closePrice);
        Double volume = new Double(st.nextToken()).doubleValue();
        dto.setVolume(volume);
        Double adjClosePrice = new Double(st.nextToken()).doubleValue();
        dto.setAdjClosePrice(adjClosePrice);
        
        return dto;
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
