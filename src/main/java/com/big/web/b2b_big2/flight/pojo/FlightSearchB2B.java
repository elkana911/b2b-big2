package com.big.web.b2b_big2.flight.pojo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import com.big.web.b2b_big2.flight.Airline;

/*dipakai utk pencarian saja utk speed.jadi tdk dipakai utk entity suatu tabel*/
public class FlightSearchB2B implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -798118302632029228L;
	private String flightAvailId;
	private String flightNumber;
	private String originIata;
	private String destinationIata;
	private Date departTime;
	private Date arrivalTime;
//	private String airline;
	private Airline airline;
	private String isTransit;	// 0 or > 0
	private String updateCode;
	private String[] transitId;
	private Seat[] seats;
	/*
	private String class_seat;
	private String class_rate;
	private String class_avail_seat;
	*/
	private String radioId;
	
	private String departureIata;
	private String arrivalIata;
	private String departureDescription;
	private String arrivalDescription;
	
	private String originDescription;
	private String destinationDescription;
	
	private String[] cheapestClass;
	private Date created_Date;
	private Date lastUpdate;
	
	private boolean rateIsSummary;	//menandakan harganya sudah total
	
	private String route;
	
//	private Date lastUpdate;	ga perlu, kan bisa pakai created_date di flightavailid
	
//	private FlightAvailSeatVO secondFlight;
	private FlightSearchB2B secondFlight;
	
	public String getFlightAvailId() {
		return flightAvailId;
	}

	public void setFlightAvailId(String flightAvailId) {
		this.flightAvailId = flightAvailId;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getOriginIata() {
		return originIata;
	}

	public void setOriginIata(String originIata) {
		this.originIata = originIata;
	}

	public String getDestinationIata() {
		return destinationIata;
	}

	public void setDestinationIata(String destinationIata) {
		this.destinationIata = destinationIata;
	}

	public String getOriginDescription() {
		return originDescription;
	}

	public void setOriginDescription(String originDescription) {
		this.originDescription = originDescription;
	}

	public String getDestinationDescription() {
		return destinationDescription;
	}

	public void setDestinationDescription(String destinationDescription) {
		this.destinationDescription = destinationDescription;
	}

	public Date getDepartTime() {
		return departTime;
	}

	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public String getIsTransit() {
		return isTransit;
	}

	public void setIsTransit(String isTransit) {
		this.isTransit = isTransit;
	}
	
	public String getUpdateCode() {
		return updateCode;
	}

	public void setUpdateCode(String updateCode) {
		this.updateCode = updateCode;
	}

	public String[] getTransitId() {
		return transitId;
	}

	public void setTransitId(String[] transitId) {
		this.transitId = transitId;
	}
/*
	public String getClass_seat() {
		return class_seat;
	}

	public void setClass_seat(String class_seat) {
		this.class_seat = class_seat;
	}

	public String getClass_rate() {
		return class_rate;
	}

	public void setClass_rate(String class_rate) {
		this.class_rate = class_rate;
	}

	public String getClass_avail_seat() {
		return class_avail_seat;
	}

	public void setClass_avail_seat(String class_avail_seat) {
		this.class_avail_seat = class_avail_seat;
	}
*/
	
	public String getRadioId() {
		return radioId;
	}

	public Seat[] getSeats() {
		return seats;
	}

	public void setSeats(Seat[] seats) {
		this.seats = seats;
	}

	public void setRadioId(String radioId) {
		this.radioId = radioId;
	}

	public String getDepartureIata() {
		return departureIata;
	}

	public void setDepartureIata(String departureIata) {
		this.departureIata = departureIata;
	}

	public String getArrivalIata() {
		return arrivalIata;
	}

	public void setArrivalIata(String arrivalIata) {
		this.arrivalIata = arrivalIata;
	}

	public FlightSearchB2B getSecondFlight() {
		return secondFlight;
	}

	public void setSecondFlight(FlightSearchB2B secondFlight) {
		this.secondFlight = secondFlight;
	}

	public String getDepartureDescription() {
		return departureDescription;
	}

	public void setDepartureDescription(String departureDescription) {
		this.departureDescription = departureDescription;
	}

	public String getArrivalDescription() {
		return arrivalDescription;
	}

	public void setArrivalDescription(String arrivalDescription) {
		this.arrivalDescription = arrivalDescription;
	}

	public String[] getCheapestClass() {
		return cheapestClass;
	}

	public void setCheapestClass(String[] cheapestClass) {
		this.cheapestClass = cheapestClass;
	}

	public boolean isRateIsSummary() {
		return rateIsSummary;
	}

	public void setRateIsSummary(boolean rateIsSummary) {
		this.rateIsSummary = rateIsSummary;
	}
	
	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public Date getCreated_Date() {
		return created_Date;
	}

	public void setCreated_Date(Date created_Date) {
		this.created_Date = created_Date;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "FlightSearchB2B [flightAvailId=" + flightAvailId
				+ ", flightNumber=" + flightNumber + ", originIata="
				+ originIata + ", destinationIata=" + destinationIata
				+ ", departTime=" + departTime + ", arrivalTime=" + arrivalTime
				+ ", airline=" + airline + ", isTransit=" + isTransit
				+ ", updateCode=" + updateCode + ", transitId="
				+ Arrays.toString(transitId) + ", seats="
				+ Arrays.toString(seats) + ", radioId=" + radioId
				+ ", departureIata=" + departureIata + ", arrivalIata="
				+ arrivalIata + ", departureDescription="
				+ departureDescription + ", arrivalDescription="
				+ arrivalDescription + ", originDescription="
				+ originDescription + ", destinationDescription="
				+ destinationDescription + ", cheapestClass="
				+ Arrays.toString(cheapestClass) + ", created_Date="
				+ created_Date + ", lastUpdate=" + lastUpdate
				+ ", rateIsSummary=" + rateIsSummary + ", route=" + route
				+ ", secondFlight=" + secondFlight + "]";
	}

	
	
}
