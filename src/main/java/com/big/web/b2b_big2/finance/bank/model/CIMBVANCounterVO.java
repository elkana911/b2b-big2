package com.big.web.b2b_big2.finance.bank.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "cimbvancounter")
public class CIMBVANCounterVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9176787675968567948L;
	
	private String id;
	private String clientCode;
	private String regionCode;
	private BigDecimal counter;
	private Date lastUpdate;
	private Long lastUpdateBy;	//user.java
	private String flag;
	
	
	public CIMBVANCounterVO(){
		setId(UUID.randomUUID().toString());
	}
	
	@Id
	@Column(length = 40)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Column(length = 3)
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	@Column(length = 3)
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public BigDecimal getCounter() {
		return counter;
	}
	public void setCounter(BigDecimal counter) {
		this.counter = counter;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Long getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(Long lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	@Column(length = 1)	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
}
