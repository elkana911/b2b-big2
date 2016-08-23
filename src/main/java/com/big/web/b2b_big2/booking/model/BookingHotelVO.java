package com.big.web.b2b_big2.booking.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.big.web.model.User;

@Entity
@Table(name="booking_hotel")
public class BookingHotelVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3993923223981637926L;
	
	private String uid;
	private String code;
	private User user;
	
	public BookingHotelVO() {
		setUid(java.util.UUID.randomUUID().toString());
	}

	@Id
	@XmlTransient
	@JsonIgnore
	@Column(name="id", length=40, nullable = false)
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

	@Column(length=10)
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@ManyToOne
	@JoinColumn(name="userid")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "BookingHotelVO [uid=" + uid + ", code=" + code + ", user=" + user + "]";
	}
	
}
