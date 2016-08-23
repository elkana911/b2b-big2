package com.big.web.b2b_big2.hotel.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.big.web.b2b_big2.util.Utils;

public class SearchForm {

	private String query;
	private String customCountry;	//based on bedsonline
	private String customCity;
	private String customZone;
    private String checkInDate;
    private String checkOutDate;
    private int nights;
    private int rooms;

    private int adult;
    private int children;
    private boolean searchPast;

	public String getCheckInDate() {
		if (checkInDate != null) checkInDate = checkInDate.trim();
		return checkInDate;
	}

	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}
	
	public Date parseCheckInDate(){
		if (StringUtils.isEmpty(checkInDate)) return new Date();

		for (String s : Utils.todayWords) {
			if (s.equalsIgnoreCase(checkInDate)){					
				checkInDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
				break;
			}
		}
		for (String s : Utils.tommorrowWords) {
			if (s.equalsIgnoreCase(checkInDate)){					
				Date _dt = new Date(new Date().getTime() + TimeUnit.DAYS.toMillis(1));
				checkInDate = new SimpleDateFormat("dd/MM/yyyy").format(_dt);
				break;
			}
		}	

		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(checkInDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
		
	}

	public String getCheckOutDate() {
		if (checkOutDate != null) checkOutDate = checkOutDate.trim();
		return checkOutDate;
	}

	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Date parseCheckOutDate() {
		if (StringUtils.isEmpty(checkOutDate)) return new Date();

		for (String s : Utils.todayWords) {
			if (s.equalsIgnoreCase(checkOutDate)){					
				checkOutDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
				break;
			}
		}
		for (String s : Utils.tommorrowWords) {
			if (s.equalsIgnoreCase(checkOutDate)){					
				Date _dt = new Date(new Date().getTime() + TimeUnit.DAYS.toMillis(1));
				checkOutDate = new SimpleDateFormat("dd/MM/yyyy").format(_dt);
				break;
			}
		}	

		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(checkOutDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}	
	}

	public String getQuery() {
		if (query != null) query = query.trim();
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getAdult() {
		return adult;
	}
	public void setAdult(int adult) {
		this.adult = adult;
	}
	public int getChildren() {
		return children;
	}
	public void setChildren(int children) {
		this.children = children;
	}

	/**
	 * to check whether return date is before depart date.
	 * return date and depart date must not NULL
	 * @return
	 */
	public boolean isValidDates() {
		return !parseCheckOutDate().before(parseCheckInDate());
	}
	public boolean isSearchPast() {
		return searchPast;
	}
	public void setSearchPast(boolean searchPast) {
		this.searchPast = searchPast;
	}

	public String getCustomCountry() {
		return customCountry;
	}

	public void setCustomCountry(String customCountry) {
		this.customCountry = customCountry;
	}

	public String getCustomCity() {
		return customCity;
	}

	public void setCustomCity(String customCity) {
		this.customCity = customCity;
	}

	public String getCustomZone() {
		return customZone;
	}

	public void setCustomZone(String customZone) {
		this.customZone = customZone;
	}

	public int getNights() {
		return nights;
	}

	public void setNights(int nights) {
		this.nights = nights;
	}

	public int getRooms() {
		return rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}

	
}
