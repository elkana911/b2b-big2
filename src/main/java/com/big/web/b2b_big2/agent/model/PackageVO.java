package com.big.web.b2b_big2.agent.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

/*
 * 22 dec 2014 lupa buat apa. seingetku harusnya tiap agen bisa berubah2 package, jd butuh datanya
 * harus ada data nominalnya
 */
@Entity
@Table(name="agent_package")
public class PackageVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5650479486630329921L;

	private String code;
	private String name;
	private Date start_date;
	private Date end_date;
	private String inactive;
	private Date inactive_date;

	@Id
	@XmlTransient
	@JsonIgnore
	@Column(length=40)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@Column(length=30, nullable = false)	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
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
}
