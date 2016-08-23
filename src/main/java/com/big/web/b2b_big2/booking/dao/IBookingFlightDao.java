package com.big.web.b2b_big2.booking.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.big.web.b2b_big2.booking.model.BookingFlightDtlVO;
import com.big.web.b2b_big2.booking.model.BookingFlightSchedVO;
import com.big.web.b2b_big2.booking.model.BookingFlightTransVO;
import com.big.web.b2b_big2.booking.model.BookingFlightVO;
import com.big.web.b2b_big2.flight.RATE_TYPE;
import com.big.web.b2b_big2.flight.model.FlightAvailSeatVO;
import com.big.web.b2b_big2.flight.pojo.BasicContact;
import com.big.web.b2b_big2.flight.pojo.BookingFormB2B;
import com.big.web.b2b_big2.flight.pojo.ContactCustomer;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.flight.pojo.PERSON_TYPE;
import com.big.web.b2b_big2.flight.pojo.PNR;
import com.big.web.model.LabelValue;

public interface IBookingFlightDao{
	//flight
	List<BookingFlightVO> findBookingFlight(String username);
	void saveBookingFlight(BookingFlightVO obj);
	void saveBookingFlightDtl(BookingFlightDtlVO obj);
	void saveBookingFlightSched(BookingFlightSchedVO obj);
	void saveBookingFlightTrans(BookingFlightTransVO obj);
	void cancelBookingFlight(String id);

//	List<BookingFlightVO> getPNRs(String bookingCode);	ambigue dengan List<PNR> getPNRs

	BookingFlightVO getBookingFlightByBookingCode(String bookingCode);
	
	/**
	 * @deprecated
	 * @see #getBookingFlights(String)
	 * @param uid
	 * @return
	 */
	BookingFlightVO getBookingFlight(String uid);
	
	/**
	 * Search paired booking code by using single uid only.
	 * @param uid
	 * @return
	 */
	List<BookingFlightVO> getBookingFlights(String uid);
	
	List<BookingFlightVO> getBookingFlight(Date from, Date to, String[] airlineCode, String userName, String[] status);
	
	/**
	 * 
	 * @param booking_flight_id
	 * @return
	 * @see #getBookingFlightDetails(String)
	 * @see #getPaxCountWithoutInfant(String)
	 */
	List<PNR> getPNRs(String booking_flight_id);
	BasicContact getPNR(String fullName);
	
	/**
	 * 
	 * @param booking_flight_id
	 * @return
	 * @see #getPNRs(String)
	 */
	int getPaxCountWithoutInfant(String booking_flight_id);
	
	/**
	 * 
	 * @param booking_flight_id
	 * @return
	 * @see #getPNRs(String)
	 */
	List<BookingFlightDtlVO> getBookingFlightDetails(String booking_flight_id);
	List<BookingFlightSchedVO> getBookingFlightSchedules(String booking_flight_id);
	BookingFlightTransVO getBookingFlightTrans(String booking_flight_id);
	/**
	 * Get expired booking flight based on timetopay and status HOLD or HK.
	 * @return
	 */
	List<BookingFlightVO> getExpiredBookingFlights();
	
	List<FareInfo> getFareInfosFromBookingFlightSchedules(String booking_flight_id);
	void cancelBookingByCode(String bookCode);
	void issuedBookingByCode(String bookCode);
	
	List<PNR> getUniquePNRs(PERSON_TYPE personType, Long userId);
	List<ContactCustomer> getContactCustomer(Long userId);
	Set<LabelValue> getBookingSummaryByStatus(String[] status);
	Set<LabelValue> getBookingFavouriteRoute();
	
	RATE_TYPE[] anyPriceChanged(String booking_flight_id);
	/**
	 * 
	 * @param bookingForm
	 * @param fas
	 * @return list of booking_flight_details.id
	 */
	List<String> isDoubleBooking(BookingFormB2B bookingForm, FlightAvailSeatVO fas);
	
	//hotel
	
	
	//rent car
	
}
