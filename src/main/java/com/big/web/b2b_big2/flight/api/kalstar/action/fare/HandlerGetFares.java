package com.big.web.b2b_big2.flight.api.kalstar.action.fare;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.jfree.util.Log;

import com.big.web.b2b_big2.flight.api.kalstar.ConfigUrl;
import com.big.web.b2b_big2.flight.api.kalstar.SqivaHandler;
import com.big.web.b2b_big2.flight.api.kalstar.action.Action;
import com.big.web.b2b_big2.flight.api.kalstar.action.ISqivaAction;
import com.big.web.b2b_big2.flight.api.kalstar.action.SqivaAction;
import com.big.web.b2b_big2.util.Utils;

public class HandlerGetFares extends SqivaAction implements ISqivaAction {
//	private static HandlerGetFares self = null;	//dont use getInstance if you intent to refresh the data
	private String originIata;
	private String destIata;
	private Date departDate;
	private String flightNo;

	private Date returnDate;
	private String returnFlightNo;


	private String parseFlightNo(String flightNo){
		if (flightNo.indexOf("-") > 0)
			return flightNo.split("-", 2)[1];
		else
			return flightNo;
	}
	
	public HandlerGetFares(ConfigUrl configUrl){
		setConfigUrl(configUrl);
	}
	
	public HandlerGetFares(ConfigUrl configUrl, String originIata, String destIata, Date departDate, String flightNo) {
		setConfigUrl(configUrl);
		this.originIata = originIata.toUpperCase();
		this.destIata = destIata.toUpperCase();
		this.departDate = departDate;
		this.flightNo = parseFlightNo(flightNo);
		
		this.returnDate = null;
		this.returnFlightNo = null;
	}
	public HandlerGetFares(ConfigUrl configUrl, String originIata, String destIata, Date departDate, String flightNo, Date returnDate, String returnFlightNo) {
		setConfigUrl(configUrl);
		this.originIata = originIata.toUpperCase();
		this.destIata = destIata.toUpperCase();
		this.departDate = departDate;
		this.flightNo = parseFlightNo(flightNo);
		
		this.returnDate = returnDate;
		this.returnFlightNo = parseFlightNo(returnFlightNo);
		
	}
/*
	public static HandlerGetFares getInstance(String originIata, String destIata, Date departDate, String flightNo){
		if (self == null) self = new HandlerGetFares(originIata, destIata, departDate, flightNo);
		
		return self;
	}

	public static HandlerGetFares getInstance(String originIata, String destIata, Date departDate, String flightNo, Date returnDate, String returnFlightNo){
		if (self == null) self = new HandlerGetFares(originIata, destIata, departDate, flightNo, returnDate, returnFlightNo);
		
		return self;
	}
*/
	//di kalstar ada beberapa harga utk 1 kelas, tp katanya diambil yg pertama saja
	private PersonFare readFare(JsonNode node){
		PersonFare f = new PersonFare();
		
		f.setTotalFare(node.get(0).getIntValue());
		f.setBasicFare(node.get(1).getIntValue());
		f.setInsurance(node.get(2).getIntValue());
		f.setAirportTax(node.get(3).getIntValue());
		f.setSurcharge(node.get(4).getIntValue());
		f.setTerminalFee(node.get(5).getIntValue());
		f.setBookingFee(node.get(6).getIntValue());
		f.setVat(node.get(7).getIntValue());
		
		return f;
	}
	/**
	 * @return {@link ResponseGetFare} 
	 */
	@Override
	public Object execute() throws Exception {

		UriBuilder uri = buildPreUrl(Action.GET_FARE_V2_NEW)
					.queryParam("org", originIata)
					.queryParam("des", destIata)
					.queryParam("flight_date", Utils.Date2String(departDate, "yyyyMMdd"))
					.queryParam("flight_no", flightNo)
				;

		if (returnDate != null){
			//soalnya di dokumen non mandatory
			uri.queryParam("return_flight", "1")
				.queryParam("ret_flight_date", Utils.Date2String(returnDate, "yyyyMMdd"))
				.queryParam("ret_flight_no", returnFlightNo);
		}
		
		String url = uri.build().toString();
		if (url == null) return null;

		System.out.println("targetUrl=" + url);
		
		String data = getFromServer(url);
//		String data = "{ \"err_code\": \"500107\", \"err_msg\": \"Fare not found : flight_no: 1015, flight_date: 20150129\" }"
//		String data = "{ \"err_code\": \"0\", \"flight_date\": \"20150130\", \"flight_no\": \"1015\", \"return_flight\":0,\"ret_flight_date\":\"\",\"ret_flight_no\":\"\",\"ret_fare_info\":[],\"org\":\"CGK\",\"des\":\"SIN\",\"fare_info\":[[\"Y/a\",[814000, 740000, 0, 0, 0, 0,0,74000],[462000, 420000, 0, 0, 0, 0,0,42000],[81400, 74000, 0, 0, 0,0,0,7400],[814000, 740000, 0, 0, 0, 0,0,74000],[462000, 420000, 0, 0, 0, 0,0,42000],[81400, 74000, 0, 0, 0,0,0,7400],\"\",\"IBOOK\",\"7\",\"15\",\"IDR\", 814000, 814000, 462000, 462000, 0, 0], [\"G/a\",[792000, 720000, 0, 0, 0, 0,0,72000],[440000, 400000, 0, 0, 0, 0,0,40000],[79200, 72000, 0, 0, 0,0,0,7200],[792000, 720000, 0, 0, 0, 0,0,72000],[440000, 400000, 0, 0, 0, 0,0,40000],[79200, 72000, 0, 0, 0,0,0,7200],\"\",\"IBOOK\",\"7\",\"15\",\"IDR\", 792000, 792000, 440000, 440000, 0, 0], [\"H/a\",[770000, 700000, 0, 0, 0, 0,0,70000],[396000, 360000, 0, 0, 0, 0,0,36000],[77000, 70000, 0, 0, 0,0,0,7000],[770000, 700000, 0, 0, 0, 0,0,70000],[396000, 360000, 0, 0, 0, 0,0,36000],[77000, 70000, 0, 0, 0,0,0,7000],\"\",\"IBOOK\",\"7\",\"15\",\"IDR\", 770000, 770000, 396000, 396000, 0, 0], [\"K/a\",[748000, 680000, 0, 0, 0, 0,0,68000],[374000, 340000, 0, 0, 0, 0,0,34000],[74800, 68000, 0, 0, 0,0,0,6800],[748000, 680000, 0, 0, 0, 0,0,68000],[374000, 340000, 0, 0, 0, 0,0,34000],[74800, 68000, 0, 0, 0,0,0,6800],\"\",\"IBOOK\",\"7\",\"15\",\"IDR\", 748000, 748000, 374000, 374000, 0, 0], [\"L/a\",[726000, 660000, 0, 0, 0, 0,0,66000],[352000, 320000, 0, 0, 0, 0,0,32000],[72600, 66000, 0, 0, 0,0,0,6600],[726000, 660000, 0, 0, 0, 0,0,66000],[352000, 320000, 0, 0, 0, 0,0,32000],[72600, 66000, 0, 0, 0,0,0,6600],\"\",\"IBOOK\",\"7\",\"15\",\"IDR\", 726000, 726000, 352000, 352000, 0, 0], [\"M/a\",[704000, 640000, 0, 0, 0, 0,0,64000],[330000, 300000, 0, 0, 0, 0,0,30000],[70400, 64000, 0, 0, 0,0,0,6400],[704000, 640000, 0, 0, 0, 0,0,64000],[330000, 300000, 0, 0, 0, 0,0,30000],[70400, 64000, 0, 0, 0,0,0,6400],\"\",\"IBOOK\",\"7\",\"15\",\"IDR\", 704000, 704000, 330000, 330000, 0, 0], [\"N/a\",[682000, 620000, 0, 0, 0, 0,0,62000],[308000, 280000, 0, 0, 0, 0,0,28000],[68200, 62000, 0, 0, 0,0,0,6200],[682000, 620000, 0, 0, 0, 0,0,62000],[308000, 280000, 0, 0, 0, 0,0,28000],[68200, 62000, 0, 0, 0,0,0,6200],\"\",\"IBOOK\",\"7\",\"15\",\"IDR\", 682000, 682000, 308000, 308000, 0, 0], [\"Q/a\",[660000, 600000, 0, 0, 0, 0,0,60000],[286000, 260000, 0, 0, 0, 0,0,26000],[66000, 60000, 0, 0, 0,0,0,6000],[660000, 600000, 0, 0, 0, 0,0,60000],[286000, 260000, 0, 0, 0, 0,0,26000],[66000, 60000, 0, 0, 0,0,0,6000],\"\",\"IBOOK\",\"7\",\"15\",\"IDR\", 660000, 660000, 286000, 286000, 0, 0], [\"R/a\",[638000, 580000, 0, 0, 0, 0,0,58000],[264000, 240000, 0, 0, 0, 0,0,24000],[63800, 58000, 0, 0, 0,0,0,5800],[638000, 580000, 0, 0, 0, 0,0,58000],[264000, 240000, 0, 0, 0, 0,0,24000],[63800, 58000, 0, 0, 0,0,0,5800],\"\",\"IBOOK\",\"7\",\"15\",\"IDR\", 638000, 638000, 264000, 264000, 0, 0], [\"T/a\",[616000, 560000, 0, 0, 0, 0,0,56000],[242000, 220000, 0, 0, 0, 0,0,22000],[61600, 56000, 0, 0, 0,0,0,5600],[616000, 560000, 0, 0, 0, 0,0,56000],[242000, 220000, 0, 0, 0, 0,0,22000],[61600, 56000, 0, 0, 0,0,0,5600],\"\",\"IBOOK\",\"7\",\"15\",\"IDR\", 616000, 616000, 242000, 242000, 0, 0], [\"V/a\",[594000, 540000, 0, 0, 0, 0,0,54000],[220000, 200000, 0, 0, 0, 0,0,20000],[59400, 54000, 0, 0, 0,0,0,5400],[594000, 540000, 0, 0, 0, 0,0,54000],[220000, 200000, 0, 0, 0, 0,0,20000],[59400, 54000, 0, 0, 0,0,0,5400],\"\",\"IBOOK\",\"7\",\"15\",\"IDR\", 594000, 594000, 220000, 220000, 0, 0], [\"X/a\",[572000, 520000, 0, 0, 0, 0,0,52000],[198000, 180000, 0, 0, 0, 0,0,18000],[57200, 52000, 0, 0, 0,0,0,5200],[572000, 520000, 0, 0, 0, 0,0,52000],[198000, 180000, 0, 0, 0, 0,0,18000],[57200, 52000, 0, 0, 0,0,0,5200],\"\",\"IBOOK\",\"7\",\"15\",\"IDR\", 572000, 572000, 198000, 198000, 0, 0], [\"A/a\",[550000, 500000, 0, 0, 0, 0,0,50000],[176000, 160000, 0, 0, 0, 0,0,16000],[55000, 50000, 0, 0, 0,0,0,5000],[550000, 500000, 0, 0, 0, 0,0,50000],[176000, 160000, 0, 0, 0, 0,0,16000],[55000, 50000, 0, 0, 0,0,0,5000],\"\",\"IBOOK\",\"7\",\"15\",\"IDR\", 550000, 550000, 176000, 176000, 0, 0], [\"P/a\",[528000, 480000, 0, 0, 0, 0,0,48000],[154000, 140000, 0, 0, 0, 0,0,14000],[52800, 48000, 0, 0, 0,0,0,4800],[528000, 480000, 0, 0, 0, 0,0,48000],[154000, 140000, 0, 0, 0, 0,0,14000],[52800, 48000, 0, 0, 0,0,0,4800],\"\",\"IBOOK\",\"7\",\"15\",\"IDR\", 528000, 528000, 154000, 154000, 0, 0], [\"B/a\",[506000, 460000, 0, 0, 0, 0,0,46000],[132000, 120000, 0, 0, 0, 0,0,12000],[50600, 46000, 0, 0, 0,0,0,4600],[506000, 460000, 0, 0, 0, 0,0,46000],[132000, 120000, 0, 0, 0, 0,0,12000],[50600, 46000, 0, 0, 0,0,0,4600],\"\",\"IBOOK\",\"7\",\"15\",\"IDR\", 506000, 506000, 132000, 132000, 0, 0], [\"O/IWJR\",[65000, 50000, 10000, 0, 0, 0,0,5000],[65000, 50000, 10000, 0, 0, 0,0,5000],[15500, 5000, 10000, 0, 0,0,0,500],[65000, 50000, 10000, 0, 0, 0,0,5000],[65000, 50000, 10000, 0, 0, 0,0,5000],[15500, 5000, 10000, 0, 0,0,0,500],\"\",\"IBOOK\",\"\",\"\",\"IDR\", 65000, 65000, 65000, 65000, 0, 0], [\"O/a\",[484000, 440000, 0, 0, 0, 0,0,44000],[110000, 100000, 0, 0, 0, 0,0,10000],[48400, 44000, 0, 0, 0,0,0,4400],[484000, 440000, 0, 0, 0, 0,0,44000],[110000, 100000, 0, 0, 0, 0,0,10000],[48400, 44000, 0, 0, 0,0,0,4400],\"\",\"IBOOK\",\"7\",\"15\",\"IDR\", 484000, 484000, 110000, 110000, 0, 0]] }";
		System.err.println(data);	//TODO: please remove this on production

		ResponseGetFare resp = new ResponseGetFare();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(data);
		
		resp.setErrCode(rootNode.path("err_code").getTextValue());
		resp.setErrMsg(rootNode.path("err_msg").getTextValue());
		
		if (resp.isError()) return resp;
		

		resp.setOrg(rootNode.path("org").getTextValue());
		resp.setDes(rootNode.path("des").getTextValue());
		resp.setFlight_date(rootNode.path("flight_date").getTextValue());
		resp.setFlight_no(rootNode.path("flight_no").getTextValue());
		
		List<FareDetail> list = new ArrayList<FareDetail>();
		JsonNode fareInfos = rootNode.path("fare_info");
		for (int i = 0; i < fareInfos.size(); i++){
			JsonNode _items = fareInfos.get(i);
			
			FareDetail fareDtl = new FareDetail();
			
			String subClass = _items.get(0).getTextValue();
			fareDtl.setClassName(subClass);
			fareDtl.setFlightNo(resp.getFlight_no());

			if (subClass.indexOf("/") > -1){
				String _basisFare = subClass.split("/", 2)[1];
				fareDtl.setBaseFare(_basisFare);
				
				if (!SqivaHandler.knownBasisFare(_basisFare)){
					throw new RuntimeException("Unkown basis fare " + _basisFare + ". Please contact admin !");
				}
			}
			
			fareDtl.setAdultFare(readFare(_items.get(1)));
			fareDtl.setChildFare(readFare(_items.get(2)));
			fareDtl.setInfantFare(readFare(_items.get(3)));
			
			fareDtl.setAdultReturnFare(readFare(_items.get(4)));
			fareDtl.setChildReturnFare(readFare(_items.get(5)));
			fareDtl.setInfantReturnFare(readFare(_items.get(6)));

			fareDtl.setNote(_items.get(7).getTextValue());
			fareDtl.setIs_ibook(_items.get(8).getTextValue());
			fareDtl.setMin_stay(_items.get(9).getTextValue());
			fareDtl.setMax_stay(_items.get(10).getTextValue());
			fareDtl.setCcy(_items.get(11).getTextValue());
			
			fareDtl.setAgentAdultFare(_items.get(12).getIntValue());
			fareDtl.setAgentAdultReturnFare(_items.get(13).getIntValue());
			fareDtl.setAgentChildFare(_items.get(14).getIntValue());
			fareDtl.setAgentChildReturnFare(_items.get(15).getIntValue());

			list.add(fareDtl);
			
		}//for
		
		resp.setFare_info(list);
		
		if (returnDate == null) return resp;
			
		resp.setRet_org(rootNode.path("ret_org").getTextValue());
		resp.setRet_des(rootNode.path("ret_des").getTextValue());
		resp.setRet_flight_date(rootNode.path("ret_flight_date").getTextValue());
		resp.setRet_flight_no(rootNode.path("ret_flight_no").getTextValue());

		List<FareDetail> listRet = new ArrayList<FareDetail>();
		JsonNode retFareInfos = rootNode.path("ret_fare_info");
		for (int i = 0; i < retFareInfos.size(); i++){
			JsonNode _items = retFareInfos.get(i);
			
			FareDetail fareDtl = new FareDetail();
			fareDtl.setFlightNo(resp.getFlight_no());
			
			String subClass = _items.get(0).getTextValue();
			fareDtl.setClassName(subClass);
			
			fareDtl.setAdultFare(readFare(_items.get(1)));
			fareDtl.setChildFare(readFare(_items.get(2)));
			fareDtl.setInfantFare(readFare(_items.get(3)));
			
			fareDtl.setAdultReturnFare(readFare(_items.get(4)));
			fareDtl.setChildReturnFare(readFare(_items.get(5)));
			fareDtl.setInfantReturnFare(readFare(_items.get(6)));

			fareDtl.setNote(_items.get(7).getTextValue());
			fareDtl.setIs_ibook(_items.get(8).getTextValue());
			fareDtl.setMin_stay(_items.get(9).getTextValue());
			fareDtl.setMax_stay(_items.get(10).getTextValue());
			fareDtl.setCcy(_items.get(11).getTextValue());
			
			fareDtl.setAgentAdultFare(_items.get(12).getIntValue());
			fareDtl.setAgentAdultReturnFare(_items.get(13).getIntValue());
			fareDtl.setAgentChildFare(_items.get(14).getIntValue());
			fareDtl.setAgentChildReturnFare(_items.get(15).getIntValue());

			listRet.add(fareDtl);
		}//for

		resp.setRet_fare_info(listRet);
		
		return resp;
	}
}
