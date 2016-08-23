package com.big.web.b2b_big2.flight;

import com.big.web.b2b_big2.setup.dao.ISetupDao;

public class BasicAirlineHandler {

	protected ISetupDao setupDao;
	protected Airline airline;
	
	public BasicAirlineHandler(Airline airline, ISetupDao setupDao){
		this.airline = airline;
		this.setupDao = setupDao;
	}
	
}
