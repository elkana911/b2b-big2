package com.big.web.b2b_big2.finance.cart.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.model.AirlineVO;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.util.Utils;

public class FlightItinerary implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1020749197979053996L;

	/**
	 * 
	 */
	private List<FareInfo> fareInfos;

	public List<FareInfo> getFareInfos() {
		return fareInfos;
	}

	public void setFareInfos(List<FareInfo> fareInfos) {
		this.fareInfos = fareInfos;
	}

	public boolean isNonStop(){
		return fareInfos.size() < 2;
	}
	
	public boolean isEmpty(){
		return fareInfos.size() < 1;
	}
	
	public BigDecimal getAmount(){
		BigDecimal sum = BigDecimal.ZERO;
		
		for (FareInfo i : fareInfos) {
			if (i.getAmount() == null){
				continue;
			}
			
			sum = sum.add(i.getAmount());
		}
		
		return sum;
	}
	
	public String[] getFlightNumbers(){
		String[] ss = new String[fareInfos.size()];
		
		for (int i = 0; i < fareInfos.size(); i++){
			ss[i] = fareInfos.get(i).getFlightNo();
		}
		
		return ss;
	}
	
	public String[] getFlightClass(){
		//kayanya utk kalstar harus dibedakan cara ambilnya
		String[] ss = new String[fareInfos.size()];
		
		for (int i = 0; i < fareInfos.size(); i++){
			
			StringBuffer sb = new StringBuffer(fareInfos.get(i).getClassName());

			if (Airline.getAirlineByCode(fareInfos.get(i).getAirlineIata()) == Airline.KALSTAR){

				if (!Utils.isEmpty(fareInfos.get(i).getBaseFare())){
					sb.append("/").append(fareInfos.get(i).getBaseFare());	//contoh A/a
				}
				
			}

			ss[i] = sb.toString();
		}
		
		return ss;
	}
	
	public String[] getRadioIds(){
		List<String> radios = new ArrayList<String>();

		List<FareInfo> listA = getFareInfos();

		//harus urut by istransit
		if (listA.size() > 1)
	    	Collections.sort(listA,  new Comparator<FareInfo>() {
	    		@Override
	    		public int compare(FareInfo s1, FareInfo s2) {
	    			String is1 = s1.getIsTransit() == null ? "0" : s1.getIsTransit();
	    			String is2 = s2.getIsTransit() == null ? "0" : s2.getIsTransit();
	    			
	    			return is1.compareTo(is2);
	    			
//	    			return s1.getIsTransit()isCheapest()Name().compareTo(s2.getName());
	    		}
	    	});
		
		for (int i = 0; i < listA.size(); i++) {
			radios.add(listA.get(i).getRadioId());
		}

		return radios.toArray(new String[radios.size()]);
	}
	
}
