package com.big.web.b2b_big2.flight.decorator;


public class FlightReturnB2BTableDecorator extends FlightB2BTableDecorator {

	@Override
	String getUniqueParam() {
		return "returnrate";
	}

}
