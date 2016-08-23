package com.big.web.b2b_big2.flight.api.kalstar.action;

import com.big.web.b2b_big2.flight.api.kalstar.exception.SqivaException;

public interface IRequest {
//    final String API_KEY = "5EB9FE68-8915-11E0-BEA0-C9892766ECF2";
//	final String rootUrl = "http://ws.demo.awan.sqiva.com/?rqid=" + API_KEY + "&airline_code=W2";
	
	void validate() throws SqivaException;
}
