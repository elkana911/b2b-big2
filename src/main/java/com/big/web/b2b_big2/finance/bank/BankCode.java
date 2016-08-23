package com.big.web.b2b_big2.finance.bank;

public enum BankCode {

	PERMATA("Permata Bank", "013"),
	BCA("Bank Central Asia", "014"),
	CIMBNIAGA("Cimb Niaga", "022"),
	UNKNOWN("", "");
	
	private String bankName;
	private String bankCode;
	
	private BankCode(String bankName, String bankCode) {
		this.bankName = bankName;
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public static BankCode getCode(String code){
		for (BankCode d : BankCode.values()) {
			if (code.equalsIgnoreCase(d.getBankCode()))
				return d;
		}
		
		throw new RuntimeException("Unhandled code of " + code);
	}
}
