package com.big.web.b2b_big2.flight;

import java.util.List;

/**
 * dipakai juga utk keperluan filter di database, so please be careful
 * @author Administrator
 *
 */
public enum Airline {
	
	AIRASIA("Air Asia", "QZ", 9),
	AVIASTAR("Aviastar", "MV", 7),
	BATIK("Batik Air", "ID", 7),
	CITILINK("Citilink", "QG", 9),
	EXPRESS("Express Air", "XN", 8),
	GARUDA("Garuda Indonesia", "GA", 9),
	KALSTAR("Kalstar", "KD", 7),
	LION("Lion Air", "JT", 7),
	MALINDO("Malindo Air", "OD", 7),
	NAM("Nam Air", "IN", 7),
	SRIWIJAYA("Sriwijaya Air", "SJ", 9),
	SUSI("Susi Air", "SI", 7),
	TRIGANA("Trigana", "IL", 7),
	WINGS("Wings Air", "IW", 7),
	UNKNOWN("", "", 0)
	;
	
	private String airlineName;
	private String airlineCode;
	private int maxPnr;	// WARNING !! pastikan jumlahnya sama jika dalam satu website/keagenan. misal maxPnr utk Lion dan Wings dan Batik Air harus sama.
						// see BookingManagerImpl.generateBooking(..)
	
	private Airline(String airlineName, String airlineCode, int maxPnr){
		this.airlineName = airlineName;
		this.airlineCode = airlineCode;
		this.maxPnr = maxPnr;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	
	public int getMaxPnr() {
		return maxPnr;
	}

	public void setMaxPnr(int maxPnr) {
		this.maxPnr = maxPnr;
	}

	public boolean isKnownCode(String airlineCode){
		 for (Airline d : Airline.values()) {
			 if (airlineCode.equalsIgnoreCase(d.getAirlineCode()))
				 return true;
		 }
		 
		 return false;
	}
	
	public static Airline getAirline(String name){
		 for (Airline d : Airline.values()) {
			 if (name.equalsIgnoreCase(d.getAirlineName()))
				 return d;
		 }
		 return UNKNOWN; 
	}
	
	public static Airline getAirlineByCode(String code){
		for (Airline d : Airline.values()) {
			if (code.equalsIgnoreCase(d.getAirlineCode()))
				return d;
		}
		return UNKNOWN; 
	}
	
	public static Airline[] getAirlinesByCode(String csv){
		String[] ss = org.apache.commons.lang.StringUtils.split(csv, ',');

		Airline[] airlines = new Airline[ss.length];
		for (int i = 0; i < ss.length; i++){
			airlines[i] = getAirlineByCode(ss[i].trim());
		}
		
		return airlines; 
	}
	
	public static boolean isAdultBirthdayRequired(Airline airline){
		return (airline == Airline.CITILINK || airline == Airline.GARUDA || airline == Airline.AIRASIA)
				;		
	}
	
	public static boolean isIDCardRequired(Airline airline, boolean adult, boolean child, boolean infant){
		return (airline == Airline.SRIWIJAYA && adult)
				;
	}
	
	public static boolean isLionGroup(Airline[] airlines){
		
		for (Airline airline : airlines) {
			if (airline == Airline.LION 
					|| airline == Airline.BATIK
					|| airline == Airline.WINGS
					){
				
			}else
				return false;
		}
		
		return true;
	}
	
	public static boolean isSriwijayaGroup(Airline[] airlines){
		for (Airline airline : airlines) {
			if (airline == Airline.SRIWIJAYA 
					|| airline == Airline.NAM
					){
				
			}else
				return false;
		}
		return true;
		
	}

	public static String[] getNames(List<Airline> airlines) {
		String[] values = new String[airlines.size()];
		
		for (int i = 0;  i < airlines.size(); i++){
			values[i] = airlines.get(i).getAirlineName();
		}
		
		return values;
	}
}
