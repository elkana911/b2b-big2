package com.big.web.b2b_big2.flight.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;

import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.model.ODRStatusVO;
import com.big.web.b2b_big2.flight.pojo.FlightSearchB2B;

public interface IFlightAsyncService {
	
	List<ODRStatusVO> getODRStatus();
	void saveOdrStatus(List<ODRStatusVO> list);
	void saveOdrStatus(ODRStatusVO obj);
	
//	Future<List<FlightSearchB2B>> callDepartODR(Airline airline, SearchForm searchForm) throws IllegalArgumentException, IOException, InterruptedException, ParseException, ExecutionException;

	@Async("callODR_executor")
	Future<List<FlightSearchB2B>> callDepartODR(Airline airline, Date departDate, String departIata, String destinationIata, int adults, int children, int infants);
//	@Async("callODR_executor")
//	Future<List<FlightSearchB2B>> callDepartODR(Airline airline, Date departDate, String departIata, String destinationIata, int adults, int children, int infants)
//			throws IllegalArgumentException, IOException, InterruptedException, ParseException, ExecutionException;


//		
//	@Async("callODR_executor")
//	Future<List<FlightSearchB2B>> callReturnODR(Airline airline, Date departDate, String departIata, String destinationIata, int adults, int children, int infants)
//			throws IllegalArgumentException, IOException, InterruptedException, ParseException, ExecutionException;
	@Async
	Future<ODRStatusVO> callODRBackground(Airline airline, Date departDate, String departIata, String destinationIata, int adults, int children, int infants);
}
