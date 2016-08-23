package com.big.web.b2b_big2.finance.bank;


public enum TOPUP_STATUS {
	PENDING("P"),
	REJECT("R"),
	RELEASE("L"),
	APPROVE("A"),
	EXPIRED("E"),
	CONFIRM("C"),	//confirm by email if topup complete
	OK("O")			//after Confirm
	;
	
	private String flag;
	
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	private TOPUP_STATUS(String flag){
		this.flag = flag;
	}
	
	public static TOPUP_STATUS getTopUpStatus(String flag) {
		for (TOPUP_STATUS d : TOPUP_STATUS.values()) {
			if (flag.equalsIgnoreCase(d.getFlag()))
				return d;
		}
		
		return null;
	}
}
