package com.big.web.b2b_big2.finance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "commission_setup", schema = "big_trans")
public class CommissionVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1160381855183375519L;

	private Long id;
	private String airline_code;
	private BigDecimal comm_rate;
	private BigDecimal issued_fee;
	
	/**
	 * diperoleh dari airline untuk agen
	 */
	private BigDecimal incentive;	 
	private BigDecimal pph23;
	private BigDecimal comm_rate_berleha;
	private Date lastUpdate;
	private Long lastUpdateBy;
	
	@Id
	@XmlTransient
	@JsonIgnore
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(length = 3)
	public String getAirline_code() {
		return airline_code;
	}
	public void setAirline_code(String airline_code) {
		this.airline_code = airline_code;
	}

	@Column(name = "commission")
	public BigDecimal getComm_rate() {
		return comm_rate;
	}
	public void setComm_rate(BigDecimal comm_rate) {
		this.comm_rate = comm_rate;
	}
	public BigDecimal getIssued_fee() {
		return issued_fee;
	}
	public void setIssued_fee(BigDecimal issued_fee) {
		this.issued_fee = issued_fee;
	}
	public BigDecimal getIncentive() {
		return incentive;
	}
	public void setIncentive(BigDecimal incentive) {
		this.incentive = incentive;
	}
	public BigDecimal getPph23() {
		return pph23;
	}
	public void setPph23(BigDecimal pph23) {
		this.pph23 = pph23;
	}
	@Column(name = "commission_berleha")
	public BigDecimal getComm_rate_berleha() {
		return comm_rate_berleha;
	}
	public void setComm_rate_berleha(BigDecimal comm_rate_berleha) {
		this.comm_rate_berleha = comm_rate_berleha;
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
	
}
