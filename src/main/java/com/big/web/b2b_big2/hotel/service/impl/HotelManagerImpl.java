package com.big.web.b2b_big2.hotel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.big.web.b2b_big2.hotel.dao.IHotelDao;
import com.big.web.b2b_big2.hotel.model.HotelSearch;
import com.big.web.b2b_big2.hotel.model.HotelVO;
import com.big.web.b2b_big2.hotel.pojo.BookingFormB2B;
import com.big.web.b2b_big2.hotel.service.IHotelManager;

@Service("hotelManager")
@Transactional
public class HotelManagerImpl implements IHotelManager{

	@Autowired IHotelDao hotelDao;
	
	@Override
	public HotelVO getHotel(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HotelVO> search(String searchTerm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HotelVO> getHotels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HotelVO> findByName(String hotelName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HotelSearch> getCheapest(BookingFormB2B searchHotel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(HotelVO hotel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(HotelVO hotel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(HotelVO hotel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getPrettyHotels() {
		return hotelDao.getPrettyHotels();
	}

}
