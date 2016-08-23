package com.big.web.b2b_big2.util;

import java.net.ConnectException;

import com.big.web.b2b_big2.flight.odr.exception.ODRException;

public class ErrorHandler {

	public static String friendlyErrorMsg(Exception e){
		if (e instanceof ConnectException)
			return e.getMessage() + ". Please Contact Administrator.";
		
		if (e instanceof ODRException)
			return "Sorry, Booking Failed. Please try again.";
			
		return e.getMessage();
	}
}
