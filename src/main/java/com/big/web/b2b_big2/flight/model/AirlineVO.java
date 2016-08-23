package com.big.web.b2b_big2.flight.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.big.web.b2b_big2.country.model.CountryVO;

//@NamedQueries({
//	@NamedQuery(11
//	name = "findAirline",
//	query = "from AirlineVO a where a.name like 'BATIK AIR'"
//	)
//})
@Indexed
@Entity
@Table(name="mst_airlines")
public class AirlineVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8627878947830060408L;
	
	private Long airline_id;
	private String name;	//must UPPERCASE
	private String code;
	private String hq;
	private CountryVO country;
	
	private String website1;
	private String website2;
	private String call_center;
	private String phone1;
	private String phone2;
	private String phone3;
	private String email1;
	private String email2;
	private String inactive;
	private Date inactive_date;
	private Date lastUpdate;
	private String agentId;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getAirline_id() {
		return airline_id;
	}
	public void setAirline_id(Long airline_id) {
		this.airline_id = airline_id;
	}
	
	@Column(nullable = false, name="nama", length=40)
	@Field
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length=3)
	@Field
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Field
	@Column(name="website1", length=255)
	public String getWebsite1() {
		return website1;
	}
	public void setWebsite1(String website1) {
		this.website1 = website1;
	}
	
	@Field
	@Column(length=255)
	public String getHq() {
		return hq;
	}
	public void setHq(String hq) {
		this.hq = hq;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="countryid")
	public CountryVO getCountry() {
		return country;
	}
	public void setCountry(CountryVO country) {
		this.country = country;
	}
	@Column(length=20)
	public String getCall_center() {
		return call_center;
	}
	public void setCall_center(String call_center) {
		this.call_center = call_center;
	}
	
	@Column(length=20)
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	
	@Column(length=100)
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	@Column(length=255)
	public String getWebsite2() {
		return website2;
	}
	public void setWebsite2(String website2) {
		this.website2 = website2;
	}

	@Column(length=20)
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	
	@Column(length=20)
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	
	@Column(length=100)
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	
	@Column(length=1)
	public String getInactive() {
		return inactive;
	}
	public void setInactive(String inactive) {
		this.inactive = inactive;
	}
	
	public Date getInactive_date() {
		return inactive_date;
	}
	public void setInactive_date(Date inactive_date) {
		this.inactive_date = inactive_date;
	}
	
	@Column(length=40)
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	
}
