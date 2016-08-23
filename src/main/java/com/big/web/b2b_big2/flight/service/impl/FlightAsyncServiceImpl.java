package com.big.web.b2b_big2.flight.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.dao.IAirlineDao;
import com.big.web.b2b_big2.flight.dao.IFlightDao;
import com.big.web.b2b_big2.flight.model.ODRStatusVO;
import com.big.web.b2b_big2.flight.odr.ODR_STATE;
import com.big.web.b2b_big2.flight.odr.ws.Service1;
import com.big.web.b2b_big2.flight.odr.ws.Service1Soap;
import com.big.web.b2b_big2.flight.pojo.FlightSearchB2B;
import com.big.web.b2b_big2.flight.pojo.OriginDestinationRaw;
import com.big.web.b2b_big2.flight.service.IFlightAsyncService;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.NetUtils;
import com.big.web.b2b_big2.util.StopWatch;
import com.big.web.b2b_big2.util.Utils;

@Component
@Transactional
public class FlightAsyncServiceImpl implements IFlightAsyncService {
	private static final Log log = LogFactory.getLog(FlightAsyncServiceImpl.class);
	
	@Autowired IAirlineDao airlineDao;
	@Autowired IFlightDao flightDao;
	@Autowired ISetupDao setupDao;
	
	/**
	 * 
	 * @param airline
	 * @param departDate
	 * @param departIata
	 * @param destinationIata
	 * @param adults
	 * @param children
	 * @param infants unused for now
	 * @return
	 * @throws IllegalArgumentException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	private Future<List<FlightSearchB2B>> process(Airline airline, Date departDate, String departIata, String destinationIata, int adults, int children, int infants){
		List<FlightSearchB2B> data = null;

		//forbid ?
		if (setupDao.getValueAsBoolean(airline.name().toLowerCase() + ".search.forbid"))
			return new AsyncResult<List<FlightSearchB2B>>(data);
		
		int ageDataMinute = setupDao.getValueAsInt(ISetupDao.KEY_ODR_DATA_AGE_MINUTES);
		//sementara dihandle tdk boleh di bawah 2 menit krn proses odr membutuhkan waktu 2 menit
		if (ageDataMinute < 2) ageDataMinute = 2;
		// ambil offline or odr here
		
		try {
			
			System.err.println("Process ODR " + airline);
			data = flightDao.searchOneWayFlightB2B(departDate, departIata, destinationIata, airline);

			//scan seluruh data apakah ada data usang ? jika iya ambil flightavailidnya
			// jika ada pertanyaan mengapa bisa berkali2 dipanggil padahal cukup 1 saja, harusnya ODR mengupdate semua schedule di hari yang sama supaya tidak diambil lagi.
			long updateIncrement = 0;
			
			String strDepartDate = Utils.Date2String(departDate, "dd-MM-yyyy");
			for (int i = 0; i < data.size(); i++){
				
				final FlightSearchB2B row = data.get(i);
				
				// tambah kontrol jika lain kali ada data > 1. saat ini diakhir loop i akan di break utk memastikan process di jaga cuma sekali saja
				if (row.getLastUpdate() != null){
					Date threshold = Utils.addMinutes(row.getLastUpdate(), ageDataMinute);
					// logikanya next row jika terulang akan lolos krn baru saja update. tp ya ga bakal lolos, kan isi row.getLastUpdate itu pasti blm sinkron dengan database di iterasi ke dua.
					// jadi setelah diskusi dengan tim scraping, diambil per airline 1 row saja
					if (!new Date().after(threshold)) continue;
				}

				updateIncrement += 1;
				
				log.info("[" + (i+1) + "/" + data.size() +"]" + row.getAirline().getAirlineName() + " > flightAvailId=" + row.getFlightAvailId() + " too old (" + row.getLastUpdate() +") updateCounter=" + updateIncrement);
				
				/*
				Air Asia
				http://localhost:8080/RunVWRProject/RunVWRProject?mode=SCHED&airline=QZ&org=CGK&dst=SUB&date=08/10/2015&adt=3

tanya verry yg dimaksud day 9 itu september atau october ? kan mon menunjukkan oct.
				Lion Air
				http://localhost:8080/RunVWRProject/RunVWRProject?mode=SCHED&airline=JT&org=Jakarta%20(CGK)&dst=Surabaya%20(SUB)&mon=Oct%202015&day=9&adt=3
				 */
				
				boolean useWebService = airline == Airline.SRIWIJAYA || airline == Airline.NAM;
				
				if (useWebService){
					StopWatch sw = StopWatch.AutoStart();				

					Service1 svc = new Service1();
					Service1Soap soap = svc.getService1Soap();
					
					// DD-MM-YYYY atau DD/MM/YYYY (13/10/2015)
					String ret = null;
					String strPassenger = String.valueOf(adults + children);
					switch (row.getAirline()){
						case LION:
						case BATIK:
						case WINGS:
							ret = soap.runODRLionAir(departIata, destinationIata, strDepartDate, strPassenger);
							break;
						case SRIWIJAYA:
						case NAM:
							ret = soap.runODRSriwijaya(departIata, destinationIata, strDepartDate, strPassenger);
							break;
						default:break;
					}
					
					log.info("----->" + row.getAirline().getAirlineName() + " ODR WebService RETURN " + ret + "\n" + sw.stopAndGetAsString());				
					
				}else{
					OriginDestinationRaw orgDestRaw = null;
					
					Hashtable<String, String> keyValue = new Hashtable<String, String>();
					keyValue.put("mode", "SCHED");
					keyValue.put("faid", row.getFlightAvailId());
					keyValue.put("airline", row.getAirline().getAirlineCode());
					keyValue.put("lastUpdate", Utils.Date2String(row.getLastUpdate(), "yyyyMMddHHmmss"));

					switch(row.getAirline()){
						case AIRASIA:
//					http://localhost:8080/RunVWRProject/RunVWRProject?mode=SCHED&airline=QZ&org=CGK&dst=SUB&date=08/10/2015&adt=3
							orgDestRaw = new OriginDestinationRaw(departIata, destinationIata);
							
							keyValue.put("date", Utils.Date2String(row.getDepartTime(), "dd/MM/yyyy"));
							break;
						case LION:
						case BATIK:
						case WINGS:
//					http://localhost:8080/RunVWRProject/RunVWRProject?mode=SCHED&airline=JT&org=Jakarta%20(CGK)&dst=Surabaya%20(SUB)&mon=Oct%202015&day=9&adt=3
							
							//replace, krn 3 airline tersebut menggunakan scraping lion saja
							keyValue.put("airline", Airline.LION.getAirlineCode());
							
							// sementara hardcode
							long webId = 1;
							
							// konversi orgiata dan destiata ke versi web/scrapingnya. untungnya dulu pernah kuambil rute2nya wkt coba selenium
							orgDestRaw = flightDao.getOrgDestFromRouteSave(departIata, destinationIata, webId);
							
							keyValue.put("day", Utils.Date2String(row.getDepartTime(), "d"));
							String mon = Utils.Date2String(row.getDepartTime(), "MMM yyyy").toLowerCase(); 
							mon = Utils.capitalizeWords(mon);
							keyValue.put("mon", mon);

							break;
						case SRIWIJAYA:
						case NAM:

							keyValue.put("airline",	Airline.SRIWIJAYA.getAirlineCode());
							orgDestRaw = new OriginDestinationRaw(departIata, destinationIata);
							
							break;
						default: 
							orgDestRaw = new OriginDestinationRaw(departIata, destinationIata);
							
							break;
					}

					keyValue.put("org", orgDestRaw.getOrigin());
					keyValue.put("dst", orgDestRaw.getDestination());
					keyValue.put("adt", String.valueOf(adults + children));
					
					String _url = NetUtils.buildUrlParam(setupDao.getValue(ISetupDao.KEY_ODR_JAVA_URL), keyValue);
					
					log.info("[" + (i+1) + "/" + data.size() +"]" + row.getAirline().getAirlineName() + " > url=" + _url);
					
					final StopWatch sw = StopWatch.AutoStart();			
					
					// 3 minutes timeout
					String resp = NetUtils.getFromServer(_url, 3);
					
					System.out.println("----->" + sw.stopAndGetAsString() + ":" + resp);
					
				}
				
				break;	//cukup sekali saja processnya saat ini (1 airline 1 process)
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			/*
			//fresh from db again. Bahaya ! karena parameternya cuma satu airline, jika 1 grup atau transit tidak akan muncul
			data.clear(); data = null;
			data = flightDao.searchOneWayFlightB2B(departDate, departIata, destinationIata, airline);
			*/
			
		}
		
		// yg hasilnya kosong maka akan manggil odr berdasar hasil pencarian saja
		// tanya very harusnya yg diambil schedule dalam 1 hari
		// kok kayanya kalo gini bisa lama juga ga mungkin 2 menit ?
		
		return new AsyncResult<List<FlightSearchB2B>>(data);	// data berisi data lama so you might want to refresh manually		
	}
	
	@Override
	@Async("callODR_executor")
	public Future<List<FlightSearchB2B>> callDepartODR(Airline airline, Date departDate, String departIata, String destinationIata, int adults, int children, int infants){			
		return process(airline, departDate, departIata.toUpperCase(), destinationIata.toUpperCase(), adults, children, infants);

	}

	@Override
	public void saveOdrStatus(ODRStatusVO obj) {
		airlineDao.saveOdrStatus(obj);
	}

	@Override
	@Async
	public Future<ODRStatusVO> callODRBackground(Airline airline, Date departDate,
			String departIata, String destinationIata, int adults,
			int children, int infants){
		ODRStatusVO data = null;
		
		//forbid ?
		if (setupDao.getValueAsBoolean(airline.name().toLowerCase() + ".search.forbid"))
			return new AsyncResult<ODRStatusVO>(data);
		
		//update in progress
		data = new ODRStatusVO(airline, ODR_STATE.RUNNING);
		airlineDao.saveOdrStatus(data);
		
		try {
			boolean useWebService = false;
			if (useWebService){
				
			}else{
				OriginDestinationRaw orgDestRaw = null;
				
				Hashtable<String, String> keyValue = new Hashtable<String, String>();
				keyValue.put("mode", "SCHED");
				keyValue.put("faid", "N/A");
				keyValue.put("airline", airline.getAirlineCode());
				keyValue.put("lastUpdate", "N/A");

				switch(airline){
					case AIRASIA:
//					http://localhost:8080/RunVWRProject/RunVWRProject?mode=SCHED&airline=QZ&org=CGK&dst=SUB&date=08/10/2015&adt=3
						orgDestRaw = new OriginDestinationRaw(departIata, destinationIata);
						
						keyValue.put("date", Utils.Date2String(departDate, "dd/MM/yyyy"));
						break;
					case LION:
					case BATIK:
					case WINGS:
//					http://localhost:8080/RunVWRProject/RunVWRProject?mode=SCHED&airline=JT&org=Jakarta%20(CGK)&dst=Surabaya%20(SUB)&mon=Oct%202015&day=9&adt=3
						
						//replace, krn 3 airline tersebut menggunakan scraping lion saja
						keyValue.put("airline", Airline.LION.getAirlineCode());
						
						// sementara hardcode
						long webId = 1;
						
						// konversi orgiata dan destiata ke versi web/scrapingnya. untungnya dulu pernah kuambil rute2nya wkt coba selenium
						orgDestRaw = flightDao.getOrgDestFromRouteSave(departIata, destinationIata, webId);
						
						keyValue.put("day", Utils.Date2String(departDate, "d"));
						String mon = Utils.Date2String(departDate, "MMM yyyy").toLowerCase(); 
						mon = Utils.capitalizeWords(mon);
						keyValue.put("mon", mon);

						break;
					default: 
						orgDestRaw = new OriginDestinationRaw(departIata, destinationIata);
						break;
				}

				keyValue.put("org", orgDestRaw.getOrigin());
				keyValue.put("dst", orgDestRaw.getDestination());
				keyValue.put("adt", String.valueOf(adults + children));
				
				String _url = NetUtils.buildUrlParam(setupDao.getValue(ISetupDao.KEY_ODR_JAVA_URL), keyValue);
				
				final StopWatch sw = StopWatch.AutoStart();			
				
				// 3 minutes timeout
				String resp = null;
				
				resp = NetUtils.getFromServer(_url, 3);
				//Thread.sleep(1 * 60 * 1000);	//coba tidur 10 menit
				
				System.out.println("----->ODRBackground for " +airline + ":" + sw.stopAndGetAsString() + ":" + resp);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//update done
			data.setLastUpdate(new Date());
			data.setStatus(ODR_STATE.STOP);
			airlineDao.saveOdrStatus(data);
		}

		return new AsyncResult<ODRStatusVO>(data);		
	}

	@Override
	public List<ODRStatusVO> getODRStatus() {
		return airlineDao.getODRStatus();
	}

	@Override
	public void saveOdrStatus(List<ODRStatusVO> list) {
		airlineDao.saveOdrStatus(list);
	}

}
