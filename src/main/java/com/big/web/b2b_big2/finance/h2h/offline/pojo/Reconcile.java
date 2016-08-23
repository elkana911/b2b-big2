package com.big.web.b2b_big2.finance.h2h.offline.pojo;

import com.big.web.b2b_big2.util.Utils;


public class Reconcile{
	private final int LEN_VA = 10;
	private final int LEN_CUSTNAME = 10;
	private final int LEN_DATE = 10;
	private final int LEN_BILLAMOUNT = 10;
	private final int LEN_PAYMENT = 10;
	private final int LEN_CHANNEL = 10;
	private final int LEN_TERMID = 10;
	private final int LEN_PRIORITY = 10;
	
	private String accountNo;
	private String customerName;
	private String date; // yyyyMMddHHmmss
	private String billAmount;
	private String payment;
	private String channel;
	private String termId;
	private String priority;
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(String billAmount) {
		this.billAmount = billAmount;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(Utils.rightPad(accountNo, LEN_VA, ' '))
			.append(Utils.rightPad(customerName, LEN_CUSTNAME, ' '))
			.append(Utils.rightPad(date, LEN_DATE, ' '))
			.append(Utils.rightPad(billAmount, LEN_BILLAMOUNT, ' '))
			.append(Utils.rightPad(payment, LEN_PAYMENT, ' '))
			.append(Utils.rightPad(channel, LEN_CHANNEL, ' '))
			.append(Utils.rightPad(termId, LEN_TERMID, ' '))
			.append(Utils.rightPad(priority, LEN_PRIORITY, ' '))
		;
		
		return sb.toString();
		/*
		return "Reconcile [accountNo=" + accountNo + ", customerName="
				+ customerName + ", date=" + date + ", billAmount="
				+ billAmount + ", payment=" + payment + ", channel=" + channel
				+ ", termId=" + termId + ", priority=" + priority + "]";
				*/
	}

}
