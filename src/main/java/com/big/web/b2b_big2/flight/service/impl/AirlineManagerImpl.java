package com.big.web.b2b_big2.flight.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.dao.IAirlineDao;
import com.big.web.b2b_big2.flight.model.AirlineVO;
import com.big.web.b2b_big2.flight.model.FlightAvailSeatVO;
import com.big.web.b2b_big2.flight.model.ODRStatusVO;
import com.big.web.b2b_big2.flight.pojo.Route;
import com.big.web.b2b_big2.flight.service.IAirlineManager;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.AppInfo;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.service.impl.GenericManagerImpl;

@Service("airlineManager")
@Transactional
public class AirlineManagerImpl extends GenericManagerImpl<AirlineVO, Long> implements IAirlineManager{

	@Autowired ISetupDao setupDao;

	IAirlineDao airlineDao;	//jgn rename menjadi 'dao' karena di parent class udah dipake
	
	@Autowired
	public AirlineManagerImpl(IAirlineDao airlineDao) {
		super(airlineDao);
		this.airlineDao = airlineDao; 
	}
	
	@Override
	public List<AirlineVO> search(String searchTerm) {
		return super.search(searchTerm, AirlineVO.class);
	}

	@Override
	public List<AirlineVO> getAirlines() {
		return airlineDao.getAllDistinct();
	}

	@Override
	public AirlineVO getAirline(String airlineId) {
		return airlineId == null ? null : airlineDao.get(Long.parseLong(airlineId));
	}

//	@Override
//	public void saveOrUpdateData(AirlineVO airline) {
//		airlineDao.saveOrUpdateData(airline);		
//	}
	
	@Override
	public void remove(Long id) {
		airlineDao.remove(id);
	}


	@Override
	public void add(AirlineVO airline) {
		airlineDao.save(airline);
	}


	@Override
	public void update(AirlineVO airline) {
		airlineDao.update(airline);
	}

	@Override
	public List<Route> getRoutes(Airline airline, Date from, Date to) {
		return airlineDao.getRoutes(airline, from, to);
	}

	@Override
	public List<AppInfo> getAppInfo() {
		List<AppInfo> list = new ArrayList<AppInfo>();

		Set<Airline> lowestRoutes = new LinkedHashSet<Airline>();
		Set<Airline> highestRoute = new LinkedHashSet<Airline>();

		Set<Airline> highestDF = new LinkedHashSet<Airline>();
		Set<Airline> lowestDF = new LinkedHashSet<Airline>();
		
		Set<Airline> highestTF = new LinkedHashSet<Airline>();
		Set<Airline> lowestTF = new LinkedHashSet<Airline>();
		
		long lastMaxRoutes = 0;
		long lastMinRoutes = -1;
		long lastMaxDF = 0;
		long lastMinDF = -1;
		long lastMaxTF = 0;
		long lastMinTF = -1;
		for (Airline airline : Airline.values()){
			
			if (airline == Airline.UNKNOWN) continue;

			Date dt = airlineDao.getLastDate(airline);
			list.add(new AppInfo(airline.name() + " Latest Date", dt == null ? "N/A" : Utils.Date2String(dt, "dd MMM yyyy")));
			
			long totalRoutes = setupDao.getCountFromTable(FlightAvailSeatVO.TABLE_NAME, "dep_time >= sysdate and airline='" + airline.getAirlineName() + "'");
			
			if (totalRoutes >= lastMaxRoutes){
				if (lastMaxRoutes != totalRoutes) highestRoute.clear();
				lastMaxRoutes = totalRoutes;
				highestRoute.add(airline);
			}
			if (lastMinRoutes == -1) lastMinRoutes = totalRoutes;
			if (totalRoutes <= lastMinRoutes){
				if (lastMinRoutes != totalRoutes) lowestRoutes.clear();
				lastMinRoutes = totalRoutes;
				lowestRoutes.add(airline);
			}
			
			list.add(new AppInfo(airline.name() + " Total Routes", totalRoutes));
			
			long todayRoutes = setupDao.getCountFromTable(FlightAvailSeatVO.TABLE_NAME, "airline='" + airline.getAirlineName() + "' and to_char(dep_time, 'ddmmrrrr') = to_char(sysdate, 'ddmmrrrr')");
			list.add(new AppInfo(airline.name() + " Today Routes", todayRoutes));

			long directF = setupDao.getCountFromTable(FlightAvailSeatVO.TABLE_NAME, "airline='" + airline.getAirlineName() + "' and dep_time >= sysdate and istransit=0");
			
			if (directF >= lastMaxDF){
				if (lastMaxDF != directF) highestDF.clear();
				lastMaxDF = directF;
				highestDF.add(airline);
			}
			if (lastMinDF == -1) lastMinDF = directF;
			if (directF <= lastMinDF){
				if (lastMinDF != directF) lowestDF.clear();
				lastMinDF = directF;
				lowestDF.add(airline);
			}

			list.add(new AppInfo(airline.name() + " Direct Flights", directF));

			long transitF = setupDao.getCountFromTable(FlightAvailSeatVO.TABLE_NAME, "airline='" + airline.getAirlineName() + "' and dep_time >= sysdate and istransit=1"); 
			if (transitF >= lastMaxTF){
				if (lastMaxTF != transitF) highestTF.clear();
				lastMaxTF = transitF;
				highestTF.add(airline);
			}
			if (lastMinTF == -1) lastMinTF = transitF;
			if (transitF <= lastMinTF){
				if (lastMinTF != transitF) lowestTF.clear();
				lastMinTF = transitF;
				lowestTF.add(airline);
			}
			list.add(new AppInfo(airline.name() + " Transit Flights", transitF));
			/*
			long brokenCF = setupDao.getCountFromTable(FlightAvailSeatVO.TABLE_NAME, "airline='" + airline.getAirlineName() + "' and dep_time >= sysdate and istransit=1"); 
			list.add(new AppInfo(airline.name() + " Broken Class Fare", brokenCF));
			select count(1) from flight_class_fare_airasia
			where flightavailid not in (select flightavailid from flightavailseat_clean where airline='Air Asia')
			 */
			
		}

		//airline lowest price, routes etc. nilai disini tidak mungkin ditarik disini, hanya dpt diisi oleh user saat pencarian
		//kategori kurang jelas krn bisa aja beda2 tiap harinya
		/*
		list.add(new AppInfo("Lowest Price", Airline.UNKNOWN.name()));
		
		//airline highest price, routes
		list.add(new AppInfo("Highest Price", Airline.UNKNOWN.name()));
		*/
		//highest routes
		list.add(new AppInfo("Highest Routes", Utils.arrayToCsv(highestRoute)));
		//highest direct flights
		list.add(new AppInfo("Highest Direct Flights", Utils.arrayToCsv(highestDF)));
		
		//highest transit flights
		list.add(new AppInfo("Highest Transit Flights", Utils.arrayToCsv(highestTF)));

		//lowest routes
		list.add(new AppInfo("Lowest Routes", Utils.arrayToCsv(lowestRoutes)));

		//lowest direct flights
		list.add(new AppInfo("Lowest Direct Flights", Utils.arrayToCsv(lowestDF)));
		
		//lowest transit flights
		list.add(new AppInfo("Lowest Transit Flights", Utils.arrayToCsv(lowestTF)));

		return list;
	}

	@Override
	public AirlineVO getAirlineByCode(String airlineIata) {
		return airlineDao.findByCode(airlineIata);
	}

	@Override
	public void saveOdrStatus(ODRStatusVO odrStatusVO) {
		airlineDao.saveOdrStatus(odrStatusVO);
	}
}
