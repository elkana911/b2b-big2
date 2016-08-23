package com.big.web.b2b_big2.booking.service;

import java.math.BigDecimal;
import java.util.List;

import com.big.web.b2b_big2.agent.pojo.Agent;
import com.big.web.b2b_big2.booking.model.BookingFlightDtlVO;
import com.big.web.b2b_big2.booking.model.BookingFlightSchedVO;
import com.big.web.b2b_big2.booking.model.BookingFlightTransVO;
import com.big.web.b2b_big2.booking.model.BookingFlightVO;
import com.big.web.b2b_big2.flight.pojo.BookingFormB2B;
import com.big.web.b2b_big2.flight.pojo.ContactCustomer;
import com.big.web.b2b_big2.flight.pojo.PERSON_TYPE;
import com.big.web.b2b_big2.flight.pojo.PNR;
import com.big.web.b2b_big2.util.AppInfo;

public interface IBookingManager {
	
	void startSlowProcess();
	
	//flight
	List<BookingFlightVO> findBookingFlight(String username);
	void saveBookingFlight(BookingFlightVO obj);
	void saveBookingFlightDtl(BookingFlightDtlVO obj);
	void saveBookingFlightSched(BookingFlightSchedVO obj);
	void saveBookingFlightTrans(BookingFlightTransVO obj);

	void cancelTicket(String id) throws Exception;
//	List<BookingFlightVO> createBookingFlights(List<String> depFlights, List<String> retFlights, Agent agent);
	
	/**
	 * Hanya melakukan insert ke database tanpa perlu booking ke airline
	 * @param agent
	 * @return
	 * @throws Exception
	 */
	List<BookingFlightVO> generateBooking(Agent agent) throws Exception;
	void bookingFromAirline(List<BookingFlightVO> list, Agent agent) throws Exception;

	void issuedTicket(String bookCode) throws Exception;
//	BookingFlightVO getPNR(String bookCode);
//	List<BookingFlightVO> getPNRs(String bookingCode);
	
	/**
	 * @deprecated
	 * @see #getBookingFlights(String)
	 * @param uid
	 * @return
	 */
	BookingFlightVO getBookingFlight(String uid);
	
	/**
	 * Search multi booking flights by its groupCode.
	 * Recommend when retrieve booking or search ticket
	 */
	List<BookingFlightVO> getBookingFlights(String uid);
	List<BookingFlightDtlVO> getBookingFlightDetails(String booking_flight_id);
	List<BookingFlightSchedVO> getBookingFlightSchedules(String booking_flight_id);
	BookingFlightTransVO getBookingFlightTrans(String booking_flight_id);
	
	/**
	 * 
	 * @param dateFrom
	 * @param dateTo
	 * @param airlineCode null to select all airline
	 * @param userName
	 * @param status set null to display all
	 * @return
	 * @throws Exception
	 */
	List<BookingFlightVO> getBookingFlight(String dateFrom, String dateTo, String[] airlineCode, String userName, String[] status) throws Exception;
	BookingFlightVO getBookingFlightByBookingCode(String bookingCode);
	void sendTicket(String bookCode, String email) throws Exception;
	/**
	 * Get expired booking flight based on timetopay and status HOLD or HK
	 * @return
	 */
	List<BookingFlightVO> getExpiredBookingFlights();

	List<PNR> getPnrList(PERSON_TYPE personType, Long userId);
	List<ContactCustomer> getContactCustomer(Long userId);
	/**
	 * Display EXPIRED on expired TimeToPay
	 * @param bookingFlight
	 * @return
	 */
	List<BookingFlightVO> prettyList(List<BookingFlightVO> bookingFlight);

	/**
	 * To check whether any passenger is already booked on the same segment (same flight, same time, same origin-destination)
	 * @param bookingForm
	 * @return
	 */
	boolean isDoubleBooking(BookingFormB2B bookingForm);
	
	//hotel
	void printVoucherHotel(String bookCode) throws Exception;
	BigDecimal calculateAmountBeforeBooking(BookingFlightVO bf);
	List<AppInfo> getAppInfo();

	
	
	//rent car
	
}
