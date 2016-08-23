package com.big.web.b2b_big2.flight.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.odr.ODR_STATE;

@Entity
@Table(name="odr_status")
public class ODRStatusVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5194070001130096228L;
	
	private Airline airline;
	private ODR_STATE status;
	private Date lastUpdate;
	
	public ODRStatusVO(){
	}
	
	public ODRStatusVO(Airline airline, ODR_STATE status) {
		this.airline = airline;
		this.status = status;
		this.lastUpdate = new Date();
	}
	
	@Id
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	public Airline getAirline() {
		return airline;
	}
	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	public ODR_STATE getStatus() {
		return status;
	}
	public void setStatus(ODR_STATE status) {
		this.status = status;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "ODRStatusVO [airline=" + airline + ", status=" + status
				+ ", lastUpdate=" + lastUpdate + "]";
	}
	
	
}
