package com.big.web.b2b_big2.flight.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;


@Indexed
@Entity
@Table(name="mst_airports")
public class AirportVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3134513753554885157L;

	private String airport_id;
	private String airport_name;
	private String aka;
	private String iata;
	private String icao;
	private String city;
	private Long country_id;
	private String country;
	private String latitude;
	private String longitude;
	private String altitude;
	private String timezone;
	private String dst;
	private Long agent_id;
	private String codebyagent;
	
	@Id
	@Column(length=40, nullable = false)
	public String getAirport_id() {
		return airport_id;
	}
	public void setAirport_id(String airport_id) {
		this.airport_id = airport_id;
	}

	@Field
	@Column(length=100, nullable=false)
	public String getAirport_name() {
		return airport_name;
	}
	public void setAirport_name(String airport_name) {
		this.airport_name = airport_name;
	}
	
	@Field
	@Column(length=20)
	public String getAka() {
		return aka;
	}
	public void setAka(String aka) {
		this.aka = aka;
	}
	
	@Column(length=3)
	public String getIata() {
		return iata;
	}
	public void setIata(String iata) {
		this.iata = iata;
	}
	@Column(length=4)
	public String getIcao() {
		return icao;
	}
	public void setIcao(String icao) {
		this.icao = icao;
	}
	
	@Column(length=100)	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Column(length=20)
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	@Column(length=20)
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	@Column(length=5)	
	public String getAltitude() {
		return altitude;
	}
	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}
	@Column(length=10)
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	@Column(length=2)
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}

	public Long getAgent_id() {
		return agent_id;
	}
	public void setAgent_id(Long agent_id) {
		this.agent_id = agent_id;
	}

	@Column(length=15)
	public String getCodebyagent() {
		return codebyagent;
	}
	public void setCodebyagent(String codebyagent) {
		this.codebyagent = codebyagent;
	}
	public Long getCountry_id() {
		return country_id;
	}
	public void setCountry_id(Long country_id) {
		this.country_id = country_id;
	}

	@Column(length=100)
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	/*
	@ManyToOne
	@JoinColumn(name="country_id", referencedColumnName="country_id")
	public CountryVO getCountry() {
		return country;
	}
	public void setCountry(CountryVO country) {
		this.country = country;
	}*/
	

}
