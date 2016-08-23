package com.big.web.b2b_big2.email.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.big.web.b2b_big2.agent.model.AgentVO;
import com.big.web.b2b_big2.agent.pojo.WHOLESALER;
import com.big.web.b2b_big2.finance.bank.model.TopUpVO;
import com.big.web.b2b_big2.webapp.controller.agent.TopUpInfo;
import com.big.web.model.User;

public interface IEmailManager {
	void sendBasicEmail(boolean sendLater, String subject, String recipent, String msg) throws Exception;

	void sendUserEmail(boolean sendLater, String subject, User user, String template, String url) throws Exception;
	
	void sendTopUpATMConfirm(boolean sendLater, AgentVO agentVO, TopUpVO topUp, String appUrl) throws Exception;

	void sendTopUpVAConfirm(boolean sendLaters, AgentVO agentVO, TopUpInfo topUp, String appUrl) throws Exception;
	/**
	 * Akan dipanggil oleh andri via url
	 * @param sendLaters
	 * @param agentVO
	 * @param topUp
	 * @param appUrl
	 * @throws Exception
	 */
	void sendTopUpVAPaidConfirm(boolean sendLaters, AgentVO agentVO, TopUpInfo topUp, String subject, String amount, String ccy, String appUrl) throws Exception;

	void sendFiles(boolean sendLater, String subject, String recipent, String msg, List<File> files) throws Exception;

	void sendSignUpAgentConfirm(boolean b, String text, User user, WHOLESALER wholeSaler, HttpServletRequest request) throws Exception;
}
