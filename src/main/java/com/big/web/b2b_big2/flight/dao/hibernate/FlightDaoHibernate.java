package com.big.web.b2b_big2.flight.dao.hibernate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.PriceRule;
import com.big.web.b2b_big2.flight.dao.IFlightDao;
import com.big.web.b2b_big2.flight.model.AbstractFlightClassFare;
import com.big.web.b2b_big2.flight.model.FlightAvailSeatVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareAirAsiaVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareAviastarVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareBatikVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareCitilinkVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareGarudaVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareKalstarVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareLionVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareMalindoVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareNamVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareSriwijayaVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareSusiVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareTriganaVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareVO;
import com.big.web.b2b_big2.flight.model.FlightClassFareXpressVO;
import com.big.web.b2b_big2.flight.model.MstRouteSaveVO;
import com.big.web.b2b_big2.flight.pojo.AirportDescription;
import com.big.web.b2b_big2.flight.pojo.FlightSearchB2B;
import com.big.web.b2b_big2.flight.pojo.FlightSearchResult;
import com.big.web.b2b_big2.flight.pojo.OriginDestinationRaw;
import com.big.web.b2b_big2.flight.pojo.SearchForm;
import com.big.web.b2b_big2.flight.pojo.Seat;
import com.big.web.b2b_big2.scraping.exception.InvalidRadioIdException;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.Currency;
import com.big.web.b2b_big2.util.StopWatch;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.hibernate.BasicHibernate;

@Repository("flightDao")
public class FlightDaoHibernate extends BasicHibernate implements IFlightDao {

	@Autowired ISetupDao setupDao;
	
	private FlightSearchB2B convertObjectColumnsToBean(Object[] obj, PriceRule[] priceRules, AirportDescription airportDescription){
		FlightSearchB2B hb = new FlightSearchB2B();
		hb.setFlightAvailId("" + obj[0]);
		hb.setFlightNumber("" + obj[1]);
		hb.setOriginIata("" + obj[2]);
		hb.setDestinationIata("" + obj[3]);		
		hb.setDepartureIata("" + obj[4]);
		hb.setArrivalIata("" + obj[5]);

		//for tooltip 
		hb.setOriginDescription(airportDescription.get(hb.getOriginIata()));
		hb.setDestinationDescription(airportDescription.get(hb.getDestinationIata()));
		hb.setDepartureDescription(airportDescription.get(hb.getDepartureIata()));
		hb.setArrivalDescription(airportDescription.get(hb.getArrivalIata()));
		/*
		hb.setOriginDescription(getAirportNameAndCity(hb.getOriginIata()));
		hb.setDestinationDescription(getAirportNameAndCity(hb.getDestinationIata()));
		hb.setDepartureDescription(getAirportNameAndCity(hb.getDepartureIata()));
		hb.setArrivalDescription(getAirportNameAndCity(hb.getArrivalIata()));
	*/
		try {
			hb.setDepartTime(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("" + obj[6]));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			hb.setArrivalTime(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("" + obj[7]));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Airline airline = Airline.getAirline("" + obj[8]);
		hb.setAirline(airline);
		
		hb.setIsTransit("" + obj[9]);
		
		String[] _transits = StringUtils.split("" + (obj[10] == null ? "" : obj[10] ), ',');
		hb.setTransitId(_transits);//hati2 bisa aja ada spasinya
		
		hb.setUpdateCode("" + obj[11]);
		
		try {
			hb.setLastUpdate(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("" + obj[12]));
//			hb.setCreated_Date(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse("" + obj[12]));
		} catch (ParseException e) {
			System.err.println("error " + e.getMessage() + " parsing lastUpdate for " + hb.getFlightAvailId());
		}
		
		//combine seat, rate and availseat into Seat class
		/*
		hb.setClass_seat("" + obj[13]);
		hb.setClass_rate("" + obj[14]);
		hb.setClass_avail_seat("" + obj[15]);
		*/

		String[] seat = org.springframework.util.StringUtils.delimitedListToStringArray("" + obj[13], ",");
		String[] rate = org.springframework.util.StringUtils.delimitedListToStringArray("" + (obj[14] == null ? "0" : obj[14]), ",");
		String[] avail_seat = org.springframework.util.StringUtils.delimitedListToStringArray("" + (obj[15] == null ? "0" : obj[15]), ",");
		
		
		PriceRule priceRule = PriceRule.getPriceRule(priceRules, airline);
		
		
		double factor = 1;
		
//		jangan ada akses database spy mempercepat
//		SQLQuery q = getSession().createSQLQuery("select value from setup where name='" + uid + "'");
		
		if (priceRule != null){
			factor = priceRule.getFactor();
		}
		/*
		if (airline == Airline.SRIWIJAYA)
//				|| hb.getAirline().equalsIgnoreCase(Airline.AIRASIA.getAirlineName())
				{
			factor = 1000;
		}
		*/
		
		//kalau transit tidak akan dilakukan filter minimum price
		boolean allowMinimumPrice = true;
		if (hb.getIsTransit() != null && !hb.getIsTransit().equals("0")){
			allowMinimumPrice = false;
			/*
			string key = airline.name().toLowerCase() + ".price.idr.minimum";
			double minimum = setupdao.getvalue key
					
			airlines airlines = minimumlist
			
			perlu object pricerule seperti factor,minimumprice..etc
			pricerule diinitiate dgn relasi database diawal sebelum pencarian spy mempercepat proses 
			
			*/
		}
		
		//pengecekan secara horisontal
		Seat[] seats = new Seat[seat.length];
		for (int i = 0; i < seat.length; i++){
			//fix rate 1.253
			if (airline == Airline.SRIWIJAYA){
				rate[i] = rate[i].replaceAll("[,. ]", "");
			}
			/*
			if airline tidak ada di pricerule maka jgn dirule
			*/
			double _rate = Double.parseDouble(rate[i]) * factor;
			int _avail = Integer.parseInt(avail_seat[i]);
			
			if (priceRule != null && allowMinimumPrice){
				if (_rate < priceRule.getMinimumPrice())
					_avail = 0;
			}
			
//			seats[i] = new Seat(_rate, Character.toUpperCase(seat[i].charAt(0)), _avail);
			seats[i] = new Seat(_rate, seat[i], _avail);
		}
		hb.setSeats(seats);
		hb.setCheapestClass(getCheapestClass(seats));
		
		hb.setRadioId("" + obj[16]);
		
	
		return hb;
	}

	private String[] getCheapestClass(Seat[] seats){
		//look for cheapest class
		Set<String> cheapestClasses = new LinkedHashSet<String>();
		double min = 0;
		for (int i = 0; i < seats.length; i++){
			if (seats[i].getRate() < 1 || seats[i].getAvailable() < 1) continue;
			
			if (min == 0 || seats[i].getRate() <= min){
				min = seats[i].getRate();
				cheapestClasses.add(seats[i].getKelas());
			}
		}
		
/*		
		Seat buf = seats[0];
		for (int i = 1; i < seats.length; i++){
			if (seats[i].getRate() < 1) continue;
			
			if (seats[i].getRate() < buf.getRate()){
				buf = seats[i];
				cheapestClasses.add(buf.getKelas());
			}
		}
	*/	
		if (cheapestClasses.size() < 1) return null;	
		
		return cheapestClasses.toArray(new String[cheapestClasses.size()]);

	}
	
//	hopefully faster than db
	private static List<FlightSearchB2B> cleanList(List<FlightSearchB2B> dirtyList){

		List<FlightSearchB2B> clean = new ArrayList<FlightSearchB2B>();
		
		// TODO remove all empty seats / price
		for (FlightSearchB2B flightSearchB2B : dirtyList) {

			//1. jika isi updatecode = flightno berarti no transit
			if (flightSearchB2B.getUpdateCode().equals(flightSearchB2B.getFlightNumber()) 
					|| StringUtils.isEmpty(flightSearchB2B.getUpdateCode()) 
					|| flightSearchB2B.getIsTransit().equals("0") 
					){
				clean.add(flightSearchB2B);
				continue;
			}

			//jangan add istransit 2 dan 3 karena akan di link waktu display ke table
			if (flightSearchB2B.getIsTransit().equals("2") || flightSearchB2B.getIsTransit().equals("3"))
				continue;
			
			//sebelum ditambah, cari transit2nya
			boolean transitExists = true;
			while (transitExists){
				transitExists = false;
				for (FlightSearchB2B tmp : dirtyList) {
					//skip its own
					if (tmp.getFlightAvailId().equals(flightSearchB2B.getFlightAvailId()))
						continue;
					
					if (tmp.getUpdateCode().equals(flightSearchB2B.getUpdateCode()) 
//							&& tmp.getAirline().equals(flightSearchB2B.getAirline())	<-- this is a bug, because lionair and wingsair surely different
							&& tmp.getOriginIata().equals(flightSearchB2B.getOriginIata()) 
							&& tmp.getDestinationIata().equals(flightSearchB2B.getDestinationIata()) 
							){
						if (tmp.getIsTransit().equals("2") && flightSearchB2B.getSecondFlight() == null){
							//tend to link ke flight pertama
							
							if (tmp.getRadioId() != null || flightSearchB2B.getRadioId() != null){
								
								boolean invalidScraping = tmp.getRadioId() == null || flightSearchB2B.getRadioId() == null;
								if (invalidScraping)
									throw new InvalidRadioIdException("One of the RadioId between the second is null [" + flightSearchB2B.getFlightAvailId() + "]");
								
								boolean rateIsSummary = tmp.getRadioId().equalsIgnoreCase(flightSearchB2B.getRadioId());
								
								flightSearchB2B.setRateIsSummary(rateIsSummary);	//cukup yg flight pertama di tandai kalau harganya sudah total berdasarkan radioId yang sama
							}
							
							flightSearchB2B.setSecondFlight(tmp);
							transitExists = true;
							break;
						}else
							if (flightSearchB2B.getSecondFlight() != null 
								&& tmp.getIsTransit().equals("3") 
								&& flightSearchB2B.getSecondFlight().getSecondFlight() == null){

								//just to make sure the scraping is valid.radioIdnya ga boleh salah satunya null
								if (tmp.getRadioId() != null || flightSearchB2B.getSecondFlight().getRadioId() != null){
									
									boolean invalidScraping = tmp.getRadioId() == null || flightSearchB2B.getSecondFlight().getRadioId() == null;
									if (invalidScraping)
										throw new InvalidRadioIdException("One of the RadioId between the third is null [" + flightSearchB2B.getFlightAvailId() + "]");
									
								}

								//link ke flight kedua karena yg pertama sudah di link di flight kedua
								flightSearchB2B.getSecondFlight().setSecondFlight(tmp);
								transitExists = true;
								break;
							}
						
					}
				}
				
			}//while			
			clean.add(flightSearchB2B);
		}//for
		
		return clean;
	}

	/**
	 * To count how many transit taken. 
	 * only applied when the value of isTransit = 1. 0 and >1 will return null
	 * @param uid
	 * @return >0 for how many transit. 
	 */
	public int countTransit(String uid) {
		if (StringUtils.isEmpty(uid)) return 0;
		
		int count = 0;
		while (true) {
			SQLQuery q = getSession().createSQLQuery("select flightdetid from flightdetails where transitid='" + uid + "'");
			if (q.list().size() > 0) {
				String s = (String) q.list().get(0);
				uid = s;
				count++;
			}else break;
		}

		return count;
	}

	@Override
	public FlightAvailSeatVO searchFlightB2B(String uid) {
		return getFlightAvailSeat(uid);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public FlightAvailSeatVO searchTransitFlightB2B(String transitId) {
		if (StringUtils.isEmpty(transitId)) return null;

		Criteria c = getSession().createCriteria(FlightAvailSeatVO.class);

		//hati2, agak rumit krn isi transitId (bisa) CSV format
		c.add(Restrictions.ilike("transitId", transitId, MatchMode.ANYWHERE));
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			FlightAvailSeatVO obj = (FlightAvailSeatVO) iterator.next();

			return obj;
		}

		return null;	
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getFlightClassFare(String flightavailid, String seatCls, Airline airline) {
		
		Class persistentClass = null;
		switch (airline){
			case KALSTAR: 
				persistentClass = FlightClassFareKalstarVO.class;
				break;
			case AIRASIA:
				persistentClass = FlightClassFareAirAsiaVO.class;
				break;
			case AVIASTAR:
				persistentClass = FlightClassFareAviastarVO.class;
				break;
			case CITILINK:
				persistentClass = FlightClassFareCitilinkVO.class;
				break;
			case GARUDA:
				persistentClass = FlightClassFareGarudaVO.class;
				break;
			case LION:
				persistentClass = FlightClassFareLionVO.class;
				break;
			case MALINDO:
				persistentClass = FlightClassFareMalindoVO.class;
				break;
			case BATIK:
				persistentClass = FlightClassFareBatikVO.class;
				break;
			case SRIWIJAYA:
				persistentClass = FlightClassFareSriwijayaVO.class;
				break;
			case NAM:
				persistentClass = FlightClassFareNamVO.class;
				break;
			case SUSI:
				persistentClass = FlightClassFareSusiVO.class;
				break;
			case TRIGANA:
				persistentClass = FlightClassFareTriganaVO.class;
				break;
			case EXPRESS:
				persistentClass = FlightClassFareXpressVO.class;
				break;
		default:
			persistentClass = FlightClassFareVO.class;
			break;
		}
		
		Criteria c = getSession().createCriteria(persistentClass);
		
		c.add(Restrictions.eq("flightavailid", flightavailid));
		c.add(Restrictions.eq("class_name", seatCls));
		
		Object found = null;
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			found = iterator.next();
			break;
		}

		return found;	
	}
	
	@Override
	public Object getFlightClassFare(String flightavailid, String seatCls){
		
		FlightAvailSeatVO fas = getFlightAvailSeat(flightavailid);
		Airline airline = Airline.getAirline(fas.getAirline());
		
		return getFlightClassFare(flightavailid, seatCls, airline);
	}


	@SuppressWarnings("rawtypes")
	@Override
	public FlightAvailSeatVO getFlightAvailSeat(String flightavailid) {
		if (StringUtils.isEmpty(flightavailid)) return null;

		Criteria c = getSession().createCriteria(FlightAvailSeatVO.class);

		c.add(Restrictions.eq("flightavailid", flightavailid));
		
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			FlightAvailSeatVO obj = (FlightAvailSeatVO) iterator.next();

			return obj;
		}

		return null;	
	}

	@SuppressWarnings("rawtypes")
	@Override
	public FlightAvailSeatVO getLatestFlightAvailSeat() {

		Criteria c = getSession().createCriteria(FlightAvailSeatVO.class);
		c.addOrder(Order.desc("lastUpdate"));
//		c.addOrder(Order.desc("created_date"));
		c.setMaxResults(1);
		
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			FlightAvailSeatVO obj = (FlightAvailSeatVO) iterator.next();

			return obj;
		}

		return null;
	}

/*
	@Override
	public void saveBooking(BookingFlightVO bookingFlight) {
		getSession().save(bookingFlight);
	}

	@Override
	public void updateBooking(BookingFlightVO bookingFlight) {
		getSession().merge(bookingFlight);
	}

	@Override
	public void removeBooking(BookingFlightVO bookingFlight) {
		getSession().delete(bookingFlight);
	}
*/

	public static void main(String[] args) {
		List<FlightSearchB2B> dirtyList = new ArrayList<FlightSearchB2B>();
		
		FlightSearchB2B data1 = new FlightSearchB2B();
		data1.setFlightAvailId("1");
		data1.setFlightNumber("SJ588");
		data1.setOriginIata("CGK");
		data1.setDestinationIata("SOQ");
		data1.setAirline(Airline.SRIWIJAYA);
		data1.setIsTransit("1");
		data1.setUpdateCode("SJ588SJ523");

		FlightSearchB2B data2 = new FlightSearchB2B();
		data2.setFlightAvailId("2");
		data2.setFlightNumber("SJ572");
		data2.setOriginIata("CGK");
		data2.setDestinationIata("SOQ");
		data2.setAirline(Airline.SRIWIJAYA);
		data2.setIsTransit("2");
		data2.setUpdateCode("SJ580SJ572");
		
		FlightSearchB2B data3 = new FlightSearchB2B();
		data3.setFlightAvailId("3");
		data3.setFlightNumber("SJ570");
		data3.setOriginIata("CGK");
		data3.setDestinationIata("SOQ");
		data3.setAirline(Airline.SRIWIJAYA);
		data3.setIsTransit("2");
		data3.setUpdateCode("SJ580SJ570");
		
		FlightSearchB2B data4 = new FlightSearchB2B();
		data4.setFlightAvailId("4");
		data4.setFlightNumber("SJ523");
		data4.setOriginIata("CGK");
		data4.setDestinationIata("SOQ");
		data4.setAirline(Airline.SRIWIJAYA);
		data4.setIsTransit("2");
		data4.setUpdateCode("SJ588SJ523");
		
		FlightSearchB2B data5 = new FlightSearchB2B();
		data5.setFlightAvailId("5");
		data5.setFlightNumber("SJ580");
		data5.setOriginIata("CGK");
		data5.setDestinationIata("SOQ");
		data5.setAirline(Airline.SRIWIJAYA);
		data5.setIsTransit("1");
		data5.setUpdateCode("SJ580SJ570");
		
		
		dirtyList.add(data1);
		dirtyList.add(data2);
		dirtyList.add(data3);
		dirtyList.add(data4);
		dirtyList.add(data5);
		
		List<FlightSearchB2B> list = cleanList(dirtyList);
		
		System.out.println(list.size());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getAirportCode(String city) {
		/*
		if (city.equalsIgnoreCase("jakarta"))
			return "CGK";
		else if (city.equalsIgnoreCase("semarang"))
			return "SRG";
		else
			*/
		if (StringUtils.isEmpty(city)) return null;

		city = city.trim().toUpperCase();
		String sql = "select IATA from MST_AIRPORTS"
				+ " WHERE 1=1"
				+ (StringUtils.isEmpty(city) ? "" : " AND UPPER(CITY)=:city OR IATA=:city")
				;
		SQLQuery query = getSession().createSQLQuery(sql);
		if (!StringUtils.isEmpty(city)) {
			query.setParameter("city", city);
		}
		
//		System.out.println("getAirportCode of " + city);
		for (Iterator iterator = query.list().iterator(); iterator.hasNext();) {
			String obj = (String)iterator.next();
			
			return obj;
		}
		
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public String getAirportName(String iata) {
		if (StringUtils.isEmpty(iata)) return null;
		
		iata = iata.trim().toUpperCase();
		String sql = "select AIRPORT_NAME from MST_AIRPORTS"
				+ " WHERE 1=1"
				+ (StringUtils.isEmpty(iata) ? "" : " AND IATA=:iata")
				;
		SQLQuery query = getSession().createSQLQuery(sql);
		if (!StringUtils.isEmpty(iata)) {
			query.setParameter("iata", iata);
		}
		
//		System.out.println("getAirportName of " + iata);
		for (Iterator iterator = query.list().iterator(); iterator.hasNext();) {
			String obj = (String)iterator.next();
			
			return obj;
		}
		
		return null;		
	}

	/**
	 * @return Soekarno Hatta Intl, Jakarta
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String getAirportNameAndCity(String iata) {
		if (StringUtils.isEmpty(iata)) return null;
		
		iata = iata.trim().toUpperCase();
		String sql = "select AIRPORT_NAME,CITY from MST_AIRPORTS"
				+ " WHERE IATA=:iata"
				;
		SQLQuery query = getSession().createSQLQuery(sql);
		if (!StringUtils.isEmpty(iata)) {
			query.setParameter("iata", iata);
		}
		
//		System.out.println("getAirportNameAndCity of " + iata);
		for (Iterator iterator = query.list().iterator(); iterator.hasNext();) {
			Object[] obj = (Object[])iterator.next();
			
			return "" + obj[0] + ", " + obj[1];
		}
		
		return null;		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getCity(String iata) {
		if (StringUtils.isEmpty(iata)) return null;
		
		iata = iata.trim().toUpperCase();
		String sql = "select CITY from MST_AIRPORTS"
				+ " WHERE 1=1"
				+ (StringUtils.isEmpty(iata) ? "" : " AND IATA=:iata")
				;
		SQLQuery query = getSession().createSQLQuery(sql);
		if (!StringUtils.isEmpty(iata)) {
			query.setParameter("iata", iata);
		}
		
//		List<?> lists = query.list();
//		System.out.println("getCity of " + iata);
		for (Iterator iterator = query.list().iterator(); iterator.hasNext();) {
			String obj = (String)iterator.next();
			
			return obj;
		}
		
		return null;		
	}

	private String getTableClassFareName(Airline airline){
		
		String preName = "FLIGHT_CLASS_FARE_";
		switch(airline){
			case EXPRESS:
				return preName + "XPRESS";
		default:
			return preName + airline.name().toUpperCase();
		}
	}
	
	private PriceRule[] queryPriceRules(List<Airline> airlines){

		Set<PriceRule> priceRules = new LinkedHashSet<PriceRule>();

		for (int i = 0; i < airlines.size(); i++){
			
			Airline airline = airlines.get(i);
			
			priceRules.addAll(Arrays.asList(queryPriceRules(airline)));

		}

		return priceRules.toArray(new PriceRule[priceRules.size()]);
	}
	
	private PriceRule[] queryPriceRules(Airline airline){
		
		Set<PriceRule> priceRules = new LinkedHashSet<PriceRule>();
		
		for (int j = 0; j < Currency.supportedCurrency.length; j++){
				
				double minimumPrice = 0;
				double factor = 1;
				
				PriceRule p = new PriceRule();
				p.setAirline(airline);
				p.setCurrency(Currency.supportedCurrency[j].toUpperCase());
				
//			sriwijaya.price.idr.minimum
//			sriwijaya.price.idr.factor
				StringBuffer preSqlKey = new StringBuffer("SELECT value FROM setup where name=")
				.append("'")		//dont forget to add quote
				.append(airline.name().toLowerCase())
				.append(".price.")
				.append(Currency.supportedCurrency[j].toLowerCase())
				;
				List<?> list = getSession().createSQLQuery(preSqlKey.toString() + ".minimum'").list();
				if (list.size() > 0){
					minimumPrice = Double.parseDouble((String)list.get(0));
				}
				p.setMinimumPrice(minimumPrice);
				
				list = getSession().createSQLQuery(preSqlKey.toString() + ".factor'").list();
				if (list.size() > 0){
					factor = Double.parseDouble((String)list.get(0));
				}
				p.setFactor(factor);
				
				priceRules.add(p);
		}

		return priceRules.toArray(new PriceRule[priceRules.size()]);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<FlightSearchB2B> searchOneWayFlightB2B(Date departDate,
			String departIata, String destinationIata, List<Airline> airlines) {
		
		List<FlightSearchB2B> lists = new ArrayList<FlightSearchB2B>();
		if (Utils.isEmpty(airlines))
			return lists;

		if (departDate == null) departDate = new Date();
		String _departDate = new SimpleDateFormat("dd/MM/yyyy").format(departDate);
		String _origin = StringUtils.isEmpty(departIata) ? null : getAirportCode(departIata);
		String _destinationIata = StringUtils.isEmpty(destinationIata) ? null : getAirportCode(destinationIata);
		
		boolean searchPast = setupDao.getValueAsBoolean(ISetupDao.KEY_SEARCH_PAST_ENABLED);
		
		StopWatch sw = StopWatch.AutoStart();
		
		//step#1: collect transitId if any
		StringBuffer queryTransit = new StringBuffer();
		
		for (int i = 0; i < airlines.size(); i++){
			Airline airline = airlines.get(i);
			
			if ( i > 0)
				queryTransit.append(" UNION ALL ");

			queryTransit.append("SELECT A.flightavailID")
			.append(" FROM FLIGHTAVAILSEAT_CLEAN A JOIN ").append(getTableClassFareName(airline)).append(" B ON A.FLIGHTAVAILID = B.FLIGHTAVAILID")
			.append(" WHERE (A.ISTRANSIT = 1)")
			.append(_origin == null ? "" : " and ORIGIN=:origin")
			.append(_destinationIata == null ? "" : " and DESTINATION=:destination")
			.append(" AND TRUNC (A.DEP_TIME) = TO_DATE (:departDate, 'DD/MM/YYYY')")
			.append(searchPast ? "" : " AND A.DEP_TIME > SYSDATE + (2 / 24)")
			//.append(((airlines.size() < 1) ? "" : " and A.AIRLINE in (:airlines)"))// bisa beda pesawat, seperti wingsair ke lionair
			.append(" GROUP BY A.flightavailID");
		}

		SQLQuery sqlTransits = getSession().createSQLQuery(queryTransit.toString());
		
		sqlTransits.setParameter("departDate", _departDate);			
		if (_origin != null) {
			sqlTransits.setParameter("origin", _origin.toUpperCase());
		}
		
		if (_destinationIata != null) {
			sqlTransits.setParameter("destination", _destinationIata.toUpperCase());
		}
		
		List<?> transits = sqlTransits.list();
		
		log.debug("q1(Transit)=" + transits.size() + " rows, " + sw.stopAndGetAsString());
	
		sw.start();
		//step#2: check if this query match with transits
		StringBuffer sbGroupTransit = new StringBuffer();
		for (int i = 0; i < airlines.size(); i++){
			Airline airline = airlines.get(i);
			
			if (i > 0)
				sbGroupTransit.append(" UNION ALL ");
			
			sbGroupTransit.append("SELECT A.flightavailID, A.FLIGHTNUM AS FLIGHT_NUMBER, A.ORIGIN, A.DESTINATION,A.DEPARTURE,A.ARRIVAL,")
			.append("TO_CHAR (A.DEP_TIME, 'DD/MM/YYYY HH24:MI:SS A.M.') AS DEPARTURE_TIME,")
			.append("TO_CHAR (A.ARR_TIME, 'DD/MM/YYYY HH24:MI:SS A.M.') AS ARRIVAL_TIME,")
			.append("A.AIRLINE AS AIRLINE, A.ISTRANSIT, A.TRANSITID,A.UPDATECODE,")
			.append("TO_CHAR (A.LASTUPDATE, 'DD/MM/YYYY HH24:MI:SS A.M.') AS LASTUPDATE,")
			.append("listagg (B.class_name, ',') WITHIN GROUP (ORDER BY B.class_sequence) Class_Seat,")
			.append("listagg (B.class_rate, ',') WITHIN GROUP (ORDER BY B.class_sequence) Class_Rate,")
			.append("listagg (B.class_avail_seat, ',') WITHIN GROUP (ORDER BY B.class_sequence) Class_Avail_Seat,")
			.append("listagg (B.radio_id, ',') WITHIN GROUP (ORDER BY B.class_sequence) Radio_ID")
			.append(" FROM FLIGHTAVAILSEAT_CLEAN A JOIN ").append(getTableClassFareName(airline)).append(" B ON A.FLIGHTAVAILID = B.FLIGHTAVAILID")
			.append(" WHERE (A.ISTRANSIT = 2 OR A.ISTRANSIT = 3)")
			.append(_origin == null ? "" : " and ORIGIN=:origin")
			.append(_destinationIata == null ? "" : " and DESTINATION=:destination")
			//.append(" AND TRUNC (DEP_TIME) = TO_DATE (:departDate, 'DD/MM/YYYY')")	//karena bisa saja abis transit, hari udah pindah
			//.append(searchPast ? "" : " AND DEP_TIME > SYSDATE + (2 / 24)")	//idem
			.append(transits.size() < 1 ? "" : " and A.TRANSITID IN (:transits)")
//			.append((airlines.size() < 1) ? "" : " and A.AIRLINE in (:airlines)")	//bisa beda pswt stlh transit
			.append(" GROUP BY A.flightavailID,A.FLIGHTNUM,A.ORIGIN,A.DESTINATION,A.DEPARTURE, A.ARRIVAL, A.DEP_TIME,A.ARR_TIME,A.airline,A.ISTRANSIT,A.TRANSITID,A.UPDATECODE,A.LASTUPDATE")
			//.append(" ORDER BY airline, updatecode, departure_time ")
		;

		}
		
		sbGroupTransit.insert(0, "SELECT * FROM(");
		sbGroupTransit.append(") ORDER BY AIRLINE, DEPARTURE_TIME, UPDATECODE");

		SQLQuery sqlGroupB = getSession().createSQLQuery(sbGroupTransit.toString());
		/*
		sqlGroupB.addScalar("FLIGHTAVAILID", StringType.INSTANCE)
			.addScalar("FLIGHT_NUMBER", StringType.INSTANCE)
			.addScalar("ORIGIN", StringType.INSTANCE)
			.addScalar("DESTINATION", StringType.INSTANCE)
			.addScalar("DEPARTURE", StringType.INSTANCE)
			.addScalar("ARRIVAL", StringType.INSTANCE)
			.addScalar("DEPARTURE_TIME", StringType.INSTANCE)
			.addScalar("ARRIVAL_TIME", StringType.INSTANCE)
			.addScalar("AIRLINE", StringType.INSTANCE)
			.addScalar("ISTRANSIT", StringType.INSTANCE)
			.addScalar("TRANSITID", StringType.INSTANCE)
			.addScalar("UPDATECODE", StringType.INSTANCE)
			.addScalar("CLASS_SEAT", StringType.INSTANCE)
			.addScalar("CLASS_RATE", StringType.INSTANCE)
			.addScalar("CLASS_AVAIL_SEAT", StringType.INSTANCE)
			.addScalar("RADIO_ID", StringType.INSTANCE);
		sqlGroupB.setpr
		sqlGroupB.setResultTransformer(Transformers.aliasToBean(FlightSearchB2B.class));
		*/

		if (_origin != null) {
			sqlGroupB.setParameter("origin", _origin.toUpperCase());
		}
		
		if (_destinationIata != null) {
			sqlGroupB.setParameter("destination", _destinationIata.toUpperCase());
		}
		
		if (transits.size() > 0) {
			sqlGroupB.setParameterList("transits", transits);
		}

		List<?> groupB = sqlGroupB.list();

		log.debug("q2(Transit)=" + groupB.size() + " rows, " + sw.stopAndGetAsString());		
		
		sw.start();
		//step#3: combine GroupA and groupB
		StringBuffer sbGroupNonTransit = new StringBuffer();
		
		for (int i = 0; i < airlines.size(); i++){
			Airline airline = airlines.get(i);
			
			if (i > 0)
				sbGroupNonTransit.append(" UNION ALL ");
			
			sbGroupNonTransit.append("SELECT A.flightavailID,A.FLIGHTNUM AS FLIGHT_NUMBER,A.ORIGIN,A.DESTINATION,A.DEPARTURE,A.ARRIVAL,")
			.append("TO_CHAR (A.DEP_TIME, 'DD/MM/YYYY HH24:MI:SS A.M.') AS DEPARTURE_TIME,")
			.append("TO_CHAR (A.ARR_TIME, 'DD/MM/YYYY HH24:MI:SS A.M.') AS ARRIVAL_TIME,")
			.append("A.AIRLINE AS AIRLINE,A.ISTRANSIT,A.TRANSITID,A.UPDATECODE,")
			.append("TO_CHAR (A.LASTUPDATE, 'DD/MM/YYYY HH24:MI:SS A.M.') AS LASTUPDATE,")
			.append("listagg (B.class_name, ',') WITHIN GROUP (ORDER BY B.class_sequence) Class_Seat,")
			.append("listagg (B.class_rate, ',') WITHIN GROUP (ORDER BY B.class_sequence) Class_Rate,")
			.append("listagg (B.class_avail_seat, ',') WITHIN GROUP (ORDER BY B.class_sequence) Class_Avail_Seat,")
			.append("listagg (B.radio_id, ',') WITHIN GROUP (ORDER BY B.class_sequence) Radio_ID")
			.append(" FROM FLIGHTAVAILSEAT_CLEAN A JOIN ").append(getTableClassFareName(airline)).append(" B ON A.FLIGHTAVAILID = B.FLIGHTAVAILID")
			.append(" WHERE (A.ISTRANSIT = 0 OR A.ISTRANSIT = 1)")
			.append(_origin == null ? "" : " and ORIGIN=:origin")
			.append(_destinationIata == null ? "" : " and DESTINATION=:destination")
			.append(" AND TRUNC (A.DEP_TIME) = TO_DATE (:departDate, 'DD/MM/YYYY')")
			.append(searchPast ? "" : " AND A.DEP_TIME > SYSDATE + (2 / 24)")
			.append(((airlines.size() < 1) ? "" : " and A.AIRLINE in (:airlines)"))
			.append(" GROUP BY A.flightavailID, A.FLIGHTNUM, A.ORIGIN, A.DESTINATION, A.DEPARTURE, A.ARRIVAL, A.DEP_TIME, A.ARR_TIME, A.AIRLINE, A.ISTRANSIT, A.TRANSITID,A.UPDATECODE,A.LASTUPDATE")
		;
		}
		
//			.append(" ORDER BY ARRIVAL_TIME, DEPARTURE_TIME")
		//.append(" ORDER BY airline, updatecode, departure_time ")
		sbGroupNonTransit.insert(0, "SELECT * FROM(");
		sbGroupNonTransit.append(") ORDER BY AIRLINE, DEPARTURE_TIME, UPDATECODE");

		SQLQuery sqlGroupNonTransit = getSession().createSQLQuery(sbGroupNonTransit.toString());
	
		sqlGroupNonTransit.setParameter("departDate", _departDate);			
		if (_origin != null) {
			sqlGroupNonTransit.setParameter("origin", _origin.toUpperCase());
		}
		
		if (_destinationIata != null) {
			sqlGroupNonTransit.setParameter("destination", _destinationIata.toUpperCase());
		}
		
		if (airlines.size() > 0) {
			sqlGroupNonTransit.setParameterList("airlines", Airline.getNames(airlines));
		}

		List<?> groupNonTransit = sqlGroupNonTransit.list();
		//23des2014 perlu di clean lagi karena ditemukan kasus jam berubah sehingga scraping tdk update, tp insert
		//contoh kasus jakarta-medan(kno) 24 des 2014
		//estimasi untuk clean jika something wrong need 14 days to fix
		/* sementara disable krn mau presentasi airasia, lagian fungsi ini akan dipindah ke manager spy bisa pake DAO yg lain
		while (true){
			boolean _flightClash = false;
			
			for (int i = 0; i < groupNonTransit.size(); i++){
				Object[] ii = (Object[]) groupNonTransit.get(i);
				FlightSearchB2B fs1 = convertObjectColumnsToBean(ii);
				
				List<FlightSearchB2B> _collect = new ArrayList<FlightSearchB2B>();
				_collect.add(fs1);
				for (int j = i+1; j < groupNonTransit.size(); j++){
					Object[] jj = (Object[]) groupNonTransit.get(j);					
					FlightSearchB2B fs2 = convertObjectColumnsToBean(jj);
					
					if (fs2.getFlightNumber().equalsIgnoreCase(fs1.getFlightNumber())
							&& fs2.getAirline().equalsIgnoreCase(fs1.getAirline())
							&& fs2.getUpdateCode().equalsIgnoreCase(fs1.getUpdateCode())
							&& fs2.getDepartureIata().equalsIgnoreCase(fs1.getDepartureIata())
							&& fs2.getArrivalIata().equalsIgnoreCase(fs1.getArrivalIata())
							){
						_collect.add(fs2);
					}
					
				}
				//only check if the second flightno is not exist
				if (_collect.size() < 2) continue;
				
				//cari yg schedulnya nabrak, dimulai aja dr data kedua, krn yg pertama isinya pasti fs1
				long _arrivalTime = _collect.get(0).getArrivalTime().getTime();
				FlightSearchB2B _clash = null;
				for (int p = 1; p < _collect.size(); p++){
					//buang yg jadwalnya tabrakan, dgn cara hitung interval penerbangan, lalu ambil yg paling keberangkatan kedua tdk lebih kecil dari arrival pertama
					if (_collect.get(p).getDepartTime().getTime() < _arrivalTime){
						_flightClash = true;	//ketemu tp belum tentu salah
						_clash = _collect.get(p);
						break;
					}
				}
				//sampai sini pasti ada 2 penerbangan yg salah satunya salah
				if (_clash != null){
					_collect.clear();//reuse
					_collect.add(fs1);
					_collect.add(_clash);
					
					String _suspectId = null;
					long _firstCreatedDate = _collect.get(0).getCreated_Date().getTime();
					long _secondCreatedDate = _collect.get(1).getCreated_Date().getTime();
					//buang yg createddatenya paling lawas
					if (_secondCreatedDate > _firstCreatedDate){
						_suspectId = _collect.get(1).getFlightAvailId();
					}else{
						_suspectId = _collect.get(0).getFlightAvailId();
					}

					if (_suspectId != null){
						for (int z = 0; z < groupNonTransit.size(); z++){
							Object[] _o = (Object[]) groupNonTransit.get(i);
							if (_o[0].toString().equals(_suspectId)){
								groupNonTransit.remove(z);
								break;
							}
						}
					}
					
					break;
				}

			}
			
			if (!_flightClash) break;
		}//while
		*/

		log.debug("q3(NonTransit)=" + groupNonTransit.size() + " rows, " + sw.stopAndGetAsString());
		
		List<?> groupAB = ListUtils.union(groupNonTransit, groupB);
		
		//TODO: check cheapest Airline ?
		//collect price rules
		PriceRule[] priceRules = queryPriceRules(airlines);
		
		AirportDescription airportDescription = new AirportDescription();

		for (Iterator iterator = groupAB.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();

			if (airportDescription.get("" + obj[2]).length() < 1)
				airportDescription.add("" + obj[2], getAirportNameAndCity("" + obj[2]));	//originIata
			
			if (airportDescription.get("" + obj[3]).length() < 1)
				airportDescription.add("" + obj[3], getAirportNameAndCity("" + obj[3]));	//destinationIata
			
			if (airportDescription.get("" + obj[4]).length() < 1)
				airportDescription.add("" + obj[4], getAirportNameAndCity("" + obj[4]));	//departureIata
			
			if (airportDescription.get("" + obj[5]).length() < 1)
				airportDescription.add("" + obj[5], getAirportNameAndCity("" + obj[5]));	//arrivalIata
			
			//just add it, will clean it later
			lists.add(convertObjectColumnsToBean(obj, priceRules, airportDescription));
		}

		return cleanList(lists);

	}


	@Override
	public FlightSearchResult searchFlightB2B(SearchForm search) {
		
		FlightSearchResult searchResult = new FlightSearchResult(search.getTripMode());
		if (!search.isValid()){
			return searchResult;
		}
		
		try {
			List<FlightSearchB2B> searchDepart = searchOneWayFlightB2B(search.parseDepartDate(), search.getDepartIata(), search.getDestinationIata(), search.getAirlines());
			searchResult.setDepartList(searchDepart);
			
			if (search.getTripMode() == 1){
				
				List<FlightSearchB2B> searchReturn = searchOneWayFlightB2B(search.parseReturnDate(), search.getDestinationIata(), search.getDepartIata(), search.getAirlines());
				searchResult.setReturnList(searchReturn);				
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return searchResult;
	}
	
	@Override
	public void updateSeat(String flightAvailId, Seat[] seats) throws Exception{
		
		for (Seat seat : seats){
			
			AbstractFlightClassFare fcf = (AbstractFlightClassFare) getFlightClassFare(flightAvailId, seat.getKelas());
			
			fcf.setClass_rate(new BigDecimal( seat.getRate()));
			fcf.setClass_avail_seat(new BigDecimal(seat.getAvailable()));
			getSession().save(fcf);
		}
		
	}

	@Override
	public void saveSchedule(FlightAvailSeatVO obj) {
		getSession().saveOrUpdate(obj);
	}

	@Override
	public List<FlightSearchB2B> searchOneWayFlightB2B(Date departDate,
			String departIata, String destinationIata, Airline airline) {
		List<FlightSearchB2B> lists = new ArrayList<FlightSearchB2B>();

		if (departDate == null) departDate = new Date();
		String _departDate = new SimpleDateFormat("dd/MM/yyyy").format(departDate);
		String _origin = StringUtils.isEmpty(departIata) ? null : getAirportCode(departIata);
		String _destinationIata = StringUtils.isEmpty(destinationIata) ? null : getAirportCode(destinationIata);
		
		boolean searchPast = setupDao.getValueAsBoolean(ISetupDao.KEY_SEARCH_PAST_ENABLED);
		
		StopWatch sw = StopWatch.AutoStart();
		
		//step#1: collect transitId if any
		StringBuffer queryTransit = new StringBuffer();
		
		queryTransit.append("SELECT A.flightavailID")
			.append(" FROM FLIGHTAVAILSEAT_CLEAN A JOIN ").append(getTableClassFareName(airline)).append(" B ON A.FLIGHTAVAILID = B.FLIGHTAVAILID")
			.append(" WHERE (A.ISTRANSIT = 1)")
			.append(_origin == null ? "" : " and ORIGIN=:origin")
			.append(_destinationIata == null ? "" : " and DESTINATION=:destination")
			.append(" AND TRUNC (A.DEP_TIME) = TO_DATE (:departDate, 'DD/MM/YYYY')")
			.append(searchPast ? "" : " AND A.DEP_TIME > SYSDATE + (2 / 24)")
			//.append(((airlines.size() < 1) ? "" : " and A.AIRLINE in (:airlines)"))// bisa beda pesawat, seperti wingsair ke lionair
			.append(" GROUP BY A.flightavailID");

		SQLQuery sqlTransits = getSession().createSQLQuery(queryTransit.toString());
		
		sqlTransits.setParameter("departDate", _departDate);			
		if (_origin != null) {
			sqlTransits.setParameter("origin", _origin.toUpperCase());
		}
		
		if (_destinationIata != null) {
			sqlTransits.setParameter("destination", _destinationIata.toUpperCase());
		}
		
		List<?> transits = sqlTransits.list();
		
		log.debug("q1(Transit)=" + transits.size() + " rows, " + sw.stopAndGetAsString());
	
		sw.start();
		//step#2: check if this query match with transits
		StringBuffer sbGroupTransit = new StringBuffer();

		sbGroupTransit.append("SELECT A.flightavailID, A.FLIGHTNUM AS FLIGHT_NUMBER, A.ORIGIN, A.DESTINATION,A.DEPARTURE,A.ARRIVAL,")
		.append("TO_CHAR (A.DEP_TIME, 'DD/MM/YYYY HH24:MI:SS A.M.') AS DEPARTURE_TIME,")
		.append("TO_CHAR (A.ARR_TIME, 'DD/MM/YYYY HH24:MI:SS A.M.') AS ARRIVAL_TIME,")
		.append("A.AIRLINE AS AIRLINE, A.ISTRANSIT, A.TRANSITID,A.UPDATECODE,")
		.append("TO_CHAR (A.LASTUPDATE, 'DD/MM/YYYY HH24:MI:SS A.M.') AS LASTUPDATE,")
		.append("listagg (B.class_name, ',') WITHIN GROUP (ORDER BY B.class_sequence) Class_Seat,")
		.append("listagg (B.class_rate, ',') WITHIN GROUP (ORDER BY B.class_sequence) Class_Rate,")
		.append("listagg (B.class_avail_seat, ',') WITHIN GROUP (ORDER BY B.class_sequence) Class_Avail_Seat,")
		.append("listagg (B.radio_id, ',') WITHIN GROUP (ORDER BY B.class_sequence) Radio_ID")
		.append(" FROM FLIGHTAVAILSEAT_CLEAN A JOIN ").append(getTableClassFareName(airline)).append(" B ON A.FLIGHTAVAILID = B.FLIGHTAVAILID")
		.append(" WHERE (A.ISTRANSIT = 2 OR A.ISTRANSIT = 3)")
		.append(_origin == null ? "" : " and ORIGIN=:origin")
		.append(_destinationIata == null ? "" : " and DESTINATION=:destination")
		//.append(" AND TRUNC (DEP_TIME) = TO_DATE (:departDate, 'DD/MM/YYYY')")	//karena bisa saja abis transit, hari udah pindah
		//.append(searchPast ? "" : " AND DEP_TIME > SYSDATE + (2 / 24)")	//idem
		.append(transits.size() < 1 ? "" : " and A.TRANSITID IN (:transits)")
//			.append((airlines.size() < 1) ? "" : " and A.AIRLINE in (:airlines)")	//bisa beda pswt stlh transit
		.append(" GROUP BY A.flightavailID,A.FLIGHTNUM,A.ORIGIN,A.DESTINATION,A.DEPARTURE, A.ARRIVAL, A.DEP_TIME,A.ARR_TIME,A.airline,A.ISTRANSIT,A.TRANSITID,A.UPDATECODE,A.LASTUPDATE")
		//.append(" ORDER BY airline, updatecode, departure_time ")
		;

		sbGroupTransit.insert(0, "SELECT * FROM(");
		sbGroupTransit.append(") ORDER BY AIRLINE, DEPARTURE_TIME, UPDATECODE");

		SQLQuery sqlGroupB = getSession().createSQLQuery(sbGroupTransit.toString());
		/*
		sqlGroupB.addScalar("FLIGHTAVAILID", StringType.INSTANCE)
			.addScalar("FLIGHT_NUMBER", StringType.INSTANCE)
			.addScalar("ORIGIN", StringType.INSTANCE)
			.addScalar("DESTINATION", StringType.INSTANCE)
			.addScalar("DEPARTURE", StringType.INSTANCE)
			.addScalar("ARRIVAL", StringType.INSTANCE)
			.addScalar("DEPARTURE_TIME", StringType.INSTANCE)
			.addScalar("ARRIVAL_TIME", StringType.INSTANCE)
			.addScalar("AIRLINE", StringType.INSTANCE)
			.addScalar("ISTRANSIT", StringType.INSTANCE)
			.addScalar("TRANSITID", StringType.INSTANCE)
			.addScalar("UPDATECODE", StringType.INSTANCE)
			.addScalar("CLASS_SEAT", StringType.INSTANCE)
			.addScalar("CLASS_RATE", StringType.INSTANCE)
			.addScalar("CLASS_AVAIL_SEAT", StringType.INSTANCE)
			.addScalar("RADIO_ID", StringType.INSTANCE);
		sqlGroupB.setpr
		sqlGroupB.setResultTransformer(Transformers.aliasToBean(FlightSearchB2B.class));
		*/

		if (_origin != null) {
			sqlGroupB.setParameter("origin", _origin.toUpperCase());
		}
		
		if (_destinationIata != null) {
			sqlGroupB.setParameter("destination", _destinationIata.toUpperCase());
		}
		
		if (transits.size() > 0) {
			sqlGroupB.setParameterList("transits", transits);
		}

		List<?> groupB = sqlGroupB.list();

		log.debug("q2(Transit)=" + groupB.size() + " rows, " + sw.stopAndGetAsString());		
		
		sw.start();
		//step#3: combine GroupA and groupB
		StringBuffer sbGroupNonTransit = new StringBuffer();

		sbGroupNonTransit.append("SELECT A.flightavailID,A.FLIGHTNUM AS FLIGHT_NUMBER,A.ORIGIN,A.DESTINATION,A.DEPARTURE,A.ARRIVAL,")
		.append("TO_CHAR (A.DEP_TIME, 'DD/MM/YYYY HH24:MI:SS A.M.') AS DEPARTURE_TIME,")
		.append("TO_CHAR (A.ARR_TIME, 'DD/MM/YYYY HH24:MI:SS A.M.') AS ARRIVAL_TIME,")
		.append("A.AIRLINE AS AIRLINE,A.ISTRANSIT,A.TRANSITID,A.UPDATECODE,")
		.append("TO_CHAR (A.LASTUPDATE, 'DD/MM/YYYY HH24:MI:SS A.M.') AS LASTUPDATE,")
		.append("listagg (B.class_name, ',') WITHIN GROUP (ORDER BY B.class_sequence) Class_Seat,")
		.append("listagg (B.class_rate, ',') WITHIN GROUP (ORDER BY B.class_sequence) Class_Rate,")
		.append("listagg (B.class_avail_seat, ',') WITHIN GROUP (ORDER BY B.class_sequence) Class_Avail_Seat,")
		.append("listagg (B.radio_id, ',') WITHIN GROUP (ORDER BY B.class_sequence) Radio_ID")
		.append(" FROM FLIGHTAVAILSEAT_CLEAN A JOIN ").append(getTableClassFareName(airline)).append(" B ON A.FLIGHTAVAILID = B.FLIGHTAVAILID")
		.append(" WHERE (A.ISTRANSIT = 0 OR A.ISTRANSIT = 1)")
		.append(_origin == null ? "" : " and ORIGIN=:origin")
		.append(_destinationIata == null ? "" : " and DESTINATION=:destination")
		.append(" AND TRUNC (A.DEP_TIME) = TO_DATE (:departDate, 'DD/MM/YYYY')")
		.append(searchPast ? "" : " AND A.DEP_TIME > SYSDATE + (2 / 24)")
		.append(" and A.AIRLINE = :airline")
		.append(" GROUP BY A.flightavailID, A.FLIGHTNUM, A.ORIGIN, A.DESTINATION, A.DEPARTURE, A.ARRIVAL, A.DEP_TIME, A.ARR_TIME, A.AIRLINE, A.ISTRANSIT, A.TRANSITID,A.UPDATECODE,A.LASTUPDATE")
		;
		
//			.append(" ORDER BY ARRIVAL_TIME, DEPARTURE_TIME")
		//.append(" ORDER BY airline, updatecode, departure_time ")
		sbGroupNonTransit.insert(0, "SELECT * FROM(");
		sbGroupNonTransit.append(") ORDER BY AIRLINE, DEPARTURE_TIME, UPDATECODE");

		SQLQuery sqlGroupNonTransit = getSession().createSQLQuery(sbGroupNonTransit.toString());
	
		sqlGroupNonTransit.setParameter("departDate", _departDate);			
		if (_origin != null) {
			sqlGroupNonTransit.setParameter("origin", _origin.toUpperCase());
		}
		
		if (_destinationIata != null) {
			sqlGroupNonTransit.setParameter("destination", _destinationIata.toUpperCase());
		}
		
		sqlGroupNonTransit.setParameter("airline", airline.getAirlineName());

		List<?> groupNonTransit = sqlGroupNonTransit.list();
		//23des2014 perlu di clean lagi karena ditemukan kasus jam berubah sehingga scraping tdk update, tp insert
		//contoh kasus jakarta-medan(kno) 24 des 2014
		//estimasi untuk clean jika something wrong need 14 days to fix
		/* sementara disable krn mau presentasi airasia, lagian fungsi ini akan dipindah ke manager spy bisa pake DAO yg lain
		while (true){
			boolean _flightClash = false;
			
			for (int i = 0; i < groupNonTransit.size(); i++){
				Object[] ii = (Object[]) groupNonTransit.get(i);
				FlightSearchB2B fs1 = convertObjectColumnsToBean(ii);
				
				List<FlightSearchB2B> _collect = new ArrayList<FlightSearchB2B>();
				_collect.add(fs1);
				for (int j = i+1; j < groupNonTransit.size(); j++){
					Object[] jj = (Object[]) groupNonTransit.get(j);					
					FlightSearchB2B fs2 = convertObjectColumnsToBean(jj);
					
					if (fs2.getFlightNumber().equalsIgnoreCase(fs1.getFlightNumber())
							&& fs2.getAirline().equalsIgnoreCase(fs1.getAirline())
							&& fs2.getUpdateCode().equalsIgnoreCase(fs1.getUpdateCode())
							&& fs2.getDepartureIata().equalsIgnoreCase(fs1.getDepartureIata())
							&& fs2.getArrivalIata().equalsIgnoreCase(fs1.getArrivalIata())
							){
						_collect.add(fs2);
					}
					
				}
				//only check if the second flightno is not exist
				if (_collect.size() < 2) continue;
				
				//cari yg schedulnya nabrak, dimulai aja dr data kedua, krn yg pertama isinya pasti fs1
				long _arrivalTime = _collect.get(0).getArrivalTime().getTime();
				FlightSearchB2B _clash = null;
				for (int p = 1; p < _collect.size(); p++){
					//buang yg jadwalnya tabrakan, dgn cara hitung interval penerbangan, lalu ambil yg paling keberangkatan kedua tdk lebih kecil dari arrival pertama
					if (_collect.get(p).getDepartTime().getTime() < _arrivalTime){
						_flightClash = true;	//ketemu tp belum tentu salah
						_clash = _collect.get(p);
						break;
					}
				}
				//sampai sini pasti ada 2 penerbangan yg salah satunya salah
				if (_clash != null){
					_collect.clear();//reuse
					_collect.add(fs1);
					_collect.add(_clash);
					
					String _suspectId = null;
					long _firstCreatedDate = _collect.get(0).getCreated_Date().getTime();
					long _secondCreatedDate = _collect.get(1).getCreated_Date().getTime();
					//buang yg createddatenya paling lawas
					if (_secondCreatedDate > _firstCreatedDate){
						_suspectId = _collect.get(1).getFlightAvailId();
					}else{
						_suspectId = _collect.get(0).getFlightAvailId();
					}

					if (_suspectId != null){
						for (int z = 0; z < groupNonTransit.size(); z++){
							Object[] _o = (Object[]) groupNonTransit.get(i);
							if (_o[0].toString().equals(_suspectId)){
								groupNonTransit.remove(z);
								break;
							}
						}
					}
					
					break;
				}

			}
			
			if (!_flightClash) break;
		}//while
		*/

		log.debug("q3(NonTransit)=" + groupNonTransit.size() + " rows, " + sw.stopAndGetAsString());
		
		List<?> groupAB = ListUtils.union(groupNonTransit, groupB);
		
		//TODO: check cheapest Airline ?
		//collect price rules
		PriceRule[] priceRules = queryPriceRules(airline);
		
		AirportDescription airportDescription = new AirportDescription();

		for (Iterator iterator = groupAB.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();

			if (airportDescription.get("" + obj[2]).length() < 1)
				airportDescription.add("" + obj[2], getAirportNameAndCity("" + obj[2]));	//originIata
			
			if (airportDescription.get("" + obj[3]).length() < 1)
				airportDescription.add("" + obj[3], getAirportNameAndCity("" + obj[3]));	//destinationIata
			
			if (airportDescription.get("" + obj[4]).length() < 1)
				airportDescription.add("" + obj[4], getAirportNameAndCity("" + obj[4]));	//departureIata
			
			if (airportDescription.get("" + obj[5]).length() < 1)
				airportDescription.add("" + obj[5], getAirportNameAndCity("" + obj[5]));	//arrivalIata
			
			//just add it, will clean it later
			lists.add(convertObjectColumnsToBean(obj, priceRules, airportDescription));
		}

		return cleanList(lists);

	}

	@Override
	public OriginDestinationRaw getOrgDestFromRouteSave(String orgIata,
			String destIata, long webId) {
		Criteria c = getSession().createCriteria(MstRouteSaveVO.class);
		
		c.add(Restrictions.eq("iata_Origin", orgIata));
		c.add(Restrictions.eq("iata_Destination", destIata));
		c.add(Restrictions.eq("web_Id", webId));
		
		int size = c.list().size();
		
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			MstRouteSaveVO obj = (MstRouteSaveVO) iterator.next();
			
			OriginDestinationRaw orgDestRaw = new OriginDestinationRaw(obj.getOrigin(), obj.getDestination());
			
			return orgDestRaw;
		}
		
		return null;
	}

}
