package com.big.web.b2b_big2.finance.bank.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Diperlukan untuk informasi tambahan di kolom TOPUP.UNIQUECODE.
 * biasanya dimulai dari 101, lalu topup kedua menjadi 102 dst...
 * 
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "topup_counter")
public class TopUpCounterVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2546049206615819709L;

	
	private Long id;
	private Date lastDate;	//harian. jam tidak perlu
	private Long lastCounter;	// > 0
	private String lastAccount;
//	private Date lastUpdate;
	
	
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	public Long getLastCounter() {
		return lastCounter;
	}
	public void setLastCounter(Long lastCounter) {
		this.lastCounter = lastCounter;
	}
	
	@Column(length = 20)
	public String getLastAccount() {
		return lastAccount;
	}
	public void setLastAccount(String lastAccount) {
		this.lastAccount = lastAccount;
	}
	@Override
	public String toString() {
		return "TopUpCounterVO [id=" + id + ", lastDate=" + lastDate
				+ ", lastCounter=" + lastCounter + ", lastAccount="
				+ lastAccount + "]";
	}
	
	
}
