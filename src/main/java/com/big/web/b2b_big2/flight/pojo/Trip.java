package com.big.web.b2b_big2.flight.pojo;

import java.util.Date;

/**
 * @deprecated 
 * @see Route
 * @author Administrator
 *
 */
public class Trip {
	private Airport airport;
	private Date time;
	private String flightNo;
	
	public Airport getAirport() {
		return airport;
	}
	public void setAirport(Airport airport) {
		this.airport = airport;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	
}
