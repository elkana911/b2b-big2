package com.big.web.b2b_big2.country.dao;

import java.util.List;

import com.big.web.b2b_big2.country.model.CountryVO;
import com.big.web.dao.GenericDao;


public interface ICountryDao extends GenericDao<CountryVO, Long>{

	List<String> getCountries(String filter);

	CountryVO getCountryById(Long country_id);
	
}
