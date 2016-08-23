package com.big.web.b2b_big2.flight.pojo;


public enum PERSON_TYPE {
	ADULT("A"), CHILD("C"), INFANT("I");
	
	private String flag;
	
	private PERSON_TYPE(String flag){
		this.flag = flag;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public static PERSON_TYPE getPersonType(String flag) {
		for (PERSON_TYPE d : PERSON_TYPE.values()) {
			if (flag.equalsIgnoreCase(d.getFlag()))
				return d;
		}
		
		return null;
	}

}
