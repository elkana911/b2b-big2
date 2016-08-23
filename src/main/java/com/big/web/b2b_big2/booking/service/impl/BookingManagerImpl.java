package com.big.web.b2b_big2.booking.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.big.web.b2b_big2.agent.dao.IAgentDao;
import com.big.web.b2b_big2.agent.pojo.Agent;
import com.big.web.b2b_big2.booking.BOOK_STATUS;
import com.big.web.b2b_big2.booking.dao.IBookingFlightDao;
import com.big.web.b2b_big2.booking.exception.BookStatusException;
import com.big.web.b2b_big2.booking.exception.BookingException;
import com.big.web.b2b_big2.booking.exception.IssuedException;
import com.big.web.b2b_big2.booking.model.BookingFlightDtlVO;
import com.big.web.b2b_big2.booking.model.BookingFlightSchedVO;
import com.big.web.b2b_big2.booking.model.BookingFlightTransVO;
import com.big.web.b2b_big2.booking.model.BookingFlightVO;
import com.big.web.b2b_big2.booking.service.IBookingAsyncService;
import com.big.web.b2b_big2.booking.service.IBookingManager;
import com.big.web.b2b_big2.customer.dao.ICustomerDao;
import com.big.web.b2b_big2.finance.cart.pojo.FlightItinerary;
import com.big.web.b2b_big2.finance.dao.IFinanceDao;
import com.big.web.b2b_big2.finance.exception.CartEmptyException;
import com.big.web.b2b_big2.finance.model.CommissionVO;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.IAirlineHandler;
import com.big.web.b2b_big2.flight.api.kalstar.SqivaHandler;
import com.big.web.b2b_big2.flight.api.kalstar.Sqiva_Handler;
import com.big.web.b2b_big2.flight.api.kalstar.action.booking.ResponseBooking;
import com.big.web.b2b_big2.flight.api.kalstar.action.sendticket.ResponseSendTicket;
import com.big.web.b2b_big2.flight.api.kalstar.exception.SqivaServerException;
import com.big.web.b2b_big2.flight.dao.IAirlineDao;
import com.big.web.b2b_big2.flight.dao.IFlightDao;
import com.big.web.b2b_big2.flight.model.FlightAvailSeatVO;
import com.big.web.b2b_big2.flight.odr.ODRHandler;
import com.big.web.b2b_big2.flight.pojo.BookingFormB2B;
import com.big.web.b2b_big2.flight.pojo.ContactCustomer;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.flight.pojo.FlightSearchB2B;
import com.big.web.b2b_big2.flight.pojo.FlightSelect;
import com.big.web.b2b_big2.flight.pojo.PERSON_TYPE;
import com.big.web.b2b_big2.flight.pojo.PNR;
import com.big.web.b2b_big2.flight.pojo.SearchForm;
import com.big.web.b2b_big2.report.dao.IReportDao;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.AppInfo;
import com.big.web.b2b_big2.util.Currency;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.model.LabelValue;
import com.big.web.model.User;

@Service("bookingManager")
@WebService(serviceName = "BookingService", endpointInterface = "com.big.web.service.BookingService" )
@Transactional
public class BookingManagerImpl implements IBookingManager{

	private static final Log log = LogFactory.getLog(BookingManagerImpl.class);

	public static final int INSURANCE_PER_PAX = 10000;
	public static final double INSURANCE_COMMISSION = 0.2;
	
	@Autowired
	IBookingFlightDao bookingFlightDao;

	@Autowired
	IFlightDao flightDao;
	
	@Autowired
	IReportDao reportDao;
	
	@Autowired
	IAgentDao agentDao;

	@Autowired
	ICustomerDao customerDao;

	@Autowired
	IFinanceDao financeDao;
	
	@Autowired
	IAirlineDao airlineDao;
	
	@Autowired
	ISetupDao setupDao;
	
	@Autowired IBookingAsyncService asyncBooking;

	@Override
	@Async
	public void startSlowProcess() {
		log.debug("start " + Thread.currentThread().getName() + " " + new Date());
		try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }	
		log.debug("end "  + Thread.currentThread().getName() + " " + new Date());
	}

	/**
	 * 
	 * @param list pastikan jumlah yg akan diproses sudah ditentukan sebelumnya. misalnya tiket pertama airasia, dan tiket kedua kalstar.
	 * jadi tidak mungkin kedua-duanya kalstar.
	 * <br>yg mungkin adalah depart and return utk airline yg sama, jumlahnya = 1
	 * @return
	 * @throws Exception
	 */
	@Override
	public void bookingFromAirline(List<BookingFlightVO> list, Agent agent) throws Exception{

		//ga support jika ada > 2
		if (list.size() > 2)
			throw new RuntimeException("Unsupported booking more than 2 airlines");
		
		//utk kebaikan, pastikan tidak ada airline yg sama
		String lastAC = "";
		for (BookingFlightVO bf : list){
			if (lastAC.equalsIgnoreCase(bf.getAirlines_iata())){
				throw new RuntimeException("Cant multi book for same airlines");
			}
			
			lastAC = bf.getAirlines_iata();
		}

		//either generate or retrieve from ODR is up to you, handle here
		//filter by airline
		
		//hati2, ada peluang bookingcode bisa sama karena cuma 6 digit
		//dan trickie, misal "KEKKNB,R4LU2P"
		//nanti di tampilan tinggal di group based on "KEKKNB,R4LU2P" yg sama
		List<String> groupCode = new ArrayList<String>();
		
		
		//booking by each airline
		Future<BookingFlightVO> booking1 = null;
		Future<BookingFlightVO> booking2 = null;
		
		for (int i = 0; i < list.size(); i++){
			
			BookingFlightVO bf = list.get(i);
			
			Airline[] airlines = Airline.getAirlinesByCode(bf.getAirlines_iata());
			
			for (int j = 0; j < airlines.length; j++) {
				
				switch (airlines[j]){
				case KALSTAR:
				case TRIGANA:
				case AVIASTAR:
					//no need ODR
					SqivaHandler kalstarHandler = new SqivaHandler(airlines[j], setupDao);
					
					ResponseBooking resp = kalstarHandler.booking(agent.getCart().getFlightCart());
					
					if (resp.isError())
						throw new SqivaServerException(resp.getErrCode(), resp.getErrMsg());
					
					//update seat here: ambil schedule, please see BigEngine-Sqiva
					//map from agent into form
					SearchForm form = new SearchForm();
					form.setDepartCity(bf.getOriginIata());
					form.setDestCity(bf.getDestinationIata());
					form.setDepartDate(bf.getDeparture_date());
					form.setReturnDate(bf.getReturn_date());
					form.setTripMode(Integer.parseInt(bf.getReturn_flag()));
					
					@SuppressWarnings("unchecked")
					List<FlightSearchB2B> _schedules = (List<FlightSearchB2B>) kalstarHandler.getSchedules(form);
					
					for (FlightSearchB2B _schedule : _schedules){
						flightDao.updateSeat(_schedule.getFlightAvailId(), _schedule.getSeats());
					}
					
					bf.setCode(resp.getBook_code());
					bf.setTimeToPay(new Date( resp.getPay_limit() ));
					
					//utk connecting flights
					/*
						for (int j = 0; j < 2; j++){
							
							bookingFlightDao.saveBookingFlightSched(sched);
						}
					 */
					
					bf.setStatus(resp.getStatusAsCSV());
					
					// normal sales sementara masukin ke nta krn bingung. please ask chin-chin
					// moved to bookingFlightTrans
//					bf.setNta(new BigDecimal(resp.getNormal_sales()));
					bf.setBook_balance(new BigDecimal(resp.getBook_balance()));
					// end moved
					
					bf.setLastUpdate(new Date());
					
					bookingFlightDao.saveBookingFlight(bf);
					
					BookingFlightTransVO _trans = bookingFlightDao.getBookingFlightTrans(bf.getId());
					
					_trans.setAfter_amount(new BigDecimal(resp.getNormal_sales()));
					_trans.setBook_balance(new BigDecimal(resp.getBook_balance()));
					
					_trans.setAfter_basefare(_trans.getBasefare());
					_trans.setAfter_iwjr(_trans.getIwjr());
					_trans.setAfter_psc(_trans.getPsc());
					_trans.setAfter_tax(_trans.getTax());
					
					//					_trans.setAfter_comm(financeDao.getNetCommission(_trans.getAfter_basefare(), _trans.getIssued(), airlines[j]));
					
					// jika ada 2 airline, perhitungan komisinya brarti di total ?
					List<BookingFlightSchedVO> bookingFlightSchedules = getBookingFlightSchedules(bf.getId());
					
					for (int k = 0; k < bookingFlightSchedules.size(); k++){
						
					}
					
					_trans.setAfter_comm(financeDao.getNetCommission(
							_trans.getAfter_basefare()
							, _trans.getIssued()
							, airlines[j]
									, bf.getOriginIata()
									, bf.getDestinationIata()
									, Integer.parseInt(bf.getReturn_flag())
									, null	// ignored for sqiva
									, null	// ignored 
							));
					
					_trans.setAfter_nta(_trans.getAfter_amount().subtract(_trans.getAfter_comm()));
					//					_trans.setAfter_nta(_trans.getNta());
					
					bookingFlightDao.saveBookingFlightTrans(_trans);
					
					groupCode.add(bf.getCode());
					
					// TODO mengapa disini ga ada modif list seperti di bawah?

				default:
					//call ODR
					if (booking1 == null)
						booking1 = asyncBooking.callODR(bf, airlines[j], bookingFlightDao, financeDao, setupDao);
					else
						booking2 = asyncBooking.callODR(bf, airlines[j], bookingFlightDao, financeDao, setupDao);
				}//switch
				
			}
		}
		
		if (list.size() == 1){
			while (!(booking1.isDone())) {
				Thread.sleep(10); //10-millisecond pause between each check
			}
			
			//get update from database
			list.set(0, booking1.get());
		}else
			if (list.size() == 2){
				while (!(booking1.isDone() && booking2.isDone())) {
					Thread.sleep(10); //10-millisecond pause between each check
				}

				// manual add. utk sementara cuma bisa handle 2 booking
				groupCode.add(booking1.get().getCode());
				list.set(0, booking1.get());

				groupCode.add(booking2.get().getCode());
				list.set(1, booking2.get());
				
			}
		

		//update groupCode
		if (list.size() > 1)
			for (int i = 0; i < list.size(); i++){
				BookingFlightVO bf = list.get(i);
				
				//moved to bookingFlightTrans
				bf.setGroupCode(StringUtils.collectionToDelimitedString(groupCode, ","));
				bookingFlightDao.saveBookingFlight(bf);
				//end moved
				
				BookingFlightTransVO _trans = bookingFlightDao.getBookingFlightTrans(bf.getId());
				_trans.setGroupCode(StringUtils.collectionToDelimitedString(groupCode, ","));
				
				bookingFlightDao.saveBookingFlightTrans(_trans);
				
			}		
	}
	
	public void bookingFromAirlineOld(List<BookingFlightVO> list, Agent agent) throws Exception{
		
		//utk kebaikan, pastikan tidak ada airline yg sama
		String lastAC = "";
		for (BookingFlightVO bf : list){
			if (lastAC.equalsIgnoreCase(bf.getAirlines_iata())){
				throw new RuntimeException("Cant multi book for same airlines");
			}
			
			lastAC = bf.getAirlines_iata();
		}
		
		//either generate or retrieve from ODR is up to you, handle here
		//filter by airline
		
		//hati2, ada peluang bookingcode bisa sama karena cuma 6 digit
		//dan trickie, misal "KEKKNB,R4LU2P"
		//nanti di tampilan tinggal di group based on "KEKKNB,R4LU2P" yg sama
		List<String> groupCode = new ArrayList<String>();
		
		//booking by each airline
		for (int i = 0; i < list.size(); i++){
			
			BookingFlightVO bf = list.get(i);
			
			Airline[] airlines = Airline.getAirlinesByCode(bf.getAirlines_iata());
			
			for (int j = 0; j < airlines.length; j++) {
				
				switch (airlines[j]){
				case KALSTAR:
				case TRIGANA:
				case AVIASTAR:
					//no need ODR
					SqivaHandler kalstarHandler = new SqivaHandler(airlines[j], setupDao);
					
					ResponseBooking resp = kalstarHandler.booking(agent.getCart().getFlightCart());
					
					if (resp.isError())
						throw new SqivaServerException(resp.getErrCode(), resp.getErrMsg());
					
					//update seat here: ambil schedule, please see BigEngine-Sqiva
					//map from agent into form
					SearchForm form = new SearchForm();
					form.setDepartCity(bf.getOriginIata());
					form.setDestCity(bf.getDestinationIata());
					form.setDepartDate(bf.getDeparture_date());
					form.setReturnDate(bf.getReturn_date());
					form.setTripMode(Integer.parseInt(bf.getReturn_flag()));
					
					@SuppressWarnings("unchecked")
					List<FlightSearchB2B> _schedules = (List<FlightSearchB2B>) kalstarHandler.getSchedules(form);
					
					for (FlightSearchB2B _schedule : _schedules){
						flightDao.updateSeat(_schedule.getFlightAvailId(), _schedule.getSeats());
					}
					
					bf.setCode(resp.getBook_code());
					bf.setTimeToPay(new Date( resp.getPay_limit() ));
					
					//utk connecting flights
					/*
						for (int j = 0; j < 2; j++){
							
							bookingFlightDao.saveBookingFlightSched(sched);
						}
					 */
					
					bf.setStatus(resp.getStatusAsCSV());
					
					// normal sales sementara masukin ke nta krn bingung. please ask chin-chin
					// moved to bookingFlightTrans
//					bf.setNta(new BigDecimal(resp.getNormal_sales()));
					bf.setBook_balance(new BigDecimal(resp.getBook_balance()));
					// end moved
					
					bf.setLastUpdate(new Date());
					
					bookingFlightDao.saveBookingFlight(bf);
					
					BookingFlightTransVO _trans = bookingFlightDao.getBookingFlightTrans(bf.getId());
					
					_trans.setAfter_amount(new BigDecimal(resp.getNormal_sales()));
					_trans.setBook_balance(new BigDecimal(resp.getBook_balance()));
					
					_trans.setAfter_basefare(_trans.getBasefare());
					_trans.setAfter_iwjr(_trans.getIwjr());
					_trans.setAfter_psc(_trans.getPsc());
					_trans.setAfter_tax(_trans.getTax());
					
					//					_trans.setAfter_comm(financeDao.getNetCommission(_trans.getAfter_basefare(), _trans.getIssued(), airlines[j]));
					
					// jika ada 2 airline, perhitungan komisinya brarti di total ?
					List<BookingFlightSchedVO> bookingFlightSchedules = getBookingFlightSchedules(bf.getId());
					
					for (int k = 0; k < bookingFlightSchedules.size(); k++){
						
					}
					
					_trans.setAfter_comm(financeDao.getNetCommission(
							_trans.getAfter_basefare()
							, _trans.getIssued()
							, airlines[j]
									, bf.getOriginIata()
									, bf.getDestinationIata()
									, Integer.parseInt(bf.getReturn_flag())
									, null	// ignored for sqiva
									, null	// ignored 
							));
					
					_trans.setAfter_nta(_trans.getAfter_amount().subtract(_trans.getAfter_comm()));
					//					_trans.setAfter_nta(_trans.getNta());
					
					bookingFlightDao.saveBookingFlightTrans(_trans);
					
					groupCode.add(bf.getCode());
					
					//TODO mengapa disini ga ada modif list seperti di bawah?
					
					//cukup sekali
					return;
				default:
					//call ODR
					ODRHandler odr = ODRHandler.getInstance();
					
					odr.booking(setupDao, airlines[j], bf.getId());
					
					//get update from database
					BookingFlightVO _buffer = bookingFlightDao.getBookingFlight(bf.getId());
					
					BookingFlightTransVO _bfTrans = bookingFlightDao.getBookingFlightTrans(_buffer.getId());
					if (_buffer.getAmount() != null){
						_bfTrans.setAfter_comm(financeDao.getNetCommission(_bfTrans.getAfter_basefare(), _bfTrans.getIssued(),  _buffer.getFirstAirline()
								, _buffer.getOriginIata()
								, _buffer.getDestinationIata()
								, Integer.parseInt(_buffer.getReturn_flag())
								, null
								, null
								));
						_bfTrans.setAfter_nta(_bfTrans.getAfter_amount().subtract(_bfTrans.getAfter_comm()));
						/*
//						_bfTrans.setAfter_amount(_buffer.getAmount());
						_bfTrans.setAfter_basefare(after_basefare);
						_bfTrans.setAfter_iwjr(after_iwjr);
						_bfTrans.setAfter_psc(after_psc);
						_bfTrans.setAfter_tax(after_tax);
						 */
						bookingFlightDao.saveBookingFlightTrans(_bfTrans);
					}
					
					groupCode.add(_buffer.getCode());
					
					list.set(i, _buffer);
					
					//cukup sekali
//					return;
				}//switch
				
			}
		}
		
		//update groupCode
		if (list.size() > 1)
			for (int i = 0; i < list.size(); i++){
				BookingFlightVO bf = list.get(i);
				
				//moved to bookingFlightTrans
				bf.setGroupCode(StringUtils.collectionToDelimitedString(groupCode, ","));
				bookingFlightDao.saveBookingFlight(bf);
				//end moved
				
				BookingFlightTransVO _trans = bookingFlightDao.getBookingFlightTrans(bf.getId());
				_trans.setGroupCode(StringUtils.collectionToDelimitedString(groupCode, ","));
				
				bookingFlightDao.saveBookingFlightTrans(_trans);
				
			}
	}

	@Override
	public List<BookingFlightVO> findBookingFlight(String username) {
		return bookingFlightDao.findBookingFlight(username);
	}

	@Override
	public void saveBookingFlight(BookingFlightVO obj) {
		bookingFlightDao.saveBookingFlight(obj);
	}

	@Override
	public void saveBookingFlightDtl(BookingFlightDtlVO obj) {
		bookingFlightDao.saveBookingFlightDtl(obj);
	}
	
	@Override
	public void saveBookingFlightSched(BookingFlightSchedVO obj) {
		bookingFlightDao.saveBookingFlightSched(obj);
	}

	@Override
	public void saveBookingFlightTrans(BookingFlightTransVO obj) {
		bookingFlightDao.saveBookingFlightTrans(obj);
	}

	@Override
	public void cancelTicket(String bookCode) throws Exception{
		
		// lebih simple harusnya, jadi cancel manual, ga perlu konfirmasi ke airline
		bookingFlightDao.cancelBookingByCode(bookCode);
		
		
		/*
		BookingFlightVO bf = bookingFlightDao.getBookingFlightByBookingCode(bookCode);
		
		Airline[] airlines = Airline.getAirlinesByCode(bf.getAirlines_iata());//JT,SJ

		for (int i = 0; i < airlines.length; i++){
			Airline _airline = airlines[i];
					
			if (_airline == Airline.KALSTAR || _airline == Airline.TRIGANA || _airline == Airline.AVIASTAR){
				
				ResponseCancel resp = new SqivaHandler(_airline, setupDao).cancel(bookCode);
				
				if (resp.isError()){
					//assume canceled by airline but local database said difference
					if (resp.getErrCode().equals("001003")){
						bookingFlightDao.cancelBookingByCode(bookCode);
					}else			
						throw new SqivaServerException(resp.getErrCode(), resp.getErrMsg());
				}else{
					bookingFlightDao.cancelBookingByCode(bookCode);
					//cukup sekali saja
					break;
				}
				
			}else{
				// cukup cancel manual, ga perlu konfirmasi ke airline
				// throw new RuntimeException(bookCode + " not canceled by any airline !");
				bookingFlightDao.cancelBookingByCode(bookCode);
			}
		}
		*/
		
	}

	private void linkPNR(BookingFlightVO obj, Agent agent) throws Exception{
		
		if (obj == null || StringUtils.isEmpty(obj.getId()))
			throw new RuntimeException("Missing Booking Master.");

		int paxInsurance = 0;
		for (PNR pnr : agent.getCart().getFlightCart().getPnr()){
			
			BookingFlightDtlVO dtl = new BookingFlightDtlVO(pnr);
			
			dtl.setBooking_flight_id(obj.getId());

			paxInsurance += INSURANCE_PER_PAX; 
					
			bookingFlightDao.saveBookingFlightDtl(dtl);
			
			customerDao.addHistory(pnr, agent.getId());
		}
		
		obj.setCcy(Currency.CURR_IDR);
		
		obj.setPnr(agent.getCart().getFlightCart().getPnr());
		obj.setCust_name(agent.getCart().getFlightCart().getCustomer().getFullName());
		obj.setCust_phone1(agent.getCart().getFlightCart().getCustomer().getPhone1());
		obj.setCust_phone2(agent.getCart().getFlightCart().getCustomer().getPhone2());
		
		// update insurance summary
		obj.setInsurance(new BigDecimal(paxInsurance).subtract(Utils.getDiscount((double)paxInsurance, INSURANCE_COMMISSION) ));
		obj.setInsuranceCommission(Utils.getDiscount((double)paxInsurance, INSURANCE_COMMISSION));

//		customerDao.addHistory(agent.getCart().getFlightCart().getCustomer(), obj.getAgentId());
		customerDao.addHistory(agent.getCart().getFlightCart().getCustomer(), agent.getId());
		customerDao.flushHistory();
	}
	
	private void linkSchedules(BookingFlightVO obj, FlightItinerary it){
		if (obj == null || StringUtils.isEmpty(obj.getId()))
			throw new RuntimeException("Missing Booking Master.");
		
		if (it.getFareInfos().size() < 1) return;
			
		Set<String> airlinesIata = new LinkedHashSet<String>();	//berguna utk memberi warna berbeda di booking report
		//krn bisa nambah, load dulu
		if (!StringUtils.isEmpty(obj.getAirlines_iata())){
			airlinesIata = StringUtils.commaDelimitedListToSet(obj.getAirlines_iata());
		}
		
//		BigDecimal _sumNetComm = BigDecimal.ZERO;
		Set<String> agentIds = new LinkedHashSet<String>();
		for (FareInfo fareInfo : it.getFareInfos()) {
			
			airlinesIata.add(fareInfo.getAirlineIata());
			agentIds.add(fareInfo.getAgentId());

//			BigDecimal _netComm = financeDao.getNetCommission(fareInfo.getRate(), Airline.getAirlineByCode(fareInfo.getAirlineIata()));
//			_sumNetComm = _sumNetComm.add(_netComm);

            
			BookingFlightSchedVO dtl = new BookingFlightSchedVO(fareInfo);
				dtl.setBooking_flight_id(obj.getId());
				dtl.setLastUpdate(new Date());
				dtl.setJourney_sell_key(fareInfo.getJourneyKey());
			
			bookingFlightDao.saveBookingFlightSched(dtl);
		}
		
		if (agentIds.size() > 1) throw new RuntimeException("Kok bisa ada " + agentIds.size() + " agen dalam 1 depart/return yah ?");
		
		obj.setAirlines_iata(StringUtils.collectionToCommaDelimitedString(airlinesIata));
		obj.setAgentId(agentIds.toArray(new String[agentIds.size()])[0]);	//ambil yg pertama saja
		
		//for display report
		obj.setItineraries(it.getFareInfos());
		
		//pax paid - net commission
		//TODO ask harga pax paid udah bener blm ?

//		BigDecimal _nta = it.getAmount().subtract(_sumNetComm);
//		obj.setNta(_nta);	//TODO cuma utk harga awal belum update dari ODR
	}
	
	/**
	 * Must be called after linkPNR and linkSchedule are called.
	 * @param obj
	 * @param it
	 */
	private void linkTrans(BookingFlightVO obj, FlightItinerary it) {
		if (obj == null || StringUtils.isEmpty(obj.getId()))
			throw new RuntimeException("Missing Booking Master.");
		
		if (it.getFareInfos().size() < 1) return;
		
		List<BookingFlightSchedVO> schedules = getBookingFlightSchedules(obj.getId());
		if (Utils.isEmpty(schedules))
			throw new RuntimeException("Missing Schedules.");
		 
		BigDecimal basefare = BigDecimal.ZERO;
		BigDecimal iwjr = BigDecimal.ZERO;
		BigDecimal psc = BigDecimal.ZERO;
		BigDecimal surchargefee = BigDecimal.ZERO;
		BigDecimal tax = BigDecimal.ZERO;
		BigDecimal commission = BigDecimal.ZERO;
		BigDecimal issuedFee = BigDecimal.ZERO;	// per rute * (adult + children), sudah dihitung fix sebelum booking. jadi wkt booking tdk perlu dihitung lagi
		BigDecimal amountTicket = BigDecimal.ZERO;	// rate + psc + iwjr + tax
		
		BigDecimal incentive = BigDecimal.ZERO;
		
		//jika sudah ada, update price
		
		BookingFlightTransVO trans = getBookingFlightTrans(obj.getId());
		if (trans == null){
			trans = new BookingFlightTransVO(obj.getId());
		}else{
			basefare = trans.getBasefare();
			iwjr = trans.getIwjr();
			psc = trans.getPsc();
			surchargefee = trans.getSurchargefee();
			tax = trans.getTax();
			commission = trans.getCommission();
			
			amountTicket = trans.getAmount();
			
			issuedFee = trans.getIssued();
//			paxPaid = basefare.add(psc).add(iwjr).add(tax);
		}
		
		for (FareInfo fi : it.getFareInfos()){
			if (fi.getRate() != null){

				Airline _airline = Airline.getAirlineByCode(fi.getAirlineIata());
				CommissionVO _commVO = financeDao.getCommission(_airline, fi);
				
				// beware yg transit jika radioidnya sama, fareinfo kedua selalu nol, jadi issuedfee harus dibedain
				if (fi.getRate().doubleValue() > 0 ){

					BigDecimal perTax = BigDecimal.ZERO.add(fi.getTax_adult() == null ? BigDecimal.ZERO : fi.getTax_adult())
							.add(fi.getTax_child() == null ? BigDecimal.ZERO : fi.getTax_child())
							.add(fi.getTax_infant() == null ? BigDecimal.ZERO : fi.getTax_infant());
					
					tax = tax.add(perTax);
					/*
					tax = tax.add(fi.getTax_adult() == null ? BigDecimal.ZERO : fi.getTax_adult())
							.add(fi.getTax_child() == null ? BigDecimal.ZERO : fi.getTax_child())
							.add(fi.getTax_infant() == null ? BigDecimal.ZERO : fi.getTax_infant())
							;
					*/
					psc = psc.add(fi.getPsc());
					
					iwjr = iwjr.add(fi.getIwjr());
					surchargefee = surchargefee.add(fi.getFuelSurcharge());
					basefare = basefare.add(fi.getRate());
					issuedFee = issuedFee.add(fi.getIssuedFee());
					
//					boolean isInfant = fi.getChildrenPax() > 0;
					//3pax (infant is not included) with 2 routes = issuedFee x 3 x 2
					BigDecimal perIssuedFee = BigDecimal.ZERO.add(_commVO.getIssued_fee().multiply(new BigDecimal(fi.getAdultPax() + fi.getChildrenPax())));
					BigDecimal _netComm = financeDao.getNetCommission(fi.getRate(), perIssuedFee, _airline, fi);
//					BigDecimal _netComm = financeDao.getNetCommission(fi.getRate(), _commVO.getIssued_fee(), _airline);
	//				-------linkTrans._netComm=27970.00, _sumNetComm=27970.00,commission=27970.00
					commission = commission.add(_netComm);
					
//					System.err.println(">>>>------linkTrans.rate=" + fi.getRate() +",_netComm=" + _netComm + ",commission=" + commission + ",sumbasefare=" + basefare);
					System.err.println(">>>>------perTax=" + perTax + ",perissued=" + perIssuedFee + ",issuedFee=" + issuedFee + ",netcomm=" + _netComm + ",commission=" + commission + ", fareInfo=" + fi.toString());
	
					amountTicket = amountTicket.add(fi.getRate()).add(fi.getPsc()).add(fi.getIwjr()).add(perTax);
//					paxPaid = paxPaid.add(basefare).add(psc).add(iwjr).add(tax);
				}else{
					BigDecimal _rate = fi.getRate();
					//FIXME khusus airasia scraping. terlalu custom, bagaimana jika ganti ke API ???
					if (_airline == Airline.AIRASIA){
						_rate = _rate.add(fi.getInfantFare());
						basefare = basefare.add(fi.getInfantFare());
						amountTicket = amountTicket.add(fi.getInfantFare());
					}
					
					issuedFee = issuedFee.add(fi.getIssuedFee());
					
					BigDecimal perIssuedFee = BigDecimal.ZERO.add(_commVO.getIssued_fee().multiply(new BigDecimal(fi.getAdultPax() + fi.getChildrenPax())));
//					issuedFee = issuedFee.add(perIssuedFee);
					BigDecimal _netComm = financeDao.getNetCommission(_rate, perIssuedFee, _airline, fi);
					commission = commission.add(_netComm);

					System.err.println(">>>>------perissued=" + perIssuedFee + ",netcomm=" + _netComm + ",issuedFee=" + issuedFee + ",commission=" + commission + ", fareInfo=" + fi.toString() );
				}
				
				//beda dengan issuedfee yg dihitung by routenya
//				issuedFee = issuedFee.add(_commVO.getIssued_fee());
				
				// update Comm_Rate, cukup dicari berdasarkan flightnum, karena dalam 1 kali booking tidak mungkin ada flightno yg sama
				for (int i = 0; i < schedules.size(); i++){
					BookingFlightSchedVO _sched = schedules.get(i);
					if (_sched.getFlightNum().equalsIgnoreCase(fi.getFlightNo())){
						
						
						_sched.setComm_Rate(_commVO.getComm_rate());
						_sched.setComm_rate_berleha(_commVO.getComm_rate_berleha());
						_sched.setPph23(_commVO.getPph23());
						
						_sched.setIncentive(_commVO.getIncentive());
						incentive = incentive.add(_commVO.getIncentive());
						
						bookingFlightDao.saveBookingFlightSched(_sched);
						break;
					}
				}
			}
			/*
			if (fi.getRate() != null && fi.getRate().doubleValue() > 0 ){
				
				tax = tax.add(fi.getTax_adult() == null ? BigDecimal.ZERO : fi.getTax_adult())
						.add(fi.getTax_child() == null ? BigDecimal.ZERO : fi.getTax_child())
						.add(fi.getTax_infant() == null ? BigDecimal.ZERO : fi.getTax_infant())
						;
				
				psc = psc.add(fi.getPsc());
				iwjr = iwjr.add(fi.getIwjr());
				surchargefee = surchargefee.add(fi.getFuelSurcharge());
				basefare = basefare.add(fi.getRate());
				
				Airline _airline = Airline.getAirlineByCode(fi.getAirlineIata());
				
				BigDecimal _netComm = financeDao.getNetCommission(fi.getRate(), _airline);
//				-------linkTrans._netComm=27970.00, _sumNetComm=27970.00,commission=27970.00
				commission = commission.add(_netComm);
				
				System.err.println(">>>>------linkTrans.rate=" + fi.getRate() +",_netComm=" + _netComm + ",commission=" + commission + ",sumbasefare=" + basefare);
				CommissionVO _commVO = financeDao.getCommission(_airline);
				
				//3pax (infant is not included) with 2 routes = issuedFee x 3 x 2
				issuedFee = issuedFee.add(_commVO.getIssued_fee());
//				issuedFee = issuedFee.add(_commVO.getIssued_fee());
				
				paxPaid = paxPaid.add(basefare).add(psc).add(iwjr).add(tax);
			}*/
		}
		trans.setAmount(amountTicket);
		trans.setBasefare(basefare);
		trans.setIwjr(iwjr);
		trans.setPsc(psc);
		trans.setSurchargefee(surchargefee);
		trans.setTax(tax);
		trans.setCommission(commission);
		
		//3pax (infant is not included) with 2 routes = issuedFee x 3 x 2
//		int paxCountWithoutInfant = bookingFlightDao.getPaxCountWithoutInfant(obj.getId());
//		trans.setIssued(issuedFee.multiply(new BigDecimal(paxCountWithoutInfant)));
		trans.setIssued(issuedFee);
		
		trans.setIns(BigDecimal.ZERO);
		trans.setIns_comm(BigDecimal.ZERO);
		trans.setSvc(BigDecimal.ZERO);
		trans.setSvc_comm(BigDecimal.ZERO);
		trans.setBook_balance(BigDecimal.ZERO);	// akan diisi waktu .bookingFromAirline. biasanya kalstar
		trans.setAfter_amount(null);	// hanya diisi setelah issued biasanya kalstar
		trans.setNta(amountTicket.subtract(commission));
		/*
		CommissionVO commVO = financeDao.getCommission(obj.getAirlines()[0]
							, obj.getOriginIata()
							, obj.getDestinationIata()
							, obj.getReturn_flag()
							, flightNo gimana caranya ambil satu dari beberapa flight ?
							, seatClass same as flightNo
								);
								*/
		
//		trans.setComm_rate(commVO.getCommission());
		//trans.setPph23(commVO.getPph23());
//		trans.setComm_berleha(commVO.getCommission_berleha());
		
		trans.setInsentive(incentive.multiply(new BigDecimal(bookingFlightDao.getPaxCountWithoutInfant(obj.getId()))));	//harusnya itung per-pax minus infant
//		trans.setInsentive(commVO.getIncentive().multiply(new BigDecimal(bookingFlightDao.getPaxCountWithoutInfant(obj.getId()))));	//harusnya itung per-pax minus infant
		
//		System.err.println(">>>>------Summary " + trans);

		bookingFlightDao.saveBookingFlightTrans(trans);
		
//		obj.setNta(amountTicket.subtract(commission));	moved into trans
		
		//for display report
		obj.setDetailTransaction(trans);
	}


	/**
	 * 1 booking code biasanya utk 1 airline. 
	 * <br>misal: jika depart dan return menggunakan airline yg sama bookingcodenya cuma 1.
	 * <br>jika depart dan return beda airline, berarti bookingcodenya ada 2
	 * <p>Tetapi dalam kasus BIG, jumlah booking ditentukan mau kemana booking akan dilakukan.
	 * <br>misal: jika departnya ada sriwijaya dan Nam, maka booking ke sriwijaya-agen sehingga akan didapat 1 kode booking.
	 * <br>jika returnnya garuda, maka booking ke gosga akan didapat 1 kode booking.
	 * <br>Jadi total ada 2 kode booking.
	 * @param agent
	 * @return list of booking code.
	 */
	@Override
	public List<BookingFlightVO> generateBooking(Agent agent) throws Exception {

		if (agent.getCart().getFlightCart().getItineraries().size() < 1){
			throw new CartEmptyException("Cart cannot empty !");
		}

		List<BookingFlightVO> newBookings = new ArrayList<BookingFlightVO>();
		//1. create first to generate new id
		BookingFlightVO bookingMaster1 = new BookingFlightVO(agent.getFlightCart());
		
		// 18 sep 2015 ada laporan yg booking eric tp yg masuk kok bisa verry ?
		User user = agentDao.getUser(agent.getUserName());
				
		bookingMaster1.setUser(user);

		//depart sudah pasti ada
		if (agent.getFlightCart().getDepartFlightItinerary().getFareInfos().size() > 0){
			
			bookingMaster1.setUpdateCode(agent.getFlightCart().getDepartFlightItinerary().getFareInfos().get(0).getUpdateCode());
			
			//1. attach flight tickets
			linkSchedules(bookingMaster1, agent.getFlightCart().getDepartFlightItinerary());

			//2. attach pnr
			linkPNR(bookingMaster1, agent);

			//3. attach trans, one to one. disini dihitung dulu sum utk diinsert ke bookingflighttrans
			linkTrans(bookingMaster1, agent.getFlightCart().getDepartFlightItinerary());
			
			//4. save mst
			bookingMaster1.setCreatedDate(new Date());
		}
		
		//beda dgn depart, attach pnr hanya dilakukan jika kode agent beda
		boolean isSecondBooking = false;
		for (FareInfo fi : agent.getFlightCart().getReturnFlightItinerary().getFareInfos()){
			if (!bookingMaster1.getAgentId().equals(fi.getAgentId())){
				isSecondBooking = true;
				break;
			}
		}
		
		try{
			boolean pleaseTestDiffAgency = agent.getFlightCart().isDifferentAgency(); 
			System.err.println("!!!!!!!!!!!pleaseTestDiffAgency=" + pleaseTestDiffAgency + ", isSecondBooking=" + isSecondBooking);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//jika ternyata beda agent, maka bookingnya 2x. 
		if (isSecondBooking){
			BookingFlightVO bookingMaster2 = new BookingFlightVO(agent.getFlightCart());
			bookingMaster2.setUser(agentDao.getUser(agent.getUserName()));
			
			bookingMaster2.setOriginIata(agent.getFlightCart().getDestinationIata());
			bookingMaster2.setDestinationIata(agent.getFlightCart().getOriginIata());
			bookingMaster2.setDeparture_date(agent.getFlightCart().getReturnDate());
			bookingMaster2.setReturn_date(null);
//			bookingMaster2.setDeparture_date(agent.getFlightCart().getDepartDate());
//			bookingMaster2.setReturn_date(agent.getFlightCart().getReturnDate());
			
			// reset to fix radioId info
			bookingMaster2.setRadio_id(StringUtils.arrayToCommaDelimitedString(agent.getFlightCart().getReturnFlightItinerary().getRadioIds()));
			// tidak mungkin second booking ada returnnya, termasuk juga di first booking
			bookingMaster1.setRadioid_return(null);
			bookingMaster2.setRadioid_return(null);
			// returnflagnya jika 1 dibalikin ke 0
			bookingMaster1.setReturn_flag("0");
			bookingMaster2.setReturn_flag("0");

			bookingMaster2.setUpdateCode(agent.getFlightCart().getReturnFlightItinerary().getFareInfos().get(0).getUpdateCode());

			//1.attach schedules
			linkSchedules(bookingMaster2, agent.getFlightCart().getReturnFlightItinerary());
			
			//2.attach pnr
			linkPNR(bookingMaster2, agent);

			//3.attach trans, one to one. disini dihitung dulu sum utk diinsert ke bookingflighttrans
			linkTrans(bookingMaster2, agent.getFlightCart().getReturnFlightItinerary());

			//4. save mst
			bookingMaster2.setCreatedDate(new Date());
			/*
			log.debug("checking first booking=" + bookingMaster1);
			log.debug("checking second booking=" + bookingMaster2);
			*/
			
			bookingFlightDao.saveBookingFlight(bookingMaster2);

			newBookings.add(bookingMaster2);
		}else{
//			jika pulang pergi dalam 1 tiket/booking code
			//repair radioId
			/* bu fini minta radioid returnnya dipisahkan dengan depart
			String[] _b2 = agent.getCart().getFlightCart().getRadioIds();
			bookingMaster1.setRadio_id(StringUtils.arrayToCommaDelimitedString(_b2));
			*/
			
			//maunya tinggal diseparate by comma, misal SJ268SJ232,SJ233SJ255
			if (!agent.getFlightCart().getReturnFlightItinerary().isEmpty()){
				String _uc = agent.getFlightCart().getReturnFlightItinerary().getFareInfos().get(0).getUpdateCode();
				bookingMaster1.setUpdateCode(bookingMaster1.getUpdateCode() + "," + _uc);
			}
			
			//attach schedules only, because PNR already linked before this
			linkSchedules(bookingMaster1, agent.getFlightCart().getReturnFlightItinerary());
			
			//attach/update trans
			linkTrans(bookingMaster1, agent.getFlightCart().getReturnFlightItinerary());
		}

		bookingFlightDao.saveBookingFlight(bookingMaster1);
		
		log.info("generateBooking by agent " + agent + "(" + user.getUsername() + ",id=" + user.getId() + ") for " + bookingMaster1);
				
		
		newBookings.add(bookingMaster1);

		//2. call ODR and read response. karena belum jadi, sementara hardcode returnnya
		/* dipindah ke external
		bookingFromAirline(newBookings, agent);	//sementara assume sukses
		*/
		//3. ODR sukses akan mengisi kolom2 yg kosong, so just reload from database
		//biasanya jumlahnya 2
		for (int i = 0; i < newBookings.size(); i++) {
			
			// cek juga dengan maksimum booking yg diperbolehkan
			int _paxWOInfant = agent.getFlightCart().getPaxCountWithoutInfant();
			
			Airline[] _airlines = newBookings.get(i).getAirlines();
			
			if (_paxWOInfant > _airlines[0].getMaxPnr()){
				throw new BookingException("Maximum Adults and Children for " + _airlines[0].getAirlineName() + " is " + _airlines[0].getMaxPnr()); 
			}

//			BookingFlightVO b = bookingFlightDao.getBookingFlight(newBookings.get(i).getId());

			//utk testing isi dengan dummy data
//			b.setNta(newBookings.get(i).getAmountTicket());
			
			//just set soon to be deprecated value
//			Airline[] _airlines = Airline.getAirlinesByCode(b.getAirlines_iata()); 
//			b.setNtaCommission( financeDao.getNetCommission(b.get rate, _airlines[0]));
//			b.setNtaCommission( Utils.getDiscount(b.getNta().doubleValue(), CommissionAirline.getCommission(_airlines[0])));
			
//			newBookings.set(i, b);
		}
		
		if (newBookings.size() > 1)
			Collections.swap(newBookings, 0, 1);	//dibalik utk kenyamanan pembaca
		
		return newBookings;
				
	}

	/**
	 * Fungsi ini akan dipanggil saat baru mau Booking Tiket atau Search PNR, dimana agent kemudian mengklik tombol 'Issue Ticket'
	 */
	@Override
	public void issuedTicket(String bookCode) throws Exception{
		//question: jika ada banyak kode booking, apakah semuanya harus issued ?
		//answer: tidak. issue harus per-tiket	harusnya bisa issue 1 kode booking saja

		//cek saldo ?
		
		BookingFlightVO bf = bookingFlightDao.getBookingFlightByBookingCode(bookCode);
		BookingFlightTransVO trans = bookingFlightDao.getBookingFlightTrans(bf.getId());

		//1. check status
		log.debug("try to issue " + bf);
		String[] status = Utils.splitBookStatus(bf.getStatus());
		
		if (BOOK_STATUS.getStatus(status[0]) == BOOK_STATUS.OK){
			throw new IssuedException(bookCode + " already issued !");
		}else{
			
		}
		
		//2. check timetopay
		Date now = new Date();
		if (now.after(bf.getTimeToPay())){
			throw new IssuedException(bookCode + " is expired !");
		}
		
		//3.check amount
		if (bf.getNta() == null){
			throw new IssuedException("Failure NTA !");
		}

		//4.time to issued
		Airline[] airlines = Airline.getAirlinesByCode(bf.getAirlines_iata());
		for (int i = 0; i < airlines.length; i++){
			Airline _airline = airlines[i];
			
			IAirlineHandler airlineHandler;
			
			switch (_airline){
				case KALSTAR:
				case TRIGANA:
				case AVIASTAR:
					airlineHandler = new Sqiva_Handler(_airline, setupDao);

					airlineHandler.issuedTicket(bookCode, bookingFlightDao);
					break;
					
				default:
					//call ODR
					ODRHandler odr = ODRHandler.getInstance();
					
					odr.issued(setupDao, _airline, bf.getCode());

//					throw new IssuedException(bookCode + " not issued by any airline !");
					
					// TODO update database
					
					break;
			}
			
			break;	// cukup sekali prosesnya
		}
		

		String userName = bf.getUser().getUsername();
		
		//saldo terpotong kalau sudah issued by agent sendiri
		BigDecimal amountPayToAirline = bf.getNta();
		BigDecimal amountToDebet = trans.getAfter_amount();	// bf.getNta();
		BigDecimal amountPassengerMustPay = bf.getAmountNTAServiceInsurance();
		
		//5. credit the account
		log.info("Issued Ticket succeed, should credit " + bf.getNta() + ". " + bf);
		financeDao.debetAccountTrx(userName, amountToDebet, Currency.CURR_IDR, bf.getId());
		
//		if (agent.getCart().getAmount().compareTo(BigDecimal.ZERO) == 0){
			//biasanya promo makanya bisa gratis
//		}
		
	}

	/*
	@Override
	public BookingFlightVO getPNR(String bookingCode) {
		return bookingFlightDao.getPNR(bookingCode);
	}

	@Override
	public List<BookingFlightVO> getPNRs(String bookingCode) {
		return bookingFlightDao.getPNRs(bookingCode);
	}*/

	@Override
	public BookingFlightVO getBookingFlight(String uid) {
		return bookingFlightDao.getBookingFlight(uid);
	}

	@Override
	public List<BookingFlightVO> getBookingFlight(String dateFrom, String dateTo, String[] airlineCode, String userName, String[] status) throws Exception {
		Date from = Utils.fixDate(dateFrom);
		Date to = Utils.fixDate(dateTo);
		
		return bookingFlightDao.getBookingFlight(from, to, airlineCode, userName, status);
	}

	@Override
	public BookingFlightVO getBookingFlightByBookingCode(String bookingCode) {
		return bookingFlightDao.getBookingFlightByBookingCode(bookingCode);	
	}

	@Override
	public void sendTicket(String bookCode, String email) throws Exception{

		BookingFlightVO bf = bookingFlightDao.getBookingFlightByBookingCode(bookCode);

		//1. check status
		String[] status = Utils.splitBookStatus(bf.getStatus());
		
		BOOK_STATUS bookStatus = BOOK_STATUS.getStatus(status[0]);
		
		if (bookStatus != BOOK_STATUS.OK
				|| bookStatus != BOOK_STATUS.CLOSED
				){
			throw new RuntimeException(bookCode + " need to issued first !");
		}else{
			
		}
		
		Airline[] airlines = Airline.getAirlinesByCode(bf.getAirlines_iata());
		for (int i = 0; i < airlines.length; i++){
			Airline _airline = airlines[i];
			if (_airline == Airline.KALSTAR || _airline == Airline.TRIGANA || _airline == Airline.AVIASTAR){
				
				ResponseSendTicket sendTicketResp = new SqivaHandler(_airline, setupDao).sendTicket(bookCode, email); 
				if (sendTicketResp.isError()){
					throw new SqivaServerException(sendTicketResp);
				}else{
					
				}
				
			}else
				if (_airline == Airline.CITILINK){
					
				}else
					throw new RuntimeException(bookCode + " not emailed by any airline !");
			
			break;
		}
	}

	@Override
	public void printVoucherHotel(String bookCode) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BookingFlightVO> getExpiredBookingFlights(){
		return bookingFlightDao.getExpiredBookingFlights();
	}
	
	@Override
	public List<BookingFlightVO> prettyList(List<BookingFlightVO> bookingFlight) {
		for (int i = 0; i <	bookingFlight.size(); i++) {
			//check tanggal
			BookingFlightVO bf = bookingFlight.get(i);
			
			String[] _statuses = Utils.splitBookStatus(bf.getStatus());
			
			try {
				BOOK_STATUS _bookStatus = BOOK_STATUS.getStatus(_statuses[0]);

				switch (_bookStatus){
					case HOLD:
					case HK:
					case CONFIRMED:
						if (bf.getTimeToPay().before(new Date())){
							bf.setStatus(BOOK_STATUS.EXPIRED.name());
	
							bookingFlight.set(i, bf);
						}
						break;
					case XX:
						bf.setStatus(BOOK_STATUS.CANCELED.name());
						bookingFlight.set(i, bf);
						break;
					case OK:
						bf.setStatus(BOOK_STATUS.ISSUED.name());
						bookingFlight.set(i, bf);
					default:
						break;
				}

			}catch(BookStatusException e){
				bf.setStatus(BOOK_STATUS.UNMAPPED.name() + " [" + bf.getStatus() + "]");
				bookingFlight.set(i, bf);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
				
		}
		
		return bookingFlight;
	}

	@Override
	public List<PNR> getPnrList(PERSON_TYPE personType, Long userId) {
		
		return bookingFlightDao.getUniquePNRs(personType, userId);
		
	}

	@Override
	public List<ContactCustomer> getContactCustomer(Long userId) {
		return bookingFlightDao.getContactCustomer(userId);
	}

	@Override
	public List<BookingFlightVO> getBookingFlights(String uid) {
		return bookingFlightDao.getBookingFlights(uid);
	}

	@Override
	public List<BookingFlightDtlVO> getBookingFlightDetails(String booking_flight_id) {
		return bookingFlightDao.getBookingFlightDetails(booking_flight_id);
	}

	@Override
	public List<BookingFlightSchedVO> getBookingFlightSchedules(String booking_flight_id) {
		return bookingFlightDao.getBookingFlightSchedules(booking_flight_id);
	}
	
	@Override
	public BookingFlightTransVO getBookingFlightTrans(String booking_flight_id) {
		return bookingFlightDao.getBookingFlightTrans(booking_flight_id);
	}


	/**
	 * Dihitung Per-booking
	 */
	@Override
	public BigDecimal calculateAmountBeforeBooking(BookingFlightVO bf) {
		
//		Airline[] airlines = bf.getAirlines();	//antisipasi transit. seperti LionAir, Batik, Wings
//		
//		Citilink_Handler.getInstance(setupDao).calculateFareInfo(fareInfo2, adultCount, childCount, infantCount)
//		if (airlines[0]){
//			
//		}
//		
//		
//		manual = bf.getTicketNtsa(bookingManager.getBookingFlightDetails(bf.getId()).size())
//				.add(bf.getTicketCommission());
		// TODO Auto-generated method stub
		return BigDecimal.ZERO;
	}

	@Override
	public List<AppInfo> getAppInfo() {
		List<AppInfo> list = new ArrayList<AppInfo>();
/*		
			Today Issued
			Today Cancelled
			Today Hold

			Total Issued Tickets
			Total Hold Tickets
			Total Cancelled Tickets
			Favourites Route
			Most Hold Ticket
			*/ 
		list.add(new AppInfo("Today Issued", setupDao.getCountFromTable(BookingFlightVO.TABLE_NAME, "(STATUS='OK' or STATUS='TKT' or STATUS='CONFIRMED') and trunc(CREATEDDATE) = trunc(sysdate)")));
		list.add(new AppInfo("Today Cancelled", setupDao.getCountFromTable(BookingFlightVO.TABLE_NAME, "(STATUS='CANCELLED' or STATUS='CANCELED' or STATUS='CANCEL' or STATUS='XX') and trunc(CREATEDDATE) = trunc(sysdate)")));
		list.add(new AppInfo("Today Hold", setupDao.getCountFromTable(BookingFlightVO.TABLE_NAME, "(STATUS='HOLD' or STATUS='CONFIRM' or STATUS='HK') and trunc(CREATEDDATE) = trunc(sysdate)")));
		list.add(new AppInfo("Today Expired", setupDao.getCountFromTable(BookingFlightVO.TABLE_NAME, "(STATUS='EXPIRED') and trunc(CREATEDDATE) = trunc(sysdate) ")));
		list.add(new AppInfo("Total Issued", setupDao.getCountFromTable(BookingFlightVO.TABLE_NAME, "STATUS='OK' or STATUS='TKT' or STATUS='CONFIRMED'")));
		list.add(new AppInfo("Total Hold", setupDao.getCountFromTable(BookingFlightVO.TABLE_NAME, "STATUS='HOLD' or STATUS='CONFIRM' or STATUS='HK'")));
		list.add(new AppInfo("Total Cancelled", setupDao.getCountFromTable(BookingFlightVO.TABLE_NAME, "STATUS='CANCELLED' or STATUS='CANCELED' or STATUS='CANCEL' or STATUS='XX'")));
		list.add(new AppInfo("Total Expired", setupDao.getCountFromTable(BookingFlightVO.TABLE_NAME, "STATUS='EXPIRED'")));
		
		Set<LabelValue> _summary = bookingFlightDao.getBookingSummaryByStatus(new String[]{"HOLD", "CONFIRM", "HK"});
		if (_summary.size() > 0){
			for (Iterator iterator = _summary.iterator(); iterator.hasNext();) {
				LabelValue labelValue = (LabelValue) iterator.next();
				
				Airline mostHoldAirline = Airline.getAirlineByCode(labelValue.getLabel());
				
				list.add(new AppInfo("Most Hold Airline", mostHoldAirline.name()));
				break;
			}
		}
		
		Set<LabelValue> _summaryFav = bookingFlightDao.getBookingFavouriteRoute();
		if (_summaryFav.size() > 0){
			for (Iterator iterator = _summaryFav.iterator(); iterator.hasNext();) {
				LabelValue labelValue = (LabelValue) iterator.next();
				
				list.add(new AppInfo("Favourites Route", labelValue.getLabel()));
				break;
			}
		}

		return list;
	}
	@Override
	public boolean isDoubleBooking(BookingFormB2B bookingForm) {
		
		boolean bDoubleBooking = false;
		
		// get flight info first
		FlightAvailSeatVO fasdepart = flightDao.getFlightAvailSeat(FlightSelect.parse(bookingForm.getDepId()).getFlightavailid());
//		Airline airline = Airline.getAirline(fasdepart.getAirline());
		
		log.debug("validating double booking depart=" + fasdepart + "\n" + bookingForm);

		// detect adult
		List<String> existPnrDepart = bookingFlightDao.isDoubleBooking(bookingForm, fasdepart);
		
		bDoubleBooking = existPnrDepart != null && existPnrDepart.size() > 0;
		
		try {
			log.debug("DOUBLE BOOKING ON DEPART ? " + bDoubleBooking + "," + existPnrDepart + ",existPnrDepart.size=" + existPnrDepart.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (!Utils.isEmpty(bookingForm.getRetId())){
			FlightAvailSeatVO fasreturn = flightDao.getFlightAvailSeat(FlightSelect.parse(bookingForm.getRetId()).getFlightavailid());

			log.debug("validating double booking return=" + fasreturn + "\n" + bookingForm);
			List<String> existPnrReturn = bookingFlightDao.isDoubleBooking(bookingForm, fasreturn);
			
			bDoubleBooking = bDoubleBooking || (existPnrReturn != null && existPnrReturn.size() > 0);
			log.debug("DOUBLE BOOKING ON RETURN ? " + bDoubleBooking);
		}
		
		
		return bDoubleBooking;
	}

}
