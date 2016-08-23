package com.big.web.b2b_big2.webapp.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.big.web.Constants;
import com.big.web.b2b_big2.agent.model.AgentVO;
import com.big.web.b2b_big2.agent.pojo.Agent;
import com.big.web.b2b_big2.agent.pojo.PackageType;
import com.big.web.b2b_big2.agent.service.IAgentManager;
import com.big.web.b2b_big2.finance.exception.TopUpIncompleteRegException;
import com.big.web.b2b_big2.finance.service.IFinanceManager;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.Currency;
import com.big.web.b2b_big2.util.Rupiah;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.message.IMessageManager;
import com.big.web.message.PROCESS_TYPE;
import com.big.web.model.User;
import com.big.web.model.UserAccountVO;
import com.big.web.service.MailEngine;
import com.big.web.service.UserManager;

/**
 * Implementation of <strong>SimpleFormController</strong> that contains
 * convenience methods for subclasses.  For example, getting the current
 * user and saving messages/errors. This class is intended to
 * be a base class for all Form controllers.
 *
 * <p><a href="BaseFormController.java.html"><i>View Source</i></a></p>
 *
 */
public class BaseFormController implements ServletContextAware {
    public static final String MESSAGES_KEY = "successMessages";
    public static final String ERRORS_MESSAGES_KEY = "errors";
    private static Configuration configHibernate = null;// = new Configuration().configure();

    protected final transient Log log = LogFactory.getLog(getClass());
    private UserManager userManager = null;
    protected MailEngine mailEngine = null;
    protected SimpleMailMessage message = null;
    protected String templateName = "accountCreated.vm";
    protected String cancelView;
    protected String successView;

    private MessageSourceAccessor messages;
    private ServletContext servletContext;
    
    @Autowired protected Agent agent;

	@Autowired protected IAgentManager agentManager;
	@Autowired protected IFinanceManager financeManager;

	@Autowired protected IMessageManager messageManager;
	@Autowired protected ISetupDao setupDao;

	
	@Autowired(required = false)
	protected
    Validator validator;

    @Autowired
    public void setMessages(MessageSource messageSource) {
        messages = new MessageSourceAccessor(messageSource);
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public UserManager getUserManager() {
        return this.userManager;
    }

    @SuppressWarnings("unchecked")
    public void saveError(HttpServletRequest request, String error) {
        List errors = (List) request.getSession().getAttribute(ERRORS_MESSAGES_KEY);
        if (errors == null) {
            errors = new ArrayList();
        }
        errors.add(error);
        request.getSession().setAttribute(ERRORS_MESSAGES_KEY, errors);
        
    }

    @SuppressWarnings("unchecked")
    public void saveError(HttpServletRequest request, PROCESS_TYPE processType, String errorMessage, Exception exception) {
    	saveError(request, errorMessage);
    	
    	try {
			messageManager.saveError(errorMessage, processType, request, exception);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @SuppressWarnings("unchecked")
    public void saveMessage(HttpServletRequest request, String msg) {
        List messages = (List) request.getSession().getAttribute(MESSAGES_KEY);

        if (messages == null) {
            messages = new ArrayList();
        }

        messages.add(msg);
        request.getSession().setAttribute(MESSAGES_KEY, messages);
    }

    /**
     * Convenience method for getting a i18n key's value.  Calling
     * getMessageSourceAccessor() is used because the RequestContext variable
     * is not set in unit tests b/c there's no DispatchServlet Request.
     *
     * @param msgKey
     * @param locale the current locale
     * @return
     */
    public String getText(String msgKey, Locale locale) {
        return messages.getMessage(msgKey, locale);
    }

    /**
     * Convenient method for getting a i18n key's value with a single
     * string argument.
     *
     * @param msgKey
     * @param arg
     * @param locale the current locale
     * @return
     */
    public String getText(String msgKey, String arg, Locale locale) {
        return getText(msgKey, new Object[] { arg }, locale);
    }

    /**
     * Convenience method for getting a i18n key's value with arguments.
     *
     * @param msgKey
     * @param args
     * @param locale the current locale
     * @return
     */
    public String getText(String msgKey, Object[] args, Locale locale) {
        return messages.getMessage(msgKey, args, locale);
    }

    /**
     * Convenience method to get the Configuration HashMap
     * from the servlet context.
     *
     * @return the user's populated form from the session
     */
    public Map getConfiguration() {
        Map config = (HashMap) servletContext.getAttribute(Constants.CONFIG);

        // so unit tests don't puke when nothing's been set
        if (config == null) {
            return new HashMap();
        }

        return config;
    }

    /**
     * Set up a custom property editor for converting form inputs to real objects
     * @param request the current request
     * @param binder the data binder
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) {
        binder.registerCustomEditor(Integer.class, null,
                                    new CustomNumberEditor(Integer.class, null, true));
        binder.registerCustomEditor(Long.class, null,
                                    new CustomNumberEditor(Long.class, null, true));
        binder.registerCustomEditor(byte[].class,
                                    new ByteArrayMultipartFileEditor());
        SimpleDateFormat dateFormat = 
            new SimpleDateFormat(getText("date.format", request.getLocale()));
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null, 
                                    new CustomDateEditor(dateFormat, true));

        if (configHibernate == null){
        	configHibernate = new Configuration().configure();
        	configHibernate.buildMappings();
        }
        
    }

    /**
     * Convenience message to send messages to users, includes app URL as footer.
     * 
     * @param user the user to send a message to.
     * @param msg the message to send.
     * @param url the URL of the application.
     */
    protected void sendUserMessage(User user, String msg, String url) {
        if (log.isDebugEnabled()) {
            log.debug("sending e-mail to user [" + user.getEmail() + "]...");
        }

        message.setTo(user.getFirstName() + "<" + user.getEmail() + ">");

        Map<String, Serializable> model = new HashMap<String, Serializable>();
        model.put("user", user);

        // TODO: once you figure out how to get the global resource bundle in
        // WebWork, then figure it out here too.  In the meantime, the Username
        // and Password labels are hard-coded into the template. 
        // model.put("bundle", getTexts());
        model.put("message", msg);
        model.put("applicationURL", url);
        mailEngine.sendMessage(message, templateName, model);
    }

    @Autowired
    public void setMailEngine(MailEngine mailEngine) {
        this.mailEngine = mailEngine;
    }

    @Autowired
    public void setMessage(SimpleMailMessage message) {
        this.message = message;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
   
    public final BaseFormController setCancelView(String cancelView) {
        this.cancelView = cancelView;
        return this;
    }

    public final String getCancelView() {
        // Default to successView if cancelView is invalid
        if (this.cancelView == null || this.cancelView.length()==0) {
            return getSuccessView();
        }
        return this.cancelView;   
    }

    public final String getSuccessView() {
        return this.successView;
    }
    
    public final BaseFormController setSuccessView(String successView) {
        this.successView = successView;
        return this;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    protected ServletContext getServletContext() {
        return servletContext;
    }
    
    protected void cleanSession(ModelMap model, HttpSession session, String key) {
    	if (model.containsAttribute(key)){
    		model.remove(key);
    		session.removeAttribute(key);
    	}    	
    }

    protected void incompleteTopUp(){
    	
    }
    
    protected void setAgentInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String userName = request.getRemoteUser();
		
		if (userName.equals("admin")){
			agent.setId("0");
			agent.setName("admin");
			agent.setUserName("admin");
			agent.setEmail("");
			agent.setBalanceUsd("0");
			agent.setBalanceIdr("0");
			agent.setPackageType(PackageType.ADMINISTRATOR);
			agent.setTravelName(getText("webapp.name", request.getLocale()));
			agent.setUserId(new Long(0));
			
			request.setAttribute("agent", agent);
			return;
		}

		AgentVO agentVO = agentManager.getAgentByUser(userName);
		//berarti belum terdaftar atau sbg ROLE_MANDIRA_ADMIN / ROLE_MANDIRA_SPV
		if (agentVO == null){
//				sendErrorResponse(message, HttpURLConnection.HTTP_FORBIDDEN);
			
            if (request.isUserInRole(Constants.MANDIRA_ADMIN_ROLE)
            		|| request.isUserInRole(Constants.MANDIRA_SPV_ROLE)
            		|| request.isUserInRole(Constants.SPV_ROLE)
            		) {
            	User usr = userManager.getUserByUsername(userName);
            	
            	agent.reset();
            	
    			agent.setId("-4");
    			agent.setName(usr.getFullName());
    			agent.setUserName(usr.getUsername());
    			agent.setEmail(usr.getEmail());
    			agent.setBalanceUsd("0");
    			agent.setBalanceIdr("0");
    			agent.setPackageType(PackageType.ADMINISTRATOR);
    			agent.setTravelName(getText("webapp.partner1.name", request.getLocale()));
    			agent.setUserId(usr.getId());
            	
            }else{
//            	String logoutUrl = RequestUtil.getAppURL(request) + "logout";	need to investigate first if getAppURL contains / in the last
            	String logoutUrl = request.getContextPath() + response.encodeRedirectURL("/logout"); 
            	
            	response.sendRedirect(logoutUrl);
//				return new ModelAndView("redirect:/logout");
            }
            
            request.setAttribute("agent", agent);            
            return;
		}
		
		UserAccountVO acc_Idr = financeManager.getUserAccount(userName, Currency.CURR_IDR);
		UserAccountVO acc_Dollar = financeManager.getUserAccount(userName, Currency.CURR_USD);

		agent.reset();
		agent.loadFromModel(agentVO, acc_Idr, acc_Dollar);
		
		request.setAttribute("agent", agent);
		
		if (acc_Idr != null){
			String val = acc_Idr.getTrx_enabled() == null ? "0" : acc_Idr.getTrx_enabled();
			
			if (!val.equals("1")){
				throw new TopUpIncompleteRegException(acc_Idr, "Your Registration is not complete. Outstanding is " + acc_Idr.getOutstanding_balance());
			}
			
		}
		
		if (acc_Dollar != null){
			String val = acc_Dollar.getTrx_enabled() == null ? "0" : acc_Dollar.getTrx_enabled();
			
			if (!val.equals("1")){
				throw new TopUpIncompleteRegException(acc_Dollar, "Your Registration is not complete. Outstanding is " + acc_Dollar.getOutstanding_balance());
			}
			
			
		}
		/*
		AgentTrxVO accIdr = agentManager.getAgentAccountByUser(userName);		
		agent.setBalanceUsd(accDollar == null ? "0" : accDollar.getSaldo().toPlainString());

		AgentTrxUsdVO accDollar = agentManager.getAgentAccountUsdByUser(userName);		
		agent.setBalanceIdr(accIdr == null ? "0" : Rupiah.format(accIdr.getSaldo().toPlainString(), false));
		*/
    	
    }
    
    /**
     * Mencari panjang field dari suatu kolom di table
     * @param cls nama table class. contoh: User.class
     * @param propertyName nama kolom/properti. contoh: username
     * @return 255 jika tipenya String dan tidak terdefinisi length-nya
     */
    public static int getColumnLength(Class<?> cls, String propertyName) {
        PersistentClass persistentClass = configHibernate.getClassMapping(cls.getName());
        Property property = persistentClass.getProperty(propertyName);
        int length = ((Column) property.getColumnIterator().next()).getLength();
        return length;
    }

    /**
     * h2u:
     * <p>
     * validateField(User.class, "username", user.getUsername(), errors, false);
      <br>      	validateField(User.class, "password", user.getPassword(), errors, false);
          <br>  	validateField(User.class, "passwordHint", user.getPasswordHint(), errors, true);
     * @param tableClass
     * @param propertyName
     * @param value
     * @param errors
     * @param allowEmpty
     */
    public void validateField(Class<?> tableClass, String propertyName, String value, final BindingResult errors, boolean allowEmpty){
    	
    	if (!allowEmpty && Utils.isEmpty(value)){
    		errors.rejectValue(propertyName, "errors.required", new Object[] { propertyName }, null);
    		return;
    	}
    	
    	int max_len = getColumnLength(tableClass, propertyName);
    	if (value != null && value.trim().length() > max_len){
    		errors.rejectValue(propertyName, "errors.field.tooLong", new Object[] { propertyName }, null);
    	}
    	
    }

}
