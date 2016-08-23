package com.big.web.b2b_big2.finance.bank.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.big.web.b2b_big2.finance.bank.TOPUP_STATUS;


@Entity
@Table(name = "topup")
public class TopUpVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1157503406853713401L;
	/**
	 * 
	 */
	
	private String id;
	private String account_no;
	private Long payment_type;
	private Date lastUpdate;
	private String bankCode;
	private BigDecimal amount;
	private BigDecimal amountToPaid;
	private String status;
	private Date timeToPay;
	private Long uniqueCode;
	private String trans_code;
	
	private Long user_id;

	public TopUpVO(){
		setId(UUID.randomUUID().toString());
	}

	/*
	public TopUpVO(UserAccountVO userAccount, IFinanceDao financeDao, TopUpPayment paymentMethod, BigDecimal amount){
		
		this();

		setAccount_no(userAccount.getNo());
		setAmount(amount);
		setUser_id(userAccount.getUser().getId());
		setBankCode(userAccount.getBank_code());
		setPayment_type(Long.parseLong(String.valueOf(paymentMethod.getId())));
		setAmountToPaid(amount);
		
		setTimeToPay(Utils.addHours(new Date(), 2)); //default 2 jam

		setTrans_code(financeDao.generateTransactionCode());
		
		setUniqueCode(financeDao.generateCounterTopUp(userAccount));
		
		setStatus(TOPUP_STATUS.PENDING.getFlag());
		
		setLastUpdate(new Date());
	}*/

	@Id
	@Column(length = 40)
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * Biasanya dipakai untuk membuat nominal unique. Jika amount 1000000 dan transCode 103, maka amounttopaid harusnya 1000103
	 * <br>contoh: 103  --> increment
	 * @see #getTrans_code()
	 * @see #getAmount()
	 * @see #getAmountToPaid()
	 */
	public Long getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(Long uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	@Column(length = 20)
	public String getAccount_no() {
		return account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public Long getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(Long payment_type) {
		this.payment_type = payment_type;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public BigDecimal getAmountToPaid() {
		return amountToPaid;
	}

	public void setAmountToPaid(BigDecimal amountToPaid) {
		this.amountToPaid = amountToPaid;
	}

	@Column(length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTimeToPay() {
		return timeToPay;
	}

	public void setTimeToPay(Date timeToPay) {
		this.timeToPay = timeToPay;
	}

	@Column(length = 3)
	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * Biasanya 5 digit supaya mudah diingat orang. contoh: 6MU2K
	 * @return
	 * @see #getUniqueCode()
	 */
	@Column(length = 6)
	public String getTrans_code() {
		return trans_code;
	}

	public void setTrans_code(String trans_code) {
		this.trans_code = trans_code;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	@Transient
	public TOPUP_STATUS getTopUpStatus(){
		return TOPUP_STATUS.getTopUpStatus(status);
	}
	
	@Override
	public String toString() {
		return "TopUpVO [id=" + id + ", account_no=" + account_no + ", payment_type=" + payment_type + ", lastUpdate="
				+ lastUpdate + ", bankCode=" + bankCode + ", amount=" + amount + ", amountToPaid=" + amountToPaid
				+ ", status=" + status + ", timeToPay=" + timeToPay + ", uniqueCode=" + uniqueCode + ", trans_code="
				+ trans_code + ", user_id=" + user_id + "]";
	}

}
