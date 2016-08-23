package com.big.web.b2b_big2.flight.api.kalstar.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.api.kalstar.exception.SqivaServerException;
import com.big.web.b2b_big2.flight.pojo.Airport;
import com.big.web.b2b_big2.flight.pojo.Route;
import com.big.web.b2b_big2.util.Utils;

public class HandlerGetOrgDes extends SqivaAction implements ISqivaAction {
	private static HandlerGetOrgDes self = null;
	
	public static HandlerGetOrgDes getInstance(){
		if (self == null) self = new HandlerGetOrgDes();
		
		return self;
	}

	@Override
	public Object execute() throws Exception {
		String url = buildPreUrl(Action.GET_ORG_DES).build().toString();
		
		if (url == null) return null;

		System.out.println("targetUrl=" + url);
		
		String data = getFromServer(url);
//		String data = "{\"err_code\":\"0\",\"origin_destination\":[[\"BDO\",[\"CGK\",\"PLM\"]],[\"CGK\",[\"BDO\",\"NRT\",\"PDG\",\"PLM\",\"SIN\",\"SUB\",\"UPG\"]]]}";
		
		//complete the data with database ?
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(data);
		
		JsonNode errCodeNode = rootNode.path("err_code");
		String errStr = errCodeNode.getTextValue();
		int errInt = errCodeNode.getIntValue();
		if (errStr.length() > 0 && !errStr.equals("0")){
			String errMsg = rootNode.path("err_msg").getTextValue();
			throw new SqivaServerException(errStr, errMsg);
		}

		List<Route> list = new ArrayList<Route>();
		JsonNode ordDestNode = rootNode.path("origin_destination");
		Iterator<JsonNode> orgDestArray = ordDestNode.getElements();//krn bentuknya array
		while (orgDestArray.hasNext()) {
			JsonNode orgDestItem = orgDestArray.next();	//["BDO",["CGK","PLM"]]
			
			if (orgDestItem.isArray()){
				JsonNode org = orgDestItem.get(0);
				
				Iterator<JsonNode> destArray = orgDestItem.get(1).getElements();
				while (destArray.hasNext()){
					JsonNode dest = destArray.next();
					
					Airport _a1 = new Airport();
					_a1.setIataCode(org.getTextValue());
					
					Airport _a2 = new Airport();
					_a2.setIataCode(dest.getTextValue());
					
					list.add(new Route(_a1, _a2));
				}
			}
		}
		
		return list;
	}
}


/*
Route [from=Airport [name=null, city=null, iataCode=BDO, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=CGK, icaoCode=null, countryId=0, schedule=null]]
Route [from=Airport [name=null, city=null, iataCode=BDO, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=PLM, icaoCode=null, countryId=0, schedule=null]]
Route [from=Airport [name=null, city=null, iataCode=CGK, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=BDO, icaoCode=null, countryId=0, schedule=null]]
Route [from=Airport [name=null, city=null, iataCode=CGK, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=NRT, icaoCode=null, countryId=0, schedule=null]]
Route [from=Airport [name=null, city=null, iataCode=CGK, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=PLM, icaoCode=null, countryId=0, schedule=null]]
Route [from=Airport [name=null, city=null, iataCode=CGK, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=SIN, icaoCode=null, countryId=0, schedule=null]]
Route [from=Airport [name=null, city=null, iataCode=CGK, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=SUB, icaoCode=null, countryId=0, schedule=null]]
Route [from=Airport [name=null, city=null, iataCode=CGK, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=UPG, icaoCode=null, countryId=0, schedule=null]]
Route [from=Airport [name=null, city=null, iataCode=LTN, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=SIN, icaoCode=null, countryId=0, schedule=null]]
Route [from=Airport [name=null, city=null, iataCode=NRT, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=CGK, icaoCode=null, countryId=0, schedule=null]]
Route [from=Airport [name=null, city=null, iataCode=NRT, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=SIN, icaoCode=null, countryId=0, schedule=null]]
Route [from=Airport [name=null, city=null, iataCode=PLM, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=BDO, icaoCode=null, countryId=0, schedule=null]]
Route [from=Airport [name=null, city=null, iataCode=PLM, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=CGK, icaoCode=null, countryId=0, schedule=null]]
Route [from=Airport [name=null, city=null, iataCode=SIN, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=CGK, icaoCode=null, countryId=0, schedule=null]]
Route [from=Airport [name=null, city=null, iataCode=SIN, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=NRT, icaoCode=null, countryId=0, schedule=null]]
Route [from=Airport [name=null, city=null, iataCode=SUB, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=CGK, icaoCode=null, countryId=0, schedule=null]]
Route [from=Airport [name=null, city=null, iataCode=SUB, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=UPG, icaoCode=null, countryId=0, schedule=null]]
Route [from=Airport [name=null, city=null, iataCode=UPG, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=CGK, icaoCode=null, countryId=0, schedule=null]]
Route [from=Airport [name=null, city=null, iataCode=UPG, icaoCode=null, countryId=0, schedule=null], to=Airport [name=null, city=null, iataCode=SUB, icaoCode=null, countryId=0, schedule=null]]
*/