package com.big.web.b2b_big2.flight.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.big.web.b2b_big2.country.dao.ICountryDao;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.dao.IAirlineDao;
import com.big.web.b2b_big2.flight.dao.IAirportDao;
import com.big.web.b2b_big2.flight.model.AirlineVO;
import com.big.web.b2b_big2.flight.model.AirportVO;
import com.big.web.b2b_big2.flight.model.FlightAvailSeatVO;
import com.big.web.b2b_big2.flight.model.ODRStatusVO;
import com.big.web.b2b_big2.flight.pojo.Airport;
import com.big.web.b2b_big2.flight.pojo.Route;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.dao.hibernate.GenericDaoHibernate;

@Repository("airlineDao")
public class AirlineDaoHibernate extends GenericDaoHibernate<AirlineVO, Long>
		implements IAirlineDao {

	@Autowired
	IAirportDao airportDao;

	@Autowired
	private ICountryDao countryDao;

	public AirlineDaoHibernate() {
		super(AirlineVO.class);
	}

	@Override
	public AirlineVO findByName(String airlineName) {
		Criteria c = getSession().createCriteria(AirlineVO.class);
		
		c.add(Restrictions.eq("name", airlineName).ignoreCase());

		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			return (AirlineVO) iterator.next();
		}
		return null;
	}
	

	@Override
	public AirlineVO findByCode(String airlineIata) {
		Criteria c = getSession().createCriteria(AirlineVO.class);
		
		c.add(Restrictions.eq("code", airlineIata).ignoreCase());

		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			return (AirlineVO) iterator.next();
		}
		return null;
	}

	/**
	 * @param airlines make sure all items are uppercase 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AirlineVO> findByNames(String[] airlines){
		
		Criteria c = getSession().createCriteria(AirlineVO.class);
		
		//convert to uppercase
		for (int i = 0; i < airlines.length; i++) {
			airlines[i] = airlines[i].toUpperCase();
		}
		c.add(Restrictions.in("name", airlines));	//yg kedua skip aja

		return c.list();
		
	}

	@Override
	public void update(AirlineVO airline) {
		getSession().merge(airline);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Route> getRoutes(Airline airline, Date from, Date to) {
		
		String sFrom = Utils.Date2String(from, "dd/MM/yyyy");
		String sTo = Utils.Date2String(to, "dd/MM/yyyy");

		SQLQuery sql = getSession().createSQLQuery("select distinct(origin), destination from flightavailseat_clean where airline=:airline and TRUNC (DEP_TIME)"
				+ " between trunc(to_date(:from, 'DD/MM/RRRR')) and trunc(to_date(:to, 'DD/MM/RRRR'))"
				+ " order by origin"
				);
		sql.setParameter("airline", airline.getAirlineName());
		sql.setParameter("from", sFrom);
		sql.setParameter("to", sTo);
	
		List<Route> routes = new ArrayList<Route>();

		for (Iterator iterator = sql.list().iterator(); iterator.hasNext();) {
			Object[] obj = (Object[])iterator.next();
			
			try {
				AirportVO a = airportDao.getAirportByIata((String)obj[0]);
				Airport _fromAirport = new Airport();
				_fromAirport.setCity(a.getCity());
				_fromAirport.setCountryId(a.getCountry_id());
//				_fromAirport.setCountry(countryDao.getCountryById(a.getCountry_id()));
				_fromAirport.setIataCode(a.getIata());
				_fromAirport.setIcaoCode(a.getIcao());
				_fromAirport.setName(a.getAirport_name());
				
				AirportVO b = airportDao.getAirportByIata((String)obj[1]);
				Airport _toAirport = new Airport();
				_toAirport.setCity(b.getCity());
				_toAirport.setCountryId(b.getCountry_id());
//				_toAirport.setCountry(countryDao.getCountryById(b.getCountry_id()));
				_toAirport.setIataCode(b.getIata());
				_toAirport.setIcaoCode(b.getIcao());
				_toAirport.setName(b.getAirport_name());
				
				routes.add(new Route(_fromAirport, _toAirport));
			} catch (Exception e) {
				log.error("Error airport data for " + airline + "(" + obj[0] + "-" + obj[1] + ")", e);
			}
			
		}
		
		return routes;
	}

	@Override
	public Date getLastDate(Airline airline) {
		Criteria c = getSession().createCriteria(FlightAvailSeatVO.class);

		c.add(Restrictions.eq("airline", airline.getAirlineName()));
		c.addOrder(Order.desc("dep_time"));
		c.setMaxResults(1);
		
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			FlightAvailSeatVO obj = (FlightAvailSeatVO) iterator.next();

			return obj.getDep_time();
		}

		return null;	
	}

	@Override
	public List<ODRStatusVO> getODRStatus() {
		Criteria c = getSession().createCriteria(ODRStatusVO.class);
		return c.list();
	}

	@Override
	public void saveOdrStatus(List<ODRStatusVO> list) {
		for (ODRStatusVO odrStatus : list) {
			getSession().saveOrUpdate(odrStatus);
		}
		
		//must commit directly
	}

	@Override
	public void saveOdrStatus(ODRStatusVO obj) {
		getSession().saveOrUpdate(obj);
		
	}


}
