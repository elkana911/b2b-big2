package com.big.web.b2b_big2.report.pojo;

import java.util.Date;

public class Revenue {
	private String id;
	
	private String airline;
	private String bookingCode;
	private Date dateIssued;
	private String commFare;
	private String commInsurance;
	private String feeService;
	private String total;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	public String getBookingCode() {
		return bookingCode;
	}
	public void setBookingCode(String bookingCode) {
		this.bookingCode = bookingCode;
	}
	public Date getDateIssued() {
		return dateIssued;
	}
	public void setDateIssued(Date dateIssued) {
		this.dateIssued = dateIssued;
	}
	public String getCommFare() {
		return commFare;
	}
	public void setCommFare(String commFare) {
		this.commFare = commFare;
	}
	public String getCommInsurance() {
		return commInsurance;
	}
	public void setCommInsurance(String commInsurance) {
		this.commInsurance = commInsurance;
	}
	public String getFeeService() {
		return feeService;
	}
	public void setFeeService(String feeService) {
		this.feeService = feeService;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	
}
