package com.big.web.b2b_big2.flight.api.kalstar;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.big.web.b2b_big2.flight.api.kalstar.action.paytype.ResponseGetPayType;
import com.big.web.b2b_big2.flight.api.kalstar.action.sendticket.ResponseSendTicket;

public class KalstarHandlerTest extends TestCase{

    private final Log log = LogFactory.getLog(KalstarHandlerTest.class);

	@Test
	public void testKnownBasisFare() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetActiveRoutes() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSchedules() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetFareDetails() {
		fail("Not yet implemented");
	}

	@Test
	public void testCancel() {
		fail("Not yet implemented");
	}

	@Test
	public void testBookingRequestBooking() {
		fail("Not yet implemented");
	}

	@Test
	public void testPayment() {
		fail("Not yet implemented");
	}

	@Test
	public void testTestJson() {
		fail("Not yet implemented");
	}

	@Test
	public void testBookingFlightCart() {
		fail("Not yet implemented");
	}

	@Test
	public void testMain() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetBalance() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPayType() {
		/*
		SqivaHandler h = new SqivaHandler();
		
		try {
			ResponseGetPayType resp = h.getPayType();
			
			assertTrue(!resp.isError());
			log.debug(resp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}

	@Test
	public void testSendTicket() {
		/*
		SqivaHandler h = new SqivaHandler();
		
		try {
			ResponseSendTicket resp = h.sendTicket("9UX7JJ", "elkana911@yahoo.com");
			
			assertTrue(!resp.isError());
			log.debug(resp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}

}
