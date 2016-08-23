package com.big.web.b2b_big2.flight.pojo;

import java.io.Serializable;

import org.springmodules.validation.bean.conf.loader.annotation.handler.Email;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotEmpty;

public class ContactCustomer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 394387158518053479L;
	private String id;
	private String fullName;
	private String phone1;
	private String phone2;
	private String agentName;
	
	@NotEmpty @Email
	private String agentEmail;
	private String agentPhone1;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAgentEmail() {
		return agentEmail;
	}
	public void setAgentEmail(String agentEmail) {
		this.agentEmail = agentEmail;
	}
	public String getAgentPhone1() {
		return agentPhone1;
	}
	public void setAgentPhone1(String agentPhone1) {
		this.agentPhone1 = agentPhone1;
	}
	@Override
	public String toString() {
		return "ContactCustomer [id=" + id + ", fullName=" + fullName + ", phone1=" + phone1 + ", phone2=" + phone2
				+ ", agentName=" + agentName + ", agentEmail=" + agentEmail + ", agentPhone1=" + agentPhone1 + "]";
	}

}
