package com.big.web.b2b_big2.agent.pojo;

/**
 * dipakai oleh table USER_ACCOUNT
 * @author Administrator
 *
 */
public enum ACCOUNT_STATUS {
	ACTIVE("A"),
	EXPIRED("E"),
	LOCKED("L"),
	DELETED("D"),
	PENDING("P")	//awaiting registration complete. must change to ACTIVE once money is transferred
	;
	
	private String flag;
	
	private ACCOUNT_STATUS(String flag) {
		this.flag = flag;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public static ACCOUNT_STATUS getAccountStatus(String flag) {
		for (ACCOUNT_STATUS d : ACCOUNT_STATUS.values()) {
			if (flag.equalsIgnoreCase(d.getFlag()))
				return d;
		}
		
		return null;
	}
}
