package com.big.web.b2b_big2.flight;

import com.big.web.b2b_big2.booking.dao.IBookingFlightDao;
import com.big.web.b2b_big2.flight.pojo.FareInfo;

public interface IAirlineHandler {
	
	char[] getEconomyClass();
	char[] getBusinessClass();
	char[] getPromoClass();
	
	void issuedTicket(String bookCode, IBookingFlightDao bookingFlightDao) throws Exception;
	
	//masih tentative penamaannya
	FareInfo calculateFareInfo(FareInfo fareInfo2, int adultCount, int childCount, int infantCount);

}
