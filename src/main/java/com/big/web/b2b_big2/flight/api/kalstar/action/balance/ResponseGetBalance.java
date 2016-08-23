package com.big.web.b2b_big2.flight.api.kalstar.action.balance;

import com.big.web.b2b_big2.flight.api.kalstar.action.BasicResponse;


/**
 * This class is customized due to differnet airlines return format
 * @author Administrator
 *
 */
public class ResponseGetBalance extends BasicResponse {

	private String book_code;
	/**
	 * Numeric code. ex: 7512671582
	 */
	private int num_code;
	
	/**
	 * Normal selling price. ex: 2292538
	 */
	private int normal_sales;
	
	/**
	 * Balance/outstanding of booking. ex: 2292538
	 */
	private int book_balance;

	public String getBook_code() {
		return book_code;
	}

	public void setBook_code(String book_code) {
		this.book_code = book_code;
	}

	public int getNum_code() {
		return num_code;
	}

	public void setNum_code(int num_code) {
		this.num_code = num_code;
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
		return "ResponseGetBalance [book_code=" + book_code + ", num_code=" + num_code + ", normal_sales="
				+ normal_sales + ", book_balance=" + book_balance + ", getErrCode()=" + getErrCode() + ", getErrMsg()="
				+ getErrMsg() + "]";
	}

	
}
