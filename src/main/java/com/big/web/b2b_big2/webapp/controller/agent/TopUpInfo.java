package com.big.web.b2b_big2.webapp.controller.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.model.UserAccountVO;


public class TopUpInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4074831437498921308L;
	
	private int paymentMethod;
	private String userName;
    private String amount;
    private String VAN;
    private String bankCodeVA;
    private String bankCodeATM;
    private String adminFee;
    private String total;
    private String minimum;
    private String outstanding;
    private String currency;
    private String balance;

	public TopUpInfo(UserAccountVO ua, ISetupDao setupDao) {
		setVAN(Utils.prettyVAN(ua.getNo()));
		setBankCodeVA(ua.getBank_code());
		setCurrency(ua.getCurrency());
		
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        BigDecimal kelipatan = setupDao.getValueAsBigDecimal(ISetupDao.KEY_TOPUP_MULTIPLE);
        String minimum = formatter.format(kelipatan == null ? BigDecimal.ZERO.doubleValue() : kelipatan.doubleValue());
        setMinimum(minimum);
    	
        String outstanding = formatter.format(ua.getOutstanding_balance() == null ? BigDecimal.ZERO.doubleValue() : ua.getOutstanding_balance().doubleValue());
		setOutstanding(outstanding);
        
		String balance = formatter.format(ua.getBalance() == null ? BigDecimal.ZERO.doubleValue() : ua.getBalance());
		setBalance(balance);

	}

	public TopUpInfo() {
		// TODO Auto-generated constructor stub
	}

	public int getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getVAN() {
		return VAN;
	}

	public void setVAN(String vAN) {
		VAN = vAN;
	}
	
	public String getBankCodeVA() {
		return bankCodeVA;
	}

	public void setBankCodeVA(String bankCodeVA) {
		this.bankCodeVA = bankCodeVA;
	}

	public String getBankCodeATM() {
		return bankCodeATM;
	}

	public void setBankCodeATM(String bankCodeATM) {
		this.bankCodeATM = bankCodeATM;
	}

	public String getAdminFee() {
		return adminFee;
	}

	public void setAdminFee(String adminFee) {
		this.adminFee = adminFee;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}
	
	public String getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(String outstanding) {
		this.outstanding = outstanding;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "TopUp [paymentMethod=" + paymentMethod + ", userName="
				+ userName + ", amount=" + amount + ", VAN=" + VAN
				+ ", bankCodeVA=" + bankCodeVA + ", bankCodeATM=" + bankCodeATM
				+ ", adminFee=" + adminFee + ", total=" + total + ", minimum="
				+ minimum + ", outstanding=" + outstanding + ", currency="
				+ currency + ", balance=" + balance + "]";
	}
    
}
