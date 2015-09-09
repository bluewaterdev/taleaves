package com.bluewater.controller;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewater.core.ta.TALib;
import com.bluewater.model.Result;
import com.bluewater.utilities.date.JavaDate;
import com.bluewater.utilities.math.MathUtil;
import com.bluewater.utilities.quotes.QuoteDataDTO;
import com.bluewater.utilities.quotes.QuoteServiceFactory;
import com.bluewater.utilities.quotes.QuoteServiceIF;
import com.bluewater.utilities.quotes.QuoteServiceTypes;
 
@Controller
@RequestMapping("/")
public class BaseController {
 
    private static double[] adjClose;
    private static double[] close;
    private static double[] highs;
    private static double[] lows;
    private static int daysBack = 400;
    
    
	@RequestMapping(value="/adx/{symbol}/{period}", method = RequestMethod.GET)
	public @ResponseBody Result adxIndicator(@PathVariable String symbol, @PathVariable Integer period ,ModelMap model) {
		try {
			loadQuoteData(symbol, daysBack);
			return new Result(MathUtil.round( TALib.ADX(adjClose, highs, lows, period)));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
				
		return null;
	}
	
	@RequestMapping(value="/plusDI/{symbol}/{period}", method = RequestMethod.GET)
	public @ResponseBody Result plusDIIndicator(@PathVariable String symbol, @PathVariable Integer period ,ModelMap model) {
		try {
			loadQuoteData(symbol, daysBack);
			double plusDI =TALib.ADX_DIS(adjClose, highs, lows, 14)[0];
			return new Result( MathUtil.round(plusDI));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
				
		return null;
	}
	
	@RequestMapping(value="/minusDI/{symbol}/{period}", method = RequestMethod.GET)
	public @ResponseBody Result minusDIIndicator(@PathVariable String symbol, @PathVariable Integer period ,ModelMap model) {
		try {
			loadQuoteData(symbol, daysBack);
			double minusDI =TALib.ADX_DIS(adjClose, highs, lows, 14)[1];
			return new Result( MathUtil.round(minusDI));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
				
		return null;
	}
	@RequestMapping(value="/rsi/{symbol}/{period}", method = RequestMethod.GET)
	public @ResponseBody Result rsiIndicator(@PathVariable String symbol, @PathVariable Integer period ,ModelMap model) {
		try {
			loadQuoteData(symbol, daysBack);
			return new Result(MathUtil.round(TALib.RSI(adjClose, period)));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
				
		return null;
 
	}
	
	@RequestMapping(value="/ema/{symbol}/{period}", method = RequestMethod.GET)
	public @ResponseBody Result emaIndicator(@PathVariable String symbol, @PathVariable Integer period ,ModelMap model) {
		try {
			loadQuoteData(symbol, daysBack);
			return new Result(MathUtil.round(TALib.EMA(adjClose, period)));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
				
		return null;
 
	}
	
	@RequestMapping(value="/sma/{symbol}/{period}", method = RequestMethod.GET)
	public @ResponseBody Result smaIndicator(@PathVariable String symbol, @PathVariable Integer period ,ModelMap model) {
		try {
			loadQuoteData(symbol, daysBack);
			return new Result(MathUtil.round(TALib.SMA(adjClose, period)));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
				
		return null;
 
	}
	@RequestMapping(value="/atr/{symbol}/{period}", method = RequestMethod.GET)
	public @ResponseBody Result atrIndicator(@PathVariable String symbol, @PathVariable Integer period ,ModelMap model) {
		try {
			loadQuoteData(symbol, daysBack);
			return new Result(MathUtil.round(TALib.ATR(adjClose, highs, lows, period)));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
				
		return null;
	}
	

	
	@RequestMapping(value="/macd/{symbol}/{p1}/{p2}/{p3}", method = RequestMethod.GET)
	public @ResponseBody Result macdIndicator(@PathVariable String symbol, @PathVariable Integer p1 ,@PathVariable Integer p2,@PathVariable Integer p3, ModelMap model) {
		try {
			loadQuoteData(symbol, daysBack);
			return new Result(MathUtil.round(TALib.MACD_SL(adjClose, p1, p2, p3)));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
				
		return null;
	}
	
	@RequestMapping(value="/macdHist/{symbol}/{p1}/{p2}/{p3}", method = RequestMethod.GET)
	public @ResponseBody Result macdHistIndicator(@PathVariable String symbol, @PathVariable Integer p1 ,@PathVariable Integer p2,@PathVariable Integer p3, ModelMap model) {
		try {
			loadQuoteData(symbol, daysBack);
			return new Result(MathUtil.round(TALib.MACD_HIST(adjClose, p1, p2, p3)));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
				
		return null;
	}
	
	@RequestMapping(value="/SAR/{symbol}/{p1}/{p2}", method = RequestMethod.GET)
	public @ResponseBody Result sarIndicator(@PathVariable String symbol, @PathVariable Double p1 ,@PathVariable Double p2,  ModelMap model) {
		try {
			loadQuoteData(symbol, daysBack);
			return new Result(MathUtil.round(TALib.SAR(highs, lows, p1, p2)));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
				
		return null;
	}
	/**
	 * SAR with default values for step and maxStep
	 * @param symbol
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/SAR/{symbol}", method = RequestMethod.GET)
	public @ResponseBody Result sarIndicator(@PathVariable String symbol,ModelMap model) {
		try {
			loadQuoteData(symbol, daysBack);
			return new Result(MathUtil.round(TALib.SAR(highs, lows, 0.02, 0.2)));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
				
		return null;
	}
	private static void loadQuoteData(String symbol, int _daysBack) throws Exception {

		Date endDate = new Date();
		Date startDate = JavaDate.getDateNDaysBeforeToday(_daysBack);
		QuoteServiceFactory quoteServiceFactory = new QuoteServiceFactory();

		QuoteServiceIF quoteService = quoteServiceFactory.getQuoteService(QuoteServiceTypes.YAHOO_QUOTE);
		List<QuoteDataDTO> data = quoteService.getQuote(symbol, startDate,endDate);
		adjClose = new double[data.size()];
		close = new double[data.size()];
		lows = new double[data.size()];
		highs = new double[data.size()];
		int i = 0;
		for (QuoteDataDTO dto : data) {
			adjClose[i] = dto.getAdjClosePrice();
			close[i] = dto.getClosePrice();
			lows[i] = dto.getLowPrice();
			highs[i] = dto.getHighPrice();
			i++;
		}

	}
}