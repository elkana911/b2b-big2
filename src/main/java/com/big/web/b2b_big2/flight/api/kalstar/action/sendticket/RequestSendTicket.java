package com.big.web.b2b_big2.flight.api.kalstar.action.sendticket;

import com.big.web.b2b_big2.flight.api.kalstar.action.IRequest;
import com.big.web.b2b_big2.flight.api.kalstar.exception.SqivaException;
import com.big.web.b2b_big2.util.Utils;


public class RequestSendTicket implements IRequest{
	private String book_code;
	
	/**
	 * Destination email address
	 */
	private String email;
	
	public String getBook_code() {
		return book_code;
	}

	public void setBook_code(String book_code) {
		this.book_code = book_code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "RequestSendTicket [book_code=" + book_code + ", email=" + email + "]";
	}

	@Override
	public void validate() throws SqivaException {
		//tolong disesuaikan dengan mandatory di dokumen API
		if (Utils.isEmpty(book_code)) throw new SqivaException("Empty book_code");
		if (Utils.isEmpty(email)) throw new SqivaException("Empty email");
	}

}
