package com.big.web.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

/*
faq
apakah unik 
rule
jika ada topup via virtual account maka dapat insert berkali2
lalu kapan updatenya, yaitu ...
*/

/**
 * Rule: 1 user/agen, 1 account
 * 
 * @author Administrator
 *
 */
@Entity
@Audited
@Table(name="user_account")
public class UserAccountVO implements Serializable{

	/**
	 *  
	 */
	private static final long serialVersionUID = 8303223905197432718L;

	private String id;
	private User user;
	private String no;
	private String bank_code;
	private String currency;	//IDR, USD
//	private Integer accountType;	//1 = Regular, 2 = Virtual Account, see Const.java
//	private BigDecimal topup_amout;
//	private String topup_status;
	private Date lastUpdate;
	
	private String status;
	
	//permintaan andri ?
	private BigDecimal balance;
	private BigDecimal outstanding_balance;
	private String trx_enabled;
	
	public UserAccountVO() {
    	setId(java.util.UUID.randomUUID().toString());
	}
	
	@Id
	@Column(length=40, nullable=false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	@Column(length=3)
	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	@Column(length=20)
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	@Column(length=3)
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/*
	@Column(name="type")
	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
*/
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getOutstanding_balance() {
		return outstanding_balance;
	}

	public void setOutstanding_balance(BigDecimal outstanding_balance) {
		this.outstanding_balance = outstanding_balance;
	}

	@Column(length = 1)
	public String getTrx_enabled() {
		return trx_enabled;
	}

	public void setTrx_enabled(String trx_enabled) {
		this.trx_enabled = trx_enabled;
	}

	
	/*
	public BigDecimal getTopup_amout() {
		return topup_amout;
	}

	public void setTopup_amout(BigDecimal topup_amout) {
		this.topup_amout = topup_amout;
	}

	@Column(length=1)
	public String getTopup_status() {
		return topup_status;
	}

	public void setTopup_status(String topup_status) {
		this.topup_status = topup_status;
	}
	*/
}
