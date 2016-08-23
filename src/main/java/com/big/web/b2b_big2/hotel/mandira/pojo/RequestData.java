package com.big.web.b2b_big2.hotel.mandira.pojo;

import javax.xml.bind.annotation.XmlElement;

/*
<?xml version="1.0" encoding="UTF-8" ?>
<WebService_GetAllotment>
	<AgentLogin>
		<APIKey>YourAPIKey</APIKey>
		<AgentId>YourAgentID</AgentId>
		<AgentName>YourAgentName</AgentName>
		<AgentPassword>YourAgentPassword</AgentPassword>
	</AgentLogin>

	<GetAllotment_Request>
		<HotelId>H0000600</HotelId>
		<RoomType>ClubRoom</RoomType>
		<Allotment_Start>2014-10-10</Allotment_Start>
		<Allotment_End>2014-10-12</Allotment_End>
	</GetAllotment_Request>
</WebService_GetAllotment>
 */
public class RequestData {
	private String HotelId;
	private String RoomType;
	private String Allotment_Start;
	private String Allotment_End;
	public String getHotelId() {
		return HotelId;
	}
	
	@XmlElement(name="HotelId")
	public void setHotelId(String hotelId) {
		HotelId = hotelId;
	}
	public String getRoomType() {
		return RoomType;
	}
	@XmlElement(name="RoomType")
	public void setRoomType(String roomType) {
		RoomType = roomType;
	}
	public String getAllotment_Start() {
		return Allotment_Start;
	}
	@XmlElement(name="Allotment_Start")
	public void setAllotment_Start(String allotment_Start) {
		Allotment_Start = allotment_Start;
	}
	public String getAllotment_End() {
		return Allotment_End;
	}
	@XmlElement(name="Allotment_End")
	public void setAllotment_End(String allotment_End) {
		Allotment_End = allotment_End;
	}
	
}
