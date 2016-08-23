package com.big.web.b2b_big2.flight.api.kalstar.action.paytype;

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

public class HandlerGetPayType extends SqivaAction implements ISqivaAction{
	private RequestGetPayType request;
	
	public HandlerGetPayType(ConfigUrl configUrl, RequestGetPayType request){
		setConfigUrl(configUrl);
		this.request = request;
	}

	@Override
	public Object execute() throws Exception {
		request.validate();
		
		UriBuilder uri = buildPreUrl(Action.GET_PAY_TYPE)
//				.queryParam("book_code",  request.getBook_code())
				;

	String url = uri.build().toString();
	if (url == null) return null;

	String data = getFromServer(url);
//	String data = "{ \"err_code\": \"0\", \"book_code\": \"9UX7JJ\", \"book_balance\": 0,\"ticket_unit\":[[\"LIA \",\"321654 0000211758\"],[\"LIA PERKASA\",\"321654 0000211759\"],[\"JACK \",\"321654 0000211760\"],[\"JACKIE \",\"321654 0000211761\"]] }";
	//complete the data with database ?

	ResponseGetPayType resp = new ResponseGetPayType();
	
	ObjectMapper mapper = new ObjectMapper();
	JsonNode rootNode = mapper.readTree(data);
	
	resp.setErrCode(rootNode.path("err_code").getTextValue());
	resp.setErrMsg(rootNode.path("err_msg").getTextValue());

	if (resp.isError()) return resp;

	List<PayTypeDetail> payType = new ArrayList<>();
	Iterator<JsonNode> names = rootNode.path("pay_type").getElements(); 
	while (names.hasNext()) {
		JsonNode value = names.next();
		PayTypeDetail bean = new PayTypeDetail();
		bean.setPaymentType(value.get(0).getTextValue());
		bean.setBank(value.get(1).getTextValue());
		
		payType.add(bean);
	}
	resp.setPayType(payType);

	return resp;

	}
	
}
