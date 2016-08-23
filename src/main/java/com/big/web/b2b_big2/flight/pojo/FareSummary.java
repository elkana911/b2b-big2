package com.big.web.b2b_big2.flight.pojo;

import java.io.Serializable;
import java.util.List;

import com.big.web.b2b_big2.booking.model.BookingFlightTransVO;

public class FareSummary implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4829418537010444607L;
	private String departDate;
	private String destinationCity;
	private List<FareInfo> fareInfo;

	private String psc;
	private String tax;
	private String iwjr;
	private String surcharge;
	private String total;
	public String getDepartDate() {
		return departDate;
	}
	public void setDepartDate(String departDate) {
		this.departDate = departDate;
	}
	
	/**
	 * Fare per flight
	 * @return
	 */
	public List<FareInfo> getFareInfo() {
		return fareInfo;
	}
	public void setFareInfo(List<FareInfo> fareInfo) {
		this.fareInfo = fareInfo;
	}
	public String getPsc() {
		return psc;
	}
	public void setPsc(String psc) {
		this.psc = psc;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	
	public String getIwjr() {
		return iwjr;
	}
	public void setIwjr(String iwjr) {
		this.iwjr = iwjr;
	}
	public String getSurcharge() {
		return surcharge;
	}
	public void setSurcharge(String surcharge) {
		this.surcharge = surcharge;
	}

	@Override
	public String toString() {
		return "FareSummary [departDate=" + departDate + ", destinationCity="
				+ destinationCity + ", fareInfo=" + fareInfo + ", psc=" + psc
				+ ", tax=" + tax + ", iwjr=" + iwjr + ", surcharge="
				+ surcharge + ", total=" + total + "]";
	}
	
}
