package com.big.web.b2b_big2.flight.api.kalstar.action.payment;

import java.util.List;

import com.big.web.b2b_big2.flight.api.kalstar.action.BasicResponse;

/**
 * This class is customized due to differnet airlines return format
 * @author Administrator
 *
 */
public class ResponsePayment extends BasicResponse{

	private String book_code;
	private int book_balance;	

	private List<TicketUnit> ticket_unit;
	
	public String getBook_code() {
		return book_code;
	}

	public void setBook_code(String book_code) {
		this.book_code = book_code;
	}

	public int getBook_balance() {
		return book_balance;
	}

	public void setBook_balance(int book_balance) {
		this.book_balance = book_balance;
	}

	public List<TicketUnit> getTicket_unit() {
		return ticket_unit;
	}

	public void setTicket_unit(List<TicketUnit> ticket_unit) {
		this.ticket_unit = ticket_unit;
	}

	@Override
	public String toString() {
		return "ResponsePayment [book_code=" + book_code + ", book_balance=" + book_balance + ", ticket_unit="
				+ ticket_unit + ", getErrCode()=" + getErrCode() + ", getErrMsg()=" + getErrMsg() + "]";
	}
	
}
