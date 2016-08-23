package com.big.web.b2b_big2.flight.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.big.web.b2b_big2.country.dao.ICountryDao;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.api.kalstar.SqivaHandler;
import com.big.web.b2b_big2.flight.dao.IAirlineDao;
import com.big.web.b2b_big2.flight.dao.IAirportDao;
import com.big.web.b2b_big2.flight.dao.IFlightDao;
import com.big.web.b2b_big2.flight.model.AbstractFlightClassFare;
import com.big.web.b2b_big2.flight.model.AirlineVO;
import com.big.web.b2b_big2.flight.model.AirportVO;
import com.big.web.b2b_big2.flight.model.FlightAvailSeatVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareAirAsiaVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareCitilinkVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareGarudaVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareKalstarVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareNamVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareSriwijayaVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareVO;
import com.big.web.b2b_big2.flight.odr.ws.Service1;
import com.big.web.b2b_big2.flight.odr.ws.Service1Soap;
import com.big.web.b2b_big2.flight.pojo.Airport;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.flight.pojo.FlightSearchB2B;
import com.big.web.b2b_big2.flight.pojo.FlightSearchResult;
import com.big.web.b2b_big2.flight.pojo.FlightSelect;
import com.big.web.b2b_big2.flight.pojo.OriginDestinationRaw;
import com.big.web.b2b_big2.flight.pojo.Route;
import com.big.web.b2b_big2.flight.pojo.SearchForm;
import com.big.web.b2b_big2.flight.service.IFlightManager;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.AppInfo;
import com.big.web.b2b_big2.util.Utils;

@Service("flightManager")
@WebService(serviceName = "FlightService", endpointInterface = "com.big.web.service.FlightService" )
@Transactional
public class FlightManagerImpl implements IFlightManager {
	private static final Log log = LogFactory.getLog(FlightManagerImpl.class);
	@Autowired IFlightDao flightDao; // jgn rename menjadi 'dao' karena di parent class
							// udah dipake

	@Autowired
	ISetupDao setupDao;
	
	@Autowired
	IAirportDao airportDao;

	@Autowired
	IAirlineDao airlineDao;

	@Autowired
	private ICountryDao countryDao;

	@Override
	public String getAirportCode(String city) {
		return flightDao.getAirportCode(city);
	}

	@Override
	public String getCity(String iata) {
		return flightDao.getCity(iata);
	}

	@Override
	public String getAirportName(String iata) {
		return flightDao.getAirportName(iata);
	}

	@Override
	public List<FlightSearchB2B> searchDepartFlightB2B(SearchForm search) {
		//aturannya currentHours plus 2 jam. jika pemesanan dilakukan jam 20 ke atas berarti otomatis tidak tersedia

		Date departDate = search.parseDepartDate();

		/*
		Date today = new Date();
		if (DateUtils.isSameDay(departDate, today)) {
			//jika setelah ditambah 2 jam berganti hari berarti tidak tersedia
			departDate = DateUtils.addHours(departDate, 2);
			
			if (!DateUtils.isSameDay(departDate, today)) return null;			
		}
		*/
		
		List<FlightSearchB2B> list;
		try {
			
			List<Airline> airlines = search.getAirlines();
			
			for (int i = airlines.size()-1; i >= 0; i--){
				//aviastar.search.forbid=1
				String _key = airlines.get(i).name().toLowerCase() + ".search.forbid";
				
				if (setupDao.getValueAsBoolean(_key)) {
					airlines.remove(i);
				}
				
			}

			list = flightDao.searchOneWayFlightB2B(
					departDate, search.getDepartIata(), search.getDestinationIata(), airlines);			
		} catch (ParseException e) {
			e.printStackTrace();
			
			return null;
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FlightSearchB2B> searchReturnFlightB2B(SearchForm search) {
		// tinggal swap aja
		List<FlightSearchB2B> list;
		try {
			List<Airline> airlines = search.getAirlines();
			
			for (int i = airlines.size()-1; i >= 0; i--){
				//aviastar.search.forbid=1
				String _key = airlines.get(i).name().toLowerCase() + ".search.forbid";
				
				if (setupDao.getValueAsBoolean(_key)) {
					airlines.remove(i);
				}
				
			}

			list = flightDao.searchOneWayFlightB2B(
					search.parseReturnDate(), search.getDestinationIata(), search.getDepartIata(), airlines);
			
			//TODO testing only. please remove when done
			/*
			if (search.isCitilink()){
				List<FlightSearchB2B> listCitilink= flightDao.searchOneWayFlightB2BCitilink(
						search.parseReturnDate(), search.getDestinationIata(), search.getDepartIata(), search.getAirlines(), search.isSearchPast());
				list =  ListUtils.union(list, listCitilink);
			}

			if (search.isKalstar()){
				
				List<FlightSearchB2B> listKalstar = flightDao.searchOneWayFlightB2BKalstar(
						search.parseReturnDate(), search.getDestinationIata(), search.getDepartIata(), search.getAirlines(), search.isSearchPast());
				list = ListUtils.union(list, listKalstar);
			}
			*/
		} catch (ParseException e) {
			e.printStackTrace();
			
			return null;
		}

		return list;
	}

	@Override
	public FlightAvailSeatVO getFlightAvailSeat(String flightAvailId) throws Exception {
		
		FlightAvailSeatVO fas = flightDao.getFlightAvailSeat(flightAvailId);
		if (fas == null) throw new RuntimeException("Cant find a valid Schedule !");
		
		return fas;
	}
	
	/**
	 * Fare per adult. usually child fare = adult fare. infant is unhandled yet
	 */
	@Override
	public FareInfo getFareInfo(String flightavailid, String seatCls) {
		
		FareInfo data = new FareInfo();
		
		FlightAvailSeatVO fas = flightDao.getFlightAvailSeat(flightavailid);
		
		Airline airline = Airline.getAirline(fas.getAirline());
		
		Object fcf = flightDao.getFlightClassFare(flightavailid, seatCls, airline);
		
		if (fcf == null || fas == null) return new FareInfo();

		switch (airline){
			case KALSTAR:
				data = ((FlightClassFareKalstarVO)fcf).getFareInfo(data);
				break;
			case CITILINK:
				data = ((FlightClassFareCitilinkVO)fcf).getFareInfo(data);
				break;
			case GARUDA:
				data = ((FlightClassFareGarudaVO)fcf).getFareInfo(data);
				break;
			case AVIASTAR:
			case BATIK:
			case LION:
			case MALINDO:
			case SUSI:
			case TRIGANA:
			case EXPRESS:
				data = ((AbstractFlightClassFare)fcf).getFareInfo(data);
				break;
			case SRIWIJAYA:
				data = ((FlightClassFareSriwijayaVO)fcf).getFareInfo(data);
				break;
			case NAM:
				data = ((FlightClassFareNamVO)fcf).getFareInfo(data);
				break;
			case AIRASIA:
				data = ((FlightClassFareAirAsiaVO)fcf).getFareInfo(data);
				break;
			default:
				data = ((FlightClassFareVO)fcf).getFareInfo(data, airline);
				break;
		}
		
		//override some info
		data.setAirline(fas.getAirline());
		data.setCurrency(fas.getCurrency().trim());
		data.setFlightNo(fas.getFlightnum());
		data.setIsTransit(fas.getIstransit());
		
		
		AirlineVO vo = airlineDao.findByName(fas.getAirline());
		data.setAirlineIata(vo.getCode());
		data.setAgentId(vo.getAgentId());
		
		
		AirportVO a = airportDao.getAirportByIata(fas.getDeparture());
		Airport _from = new Airport();
		_from.setCity(a.getCity());
//		_from.setCountry(countryDao.getCountryById(a.getCountry_id()));
		_from.setCountryId(a.getCountry_id());
		_from.setIataCode(a.getIata());
		_from.setIcaoCode(a.getIcao());
		_from.setName(a.getAirport_name());
		
		_from.setSchedule(fas.getDep_time());
		
		
		AirportVO b = airportDao.getAirportByIata(fas.getArrival());
		Airport _to = new Airport();
		_to.setCity(b.getCity());
//		_to.setCountry(countryDao.getCountryById(b.getCountry_id()));
		_to.setCountryId(b.getCountry_id());
		_to.setIataCode(b.getIata());
		_to.setIcaoCode(b.getIcao());
		_to.setName(b.getAirport_name());
		
		_to.setSchedule(fas.getArr_time());

		Route route = new Route(_from, _to);			
		data.setRoute(route);
		
		return data;
	}

	/**
	 * disiapkan sebagai solusi baru yang lengkap dan mudah 
	 */
	@Override
	public FlightSearchResult searchFlightB2B(SearchForm search) {
		return flightDao.searchFlightB2B(search);
	}

	@Override
	public AbstractFlightClassFare getFlightClassFare(FlightSelect fs) {
		return (AbstractFlightClassFare) flightDao.getFlightClassFare(fs.getFlightavailid(), fs.getSeat().getKelas(), fs.getAirline());
	}

	/* 
	 * Jangan dipake dulu. Kelemahan ga bisa async utk masing2 airline, harusnya async based on each line.
	 * kalo dipaksa pake, berakibat time take too long. Seharusnya masing2 airline ada handlernya sendiri2.
	 * 
	 * WARNING ! Async must be called from outside class, not within this class
	 * please check with applicationContext-service executor tag
	 * @see com.big.web.b2b_big2.flight.service.IFlightManager#syncSeat(com.big.web.b2b_big2.flight.pojo.SearchForm)
	 */
	@Override
	@Async("updateSeat_executor")
	public void syncSeat(SearchForm searchForm) {
		
		if (setupDao.getValueAsBoolean(ISetupDao.KEY_SYNC_SEAT_DISABLED)){
			log.info("Want to update Seat " + Utils.listToCSV(searchForm.getAirlines()) + " but Update Seat is disabled at setup");
			return;
		}
		
		long sleepInterval = setupDao.getValueAsLong(ISetupDao.KEY_SYNC_SEAT_SLEEP);
		if (sleepInterval < 1000) 
			sleepInterval = 1000;
		

		for (Airline airline: searchForm.getAirlines()){
			
			log.debug("SyncSeat airline " + airline + " using Thread " + Thread.currentThread().getName() + " start at " + new Date());
			
			switch (airline){
			case AVIASTAR:
			case TRIGANA:
			case KALSTAR:
					SqivaHandler kalstarHandler = new SqivaHandler(airline, setupDao);
					@SuppressWarnings("unchecked")
					List<FlightSearchB2B> _schedules = (List<FlightSearchB2B>) kalstarHandler.getSchedules(searchForm);
					
					for (FlightSearchB2B _schedule : _schedules){
						try {
							flightDao.updateSeat(_schedule.getFlightAvailId(), _schedule.getSeats());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				break;
			case AIRASIA:
				Hashtable<String, String> keyValue = new Hashtable<String, String>();
				keyValue.put("mode", "SCHED");
				keyValue.put("faid", "N/A");
				keyValue.put("airline", airline.getAirlineCode());
//				keyValue.put("lastUpdate", Utils.Date2String(row.getLastUpdate(), "yyyyMMddHHmmss"));
				OriginDestinationRaw orgDestRaw;
				try {
					orgDestRaw = new OriginDestinationRaw(searchForm.getDepartIata(), searchForm.getDestinationIata());

					keyValue.put("date", Utils.Date2String(searchForm.parseDepartDate(), "dd/MM/yyyy"));
					keyValue.put("org", orgDestRaw.getOrigin());
					keyValue.put("dst", orgDestRaw.getDestination());
					keyValue.put("adt", String.valueOf(searchForm.getAdult() + searchForm.getChildren()));
					
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				}
				
				break;
			case LION:
			case BATIK:
			case WINGS:
				break;
			case SRIWIJAYA:
			case NAM:
			case GARUDA:
			case EXPRESS:
				default:
					Service1 svc = new Service1();
					Service1Soap soap = svc.getService1Soap();
					
					String sDepartDate = Utils.Date2String(searchForm.parseDepartDate(), "dd/MM/yyyy");
				try {
					Object ret = soap.updateSchedule(searchForm.getDepartIata(), searchForm.getDestinationIata(), sDepartDate, airline.getAirlineCode());
				} catch (ParseException e) {
					e.printStackTrace();
				}

					break;
				
			}
		}
		

		try {
            Thread.sleep(sleepInterval);	//gave it a time to rest
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
		
		log.debug("SyncSeat end "  + Thread.currentThread().getName() + " " + new Date());
		
	}

	@Override
	public List<AppInfo> getAppInfo() {
		List<AppInfo> list = new ArrayList<AppInfo>();
		list.add(new AppInfo("All Routes", setupDao.getCountFromTable(FlightAvailSeatVO.TABLE_NAME, "dep_time >= sysdate")));
		
		FlightAvailSeatVO fas = flightDao.getLatestFlightAvailSeat();
		
		// <c:url value="/admin/listPeople"/>
		// <img class="img-thumbnail" src="<c:url value='/feeds/getThumb?imagePath=${newsItem.thumbId}' />" width="100px" height="auto" />
		// <link rel="stylesheet" href="<c:url value="/styles/jquery-ui.css"/>" />
		//  <a href="${ctx}/admin/monitor" class="btn btn-primary">
		// myContextPath
		try {
			StringBuffer sb  = new StringBuffer(fas.getAirline())
									.append(" ").append(fas.getFlightnum())
									.append(",&nbsp;<a href='../admin/airports?q=")
//								.append(",&nbsp;<a href='${myContextPath}/admin/airports?q=")
//								.append(",&nbsp;<a href=\"<c:url value='/admin/airports?q=")
//								.append(",<a href='admin/airports?q=")
											.append(fas.getOrigin())
											.append(",")
											.append(fas.getDestination())
									.append("'>")
//								.append("'/>\">")
									.append(fas.getOrigin()).append("-").append(fas.getDestination())
									.append("</a> ").append(fas.getDep_time())
			;
			
			list.add(new AppInfo("Latest Addition", sb.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		list.add(new AppInfo("Today Flights", setupDao.getCountFromTable(FlightAvailSeatVO.TABLE_NAME, "trunc(created_date)=trunc(sysdate)")));
		
		list.add(new AppInfo("Yesterday Flights", setupDao.getCountFromTable(FlightAvailSeatVO.TABLE_NAME, "trunc(created_date)=trunc(sysdate-1)")));
		
		return list;
	}

	@Override
	public String helloWorld(String name) {
		return "Hello " + name;
	}

	@Override
	public FlightSearchResult searchFlightB2B(String departDateDDMMYYYY,
			String departIata, String destinationIata, List<String> airlines,
			int tripMode) {
		
		SearchForm search = new SearchForm();
		search.setDepartDate(Utils.String2Date(departDateDDMMYYYY, "ddMMyyyy"));
		search.setDepartCity(departIata);
		search.setDestCity(destinationIata);
		search.setAirlines(Utils.listToArray(airlines));
		search.setTripMode(tripMode);
		
		search.setAdult(1);
		search.setChildren(0);
		search.setInfants(0);
		
		FlightSearchResult result = searchFlightB2B(search);
		return result;
	}

	@Override
	public void saveSchedule(FlightAvailSeatVO obj) {
		flightDao.saveSchedule(obj);
	}


}