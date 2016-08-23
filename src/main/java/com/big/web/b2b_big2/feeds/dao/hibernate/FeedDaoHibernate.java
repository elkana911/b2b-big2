package com.big.web.b2b_big2.feeds.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.big.web.b2b_big2.feeds.dao.IFeedDao;
import com.big.web.b2b_big2.feeds.model.FeedsVO;
import com.big.web.b2b_big2.feeds.pojo.Headline;
import com.big.web.dao.hibernate.GenericDaoHibernate;

@Repository("feedDao")
public class FeedDaoHibernate extends GenericDaoHibernate<FeedsVO, String> implements IFeedDao {

	public FeedDaoHibernate() {
		super(FeedsVO.class);
	}
	
	@Override
	public List<FeedsVO> getFeeds(int max) {
		Criteria c = getSession().createCriteria(FeedsVO.class);
		
		c.addOrder(Order.desc("lastUpdate"));
		c.setMaxResults(max);
		
		return c.list();	
	}

	@Override
	public List<FeedsVO> getAllFeeds() {
		return getAllDistinct();
	}

	@Override
	public void add(FeedsVO feed) {
		
		getSession().save(feed);
		
	}

	@Override
	public void update(FeedsVO feed) {
		getSession().merge(feed);
	}

	@Override
	public void remove(FeedsVO feed) {
		getSession().delete(feed);
	}

	@Override
	public List<FeedsVO> getHeadlines() {
		Criteria c = getSession().createCriteria(FeedsVO.class);
		
		c.addOrder(Order.asc("headline"));
		c.add(Restrictions.isNotNull("headline"));
		
		//c.setMaxResults(max);
		
		return c.list();	
	}
}
