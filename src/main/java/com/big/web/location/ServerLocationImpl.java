package com.big.web.location;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import com.big.web.b2b_big2.util.NetUtils;
import com.big.web.b2b_big2.util.Utils;

/**
 * https://ipinfo.io/developers * 
 * @author Administrator
 *
 */
@Component
public class ServerLocationImpl implements IServerLocation{

	@Override
	public ServerLocation getLocation(String ipAddress) throws Exception {
		String url = "http://ipinfo.io/" + ipAddress + "/json";
	
		/*
		city: "Jakarta"
		country: "ID"
		hostname: "No Hostname"
		ip: "202.62.17.48"
		loc: "-6.1744,106.8294"
		org: "AS4832 PT. iNterNUX"
		region: "Jakarta Raya"
		 */
		String data = NetUtils.getFromServer(url);
//		String data = "{\"ip\": \"202.62.17.48\",\"hostname\": \"No Hostname\",\"city\": \"Jakarta\",\"region\": \"Jakarta Raya\",\"country\": \"ID\",\"loc\": \"-6.1744,106.8294\",\"org\": \"AS4832 PT. iNterNUX\"}";
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(data);
		
		ServerLocation resp = new ServerLocation();
		resp.setCity(rootNode.path("city").getTextValue());
		resp.setCountry(rootNode.path("country").getTextValue());
		resp.setHostName(rootNode.path("hostname").getTextValue());
		resp.setIp(rootNode.path("ip").getTextValue());
		
		String latlong = rootNode.path("loc").getTextValue();
		if (!Utils.isEmpty(latlong)){
			String[] ss = latlong.split(",");
			resp.setLatitude(ss[0]);
			resp.setLongitude(ss[1]);
		}
		resp.setOrg(rootNode.path("org").getTextValue());
		resp.setRegion(rootNode.path("region").getTextValue());
		
		return resp;

	}
	
	public static void main(String[] args) {
		IServerLocation imp = new ServerLocationImpl();
		
		try {
			Object obj = imp.getLocation("202.62.17.48");
			
			System.err.println(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
