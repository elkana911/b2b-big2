package com.big.web.b2b_big2.feeds.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.big.web.b2b_big2.feeds.dao.IFeedDao;
import com.big.web.b2b_big2.feeds.model.FeedsVO;
import com.big.web.b2b_big2.feeds.service.IFeedManager;
import com.big.web.service.impl.GenericManagerImpl;

@Service("feedManager")
@Transactional
public class FeedManagerImpl extends GenericManagerImpl<FeedsVO, String> implements IFeedManager {

	IFeedDao feedDao;

	@Autowired
	public FeedManagerImpl(IFeedDao feedDao) {
		super(feedDao);
		this.feedDao = feedDao; 
	}

	@Override
	public List<FeedsVO> getAllFeeds() {
		return feedDao.getAllDistinct();
	}

	@Override
	public List<FeedsVO> getFeeds(int max) {
		return feedDao.getFeeds(max);
	}

	@Override
	public void add(FeedsVO feed) {
		feedDao.save(feed);
	}

	@Override
	public void update(FeedsVO feed) {
		feedDao.update(feed);
	}

	@Override
	public void remove(FeedsVO feed) {
		feedDao.remove(feed);
	}

	@Override
	public List<FeedsVO> getHeadlines() {
		return feedDao.getHeadlines();
	}

	@Override
	public List<FeedsVO> search(String searchTerm) {
		return super.search(searchTerm, FeedsVO.class);
	}

}
