package com.big.web.b2b_big2.util;

import java.io.Serializable;

public class AppInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7907405639494070042L;
	private String label;
	private String value;
	
	
	public AppInfo(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}

	public AppInfo(String label, int value) {
		super();
		this.label = label;
		this.value = String.valueOf(value);
	}

	public AppInfo(String label, long value) {
		super();
		this.label = label;
		this.value = String.valueOf(value);
	}

	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "AppInfo [label=" + label + ", value=" + value + "]";
	}
	
}
