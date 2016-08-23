package com.big.web.b2b_big2.flight.dao;

import java.util.Date;
import java.util.List;

import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.model.AirlineVO;
import com.big.web.b2b_big2.flight.model.ODRStatusVO;
import com.big.web.b2b_big2.flight.pojo.Route;
import com.big.web.dao.GenericDao;

public interface IAirlineDao extends GenericDao<AirlineVO, Long>{
	AirlineVO findByName(String airlineName);
	List<AirlineVO> findByNames(String[] airlines);
	AirlineVO findByCode(String airlineIata);
	Date getLastDate(Airline airline);
	
	List<Route> getRoutes(Airline airline, Date from, Date to);

	void update(AirlineVO airline);
	
	List<ODRStatusVO> getODRStatus();
	void saveOdrStatus(List<ODRStatusVO> list);
	void saveOdrStatus(ODRStatusVO obj);
}
