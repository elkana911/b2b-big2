package com.big.web.b2b_big2.flight.api.kalstar.action.voidpayment;

import com.big.web.b2b_big2.flight.api.kalstar.action.BasicResponse;


/**
 * @author Administrator
 *
 */
public class ResponseVoidPayment extends BasicResponse{

	private String book_code;
	
	/**
	 * Biaya normal penjualan
	 */
	private int normal_sales;
	/**
	 * Balance/outstanding for this booking, after void payment
	 */
	private int book_balance;
	public String getBook_code() {
		return book_code;
	}
	public void setBook_code(String book_code) {
		this.book_code = book_code;
	}
	
	public int getNormal_sales() {
		return normal_sales;
	}
	
	public void setNormal_sales(int normal_sales) {
		this.normal_sales = normal_sales;
	}
	
	public int getBook_balance() {
		return book_balance;
	}
	
	public void setBook_balance(int book_balance) {
		this.book_balance = book_balance;
	}
	
	@Override
	public String toString() {
		return "ResponseVoidPayment [book_code=" + book_code + ", normal_sales=" + normal_sales + ", book_balance="
				+ book_balance + ", getErrCode()=" + getErrCode() + ", getErrMsg()=" + getErrMsg() + "]";
	}
	
}
