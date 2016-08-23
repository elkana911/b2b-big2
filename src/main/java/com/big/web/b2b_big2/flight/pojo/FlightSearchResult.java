package com.big.web.b2b_big2.flight.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * disiapkan sebagai solusi baru yang lengkap dan mudah 
 */
public class FlightSearchResult {
	private TripMode tripMode;
	
	private List<FlightSearchB2B> departList;
	private List<FlightSearchB2B> returnList;
	
	public FlightSearchResult(TripMode tripMode) {
		this.tripMode = tripMode;
		departList = new ArrayList<FlightSearchB2B>();
		returnList = new ArrayList<FlightSearchB2B>();
	}
	
	public FlightSearchResult(int tripMode) {
		this.tripMode = tripMode == 0 ? TripMode.ONE_WAY : TripMode.RETURN;
		departList = new ArrayList<FlightSearchB2B>();
		returnList = new ArrayList<FlightSearchB2B>();
	}

	public TripMode getTripMode() {
		return tripMode;
	}
	public void setTripMode(TripMode tripMode) {
		this.tripMode = tripMode;
	}
	public List<FlightSearchB2B> getDepartList() {
		return departList;
	}
	public void setDepartList(List<FlightSearchB2B> departList) {
		this.departList = departList;
	}
	public List<FlightSearchB2B> getReturnList() {
		return returnList;
	}
	public void setReturnList(List<FlightSearchB2B> returnList) {
		this.returnList = returnList;
	}

	public boolean isEmpty() {
		return departList.size() == 0 && returnList.size() == 0;
	}
	
}
