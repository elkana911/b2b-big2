package com.big.web.b2b_big2.flight.pojo;

import java.util.HashMap;
import java.util.Map;

import com.big.web.b2b_big2.util.Utils;

public class AirportDescription {
	
	//iata, description
	private Map<String, String> map = new HashMap<String, String>();
	
	public void add(String iata, String description) {
		if (Utils.isEmpty(iata)) return;
		
		map.put(iata, description);
	}

	public String get(String iata) {
		
		if (!Utils.isEmpty(iata))
		for(Map.Entry<String, String> m : map.entrySet() ){
			if (iata.equalsIgnoreCase(m.getKey()))
				return m.getValue();
		}

		return "";
	}

}
