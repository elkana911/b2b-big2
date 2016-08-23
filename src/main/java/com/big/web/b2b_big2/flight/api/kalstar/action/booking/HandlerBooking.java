package com.big.web.b2b_big2.flight.api.kalstar.action.booking;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.big.web.b2b_big2.flight.api.kalstar.ConfigUrl;
import com.big.web.b2b_big2.flight.api.kalstar.action.Action;
import com.big.web.b2b_big2.flight.api.kalstar.action.ISqivaAction;
import com.big.web.b2b_big2.flight.api.kalstar.action.SqivaAction;
import com.big.web.b2b_big2.flight.pojo.PNR;
import com.big.web.b2b_big2.util.Utils;

public class HandlerBooking extends SqivaAction implements ISqivaAction {
	private RequestBooking request;
	
	public HandlerBooking(ConfigUrl configUrl, RequestBooking request) {
		setConfigUrl(configUrl);
		this.request = request;
	}

	@Override
	public Object execute() throws Exception{
		
		request.validate();
		
		//ret_flight_no=213,214&dep_flight_no=600&dep_date=20130910&subclass_dep=A/A&subclass_ret=E/E,E/Y&caller=JONIE&caller_1=00898989&num_pax_adult=1&num_pax_child=0&num_pax_infant=1&a_first_name_1=IVHAN&a_last_name_1=FG&a_salutation_1=MR&a_birthdate_1=19901010&a_mobile_1=0812345&a_passport_1=pass1&a_nationality_1=ID&a_passport_exp_1=20120303&i_first_name_1=LOUIS&i_birthdate_
		UriBuilder uri = buildPreUrl(Action.BOOKING_V2)
					.queryParam("org",  request.getOrg())
					.queryParam("des", request.getDes())
					.queryParam("round_trip", request.getRound_trip())
					.queryParam("dep_flight_no", request.getDep_flight_no_AsCSV())
					.queryParam("dep_date",Utils.Date2String(request.getDep_date(), "yyyyMMdd"))
					.queryParam("caller", request.getCustomer().getFullName())
					.queryParam("contact_1", request.getCustomer().getPhone1())
					.queryParam("num_pax_adult", request.getAdult().size())
					.queryParam("num_pax_child", request.getChild().size())
					.queryParam("num_pax_infant", request.getInfant().size())
					.queryParam("subclass_dep", request.getSubclass_dep_AsCSV())
				;
//		a_first_name_1=IVHAN&a_last_name_1=FG&a_salutation_1=MR&a_birthdate_1=19901010&a_mobile_1=0812345&
		//a_passport_1=pass1&a_nationality_1=ID&a_passport_exp_1=20120303
		for (int i = 1; i <= request.getAdult().size(); i++){
			PNR pnr = request.getAdult().get(i-1);
			uri.queryParam("a_first_name_" + i, pnr.getFirstMiddleName())
				.queryParam("a_last_name_" + i, pnr.getLastName())
				.queryParam("a_salutation_" + i, pnr.getTitle().name())
			;
			if (!Utils.isEmpty(pnr.getCountryCode())){
				uri.queryParam("a_nationality_" + i, pnr.getCountryCode());
			}
			if (pnr.getPhone() != null){
				uri.queryParam("a_mobile_" + i, pnr.getPhone().getNumbersOnly());
			}	
			if (pnr.getBirthday() != null){
				uri.queryParam("a_birthdate_" + i, Utils.Date2String(pnr.getBirthday(), "yyyyMMdd"));
			}
			
			if (!Utils.isEmpty(pnr.getPassportId())){
				uri.queryParam("a_passport_" + i, pnr.getPassportId())
				.queryParam("a_passport_exp_" + i, Utils.Date2String(pnr.getPassportExpired(), "yyyyMMdd"))
				;
			}

		}
		
		for (int i = 1; i <= request.getChild().size(); i++){
			PNR pnr = request.getChild().get(i-1);
			uri.queryParam("c_first_name_" + i, pnr.getFirstMiddleName())
				.queryParam("c_last_name_" + i, pnr.getLastName())
				.queryParam("c_salutation_" + i, pnr.getTitle().name())
			;

			if (!Utils.isEmpty(pnr.getCountryCode())){
				uri.queryParam("c_nationality_" + i, pnr.getCountryCode());
			}
			if (pnr.getPhone() != null){
				uri.queryParam("c_mobile_" + i, pnr.getPhone().getNumbersOnly());
			}	

			if (pnr.getBirthday() != null){
				uri.queryParam("c_birthdate_" + i, Utils.Date2String(pnr.getBirthday(), "yyyyMMdd"));
			}
			
			if (!Utils.isEmpty(pnr.getPassportId())){
				uri.queryParam("c_passport_" + i, pnr.getPassportId())
				.queryParam("c_passport_exp_" + i, Utils.Date2String(pnr.getPassportExpired(), "yyyyMMdd"))
				;
			}
		}	
		
		for (int i = 1; i <= request.getInfant().size(); i++){
			PNR pnr = request.getInfant().get(i-1);
			uri.queryParam("i_first_name_" + i, pnr.getFirstMiddleName())
				.queryParam("i_last_name_" + i, pnr.getLastName())
				.queryParam("i_salutation_" + i, pnr.getTitle().name())
			;

			uri.queryParam("i_parent_" + i, pnr.getParentId());
			if (!Utils.isEmpty(pnr.getCountryCode())){
				uri.queryParam("i_nationality_" + i, pnr.getCountryCode());
			}
			
			if (pnr.getPhone() != null){
				uri.queryParam("i_mobile_" + i, pnr.getPhone().getNumbersOnly());
			}	

			if (pnr.getBirthday() != null){
				uri.queryParam("i_birthdate_" + i, Utils.Date2String(pnr.getBirthday(), "yyyyMMdd"));
			}
			
			if (!Utils.isEmpty(pnr.getPassportId())){
				uri.queryParam("i_passport_" + i, pnr.getPassportId())
				.queryParam("i_passport_exp_" + i, Utils.Date2String(pnr.getPassportExpired(), "yyyyMMdd"))
				;
			}
		}
		
		if (request.getRound_trip() == 1){
			//soalnya di dokumen non mandatory
			uri.queryParam("return_flight", "1")
				.queryParam("ret_date",Utils.Date2String(request.getRet_date(), "yyyyMMdd"))
				.queryParam("ret_flight_no", request.getRet_flight_no_AsCSV())
				.queryParam("subclass_ret", request.getSubclass_ret_AsCSV())
				;
		}
		
		String url = uri.build().toString();
		if (url == null) return null;
		
		System.out.println("targetUrl=" + url);
//		url=http://ws.demo.awan.sqiva.com/?rqid=5EB9FE68-8915-11E0-BEA0-C9892766ECF2&airline_code=W2&app=transaction&action=booking_v2&org=CGK&des=BDO&round_trip=0&dep_flight_no=300&dep_date=20150211&caller=Robin+Wheelchair&contact_1=02182645362&num_pax_adult=1&num_pax_child=0&num_pax_infant=0&subclass_dep=A/A&a_first_name_1=Roy&a_last_name_1=Suryo&a_salutation_1=MR&a_mobile_1=087882345210&a_nationality_1=ID
		String data = getFromServer(url);
		//complete the data with database ?
//		String data = "{ \"err_code\": \"001002\", \"err_msg\": \"Validation error: [ dep_date ] have invalid date format !\" }";
//		String data = "{ \"err_code\": \"0\", \"org\": \"CGK\", \"des\": \"BDO\", \"round_trip\": 0, \"book_code\": \"AVURC7\", \"dep_date\": \"20150211\", \"dep_flight_no\": \"300\", \"ret_date\": \"\", \"ret_flight_no\": \"\", \"pax_num\": [1,0,0], \"pax_name\": [\"ROY SURYO\"] , \"normal_sales\": 350000, \"book_balance\": 350000,\"pay_limit\":\"11-FEB-2015 13:30 (UTC+9)\",\"status\":\"HK\",\"ret_status\":\"\" }";
//		{ "err_code": "0", "org": "CGK", "des": "BDO", "round_trip": 0, "book_code": "AVURC7", "dep_date": "20150211", "dep_flight_no": "300", "ret_date": "", "ret_flight_no": "", "pax_num": [1,0,0], "pax_name": ["ROY SURYO"] , "normal_sales": 350000, "book_balance": 350000,"pay_limit":"11-FEB-2015 13:30 (UTC+9)","status":"HK","ret_status":"" }
//		{ "err_code": "001002", "err_msg": "Validation error: [ i_parent_1 ] should not blank" }
//		String data = "{ \"err_code\": \"0\", \"org\": \"CGK\", \"des\": \"UPG\", \"round_trip\": 0, \"book_code\": \"9UX7JJ\", \"dep_date\": \"20150218\", \"dep_flight_no\": \"100,200\", \"ret_date\": \"\", \"ret_flight_no\": \"\", \"pax_num\": [2,0,2], \"pax_name\": [\"LIA \",\"LIA PERKASA\",\"JACK \",\"JACKIE \"] , \"normal_sales\": 1544000, \"book_balance\": 1544000,\"pay_limit\":\"18-FEB-2015 11:00 (UTC+9)\",\"status\":\"HK\",\"status_2\":\"HK\",\"ret_status\":\"\" }";
		ResponseBooking resp = new ResponseBooking();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(data);
		
		resp.setErrCode(rootNode.path("err_code").getTextValue());
		resp.setErrMsg(rootNode.path("err_msg").getTextValue());

		if (resp.isError()) return resp;

		resp.setOrg(rootNode.path("org").getTextValue());
		resp.setDes(rootNode.path("des").getTextValue());
		resp.setRound_trip(rootNode.path("round_trip").getIntValue());
		resp.setBook_code(rootNode.path("book_code").getTextValue());
		resp.setDep_date(rootNode.path("dep_date").getTextValue());
		resp.setDep_flight_no(rootNode.path("dep_flight_no").getTextValue());
		resp.setRet_date(rootNode.path("ret_date").getTextValue());
		resp.setRet_flight_no(rootNode.path("ret_flight_no").getTextValue());
		resp.setNormal_sales(rootNode.path("normal_sales").getIntValue());
		resp.setBook_balance(rootNode.path("book_balance").getIntValue());
		resp.setPay_limit(rootNode.path("pay_limit").getTextValue());
		resp.setStatus(rootNode.path("status").getTextValue());
		resp.setStatus_2(rootNode.path("status_2").getTextValue());
		resp.setStatus_3(rootNode.path("status_3").getTextValue());
		resp.setRet_status(rootNode.path("ret_status").getTextValue());
		resp.setRet_status_2(rootNode.path("ret_status_2").getTextValue());
		resp.setRet_status_3(rootNode.path("ret_status_3").getTextValue());
		
		resp.setPax_adult(rootNode.path("pax_num").get(0).getIntValue());
		resp.setPax_child(rootNode.path("pax_num").get(1).getIntValue());
		resp.setPax_infant(rootNode.path("pax_num").get(2).getIntValue());

		List<String> nameList = new ArrayList<>();
		Iterator<JsonNode> names = rootNode.path("pax_name").getElements(); 
		while (names.hasNext()) {
			JsonNode value = names.next();
			nameList.add(value.getTextValue());
		}
		resp.setPaxNames(nameList);

		return resp;
	}
}
