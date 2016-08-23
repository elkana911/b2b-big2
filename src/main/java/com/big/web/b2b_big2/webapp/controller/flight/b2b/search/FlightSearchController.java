package com.big.web.b2b_big2.webapp.controller.flight.b2b.search;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;

import com.big.web.b2b_big2.booking.service.IBookingManager;
import com.big.web.b2b_big2.finance.service.IFinanceManager;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.model.AbstractFlightClassFare;
import com.big.web.b2b_big2.flight.model.AirlineVO;
import com.big.web.b2b_big2.flight.model.FlightAvailSeatVO;
import com.big.web.b2b_big2.flight.model.ODRStatusVO;
import com.big.web.b2b_big2.flight.odr.ODRHandler;
import com.big.web.b2b_big2.flight.odr.ODR_STATE;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.flight.pojo.FareSummary;
import com.big.web.b2b_big2.flight.pojo.FlightSearchB2B;
import com.big.web.b2b_big2.flight.pojo.FlightSearchResult;
import com.big.web.b2b_big2.flight.pojo.FlightSelect;
import com.big.web.b2b_big2.flight.pojo.ROUTE_MODE;
import com.big.web.b2b_big2.flight.pojo.TripMode;
import com.big.web.b2b_big2.flight.service.IAirlineManager;
import com.big.web.b2b_big2.flight.service.IAirportManager;
import com.big.web.b2b_big2.flight.service.IFlightAsyncService;
import com.big.web.b2b_big2.flight.service.IFlightManager;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.StopWatch;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.b2b_big2.webapp.controller.BaseFormController;

public class FlightSearchController extends BaseFormController{
	@Autowired
	protected IFlightManager flightManager;

	@Autowired
	protected IAirportManager airportManager;

	@Autowired
	protected IAirlineManager airlineManager;
	
	@Autowired
	protected IBookingManager bookingManager;

	@Autowired
	protected IFinanceManager financeManager;

	@Autowired
	protected IFlightAsyncService flightAsyncService;

	public FlightSearchResult _callODR(Airline[] airlines, Date departDate, Date returnDate, String departIata, String destinationIata, int adults, int children, int infants) throws IllegalArgumentException, IOException, InterruptedException, ParseException, ExecutionException{
		List<Future<List<FlightSearchB2B>>> departWorkers = new ArrayList<Future<List<FlightSearchB2B>>>();
		List<Future<List<FlightSearchB2B>>> returnWorkers = new ArrayList<Future<List<FlightSearchB2B>>>();
		
		List<Airline> alreadyScrapListDepart = new ArrayList<Airline>();
		for (Airline airline : airlines){

			// already scrap ?
			boolean _ada = false;
			for (Airline _airline : alreadyScrapListDepart){
				if (_airline == airline) {
					_ada = true;
					break;
				}
			}
			
			if (_ada){
				continue;
			}else{
				alreadyScrapListDepart.clear();
				
				//jika satu grup ya tinggal tambahin aja
				switch (airline){
					case LION:
					case WINGS:
					case BATIK:
						alreadyScrapListDepart.add(Airline.LION);	//dianggap sebagai airline utama krn wings dan batik cuma nebeng
						alreadyScrapListDepart.add(Airline.WINGS);
						alreadyScrapListDepart.add(Airline.BATIK);
						break;
					case SRIWIJAYA:
					case NAM:
						alreadyScrapListDepart.add(Airline.SRIWIJAYA);	//dianggap sebagai airline utama krn nam cuma nebeng
						alreadyScrapListDepart.add(Airline.NAM);
						break;
					default:
						alreadyScrapListDepart.add(airline);
						break;
				}
			}
			System.err.println("Adding ODR " + alreadyScrapListDepart.get(0));

//			Future<List<FlightSearchB2B>> future = flightAsyncService.callDepartODR(airline, bookingForm.getSearchForm());
//			Future<List<FlightSearchB2B>> future = flightAsyncService.callDepartODR(airline, departDate, departIata, destinationIata, adults, children, infants);
			Future<List<FlightSearchB2B>> future = flightAsyncService.callDepartODR(alreadyScrapListDepart.get(0), departDate, departIata, destinationIata, adults, children, infants);
			
			departWorkers.add(future);
		}
		
		if (returnDate != null){
			List<Airline> alreadyScrapListReturn = new ArrayList<Airline>();
			
			for (Airline airline : airlines){
				// already scrap ?
				boolean _ada = false;
				for (Airline _airline : alreadyScrapListReturn){
					if (_airline == airline) {
						_ada = true;
						break;
					}
				}
				
				if (_ada){
					continue;
				}else{
					alreadyScrapListReturn.clear();

					//jika satu grup ya tinggal tambahin aja
					switch (airline){
						case LION:
						case WINGS:
						case BATIK:
							alreadyScrapListReturn.add(Airline.LION);
							alreadyScrapListReturn.add(Airline.WINGS);
							alreadyScrapListReturn.add(Airline.BATIK);
							break;
						case SRIWIJAYA:
						case NAM:
							alreadyScrapListReturn.add(Airline.SRIWIJAYA);
							alreadyScrapListReturn.add(Airline.NAM);
							break;
						default:
							alreadyScrapListReturn.add(airline);
							break;
					}
				}

				Future<List<FlightSearchB2B>> future = flightAsyncService.callDepartODR(alreadyScrapListReturn.get(0), returnDate, destinationIata, departIata, adults, children, infants);
//				Future<List<FlightSearchB2B>> future = flightAsyncService.callDepartODR(airline, returnDate, destinationIata, departIata, adults, children, infants);
				returnWorkers.add(future);
			}
		}
		
		// all busy ?
		boolean isDone = false;
		do{
			isDone = true;
			for (Future<?> f : departWorkers){
				isDone = isDone && f.isDone();
			}

			if (isDone)
				for (Future<?> f : returnWorkers){
					isDone = isDone && f.isDone();
				}
			
			Thread.sleep(1000);
		}while (!isDone);

		FlightSearchResult fsr = new FlightSearchResult(returnDate == null ? TripMode.ONE_WAY : TripMode.RETURN);
		
		//union
//		List<FlightSearchB2B> listDepart = new ArrayList<FlightSearchB2B>();
		for (int i = 0; i < departWorkers.size(); i++){
			List<FlightSearchB2B> list = departWorkers.get(i).get();
			if (list != null)
				fsr.getDepartList().addAll(list);
		}
		
		for (int i = 0; i < returnWorkers.size(); i++){
			List<FlightSearchB2B> list = returnWorkers.get(i).get();
			if (list != null)
				fsr.getReturnList().addAll(list);
		}

		return fsr;
	}
	
	// pastikan method ini hanya dipanggil oleh satu instance !!
	public void _callODRBackground(Airline[] airlines, Date departDate, Date returnDate, String departIata, String destinationIata, int adults, int children, int infants) throws IllegalArgumentException, IOException, InterruptedException, ParseException, ExecutionException{
		
		//dibutuhkan status di table. jika running brarti jangan diproses lagi.
		List<ODRStatusVO> odrStatusList = flightAsyncService.getODRStatus();
		
		for (Airline airline : airlines){
			
			if (airline == Airline.UNKNOWN)
				continue;
			
			//sementara handle lion grup dan airasia dulu
			switch(airline){
			case LION:
				break;
			case AIRASIA:
				break;
			
			case WINGS:
			case BATIK:
				default:
					// skip karena satu grup dengan yg ada atau belum tertangani
					continue;
			}
			
			boolean _found = false;

			for (ODRStatusVO odrStatusVO : odrStatusList) {
				if (odrStatusVO.getAirline() == airline){
					_found = true;
					break;
				}
			}

			if (!_found)
				odrStatusList.add(new ODRStatusVO(airline, ODR_STATE.STOP));
		}
		
		for (ODRStatusVO odrStatusVO : odrStatusList) {
			
			// tapi dicek lastupdatenya jika lebih dari 10 menit diset DONE aja
			if (odrStatusVO.getStatus() == ODR_STATE.RUNNING) continue;
			
			odrStatusVO.setStatus(ODR_STATE.RUNNING);		
			airlineManager.saveOdrStatus(odrStatusVO);
			
			switch (odrStatusVO.getAirline()){
		    case LION:
		    case BATIK:
		    case WINGS:
		    case AIRASIA:
		    	
		    	//permintaan pak mul nantinya bisa ambil H-1 dan H+1
				flightAsyncService.callODRBackground(odrStatusVO.getAirline(), departDate, departIata, destinationIata, adults, children, infants);
				
		    	break;
		    	
		    	default: break;
			
			}
		}
		
	}

	public FareSummary _getFareDetail(
			String[] idDepCombination,
			String[] idRetCombination, // enable javascript to sent null values
			String departDate, // hanya dipakai utk display hotel, bukan utk pencarian tiket pesawat. tiket pesawat nanti bisa dicari dari id-nya
			String destinationCity, 
			int adults,
			int children, 
			int infants) throws Exception{
		
		StopWatch sw = StopWatch.AutoStart();
		
		List<FareInfo> list = new ArrayList<FareInfo>();

		// cari baru 7 oct 15, call odr utk selected flights then retrieve from local
		boolean isCallODR = setupDao.getValueAsBoolean(ISetupDao.KEY_ODR_SEARCH_DETAIL_ENABLED);
		
		if (isCallODR){
			
			// collect airlines
			Set<Airline> _airlines = new LinkedHashSet<Airline>();
			
			Date _depTime = null;
			String _departIata = null;
			String _destIata = null;
			if (idDepCombination != null){
				for (String s : idDepCombination){
					FlightSelect fs = FlightSelect.parse(s);
					_airlines.add(fs.getAirline());
					
					FlightAvailSeatVO fas = flightManager.getFlightAvailSeat(fs.getFlightavailid());
					_depTime = fas.getDep_time();
					_departIata = fas.getOrigin();
					_destIata = fas.getDestination();
					
					// 1 data aja yg diambil
					break;
				}
			}
			
			Date _retTime = null;
			if (idRetCombination != null){
				for (String s : idRetCombination){
					FlightSelect fs = FlightSelect.parse(s);
					_airlines.add(fs.getAirline());
					
					FlightAvailSeatVO fas = flightManager.getFlightAvailSeat(fs.getFlightavailid());
					_retTime = fas.getDep_time();

					// 1 data aja yg diambil
					break;
				}
			}
			
			
			try {
				_callODR(_airlines.toArray(new Airline[_airlines.size()]), _depTime, _retTime, _departIata, _destIata
						, adults, children, infants);
			} catch (Exception e) {
			}
		}
		
		// proses setelah ini murni ambil data clean. so if any ODR needed, please run first
		
		
		// 479ec712-10e9-46b2-bbe1-6ddaa3d9bf34G0000001, 
		// cukup diambil flightavailid & seatclass saja: 479ec712-10e9-46b2-bbe1-6ddaa3d9bf34 dan G 
		ODRHandler odrHandler = ODRHandler.getInstance();
		if (idDepCombination != null){
			//must able to send/handle connected/transit flight
			
			//tapi jika radioIdnya sama berarti 1 harga saja yg ditampilkan, sisanya harganya saja di 0-in
			String lastRadioId = "";
			Set<Integer> idxRateIsSummary = new LinkedHashSet<Integer>();

			for (int i = 0; i < idDepCombination.length; i++){
				FlightSelect fs = FlightSelect.parse(idDepCombination[i]);
				
				AbstractFlightClassFare fcf = flightManager.getFlightClassFare(fs);

				if (fcf.getRadio_id() == null) continue;
				
				if (lastRadioId.equalsIgnoreCase(fcf.getRadio_id()))
					idxRateIsSummary.add(new Integer(i));
				else
					lastRadioId = fcf.getRadio_id();
			}
			
//			loopDep:
			for (int i = 0; i < idDepCombination.length; i++){
				//skip jika ada di idx
				/*
				for (Integer _i : idx) {
					if (_i.intValue() == i) continue loopDep;
				}
				*/
				
				FlightSelect fs = FlightSelect.parse(idDepCombination[i]);
// FlightAvailSeatVO fas = flightManager.getFlightAvailSeat(fs.getFlightavailid());
				
				List<FareInfo> odrResult = null;
				try {
					odrResult = odrHandler.getFareDetails(fs, adults, children, infants, flightManager, financeManager, setupDao);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					throw e;
				}
				
				if (odrResult == null) continue;
				
				//repair RouteMode & agentId & same radio Id
				for (int j = 0; j < odrResult.size(); j++){
					FareInfo element = odrResult.get(j);
					
					Airline _airline = Airline.getAirlineByCode(element.getAirlineIata());
					AirlineVO _airlineVO = airlineManager.getAirlineByCode(element.getAirlineIata());
					
					element.setAgentId(_airlineVO.getAgentId());
					element.setRouteMode(ROUTE_MODE.DEPART);
					
					//FIXME sriwijaya psc hanya berlaku di flight yg pscnya tertinggi. lha kalo nam ?
//								if (_airline == Airline.SRIWIJAYA && i > 0)
//									element.setPsc(BigDecimal.ZERO);
					
					for (Integer _i : idxRateIsSummary) {
						//airasia minta dibedain, jd jangan reset infantfarenya
						if (_airline == Airline.AIRASIA){
							BigDecimal __buf = element.getInfantFare();
							if (_i.intValue() == i) element.resetAllFare();
							element.setInfantFare(__buf);
							
						}else
							if (_i.intValue() == i) element.resetAllFare();
					}
					odrResult.set(j, element);
				} // for j
				
				list.addAll(odrResult);
			} // for i
			
			//FIXME sriwijaya psc hanya berlaku di flight yg pscnya tertinggi. lha kalo nam ?
			if (list.size() > 0){
				BigDecimal biggest = list.get(0).getPsc();  
				int biggestFareInfo = 0;
				
				for (int i = 1; i < list.size(); i++){
					if (list.get(i).getPsc().compareTo(biggest) < 0){
						biggest = list.get(i).getPsc();
						biggestFareInfo = i;
					}
				}
				
				for (int i = 0; i < list.size(); i++){
					if (i == biggestFareInfo) continue;
					
					FareInfo element = list.get(i);
					
					Airline _airline = Airline.getAirlineByCode(element.getAirlineIata());
					if (_airline == Airline.SRIWIJAYA || _airline == Airline.NAM){
						element.setPsc(BigDecimal.ZERO);
						list.set(i, element);
					}
				}
			}
		}	// if
		
		if (idRetCombination != null){

			String lastRadioId = "";
			Set<Integer> idxRateIsSummary = new LinkedHashSet<Integer>();
			
			for (int i = 0; i < idRetCombination.length; i++){
				FlightSelect fs = FlightSelect.parse(idRetCombination[i]);
				
				AbstractFlightClassFare fcf = flightManager.getFlightClassFare(fs);
				
				if (fcf.getRadio_id() != null){
					if (lastRadioId.equalsIgnoreCase(fcf.getRadio_id()))
						idxRateIsSummary.add(new Integer(i));
					else
						lastRadioId = fcf.getRadio_id();
				}
				
			}
			
			for (int i = 0; i < idRetCombination.length; i++){
				FlightSelect fs = FlightSelect.parse(idRetCombination[i]);
//				FlightAvailSeatVO fas = flightManager.getFlightAvailSeat(fs.getFlightavailid());
				
				//kenapa tidak di mark sebagai RETURN
				List<FareInfo> odrResult = null;
				try {
					odrResult = (List<FareInfo>)odrHandler.getFareDetails(fs, adults, children, infants, flightManager, financeManager, setupDao);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					throw e;
				}
				
				//need to fix/repair route
				if (odrResult == null) continue;

				//repair RouteMode
				for (int j = 0; j < odrResult.size(); j++){
					FareInfo element = odrResult.get(j);
					Airline _airline = Airline.getAirlineByCode(element.getAirlineIata());

					element.setRouteMode(ROUTE_MODE.RETURN);
					
					//FIXME sriwijaya psc hanya berlaku di flight yg pscnya tertinggi
//						if (_airline == Airline.SRIWIJAYA && i > 0)
//							element.setPsc(BigDecimal.ZERO);

					for (Integer _i : idxRateIsSummary) {
						if (_airline == Airline.AIRASIA){
							BigDecimal __buf = element.getInfantFare();
							if (_i.intValue() == i) element.resetAllFare();
							element.setInfantFare(__buf);
						}else
							if (_i.intValue() == i) element.resetAllFare();
					}

					odrResult.set(j, element);
				}// for j
				
				list.addAll(odrResult);
			}// for i
			if (list.size() > 0){
				BigDecimal biggest = list.get(0).getPsc();  
				int biggestFareInfo = 0;
				
				for (int i = 1; i < list.size(); i++){
					if (list.get(i).getPsc().compareTo(biggest) < 0){
						biggest = list.get(i).getPsc();
						biggestFareInfo = i;
					}
				}
				
				for (int i = 0; i < list.size(); i++){
					if (i != biggestFareInfo){
						FareInfo element = list.get(i);
						
						Airline _airline = Airline.getAirlineByCode(element.getAirlineIata());
						if (_airline == Airline.SRIWIJAYA || _airline == Airline.NAM){
							element.setPsc(BigDecimal.ZERO);
							list.set(i, element);
						}
						
					}
				}
			}

			
		}
		
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal tax = BigDecimal.ZERO;
		BigDecimal psc = BigDecimal.ZERO;
		BigDecimal iwjr = BigDecimal.ZERO;
		BigDecimal surcharge = BigDecimal.ZERO;
		BigDecimal baseFareTotal = BigDecimal.ZERO;
		
		for (FareInfo fi : list){
			tax = tax.add(fi.getTax_adult() == null ? BigDecimal.ZERO : fi.getTax_adult())
					.add(fi.getTax_child() == null ? BigDecimal.ZERO : fi.getTax_child())
					.add(fi.getTax_infant() == null ? BigDecimal.ZERO : fi.getTax_infant())
					;

			psc = psc.add(fi.getPsc() == null ? BigDecimal.ZERO : fi.getPsc());
			iwjr = iwjr.add(fi.getIwjr() == null ? BigDecimal.ZERO : fi.getIwjr());
			surcharge = surcharge.add(fi.getFuelSurcharge() == null ? BigDecimal.ZERO : fi.getFuelSurcharge());
			
			total = total.add(fi.getRate() == null ? BigDecimal.ZERO : fi.getRate());
		}
		baseFareTotal.add(total);

		FareSummary summary = new FareSummary();
		

		summary.setFareInfo(list);

		try {
			if (Utils.isEmpty(departDate)){
				departDate = Utils.today();
			}
			summary.setDepartDate(Utils.prettyDate(new SimpleDateFormat("dd/MM/yyyy").parse(departDate)));
		} catch (ParseException e) {
			System.err.println("Error Parsing date for " + departDate);
			e.printStackTrace();
			summary.setDepartDate("Invalid Date");
		}
		

		DecimalFormat formatter = new DecimalFormat("###,###,###.##");

		summary.setPsc(formatter.format(psc.doubleValue()));
		summary.setTax(formatter.format(tax.doubleValue()));
		summary.setIwjr(formatter.format(iwjr.doubleValue()));
		summary.setSurcharge(formatter.format(surcharge.doubleValue()));
		
		total = total.add(tax)
				.add(psc)
				.add(iwjr)
				.add(surcharge)
				;
		summary.setTotal(formatter.format(total.doubleValue()));

		summary.setDestinationCity(airportManager.getCity(destinationCity));

		System.err.println(summary.toString());
		
		log.info("Get Fare Detail took " + sw.stopAndGetAsString());
		
		return summary;
	}

}
