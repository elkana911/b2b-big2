package com.big.web.b2b_big2.hotel.service;

import java.util.List;

import com.big.web.b2b_big2.hotel.model.HotelSearch;
import com.big.web.b2b_big2.hotel.model.HotelVO;
import com.big.web.b2b_big2.hotel.pojo.BookingFormB2B;
import com.big.web.b2b_big2.hotel.pojo.SearchForm;


public interface IHotelManager {
	HotelVO getHotel(String uid);

	List<HotelVO> search(String searchTerm);
	
	List<HotelVO> getHotels();
	List<String> getPrettyHotels();

	List<HotelVO> findByName(String hotelName);
	
	List<HotelSearch> getCheapest(BookingFormB2B searchHotel);
	
	void add(HotelVO hotel);
	void update(HotelVO hotel);
	void remove(HotelVO hotel);
}
