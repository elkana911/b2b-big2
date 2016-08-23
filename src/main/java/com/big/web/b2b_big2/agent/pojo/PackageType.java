package com.big.web.b2b_big2.agent.pojo;


public enum PackageType {
	
	BASIC(101),
	PROFESSIONAL(102),
	PREMIUM(103),
	ADMINISTRATOR(99),
	UNKNOWN(0);

	private int code;	//cek with table agent_package
	
	private PackageType(int code) {
		this.code  = code;
	}
	
	public int getCode() {
		return this.code;
	}

	public static PackageType get(Integer code) {
		if (code == null) return UNKNOWN;
		
		for (PackageType m : PackageType.values()) {
			if (code.intValue() == m.getCode())
				return m;
		}
		
		return UNKNOWN;
	}
}
