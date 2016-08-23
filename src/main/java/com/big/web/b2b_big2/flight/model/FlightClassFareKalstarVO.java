package com.big.web.b2b_big2.flight.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="flight_class_fare_kalstar")
public class FlightClassFareKalstarVO extends AbstractSqivaFlightClassFare implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
