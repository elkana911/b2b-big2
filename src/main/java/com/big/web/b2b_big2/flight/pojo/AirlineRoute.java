package com.big.web.b2b_big2.flight.pojo;

import java.io.Serializable;

import com.big.web.b2b_big2.flight.Airline;

public class AirlineRoute implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Airline airline;
	private Route route;
	
	public AirlineRoute(Airline airline, Route route) {
		super();
		this.airline = airline;
		this.route = route;
	}
	
	public Airline getAirline() {
		return airline;
	}
	public void setAirline(Airline airline) {
		this.airline = airline;
	}
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	
	@Override
	public String toString() {
		return "AirlineRoute [airline=" + airline + ", route=" + route + "]";
	}
	

}
