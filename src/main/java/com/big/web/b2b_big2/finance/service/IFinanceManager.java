package com.big.web.b2b_big2.finance.service;

import java.math.BigDecimal;
import java.util.List;

import com.big.web.b2b_big2.finance.bank.BankCode;
import com.big.web.b2b_big2.finance.bank.TopUpPayment;
import com.big.web.b2b_big2.finance.bank.model.BankVO;
import com.big.web.b2b_big2.finance.bank.model.PaymentWayVO;
import com.big.web.b2b_big2.finance.bank.model.TopUpVO;
import com.big.web.b2b_big2.finance.model.CommissionVO;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.util.AppInfo;
import com.big.web.model.User;
import com.big.web.model.UserAccountVO;

public interface IFinanceManager {

	/**
	 * Deault is selected by IDR currency
	 * @param userName
	 * @return
	 * @see #getUserAccount(String, String)
	 */
	UserAccountVO getUserAccount(String userName);
	
	/**
	 * 
	 * @param userName
	 * @param currIdr
	 * @return
	 * @see #getUserAccount(String)
	 */
	UserAccountVO getUserAccount(String userName, String currIdr);
	UserAccountVO getUserAccountByAccountNo(String vaNumber);
	UserAccountVO getUserAccountById(String id);

	User getUserByAccountNo(String vaNumber);
	User getUserByAccountID(String id);
	
	List<PaymentWayVO> getPaymentMethods();
	
	BankVO getBankByCode(String bankCode);
	List<BankVO> getBankSupportATM();
	List<BankVO> getBankSupportVirtualAccount();
	List<BankVO> getBanks();
	//BankVO getBank(Payment paymentMethod)

	TopUpVO topUp(BankCode bank, TopUpPayment paymentMethod, String userName, BigDecimal amount);

	List<AppInfo> getAppInfo();

	List<TopUpVO> getTopUps(Long userId);
	List<TopUpVO> getTopUps(String bankCode, String status, Long agencyCode);

	BigDecimal getCommission(BigDecimal rate, Airline airline, FareInfo fareInfo);
	BigDecimal getNetCommission(BigDecimal rate, BigDecimal issuedFee, Airline airline, FareInfo fareInfo);
	CommissionVO getCommission(Airline airline, FareInfo fareInfo);





//	UserAccountVO createVirtualAccount(AgentVO agent, WHOLESALER wholeSaler);
}
