package com.big.web.b2b_big2.flight.service;

import java.util.List;

import org.springframework.scheduling.annotation.Async;

import com.big.web.b2b_big2.flight.model.AbstractFlightClassFare;
import com.big.web.b2b_big2.flight.model.FlightAvailSeatVO;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.flight.pojo.FlightSearchB2B;
import com.big.web.b2b_big2.flight.pojo.FlightSearchResult;
import com.big.web.b2b_big2.flight.pojo.FlightSelect;
import com.big.web.b2b_big2.flight.pojo.SearchForm;
import com.big.web.b2b_big2.util.AppInfo;


public interface IFlightManager{
	
	/**
	 * @param search
	 * @return
	 */
	List<FlightSearchB2B> searchDepartFlightB2B(SearchForm search);
	
	/**
	 * @param search
	 * @return
	 */
	List<FlightSearchB2B> searchReturnFlightB2B(SearchForm search);
	
	/**
	 * Cara baru yg dipersiapkan utk menggantikan searchXXX
	 * @param city
	 * @return
	 */
	FlightSearchResult searchFlightB2B(SearchForm search);
	
	String getAirportCode(String city);
	String getCity(String iata);
	String getAirportName(String iata);
	
	FlightAvailSeatVO getFlightAvailSeat(String flightAvailId) throws Exception;
	
	
	/**
	 * Harga yg ditampilkan murni dari database, jadi jika ada aturan tertentu dr suatu airline you have to calculate manual.
	 */
	FareInfo getFareInfo(String flightavailid, String seatCls);
	
	List<AppInfo> getAppInfo();

	AbstractFlightClassFare getFlightClassFare(FlightSelect fs);

	@Async("updateSeat_executor")
	void syncSeat(SearchForm searchForm);
	
	/*
	BookingFlightVO createBooking(String flightdetid);
	void addBooking(BookingFlightVO bookingFlight);
	void updateBooking(BookingFlightVO bookingFlight);
	void removeBooking(BookingFlightVO bookingFlight);
*/
//	List<FlightSearchB2B> getDepartFlightB2B(SearchForm searchForm);
	String helloWorld(String name);
	FlightSearchResult searchFlightB2B(String departDateDDMMYYYY, String departIata,
			String destinationIata, List<String> airlines, int tripMode);

	void saveSchedule(FlightAvailSeatVO obj);
	
}
