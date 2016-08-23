package com.big.web.b2b_big2.agent.service;

import java.util.Collection;
import java.util.List;

import com.big.web.b2b_big2.agent.model.AgentTrxUsdVO;
import com.big.web.b2b_big2.agent.model.AgentTrxVO;
import com.big.web.b2b_big2.agent.model.AgentVO;
import com.big.web.b2b_big2.agent.model.Whole_SalerVO;
import com.big.web.b2b_big2.agent.pojo.ACCOUNT_STATUS;
import com.big.web.b2b_big2.agent.pojo.Agent;
import com.big.web.b2b_big2.agent.pojo.WHOLESALER;
import com.big.web.b2b_big2.finance.bank.TOPUP_STATUS;
import com.big.web.b2b_big2.finance.bank.model.TopUpVO;
import com.big.web.b2b_big2.finance.cart.pojo.Cart;
import com.big.web.b2b_big2.report.pojo.Revenue;
import com.big.web.b2b_big2.util.AppInfo;
import com.big.web.model.User;
import com.big.web.model.UserAccountVO;

public interface IAgentManager {
	void signUpSubAgent(User user, WHOLESALER wholeSaler) throws Exception;
	
	void saveAgent(AgentVO agent);
	void updateAgent(AgentVO agent);
	void removeAgent(AgentVO agent);
	
	List<AgentVO> getAgents();
	List<AgentVO> getSubAgents();
	List<AgentVO> searchAll(String searchTerm);
	List<AgentVO> searchAgents(String searchTerm);
	List<AgentVO> searchSubAgents(String searchTerm);
	List<AgentVO> searchSubAgents(String searchTerm, ACCOUNT_STATUS accountStatus, Whole_SalerVO wholeSaler);
	
	AgentVO getAgentById(String uid);
	AgentVO getAgentByAgentId(Long id);
	AgentVO getAgentByUserId(Long userId);
	AgentVO getAgentByUser(String userName);
	
	//UserAccountVO generateTopUp(BankCode bank, TopUpPayment paymentMethod, String userName, BigDecimal amount);
//	UserAccountVO generateTopUp(String userName, BigDecimal amount);

	AgentTrxVO getAgentAccountByUser(String userName);
	AgentTrxUsdVO getAgentAccountUsdByUser(String userName);
	
	List<Revenue> getReportRevenue(String dateFrom, String dateTo) throws Exception;
	List<TopUpVO> getReportTopUp(String dateFrom, String dateTo, TOPUP_STATUS[] topUpStatus) throws Exception ;
//	List<Booking> getReportBooking(String dateFrom, String dateTo);
	
	boolean isPermitToBuy(String userName, Cart cart);
	boolean isPermitToBuy(Agent agent);
	
	List<Whole_SalerVO> getWholeSalers();
	Long getWholeSalerAgentId(String uid);
	Whole_SalerVO getWholeSaler(Long userId);
	Whole_SalerVO getWholeSalerByAgency(Long agent_id);
	
	List<AppInfo> getAppInfo();



	
}
