package com.big.web.b2b_big2.hotel.mandira.pojo;

import javax.xml.bind.annotation.XmlElement;
/*
<?xml version="1.0" encoding="UTF-8" ?>
<WebService_GetAllotment>
	<Message>
	<Status>Success</Status>
	</Message>
	
	<GetAllotment_Response>
		<Date>2014-10-10</Date>
		<Allotment>5</Allotment>
	</GetAllotment_Response>
	
	<GetAllotment_Response>
		<Date>2014-10-11</Date>
		<Allotment>5</Allotment>
	</GetAllotment_Response>
	
	<GetAllotment_Response>
		<Date>2014-10-12</Date>
		<Allotment>5</Allotment>
	</GetAllotment_Response>
</WebService_GetAllotment>
 */
public class ResponseData {
	private String date;
	private String allotment;
	
	public String getDate() {
		return date;
	}
	@XmlElement(name="Date")
	public void setDate(String date) {
		this.date = date;
	}
	public String getAllotment() {
		return allotment;
	}
	@XmlElement(name="Allotment")
	public void setAllotment(String allotment) {
		this.allotment = allotment;
	}
}
