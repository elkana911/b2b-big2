package com.big.web.b2b_big2.flight.api.kalstar.action.sendticket;

import com.big.web.b2b_big2.flight.api.kalstar.action.BasicResponse;


/**
 * This class is customized due to differnet airlines return format
 * @author Administrator
 *
 */
public class ResponseSendTicket extends BasicResponse{

	private String book_code;
	
	/**
	 * Booking code status. XX indicates that this booking has been canceled
	 */
	private String status;
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ResponseSendTicket [book_code=" + book_code + ", status=" + status + ", email=" + email
				+ ", getErrCode()=" + getErrCode() + ", getErrMsg()=" + getErrMsg() + "]";
	}

	
}
