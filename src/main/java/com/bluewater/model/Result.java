package com.bluewater.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "result")
public class Result {
	private double value;

	public Result() {
		
	}
	public Result(double _value) {
		this.value = _value;
	}

	public double getValue() {
		return value;
	}
	@XmlElement
	public void setValue(double value) {
		this.value = value;
	}

}
