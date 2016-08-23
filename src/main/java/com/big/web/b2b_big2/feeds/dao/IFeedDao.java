package com.big.web.b2b_big2.feeds.dao;

import java.util.List;

import com.big.web.b2b_big2.feeds.model.FeedsVO;
import com.big.web.dao.GenericDao;

public interface IFeedDao extends GenericDao<FeedsVO, String> {
	List<FeedsVO> getAllFeeds();
	List<FeedsVO> getFeeds(int max);
	
	void add(FeedsVO feed);
	void update(FeedsVO feed);
	void remove(FeedsVO feed);
	List<FeedsVO> getHeadlines();	
}
