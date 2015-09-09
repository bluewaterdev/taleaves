package com.bluewater.utilities.quotes;

import java.io.Serializable;
import java.util.Date;

// non database dto
public class QuoteDataDTO implements Serializable {
	
	private String symbol;
	private Date dataDate;
	private Double openPrice;
	private Double highPrice;
	private Double lowPrice;
	private Double closePrice;
	private Double adjClosePrice;
	private Double volume;

	private static final long serialVersionUID = 1L;
	

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Date getDataDate() {
		return dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public Double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}

	public Double getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(Double highPrice) {
		this.highPrice = highPrice;
	}

	public Double getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Double lowPrice) {
		this.lowPrice = lowPrice;
	}

	public Double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(Double closePrice) {
		this.closePrice = closePrice;
	}

	public Double getAdjClosePrice() {
		return adjClosePrice;
	}

	public void setAdjClosePrice(Double adjClosePrice) {
		this.adjClosePrice = adjClosePrice;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

}
