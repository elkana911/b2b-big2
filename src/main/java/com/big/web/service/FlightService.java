package com.big.web.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.big.web.b2b_big2.flight.pojo.FlightSearchResult;

@WebService
@Path("/flights")
public interface FlightService {

	@GET
	@Path("{name}")
	String helloWorld(@PathParam("name") String name);

	@GET
	FlightSearchResult searchFlightB2B(String departDateDDMMYYYY, String departIata,
			String destinationIata, List<String> airlines, int tripMode);
}
