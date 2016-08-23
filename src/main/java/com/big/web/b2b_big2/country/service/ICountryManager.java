package com.big.web.b2b_big2.country.service;

import java.util.List;

import com.big.web.b2b_big2.country.model.CountryVO;
import com.big.web.service.GenericManager;

public interface ICountryManager  extends GenericManager<CountryVO, Long>{
	List<CountryVO> getCountries();
}
