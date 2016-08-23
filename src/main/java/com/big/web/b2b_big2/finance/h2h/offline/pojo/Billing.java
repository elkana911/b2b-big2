package com.big.web.b2b_big2.finance.h2h.offline.pojo;

import com.big.web.b2b_big2.util.Utils;


public class Billing{
	private final int LEN_VA = 20;
	private final int LEN_CUSTNAME = 30;
	private final int LEN_AMOUNT = 12;
	private final int LEN_REMARKS = 30;
	private final int LEN_PRIORITY = 2;
	
	private String accountNo;
	private String customerName;
	private String amount;
	private String remarks;
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
			.append(Utils.rightPad(amount, LEN_AMOUNT, ' '))
			.append(Utils.rightPad(remarks, LEN_REMARKS, ' '))
			.append(Utils.rightPad(priority, LEN_PRIORITY, ' '))
			;
		
		return sb.toString();
		/*
		return "MultiBilling [accountNo=" + accountNo + ", customerName="
				+ customerName + ", amount=" + amount + ", remarks=" + remarks
				+ ", priority=" + priority + "]";
				*/
	}

}
