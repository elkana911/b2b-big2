package com.big.web.b2b_big2.flight.pojo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.util.Utils;

public class SearchForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String departCity;
	private String destCity;
    private int tripMode;
    private int dateMode;
    private String departDate;
    private String returnDate;
    private int adult;
    private int children;
    private int infants;
    
    private boolean allAirlines;
    
    private boolean garuda;
    private boolean airasia;
    private boolean citilink;
    private boolean lion;
    private boolean batik;
    private boolean wings;
    private boolean kalstar;
    private boolean sriwijaya;
    private boolean malindo;
    private boolean nam;
    private boolean express;
    private boolean trigana;
    private boolean susi;
    private boolean aviastar;
    
	public int getTripMode() {
		return tripMode;
	}
	public void setTripMode(int tripMode) {
		this.tripMode = tripMode;
	}
	public int getDateMode() {
		return dateMode;
	}
	public void setDateMode(int dateMode) {
		this.dateMode = dateMode;
	}
	public String getDepartDate() {
		
		if (departDate != null) departDate = departDate.trim();
		return departDate;
	}
	
	public Date parseDepartDate() {
		if (StringUtils.isEmpty(departDate)) return new Date();

		for (String s : Utils.todayWords) {
			if (s.equalsIgnoreCase(departDate)){					
				departDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
				break;
			}
		}
		for (String s : Utils.tommorrowWords) {
			if (s.equalsIgnoreCase(departDate)){					
				Date _dt = new Date(new Date().getTime() + TimeUnit.DAYS.toMillis(1));
				departDate = new SimpleDateFormat("dd/MM/yyyy").format(_dt);
				break;
			}
		}	

		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(departDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}
	
	/**
	 * @param departDate Please use dd/MM/yyyy format
	 */
	public void setDepartDate(String departDate) {
		this.departDate = departDate;
	}
	
	public void setDepartDate(Date departDate){
		setDepartDate(Utils.Date2String(departDate, "dd/MM/yyyy"));
	}
	
	public String getReturnDate() {
		if (returnDate != null) returnDate = returnDate.trim();
		return returnDate;
	}
	
	public Date parseReturnDate() {
		if (StringUtils.isEmpty(returnDate)) return new Date();

		for (String s : Utils.todayWords) {
			if (s.equalsIgnoreCase(returnDate)){					
				returnDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
				break;
			}
		}
		for (String s : Utils.tommorrowWords) {
			if (s.equalsIgnoreCase(returnDate)){					
				Date _dt = new Date(new Date().getTime() + TimeUnit.DAYS.toMillis(1));
				returnDate = new SimpleDateFormat("dd/MM/yyyy").format(_dt);
				break;
			}
		}	

		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(returnDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}	
	}
	
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public void setReturnDate(Date returnDate){
		setReturnDate(Utils.Date2String(returnDate, "dd/MM/yyyy"));
	}
	
	public String getDepartCity() {
		if (departCity != null) departCity = departCity.trim();
		return departCity;
	}
	public void setDepartCity(String departCity) {
		this.departCity = departCity;
	}
	public String getDestCity() {
		if (destCity != null) destCity = destCity.trim();
		return destCity;
	}
	public void setDestCity(String destCity) {
		this.destCity = destCity;
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
	public int getInfants() {
		return infants;
	}
	public void setInfants(int infants) {
		this.infants = infants;
	}
	public List<Airline> getAirlines() {
		List<Airline> airlines = new ArrayList<Airline>();
		
		if (isBatik())	airlines.add(Airline.BATIK);
		if (isWings()) airlines.add(Airline.WINGS);
		if (isLion())	airlines.add(Airline.LION);
		
		if (isCitilink()) airlines.add(Airline.CITILINK);
		if (isGaruda()) airlines.add(Airline.GARUDA);
		
		if (isExpress()) airlines.add(Airline.EXPRESS);
		
		if (isSriwijaya()) airlines.add(Airline.SRIWIJAYA);
		if (isNam()) airlines.add(Airline.NAM);

		if (isKalstar()) airlines.add(Airline.KALSTAR);
		if (isMalindo()) airlines.add(Airline.MALINDO);
		if (isSusi()) airlines.add(Airline.SUSI);
		
		if (isAirasia()) airlines.add(Airline.AIRASIA);
		if (isAviastar()) airlines.add(Airline.AVIASTAR);
		if (isTrigana()) airlines.add(Airline.TRIGANA);
			
		return airlines;
	}
	/*
	public List<String> getAirlines() {
		List<String> airlines = new ArrayList<String>();
		
		if (isBatik())	airlines.add(Airline.BATIK.getAirlineName());
		if (isWings()) airlines.add(Airline.WINGS.getAirlineName());
		if (isLion())	airlines.add(Airline.LION.getAirlineName());
		
		if (isCitilink()) airlines.add(Airline.CITILINK.getAirlineName());
		if (isGaruda()) airlines.add(Airline.GARUDA.getAirlineName());
		
		if (isExpress()) airlines.add(Airline.EXPRESS.getAirlineName());
		
		if (isSriwijaya()) airlines.add(Airline.SRIWIJAYA.getAirlineName());
		if (isNam()) airlines.add(Airline.NAM.getAirlineName());
		
		if (isKalstar()) airlines.add(Airline.KALSTAR.getAirlineName());
		if (isMalindo()) airlines.add(Airline.MALINDO.getAirlineName());
		if (isSusi()) airlines.add(Airline.SUSI.getAirlineName());
		
		if (isAirasia()) airlines.add(Airline.AIRASIA.getAirlineName());
		if (isAviastar()) airlines.add(Airline.AVIASTAR.getAirlineName());
		if (isTrigana()) airlines.add(Airline.TRIGANA.getAirlineName());
		
		return airlines;
	}
	*/
	
	public void setAirlines(String[] airlinesCodes) {
		
		for (String airlineCode : airlinesCodes) {
			setAirasia(airlineCode.equalsIgnoreCase(Airline.AIRASIA.getAirlineCode()));
			setAviastar(airlineCode.equalsIgnoreCase(Airline.AVIASTAR.getAirlineCode()));
			setBatik(airlineCode.equalsIgnoreCase(Airline.BATIK.getAirlineCode()));
			setCitilink(airlineCode.equalsIgnoreCase(Airline.CITILINK.getAirlineCode()));
			setExpress(airlineCode.equalsIgnoreCase(Airline.EXPRESS.getAirlineCode()));
			setGaruda(airlineCode.equalsIgnoreCase(Airline.GARUDA.getAirlineCode()));
			setKalstar(airlineCode.equalsIgnoreCase(Airline.KALSTAR.getAirlineCode()));
			setLion(airlineCode.equalsIgnoreCase(Airline.LION.getAirlineCode()));
			setMalindo(airlineCode.equalsIgnoreCase(Airline.MALINDO.getAirlineCode()));
			setNam(airlineCode.equalsIgnoreCase(Airline.NAM.getAirlineCode()));
			setSriwijaya(airlineCode.equalsIgnoreCase(Airline.SRIWIJAYA.getAirlineCode()));
			setSusi(airlineCode.equalsIgnoreCase(Airline.SUSI.getAirlineCode()));
			setTrigana(airlineCode.equalsIgnoreCase(Airline.TRIGANA.getAirlineCode()));
			setWings(airlineCode.equalsIgnoreCase(Airline.WINGS.getAirlineCode()));
		}
		
	}
	
	public String getDepartIata() throws ParseException {
		return Utils.getIataCode(getDepartCity());
	}
	public String getDestinationIata() throws ParseException{
		return Utils.getIataCode(getDestCity());
	}

	/**
	 * to check whether return date is before depart date.
	 * return date and depart date must not NULL
	 * @return
	 */
	public boolean isValidDates() {
		if (tripMode == 0)
			return parseDepartDate() != null;
		else
			return !parseReturnDate().before(parseDepartDate());
	}

	
	public boolean isAllAirlines() {
		return allAirlines;
	}
	public void setAllAirlines(boolean allAirlines) {
		this.allAirlines = allAirlines;
	}
	public boolean isGaruda() {
		return garuda;
	}
	public void setGaruda(boolean garuda) {
		this.garuda = garuda;
	}
	public boolean isCitilink() {
		return citilink;
	}
	public void setCitilink(boolean citilink) {
		this.citilink = citilink;
	}
	public boolean isLion() {
		return lion;
	}
	public void setLion(boolean lion) {
		this.lion = lion;
	}
	public boolean isBatik() {
		return batik;
	}
	public void setBatik(boolean batik) {
		this.batik = batik;
	}
	public boolean isWings() {
		return wings;
	}
	public void setWings(boolean wings) {
		this.wings = wings;
	}
	public boolean isKalstar() {
		return kalstar;
	}
	public void setKalstar(boolean kalstar) {
		this.kalstar = kalstar;
	}
	public boolean isSriwijaya() {
		return sriwijaya;
	}
	public void setSriwijaya(boolean sriwijaya) {
		this.sriwijaya = sriwijaya;
	}
	public boolean isMalindo() {
		return malindo;
	}
	public void setMalindo(boolean malindo) {
		this.malindo = malindo;
	}
	public boolean isNam() {
		return nam;
	}
	public void setNam(boolean nam) {
		this.nam = nam;
	}
	public boolean isExpress() {
		return express;
	}
	public void setExpress(boolean express) {
		this.express = express;
	}
	public boolean isTrigana() {
		return trigana;
	}
	public void setTrigana(boolean trigana) {
		this.trigana = trigana;
	}
	public boolean isSusi() {
		return susi;
	}
	public void setSusi(boolean susi) {
		this.susi = susi;
	}
	public boolean isAirasia() {
		return airasia;
	}
	public void setAirasia(boolean airasia) {
		this.airasia = airasia;
	}

	public boolean isAviastar() {
		return aviastar;
	}
	public void setAviastar(boolean aviastar) {
		this.aviastar = aviastar;
	}
	
	public boolean isValid(){
		
		if (getAirlines().size() < 1) return false;
		
		try {
			if (Utils.isEmpty(getDepartIata())) return false;

			if (Utils.isEmpty(getDestinationIata())) return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	
	@Override
	public String toString() {
		return "SearchForm [departCity=" + departCity + ", destCity="
				+ destCity + ", tripMode=" + tripMode + ", dateMode="
				+ dateMode + ", departDate=" + departDate + ", returnDate="
				+ returnDate + ", adult=" + adult + ", children=" + children
				+ ", infants=" + infants + ", allAirlines=" + allAirlines
				+ ", garuda=" + garuda + ", airasia=" + airasia + ", citilink="
				+ citilink + ", lion=" + lion + ", batik=" + batik + ", wings="
				+ wings + ", kalstar=" + kalstar + ", sriwijaya=" + sriwijaya
				+ ", malindo=" + malindo + ", nam=" + nam + ", express="
				+ express + ", trigana=" + trigana + ", susi=" + susi
				+ ", aviastar=" + aviastar + "]";
	}

}
