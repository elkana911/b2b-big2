package com.big.web.b2b_big2.flight.service;

import java.util.List;

import com.big.web.b2b_big2.flight.model.AirportVO;
import com.big.web.service.GenericManager;


public interface IAirportManager extends GenericManager<AirportVO , String> {
	
	List<AirportVO> search(String searchTerm);
	List<AirportVO> searchByCountry(String searchTerm, String countryCode);
	
	AirportVO getAirport(String uid);
	AirportVO getAirportByIata(String iata);

	List<AirportVO> getAirports();
	List<String> getPrettyAirports();
	
	String getAirportCode(String city);
	
	void addAirport(AirportVO airport);
	void updateAirport(AirportVO airport);
	void removeAirport(AirportVO airport);

	String getCity(String fieldValue);

}
