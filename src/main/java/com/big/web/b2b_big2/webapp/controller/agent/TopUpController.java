package com.big.web.b2b_big2.webapp.controller.agent;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.big.web.b2b_big2.agent.model.AgentVO;
import com.big.web.b2b_big2.email.service.IEmailManager;
import com.big.web.b2b_big2.finance.bank.BankCode;
import com.big.web.b2b_big2.finance.bank.TopUpPayment;
import com.big.web.b2b_big2.finance.bank.model.TopUpVO;
import com.big.web.b2b_big2.finance.exception.PendingTopUpException;
import com.big.web.b2b_big2.finance.exception.TopUpIncompleteRegException;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.Rupiah;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.b2b_big2.webapp.controller.BaseFormController;
import com.big.web.b2b_big2.webapp.util.RequestUtil;
import com.big.web.model.UserAccountVO;
import com.big.web.service.MailEngine;

/**
 * Controller class to upload Files.
 * Contoh penggunaan handle double submit
 */
@Controller
@SessionAttributes({"paymentBanks", "paymentVA", "paymentMethods"})
public class TopUpController extends BaseFormController {

	@Autowired
	private IEmailManager emailManager;
	
	//collect Input here for validation simplicity
	private static final String PATH_AMOUNT = "amount";
	private static final String PAGE_TOPUP = "/agent/topUp";
	private static final String PAGE_TOPUP_RESULT_ATM = "/agent/topUpResultATM";
	private static final String PAGE_TOPUP_RESULT_VA = "/agent/topUpResultVA";
	private static final String PAGE_TOPUP_RESULT = "/agent/topUpResult";
	private static final String PAGE_TOPUP_PENDING = "/agent/topUpPending";
	
    public TopUpController() {
        setCancelView("redirect:/home");
        setSuccessView(PAGE_TOPUP_RESULT);
    }
    
    @Autowired
    public void setMailEngine(MailEngine mailEngine) {
        this.mailEngine = mailEngine;
    }

    @Autowired
    public void setMessage(SimpleMailMessage message) {
        this.message = message;
    }

    @ModelAttribute
    @RequestMapping(value = "/agent/topup", method = RequestMethod.GET)
    public ModelAndView showForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
    	
		try {
			setAgentInfo(request, response);
		} catch (TopUpIncompleteRegException e){
			e.printStackTrace();
			return new ModelAndView("/agent/topUpIncompleteReg")
						.addObject("topup_va", new TopUpInfo(e.getAccount(), setupDao))
						.addObject("bankName", financeManager.getBankByCode(e.getAccount().getBank_code()).getAka())
						;
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/logout");
		}
		
    	request.setAttribute("agent", agent);
    	//to handle double submit/f5 button
        Map<String,?> map = RequestContextUtils.getInputFlashMap(request); 
        if (map!=null) {
        	if (map.containsKey("topup"))
        		return new ModelAndView(getSuccessView());
        	else if (map.containsKey("topup_pending"))
        		return new ModelAndView(PAGE_TOPUP_PENDING);
        	else if (map.containsKey("topup_atm"))
        		return new ModelAndView(PAGE_TOPUP_RESULT_ATM);
        	else if (map.containsKey("topup_va"))
        		return new ModelAndView(PAGE_TOPUP_RESULT_VA);
        }

        request.setAttribute("paymentBanks", financeManager.getBankSupportATM());
        request.setAttribute("paymentVA", financeManager.getBankSupportVirtualAccount());
        request.setAttribute("paymentMethods", financeManager.getPaymentMethods());
        
        TopUpInfo topUp = new TopUpInfo();
        topUp.setPaymentMethod(2);	//set virtual account as default
        
        return new ModelAndView(PAGE_TOPUP).addObject("maTopUp", topUp);
    }

    @RequestMapping(value = "/agent/topup", method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("maTopUp") TopUpInfo topUp, BindingResult errors, HttpServletRequest request,
    		RedirectAttributes ra) throws Exception {

        boolean globalError = false;
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        String appUrl = RequestUtil.getAppURL(request);
        topUp.setUserName(request.getRemoteUser());

        UserAccountVO ua = financeManager.getUserAccount(topUp.getUserName());
        topUp.setVAN(Utils.prettyVAN(ua.getNo()));
        topUp.setCurrency(ua.getCurrency());

        String balance = formatter.format(ua.getBalance() == null ? BigDecimal.ZERO.doubleValue() : ua.getBalance());
        topUp.setBalance(balance);
        
        TopUpPayment paymentMethod = TopUpPayment.getId(topUp.getPaymentMethod());
        BankCode bank = null;
        BigDecimal amount = new BigDecimal(1000);
        BigDecimal kelipatan = setupDao.getValueAsBigDecimal(ISetupDao.KEY_TOPUP_MULTIPLE);

        if (validator != null) { // validator is null during testing
            validator.validate(topUp, errors);

            switch (paymentMethod){
            case TRANSFER_ATM:
            	bank = BankCode.getCode(topUp.getBankCodeATM());
            	
                if (!Utils.isNumeric(topUp.getAmount())) {
    	            errors.rejectValue(PATH_AMOUNT, "errors.invalid", new Object[] { getText("topup.amount", request.getLocale()) },
    	                    "Amount");
                }
                
                //repair amount for comma/dot
                topUp.setAmount(topUp.getAmount().replaceAll("[,. ]", ""));

                amount = amount.multiply(new BigDecimal(topUp.getAmount()));
                
                if (amount.compareTo(kelipatan) < 0){
                	errors.rejectValue(PATH_AMOUNT, "topup.minimumTopUp", new Object[] { getText(Rupiah.format(kelipatan.toPlainString(), true), request.getLocale()) },
                			"Amount");
                }
                
            	break;
            case VIRTUAL_ACCOUNT:
            	bank = BankCode.getCode(topUp.getBankCodeVA());
            	
    	        String minimum = formatter.format(kelipatan == null ? BigDecimal.ZERO.doubleValue() : kelipatan.doubleValue());
            	topUp.setMinimum(minimum);

            	String outstanding = formatter.format(ua.getOutstanding_balance() == null ? BigDecimal.ZERO.doubleValue() : ua.getOutstanding_balance().doubleValue());
            	topUp.setOutstanding(outstanding);
            	break;
            	
            }

            if (Utils.isForbidAdmin(request)){ 
            	globalError = true;
				saveError(request, getText("errors.admin.forbidTransaction", request.getLocale()));
            }
            if (errors.hasErrors() || globalError) {
                request.setAttribute("paymentBanks", financeManager.getBankSupportATM());
                request.setAttribute("paymentVA", financeManager.getBankSupportVirtualAccount());
                request.setAttribute("paymentMethods", financeManager.getPaymentMethods());
            	request.setAttribute("maTopUp", topUp);
                return PAGE_TOPUP;
            }
        }
        
        
        //utk mencegah user submit 2x/pencet F5
        ra.addFlashAttribute("bankName", financeManager.getBankByCode(bank.getBankCode()).getAka());        	
        
        AgentVO agentVO = agentManager.getAgentByUser(request.getRemoteUser());
        
        if (paymentMethod == TopUpPayment.VIRTUAL_ACCOUNT){
        	ra.addFlashAttribute("topup_va", topUp);

        	emailManager.sendTopUpVAConfirm(true, agentVO, topUp, appUrl);

        	return "redirect:/agent/topup";	//redirect utk dikembalikan ke GET supaya tidak double submit
        }

        topUp.setAdminFee("0 (FREE)");
        topUp.setTotal(topUp.getAmount() + ".000");      

        TopUpVO topUpVO = null;
        try{
			topUpVO = financeManager.topUp(bank, paymentMethod, topUp.getUserName(), amount);

	        String pay = formatter.format(topUpVO.getAmountToPaid().doubleValue());

	        ra.addFlashAttribute("pay", pay);
        	ra.addFlashAttribute("topup_atm", topUp);
        	
        	emailManager.sendTopUpATMConfirm(true, agentVO, topUpVO, appUrl);

        }catch(PendingTopUpException e){
			saveError(request, getText("topup.pendingPaid", request.getLocale()));
			
			//fix timetopay to display gmt using calendar trick
			Calendar cal = Calendar.getInstance();
			cal.setTime(e.getData().getTimeToPay());
			e.getData().setTimeToPay(cal.getTime());
			
			ra.addFlashAttribute("topup_pending", e.getData());
			if (e.getData().getPayment_type() == TopUpPayment.VIRTUAL_ACCOUNT.getId())
				ra.addFlashAttribute("pay", formatter.format(e.getData().getAmount().doubleValue()));
			else
				ra.addFlashAttribute("pay", formatter.format(e.getData().getAmountToPaid().doubleValue()));
			
			ra.addFlashAttribute("bankName", financeManager.getBankByCode(e.getData().getBankCode()).getAka());

			return "redirect:/agent/topup";

        }catch (Exception e){
        	e.printStackTrace();
			saveError(request, getText("errors.detail", new String[]{e.getMessage()}, request.getLocale()));

			return "redirect:/agent/topup";
        }
        
        return "redirect:/agent/topup";	//redirect utk dikembalikan ke GET supaya tidak double submit
    }

    /*
    @RequestMapping(value = "/agent/topup2", method = RequestMethod.POST)
    public String onSubmit2(@ModelAttribute("maTopUp2") TopUpInfo topUp, BindingResult errors, HttpServletRequest request,
    		RedirectAttributes ra) throws Exception {

        if (request.getParameter("cancel") != null) {
            return getCancelView();
        }

        boolean globalError = false;
        String appUrl = RequestUtil.getAppURL(request);
        BigDecimal amount = new BigDecimal(1000);
        if (validator != null) { // validator is null during testing
            validator.validate(topUp, errors);

            if (!Utils.isNumeric(topUp.getAmount())) {
	            errors.rejectValue(PATH_AMOUNT, "errors.invalid", new Object[] { getText("topup.amount", request.getLocale()) },
	                    "Amount");
            }
            
            //repair amount for comma/dot
            topUp.setAmount(topUp.getAmount().replaceAll("[,. ]", ""));
            
            amount = amount.multiply(new BigDecimal(topUp.getAmount()));
            
            BigDecimal kelipatan = setupDao.getValueAsBigDecimal(ISetupDao.KEY_TOPUP_MULTIPLE);
            
            if (amount.compareTo(kelipatan) < 0){
            	errors.rejectValue(PATH_AMOUNT, "topup.minimumTopUp", new Object[] { getText(Rupiah.format(kelipatan.toPlainString(), true), request.getLocale()) },
            			"Amount");
            }

            if (Utils.isForbidAdmin(request)){ 
            	globalError = true;
				saveError(request, getText("errors.admin.forbidTransaction", request.getLocale()));
            }
            
            if (errors.hasErrors() || globalError) {
                request.setAttribute("paymentBanks", financeManager.getBankSupportATM());
                request.setAttribute("paymentVA", financeManager.getBankSupportVirtualAccount());
                request.setAttribute("paymentMethods", financeManager.getPaymentMethods());
            	request.setAttribute("maTopUp", topUp);
                return PAGE_TOPUP;
            }
        }
        
        //add/get agent account
//        BigDecimal amt = new BigDecimal(topUpResult.getAmount()).multiply(new BigDecimal(1000));
//        BigDecimal amt = new BigDecimal(topUpResult.getAmount()).multiply(new BigDecimal(1000000));
        
        TopUpPayment paymentMethod = TopUpPayment.getId(topUp.getPaymentMethod());
        BankCode bank = null;
        if (paymentMethod == TopUpPayment.TRANSFER_ATM){
        	bank = BankCode.getCode(topUp.getBankCodeATM());
        }else if (paymentMethod == TopUpPayment.VIRTUAL_ACCOUNT){
        	bank = BankCode.getCode(topUp.getBankCodeVA());
        }
        
        topUp.setUserName(request.getRemoteUser());
        topUp.setAdminFee("0 (FREE)");
        topUp.setTotal(topUp.getAmount() + ".000");        
        
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");

        TopUpVO topUpVO = null;
		try {
			topUpVO = financeManager.topUp(bank, paymentMethod, topUp.getUserName(), amount);
			
			UserAccountVO ua = financeManager.getUserAccount(topUp.getUserName());
			
	        topUp.setVAN(Utils.prettyVAN(ua.getNo()));
	        
	        //utk mencegah user submit 2x/pencet F5
	        ra.addFlashAttribute("bankName", financeManager.getBankByCode(bank.getBankCode()).getAka());        	
	        
	        AgentVO agentVO = agentManager.getAgentByUser(request.getRemoteUser());

	        String pay = formatter.format(topUpVO.getAmountToPaid().doubleValue());

	        ra.addFlashAttribute("pay", pay);
	        
	        if (paymentMethod == TopUpPayment.TRANSFER_ATM){
	             
	        	ra.addFlashAttribute("topup_atm", topUp);
	        	
	        	emailManager.sendTopUpATMConfirm(true, agentVO, topUpVO, appUrl);
	        }
	        else if (paymentMethod == TopUpPayment.VIRTUAL_ACCOUNT){
	        	ra.addFlashAttribute("topup_va", topUp);
	        	topUpVO.setAccount_no(Utils.prettyVAN(topUpVO.getAccount_no()));
	        	emailManager.sendTopUpVAConfirm(true, agentVO, topUp, appUrl);
	        }
		} catch (PendingTopUpException e) {
			saveError(request, getText("topup.pendingPaid", request.getLocale()));
			
			//fix timetopay to display gmt using calendar trick
			Calendar cal = Calendar.getInstance();
			cal.setTime(e.getData().getTimeToPay());
			e.getData().setTimeToPay(cal.getTime());
			
			ra.addFlashAttribute("topup_pending", e.getData());
			if (e.getData().getPayment_type() == TopUpPayment.VIRTUAL_ACCOUNT.getId())
				ra.addFlashAttribute("pay", formatter.format(e.getData().getAmount().doubleValue()));
			else
				ra.addFlashAttribute("pay", formatter.format(e.getData().getAmountToPaid().doubleValue()));
			
			ra.addFlashAttribute("bankName", financeManager.getBankByCode(e.getData().getBankCode()).getAka());

			return "redirect:/agent/topup";
		}catch (Exception e){
			e.printStackTrace();
			saveError(request, getText("errors.detail", new String[]{e.getMessage()}, request.getLocale()));

			return "redirect:/agent/topup";
		}
                
        return "redirect:/agent/topup";	//redirect utk dikembalikan ke GET supaya tidak double submit
    }
    */
}
