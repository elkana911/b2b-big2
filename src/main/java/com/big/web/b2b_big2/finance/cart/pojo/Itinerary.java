package com.big.web.b2b_big2.finance.cart.pojo;

import com.big.web.b2b_big2.flight.pojo.Airport;

public class Itinerary extends Item{

	private Airport depart;
	private Airport arrival;
	private char seatClass;
	
	public Itinerary() {
		super();
	}

	
	public Airport getDepart() {
		return depart;
	}


	public void setDepart(Airport depart) {
		this.depart = depart;
	}


	public Airport getArrival() {
		return arrival;
	}


	public void setArrival(Airport arrival) {
		this.arrival = arrival;
	}


	public char getSeatClass() {
		return seatClass;
	}

	public void setSeatClass(char seatClass) {
		this.seatClass = seatClass;
	}
	
	
}
