package com.big.web.b2b_big2.flight.service;

import java.util.Date;
import java.util.List;

import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.model.AirlineVO;
import com.big.web.b2b_big2.flight.model.ODRStatusVO;
import com.big.web.b2b_big2.flight.pojo.Route;
import com.big.web.b2b_big2.util.AppInfo;
import com.big.web.service.GenericManager;

public interface IAirlineManager extends GenericManager<AirlineVO, Long>{
	List<AirlineVO> search(String searchTerm);
	
	List<AirlineVO> getAirlines();
	AirlineVO getAirlineByCode(String airlineIata);
	
	void add(AirlineVO airline);
	void update(AirlineVO airline);
	void remove(AirlineVO airline);
	
	AirlineVO getAirline(String airlineId);
	
	List<Route> getRoutes(Airline airline, Date from, Date to);

	List<AppInfo> getAppInfo();

	void saveOdrStatus(ODRStatusVO odrStatusVO);


}
