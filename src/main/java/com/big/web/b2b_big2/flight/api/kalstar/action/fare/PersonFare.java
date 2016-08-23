package com.big.web.b2b_big2.flight.api.kalstar.action.fare;

import java.io.Serializable;

public class PersonFare implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int totalFare;
	private int basicFare;
	private int insurance;
	private int airportTax;
	private int surcharge;
	private int terminalFee;
	private int bookingFee;
	private int vat;	//tax
	public int getTotalFare() {
		return totalFare;
	}
	public void setTotalFare(int totalFare) {
		this.totalFare = totalFare;
	}
	public int getBasicFare() {
		return basicFare;
	}
	public void setBasicFare(int basicFare) {
		this.basicFare = basicFare;
	}
	public int getInsurance() {
		return insurance;
	}
	public void setInsurance(int insurance) {
		this.insurance = insurance;
	}
	public int getAirportTax() {
		return airportTax;
	}
	public void setAirportTax(int airportTax) {
		this.airportTax = airportTax;
	}
	public int getSurcharge() {
		return surcharge;
	}
	public void setSurcharge(int surcharge) {
		this.surcharge = surcharge;
	}
	public int getTerminalFee() {
		return terminalFee;
	}
	public void setTerminalFee(int terminalFee) {
		this.terminalFee = terminalFee;
	}
	public int getBookingFee() {
		return bookingFee;
	}
	public void setBookingFee(int bookingFee) {
		this.bookingFee = bookingFee;
	}
	public int getVat() {
		return vat;
	}
	public void setVat(int vat) {
		this.vat = vat;
	}
	@Override
	public String toString() {
		return "PersonFare [totalFare=" + totalFare + ", basicFare=" + basicFare + ", insurance=" + insurance
				+ ", airportTax=" + airportTax + ", surcharge=" + surcharge + ", terminalFee=" + terminalFee
				+ ", bookingFee=" + bookingFee + ", vat=" + vat + "]";
	}
	
	
	
}
