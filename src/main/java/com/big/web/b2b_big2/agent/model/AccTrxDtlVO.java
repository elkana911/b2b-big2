package com.big.web.b2b_big2.agent.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.envers.Audited;

import com.big.web.model.UserAccountVO;

/**
 * Karena table ini menyimpan seluruh transaksi agen, apa ini yg namanya perlu partisi ya ??
 * @author Administrator
 *
 */
@Entity
@Audited
@Table(name="acc_trx_dtl")
public class AccTrxDtlVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private UserAccountVO account; 	// master 
	private BigDecimal debet;		// new top up masuk sini
	private BigDecimal credit;		// pembelian tiket potong dr sini 
	private BigDecimal balance;
	private Date createdDate;
	private Date bankTransDate;
	private String remarks;
	private Integer closed_year;
	private String ref_bookingFlight;	// bookingflight.id
	
    public AccTrxDtlVO() {
    	setId(java.util.UUID.randomUUID().toString());
	}

    @Id
    @XmlTransient
    @JsonIgnore
    @Column(length = 40, nullable=false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="account_id")
	public UserAccountVO getAccount() {
		return account;
	}

	public void setAccount(UserAccountVO account) {
		this.account= account;
	}

	public BigDecimal getDebet() {
		return debet;
	}

	public void setDebet(BigDecimal debet) {
		this.debet = debet;
	}

	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	@Column(name="created_date")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

    @Column(length = 50)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Column(name= "banktrans_date")
	public Date getBankTransDate() {
		return bankTransDate;
	}

	public void setBankTransDate(Date bankTransDate) {
		this.bankTransDate = bankTransDate;
	}

	public Integer getClosed_year() {
		return closed_year;
	}

	public void setClosed_year(Integer closed_year) {
		this.closed_year = closed_year;
	}

	@Column(length = 40)
	public String getRef_bookingFlight() {
		return ref_bookingFlight;
	}

	public void setRef_bookingFlight(String ref_bookingFlight) {
		this.ref_bookingFlight = ref_bookingFlight;
	}

	@Override
	public String toString() {
		return "AccTrxDtlVO [id=" + id + ", account=" + account + ", debet="
				+ debet + ", credit=" + credit + ", balance=" + balance
				+ ", createdDate=" + createdDate + ", bankTransDate="
				+ bankTransDate + ", remarks=" + remarks + ", closed_year="
				+ closed_year + ", ref_bookingFlight=" + ref_bookingFlight
				+ "]";
	}
	
	
}
