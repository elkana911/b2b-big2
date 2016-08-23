package com.big.web.b2b_big2.flight.dao;

import java.util.List;

import com.big.web.b2b_big2.flight.model.AirportVO;
import com.big.web.dao.GenericDao;

public interface IAirportDao extends GenericDao<AirportVO, String> {

	List<String> getPrettyAirports();
	
	String getAirportCode(String city);
	String getAirportName(String iata);
	String getAirportNameAndCity(String iata);
	String getCity(String iata);
	AirportVO getAirportByIata(String iata);


	void saveAirport(AirportVO airport);

	void updateAirport(AirportVO airport);

	void removeAirport(AirportVO airport);

	List<AirportVO> searchByCountry(String searchTerm, String countryCode);

}
