package com.big.web.b2b_big2.customer.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "mst_qcustomer")
public class QCustomer implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2715765953192069697L;
	
	private Long id;
	private String fullName;
	private String idCard;
	private String id_Type;	// future use
	private String phone1;
	private String phone2;
	private Date birthday;
	private String title;
	private String countryCode;
	private String contact_Type;
	private String agent_Id;
	private String email;
	private String specialRequest;
	private Long counter;
	private String person_Type;
	
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="QCUSTOMER_SEQ")
    @SequenceGenerator(name="QCUSTOMER_SEQ", sequenceName="QCUSTOMER_SEQ")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(length = 120)
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	@Column(length = 20)
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	@Column(length = 1)
	public String getId_Type() {
		return id_Type;
	}
	public void setId_Type(String id_Type) {
		this.id_Type = id_Type;
	}

	@Column(length = 3)
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	@Column(length = 20)
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	
	@Column(length = 20)
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Column(length = 4)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	@Column(length = 4)
	public String getContact_Type() {
		return contact_Type;
	}
	public void setContact_Type(String contact_Type) {
		this.contact_Type = contact_Type;
	}
	
	@Column(length = 40)
	public String getAgent_Id() {
		return agent_Id;
	}
	public void setAgent_Id(String agent_Id) {
		this.agent_Id = agent_Id;
	}
	
	@Column(length = 100)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(length = 20)
	public String getSpecialRequest() {
		return specialRequest;
	}
	public void setSpecialRequest(String specialRequest) {
		this.specialRequest = specialRequest;
	}
	public Long getCounter() {
		return counter;
	}
	public void setCounter(Long counter) {
		this.counter = counter;
	}
	@Column(length = 1)
	public String getPerson_Type() {
		return person_Type;
	}
	public void setPerson_Type(String person_Type) {
		this.person_Type = person_Type;
	}
	
	@Override
	public String toString() {
		return "QCustomer [id=" + id + ", fullName=" + fullName + ", idCard="
				+ idCard + ", id_Type=" + id_Type + ", phone1=" + phone1
				+ ", phone2=" + phone2 + ", birthday=" + birthday + ", title="
				+ title + ", countryCode=" + countryCode + ", contact_Type="
				+ contact_Type + ", agent_Id=" + agent_Id + ", email=" + email
				+ ", specialRequest=" + specialRequest + ", counter=" + counter
				+ ", person_Type=" + person_Type + "]";
	}
	
	
}
