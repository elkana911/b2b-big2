package com.big.web.b2b_big2.finance.bank;

public enum VAN_STATUS {
	PENDING("P"),
	REJECT("R"),
	RELEASE("L"),
	APPROVE("A")
	;
	
	private String flag;
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	private VAN_STATUS(String flag){
		this.flag = flag;
	}
	
}
