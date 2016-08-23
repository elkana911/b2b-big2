package com.big.web.b2b_big2.flight.api.kalstar.action;

public class BasicResponse {
	private String errCode;
	private String errMsg;

	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public boolean isError(){
		return errCode.length() > 0 && !errCode.equals("0");
	}

	@Override
	public String toString() {
		return "BasicResponse [errCode=" + errCode + ", errMsg=" + errMsg + "]";
	}
	
}
