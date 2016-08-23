package com.big.web.b2b_big2.flight.api.kalstar.action.cancel;

import com.big.web.b2b_big2.flight.api.kalstar.action.BasicResponse;


/**
 * This class is customized due to differnet airlines return format
 * @author Administrator
 *
 */
public class ResponseCancel extends BasicResponse{

	private String book_code;
	
	/**
	 * Booking code status. XX indicates that this booking has been canceled
	 */
	private String status;
	
	private int refund_amount;	
	
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

	public int getRefund_amount() {
		return refund_amount;
	}

	public void setRefund_amount(int refund_amount) {
		this.refund_amount = refund_amount;
	}

	@Override
	public String toString() {
		return "ResponseCancel [book_code=" + book_code + ", status=" + status + ", refund_amount=" + refund_amount
				+ ", getErrCode()=" + getErrCode() + ", getErrMsg()=" + getErrMsg() + "]";
	}

	
}
