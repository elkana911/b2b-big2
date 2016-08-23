package com.big.web.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.big.web.b2b_big2.booking.model.BookingFlightDtlVO;
import com.big.web.b2b_big2.booking.model.BookingFlightSchedVO;
import com.big.web.b2b_big2.booking.model.BookingFlightVO;

@WebService
@Path("/bookingService")
public interface BookingService {
	
	@GET
	@Path("{username}")
	List<BookingFlightVO> findBookingFlight(@PathParam("username") String username);
	
	@GET
	@Path("{uid}")
	List<BookingFlightVO> getBookingFlights(@PathParam("uid") String uid);
	
	@GET
	@Path("{booking_flight_id}")
	List<BookingFlightDtlVO> getBookingFlightDetails(@PathParam("booking_flight_id") String booking_flight_id);
	
	@GET
	@Path("{booking_flight_id}")
	List<BookingFlightSchedVO> getBookingFlightSchedules(@PathParam("booking_flight_id") String booking_flight_id);
	
}
