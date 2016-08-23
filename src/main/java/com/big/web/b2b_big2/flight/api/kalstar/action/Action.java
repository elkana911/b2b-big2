package com.big.web.b2b_big2.flight.api.kalstar.action;

public enum Action {
		//kata2nya harus sama persis dengan APInya
	GET_ORG_DES("data")
	,GET_PAY_TYPE("data")
	,GET_SCHEDULE_V2("information")
	,GET_FARE_V2_NEW("information")
	,GET_BALANCE("information")
	,BOOKING_V2("transaction")
	,CANCEL_BOOK("transaction")
	,SEND_TICKET("transaction")
	,PAYMENT("transaction")
	,VOID_PAYMENT("transaction")
	
	;
	
	private String appType;
	
	private Action(String appType){
		this.appType = appType;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

}
