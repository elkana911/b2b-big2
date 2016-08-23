package com.big.web.b2b_big2.finance.bank;


/*
 * Cocokkan dengan table PaymentWay
 */
public enum TopUpPayment {
	TRANSFER_ATM(1),
	VIRTUAL_ACCOUNT(2)
	;
	
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private TopUpPayment(int id){
		this.id = id;
	}
	
	
	public static TopUpPayment getId(int id){
		for (TopUpPayment d : TopUpPayment.values()) {
			if (id == d.getId())
				return d;
		}
		
		throw new RuntimeException("Unhandled id of " + id);
	}
}
