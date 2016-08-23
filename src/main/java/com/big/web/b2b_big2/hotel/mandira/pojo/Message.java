package com.big.web.b2b_big2.hotel.mandira.pojo;

import javax.xml.bind.annotation.XmlElement;

public class Message {
	private String status;

	public String getStatus() {
		return status;
	}
	@XmlElement(name="Status")
	public void setStatus(String status) {
		this.status = status;
	}
}
