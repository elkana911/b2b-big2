package com.big.web.b2b_big2.flight.api.kalstar.action.schedule;

import java.util.List;

public class ResponseSchedule {
	private String err_code;
	private String err_msg;
	private String org;
	private String des;
	private String flight_date;
	private Double extra_days;
	
	private String ret_flight_date;
	
	private List<List<?>> schedule;
	private List<List<?>> ret_schedule;
	
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_msg() {
		return err_msg;
	}
	public void setErr_msg(String err_msg) {
		this.err_msg = err_msg;
	}
	public List<List<?>> getSchedule() {
		return schedule;
	}
	public void setSchedule(List<List<?>> schedule) {
		this.schedule = schedule;
	}
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
	public Double getExtra_days() {
		return extra_days;
	}
	public void setExtra_days(Double extra_days) {
		this.extra_days = extra_days;
	}
	public String getRet_flight_date() {
		return ret_flight_date;
	}
	public void setRet_flight_date(String ret_flight_date) {
		this.ret_flight_date = ret_flight_date;
	}
	public List<List<?>> getRet_schedule() {
		return ret_schedule;
	}
	public void setRet_schedule(List<List<?>> ret_schedule) {
		this.ret_schedule = ret_schedule;
	}
	
	
}
