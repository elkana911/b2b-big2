package com.big.web.b2b_big2.common;

public enum CONTACT_TYPE {
	PASSENGER("P"), CONTACT_PERSON("C");
	
	private String flag;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	private CONTACT_TYPE(String flag){
		this.flag = flag;
	}
	
	public static CONTACT_TYPE getContactType(String flag){
		for (CONTACT_TYPE d : CONTACT_TYPE.values()) {
			if (flag.equalsIgnoreCase(d.getFlag()))
				return d;
		}
		
		return null;		
	}
}
