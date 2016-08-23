package com.big.web.b2b_big2.country.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.big.web.b2b_big2.country.dao.ICountryDao;
import com.big.web.b2b_big2.country.model.CountryVO;
import com.big.web.b2b_big2.country.service.ICountryManager;
import com.big.web.service.impl.GenericManagerImpl;

@Service("countryManager")
@Transactional
public class CountryManagerImpl extends GenericManagerImpl<CountryVO, Long> implements ICountryManager{

	ICountryDao countryDao;	//jgn rename menjadi 'dao' karena di parent class udah dipake

	@Autowired
	public CountryManagerImpl(ICountryDao countryDao) {
		super(countryDao);
		this.countryDao = countryDao; 
	}

	@Override
	public List<CountryVO> getCountries() {
		return countryDao.getAllDistinct();
	}

}
