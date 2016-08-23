package com.big.web.b2b_big2.booking.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import com.big.web.b2b_big2.flight.pojo.BasicContact;
import com.big.web.b2b_big2.flight.pojo.PNR;
import com.big.web.b2b_big2.util.Utils;
import com.google.i18n.phonenumbers.NumberParseException;

@Entity
@Table(name="booking_flight_detail")
public class BookingFlightDtlVO implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5953750355055777195L;
	
	private String id;
	private String booking_flight_id;
	private String passenger_id_type;
	private String passenger_first_name;
	private String passenger_middle_name;
	private String passenger_phone;
	private String passenger_type;
	private String passenger_title;
	private String passenger_id;
	private String passenger_last_name;
	private String passenger_nationality;
	
	private BasicContact.SpecialRequest spec_request;
	private Date passenger_bday;
	private String passenger_ticket_no;
	private Integer infant_ref_id;
	
	public BookingFlightDtlVO() {
		setId(java.util.UUID.randomUUID().toString());
	}

	public BookingFlightDtlVO(PNR pnr) throws NumberParseException {
		this();
//		dtl.setPassenger_id_type(passenger_id_type);
//		setPassenger_first_name(pnr.getFirstName().toUpperCase());
		
		//tergantung airline
		setPassenger_first_name(pnr.getFirstName().toUpperCase());
//		setPassenger_first_name(pnr.getFirstMiddleName().toUpperCase());
		
		setPassenger_bday(pnr.getBirthday());
		
		setPassenger_middle_name(pnr.getMiddleName().toUpperCase());
		setPassenger_last_name(pnr.getLastName().toUpperCase());
//		dtl.setPassenger_id(passenger_id);
		setPassenger_title(pnr.getTitle().name().toUpperCase());
		setSpec_request(pnr.getSpecialRequest());
		
		setPassenger_id(pnr.getIdCard());
		if (pnr.getPhone() != null){
			setPassenger_phone(pnr.getPhone().getNumbersOnly());
		}
		
		setPassenger_type(pnr.getPersonType().getFlag());
		
		setPassenger_nationality(pnr.getCountryCode());

		setInfant_ref_id(pnr.getParentId());

	}

	@Id
	@XmlTransient
	@JsonIgnore
	@Column(name="id", length=40, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(length = 40)
	public String getBooking_flight_id() {
		return booking_flight_id;
	}

	public void setBooking_flight_id(String booking_flight_id) {
		this.booking_flight_id = booking_flight_id;
	}

	@Column(length = 1)
	public String getPassenger_id_type() {
		return passenger_id_type;
	}

	public void setPassenger_id_type(String passenger_id_type) {
		this.passenger_id_type = passenger_id_type;
	}

	@Column(length = 15)
	public String getPassenger_phone() {
		return passenger_phone;
	}

	public void setPassenger_phone(String passenger_phone) {
		this.passenger_phone = passenger_phone;
	}

	@Column(length = 1)
	public String getPassenger_type() {
		return passenger_type;
	}

	public void setPassenger_type(String passenger_type) {
		this.passenger_type = passenger_type;
	}

	@Column(length = 40)
	public String getPassenger_first_name() {
		return passenger_first_name;
	}

	public void setPassenger_first_name(String passenger_first_name) {
		this.passenger_first_name = passenger_first_name;
	}
	
	@Column(length = 40)
	public String getPassenger_middle_name() {
		return passenger_middle_name;
	}

	public void setPassenger_middle_name(String passenger_middle_name) {
		this.passenger_middle_name = passenger_middle_name;
	}

	@Column(length = 4)
	public String getPassenger_title() {
		return passenger_title;
	}

	public void setPassenger_title(String passenger_title) {
		this.passenger_title = passenger_title;
	}

	@Column(length = 20)
	public String getPassenger_id() {
		return passenger_id;
	}

	public void setPassenger_id(String passenger_id) {
		this.passenger_id = passenger_id;
	}

	@Column(length = 40)
	public String getPassenger_last_name() {
		return passenger_last_name;
	}

	public void setPassenger_last_name(String passenger_last_name) {
		this.passenger_last_name = passenger_last_name;
	}

	@DateTimeFormat(pattern="dd/MM/yyyy")
	public Date getPassenger_bday() {
		return passenger_bday;
	}

	public void setPassenger_bday(Date passenger_bday) {
		this.passenger_bday = passenger_bday;
	}

	@Column(length = 40)
	public String getPassenger_ticket_no() {
		return passenger_ticket_no;
	}

	public void setPassenger_ticket_no(String passenger_ticket_no) {
		this.passenger_ticket_no = passenger_ticket_no;
	}

	public Integer getInfant_ref_id() {
		return infant_ref_id;
	}

	public void setInfant_ref_id(Integer infant_ref_id) {
		this.infant_ref_id = infant_ref_id;
	}
	
	@Column(length = 3)
	public String getPassenger_nationality() {
		return passenger_nationality;
	}

	public void setPassenger_nationality(String passenger_nationality) {
		this.passenger_nationality = passenger_nationality;
	}

	@Enumerated(EnumType.STRING)	
	@Column(length = 20)
	public BasicContact.SpecialRequest getSpec_request() {
		return spec_request;
	}

	public void setSpec_request(BasicContact.SpecialRequest spec_request) {
		this.spec_request = spec_request;
	}

	@Transient public String getFullName(){
		return Utils.getFullName(passenger_first_name, passenger_middle_name, passenger_last_name);
	}
	
}
