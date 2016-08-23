package com.big.web.b2b_big2.finance.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.big.web.b2b_big2.agent.model.AccTrxDtlVO;
import com.big.web.b2b_big2.agent.model.AgentTrxUsdVO;
import com.big.web.b2b_big2.agent.model.AgentTrxVO;
import com.big.web.b2b_big2.agent.model.AgentVO;
import com.big.web.b2b_big2.agent.pojo.WHOLESALER;
import com.big.web.b2b_big2.booking.model.BookingFlightVO;
import com.big.web.b2b_big2.booking.model.BookingHotelVO;
import com.big.web.b2b_big2.finance.bank.BankCode;
import com.big.web.b2b_big2.finance.bank.TOPUP_STATUS;
import com.big.web.b2b_big2.finance.bank.TopUpPayment;
import com.big.web.b2b_big2.finance.bank.model.BankVO;
import com.big.web.b2b_big2.finance.bank.model.CIMBVANCounterVO;
import com.big.web.b2b_big2.finance.bank.model.PaymentWayVO;
import com.big.web.b2b_big2.finance.bank.model.TopUpVO;
import com.big.web.b2b_big2.finance.model.CommissionRangeVO;
import com.big.web.b2b_big2.finance.model.CommissionVO;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.model.User;
import com.big.web.model.UserAccountVO;

public interface IFinanceDao {
	
	static final int DEPOSIT_MIN = 100000;
	
	BankVO getBank(String bankCode);
	List<BankVO> getBanks();
	List<BankVO> getBankSupportATM();
	
	UserAccountVO createAccount(AgentVO agent);
	UserAccountVO getUserAccount(String userName);
	UserAccountVO getUserAccount(String userName, String currIdr);
	UserAccountVO getUserAccountByAccoutNo(String vaNumber);
	UserAccountVO getUserAccountById(String id);
	User getUserByAccountNo(String vaNumber);
	User getUserByAccountId(String id);

	void saveAccount(UserAccountVO obj);
	
	
	BigDecimal getFirstTopUpAmount(AgentVO agent, WHOLESALER wholeSaler);
	TopUpVO topUp(BankCode bank, TopUpPayment paymentMethod, String userName, BigDecimal amount);
	void saveTopUp(TopUpVO topup);
	
	AgentTrxVO getLastAgentTransaction(String userName);
	AgentTrxVO debetAccount(String userName, BigDecimal debetAmount);
	AgentTrxVO creditAccount(String userName, BigDecimal creditAmount);
	AccTrxDtlVO creditAccountTrx(String userName, BigDecimal creditAmount, String currency);
	AccTrxDtlVO debetAccountTrx(String userName, BigDecimal debetAmount, String currency, String bookingFlightId);

	AgentTrxUsdVO getAgentAccoutUsdByUser(String userName);
	AgentTrxVO getAgentAccoutIdrByUser(String userName);
//	VANGroupVO getVANLast(BankCode bankCode);

	CIMBVANCounterVO getLastCounterFromCIMB(String clientCode56, String regionCode789);

	UserAccountVO isAccountExists(String no);

	List<PaymentWayVO> getPaymentMethods();
	List<BankVO> getBankSupportVirtualAccount();
	
	/**
	 * Get any pending payment.
	 * 
	 * @param acc user account
	 * @param cleanUp add cleanup feature to flag expired topup. default is true.
	 * @return
	 */
	TopUpVO getPendingTopUp(UserAccountVO acc, boolean cleanUp);
	List<TopUpVO> getTopUps(Date from, Date to, TOPUP_STATUS[] topUpStatus);
	List<TopUpVO> getTopUps(Long userId);
	List<TopUpVO> getTopUps(String bankCode, String status, Long agency);

	List<BookingFlightVO> getRevenueFlight(Date from, Date to);
	List<BookingHotelVO> getRevenueHotel(Date from, Date to);

//	TopUpCounterVO incrementLastCounterTopUp(UserAccountVO account);
	boolean isTopUpExists(String transCode);

//	BigDecimal getLastSaldo(Agent agent);
	
	/**
	 * Generate 5 unique alphanumeric
	 * @return 6MU2K
	 * @see #incrementLastCounterTopUp(UserAccountVO)
	 */
	String generateTransactionCode();
	long generateCounterTopUp(UserAccountVO acc);
	
	/**
	 * Mendapatkan komisi kotor yang didapat sub agent
	 * @param rate
	 * @param airline
	 * @param fareInfo
	 * @return
	 */
	BigDecimal getCommission(BigDecimal rate, Airline airline, FareInfo fareInfo);
	BigDecimal getCommission(BigDecimal rate, Airline airline, String originIata, String destinationIata, int tripMode, String flightNo, Character seatClass);
	
	/**
	 * Mendapatkan komisi bersih yg didapat sub agent
	 * @param rate
	 * @param issuedFee
	 * @param airline
	 * @param fareInfo
	 * @return
	 */
	BigDecimal getNetCommission(BigDecimal rate, BigDecimal issuedFee, Airline airline, FareInfo fareInfo);
	
	/**
	 * Mendapatkan komisi bersih yg didapat sub agent
	 * @param rate
	 * @param issuedFee
	 * @param airline
	 * @param originIata
	 * @param destinationIata
	 * @param tripMode
	 * @param flightNo
	 * @param seatClass
	 * @return
	 */
	BigDecimal getNetCommission(BigDecimal rate, BigDecimal issuedFee, Airline airline, String originIata, String destinationIata, int tripMode, String flightNo, Character seatClass);
	CommissionVO getCommission(Airline airline, FareInfo fareInfo);
	CommissionVO getCommission(Airline airline, String originIata, String destinationIata, int tripMode, String flightNo, Character seatClass);

	List<CommissionRangeVO> getCommissionRange(Airline airline);
	CommissionRangeVO getCommissionRange(Airline airline, String originIata, String destinationIata, int tripMode, String flightNo);
	
	/**
	 * Mengambil langsung nilai komisi berdasarkan seat class nya
	 * @param airline
	 * @param originIata
	 * @param destinationIata
	 * @param tripMode
	 * @param flightNo
	 * @param seatClass
	 * @return
	 */
	BigDecimal getCommissionRangeRate(Airline airline, String originIata, String destinationIata, int tripMode, String flightNo, Character seatClass);

}
