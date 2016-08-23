package com.big.web.b2b_big2.flight.api.kalstar.action.payment;

import com.big.web.b2b_big2.flight.api.kalstar.action.IRequest;
import com.big.web.b2b_big2.flight.api.kalstar.exception.SqivaException;
import com.big.web.b2b_big2.util.Utils;

public class RequestPayment implements IRequest{
	private String book_code;
	
	/**
	 * Payment amount. If not specified, counted as full payment
	 */
	private int amount;
	
	/**
	 * Payment type. If not specified, by default is Agent
	 */
	private String pay_type;
	
	/**
	 * Payment Bank. Mandatory if payment type has pay bank
	 */
	private String pay_bank;

	public String getBook_code() {
		return book_code;
	}
	public void setBook_code(String book_code) {
		this.book_code = book_code;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}
	public String getPay_bank() {
		return pay_bank;
	}
	public void setPay_bank(String pay_bank) {
		this.pay_bank = pay_bank;
	}


	@Override
	public String toString() {
		return "RequestPayment [book_code=" + book_code + ", amount=" + amount + ", pay_type=" + pay_type
				+ ", pay_bank=" + pay_bank + "]";
	}
	@Override
	public void validate() throws SqivaException {
		//tolong disesuaikan dengan mandatory di dokumen API
		if (Utils.isEmpty(book_code)) throw new SqivaException("Empty book_code");
	}
	
	
}
