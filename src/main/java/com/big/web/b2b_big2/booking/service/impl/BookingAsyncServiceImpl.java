package com.big.web.b2b_big2.booking.service.impl;

import java.io.IOException;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.big.web.b2b_big2.booking.dao.IBookingFlightDao;
import com.big.web.b2b_big2.booking.model.BookingFlightTransVO;
import com.big.web.b2b_big2.booking.model.BookingFlightVO;
import com.big.web.b2b_big2.booking.service.IBookingAsyncService;
import com.big.web.b2b_big2.finance.dao.IFinanceDao;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.odr.ODRHandler;
import com.big.web.b2b_big2.setup.dao.ISetupDao;

@Component
@Transactional
public class BookingAsyncServiceImpl implements IBookingAsyncService {
	private static final Log log = LogFactory.getLog(BookingAsyncServiceImpl.class);

	/**
	 * Warning ! @Async tidak akan dijalan bila dipanggil dari class yg sama, dan juga ga boleh private
	 * baca http://www.kgasoft.com/spring-async-method-not-working/
	 * utk penggunaan Future, read https://spring.io/guides/gs/async-method/
	 * 
	 * @param airline
	 * @throws Exception 
	 */

	@Override
	@Async("callODR_executor")
	public Future<BookingFlightVO> callODR(BookingFlightVO bf, Airline airline, IBookingFlightDao bookingFlightDao, IFinanceDao financeDao, ISetupDao setupDao) throws IllegalArgumentException, IOException, InterruptedException{
		
		/*
		log.debug("[" + airline + "] start " + Thread.currentThread().getName() + " " + new Date());
		try {
            Thread.sleep(10000);
            Thread.currentThread().interrupt();
        }	
		log.debug("[" + airline + "] end "  + Thread.currentThread().getName() + " " + new Date());
		*/
		
		ODRHandler odr = new ODRHandler();
		
		try {
			odr.booking(setupDao, airline, bf.getId());
		} catch (IllegalArgumentException | IOException e) {
			throw e;
		}
		
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
//			_bfTrans.setAfter_amount(_buffer.getAmount());
			_bfTrans.setAfter_basefare(after_basefare);
			_bfTrans.setAfter_iwjr(after_iwjr);
			_bfTrans.setAfter_psc(after_psc);
			_bfTrans.setAfter_tax(after_tax);
			 */
			bookingFlightDao.saveBookingFlightTrans(_bfTrans);
		}
		
		/*
		groupCode.add(_buffer.getCode());
		
		list.set(i, _buffer);
*/
		
		return new AsyncResult<BookingFlightVO>(_buffer);

	}

}
