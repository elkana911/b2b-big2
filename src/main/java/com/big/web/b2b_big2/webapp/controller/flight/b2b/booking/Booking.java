package com.big.web.b2b_big2.webapp.controller.flight.b2b.booking;

public class Booking {
	private String id;
	private String code;
	
	public Booking(String id, String code) {
		this.id = id;
		this.code = code;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
