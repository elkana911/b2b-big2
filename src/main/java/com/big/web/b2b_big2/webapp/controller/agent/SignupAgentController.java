package com.big.web.b2b_big2.webapp.controller.agent;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.commons.lang.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.big.web.Constants;
import com.big.web.b2b_big2.agent.model.Whole_SalerVO;
import com.big.web.b2b_big2.agent.pojo.WHOLESALER;
import com.big.web.b2b_big2.email.service.IEmailManager;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.webapp.controller.BaseFormController;
import com.big.web.model.User;
import com.big.web.model.UserAccountVO;
import com.big.web.service.RoleManager;
import com.big.web.service.UserExistsException;

/**
 * Controller to signup new agents.
 * prinsipnya sama dengan new users tp dengan tambahan field
 *
 */
@Controller
@SessionAttributes({"wholesaler"})
public class SignupAgentController extends BaseFormController {
    private RoleManager roleManager;
    
    @Autowired
    IEmailManager emailManager;
    
    @Autowired
    public void setRoleManager(final RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    public SignupAgentController() {
        setCancelView("redirect:login");
        setSuccessView("redirect:login");
//        setSuccessView("redirect:home");
    }

    public static final String RECAPTCHA_HTML = "reCaptchaHtml";
    
    /**
     * Tell mandira to add parameter ..url...?ccode=113 to custom sign up process 
     * @param agentId
     * @param request
     * @param redirectAttributes
     * @return
     */
    @ModelAttribute("agentuser")
    @RequestMapping(value = "/agentsignup", method = RequestMethod.GET)
    public ModelAndView showForm(@RequestParam(required = false, value="ccode")Long agentId, final HttpServletRequest request, final RedirectAttributes redirectAttributes) {

        if (isFormSubmission(request)) {
            return new ModelAndView("redirect:/home");
        }
        
        boolean useSSO = false;

        Map<String,?> map = RequestContextUtils.getInputFlashMap(request); 
        if (map!=null) {
        	/*
        	if (map.containsKey("topup"))
        		return new ModelAndView(getSuccessView());
        	else if (map.containsKey("topup_pending"))
        		return new ModelAndView(PAGE_TOPUP_PENDING);
        	else if (map.containsKey("topup_atm"))
        		return new ModelAndView(PAGE_TOPUP_RESULT_ATM);
        	else if (map.containsKey("topup_va"))
        		return new ModelAndView(PAGE_TOPUP_RESULT_VA);
        		*/
        }

        
    	ModelMap m = new ModelMap();
    	User u = new User();
    	u.getAddress().setCountry("ID");	//gunakan ISO code, jangan indonesia
    	
    	List<Whole_SalerVO> listWholeSaler = agentManager.getWholeSalers();

    	if (agentId != null){
    		for (Whole_SalerVO wholeSalerVO : listWholeSaler) {
				if (wholeSalerVO.getAgent_id().compareTo(agentId) == 0){
					
					Whole_SalerVO _buffer = wholeSalerVO;
					
					listWholeSaler.clear();
					listWholeSaler.add(_buffer);
					break;
				}
			}
    	}
    	
    	if (useSSO){
//    		u.getAgent().setAff_id(agentManager.getWholeSaler(WholeSaler.MANDIRATRAVEL));
    	}
    	
    	m.put("agentuser", u);
    	m.put("wholesaler", listWholeSaler);
    	m.put("ccode", agentId);
//    	m.put(RECAPTCHA_HTML, c.createRecaptchaHtml(null, null));
    	
    	return new ModelAndView("/agent/agentSignup", m);
    }

    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/agentsignup", method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("agentuser") User user, ModelMap model, final BindingResult errors, final HttpServletRequest request, final HttpServletResponse response
    		, RedirectAttributes ra
    		) throws Exception {

    	boolean skipCaptcha = setupDao.getValueAsBoolean(ISetupDao.KEY_CAPTCHA_SKIP);
    			
        if (request.getParameter("cancel") != null) {
            return getCancelView();
        }
        
        List<Whole_SalerVO> wholeSalerList = (List<Whole_SalerVO>) model.get("wholesaler");
        
        WHOLESALER wholeSaler = WHOLESALER.getWholeSaler(user.getAgent().getAgent_id());
        
        if (wholeSalerList == null)
        	return "redirect:/agentsignup";

        final Locale locale = request.getLocale();
        
        if (validator != null) { // validator is null during testing
        	
            validator.validate(user, errors);

            if (!StringUtils.isEmpty(user.getUsername())) {
            	if (user.getUsername().length() < 3) {
            		errors.rejectValue("username", "errors.tooshort",
            				new Object[] { user.getUsername() }, "username too short");            	            		
            	}else
            	if (setupDao.isForbidUser(user.getUsername())) {
            		errors.rejectValue("username", "errors.existing.user",
            				new Object[] { user.getUsername(), user.getEmail() }, "duplicate user");            	
            	}
            	
            }
            
    		if (StringUtils.isEmpty(user.getAgent().getAgent_Name())) {
                errors.rejectValue("agent.agent_Name", "errors.required", new Object[] { getText("agent.travel", request.getLocale()) },
                        "Travel name is a required field.");
    		}

            if (StringUtils.isEmpty(user.getPassword())) {
                errors.rejectValue("password", "errors.required", new Object[] { getText("user.password", request.getLocale()) },
                        "Password is a required field.");
            }else{
            	if (!user.getPassword().equals(user.getConfirmPassword())){
            		errors.rejectValue("password", "errors.password.notmatch");
            		errors.rejectValue("confirmPassword", "errors.password.notmatch");
            	}
            	
            }

            if (StringUtils.isEmpty(user.getPhoneNumber())) {
            	errors.rejectValue("phoneNumber", "errors.required", new Object[] { getText("user.phoneNumber", request.getLocale()) },
            			"Phone Number is a required field.");
            }

            if (StringUtils.isEmpty(user.getAgent().getPhone2())) {
            	errors.rejectValue("agent.phone2", "errors.required", new Object[] { getText("agent.mobilePhone", request.getLocale()) },
            			"Mobile Phone is a required field.");
            }
            
            if (user.getPhoneNumber().equalsIgnoreCase(user.getAgent().getPhone2())){
            	errors.rejectValue("agent.phone2", "errors.phone.duplicate", new Object[] { getText("agent.mobilePhone", request.getLocale()) },
            			"Duplicate field.");
            }

            if (StringUtils.isEmpty(user.getAddress().getAddress())) {
            	errors.rejectValue("address.address", "errors.required", new Object[] { getText("agent.address1", request.getLocale()) },
            			"Address is a required field.");
            }

            if (StringUtils.isEmpty(user.getAddress().getCity())) {
            	errors.rejectValue("address.city", "errors.required", new Object[] { getText("agent.city", request.getLocale()) },
            			"City is a required field.");
            }

            if (StringUtils.isEmpty(user.getAddress().getCountry())) {
            	errors.rejectValue("address.country", "errors.required", new Object[] { getText("agent.country", request.getLocale()) },
            			"Country is a required field.");
            }

            if (wholeSaler != WHOLESALER.MANDIRA_TRAVEL){
            	/*
	            if (StringUtils.isEmpty(user.getAgent().getYm_id())) {
	            	errors.rejectValue("agent.ym_id", "errors.required", new Object[] { getText("agent.ym", request.getLocale()) },
	            			"YM is a required field.");
	            }
	            */
	
	            if (user.getAgent().getPackage_code() == null){
	            	errors.rejectValue("agent.package_code", "errors.required", new Object[] { getText("agent.package", request.getLocale()) },
	            			"Package is a required field.");
	            }
            }
            
            
            try{
                User _user = this.getUserManager().getUserByUsername(user.getUsername());
                
                if (_user != null)
                	throw new RuntimeException("_user " + _user.getUsername() + " exists !");
            }catch(UsernameNotFoundException e){
            	//skip
            }
            catch(Exception e){
            	e.printStackTrace();
//        		errors.rejectValue("username", "errors.existing.user",
//        				new Object[] { user.getUsername(), user.getEmail() }, "username exist");            	            		
            }

            try{
            	User _email = this.getUserManager().getUserByEmail(user.getEmail());
            	
            	if (_email != null)
            		throw new RuntimeException("_email " + _email.getEmail() + " exists !");
            }catch(UsernameNotFoundException e){
            	//skip
            }
            catch(Exception e){
            	errors.rejectValue("email", "errors.existing.user",
            			new Object[] { user.getUsername(), user.getEmail() }, "username exist");            	            		
            }

            if (errors.hasErrors()) {
            	request.setAttribute("ccode", wholeSaler.getAgentId());
                return "/agent/agentSignup";
//                return "redirect:/agentsignup";
            }
            
            if (!skipCaptcha){
	            ReCaptchaResponse reCaptchaResponse;
				try {
					ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
					
	//				reCaptcha.setPrivateKey("6LfHEf0SAAAAAAVxAwOa9dcdXclAlCRKJ033ataQ");	//worked for localhost
					reCaptcha.setPrivateKey("6Lel2v0SAAAAAHnayUEriPyoW4BuXtchoUgo_454");	//working for server kuningan
					reCaptchaResponse = reCaptcha.checkAnswer(request.getRemoteAddr(), request.getParameter("recaptcha_challenge_field"), request.getParameter("recaptcha_response_field"));
				
					if (!reCaptchaResponse.isValid()) {
		                ObjectError error = new ObjectError("errorcaptcha","Invalid captcha.");
		                errors.addError(error);
		                request.setAttribute("errorcaptcha", "Invalid captcha");
					}
				} catch (Exception e) {
					log.debug(e);
	                ObjectError error = new ObjectError("errorcaptcha","Invalid captcha.");
	                errors.addError(error);
	                request.setAttribute("errorcaptcha", "Invalid captcha");
	            	
				}
            }
            
            String loc = setupDao.getValue(ISetupDao.KEY_LOGO);
            
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
            
            if (file.getSize() > 0) {
            	
            	if (!file.getFileItem().getContentType().toLowerCase().startsWith("image")) {
                	errors.rejectValue("agent.logoId", "errors.image", new Object[] { getText("agent.uploadLogo", request.getLocale()) },
                			"Logo is an invalid image.");            		
            	}else {
            	
	            	String ext = StringUtils.getFilenameExtension(file.getFileItem().getName());
	            	String logoFile = java.util.UUID.randomUUID().toString() + "." + ext;
	            	
	            	log.debug("attempt to write logo " + loc + logoFile);
	            	
	            	user.getAgent().setLogoId(logoFile);
	                //retrieve the file data
	                InputStream stream = file.getInputStream();
	
	                //write the file to the file specified
	                OutputStream bos = new FileOutputStream(loc + logoFile);
	                int bytesRead;
	                byte[] buffer = new byte[8192];
	
	                while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
	                    bos.write(buffer, 0, bytesRead);
	                }
	
	                bos.close();
	
	                //close the stream
	                stream.close();
            	}
            }
            
            if (errors.hasErrors()) {
            	request.setAttribute("ccode", wholeSaler.getAgentId());
                return "/agent/agentSignup";
            }
        }

        user.setEnabled(true);

        // Set the default user role on this new user
        user.addRole(roleManager.getRole(Constants.USER_ROLE));	//sub-agent adalah user

        // unencrypted users password to log in user automatically
        final String password = user.getPassword();
        UserAccountVO acc = null;
        boolean useSSO = false;
        try {
        	user.setFirstName(WordUtils.capitalizeFully(user.getAgent().getAgent_Name()));
        	user.setLastName(WordUtils.capitalizeFully(user.getAgent().getAgent_Name()));	//lupa mengapa perlu lastname ? krn defaultnya lastName ga boleh null.
        	
            user.setCreated_date(new Date());
        	this.getUserManager().saveUser(user);	//sampai sini jika sukses, USER_APP dan ROLE sudah tercommit.
        	
        	agentManager.signUpSubAgent(user, wholeSaler);
        	
        	/*
            //assign user as agent
            AgentVO agent = new AgentVO(user);

            //this form is about sub-agent, so user must have affiliate id
            AgentVO wholeSalerAgent = agentManager.getAgentByAgentId(wholeSaler.getAgentId());
            
            agent.setAff_id(wholeSalerAgent.getId());
            
            user.setCreated_date(new Date());
            this.getUserManager().saveUser(user);
            // buat accountnya            
//            acc = agentManager.createVirtualAccount(agent);            
            acc = financeManager.createVirtualAccount(agent, wholeSaler);            

            agentManager.addAgent(agent);
            */
            
        } catch (final AccessDeniedException ade) {
            // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
            log.warn(ade.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return null;
        } catch (final UserExistsException e) {
            errors.rejectValue("username", "errors.existing.user",
                    new Object[] { user.getUsername(), user.getEmail() }, "duplicate user");
            
            //restore password after encrypted
            user.setPassword(password);
            
        	request.setAttribute("ccode", wholeSaler.getAgentId());
            return "/agent/agentSignup";
        }

        saveMessage(request, getText("subAgent.registered", user.getUsername(), locale));
        /* dont delete this, will show you how to autologin
	        request.getSession().setAttribute(Constants.REGISTERED, Boolean.TRUE);
	        // log user in automatically
	        final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
	                user.getUsername(), password, user.getAuthorities());
	        auth.setDetails(user);
	        SecurityContextHolder.getContext().setAuthentication(auth);
        */
        
        // Send user an e-mail
        if (log.isDebugEnabled()) {
            log.debug("sending e-mail signup confirmation to user [" + user.getEmail() + "]...");
        }

        try {
			emailManager.sendSignUpAgentConfirm(true
												, getText("signup.email.subject", locale)
												, user
												, wholeSaler
												, request
												);
			
			//notify a new user to someone ?
			
		} catch (Exception e) {
			e.printStackTrace();
            saveError(request, e.getMessage());

		}

        ra.addFlashAttribute("message", "Successfully added..");
        
        return getSuccessView();
    }

    private boolean isFormSubmission(final HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("post");
    }

}
