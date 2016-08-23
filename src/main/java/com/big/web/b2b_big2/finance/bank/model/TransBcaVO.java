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
@Table(name = "transbca")
public class TransBcaVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2784348893120555104L;

	private String transId;
	private Date dateTrans;
	private String description;
	private String cab;
	private BigDecimal amount;
	private String transType;
	private BigDecimal balance;
	private String accountNumber;
	private Date created_date;
	private String b2b_flag;
	
	public TransBcaVO() {
		setTransId(UUID.randomUUID().toString());
	}
	
	@Id
	@Column(length = 40)
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public Date getDateTrans() {
		return dateTrans;
	}
	public void setDateTrans(Date dateTrans) {
		this.dateTrans = dateTrans;
	}
	
	@Column(length = 100)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(length = 5)
	public String getCab() {
		return cab;
	}
	public void setCab(String cab) {
		this.cab = cab;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@Column(length = 3)
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	@Column(length = 15)
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	
	@Column(length = 1)
	public String getB2b_flag() {
		return b2b_flag;
	}
	public void setB2b_flag(String b2b_flag) {
		this.b2b_flag = b2b_flag;
	}
	
	
}
