package com.big.web.b2b_big2.email.service.impl;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.big.web.b2b_big2.agent.model.AgentVO;
import com.big.web.b2b_big2.agent.pojo.PackageType;
import com.big.web.b2b_big2.agent.pojo.WHOLESALER;
import com.big.web.b2b_big2.email.EMAIL_STATUS;
import com.big.web.b2b_big2.email.dao.IEmailDao;
import com.big.web.b2b_big2.email.model.EmailVO;
import com.big.web.b2b_big2.email.service.IEmailManager;
import com.big.web.b2b_big2.finance.bank.model.TopUpVO;
import com.big.web.b2b_big2.finance.dao.IFinanceDao;
import com.big.web.b2b_big2.util.Rupiah;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.b2b_big2.webapp.controller.agent.TopUpInfo;
import com.big.web.b2b_big2.webapp.util.RequestUtil;
import com.big.web.model.User;
import com.big.web.model.UserAccountVO;
import com.big.web.service.MailEngine;

@Service("emailManager")
@Transactional
public class EmailManagerImpl implements IEmailManager{

	@Autowired
	IEmailDao emailDao;

	@Autowired
	protected IFinanceDao financeDao;

	@Autowired
	protected MailEngine mailEngine;

	
	@Autowired
	protected SimpleMailMessage mailMessage;
	
	private void sendEmail(boolean sendLater, EmailVO emailVO, Map<String, Serializable> model) throws Exception{
		if (sendLater){
			//must create cache file as contents
			String contents = mailEngine.mergeTemplateIntoString(emailVO.getTemplate(), model);
			
			Utils.stringToFile(contents, new File(emailDao.getEmailCacheDir() + emailVO.getId() + ".txt"));
			
			emailVO.setStatus(EMAIL_STATUS.PENDING.getFlag());
			emailVO.setLastUpdate(new Date());
			emailDao.save(emailVO);
			return;
		}

		//send email right away, no need to insert into email table
		mailMessage.setSubject(emailVO.getSubject());
		try {
			mailMessage.setTo(emailVO.getRecipient());
			mailEngine.sendMessage(mailMessage, emailVO.getTemplate(), model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendTopUpATMConfirm(boolean sendLater, AgentVO agentVO, TopUpVO topUp, String appUrl) throws Exception{

		EmailVO emailVO = new EmailVO();
		
		emailVO.setSubject("Transaksi " + topUp.getTrans_code() + " untuk Top Up");
		emailVO.setRecipient(agentVO.getEmail1());
		emailVO.setTemplate("topUpATMConfirm.vm");
		
        Map<String, Serializable> model = new HashMap<String, Serializable>();
        
        if (agentVO != null)
        	model.put("agent", agentVO);
        
        if (topUp != null)
        	model.put("topup", topUp);
        
        model.put("message", "Top Up Confirmation");
        model.put("applicationURL", appUrl);
        
        sendEmail(sendLater, emailVO, model);

	}

	@Override
	public void sendTopUpVAConfirm(boolean sendLater, AgentVO agentVO, TopUpInfo topUp, String appUrl) throws Exception{

		EmailVO emailVO = new EmailVO();
		emailVO.setSubject("Transaksi Top Up Cimb Niaga");
		emailVO.setRecipient(agentVO.getEmail1());
		emailVO.setTemplate("topUpVAConfirm.vm");
//		.addObject("bankName", financeManager.getBankByCode(e.getAccount().getBank_code()).getAka())
		Map<String, Serializable> model = new HashMap<String, Serializable>();
		
		if (agentVO != null)
			model.put("agent", agentVO);
		
        if (topUp != null)
        	model.put("topup", topUp);
//        model.put("message", "Top Up Confirmation");
        model.put("applicationURL", appUrl);
		model.put("bankName", financeDao.getBank(topUp.getBankCodeVA()).getAka());
        
		sendEmail(sendLater, emailVO, model);
	}

	@Override
	public void sendTopUpVAPaidConfirm(boolean sendLater, AgentVO agentVO, TopUpInfo topUp, String subject, String amount, String ccy, String appUrl) throws Exception{

		EmailVO emailVO = new EmailVO();
		emailVO.setSubject(Utils.isEmpty(subject) ? "Terima kasih telah melakukan Pembayaran Top Up" : subject);
		emailVO.setRecipient(agentVO.getEmail1());
		emailVO.setTemplate("topUpVAPaidConfirm.vm");
//		.addObject("bankName", financeManager.getBankByCode(e.getAccount().getBank_code()).getAka())
		Map<String, Serializable> model = new HashMap<String, Serializable>();
		
		if (agentVO != null)
			model.put("agent", agentVO);
		
		if (topUp != null)
			model.put("topup", topUp);
		
		model.put("amount", Utils.isEmpty(amount) ? "" : amount);
		model.put("ccy", ccy);
		model.put("applicationURL", appUrl);
		model.put("bankName", financeDao.getBank(topUp.getBankCodeVA()).getAka());
		
		sendEmail(sendLater, emailVO, model);
	}

	@Override
	public void sendSignUpAgentConfirm(boolean sendLater, String subject, User user, WHOLESALER wholeSaler, HttpServletRequest request) throws Exception {
		EmailVO emailVO = new EmailVO();
		
		emailVO.setSubject(subject);
		emailVO.setRecipient(user.getFirstName() + "<" + user.getEmail() + ">");
//		emailVO.setTemplate("accountCreated.vm");
		
		Long _id = user.getAgent().getAgent_id();
		//_id tujuannya buat nanti bedain mau pake template yg mana
		emailVO.setTemplate(_id != null ? "accountSubAgentCreated.vm" : "accountCreated.vm");

        Map<String, Serializable> model = new HashMap<String, Serializable>();
        model.put("user", user);
//        model.put("message", message);	//dipindah ke template itu sendiri
    
        UserAccountVO acc = financeDao.getUserAccount(user.getUsername());
        
        model.put("pkg", PackageType.get(user.getAgent().getPackage_code()) );
        model.put("accountVA", Utils.prettyVAN(acc.getNo()));
        model.put("applicationURL", RequestUtil.getAppURL(request));

        
        BigDecimal firstTopUpAmount = financeDao.getFirstTopUpAmount(user.getAgent(), wholeSaler);
        
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        String pay = formatter.format(firstTopUpAmount.doubleValue());
        model.put("pay", pay + " IDR");
        
        model.put("amountWords", Rupiah.say(firstTopUpAmount.doubleValue()) + " RUPIAH");

        sendEmail(sendLater, emailVO, model);

	}

	@Override
	public void sendUserEmail(boolean sendLater, String subject, User user, String template, String url) throws Exception{
		
		EmailVO emailVO = new EmailVO();

		emailVO.setSubject(subject);
		emailVO.setRecipient(user.getFullName() + "<" + user.getEmail() + ">");
		emailVO.setTemplate(template);
		
		Map<String, Serializable> model = new HashMap<String, Serializable>();
        model.put("user", user);
        model.put("applicationURL", url);
		
		sendEmail(sendLater, emailVO, model);

	}

	@Override
	public void sendFiles(boolean sendLater, String subject, String recipent, String msg, List<File> files) throws Exception {
		EmailVO emailVO = new EmailVO();
		
		emailVO.setSubject(subject);
		emailVO.setRecipient(recipent);
		emailVO.setTemplate("simple.vm");		
		
		if (files.size() > 3)
			throw new RuntimeException("Too many attachment files !");
		
		if (files.size() == 1)
			emailVO.setCache_attachment1(files.get(0).getName());

		if (files.size() == 2){
			emailVO.setCache_attachment1(files.get(0).getName());
			emailVO.setCache_attachment2(files.get(1).getName());
			
		}
		if (files.size() == 3){
			emailVO.setCache_attachment1(files.get(0).getName());
			emailVO.setCache_attachment2(files.get(1).getName());
			emailVO.setCache_attachment3(files.get(2).getName());
		}
		
        Map<String, Serializable> model = new HashMap<String, Serializable>();
        model.put("message", msg);
		
		if (sendLater){
			//must create cache file as contents
			String contents = mailEngine.mergeTemplateIntoString(emailVO.getTemplate(), model);
			
			
			for (File file : files){
				String destFile = emailDao.getEmailCacheDir() + emailVO.getId() + "/" + file.getName();

				FileUtils.moveFile(file, new File(destFile));
			}
//			FileUtils.copyFile(pdfFile, new File(destFile));
			
			Utils.stringToFile(contents, new File(emailDao.getEmailCacheDir() + emailVO.getId() + ".txt"));
			
			emailVO.setStatus(EMAIL_STATUS.PENDING.getFlag());
			emailVO.setLastUpdate(new Date());
			emailDao.save(emailVO);
			return;
		}

		//send email right away, no need to insert into email table
		mailMessage.setSubject(emailVO.getSubject());
		try {
			mailMessage.setTo(emailVO.getRecipient());
			
			mailEngine.sendMessage(new String[]{emailVO.getRecipient()}, null, files, msg, subject);
//			mailEngine.sendMessage(mailMessage, emailVO.getTemplate(), model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void sendBasicEmail(boolean sendLater, String subject, String recipent, String msg)
			throws Exception {
		EmailVO emailVO = new EmailVO();
		
		emailVO.setSubject(subject);
		emailVO.setRecipient(recipent);
		emailVO.setTemplate("simple.vm");		
		
        Map<String, Serializable> model = new HashMap<String, Serializable>();
        model.put("message", msg);
		
		if (sendLater){
			//must create cache file as contents
			String contents = mailEngine.mergeTemplateIntoString(emailVO.getTemplate(), model);
			
			Utils.stringToFile(contents, new File(emailDao.getEmailCacheDir() + emailVO.getId() + ".txt"));
			
			emailVO.setStatus(EMAIL_STATUS.PENDING.getFlag());
			emailVO.setLastUpdate(new Date());
			emailDao.save(emailVO);
			return;
		}

		//send email right away, no need to insert into email table
		mailMessage.setSubject(emailVO.getSubject());
		try {
			mailMessage.setTo(emailVO.getRecipient());
			
			mailEngine.sendMessage(new String[]{emailVO.getRecipient()}, null, null, msg, subject);
//			mailEngine.sendMessage(mailMessage, emailVO.getTemplate(), model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
