package com.big.web.b2b_big2.flight.pojo;

public class OriginDestinationRaw {
	private String origin;	// Ahmedabad (AMD)
	private String destination;
	
	public OriginDestinationRaw(String origin, String destination) {
		super();
		this.origin = origin;
		this.destination = destination;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	
}
