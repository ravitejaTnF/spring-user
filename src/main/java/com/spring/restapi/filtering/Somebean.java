package com.spring.restapi.filtering;

import com.fasterxml.jackson.annotation.JsonFilter;

//@JsonIgnoreProperties({"val2"})
@JsonFilter("SomeBeanFilter")
public class Somebean {
	
//	@JsonIgnore
	private String val1;
	private String val2;
	private String val3;
	
	public Somebean() {};
	
	public Somebean(String val1, String val2, String val3) {
		super();
		this.val1 = val1;
		this.val2 = val2;
		this.val3 = val3;
	}

	public String getVal1() {
		return val1;
	}

	public String getVal2() {
		return val2;
	}

	public String getVal3() {
		return val3;
	}

	@Override
	public String toString() {
		return "Somebean [val1=" + val1 + ", val2=" + val2 + ", val3=" + val3 + "]";
	}
	
}
