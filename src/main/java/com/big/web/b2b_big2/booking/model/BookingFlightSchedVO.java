package com.big.web.b2b_big2.booking.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.util.Utils;

@Entity
@Table(name="booking_flight_schedule")
public class BookingFlightSchedVO implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5953750355055777195L;
	
	private String id;
	private String booking_flight_id;
	
	private String departure;
	private String arrival;
	private Date dep_time;
	private Date arrival_time;
	private String flightNum;
	private String class_name;
	private Date lastUpdate;
	
	private String journey_sell_key;
	
	private BigDecimal comm_Rate;	// berhubung bookingflighttrans is oneToOne with bookingflight, each flight should store info about commission rate
	private BigDecimal incentive;
	private BigDecimal pph23;
	private BigDecimal comm_rate_berleha;

	public BookingFlightSchedVO() {
		setId(java.util.UUID.randomUUID().toString());
	}

	public BookingFlightSchedVO(FareInfo fareInfo) {
		this();
		setArrival(fareInfo.getRoute().getTo().getIataCode());
		setArrival_time(fareInfo.getRoute().getTo().getSchedule());
		
		if (!Utils.isEmpty(fareInfo.getBaseFare())){
			
			//gara2 kalstar jd membingungkan
			setClass_name(fareInfo.getClassName() + "/" + fareInfo.getBaseFare());
			
		}
		else
			setClass_name(fareInfo.getClassName());
		
		setDep_time(fareInfo.getRoute().getFrom().getSchedule());
		setDeparture(fareInfo.getRoute().getFrom().getIataCode());
		setFlightNum(fareInfo.getFlightNo());
	}

	@Id
	@XmlTransient
	@JsonIgnore
	@Column(name="id", length=40, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(length = 40)
	public String getBooking_flight_id() {
		return booking_flight_id;
	}

	public void setBooking_flight_id(String booking_flight_id) {
		this.booking_flight_id = booking_flight_id;
	}

	@Column(length = 5)
	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	@Column(length = 5)
	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public Date getDep_time() {
		return dep_time;
	}

	public void setDep_time(Date dep_time) {
		this.dep_time = dep_time;
	}

	public Date getArrival_time() {
		return arrival_time;
	}

	public void setArrival_time(Date arrival_time) {
		this.arrival_time = arrival_time;
	}

	@Column(length = 15)
	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	@Column(length = 10)
	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Column(length = 255)
	public String getJourney_sell_key() {
		return journey_sell_key;
	}

	public void setJourney_sell_key(String journey_sell_key) {
		this.journey_sell_key = journey_sell_key;
	}

	public BigDecimal getComm_Rate() {
		return comm_Rate;
	}

	public void setComm_Rate(BigDecimal comm_Rate) {
		this.comm_Rate = comm_Rate;
	}

	public BigDecimal getIncentive() {
		return incentive;
	}

	public void setIncentive(BigDecimal incentive) {
		this.incentive = incentive;
	}

	public BigDecimal getPph23() {
		return pph23;
	}

	public void setPph23(BigDecimal pph23) {
		this.pph23 = pph23;
	}

	public BigDecimal getComm_rate_berleha() {
		return comm_rate_berleha;
	}

	public void setComm_rate_berleha(BigDecimal comm_rate_berleha) {
		this.comm_rate_berleha = comm_rate_berleha;
	}

	@Override
	public String toString() {
		return "BookingFlightSchedVO [id=" + id + ", booking_flight_id="
				+ booking_flight_id + ", departure=" + departure + ", arrival="
				+ arrival + ", dep_time=" + dep_time + ", arrival_time="
				+ arrival_time + ", flightNum=" + flightNum + ", class_name="
				+ class_name + ", lastUpdate=" + lastUpdate
				+ ", journey_sell_key=" + journey_sell_key + ", comm_Rate="
				+ comm_Rate + ", incentive=" + incentive + ", pph23=" + pph23
				+ ", comm_rate_berleha=" + comm_rate_berleha + "]";
	}

}
