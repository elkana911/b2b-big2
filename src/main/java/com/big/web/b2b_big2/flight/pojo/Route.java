package com.big.web.b2b_big2.flight.pojo;

import java.io.Serializable;

/**
 * A small information about a trip
 * @author Administrator
 *
 */
public class Route implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5424652033344999429L;
	private Airport from;
	private Airport to;
	
	public Route(Airport from, Airport to) {
		this.from = from;
		this.to = to;
	}

	public Airport getFrom() {
		return from;
	}

	public void setFrom(Airport from) {
		this.from = from;
	}

	public Airport getTo() {
		return to;
	}

	public void setTo(Airport to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "Route [from=" + from + ", to=" + to + "]";
	}

}
