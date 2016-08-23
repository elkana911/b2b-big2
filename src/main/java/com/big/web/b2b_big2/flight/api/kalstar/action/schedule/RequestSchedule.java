package com.big.web.b2b_big2.flight.api.kalstar.action.schedule;

import java.util.Date;

public class RequestSchedule {
	
	private String originIata;
	private String destIata;
	private Date departDate;
	
	private int return_flight;
	private Date returnDate;
	
	public String getOriginIata() {
		return originIata;
	}
	public void setOriginIata(String originIata) {
		this.originIata = originIata;
	}
	public String getDestIata() {
		return destIata;
	}
	public void setDestIata(String destIata) {
		this.destIata = destIata;
	}
	public Date getDepartDate() {
		return departDate;
	}
	public void setDepartDate(Date departDate) {
		this.departDate = departDate;
	}
	
	public int getReturn_flight() {
		return return_flight;
	}
	public void setReturn_flight(int return_flight) {
		this.return_flight = return_flight;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	@Override
	public String toString() {
		return "RequestSchedule [originIata=" + originIata + ", destIata=" + destIata + ", departDate=" + departDate
				+ ", return_flight=" + return_flight + ", returnDate=" + returnDate + "]";
	}
	
}
