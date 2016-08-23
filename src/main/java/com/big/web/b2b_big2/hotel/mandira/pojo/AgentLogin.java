package com.big.web.b2b_big2.hotel.mandira.pojo;

import javax.xml.bind.annotation.XmlElement;

public class AgentLogin {
	private String APIKey;
	private String AgentId;
	private String AgentName;
	private String AgentPassword;
	
	
	public AgentLogin(String aPIKey, String agentId, String agentName,
			String agentPassword) {
		APIKey = aPIKey;
		AgentId = agentId;
		AgentName = agentName;
		AgentPassword = agentPassword;
	}
	public String getAPIKey() {
		return APIKey;
	}
	
	@XmlElement(name="APIKey")
	public void setAPIKey(String aPIKey) {
		APIKey = aPIKey;
	}
	public String getAgentId() {
		return AgentId;
	}

	@XmlElement(name="AgentId")
	public void setAgentId(String agentId) {
		AgentId = agentId;
	}
	public String getAgentName() {
		return AgentName;
	}
	
	@XmlElement(name="AgentName")
	public void setAgentName(String agentName) {
		AgentName = agentName;
	}
	public String getAgentPassword() {
		return AgentPassword;
	}
	
	@XmlElement(name="AgentPassword")	
	public void setAgentPassword(String agentPassword) {
		AgentPassword = agentPassword;
	}
}
