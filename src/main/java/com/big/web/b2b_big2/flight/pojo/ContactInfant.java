package com.big.web.b2b_big2.flight.pojo;

import java.io.Serializable;

public class ContactInfant extends BasicContact implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int num;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "ContactInfant [num=" + num + "]";
	}
	
}
