package com.big.web.b2b_big2.flight.api.kalstar.action.balance;

import com.big.web.b2b_big2.flight.api.kalstar.action.IRequest;
import com.big.web.b2b_big2.flight.api.kalstar.exception.SqivaException;
import com.big.web.b2b_big2.util.Utils;


public class RequestGetBalance implements IRequest {
	private String book_code;
	
	public String getBook_code() {
		return book_code;
	}

	public void setBook_code(String book_code) {
		this.book_code = book_code;
	}

	@Override
	public String toString() {
		return "RequestGetBalance [book_code=" + book_code + "]";
	}

	@Override
	public void validate() throws SqivaException {
		//tolong disesuaikan dengan mandatory di dokumen API
		if (Utils.isEmpty(book_code)) throw new SqivaException("Empty book_code");
	}
	
}
