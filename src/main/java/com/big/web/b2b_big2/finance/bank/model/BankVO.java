package com.big.web.b2b_big2.finance.bank.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
@Table(name="mst_bank")
public class BankVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5501651991995722378L;
	
	private String id;
	private String name;
	private String address1;
	private String address2;
	private String website1;
	private String website2;
	private String zipCode;
	private String email1;
	private String email2;
	private String code;
	private String van;
	private String atmSupport;	//Y / NULL / <> Y
	private String vaSupport;	//Y / NULL / <> Y
	private String aka;
	
	@Id
	@XmlTransient
	@JsonIgnore
	@Column(length=40, nullable = false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(length=100, nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(length = 100)
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	@Column(length = 50)
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	@Column(length = 255)
	public String getWebsite1() {
		return website1;
	}
	public void setWebsite1(String website1) {
		this.website1 = website1;
	}

	@Column(length = 255)
	public String getWebsite2() {
		return website2;
	}
	public void setWebsite2(String website2) {
		this.website2 = website2;
	}
	@Column(length = 15)
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Column(length = 100)
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	
	@Column(length = 100)	
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	@Column(length = 3)	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@Column(length = 1)	
	public String getAtmSupport() {
		return atmSupport;
	}
	public void setAtmSupport(String atmSupport) {
		this.atmSupport = atmSupport;
	}

	@Column(length = 1)	
	public String getVaSupport() {
		return vaSupport;
	}
	public void setVaSupport(String vaSupport) {
		this.vaSupport = vaSupport;
	}
	@Column(length = 4)
	public String getVan() {
		return van;
	}
	public void setVan(String van) {
		this.van = van;
	}
	
	
	@Column(length = 30)
	public String getAka() {
		return aka;
	}
	public void setAka(String aka) {
		this.aka = aka;
	}
	
	@Transient
	public String getBankLabel() {
		return code + " (" + name + ")";
	}
	
	@Override
	public String toString() {
		return "BankVO [id=" + id + ", name=" + name + ", address1=" + address1 + ", address2=" + address2
				+ ", website1=" + website1 + ", website2=" + website2 + ", zipCode=" + zipCode + ", email1=" + email1
				+ ", email2=" + email2 + ", code=" + code + ", van=" + van + ", atmSupport=" + atmSupport + ", aka="
				+ aka + "]";
	}

	
}
