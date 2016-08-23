package com.big.web.b2b_big2.flight.api.kalstar;

import java.util.List;

import com.big.web.b2b_big2.booking.dao.IBookingFlightDao;
import com.big.web.b2b_big2.booking.model.BookingFlightDtlVO;
import com.big.web.b2b_big2.booking.model.BookingFlightVO;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.BasicAirlineHandler;
import com.big.web.b2b_big2.flight.IAirlineHandler;
import com.big.web.b2b_big2.flight.api.kalstar.action.balance.ResponseGetBalance;
import com.big.web.b2b_big2.flight.api.kalstar.action.payment.ResponsePayment;
import com.big.web.b2b_big2.flight.api.kalstar.action.payment.TicketUnit;
import com.big.web.b2b_big2.flight.api.kalstar.action.sendticket.ResponseSendTicket;
import com.big.web.b2b_big2.flight.api.kalstar.exception.SqivaServerException;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.Utils;

public class Sqiva_Handler extends BasicAirlineHandler implements IAirlineHandler {
	//karena dipakai oleh banyak airline, ga bisa pake self
	/*
	private static KalstarHandler self = null;
	public static KalstarHandler getInstance(Airline airline, ISetupDao setupDao){
		if (self == null)
			self = new KalstarHandler(airline, setupDao);
		
		return self;
	}
	*/
	
	/**
	 * 
	 * @param airline Soalnya bisa dipakai oleh airline yg kerjasama dengan Sqiva
	 * @param setupDao
	 */
	public Sqiva_Handler(Airline airline, ISetupDao setupDao) {
		super(airline, setupDao);
	}

	/**
	 * Kalstar hanya menggunakan first name dan last name
	 */
	private boolean matchPnr(BookingFlightDtlVO bookingFlightDtlVO, String ticketName){
		StringBuffer sb = new StringBuffer(Utils.nvl(bookingFlightDtlVO.getPassenger_first_name(), "").trim())
		.append(" ").append(Utils.nvl(bookingFlightDtlVO.getPassenger_last_name(), "").trim())
		;
		
		return sb.toString().equalsIgnoreCase(ticketName.trim());
		
	}
	
	@Override
	public void issuedTicket(String bookCode, IBookingFlightDao bookingFlightDao) throws Exception {
		
		//cek dulu UDAH DIBAYAR apa belum, cek dulu status booking code.
		//jika udah dibayar brarti bookbalance = 0
		ResponseGetBalance balanceResp = new SqivaHandler(airline, this.setupDao).getBalance(bookCode); 
		if (balanceResp.isError()){
			throw new SqivaServerException(balanceResp);
		}
		
		if (balanceResp.getBook_balance() == 0){
			//brarti udah dibayar
			bookingFlightDao.issuedBookingByCode(bookCode);
			
			return;
		}
		
		ResponsePayment paymentResp = new SqivaHandler(airline, setupDao).payment(bookCode);

		if (paymentResp.isError()){
			//assume ok by airline but local database said difference ?
//			if (resp.getErrCode().equals("001003")){
//				bookingFlightDao.issuedBookingByCode(bookCode);
//			}else			
				throw new SqivaServerException(paymentResp);
		}else	{
			
			BookingFlightVO bf = bookingFlightDao.getBookingFlightByBookingCode(bookCode);

			//update ticket no
			List<BookingFlightDtlVO> pnrs = bookingFlightDao.getBookingFlightDetails(bf.getId());
			
			for (BookingFlightDtlVO bookingFlightDtlVO : pnrs) {

				for (TicketUnit ticketUnit : paymentResp.getTicket_unit()) {
					if (matchPnr(bookingFlightDtlVO, ticketUnit.getPax_name())) {
						bookingFlightDtlVO.setPassenger_ticket_no(ticketUnit.getTicket_no());
						
						bookingFlightDao.saveBookingFlightDtl(bookingFlightDtlVO);
						break;
					}
				}
			}

			/*
			ResponsePayment [errCode=0, errMsg=null, book_code=9UX7JJ, book_balance=0, 
					ticket_unit=
					[TicketUnit [pax_name=LIA , ticket_no=321654 0000211758], 
					TicketUnit [pax_name=LIA PERKASA, ticket_no=321654 0000211759], 
					TicketUnit [pax_name=JACK , ticket_no=321654 0000211760], 
					TicketUnit [pax_name=JACKIE , ticket_no=321654 0000211761]]]
							*/
			String email = "elkana911@yahoo.com";
			ResponseSendTicket sendTicketResp = new SqivaHandler(airline, setupDao).sendTicket(bookCode, email);
			if (sendTicketResp.isError()){
				throw new SqivaServerException(sendTicketResp);
			}
			
			bookingFlightDao.issuedBookingByCode(bookCode);
		}
		
	}

	@Override
	public char[] getEconomyClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getBusinessClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getPromoClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FareInfo calculateFareInfo(FareInfo fareInfo2, int adultCount, int childCount, int infantCount) {
		// TODO Auto-generated method stub
		return null;
	}

}
