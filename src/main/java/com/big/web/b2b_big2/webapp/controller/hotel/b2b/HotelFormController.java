package com.big.web.b2b_big2.webapp.controller.hotel.b2b;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.big.web.b2b_big2.country.service.ICountryManager;
import com.big.web.b2b_big2.hotel.service.IHotelManager;
import com.big.web.b2b_big2.webapp.controller.BaseFormController;

public class HotelFormController extends BaseFormController{
	@Autowired
	private ICountryManager countryManager;
	
	@Autowired private IHotelManager hotelManager;
	
    @RequestMapping(value = "/getHotels", method = RequestMethod.GET, headers="Accept=*/*")
    public @ResponseBody List<String> getPrettyHotels(){
    	
    	List<String> hotels = hotelManager.getPrettyHotels();
    	
    	return hotels;
    }

    @RequestMapping(value = "/getHotelsByCity", method = RequestMethod.GET, headers="Accept=*/*")
    public @ResponseBody List<String> getPrettyHotelsByCity(@RequestParam(value = "city") String city){
    	
    	List<String> hotels = hotelManager.getPrettyHotels();
    	return hotels;
    }

    @RequestMapping(value = "/getHotelsByName", method = RequestMethod.GET, headers="Accept=*/*")
    public @ResponseBody List<String> getPrettyHotelsByName(@RequestParam(value = "name") String name){
    	List<String> hotels = hotelManager.getPrettyHotels();
    	return hotels;
    }
}
