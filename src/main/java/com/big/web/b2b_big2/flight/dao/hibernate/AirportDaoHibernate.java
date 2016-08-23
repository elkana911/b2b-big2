package com.big.web.b2b_big2.flight.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.big.web.b2b_big2.country.model.CountryVO;
import com.big.web.b2b_big2.flight.dao.IAirportDao;
import com.big.web.b2b_big2.flight.model.AirportVO;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.dao.hibernate.GenericDaoHibernate;

@Repository("airportDao")
public class AirportDaoHibernate extends GenericDaoHibernate<AirportVO, String>
		implements IAirportDao {

	public AirportDaoHibernate() {
		super(AirportVO.class);
	}

	
	@SuppressWarnings("rawtypes")
	@Override
	public String getCity(String iata) {
		if (StringUtils.isEmpty(iata)) return null;
		
		iata = iata.trim().toUpperCase();
		String sql = "select CITY from MST_AIRPORTS"
					+ " WHERE IATA=:iata"
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

	@Override
	public void saveAirport(AirportVO airport) {
		getSession().save(airport);
	}

	@Override
	public void updateAirport(AirportVO airport) {
		getSession().merge(airport);
	}

	@Override
	public void removeAirport(AirportVO airport) {
		getSession().delete(airport);
	}

	/**
	 * display all airports in text formation: Jakarta, Soekarno Intl (CGK)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPrettyAirports() {
		
		String sql = "select city || ', ' || airport_name || ' (' || iata || ')' as xxx from mst_airports"
				+ " WHERE country_id in (62, 60, 65, 66, 81) and iata is not null"
				+ " order by city"
				;
		SQLQuery query = getSession().createSQLQuery(sql).addScalar("xxx", StringType.INSTANCE);

		return query.list();		

		/*
		Criteria criteria = getSession().createCriteria(AirportVO.class);
		criteria.setProjection(Projections.projectionList()
				.add(Projections.property("airport_name"))
				.add(Projections.property("iata"))
				);
		
		if (!StringUtils.isEmpty(filter))
			criteria.add(Restrictions.ilike("airport_name", "%" + filter + "%", MatchMode.ANYWHERE));
		
		return criteria.list();
		*/
	}


	@Override
	public AirportVO getAirportByIata(String iata) {
		Criteria criteria = getSession().createCriteria(AirportVO.class);
		criteria.add(Restrictions.eq("iata", iata));
		
		List<?> list = criteria.list();
		
		if (list.size() > 1){
			//ambil beberapa negara saja
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				AirportVO obj = (AirportVO)iterator.next();
				
				int countryId = obj.getCountry_id() == null ? 0 : obj.getCountry_id().intValue();
				
				if (countryId == CountryVO.INDONESIA 
					|| countryId == CountryVO.JAPAN
					|| countryId == CountryVO.MALAYSIA
					|| countryId == CountryVO.SINGAPORE
					|| countryId == CountryVO.THAILAND
					)
					return obj;
			}
			
		}else{
			if (list.size() == 1)
				return (AirportVO)list.get(0);
			
		}

		return null;
	}

	@Override
	public List<AirportVO> searchByCountry(String searchTerm, String countryCode) {

		/*
			select a.* from mst_airports a inner join mst_countries b 
			on a.country_id = b.country_id
			where b.country_code = 'ID'		 
		 */
		StringBuffer sb = new StringBuffer("select a.* from mst_airports a inner join mst_countries b on a.country_id = b.country_id where 1=1");

		String[] searchTerms = null;
		if (!Utils.isEmpty(searchTerm)){
			searchTerms = org.apache.commons.lang.StringUtils.split(searchTerm, ',');
			for (int i = 0; i < searchTerms.length; i++) {
				
				if (i > 0)
					sb.append(" or ");
				else
					sb.append(" and ");
				
				if (!Utils.isEmpty(searchTerms[i])){
					/*
					sb.append(" (UPPER(a.airport_name) like '%' || UPPER(:searchTerm" + i + ") || '%'" );
					sb.append(" or UPPER(a.aka) like '%' || UPPER(:searchTerm" + i + ") || '%'" );
					sb.append(" or UPPER(a.iata) like '%' || UPPER(:searchTerm" + i + ") || '%'" );
					sb.append(")");
					*/
					sb.append(" (UPPER(a.airport_name) like '%' || UPPER(:searchTerm" + i + ") || '%'" );
					sb.append(" or UPPER(a.aka) like '%' || UPPER(:searchTerm" + i + ") || '%'" );
					sb.append(" or a.iata like '%' || :searchTerm" + i + " || '%'" );
					sb.append(")");
				}
			}
		}
		
        if (!Utils.isEmpty(countryCode)){
        	sb.append(" and b.country_code = :countryCode");
        }
        sb.append(" order by a.airport_name");
        
        SQLQuery q = getSession().createSQLQuery(sb.toString());
        q.addEntity(AirportVO.class);
        
		if (!Utils.isEmpty(searchTerm)){
			for (int i = 0; i < searchTerms.length; i++) {
				q.setParameter("searchTerm" + i, searchTerms[i].toUpperCase());
			}
			
		}
		
        if (!Utils.isEmpty(countryCode)){
        	q.setParameter("countryCode", countryCode);
        }
        
        return q.list();

	}

}
