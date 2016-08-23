package com.big.web.service;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.big.web.b2b_big2.agent.model.AgentVO;
import com.big.web.b2b_big2.agent.pojo.Agent;
import com.big.web.b2b_big2.agent.service.IAgentManager;
import com.big.web.b2b_big2.booking.model.BookingFlightDtlVO;
import com.big.web.b2b_big2.booking.model.BookingFlightSchedVO;
import com.big.web.b2b_big2.booking.model.BookingFlightVO;
import com.big.web.b2b_big2.booking.service.IBookingManager;
import com.big.web.b2b_big2.finance.cart.pojo.FlightCart;
import com.big.web.b2b_big2.flight.model.FlightAvailSeatVO;
import com.big.web.b2b_big2.flight.pojo.BasicContact;
import com.big.web.b2b_big2.flight.pojo.BookingFormB2B;
import com.big.web.b2b_big2.flight.pojo.PERSON_TYPE;
import com.big.web.b2b_big2.flight.pojo.PNR;
import com.big.web.b2b_big2.flight.pojo.Title;
import com.big.web.b2b_big2.flight.service.IFlightManager;
import com.big.web.b2b_big2.util.Utils;

public class BookingManagerTest extends BaseManagerTestCase {
    private Log log = LogFactory.getLog(BookingManagerTest.class);
    
    @Autowired
    private IBookingManager bookingMgr;
    
    @Autowired
    private IFlightManager flightMgr;

    @Autowired
    private IAgentManager agentMgr;

    @Test
    @Ignore
    public void testDoubleBooking() throws Exception{
    	
    	String originIata = "CGK";
    	String destIata = "DPS";
    	Date departTime = Utils.String2Date("21/08/2015", "dd/MM/yyyy");
    	Date arrivalTime = Utils.addHours(departTime, 2);
    	String flightNo = "ABC123";
    	
    	
    	// 1. add dummy schedule
    	FlightAvailSeatVO fasVO = new FlightAvailSeatVO();
    	fasVO.setOrigin(originIata);
    	fasVO.setDestination(destIata);
    	fasVO.setDep_time(departTime);
    	fasVO.setArr_time(arrivalTime);
    	fasVO.setFlightnum(flightNo);
    	flightMgr.saveSchedule(fasVO);
    	
    	// 2. add to cart
    	FlightCart flightCart = new FlightCart();
    	flightCart.setOriginIata(originIata);
    	flightCart.setDestinationIata(destIata);
    	flightCart.setTripMode(0);
    	flightCart.setDepartDate(departTime);
//    	flightCart.setReturnDate(returnDate);
    	flightCart.setUseInsurance(false);
//    	flightCart.setDepartFlightItinerary(departFlightItinerary);
//    	flightCart.setPnr(pnr);
//    	flightCart.setCustomer(customer);
    	
    	BookingFlightVO bfVO = new BookingFlightVO(flightCart);
    	
    	bookingMgr.saveBookingFlight(bfVO);
    	
    	// 3. add dummy adult pnr
    	BasicContact adult1 = new BasicContact();
    	adult1.setFullName("ERIC ELKANA TARIGAN");
    	adult1.setTitle(Title.MR);
    	adult1.setBirthday(Utils.String2Date("11/09/1977", "dd/MM/yyyy"));

    	PNR pnr = new PNR(adult1, PERSON_TYPE.ADULT);
    	// 4. add details
    	BookingFlightDtlVO bfDtlVO = new BookingFlightDtlVO(pnr);
    	
    	bfDtlVO.setBooking_flight_id(bfVO.getId());
    	
    	bookingMgr.saveBookingFlightDtl(bfDtlVO);
    	
    	// 5. add schedule
    	BookingFlightSchedVO bfSchedVO = new BookingFlightSchedVO();
    	bfSchedVO.setBooking_flight_id(bfVO.getId());
    	bfSchedVO.setFlightNum(flightNo);
    	bfSchedVO.setDep_time(departTime);
    	bfSchedVO.setDeparture(originIata);
    	bfSchedVO.setArrival_time(arrivalTime);
    	bfSchedVO.setArrival(destIata);
    	bfSchedVO.setClass_name("K");
    	bfSchedVO.setLastUpdate(new Date());
    	bookingMgr.saveBookingFlightSched(bfSchedVO);
    	
    	// check double
    	
    	List<BasicContact> adult = new ArrayList<BasicContact>();
    	adult.add(adult1);
    	
    	BookingFormB2B bookingForm = new BookingFormB2B();
    	bookingForm.setAdult(adult);
    	
    	bookingForm.setDepId(fasVO.getFlightavailid());
    	
    	// 5. pastiin dulu dummy data memang ada
    	assertTrue(flightMgr.getFlightAvailSeat(fasVO.getFlightavailid()) != null);
    	
    	List<BookingFlightVO> bookingFlights = bookingMgr.getBookingFlights(bfVO.getId());
    	assertTrue(!Utils.isEmpty(bookingFlights));
    	
    	List<BookingFlightDtlVO> bookingFlightDetails = bookingMgr.getBookingFlightDetails(bfVO.getId());
    	assertTrue(!Utils.isEmpty(bookingFlightDetails));

    	List<BookingFlightSchedVO> bookingFlightSchedules = bookingMgr.getBookingFlightSchedules(bfVO.getId());
    	assertTrue(!Utils.isEmpty(bookingFlightSchedules));
    	
    	boolean betul = bookingMgr.isDoubleBooking(bookingForm);
    	
    	System.out.println("double booking ? " + (betul ? "iya tuh" : "kaga tuh"));
    	
    }
    
    @Test
    public void testAsyncBooking() throws Exception{
    	
//    	List<BookingFlightVO> list = bookingMgr.getBookingFlights("4f661a82-ea48-4ccc-8d3e-d02c36cb4905");
    	List<BookingFlightVO> list = bookingMgr.getBookingFlights("846ce6b9-971a-4803-941e-5fa9f265f505");
    	
		AgentVO agentVO = agentMgr.getAgentByUser("elkana");
		
    	Agent agent = new Agent();
    	agent.loadFromModel(agentVO, null, null);

    	// jika mau test list satu aja
    	if (list.size() > 1)
    		list.remove(0);
    	
    	bookingMgr.bookingFromAirline(list, agent);
    	
    	System.out.println("Finish testAsyncBooking");
    }
    
}
