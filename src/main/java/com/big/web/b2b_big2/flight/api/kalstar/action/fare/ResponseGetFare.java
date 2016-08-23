package com.big.web.b2b_big2.flight.api.kalstar.action.fare;

import java.util.List;

import com.big.web.b2b_big2.flight.api.kalstar.action.BasicResponse;

public class ResponseGetFare extends BasicResponse {
	
	private String org;
	private String des;
	private String flight_date;
	private String flight_no;
	private List<FareDetail> fare_info;
	
	private String ret_flight_date;
	private String ret_flight_no;
	private String ret_org;
	private String ret_des;
	private List<FareDetail> ret_fare_info;
	
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getFlight_date() {
		return flight_date;
	}
	public void setFlight_date(String flight_date) {
		this.flight_date = flight_date;
	}
	public String getFlight_no() {
		return flight_no;
	}
	public void setFlight_no(String flight_no) {
		this.flight_no = flight_no;
	}
	public List<FareDetail> getFare_info() {
		return fare_info;
	}
	public void setFare_info(List<FareDetail> fare_info) {
		this.fare_info = fare_info;
	}
	public String getRet_flight_date() {
		return ret_flight_date;
	}
	public void setRet_flight_date(String ret_flight_date) {
		this.ret_flight_date = ret_flight_date;
	}
	public String getRet_flight_no() {
		return ret_flight_no;
	}
	public void setRet_flight_no(String ret_flight_no) {
		this.ret_flight_no = ret_flight_no;
	}
	public String getRet_org() {
		return ret_org;
	}
	public void setRet_org(String ret_org) {
		this.ret_org = ret_org;
	}
	public String getRet_des() {
		return ret_des;
	}
	public void setRet_des(String ret_des) {
		this.ret_des = ret_des;
	}
	public List<FareDetail> getRet_fare_info() {
		return ret_fare_info;
	}
	public void setRet_fare_info(List<FareDetail> ret_fare_info) {
		this.ret_fare_info = ret_fare_info;
	}

	@Override
	public String toString() {
		return "ResponseGetFare [org=" + org + ", des=" + des + ", flight_date=" + flight_date + ", flight_no="
				+ flight_no + ", fare_info=" + fare_info + ", ret_flight_date=" + ret_flight_date + ", ret_flight_no="
				+ ret_flight_no + ", ret_org=" + ret_org + ", ret_des=" + ret_des + ", ret_fare_info=" + ret_fare_info
				+ ", getErrCode()=" + getErrCode() + ", getErrMsg()=" + getErrMsg() + "]";
	}

}
