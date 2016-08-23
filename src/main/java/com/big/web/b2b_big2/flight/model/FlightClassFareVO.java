package com.big.web.b2b_big2.flight.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.flight.service.impl.FlightManagerImpl;

@Deprecated
@Entity
@Table(name="flight_class_fare_clean")
public class FlightClassFareVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long class_fare_id;
	private String flightavailid;
	private String class_name;
	private BigDecimal class_avail_seat;
	private BigDecimal class_rate;
	private BigDecimal basic_rate;
	private BigDecimal tax;
	private BigDecimal iwjr;
	private BigDecimal agent_discount;
	private BigDecimal extra_cover;
	private BigDecimal nta;
	private BigDecimal surcharge_fee;
	private BigDecimal insentif;
	private BigDecimal total;
	private String radio_id;
	private BigDecimal class_sequence;
	private Date lastUpdate;
	
	@Id
	public Long getClass_fare_id() {
		return class_fare_id;
	}
	public void setClass_fare_id(Long class_fare_id) {
		this.class_fare_id = class_fare_id;
	}
	
	@Column(length=40)
	public String getFlightavailid() {
		return flightavailid;
	}
	public void setFlightavailid(String flightavailid) {
		this.flightavailid = flightavailid;
	}
	
	@Column(length=1)
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public BigDecimal getClass_avail_seat() {
		return class_avail_seat;
	}
	public void setClass_avail_seat(BigDecimal class_avail_seat) {
		this.class_avail_seat = class_avail_seat;
	}
	public BigDecimal getClass_rate() {
		return class_rate;
	}
	public void setClass_rate(BigDecimal class_rate) {
		this.class_rate = class_rate;
	}
	public BigDecimal getBasic_rate() {
		return basic_rate;
	}
	public void setBasic_rate(BigDecimal basic_rate) {
		this.basic_rate = basic_rate;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public BigDecimal getIwjr() {
		return iwjr;
	}
	public void setIwjr(BigDecimal iwjr) {
		this.iwjr = iwjr;
	}
	public BigDecimal getAgent_discount() {
		return agent_discount;
	}
	public void setAgent_discount(BigDecimal agent_discount) {
		this.agent_discount = agent_discount;
	}
	public BigDecimal getExtra_cover() {
		return extra_cover;
	}
	public void setExtra_cover(BigDecimal extra_cover) {
		this.extra_cover = extra_cover;
	}
	public BigDecimal getNta() {
		return nta;
	}
	public void setNta(BigDecimal nta) {
		this.nta = nta;
	}
	public BigDecimal getSurcharge_fee() {
		return surcharge_fee;
	}
	public void setSurcharge_fee(BigDecimal surcharge_fee) {
		this.surcharge_fee = surcharge_fee;
	}
	public BigDecimal getInsentif() {
		return insentif;
	}
	public void setInsentif(BigDecimal insentif) {
		this.insentif = insentif;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	@Column(length=255)
	public String getRadio_id() {
		return radio_id;
	}
	public void setRadio_id(String radio_id) {
		this.radio_id = radio_id;
	}
	public BigDecimal getClass_sequence() {
		return class_sequence;
	}
	public void setClass_sequence(BigDecimal class_sequence) {
		this.class_sequence = class_sequence;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * 
	 * @param data
	 * @return
	 * @see FlightManagerImpl#getFareInfo(String, String)
	 */
	@Transient
	public FareInfo getFareInfo(FareInfo data, Airline airline) {
//		FareInfo data = new FareInfo();
		
		data.setId(getFlightavailid());
		data.setRef_ClassFareId(getClass_fare_id());
		
		data.setRadioId(getRadio_id());
		data.setClassName(getClass_name());
		data.setTax_adult(getTax());
		data.setTax_child(getTax());
		data.setTax_infant(BigDecimal.ZERO);
		data.setAmount(getTotal());
		data.setFuelSurcharge(getSurcharge_fee());
		
		int multiplier = 1000;	//multiply

		if (airline == Airline.SRIWIJAYA){
			data.setRate(new BigDecimal(multiplier).multiply(getClass_rate()) );
		}else{

			if (getClass_rate().toPlainString().length() < 5)
				data.setRate(new BigDecimal(multiplier).multiply(getClass_rate()));
			else
				data.setRate(getClass_rate());
		}
		
		return data;
	}
	
	
}
