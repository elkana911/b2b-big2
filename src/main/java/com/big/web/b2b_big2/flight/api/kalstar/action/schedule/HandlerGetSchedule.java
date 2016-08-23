package com.big.web.b2b_big2.flight.api.kalstar.action.schedule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.big.web.b2b_big2.flight.api.kalstar.ConfigUrl;
import com.big.web.b2b_big2.flight.api.kalstar.action.Action;
import com.big.web.b2b_big2.flight.api.kalstar.action.ISqivaAction;
import com.big.web.b2b_big2.flight.api.kalstar.action.SqivaAction;
import com.big.web.b2b_big2.flight.api.kalstar.exception.SqivaException;
import com.big.web.b2b_big2.flight.api.kalstar.exception.SqivaServerException;
import com.big.web.b2b_big2.flight.pojo.FlightSearchB2B;
import com.big.web.b2b_big2.flight.pojo.SearchForm;
import com.big.web.b2b_big2.flight.pojo.Seat;
import com.big.web.b2b_big2.util.Utils;

public class HandlerGetSchedule extends SqivaAction implements ISqivaAction {
	private SearchForm form;
	
	public HandlerGetSchedule(ConfigUrl configUrl, SearchForm form) {
		setConfigUrl(configUrl);
		this.form = form;
	}

	@Override
	public Object execute() throws Exception {
		if (form == null) 
			throw new SqivaException("SearchForm not defined !");
		
		String originIata = form.getDepartIata().toUpperCase();
		String destIata = form.getDestinationIata().toUpperCase();
		String departDate = Utils.changeDatePattern(form.getDepartDate(), "dd/MM/yyyy", "yyyyMMdd");
		String returnDate = form.getTripMode() == 1 ? Utils.changeDatePattern(form.getReturnDate(), "dd/MM/yyyy", "yyyyMMdd") : null;

		UriBuilder uri = buildPreUrl(Action.GET_SCHEDULE_V2)
					.queryParam("org", originIata)
					.queryParam("des", destIata)
					.queryParam("flight_date", departDate)
				;
		if (form.getTripMode() == 1){
			//soalnya di dokumen non mandatory
			uri.queryParam("return_flight", "1")
				.queryParam("ret_flight_date", returnDate);
		}
		
		String url = uri.build().toString();
		if (url == null) return null;

		System.out.println("targetUrl=" + url);
		
		String data = getFromServer(url);
//		String data = "{ \"err_code\": \"0\", \"org\": \"CGK\", \"des\": \"NRT\", \"flight_date\": \"20150204\", \"extra_days\":0 ,\"schedule\": [[],[]],\"ret_flight_date\":\"\",\"ret_schedule\":[[],[]] }";
//		String data = "{ \"err_code\": \"0\", \"org\": \"CGK\", \"des\": \"SIN\", \"flight_date\": \"20150129\", \"extra_days\":0 ,\"schedule\": [[[\"Q1-1015\",\"CGK\",\"SIN\",\"20150129\",\"20150129\",\"2005\",\"2300\",\"01h55m\",\"737-200\",\"0\",[[\"Y\",\"135\"],[\"G\",\"119\"],[\"H\",\"104\"],[\"K\",\"90\"],[\"L\",\"77\"],[\"M\",\"65\"],[\"N\",\"54\"],[\"Q\",\"44\"],[\"R\",\"35\"],[\"T\",\"27\"],[\"V\",\"20\"],[\"X\",\"14\"],[\"A\",\"9\"],[\"P\",\"5\"],[\"B\",\"2\"],[\"O\",\"0\"]],\"CGK-SIN\"]],[]],\"ret_flight_date\":\"\",\"ret_schedule\":[[],[]] }";
		//complete the data with database ?
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(data);
		
		JsonNode errCodeNode = rootNode.path("err_code");
		String errStr = errCodeNode.getTextValue();
		int errInt = errCodeNode.getIntValue();
		if (errStr.length() > 0 && !errStr.equals("0")){
			String errMsg = rootNode.path("err_msg").getTextValue();
			throw new SqivaServerException("error Code:" + errStr + ", " + errMsg);
		}
		
		JsonNode extraDaysNode = rootNode.path("extra_days");	//ignored for the moment
		
		List<FlightSearchB2B> list = new ArrayList<FlightSearchB2B>();
		
		JsonNode scheduleNode = rootNode.path("schedule");
		JsonNode sch_direct = scheduleNode.get(0);
		JsonNode sch_conn = scheduleNode.get(1);
		for (int i = 0; i < sch_direct.size(); i++){
			JsonNode _items = sch_direct.get(i);
			
			FlightSearchB2B search = new FlightSearchB2B();
			search.setOriginIata(originIata);
			search.setDestinationIata(destIata);
			
			String flightNo = _items.get(0).getTextValue();
			String org = _items.get(1).getTextValue();
			String dest = _items.get(2).getTextValue();
			String depDate = _items.get(3).getTextValue();	//20150129
			String arrDate = _items.get(4).getTextValue();	//20150129
			String depTime = _items.get(5).getTextValue();	//2005
			String arrTime = _items.get(6).getTextValue();	//2300
			String duration = _items.get(7).getTextValue();
			String aircraft = _items.get(8).getTextValue();
			String transit = _items.get(9).getTextValue();

			Set<Seat> seats = new LinkedHashSet<Seat>();
			
			Iterator<JsonNode> avails = _items.get(10).getElements();
			while (avails.hasNext()){
				JsonNode avail = avails.next();	//["Y","135"]
				
				String seatClass = avail.get(0).getTextValue();
				String availSeat = avail.get(1).getTextValue();
				
				Seat seat = new Seat(0, seatClass, Integer.parseInt(availSeat));
				seats.add(seat);
				
			}
			
			String route = _items.get(11).getTextValue();
			
			search.setFlightNumber(flightNo);
			search.setDepartureIata(org);
			search.setArrivalIata(dest);
			
			search.setDepartTime(Utils.String2Date(depDate + depTime, "yyyyMMddHHmm"));
			search.setArrivalTime(Utils.String2Date(arrDate + arrTime, "yyyyMMddHHmm"));
//			search.setIsTransit(transit);
			search.setSeats(seats.toArray(new Seat[seats.size()]));
			
			list.add(search);
		}//for

		//mungkin ga perlu krn b2b carinya selalu by depart
		/*
		JsonNode retScheduleNode = rootNode.path("ret_schedule");
		JsonNode ret_direct = retScheduleNode.get(0);
		JsonNode ret_conn = retScheduleNode.get(1);
		for (int i = 0; i < ret_direct.size(); i++){
			JsonNode _items = ret_direct.get(i);
			
			FlightSearchB2B search = new FlightSearchB2B();
			search.setOriginIata(originIata);
			search.setDestinationIata(destIata);
			
			String flightNo = _items.get(0).getTextValue();
			String org = _items.get(1).getTextValue();
			String dest = _items.get(2).getTextValue();
			String depDate = _items.get(3).getTextValue();	//20150129
			String arrDate = _items.get(4).getTextValue();	//20150129
			String depTime = _items.get(5).getTextValue();	//2005
			String arrTime = _items.get(6).getTextValue();	//2300
			String duration = _items.get(7).getTextValue();
			String aircraft = _items.get(8).getTextValue();
			String transit = _items.get(9).getTextValue();

			Set<Seat> seats = new LinkedHashSet<Seat>();
			
			Iterator<JsonNode> avails = _items.get(10).getElements();
			while (avails.hasNext()){
				JsonNode avail = avails.next();	//["Y","135"]
				
				String seatClass = avail.get(0).getTextValue();
				String availSeat = avail.get(1).getTextValue();
				
				Seat seat = new Seat(0, seatClass.charAt(0), Integer.parseInt(availSeat));
				seats.add(seat);
				
			}
			
			String route = _items.get(11).getTextValue();
			
			search.setFlightNumber(flightNo);
			search.setDepartureIata(org);
			search.setArrivalIata(dest);
			
			search.setDepartTime(Utils.String2Date(depDate + depTime, "yyyyMMddHHmm"));
			search.setArrivalTime(Utils.String2Date(arrDate + arrTime, "yyyyMMddHHmm"));
//			search.setIsTransit(transit);
			search.setSeats(seats.toArray(new Seat[seats.size()]));
			
			list.add(search);
		}//for

		for (int i = 0; i < ret_conn.size(); i++){
			JsonNode _items = ret_conn.get(i);
			
			FlightSearchB2B search = new FlightSearchB2B();
			
//			list.add(search);			
			
		}
	*/
		return list;
	}
}
