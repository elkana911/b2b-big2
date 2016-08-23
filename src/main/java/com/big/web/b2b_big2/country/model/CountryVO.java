package com.big.web.b2b_big2.country.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;

@Indexed
@Entity
@Table(name="mst_countries")
public class CountryVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6097679844282753516L;
	
	public static final int INDONESIA = 62;
	public static final int JAPAN = 81;
	public static final int MALAYSIA = 60;
	public static final int SINGAPORE = 65;
	public static final int THAILAND = 66;

	private Long country_id;
	private String country_name;
	private String country_code;
	private Long region_id;
	private String capital;
	private String currency_code;
	private String currency_name;
	
	@Id
	public Long getCountry_id() {
		return country_id;
	}
	public void setCountry_id(Long country_id) {
		this.country_id = country_id;
	}

	@Column(length=50)
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	@Column(length=3)
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}
	public Long getRegion_id() {
		return region_id;
	}
	public void setRegion_id(Long region_id) {
		this.region_id = region_id;
	}
	@Column(length=40)
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	@Column(length=3)
	public String getCurrency_code() {
		return currency_code;
	}
	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}
	@Column(length=20)
	public String getCurrency_name() {
		return currency_name;
	}
	public void setCurrency_name(String currency_name) {
		this.currency_name = currency_name;
	}
	
	@Override
	public String toString() {
		return country_name;
	}
	
	
}
