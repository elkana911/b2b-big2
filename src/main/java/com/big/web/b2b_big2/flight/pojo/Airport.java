package com.big.web.b2b_big2.flight.pojo;

import java.io.Serializable;
import java.util.Date;

import com.big.web.b2b_big2.util.Utils;

public class Airport implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6676065547115249084L;
	private String name;
	private String city;
	private String iataCode;
	private String icaoCode;
//	private CountryVO country;	dont use Entity in serializable any more.
	private Long countryId;
	private Date schedule;
	
	public Airport() {
	}

	public boolean isEmpty(){
		return Utils.isEmpty(name)
				&& Utils.isEmpty(city)
				&& Utils.isEmpty(iataCode)
				&& Utils.isEmpty(icaoCode)
				&& countryId == null
				&& schedule == null
				;
	}
//	/**
//	 * only set basic info : city, country, iata, icao, name
//	 * @param airportVO
//	 */
//	public Airport(AirportVO airportVO) {
//		
//		setCity(airportVO.getCity());
//		setCountry(country);
//		setCountryId(airportVO.getCountry_id());
//		setIataCode(airportVO.getIata());
//		setIcaoCode(airportVO.getIcao());
//		setName(airportVO.getAirport_name());
//		
//	}
//	
	public Airport(String name, String city, String iataCode, Date schedule) {
		this.name = name;
		this.city = city;
		this.iataCode = iataCode;
		this.schedule = schedule;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIataCode() {
		return iataCode;
	}
	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}
	public String getIcaoCode() {
		return icaoCode;
	}
	public void setIcaoCode(String icaoCode) {
		this.icaoCode = icaoCode;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Date getSchedule() {
		return schedule;
	}
	public void setSchedule(Date schedule) {
		this.schedule = schedule;
	}
	@Override
	public String toString() {
		return "Airport [name=" + name + ", city=" + city + ", iataCode="
				+ iataCode + ", icaoCode=" + icaoCode + ", countryId=" + countryId
				+ ", schedule=" + schedule + "]";
	}
	
	
	
}
