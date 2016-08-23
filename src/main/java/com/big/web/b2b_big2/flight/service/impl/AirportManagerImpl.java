package com.big.web.b2b_big2.flight.service.impl;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.big.web.b2b_big2.flight.dao.IAirportDao;
import com.big.web.b2b_big2.flight.model.AirportVO;
import com.big.web.b2b_big2.flight.service.IAirportManager;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.service.impl.GenericManagerImpl;

@Service("airportManager")
@Transactional
public class AirportManagerImpl extends GenericManagerImpl<AirportVO, String> implements IAirportManager{

	IAirportDao airportDao;	//jgn rename menjadi 'dao' karena di parent class udah dipake

	@Autowired
	public AirportManagerImpl(IAirportDao airportDao) {
		super(airportDao);
		this.airportDao = airportDao; 
	}

	@Override
	public List<AirportVO> search(String searchTerm) {
		return super.search(searchTerm, AirportVO.class);
	}
	

	@Override
	public List<AirportVO> searchByCountry(String searchTerm, String countryCode) {
		// TODO Auto-generated method stub
		return airportDao.searchByCountry(searchTerm, countryCode);
	}

	@Override
	public List<AirportVO> getAirports() {
		return airportDao.getAllDistinct();
	}

	@Override
	public AirportVO getAirport(String uid) {
		return uid == null ? null : airportDao.get(uid);
	}

	@Override
	public String getAirportCode(String city) {
		return airportDao.getAirportCode(city);
	}


	@Override
	public void addAirport(AirportVO airport) {
		airportDao.saveAirport(airport);
	}


	@Override
	public void updateAirport(AirportVO airport) {
		airportDao.updateAirport(airport);
	}


	@Override
	public void removeAirport(AirportVO airport) {
		
		airportDao.removeAirport(airport);
	}

	@Override
	public List<String> getPrettyAirports() {
		return airportDao.getPrettyAirports();
	}

	/**
	 * 
	 * @param fieldValue asumsi user mengisi sembarang, atau via autocomplete
	 * @return
	 */
	@Override
	public String getCity(String fieldValue) {

		//mungkin ada kurungnya
		String tmp;
		try {
			fieldValue = Utils.getIataCode(fieldValue);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		if (fieldValue.length() > 3){
			tmp = getAirportCode(fieldValue);

			//last attempt, cari manual lwt list
			if (tmp == null){
		    	List<String> airports = getPrettyAirports();
		    	for (String s: airports){
//		    		jakarta (cgk) kok ga nemu
		    		if (s.toLowerCase().contains(fieldValue.toLowerCase())){
		    			try {
							tmp = Utils.getIataCode(s);
						} catch (ParseException e) {
							e.printStackTrace();
						}
		    			break;
		    		}
		    	}
			}

		}else{
			//validate the iata
			if (fieldValue.length() < 1){
				return "";
			}else 
				tmp = fieldValue;
		}
		
		return airportDao.getCity(tmp);
	}

	@Override
	public AirportVO getAirportByIata(String iata) {
		return airportDao.getAirportByIata(iata);
	}
}
