package com.big.web.b2b_big2.flight.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="flightavailseat_clean")
public class FlightAvailSeatVO implements Serializable{

	public static final String TABLE_NAME = "flightavailseat_clean";
	/**
	 * 
	 */
	private static final long serialVersionUID = 7870276991920154266L;
	private String flightavailid;
	private String flightnum;
	private String origin;
	private String destination;
	private Date dep_time;
	private Date arr_time;
	private String airline;
	private String istransit;	//0 jika non transit. > 0 brarti sequence utk rutenya
	private String departure;
	private String arrival;
	private String currency;
	private Long agent_id;
	private Date created_date;
	private String updatecode;	//berisi gabungan flightno dr beberapa pesawat. (connecting flight)
	private String transitId;	//berisi flightid sebelumnya
	private String route;	//diisi utk pswt yg transit tp 1 penerbangan. misal CGK-SUB-DPS.
	private String aircraft;
	
	private String journey_sell_key;
	
	private Date lastUpdate;
	
	public FlightAvailSeatVO() {
		setFlightavailid(java.util.UUID.randomUUID().toString());
	}
	
	@Id
	@Column(length=40)
	public String getFlightavailid() {
		return flightavailid;
	}
	public void setFlightavailid(String flightavailid) {
		this.flightavailid = flightavailid;
	}
	
	@Column(length=30)
	public String getFlightnum() {
		return flightnum;
	}
	public void setFlightnum(String flightnum) {
		this.flightnum = flightnum;
	}
	
	@Column(length=5)
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	@Column(length=5)
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public Date getDep_time() {
		return dep_time;
	}
	public void setDep_time(Date dep_time) {
		this.dep_time = dep_time;
	}

	public Date getArr_time() {
		return arr_time;
	}
	public void setArr_time(Date arr_time) {
		this.arr_time = arr_time;
	}
	
	@Transient
	public String getArr_time_short(){
		if (arr_time == null) return null;
		return new SimpleDateFormat("HHmm").format(arr_time);
	}

	@Column(length=40)
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
	
	@Column(length=1)
	public String getIstransit() {
		return istransit;
	}
	public void setIstransit(String istransit) {
		this.istransit = istransit;
	}
	
	@Column(length=5)
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	
	@Column(length=5)
	public String getArrival() {
		return arrival;
	}
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
	
	@Column(length=10)
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public Long getAgent_id() {
		return agent_id;
	}
	public void setAgent_id(Long agent_id) {
		this.agent_id = agent_id;
	}
	
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	
	@Column(length=20)
	public String getUpdatecode() {
		return updatecode;
	}
	public void setUpdatecode(String updatecode) {
		this.updatecode = updatecode;
	}
	
	@Column(length=40)
	public String getTransitId() {
		return transitId;
	}
	public void setTransitId(String transitId) {
		this.transitId = transitId;
	}
	@Column(length=20)
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	@Column(length=20)
	public String getAircraft() {
		return aircraft;
	}
	public void setAircraft(String aircraft) {
		this.aircraft = aircraft;
	}
	
	@Column(length=255)
	public String getJourney_sell_key() {
		return journey_sell_key;
	}
	public void setJourney_sell_key(String journey_sell_key) {
		this.journey_sell_key = journey_sell_key;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "FlightAvailSeatVO [flightavailid=" + flightavailid
				+ ", flightnum=" + flightnum + ", origin=" + origin
				+ ", destination=" + destination + ", dep_time=" + dep_time
				+ ", arr_time=" + arr_time + ", airline=" + airline
				+ ", istransit=" + istransit + ", departure=" + departure
				+ ", arrival=" + arrival + ", currency=" + currency
				+ ", agent_id=" + agent_id + ", created_date=" + created_date
				+ ", updatecode=" + updatecode + ", transitId=" + transitId
				+ ", route=" + route + ", aircraft=" + aircraft
				+ ", journey_sell_key=" + journey_sell_key + ", lastUpdate="
				+ lastUpdate + "]";
	}

}
