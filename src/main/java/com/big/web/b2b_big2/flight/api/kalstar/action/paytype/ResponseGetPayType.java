package com.big.web.b2b_big2.flight.api.kalstar.action.paytype;

import java.util.List;

import com.big.web.b2b_big2.flight.api.kalstar.action.BasicResponse;


/**
 * @author Administrator
 *
 */
public class ResponseGetPayType extends BasicResponse{

	private List<PayTypeDetail> payType;

	public List<PayTypeDetail> getPayType() {
		return payType;
	}

	public void setPayType(List<PayTypeDetail> payType) {
		this.payType = payType;
	}

	@Override
	public String toString() {
		return "ResponseGetPayType [payType=" + payType + "]";
	}
	
}
