package com.big.web.b2b_big2.flight.odr;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.big.web.b2b_big2.finance.model.CommissionVO;
import com.big.web.b2b_big2.finance.service.IFinanceManager;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.api.citilink.Citilink_Handler;
import com.big.web.b2b_big2.flight.api.kalstar.SqivaHandler;
import com.big.web.b2b_big2.flight.api.kalstar.action.fare.FareDetail;
import com.big.web.b2b_big2.flight.model.FlightAvailSeatVO;
import com.big.web.b2b_big2.flight.odr.exception.ODRException;
import com.big.web.b2b_big2.flight.odr.ws.BookingFlight;
import com.big.web.b2b_big2.flight.odr.ws.Service1;
import com.big.web.b2b_big2.flight.odr.ws.Service1Soap;
import com.big.web.b2b_big2.flight.pojo.Airport;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.flight.pojo.FlightSelect;
import com.big.web.b2b_big2.flight.pojo.Route;
import com.big.web.b2b_big2.flight.service.IFlightManager;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.NetUtils;
import com.big.web.b2b_big2.util.Utils;

/**
 * Citilink will call VWR job
 * Kalstar will call API
 * 
 * @author Administrator
 *
 */
public class ODRHandler {
	public static ODRHandler self = null;

	private static final Log log = LogFactory.getLog(ODRHandler.class);
//	private IFlightManager iFlightManager;
	
	public static ODRHandler getInstance(){
		if (self == null){
			self = new ODRHandler();
//			self.iFlightManager = flightMgr;
		}
		
		return self;
	}
	
	public void booking(ISetupDao setupDao, Airline airline, String bookingFlightUid) throws IllegalArgumentException, MalformedURLException, IOException{
		log.debug("try to booking " + airline + ",id=" + bookingFlightUid);
		
		switch(airline){
		case AIRASIA:
			//berbeda dengan ODR yg belum bisa booking airasia krn ditemukan kelemehan di ODR
			
			Hashtable<String, String> keyValue = new Hashtable<String, String>();
			keyValue.put("bookingId", bookingFlightUid);
			
			String _url = NetUtils.buildUrlParam(setupDao.getValue(ISetupDao.KEY_ODR_JAVA_URL), keyValue);
			
			String _data = NetUtils.getFromServer(_url);
			
			log.debug("----->Airasia Booking return " + _data);
			break;
		case SRIWIJAYA:
		case NAM:
		case LION:
		case BATIK:
		case WINGS:
		case GARUDA:
		case MALINDO:
		case EXPRESS:
		case CITILINK:
				Service1 svc = new Service1();
				Service1Soap soap = svc.getService1Soap();
				
	//			BookingFlight bookDetail = new BookingFlight();
				/*
				bookDetail.setAirlineIata(value);
				bookDetail.setBookAmount(value);
				bookDetail.setBookID(value);
				bookDetail.setBookingCode(value);
				bookDetail.setDepDate(value);
				bookDetail.setErrorCode(value);
				bookDetail.setFareSellKey(value);
				bookDetail.setIsReturn(value);
				bookDetail.setJourneyKey(value);
				bookDetail.setOrigin(value);
				bookDetail.setPasenger(value);
				bookDetail.setPassCount(value);
				bookDetail.setRadioId(value);
				bookDetail.setReturnDate(value);
				
				soap.getBooking(criteria, bookDetail);
				
				*/
				BookingFlight ret = soap.commitBooking(bookingFlightUid);
				/*
	//			soap.commitBooking(b3a017cd-7dab-4327-a8d7-0006c4a8be69) return [BookingFlight [
	  					bookID=b3a017cd-7dab-4327-a8d7-0006c4a8be69
	 					, carrierCode=QG
	 					, origin=CGK
	 					, destination=BTH
	 					, departureDate=25/02/2015 00:00:
	 					, fareSellKey=0~M~~M~RGFR~~6~X
	 					, passCount=0
	 					, journeyKey=QG~9571~ ~~CGK~02/25/2015 10:30~BTH~02/25/2015 12:10~
	 					, bookingCode=KEKKNB
	 					, bookingAmount=821500.0
	 					, errorCode=0, timeToPay=26/02/2015 03:30:00, bookStatus=Hold]]
				
				*/
				
				log.debug("commitbooking return " + ret);
				
				if (ret.getErrorCode().equals("0")){
//					bookingFlightDao.
				}else{
					log.error("Error Booking return " + ret);
					throw new ODRException("Booking Failed (errorCode=" + ret.getErrorCode() + ")");
				}
				
			break;
		default:
			throw new ODRException("Cannot book " + airline);
		}
	}
	
	public void issued(ISetupDao setupDao, Airline airline, String bookingCode) throws IllegalArgumentException, MalformedURLException, IOException{
		log.debug("try to issued " + airline + ",code=" + bookingCode);
		
		switch(airline){
		case AIRASIA:
			//berbeda dengan ODR yg belum bisa booking airasia krn ditemukan kelemehan di ODR
			
			Hashtable<String, String> keyValue = new Hashtable<String, String>();
			keyValue.put("bookingCode", bookingCode);
			
			String _url = NetUtils.buildUrlParam(setupDao.getValue(ISetupDao.KEY_ODR_JAVA_URL), keyValue);
			
			String _data = NetUtils.getFromServer(_url);
			
			log.debug("----->Airasia Issued return " + _data);
			break;
		case SRIWIJAYA:
		case NAM:
		case LION:
		case BATIK:
		case WINGS:
		case GARUDA:
		case MALINDO:
		case EXPRESS:
		case CITILINK:
			Service1 svc = new Service1();
			Service1Soap soap = svc.getService1Soap();

			BookingFlight ret = soap.confirmBooking(bookingCode);
			
			log.debug("commitissued return " + ret);
			
			if (ret.getErrorCode().equals("0")){
//					bookingFlightDao.
			}else{
				log.error("Error Issued return " + ret);
				throw new ODRException("Issued Failed (errorCode=" + ret.getErrorCode() + ")");
			}
			
			break;
		default:
			throw new ODRException("Cannot issue " + airline);
		}
	}
	
	/*
	 * cara kerjanya meng-update flightclassfare dengan price yg baru
	 * akan menampilkan update harga dr airline utk ditampilkan di searching result.
	 */
//	public Object getFareDetails(String originIata, String destIata, Date departDate, Airline airline, String flightNo) throws Exception{
	public List<FareInfo> getFareDetails(FlightSelect flightSelect, int adultCount, int childCount, int infantCount, IFlightManager flightManager, IFinanceManager financeManager, ISetupDao setupDao) throws Exception{
		
		List<FareInfo> list = new ArrayList<FareInfo>();

		FlightAvailSeatVO fas = flightManager.getFlightAvailSeat(flightSelect.getFlightavailid());
		Airline airline = Airline.getAirline(fas.getAirline());

		int sumTotal = 0;
		int sumBasicFare = 0;
		int sumFuelSurcharge = 0;
		int sumInsurance = 0;
		int sumVatAdult = 0;
		int sumVatChild = 0;
		int sumVatInfant = 0;
		int sumIwjr = 0;
		int sumPsc = 0;
		int sumIssuedFee = 0;
		
		//diatur sesuai aturan dari masing2 airline
		switch (airline){
		case KALSTAR:
		case AVIASTAR:
		case TRIGANA:
			SqivaHandler sqivaHandler = new SqivaHandler(airline, setupDao);
			//beda dengan citilink, we use fas.departure and fas.arrival
			List<FareDetail> fareDtlList = sqivaHandler.getFareDetails(fas.getDeparture(), fas.getArrival(), fas.getDep_time(), fas.getFlightnum());
			
			for (FareDetail fareDetail : fareDtlList) {
				//TODO di production please handle for multiple class such as A/A and A/RT  <--hanya terlihat di development
				if (fareDetail.getClassName().equalsIgnoreCase(flightSelect.getSeat().getKelas()) ){
					FareInfo fi = new FareInfo();
					
					fi.setAdultPax(adultCount);
					fi.setChildrenPax(childCount);
					fi.setInfantPax(infantCount);
					
					fi.setAirline(airline.getAirlineName());
					fi.setAirlineIata(airline.getAirlineCode());
					fi.setCurrency(fareDetail.getCcy());
					fi.setClassName(String.valueOf(fareDetail.getClassName()));
					fi.setBaseFare(fareDetail.getBaseFare());
					fi.setFlightNo(fareDetail.getFlightNo());
					
					sumTotal += fareDetail.getAdultFare().getTotalFare() * adultCount;
					sumTotal += fareDetail.getChildFare().getTotalFare() * childCount;
					sumTotal += fareDetail.getInfantFare().getTotalFare() * infantCount;

					sumBasicFare += fareDetail.getAdultFare().getBasicFare() * adultCount;
					sumBasicFare += fareDetail.getChildFare().getBasicFare() * childCount;
					sumBasicFare += fareDetail.getInfantFare().getBasicFare() * infantCount;

					sumFuelSurcharge += fareDetail.getAdultFare().getSurcharge() * adultCount;
					sumFuelSurcharge += fareDetail.getChildFare().getSurcharge() * childCount;
					sumFuelSurcharge += fareDetail.getInfantFare().getSurcharge() * infantCount;

					//di kalstar, insurance selalu ada dan jumlahnya 5000. itu brarti IWJR !
					sumIwjr += fareDetail.getAdultFare().getInsurance() * adultCount;
					sumIwjr += fareDetail.getChildFare().getInsurance() * childCount;
					sumIwjr += fareDetail.getInfantFare().getInsurance() * infantCount;

					sumVatAdult += fareDetail.getAdultFare().getVat() * adultCount;
					sumVatChild += fareDetail.getChildFare().getVat() * childCount;
					sumVatInfant += fareDetail.getInfantFare().getVat() * infantCount;

					sumPsc += fareDetail.getAdultFare().getAirportTax() * adultCount;
					sumPsc += fareDetail.getChildFare().getAirportTax() * childCount;
					sumPsc += fareDetail.getInfantFare().getAirportTax() * infantCount;

					fi.setRate(new BigDecimal(sumBasicFare));
					fi.setPsc(new BigDecimal(sumPsc));
					fi.setFuelSurcharge(new BigDecimal(sumFuelSurcharge));
					fi.setTax_adult(new BigDecimal(sumVatAdult));
					fi.setTax_child(new BigDecimal(sumVatChild));
					fi.setTax_infant(new BigDecimal(sumVatInfant));
					fi.setInsurance(new BigDecimal(sumInsurance));
					fi.setIwjr(new BigDecimal(sumIwjr));
					fi.setAmount(new BigDecimal(sumTotal));

					Airport _from = new Airport(null, null, fas.getDeparture(), fas.getDep_time());
					Airport _to = new Airport(null, null, fas.getArrival(), fas.getArr_time());
					
					fi.setRoute(new Route(_from, _to));
//					fi.setRouteMode(DepartOrReturn.DEPART);		forbid
					fi.setFlightNo(fas.getFlightnum());

					fi.setUpdateCode(fas.getUpdatecode());
					fi.setJourneyKey(fas.getJourney_sell_key());

					list.add(fi);
				}
			}
						
			break;
		case CITILINK:
			/*
			 * 9 mar 2015 request bu fini jangan odr dulu, cukup dipakai di booking saja
			Service1 svc = new Service1();
			Service1Soap soap = svc.getService1Soap();

			String sDepartDate = Utils.Date2String(fas.getDep_time(), "dd/MM/yyyy");
			System.out.println("ODR try to updateSchedule for " + fas.getDeparture() + "-" + fas.getDestination() + "," + sDepartDate + " for " + airline.getAirlineCode());
			
			Object ret = soap.updateSchedule(fas.getDeparture(), fas.getDestination(), sDepartDate, airline.getAirlineCode());
//			String ret = "Schedule Updated";
			System.out.println("ODR return message:" + ret);
			 */
			
			//ambil dari database
			/*
			FareInfo fareInfo1 = flightManager.getFareInfo(flightSelect.getFlightavailid(), flightSelect.getSeat().getKelas());
			fareInfo1.setRouteMode(DepartOrReturn.DEPART);
			list.add(fareInfo1);
			 */
		default:
			//ambil dari database dengan asumsi proses internet sukses mengupdate harga di database
			
			FareInfo fareInfo2 = flightManager.getFareInfo(flightSelect.getFlightavailid(), flightSelect.getSeat().getKelas());

			if (airline == Airline.SRIWIJAYA){
				fareInfo2 = Sriwijaya_Handler.getInstance(setupDao).calculateFareInfo(fareInfo2, adultCount, childCount, infantCount);
			}else
			if (airline == Airline.NAM){
				fareInfo2 = Nam_Handler.getInstance(setupDao).calculateFareInfo(fareInfo2, adultCount, childCount, infantCount);
			}else
			if (airline == Airline.CITILINK){
				fareInfo2 = Citilink_Handler.getInstance(setupDao).calculateFareInfo(fareInfo2, adultCount, childCount, infantCount);
			}else 
			if (airline == Airline.GARUDA){
				fareInfo2 = Garuda_Handler.getInstance(setupDao).calculateFareInfo(fareInfo2, adultCount, childCount, infantCount);
			}else 
			if (airline == Airline.AIRASIA){
				fareInfo2 = AirAsia_Handler.getInstance(setupDao).calculateFareInfo(fareInfo2, adultCount, childCount, infantCount);
			}else 
			if (airline == Airline.EXPRESS){
				fareInfo2 = Xpress_Handler.getInstance(setupDao).calculateFareInfo(fareInfo2, adultCount, childCount, infantCount);
			}else{
				System.err.println("getFareDetails:" + fareInfo2);
				sumBasicFare += Utils.convertToInt(fareInfo2.getRate(), airline) * adultCount;
				sumBasicFare += Utils.convertToInt(fareInfo2.getChildFare(), airline) * childCount;
				sumBasicFare += Utils.convertToInt(fareInfo2.getInfantFare(), airline) * infantCount;
				
				sumFuelSurcharge += Utils.convertToInt(fareInfo2.getFuelSurcharge(), airline) * adultCount;
				sumFuelSurcharge += Utils.convertToInt(fareInfo2.getFuelSurcharge(), airline) * childCount;
				sumFuelSurcharge += Utils.convertToInt(fareInfo2.getFuelSurcharge(), airline) * infantCount;
				
				sumInsurance += Utils.convertToInt(fareInfo2.getInsurance(), airline) * adultCount;
				sumInsurance += Utils.convertToInt(fareInfo2.getInsurance(), airline) * childCount;
				sumInsurance += Utils.convertToInt(fareInfo2.getInsurance(), airline) * infantCount;
				
				sumVatAdult += Utils.convertToInt(fareInfo2.getTax_adult(), airline) * adultCount;
				sumVatChild += Utils.convertToInt(fareInfo2.getTax_child(), airline) * childCount;
				sumVatInfant += Utils.convertToInt(fareInfo2.getTax_infant(), airline) * infantCount;
				
				sumPsc += Utils.convertToInt(fareInfo2.getPsc(), airline) * adultCount;
				sumPsc += Utils.convertToInt(fareInfo2.getPsc(), airline) * childCount;
				sumPsc += Utils.convertToInt(fareInfo2.getPsc(), airline) * infantCount * 0;	//biasanya free

				sumIwjr += Utils.convertToInt(fareInfo2.getIwjr(), airline) * adultCount;
				sumIwjr += Utils.convertToInt(fareInfo2.getIwjr(), airline) * childCount;
				sumIwjr += Utils.convertToInt(fareInfo2.getIwjr(), airline) * infantCount;
				
				sumTotal += Utils.convertToInt(fareInfo2.getAmount(), airline) * adultCount;
				sumTotal += Utils.convertToInt(fareInfo2.getAmount(), airline) * childCount;
				sumTotal += Utils.convertToInt(fareInfo2.getAmount(), airline) * infantCount;
				
				fareInfo2.setRate(new BigDecimal(sumBasicFare));
				fareInfo2.setPsc(new BigDecimal(sumPsc));
				fareInfo2.setFuelSurcharge(new BigDecimal(sumFuelSurcharge));
				fareInfo2.setTax_adult(new BigDecimal(sumVatAdult));
				fareInfo2.setTax_child(new BigDecimal(sumVatChild));
				fareInfo2.setTax_infant(new BigDecimal(sumVatInfant));
				fareInfo2.setInsurance(new BigDecimal(sumInsurance));
				fareInfo2.setIwjr(new BigDecimal(sumIwjr));
				fareInfo2.setAmount(new BigDecimal(sumTotal));
			}

			fareInfo2.setUpdateCode(fas.getUpdatecode());
			fareInfo2.setJourneyKey(fas.getJourney_sell_key());
			fareInfo2.setAdultPax(adultCount);
			fareInfo2.setChildrenPax(childCount);
			fareInfo2.setInfantPax(infantCount);
			
			//calculate issuedfee, netcomm etc using BIG_TRANS.COMMISSION
			CommissionVO _commVO = financeManager.getCommission(airline, fareInfo2);
			BigDecimal issuedFee = BigDecimal.ZERO.add(_commVO.getIssued_fee()
															  .multiply(new BigDecimal(fareInfo2.getAdultPax() + fareInfo2.getChildrenPax())));

			fareInfo2.setIssuedFee(issuedFee);
			
			list.add(fareInfo2);

			break;
		}
		
		return list;
	}
	
	
}
