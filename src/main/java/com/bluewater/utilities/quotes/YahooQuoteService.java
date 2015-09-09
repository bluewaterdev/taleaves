/*
 * Created on Jun 17, 2004
 *

 */
package com.bluewater.utilities.quotes;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

/**
 * @author jc
 * Get historical quotes for date range from Yahoo
 */
public class YahooQuoteService extends AbstractQuoteService  {
   
    /**
     * sets url to get last quote for symbol. this is used to get todays quote data ( use historical format for older data )
     */
   
   
	public List<QuoteDataDTO> getWeeklyHistoricalData( String symbol, java.util.Date startdate, java.util.Date enddate ) throws Exception {
		String quoteurl = getQuoteUrl( QuoteParameters.YAHOO_WEEKLY_PRICE_HISTORY, symbol,startdate, enddate ) ;
		return loadQuoteDataList(quoteurl);
	}
	
	public String fetchQuoteData(String url) throws Exception {
	    URL urlobj =new URL(url);
		 
        URLConnection urlconn =urlobj.openConnection();
        
        Authenticator.setDefault(new MyAuthenticator());
/*
        Properties properties = System.getProperties();
        properties.put("http.proxyHost", "proxy.medplus.com/proxy.pac");
        properties.put("http.proxyPort", "80");
        
        String login="comer_j:Password04";
		String encodedLogin = new BASE64Encoder().encode(login.getBytes());
        urlconn.setRequestProperty("Proxy-Authorization", "Basic " + encodedLogin);*/

        HttpURLConnection httpCon = null;
        StringWriter sw = new StringWriter();
        
        if (urlconn instanceof HttpURLConnection)
        {
			httpCon = (HttpURLConnection) urlconn;
			httpCon.setRequestMethod("GET");
			httpCon.setUseCaches(false);

			// Open a connection with the server
			httpCon.connect();

			// Get the InputStream, which completes the request
			try {
				InputStream response = httpCon.getInputStream();
				// Save output to string

				byte b;
				while ((b = (byte) response.read()) != -1)
					sw.write(b);

			} catch (IOException ioe) {
               // System.out.println("Exception getting quote data: response code " + httpCon.getResponseCode());
			   //  System.out.println("url:" +url );
			    return "1900-01-01,0.00,0.00,0.00,0.00,0,0.00";
			}
		}
      
		return sw.toString();
	}
 
	static class MyAuthenticator extends Authenticator {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("comer_j", "Password04".toCharArray());
		}
	}

	@Override
	public QuoteDataDTO getQuote(String symbol) throws Exception {
		String quoteurl = getQuoteUrl( QuoteParameters.YAHOO_LAST_QUOTE, symbol );
		List<QuoteDataDTO> l = loadQuoteDataList(quoteurl);
		return (QuoteDataDTO)l.get(0);
	}


	private List<QuoteDataDTO> loadQuoteDataList( String quoteurl ) throws Exception {
		String[] csvData = fetchQuoteData( quoteurl).split("\\n");
		List<QuoteDataDTO> l = parseCSVDataString( csvData);
		return l;
	}


	@Override
	public QuoteDataDTO getQuote(String symbol, Date date) throws Exception {
		String quoteurl = getQuoteUrl( QuoteParameters.YAHOO_PRICE_HISTORY, symbol, date, date );
		List<QuoteDataDTO> l = loadQuoteDataList(quoteurl);
		return (QuoteDataDTO)l.get(0);
	}


	@Override
	public List<QuoteDataDTO> getQuote(String symbol, Date startDate, Date endDate) throws Exception {
		String quoteurl = getQuoteUrl( QuoteParameters.YAHOO_PRICE_HISTORY, symbol, startDate, endDate );
		return loadQuoteDataList(quoteurl);
	}
    

//Date,Open,High,Low,Close,Volume,Adj. Close
}
