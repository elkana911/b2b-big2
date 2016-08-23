package com.big.web.b2b_big2.flight.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass	//sebagai penanda kalau dipakai oleh banyak table seperti kalstar,trigana dan avia
public abstract class AbstractSqivaFlightClassFare extends AbstractFlightClassFare{

	private String fare_base;	//khusus sqiva data


	@Column(length = 10)
	public String getFare_base() {
		return fare_base;
	}
	public void setFare_base(String fare_base) {
		this.fare_base = fare_base;
	}

}
