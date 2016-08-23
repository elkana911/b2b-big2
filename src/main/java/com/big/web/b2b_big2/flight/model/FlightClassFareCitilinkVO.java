package com.big.web.b2b_big2.flight.model;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.big.web.b2b_big2.flight.pojo.FareInfo;

@Entity
@Table(name="flight_class_fare_citilink")
public class FlightClassFareCitilinkVO extends AbstractFlightClassFare implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private BigDecimal promo_disc;
	private BigDecimal disc_desc;
	private BigDecimal chd_disc;
	
	public BigDecimal getPromo_disc() {
		return promo_disc;
	}
	public void setPromo_disc(BigDecimal promo_disc) {
		this.promo_disc = promo_disc;
	}
	public BigDecimal getDisc_desc() {
		return disc_desc;
	}
	public void setDisc_desc(BigDecimal disc_desc) {
		this.disc_desc = disc_desc;
	}
	public BigDecimal getChd_disc() {
		return chd_disc;
	}
	public void setChd_disc(BigDecimal chd_disc) {
		this.chd_disc = chd_disc;
	}
	
	@Transient
	@Override
	public FareInfo getFareInfo(FareInfo data) {
		FareInfo data_ = super.getFareInfo(data);
		
		data_.setPromoDiscount(getPromo_disc());
		data_.setChildDiscount(getChd_disc());
		
		return data_;
	}
}
