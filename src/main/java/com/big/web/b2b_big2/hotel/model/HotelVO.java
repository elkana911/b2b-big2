package com.big.web.b2b_big2.hotel.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.big.web.b2b_big2.INamedQuery;

@NamedQueries ({
    @NamedQuery(           
        name = INamedQuery.HOTEL_CHEAPEST,
        query = "select h from HotelVO h"
        )
})
@XmlRootElement
@Indexed
@Entity 
@Table(name="mst_hotels")
public class HotelVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6621549859366298641L;
	
	private String hotel_id;
	private String hotel_Name;
	private Integer country_id;
	private String address1;
	private String address2;
	private String city;
	private String stateCode;
	private String zipCode;
	private String website1;
	private String website2;
	private String website3;
	private String phone1;
	private String phone2;
	private String phone3;
	private String email1;
	private String email2;
	private String star;
	private String category;
	private String checkInTime;
	private String checkOutTime;
	private String fax;
	private Integer agent_Id;
	private String codeByAgent;
	private String picByAgent;
	private Date lastUpdate;
	
//	private BigDecimal price;
	
	public HotelVO() {
		setHotel_id(java.util.UUID.randomUUID().toString());
	}
	
	@Id 
	//@GeneratedValue(strategy = GenerationType.AUTO) disabled karena diisi manual
	@XmlTransient	//buat ngumpetin dari user yg akses via web service
	public String getHotel_id() {
		return hotel_id;
	}
	public void setHotel_id(String hotel_id) {
		this.hotel_id = hotel_id;
	}
	
	@Column(nullable = false, length=40)
	@Field
	public String getHotel_Name() {
		return hotel_Name;
	}
	public void setHotel_Name(String hotel_Name) {
		this.hotel_Name = hotel_Name;
	}
	
	public Integer getCountry_id() {
		return country_id;
	}
	public void setCountry_id(Integer country_id) {
		this.country_id = country_id;
	}
	
	@Column(length=70)
	@Field
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	@Column(length=50)
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	@Column(length=30)
	@Field
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(length=20)
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	@Column(length=100)
	@Field
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getWebsite1() {
		return website1;
	}
	public void setWebsite1(String website1) {
		this.website1 = website1;
	}
	public String getWebsite2() {
		return website2;
	}
	public void setWebsite2(String website2) {
		this.website2 = website2;
	}
	public String getWebsite3() {
		return website3;
	}
	public void setWebsite3(String website3) {
		this.website3 = website3;
	}

	
	@Column(name="agent_id")
	public Integer getAgentId() {
		return agent_Id;
	}
	public void setAgentId(Integer agentId) {
		this.agent_Id = agentId;
	}
	public String getCodeByAgent() {
		return codeByAgent;
	}
	public void setCodeByAgent(String codeByAgent) {
		this.codeByAgent = codeByAgent;
	}
	
	@Column(length=50)
	public String getPicByAgent() {
		return picByAgent;
	}
	public void setPicByAgent(String picByAgent) {
		this.picByAgent = picByAgent;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCheckInTime() {
		return checkInTime;
	}
	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}
	public String getCheckOutTime() {
		return checkOutTime;
	}
	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}
	public Integer getAgent_Id() {
		return agent_Id;
	}
	public void setAgent_Id(Integer agent_Id) {
		this.agent_Id = agent_Id;
	}

//    @Column(precision = 6, scale = 2)
//    public BigDecimal getPrice() {
//	return price;
//    }

//    public void setPrice(BigDecimal price) {
//	this.price = price;
//    }
}
