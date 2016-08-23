package com.big.web.b2b_big2.agent.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="whole_saler")
public class Whole_SalerVO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private Long agent_id;
	private String va_code;
	
	private String agentName;

	@Id
	@XmlTransient
	@JsonIgnore
	@Column(length=40, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(Long agent_id) {
		this.agent_id = agent_id;
	}
	
	@Column(length = 2)
	public String getVa_code() {
		return va_code;
	}

	public void setVa_code(String va_code) {
		this.va_code = va_code;
	}

	@Transient
	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	
}
