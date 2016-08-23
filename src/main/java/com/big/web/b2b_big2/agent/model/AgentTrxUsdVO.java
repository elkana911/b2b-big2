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
import org.hibernate.search.annotations.Indexed;

import com.big.web.model.UserAccountVO;

/**
 * @deprecated
 * @author Administrator
 * @see	AccTrxDtlVO
 */
@Entity
@Table(name="agent_trx_usd")
public class AgentTrxUsdVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8371241275893455775L;

	private String id;
	private UserAccountVO account; 
	private BigDecimal debet;
	private BigDecimal credit;
	private BigDecimal saldo;
	private Date createdDate;

    public AgentTrxUsdVO() {
    	setId(java.util.UUID.randomUUID().toString());
	}

    @Id
    @XmlTransient
    @JsonIgnore
    @Column(length = 40)
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
		this.account = account;
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

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	
}
