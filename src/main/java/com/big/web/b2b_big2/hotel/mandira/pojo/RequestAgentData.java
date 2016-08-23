package com.big.web.b2b_big2.hotel.mandira.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="WebService_GetAllotment")
public class RequestAgentData {
	AgentLogin agentLogin;
	RequestData request;
	public AgentLogin getAgentLogin() {
		return agentLogin;
	}
	
	@XmlElement(name="AgentLogin")
	public void setAgentLogin(AgentLogin agentLogin) {
		this.agentLogin = agentLogin;
	}
	public RequestData getRequest() {
		return request;
	}
	
	@XmlElement(name="GetAllotment_Request")
	public void setRequest(RequestData request) {
		this.request = request;
	}
}
