package com.big.web.b2b_big2.flight.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mst_route_save")
public class MstRouteSaveVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2110545632846284900L;

	private String id;
	private String origin;	// Denpasar (DPS)
	private String destination;	// Ambon (AMQ)
	private Date created_Date;
	private Long web_Id;	// link with MST_WEB_FLIGHT_SAVE.ID
	
	private String iata_Origin;
	private String iata_Destination;
	private String favourite;	// favourite 0 / null / 1
	
	private Date last_Update;
	private String status;
	
	public MstRouteSaveVO() {
		setId(UUID.randomUUID().toString());
	}

	@Id
	@Column(length = 40)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(length = 100, nullable = false)
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Column(length = 100, nullable = false)
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Date getCreated_Date() {
		return created_Date;
	}

	public void setCreated_Date(Date created_Date) {
		this.created_Date = created_Date;
	}

	@Column(nullable = false)
	public Long getWeb_Id() {
		return web_Id;
	}

	public void setWeb_Id(Long web_Id) {
		this.web_Id = web_Id;
	}

	@Column(length = 3, nullable = false)
	public String getIata_Origin() {
		return iata_Origin;
	}

	public void setIata_Origin(String iata_Origin) {
		this.iata_Origin = iata_Origin;
	}

	@Column(length = 3, nullable = false)
	public String getIata_Destination() {
		return iata_Destination;
	}

	public void setIata_Destination(String iata_Destination) {
		this.iata_Destination = iata_Destination;
	}

	@Column(length = 1)
	public String getFavourite() {
		return favourite;
	}

	public void setFavourite(String favourite) {
		this.favourite = favourite;
	}

	public Date getLast_Update() {
		return last_Update;
	}

	public void setLast_Update(Date last_Update) {
		this.last_Update = last_Update;
	}

	@Column(length = 15)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MstRouteSaveVO " + origin + "-" + destination;
	}
	
	
	
}
