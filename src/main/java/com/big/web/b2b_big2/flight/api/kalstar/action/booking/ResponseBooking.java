package com.big.web.b2b_big2.flight.api.kalstar.action.booking;

import java.util.List;

import com.big.web.b2b_big2.flight.api.kalstar.action.BasicResponse;
import com.big.web.b2b_big2.util.Utils;

/**
 * This class is customized due to differnet airlines return format
 * @author Administrator
 *
 */
public class ResponseBooking extends BasicResponse{

	private String org;
	private String des;
	private int round_trip;	
	private String book_code;
	private String dep_date;
	private String dep_flight_no;
	private String ret_date;
	private String ret_flight_no;
	
	private int normal_sales;
	private int book_balance;
	private String pay_limit;
	private String status;
	private String status_2;
	private String status_3;
	private String ret_status;
	private String ret_status_2;
	private String ret_status_3;
	
	private int pax_adult;
	private int pax_child;
	private int pax_infant;

	private List<String> paxNames;

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

	public String getBook_code() {
		return book_code;
	}

	public void setBook_code(String book_code) {
		this.book_code = book_code;
	}

	public String getDep_date() {
		return dep_date;
	}

	public void setDep_date(String dep_date) {
		this.dep_date = dep_date;
	}

	public String getDep_flight_no() {
		return dep_flight_no;
	}

	public void setDep_flight_no(String dep_flight_no) {
		this.dep_flight_no = dep_flight_no;
	}

	public String getRet_date() {
		return ret_date;
	}

	public void setRet_date(String ret_date) {
		this.ret_date = ret_date;
	}

	public String getRet_flight_no() {
		return ret_flight_no;
	}

	public void setRet_flight_no(String ret_flight_no) {
		this.ret_flight_no = ret_flight_no;
	}

	public int getNormal_sales() {
		return normal_sales;
	}

	public void setNormal_sales(int normal_sales) {
		this.normal_sales = normal_sales;
	}

	public int getBook_balance() {
		return book_balance;
	}

	public void setBook_balance(int book_balance) {
		this.book_balance = book_balance;
	}

	public String getPay_limit() {
		return pay_limit;
	}

	public void setPay_limit(String pay_limit) {
		this.pay_limit = pay_limit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus_2() {
		return status_2;
	}

	public void setStatus_2(String status_2) {
		this.status_2 = status_2;
	}

	public String getStatus_3() {
		return status_3;
	}

	public void setStatus_3(String status_3) {
		this.status_3 = status_3;
	}

	public String getRet_status() {
		return ret_status;
	}

	public void setRet_status(String ret_status) {
		this.ret_status = ret_status;
	}

	public String getRet_status_2() {
		return ret_status_2;
	}

	public void setRet_status_2(String ret_status_2) {
		this.ret_status_2 = ret_status_2;
	}

	public String getRet_status_3() {
		return ret_status_3;
	}

	public void setRet_status_3(String ret_status_3) {
		this.ret_status_3 = ret_status_3;
	}

	public int getPax_adult() {
		return pax_adult;
	}

	public void setPax_adult(int pax_adult) {
		this.pax_adult = pax_adult;
	}

	public int getPax_child() {
		return pax_child;
	}

	public void setPax_child(int pax_child) {
		this.pax_child = pax_child;
	}

	public int getPax_infant() {
		return pax_infant;
	}

	public void setPax_infant(int pax_infant) {
		this.pax_infant = pax_infant;
	}

	public List<String> getPaxNames() {
		return paxNames;
	}

	public void setPaxNames(List<String> paxNames) {
		this.paxNames = paxNames;
	}

	@Override
	public String toString() {
		return "ResponseBooking [org=" + org + ", des=" + des + ", round_trip=" + round_trip + ", book_code="
				+ book_code + ", dep_date=" + dep_date + ", dep_flight_no=" + dep_flight_no + ", ret_date=" + ret_date
				+ ", ret_flight_no=" + ret_flight_no + ", normal_sales=" + normal_sales + ", book_balance="
				+ book_balance + ", pay_limit=" + pay_limit + ", status=" + status + ", status_2=" + status_2
				+ ", status_3=" + status_3 + ", ret_status=" + ret_status + ", ret_status_2=" + ret_status_2
				+ ", ret_status_3=" + ret_status_3 + ", pax_adult=" + pax_adult + ", pax_child=" + pax_child
				+ ", pax_infant=" + pax_infant + ", paxNames=" + paxNames + ", getErrCode()=" + getErrCode()
				+ ", getErrMsg()=" + getErrMsg() + "]";
	}

	public String getStatusAsCSV() {
		StringBuffer sb = new StringBuffer();

		if (!Utils.isEmpty(status)){
			sb.append(status);
			
			if (!Utils.isEmpty(status_2)){
				sb.append(",").append(status_2);
				
				if (!Utils.isEmpty(status_3))
					sb.append(",").append(status_3);
			}
		}

		if (!Utils.isEmpty(ret_status)){
			
			if (sb.length() > 0) sb.append(",");
			
			sb.append(ret_status);
			
			if (!Utils.isEmpty(ret_status_2)){
				sb.append(",").append(ret_status_2);
				
				if (!Utils.isEmpty(ret_status_3))
					sb.append(",").append(ret_status_3);
			}
		}
		
		return sb.toString();
	}

	
}
