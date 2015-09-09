package com.bluewater.core.ta;

public class TAValue {
	private String name;
	private double todaysValue;
	private double yesterdaysValue;
	private double twoDaysAgoValue;

	public TAValue(String label) {
		this.name = label;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTodaysValue() {
		return todaysValue;
	}

	public void setTodaysValue(double todaysValue) {
		this.todaysValue = todaysValue;
	}

	public double getYesterdaysValue() {
		return yesterdaysValue;
	}

	public void setYesterdaysValue(double yesterdaysValue) {
		this.yesterdaysValue = yesterdaysValue;
	}

	public double getTwoDaysAgoValue() {
		return twoDaysAgoValue;
	}

	public void setTwoDaysAgoValue(double twoDaysAgoValue) {
		this.twoDaysAgoValue = twoDaysAgoValue;
	}

}
