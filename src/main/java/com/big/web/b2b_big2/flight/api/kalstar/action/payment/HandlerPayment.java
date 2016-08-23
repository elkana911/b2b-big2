package com.big.web.b2b_big2.flight.api.kalstar.action.payment;

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
import com.big.web.b2b_big2.util.Utils;

public class HandlerPayment extends SqivaAction implements ISqivaAction {
	private RequestPayment request;
	
	public HandlerPayment(ConfigUrl configUrl, RequestPayment request) {
		setConfigUrl(configUrl);
		this.request = request;
	}

	@Override
	public Object execute() throws Exception{
		
		request.validate();
		
		//ret_flight_no=213,214&dep_flight_no=600&dep_date=20130910&subclass_dep=A/A&subclass_ret=E/E,E/Y&caller=JONIE&caller_1=00898989&num_pax_adult=1&num_pax_child=0&num_pax_infant=1&a_first_name_1=IVHAN&a_last_name_1=FG&a_salutation_1=MR&a_birthdate_1=19901010&a_mobile_1=0812345&a_passport_1=pass1&a_nationality_1=ID&a_passport_exp_1=20120303&i_first_name_1=LOUIS&i_birthdate_
		UriBuilder uri = buildPreUrl(Action.PAYMENT)
					.queryParam("book_code",  request.getBook_code())

					;

		if (request.getAmount() > 0){
			uri.queryParam("amount", request.getAmount())
				;
		}
		
		if (!Utils.isEmpty(request.getPay_type()))
			uri.queryParam("pay_type", request.getPay_type());

		if (!Utils.isEmpty(request.getPay_bank()))
			uri.queryParam("pay_bank", request.getPay_bank());
		
		String url = uri.build().toString();
		if (url == null) return null;
		
//		System.out.println("targetUrl=" + url);
//		url=http://ws.demo.awan.sqiva.com/?rqid=5EB9FE68-8915-11E0-BEA0-C9892766ECF2&airline_code=W2&app=transaction&action=booking_v2&org=CGK&des=BDO&round_trip=0&dep_flight_no=300&dep_date=20150211&caller=Robin+Wheelchair&contact_1=02182645362&num_pax_adult=1&num_pax_child=0&num_pax_infant=0&subclass_dep=A/A&a_first_name_1=Roy&a_last_name_1=Suryo&a_salutation_1=MR&a_mobile_1=087882345210&a_nationality_1=ID
		String data = getFromServer(url);
//		String data = "{ \"err_code\": \"0\", \"book_code\": \"9UX7JJ\", \"book_balance\": 0,\"ticket_unit\":[[\"LIA \",\"321654 0000211758\"],[\"LIA PERKASA\",\"321654 0000211759\"],[\"JACK \",\"321654 0000211760\"],[\"JACKIE \",\"321654 0000211761\"]] }";
		//complete the data with database ?

		ResponsePayment resp = new ResponsePayment();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(data);
		
		resp.setErrCode(rootNode.path("err_code").getTextValue());
		resp.setErrMsg(rootNode.path("err_msg").getTextValue());

		if (resp.isError()) return resp;

		resp.setBook_code(rootNode.path("book_code").getTextValue());
		resp.setBook_balance(rootNode.path("book_balance").getIntValue());

		List<TicketUnit> ticketUnit = new ArrayList<>();
		Iterator<JsonNode> names = rootNode.path("ticket_unit").getElements(); 
		while (names.hasNext()) {
			JsonNode value = names.next();
			TicketUnit tu = new TicketUnit();
			tu.setPax_name(value.get(0).getTextValue());
			tu.setTicket_no(value.get(1).getTextValue());
			
			ticketUnit.add(tu);
		}
		resp.setTicket_unit(ticketUnit);

		return resp;
	}
}
