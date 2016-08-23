package com.big.web.b2b_big2.flight.model;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.util.Utils;

@Entity
@Table(name="flight_class_fare_garuda")
public class FlightClassFareGarudaVO extends AbstractFlightClassFare implements Serializable{
	private static final long serialVersionUID = 1L;

	private BigDecimal pph;
	
	public BigDecimal getPph() {
		return pph;
	}

	public void setPph(BigDecimal pph) {
		this.pph = pph;
	}

	@Transient
	@Override
	public FareInfo getFareInfo(FareInfo data) {
		FareInfo data_ = super.getFareInfo(data);
		
		data_.setPph(getPph());
		
		if (getChd_fare() != null)
			data_.setTax_child(Utils.getDiscount(getChd_fare().doubleValue(), 0.1));
		
		if (getInf_fare() != null)
			data_.setTax_infant(Utils.getDiscount(getInf_fare().doubleValue(), 0.1));
		
		return data_;
	}
}
