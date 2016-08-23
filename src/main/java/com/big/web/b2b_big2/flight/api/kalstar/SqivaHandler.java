package com.big.web.b2b_big2.flight.api.kalstar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.big.web.b2b_big2.finance.cart.pojo.FlightCart;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.api.kalstar.action.HandlerGetOrgDes;
import com.big.web.b2b_big2.flight.api.kalstar.action.ISqivaAction;
import com.big.web.b2b_big2.flight.api.kalstar.action.balance.HandlerGetBalance;
import com.big.web.b2b_big2.flight.api.kalstar.action.balance.RequestGetBalance;
import com.big.web.b2b_big2.flight.api.kalstar.action.balance.ResponseGetBalance;
import com.big.web.b2b_big2.flight.api.kalstar.action.booking.HandlerBooking;
import com.big.web.b2b_big2.flight.api.kalstar.action.booking.RequestBooking;
import com.big.web.b2b_big2.flight.api.kalstar.action.booking.ResponseBooking;
import com.big.web.b2b_big2.flight.api.kalstar.action.cancel.HandlerCancel;
import com.big.web.b2b_big2.flight.api.kalstar.action.cancel.RequestCancel;
import com.big.web.b2b_big2.flight.api.kalstar.action.cancel.ResponseCancel;
import com.big.web.b2b_big2.flight.api.kalstar.action.fare.FareDetail;
import com.big.web.b2b_big2.flight.api.kalstar.action.fare.HandlerGetFares;
import com.big.web.b2b_big2.flight.api.kalstar.action.fare.ResponseGetFare;
import com.big.web.b2b_big2.flight.api.kalstar.action.payment.HandlerPayment;
import com.big.web.b2b_big2.flight.api.kalstar.action.payment.RequestPayment;
import com.big.web.b2b_big2.flight.api.kalstar.action.payment.ResponsePayment;
import com.big.web.b2b_big2.flight.api.kalstar.action.paytype.HandlerGetPayType;
import com.big.web.b2b_big2.flight.api.kalstar.action.paytype.RequestGetPayType;
import com.big.web.b2b_big2.flight.api.kalstar.action.paytype.ResponseGetPayType;
import com.big.web.b2b_big2.flight.api.kalstar.action.schedule.HandlerGetSchedule;
import com.big.web.b2b_big2.flight.api.kalstar.action.sendticket.HandlerSendTicket;
import com.big.web.b2b_big2.flight.api.kalstar.action.sendticket.RequestSendTicket;
import com.big.web.b2b_big2.flight.api.kalstar.action.sendticket.ResponseSendTicket;
import com.big.web.b2b_big2.flight.api.kalstar.exception.SqivaServerException;
import com.big.web.b2b_big2.flight.pojo.BasicContact.SpecialRequest;
import com.big.web.b2b_big2.flight.pojo.ContactCustomer;
import com.big.web.b2b_big2.flight.pojo.FlightSearchB2B;
import com.big.web.b2b_big2.flight.pojo.PERSON_TYPE;
import com.big.web.b2b_big2.flight.pojo.PNR;
import com.big.web.b2b_big2.flight.pojo.SearchForm;
import com.big.web.b2b_big2.flight.pojo.Title;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.Phone;
import com.big.web.b2b_big2.util.StopWatch;
import com.big.web.b2b_big2.util.Utils;

/*
 * menurutku ga cocok utk b2b ini memakai ikalstaraction krn hanya cocok utk background proses dengan method execute-nya
 * jadi HandlerGetSchedule misalnya bisa dipanggil tanpa harus execute. 
 */
public class SqivaHandler {
//	Y/G/H/K/L/M/N/Q/R/T/V/X/A/P/B/C/D/O/
	public static final char[] class_business = {'c', 'j', 'd', 'i', 'z'};
	public static final char[] class_economy = {'y', 'b', 'm', 'h', 'n', 'g', 'k', 'l', 'o', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x'};
	public static final char[] class_first = {'f', 'a', 'p', 'r'};

	private static final String[] basis_fares = {"a", "b", "c", "d", "e", "f","g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "iwjr", "alet", "rt"};

	private static final String[] basis_class = {"o", "d", "c", "b", "p", "a", "x", "v", "t", "r", "q", "n", "m", "l", "k", "h", "g", "y"};
												// cheaper ----> expensive 

	public static String KEY_API = "kalstar.apikey";
	public static String KEY_HOST = "kalstar.host";
	public static String KEY_PROTOCOL = "kalstar.protocol";
	public static String KEY_CODE = "kalstar.code";
	
	public ConfigUrl configUrl;

    private static final Log log = LogFactory.getLog(SqivaHandler.class);
    
    public SqivaHandler(Airline airline, ISetupDao setup) {
		KEY_API = airline.name().toLowerCase() + ".apikey";
		KEY_HOST = airline.name().toLowerCase() + ".host";
		KEY_PROTOCOL = airline.name().toLowerCase() + ".protocol";
		KEY_CODE = airline.name().toLowerCase() + ".code";
		
		configUrl = new ConfigUrl();
		configUrl.setProtocol(Utils.nvl(setup.getValue(KEY_PROTOCOL), "http"));
		configUrl.setHost(Utils.nvl(setup.getValue(KEY_HOST), "ws.demo.awan.sqiva.com"));
		configUrl.setApiKey(Utils.nvl(setup.getValue(KEY_API), "5EB9FE68-8915-11E0-BEA0-C9892766ECF2"));
		configUrl.setAirlineCode(Utils.nvl(setup.getValue(KEY_CODE), "KD"));

	}

	public static boolean knownBasisFare(String value){
		for (String s : basis_fares){
			if (s.equalsIgnoreCase(value))
				return true;
		}
		
		return false;
	}

    /**
     * Get active routes available from kalstar.
     * <p>Purpose: synchronization or combo list for depart/destination
     * @return List&lt;Route&gt;
     */
	public Object getActiveRoutes(){
		
		StopWatch sw = StopWatch.AutoStart();
		ISqivaAction kalstarAct = HandlerGetOrgDes.getInstance();
		
		try {
			return kalstarAct.execute();
		} catch (Exception e) {
			log.error(e.getMessage());
		}finally{
			log.debug("getActiveRoutes took " + sw.getElapsedTimeInString());
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param form
	 * @return
	 */
	public Object getSchedules(SearchForm form){
		
		StopWatch sw = StopWatch.AutoStart();
		HandlerGetSchedule kalstarAct = new HandlerGetSchedule(configUrl, form);
		
		try {
			return kalstarAct.execute();
		} catch (Exception e) {
			log.error(e.getMessage());
		}finally{
			log.debug("getSchedules took " + sw.stopAndGetAsString());
		}

		return null;
	}

	public List<FareDetail> getFareDetails(String originIata, String destIata, Date departDate, String flightNo) throws Exception {
		StopWatch sw = StopWatch.AutoStart();
		ISqivaAction kalstarAct = new HandlerGetFares(this.configUrl, originIata, destIata, departDate, flightNo);
		
		try {
			ResponseGetFare resp = (ResponseGetFare) kalstarAct.execute();
			
			if (resp.isError()) throw new SqivaServerException(resp.getErrCode(), resp.getErrMsg());
				
			return resp.getFare_info();
			
		}catch (SqivaServerException e){
			log.error("(" + e.getErrorCode() + ") " + e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}finally{
			log.debug("getFare took " + sw.stopAndGetAsString());
		}
		
//		return null;
	}


	public ResponseCancel cancel(String bookCode)  throws Exception{
		
		RequestCancel req = new RequestCancel();
		req.setBook_code(bookCode);
		
		ISqivaAction kalstarAct = new HandlerCancel(configUrl, req);
		
		return (ResponseCancel) kalstarAct.execute();
	}
	
	public ResponseBooking booking(RequestBooking req) throws Exception{
		
		ISqivaAction kalstarAct = new HandlerBooking(configUrl, req);
		
		return (ResponseBooking) kalstarAct.execute();
	}
	

	public ResponsePayment payment(String bookCode) throws Exception {
		RequestPayment req = new RequestPayment();
		
		req.setBook_code(bookCode);
//		req.setAmount(amount);
//		req.setPay_bank(pay_bank);
//		req.setPay_type(pay_type);
		
		ISqivaAction kalstarAct = new HandlerPayment(configUrl, req);
		
		return (ResponsePayment) kalstarAct.execute();
	}

	private void testSchedules(){
		SearchForm form = new SearchForm();
		form.setDepartCity("CGK");
		form.setDestCity("SIN");
		form.setDepartDate("12/02/2015");
		
		List<FlightSearchB2B> scheds = (List<FlightSearchB2B>) getSchedules(form);
		
		System.err.println("Schedules return " + scheds.size());		
		for (FlightSearchB2B flightSearchB2B : scheds) {
			System.err.println(flightSearchB2B);
		}
	}
	
	private void testFareDetails() throws Exception{
		String flightNo = "Q1-300";
		String originIata = "CGK";
		String destIata = "BDO";
		Date depDate = Utils.String2Date("20150209", "yyyyMMdd");
		
		List<FareDetail> fare = (List<FareDetail>) getFareDetails(originIata, destIata, depDate, flightNo);

		System.out.println(fare);
		
	}
	
	private void testBookingDepart(){
		RequestBooking req = new RequestBooking();
		
		
		PNR pnr = new PNR();
		pnr.setPersonType(PERSON_TYPE.ADULT);
		pnr.setFullName("Roy");
		pnr.setId("123456331234234");
		//pnr.setPhone(new Phone("087882345210"));
		pnr.setSpecialRequest(SpecialRequest.WHEELCHAIR);
		pnr.setTitle(Title.MR);
		pnr.setCountryCode("ID");
		pnr.setId("3333311111");
		
		List<PNR> adults = new ArrayList<PNR>();
		List<PNR> children = new ArrayList<PNR>();
		List<PNR> infants = new ArrayList<PNR>();
		adults.add(pnr);
		
		ContactCustomer cust = new ContactCustomer();
		cust.setFullName("Robin Wheelchair");
		cust.setPhone1("02182645362");
		
		req.setAdult(adults);
		req.setChild(children);
		req.setInfant(infants);
		req.setCustomer(cust);
		
		//gw heran kenapa ga ada depart time. karena cukup flightno, org dan des
		req.setDep_date(Utils.String2Date("17/02/2015", "dd/MM/yyyy"));

		String[] depFlightNo = {"301"};
		req.setDep_flight_no(depFlightNo);
		
		req.setOrg("BDO");
		req.setDes("CGK");
		req.setRound_trip(0);

		/*
		paling repot kudu cocokin di db. cara tercepat:
	select a.flightnum, a.updatecode, a.origin, a.destination, a.departure, a.arrival, a.dep_time, a.istransit,a.route, b.class_name, b.fare_base, b.class_avail_seat 
	from flightavailseat_clean a inner join flight_class_fare_kalstar b on a.flightavailid = b.flightavailid
	where to_char(a.dep_time, 'DD/MM/RRRR') = :deptime
	order by a.created_date, b.class_name
	*/
		
		String[] subClassDep = {"A/A"};
		req.setSubclass_dep(subClassDep);	
//		req.setSubclass_dep("A/a");	//No fare base a for this flight departure schedule or it's hidden
//		req.setSubclass_dep("A");//Wrong subclass_dep input format
		
		ResponseBooking booking;
		try {
			booking = (ResponseBooking)booking(req);
			System.out.println(booking);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void testCancelBooking(){

		String bookCode = "UUU123";
		
		ResponseCancel booking;
		try {
			booking = (ResponseCancel)cancel(bookCode);
			System.out.println(booking);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void testBookingDepartReturn(){
		RequestBooking req = new RequestBooking();
		
		PNR pnr = new PNR();
		pnr.setPersonType(PERSON_TYPE.ADULT);
		pnr.setFullName("Roy SuryoX");	//ga boleh ada angka
		pnr.setId("123456331234234");
		pnr.setPhone(new Phone("087882345211"));
		pnr.setSpecialRequest(SpecialRequest.WHEELCHAIR);
		pnr.setTitle(Title.MR);
		pnr.setCountryCode("ID");
		pnr.setId("3333311111");
		
		List<PNR> adults = new ArrayList<PNR>();
		List<PNR> children = new ArrayList<PNR>();
		List<PNR> infants = new ArrayList<PNR>();
		adults.add(pnr);
		
		ContactCustomer cust = new ContactCustomer();
		cust.setFullName("Robin WheelchairX");
		cust.setPhone1("02182645362");
		
		req.setAdult(adults);
		req.setChild(children);
		req.setInfant(infants);
		req.setCustomer(cust);
		
		req.setDep_date(Utils.String2Date("14/02/2015", "dd/MM/yyyy"));
		
		String[] depFlightNo = {"100", "200"};
		req.setDep_flight_no(depFlightNo);
		req.setOrg("CGK");
		req.setDes("UPG");
		
		/*
		 * biasa development utk connecting flight bolakbalik bisa ambil sampel CGK-UPG. tp ga tiap hari ada.
			paling repot kudu cocokin di db. cara tercepat:
		select a.flightnum, a.updatecode, a.origin, a.destination, a.departure, a.arrival, a.dep_time, a.istransit,a.route, b.class_name, b.fare_base, b.class_avail_seat 
		from flightavailseat_clean a inner join flight_class_fare_kalstar b on a.flightavailid = b.flightavailid
		where to_char(a.dep_time, 'DD/MM/RRRR') = :deptime
		order by a.created_date, b.class_name
		*/
		String[] subClassDep = {"A/A", "A/A"};
		req.setSubclass_dep(subClassDep);	//di lokal availseat 10
//		req.setSubclass_dep("A/a");//Wrong subclass_dep input format
//		req.setSubclass_dep("A");//Validation error: Wrong subclass_dep input format
		
		req.setRound_trip(1);
		req.setRet_date(Utils.String2Date("14/02/2015", "dd/MM/yyyy"));
		String[] retFlightNo = {"201", "101"};
		req.setRet_flight_no(retFlightNo);
		
		String[] subClassRet = {"A/A", "A/A"};
		req.setSubclass_ret(subClassRet);	//di lokal availseat 10
		
		ResponseBooking booking;
		try {
			booking = (ResponseBooking)booking(req);
			System.out.println(booking);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
//		ResponseBooking [errCode=001002, errMsg=Validation error: [ ret_date ] have invalid date format !, org=null, des=null, round_trip=0, book_code=null, dep_date=null, dep_flight_no=null, ret_date=null, ret_flight_no=null, normal_sales=0, book_balance=0, pay_limit=null, status=null, status_2=null, status_3=null, ret_status=null, ret_status_2=null, ret_status_3=null, pax_adult=0, pax_child=0, pax_infant=0, paxNames=null]
//		ResponseBooking [errCode=001002, errMsg=Validation error: [ a_last_name_1 ] should contain only char and space, org=null, des=null, round_trip=0, book_code=null, dep_date=null, dep_flight_no=null, ret_date=null, ret_flight_no=null, normal_sales=0, book_balance=0, pay_limit=null, status=null, status_2=null, status_3=null, ret_status=null, ret_status_2=null, ret_status_3=null, pax_adult=0, pax_child=0, pax_infant=0, paxNames=null]
//		ResponseBooking [errCode=0, errMsg=null, org=CGK, des=UPG, round_trip=1, book_code=5W7P47, dep_date=20150214, dep_flight_no=100,200, ret_date=20150214, ret_flight_no=201,101, normal_sales=2280000, book_balance=2280000, pay_limit=13-FEB-2015 22:00 (UTC+9), status=HK, status_2=HK, status_3=null, ret_status=HK, ret_status_2=HK, ret_status_3=null, pax_adult=1, pax_child=0, pax_infant=0, paxNames=[ROY SURYOX]]
	}
	
	public ResponseBooking booking(FlightCart cart) throws Exception{

		RequestBooking req = new RequestBooking();
		
		req.setAdult(cart.getPnr(PERSON_TYPE.ADULT));
		req.setChild(cart.getPnr(PERSON_TYPE.CHILD));
		req.setInfant(cart.getPnr(PERSON_TYPE.INFANT));
		req.setCustomer(cart.getCustomer());
		
		req.setDep_date(cart.getDepartDate());
		req.setDep_flight_no(cart.getDepartFlightItinerary().getFlightNumbers());
		
		req.setOrg(cart.getOriginIata());
		req.setDes(cart.getDestinationIata());
		
		req.setRet_date(cart.getReturnDate());
		req.setRet_flight_no(cart.getReturnFlightItinerary().getFlightNumbers());
		req.setRound_trip(cart.getTripMode());
		req.setSubclass_dep(cart.getDepartFlightItinerary().getFlightClass());
		req.setSubclass_ret(cart.getReturnFlightItinerary().getFlightClass());
		//RequestBooking [org=BDO, des=PLM, round_trip=0, dep_flight_no=[300], dep_date=Thu Apr 02 12:45:36 GMT+07:00 2015, subclass_dep=[C/C], customer=com.big.web.b2b_big2.flight.pojo.ContactCustomer@232131c8, adult=[BasicContact [id=null, idCard=444422233, title=MRS, fullName=LIA PERKASA, birthday=null, specialRequest=null, phone=null, countryCode=null]], child=[], infant=[], ret_date=Thu Apr 02 12:45:36 GMT+07:00 2015, ret_flight_no=[], subclass_ret=[]]
		return booking(req);
//		ResponseBooking kalstarResp = (ResponseBooking) booking(req);
		
//		if (kalstarResp.isError()) throw new KalstarServerException(kalstarResp.getErrCode(), kalstarResp.getErrMsg());
		
//		respBooking.setCode(kalstarResp.getBook_code());

//		pay_limit=13-FEB-2015 22:00 (UTC+9)

//		respBooking.setTimeToPay(new Date(kalstarResp.getPay_limit()));
		
//		return respBooking;
	}
	
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		SqivaHandler h = new SqivaHandler(Airline.KALSTAR, null);
		
		try {
//			h.testJson();
/*
			List<Route> list = (List<Route>) h.getActiveRoutes();
			for (Route route : list) {
				System.out.println(route.toString());
			}
*/

//			h.testSchedules();
//			h.testFareDetails();
//			h.testBookingDepart();
//			h.testBookingDepartReturn();
			h.testCancelBooking();
			/*
			String originIata = "CGK";
			String destIata = "BDO";
			Date departDate = Utils.String2Date("23/02/2015", "dd/MM/yyyy");
			String flightNo = "1015";
			h.getFareDetails(originIata, destIata, departDate, flightNo);
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.exit(0);
	}

	public ResponseGetBalance getBalance(String bookCode) throws Exception {
		RequestGetBalance req = new RequestGetBalance();
		req.setBook_code(bookCode);
		
		ISqivaAction kalstarAct = new HandlerGetBalance(configUrl, req);
		
		return (ResponseGetBalance) kalstarAct.execute();
	}

	public ResponseSendTicket sendTicket(String bookCode, String email) throws Exception{
		RequestSendTicket req = new RequestSendTicket();
		req.setBook_code(bookCode);
		req.setEmail(email);
		
		ISqivaAction kalstarAct = new HandlerSendTicket(configUrl, req);
		
		return (ResponseSendTicket) kalstarAct.execute();
	}
	
	public ResponseGetPayType getPayType() throws Exception{
		RequestGetPayType req = new RequestGetPayType();
		
		ISqivaAction kalstarAct = new HandlerGetPayType(configUrl, req);
		
		return (ResponseGetPayType) kalstarAct.execute();
		
	}


	/*
	public Object updateSchedule(String originIata, String destIata, Date departDate, String fightNo) {
		ErrorKalstar error;
		
		SearchForm form = new SearchForm();
		form.setDepartCity(originIata);
		form.setDestCity(destIata);
		form.setDepartDate(departDate);
		
		HandlerGetSchedule handler = HandlerGetSchedule.getInstance(form);
		//kudunya ada flight no waktu user click radio
		FareDetail fare = handler.getFareDetail();

		//how to update database ?
		flightManager.update(fcf);
		flightManager.update(fas);
		
		System.out.println("Successfully update " + fare);
		
		return Integer.valueOf(error.getErrCode());
	}
	*/
}
