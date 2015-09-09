package com.bluewater.utilies.screener;

import java.util.Date;
import java.util.List;

import com.bluewater.core.ta.TALib;
import com.bluewater.utilities.date.JavaDate;
import com.bluewater.utilities.quotes.QuoteDataDTO;
import com.bluewater.utilities.quotes.QuoteServiceFactory;
import com.bluewater.utilities.quotes.QuoteServiceIF;
import com.bluewater.utilities.quotes.QuoteServiceTypes;

public class SarAdxScreener {

	/**
	 * @param args
	 */
	
	private static Date currentDay = JavaDate.getNearestWeekDay(JavaDate.getDateNDaysBeforeDate(new Date(), 1));
	private static Date priorDay  = JavaDate.getNearestWeekDay(JavaDate.getDateNDaysBeforeDate(currentDay, 1));
	private static int numPeriods = 400;
	
	public enum SIGNAL  { BUY, SELL, NONE }
	
	public enum NAS100{
		ATVI, ADBE, ALTR, AMZN, APCC, AMGN, APOL, AAPL, AMAT, ATYT, ADSK, BEAS, BBBY, BIIB, BMET, BRCM, CDNS, CDWC, CELG, CHRW, CHKP, CKFR, CHIR, CTAS, CSCO, CTXS, CTSH, CMCSA, CMVT, COST, DELL, XRAY, DISCA, EBAY, DISH, ERTS, EXPE, EXPD, ESRX, FAST, FISV, FLEX, GRMN, GENZ, GILD, GOOG, IACI, INTC, INTU, JDSU, JNPR, KLAC, LRCX, LAMR, LBTYA, LNCR, LLTC, ERICY, MRVL, MXIM, MCIP, MEDI, MERQ, MCHP, MSFT, MNST, NTAP, NIHD, NVLS, NTLI, NVDA, ORCL, PCAR, PDCO, PTEN, PAYX, PETM, PIXR, QCOM, RHAT, RIMM, ROST, SNDK, SHLD, SEPR, SIRI, SPLS, SBUX, SUNW, SYMC, TLAB, TEVA, URBN, VRSN, WFMI, WYNN, XLNX, XMSR, YHOO
	}

	public enum ListA{AAPL, GOOG, BIDU, LNKD, PCLN, NFLX, EBAY, AMZN }
	public enum ListB { AAPL, ABC, ACT, ADP, AET, AFL, AGCO, AGG, AGN, ALXN, AMGN, 
		 AMP, AMT, AMZN, ANF, AON, APA, APC, APD, ASH, AXP, BA, BAX, BBBY, BDX, 
		 BEAV, BG, BIIB, BIDU, BMRN, BND, BXP, C, CAM, CAT, CB, CBS, CCI, CELG, CF, 
		 CHRW, CHTR, CI, CL, CLR, CME, CMI, COF, COG, COH, COP, COST, CREE, CTSH, 
		 CTXS, CVS, CVX, CXO, D, DD, DE, DECK, DG, DGX, DHR, DIA, DIS, DISCA, 
		 DKS, DLR, DLTR, DO, DOV, DRI, DTE, DTV, DUK, DVN, DVY, EBAY, ECL, ED, 
		 EFA, EL, EMN, EMR, EOG, EPD, EQR, EQT, ESRX, ETN, ETP, ETR, EW, EWW, 
		 EWY, EWZ, EXP, EXPE, FAS, FAST, FDO, FDX, FFIV, FLR, FSLR, FTI, GD, GDI, 
		 GILD, GLD, GMCR, GOOG, GS, HCN, HCP, HD, HES, HNZ, HOG, HON, HOT, HP, 
		 HSY, HUM, HYG, IACI, IBM, ILMN, INTU, ITW, IVV, IWD, IWF, IWM, IWN, IYR, 
		 JEC, JNJ, JOY, JPM, JWN, K, KLAC, KMB, KMP, KRFT, KSS, LBTYA, LEA, LIFE, 
		 LLY, LMT, LNKD, LQD, LTD, LUFK, LVS, MAC, MCD, MCK, MCO, MDT, MDY, MJN, 
		 MMM, MNST, MON, MOS, MPC, MSI, MUR, NBL, NEE, NFLX, NKE, NOC, NOV, NSC, 
		 NTRS, NUS, O, OIS, OMC, ONXX, OXY, PANW, PCAR, PCLN, PEP, PETM, PG, PH, PM, 
		 PNC, PNR, PPG, PRU, PSX, PVH, PX, PXD, QCOM, QLD, QQQ, REGN, RLGY, RMD, 
		 ROST, RRC, RTN, SBAC, SBUX, SDY, SLB, SM, SNDK, SPG, SPY, SRE, SSO, STT, 
		 STZ, SWK, SYK, TAP, TBT, TDC, TGT, TIF, TIP, TJX, TLT, TMO, TQQQ, TRIP, 
		 TROW, TRV, TRW, TSLA, TSO, TWC, TWX, UA, ULTA, UNH, UNP, UPRO, UPS, URI, 
		 UTX, V, VGK, VIAB, VIG, VMW, VNO, VNQ, VOO, VRTX, VTI, VTR, VZ, WAG, 
		 WDAY, WDC, WFM, WHR, WLP, WMT, WPZ, WSM, WYN, WYNN, XLE, XLY, XOM, XOP, 
		 XRT, YUM, ZMH }; 


	public static void main(String[] args) throws Exception {
		SIGNAL today, yesterday;
		System.out.println("current day " +  currentDay.toString());
		System.out.println("prev day " +  priorDay.toString());
		for (ListB ticker : ListB.values()) {
			today = checkForSignal(ticker.toString(), currentDay);
			yesterday = checkForSignal(ticker.toString(), priorDay);
			if ( today != SIGNAL.NONE) {
				if ( yesterday == SIGNAL.NONE )
				System.out.println( today + " , " + ticker);
			}
			
			
		}
	
	}

	private static SIGNAL checkForSignal(String symbol, Date endDate)
			throws Exception {

		double[] adjClose;
		double[] close;
		double[] highs;
		double[] lows;

		Date startDate = JavaDate.getDateNDaysBeforeToday(numPeriods);
		QuoteServiceFactory quoteServiceFactory = new QuoteServiceFactory();

		QuoteServiceIF quoteService = quoteServiceFactory
				.getQuoteService(QuoteServiceTypes.YAHOO_QUOTE);
		List<QuoteDataDTO> data = quoteService.getQuote(symbol, startDate,
				endDate);

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
		if (adjClose.length == 0)
			return SIGNAL.NONE;
		
		double closing = adjClose[0];
		double adx = TALib.ADX(adjClose, highs, lows, 14);
		double sma50 = TALib.SMA(adjClose, 50);
		double sar = TALib.SAR(highs, lows, 0.02, 0.2);
		double[] DMs = TALib.ADX_DIS(adjClose, highs, lows, 14);
		double dmPlus = DMs[0];
		double dmMinus = DMs[1];
       
        boolean above50 = closing > sma50;
		boolean below50 = closing < sma50;
		if ((above50) && (dmPlus > dmMinus) && (adx > dmMinus))
			return SIGNAL.BUY;

		if ((below50) && (dmPlus < dmMinus) && (adx > dmPlus))
			return SIGNAL.SELL;
		return SIGNAL.NONE;

	}
	

}
