package com.big.web.b2b_big2.flight.api.kalstar.action.paytype;

import com.big.web.b2b_big2.flight.pojo.FareInfo;

/**
 * Store information about an airline/Kalstar's fare. this bean not store how many adults and children book, use {@link FareInfo}
 * 
 * @author Administrator
 * @see FareInfo
 */
public class PayTypeDetail {
	
	private String paymentType;
	private String bank;
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	@Override
	public String toString() {
		return "PayTypeDetail [paymentType=" + paymentType + ", bank=" + bank + "]";
	}


	
}
