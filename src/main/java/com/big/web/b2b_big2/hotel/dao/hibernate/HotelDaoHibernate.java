package com.big.web.b2b_big2.hotel.dao.hibernate;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import com.big.web.b2b_big2.hotel.dao.IHotelDao;
import com.big.web.hibernate.BasicHibernate;

@Repository("hotelDao")
public class HotelDaoHibernate extends BasicHibernate implements IHotelDao{

	@Override
	public List<String> getPrettyHotels() {
		StringBuffer sql = new StringBuffer("select * from mst_hotels")
		.append(" WHERE country_id in (62, 60, 65, 66, 81)")
		.append(" order by city, hotel_name")
				;

		SQLQuery query = getSession().createSQLQuery(sql.toString());
//		SQLQuery query = getSession().createSQLQuery(sql).addScalar("xxx", StringType.INSTANCE);

		return query.list();		
	}

}
