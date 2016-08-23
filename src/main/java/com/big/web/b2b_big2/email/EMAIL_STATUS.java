package com.big.web.b2b_big2.email;

public enum EMAIL_STATUS {
	
	EXPIRED("E"),
	PENDING("P"),
	FAILED("F"),
	OK("O")	//kata lain dari SENT
	;
	
	private String flag;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	private EMAIL_STATUS(String flag){
		this.flag = flag;
	}
	
}
