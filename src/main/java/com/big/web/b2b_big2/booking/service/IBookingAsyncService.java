package com.big.web.b2b_big2.booking.service;

import java.io.IOException;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;

import com.big.web.b2b_big2.booking.dao.IBookingFlightDao;
import com.big.web.b2b_big2.booking.model.BookingFlightVO;
import com.big.web.b2b_big2.finance.dao.IFinanceDao;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.setup.dao.ISetupDao;

public interface IBookingAsyncService {

	@Async("callODR_executor")
	Future<BookingFlightVO> callODR(BookingFlightVO bf, Airline airline, IBookingFlightDao bookingFlightDao, IFinanceDao financeDao, ISetupDao setupDao) throws IllegalArgumentException, IOException, InterruptedException;

}
