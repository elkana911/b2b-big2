package com.big.web.b2b_big2.hotel.mandira.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class ResponseAgentData {
	private Message msg;
	private List<ResponseData> data;
	
	public List<ResponseData> getData() {
		return data;
	}
	@XmlElement(name="GetAllotment_Response")
	public void setData(List<ResponseData> data) {
		this.data = data;
	}
	public Message getMsg() {
		return msg;
	}
	@XmlElement(name="Message")
	public void setMsg(Message msg) {
		this.msg = msg;
	}
}
