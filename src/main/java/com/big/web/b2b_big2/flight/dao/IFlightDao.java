package com.big.web.b2b_big2.flight.dao;

import java.util.Date;
import java.util.List;

import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.model.FlightAvailSeatVO;
import com.big.web.b2b_big2.flight.pojo.FlightSearchB2B;
import com.big.web.b2b_big2.flight.pojo.FlightSearchResult;
import com.big.web.b2b_big2.flight.pojo.OriginDestinationRaw;
import com.big.web.b2b_big2.flight.pojo.SearchForm;
import com.big.web.b2b_big2.flight.pojo.Seat;

public interface IFlightDao {
	
	List<FlightSearchB2B> searchOneWayFlightB2B(Date departDate,
			String departIata, String destinationIata, List<Airline> airlines);

	List<FlightSearchB2B> searchOneWayFlightB2B(
			Date departDate, String departIata, String destinationIata,
			Airline airline);
	
	FlightAvailSeatVO searchTransitFlightB2B(String transitId);
	FlightAvailSeatVO searchFlightB2B(String uid);
	FlightSearchResult searchFlightB2B(SearchForm search);
	
	String getAirportCode(String city);
	String getAirportName(String iata);
	String getAirportNameAndCity(String iata);
	String getCity(String iata);
	
	Object getFlightClassFare(String flightavailid, String seatCls);
	Object getFlightClassFare(String flightavailid, String seatCls, Airline airline);
	FlightAvailSeatVO getFlightAvailSeat(String flightavailid);
	FlightAvailSeatVO getLatestFlightAvailSeat();
	
	void updateSeat(String flightAvailId, Seat[] seats) throws Exception;

	void saveSchedule(FlightAvailSeatVO obj);

	// scraping part
	OriginDestinationRaw getOrgDestFromRouteSave(String orgIata,
			String destIata, long webId);
}
