package com.big.web.b2b_big2.agent.dao;

import java.util.List;

import com.big.web.b2b_big2.agent.model.AgentVO;
import com.big.web.b2b_big2.agent.model.Whole_SalerVO;
import com.big.web.b2b_big2.agent.pojo.ACCOUNT_STATUS;
import com.big.web.b2b_big2.finance.bank.model.CIMBVANCounterVO;
import com.big.web.model.User;

public interface IAgentDao {
	void update(AgentVO agent);
	AgentVO getAgent(String uid);

	List<AgentVO> getAgents();
	List<AgentVO> getSubAgents();
	
	AgentVO getAgentByUserId(Long userId);
	AgentVO getAgentByUser(String userName);
	AgentVO getAgentByAgentId(Long agentId);
	
	boolean login(String username, String password);

	User getUser(String userName);
	
	void saveAgent(AgentVO obj);
	void removeAgent(Long userId);
	
//	void saveVAN(VANGroupVO van);
	void saveVAN(CIMBVANCounterVO van);

	Long getWholeSalerAgentId(String uid);
	String getWholeSalerVA_Code(Long agentId);
	Whole_SalerVO getWholeSaler(Long userId);
	Whole_SalerVO getWholeSalerByAgency(Long agent_id);
	
	boolean isSubAgent(AgentVO agent);

	List<Whole_SalerVO> getWholeSalers();

	List<AgentVO> searchAll(String searchTerm);
	List<AgentVO> searchAgents(String searchTerm);
	List<AgentVO> searchSubAgents(String searchTerm);
	List<AgentVO> searchSubAgents(String searchTerm, ACCOUNT_STATUS accountStatus, Whole_SalerVO wholeSaler);


}
