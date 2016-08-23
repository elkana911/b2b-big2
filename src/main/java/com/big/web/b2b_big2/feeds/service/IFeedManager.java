package com.big.web.b2b_big2.feeds.service;

import java.util.List;

import com.big.web.b2b_big2.feeds.model.FeedsVO;
import com.big.web.service.GenericManager;

public interface IFeedManager extends GenericManager<FeedsVO, String>{
	List<FeedsVO> getAllFeeds();
	
	List<FeedsVO> getFeeds(int max);
	List<FeedsVO> search(String searchTerm);
	
	void add(FeedsVO feed);
	void update(FeedsVO feed);
	void remove(FeedsVO feed);

	List<FeedsVO> getHeadlines();
}
