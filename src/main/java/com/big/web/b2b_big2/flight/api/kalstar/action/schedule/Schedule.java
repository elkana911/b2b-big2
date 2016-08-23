package com.big.web.b2b_big2.flight.api.kalstar.action.schedule;

import java.util.Date;
import java.util.List;

import com.big.web.b2b_big2.flight.api.kalstar.action.fare.FareDetail;
import com.big.web.b2b_big2.flight.pojo.ROUTE_MODE;

/**
 * Dibuat serupa dengan flightavailseatvo
 * @author Administrator
 *
 */
public class Schedule {
	private String id;
	private String flightNo;
	private String org;
	private String dest;
	private Date depDate;
	private Date arrDate;
	private String duration;
	private String aircraft;
	private String transit;	//disamakan dengan flightavailseat.istransit. 0 jika non transit. > 0 brarti sequence utk rutenya
	private String route;

	private String connectingId;	//to connect to previous plane. same connectingId is same flights
	private String updateCode;	//see flightavailseat, to connect flightnumber
	
	private ROUTE_MODE tripMode;	//just add on info, not recommend to insert database

	private List<FareDetail> fareDetails;
	
	private Date returnDate;
	private String returnFlightNo;
	
	public Schedule() {
		setId(java.util.UUID.randomUUID().toString());
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public Date getDepDate() {
		return depDate;
	}

	public void setDepDate(Date depDate) {
		this.depDate = depDate;
	}

	public Date getArrDate() {
		return arrDate;
	}

	public void setArrDate(Date arrDate) {
		this.arrDate = arrDate;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getAircraft() {
		return aircraft;
	}

	public void setAircraft(String aircraft) {
		this.aircraft = aircraft;
	}

	public String getTransit() {
		return transit;
	}

	public void setTransit(String transit) {
		this.transit = transit;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public List<FareDetail> getFareDetails() {
		return fareDetails;
	}
	public void setFareDetails(List<FareDetail> fareDetails) {
		this.fareDetails = fareDetails;
	}
	public String getConnectingId() {
		return connectingId;
	}

	public void setConnectingId(String connectingId) {
		this.connectingId = connectingId;
	}

	public String getUpdateCode() {
		return updateCode;
	}

	public void setUpdateCode(String updateCode) {
		this.updateCode = updateCode;
	}

	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public String getReturnFlightNo() {
		return returnFlightNo;
	}
	public void setReturnFlightNo(String returnFlightNo) {
		this.returnFlightNo = returnFlightNo;
	}
	
	
	public ROUTE_MODE getTripMode() {
		return tripMode;
	}
	public void setTripMode(ROUTE_MODE tripMode) {
		this.tripMode = tripMode;
	}
	@Override
	public String toString() {
		return "Schedule [id=" + id + ", flightNo=" + flightNo + ", org=" + org + ", dest=" + dest + ", depDate="
				+ depDate + ", arrDate=" + arrDate + ", duration=" + duration + ", aircraft=" + aircraft + ", transit="
				+ transit + ", route=" + route + ", connectingId=" + connectingId + ", updateCode=" + updateCode
				+ ", tripMode=" + tripMode + ", fareDetails=" + fareDetails + ", returnDate="
				+ returnDate + ", returnFlightNo=" + returnFlightNo + "]";
	}
	
	
}
