package com.big.web.b2b_big2.flight.api.kalstar.action.booking;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.big.web.b2b_big2.flight.api.kalstar.action.IRequest;
import com.big.web.b2b_big2.flight.api.kalstar.exception.SqivaException;
import com.big.web.b2b_big2.flight.pojo.ContactCustomer;
import com.big.web.b2b_big2.flight.pojo.PNR;
import com.big.web.b2b_big2.util.Utils;

public class RequestBooking implements IRequest{
	private String org;
	private String des;
	private int round_trip;
	private String[] dep_flight_no;
	private Date dep_date;
	
	private String[] subclass_dep;

	private ContactCustomer customer;
	private List<PNR> adult;
	private List<PNR> child;
	private List<PNR> infant;
	
	/*
	private String contact_1;
	private String contact_2;
	private String contact_3;
	*/
	
	private Date ret_date;
	private String[] ret_flight_no;
	private String[] subclass_ret;
	
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
	public int getRound_trip() {
		return round_trip;
	}
	public void setRound_trip(int round_trip) {
		this.round_trip = round_trip;
	}
	public String[] getDep_flight_no() {
		return dep_flight_no;
	}
	
	public String getDep_flight_no_AsCSV(){
		if (dep_flight_no == null) return "";

		return StringUtils.arrayToDelimitedString(dep_flight_no, ",");
	}
	
	public void setDep_flight_no(String[] dep_flight_no) {
		//sqiva mewajibkan flightno hanya nomor saja
		for (int i = 0; i < dep_flight_no.length; i++){
			if (dep_flight_no[i].indexOf("-") > 0)
				dep_flight_no[i] = dep_flight_no[i].split("-", 2)[1]; 
		}
		
		this.dep_flight_no = dep_flight_no;
	}
	public Date getDep_date() {
		return dep_date;
	}
	public void setDep_date(Date dep_date) {
		this.dep_date = dep_date;
	}

	public ContactCustomer getCustomer() {
		return customer;
	}
	public void setCustomer(ContactCustomer customer) {
		this.customer = customer;
	}

	public List<PNR> getAdult() {
		return adult;
	}
	public void setAdult(List<PNR> adult) {
		this.adult = adult;
	}

	public List<PNR> getChild() {
		return child;
	}
	public void setChild(List<PNR> child) {
		this.child = child;
	}
	public List<PNR> getInfant() {
		return infant;
	}
	public void setInfant(List<PNR> infant) {
		this.infant = infant;
	}
	
	//a char of seat class, ex: Y
	public String[] getSubclass_dep() {
		return subclass_dep;
	}

	public String getSubclass_dep_AsCSV() {
		if (subclass_dep == null) return "";

		return StringUtils.arrayToDelimitedString(subclass_dep, ",");

	}
	
	public void setSubclass_dep(String[] subclass_dep) {
		this.subclass_dep = subclass_dep;
	}
	
	public Date getRet_date() {
		return ret_date;
	}
	
	public void setRet_date(Date ret_date) {
		this.ret_date = ret_date;
	}
	
	public String[] getRet_flight_no() {
		return ret_flight_no;
	}

	public String getRet_flight_no_AsCSV() {
		if (ret_flight_no == null) return "";

		return StringUtils.arrayToDelimitedString(ret_flight_no, ",");
	}
	
	public void setRet_flight_no(String[] ret_flight_no) {
		this.ret_flight_no = ret_flight_no;
	}

	public String[] getSubclass_ret() {
		return subclass_ret;
	}

	public String getSubclass_ret_AsCSV() {
		if (subclass_ret == null) return "";

		return StringUtils.arrayToDelimitedString(subclass_ret, ",");
	}
	public void setSubclass_ret(String[] subclass_ret) {
		this.subclass_ret = subclass_ret;
	}
	
	@Override
	public String toString() {
		return "RequestBooking [org=" + org + ", des=" + des + ", round_trip=" + round_trip + ", dep_flight_no="
				+ Arrays.toString(dep_flight_no) + ", dep_date=" + dep_date + ", subclass_dep="
				+ Arrays.toString(subclass_dep) + ", customer=" + customer + ", adult=" + adult + ", child=" + child
				+ ", infant=" + infant + ", ret_date=" + ret_date + ", ret_flight_no=" + Arrays.toString(ret_flight_no)
				+ ", subclass_ret=" + Arrays.toString(subclass_ret) + "]";
	}
	@Override
	public void validate() throws SqivaException {
		if (Utils.isEmpty(org)) throw new SqivaException("Empty Origin");

		if (Utils.isEmpty(des)) throw new SqivaException("Empty Destination");

		if (dep_date == null) throw new SqivaException("Empty Departure Date");

		if (Utils.isEmpty(dep_flight_no)) throw new SqivaException("Empty Flight Number");

		if (Utils.isEmpty(customer.getFullName())) throw new SqivaException("Empty Customer Name");
		if (Utils.isEmpty(customer.getPhone1())) throw new SqivaException("Empty Customer Phone");

		if (Utils.isEmpty(subclass_dep)) throw new SqivaException("Empty Class seat");

		if (adult.size() < 1) throw new SqivaException("Empty Adult");
		
	}
	
	
}
