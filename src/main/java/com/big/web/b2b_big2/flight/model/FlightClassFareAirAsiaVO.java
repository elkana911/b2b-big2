package com.big.web.b2b_big2.flight.model;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.big.web.b2b_big2.flight.pojo.FareInfo;

@Entity
@Table(name="flight_class_fare_airasia")
public class FlightClassFareAirAsiaVO extends AbstractFlightClassFare implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String upd_flag;
	
	@Column(length = 1)
	public String getUpd_flag() {
		return upd_flag;
	}

	public void setUpd_flag(String upd_flag) {
		this.upd_flag = upd_flag;
	}

	@Transient
	@Override
	public FareInfo getFareInfo(FareInfo data) {
		FareInfo data_ = super.getFareInfo(data);

		if (data_.getChildFare() == null)
			data_.setChildFare(data_.getRate());

		//masalahnya ternyata wkt scraping airasia baru 1 fare yaitu 150.000, pdhl utk transit sekali misalnya harus dikali 2 menjadi 300rb

		//infant tidak kena tax
		
		return data_;
	}
}
