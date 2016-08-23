package com.big.web.b2b_big2.country.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.big.web.b2b_big2.country.dao.ICountryDao;
import com.big.web.b2b_big2.country.model.CountryVO;
import com.big.web.dao.hibernate.GenericDaoHibernate;

@Repository("countryDao")
public class CountryDaoHibernate extends GenericDaoHibernate<CountryVO, Long>
implements ICountryDao  {
	
	public CountryDaoHibernate() {
		super(CountryVO.class);
	}

	@Transactional
	@Override
	public List<String> getCountries(String filter) {

		Criteria criteria = getSession().createCriteria(CountryVO.class);
		criteria.setProjection(Projections.projectionList().add(Projections.property("country_name")));
		criteria.add(Restrictions.ilike("country_name", "%" + filter + "%", MatchMode.ANYWHERE));
		
		return criteria.list();

	}

	@Override
	public CountryVO getCountryById(Long country_id) {
		Criteria c = getSession().createCriteria(CountryVO.class);
		
		c.add(Restrictions.eq("country_id", country_id));
		
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			return (CountryVO) iterator.next();
		}
		return null;
	}
}
