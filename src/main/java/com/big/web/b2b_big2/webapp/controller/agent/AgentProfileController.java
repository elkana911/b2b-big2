package com.big.web.b2b_big2.webapp.controller.agent;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.big.web.Constants;
import com.big.web.b2b_big2.finance.exception.TopUpIncompleteRegException;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.webapp.controller.BaseFormController;
import com.big.web.b2b_big2.webapp.controller.UpdatePasswordController;
import com.big.web.b2b_big2.webapp.util.RequestUtil;
import com.big.web.model.Role;
import com.big.web.model.User;
import com.big.web.service.RoleManager;
import com.big.web.service.UserExistsException;
import com.big.web.service.UserManager;

/**
 * Implementation of <strong>SimpleFormController</strong> that interacts with
 * the {@link UserManager} to retrieve/persist values to the database.
 *
 * <p><a href="UserFormController.java.html"><i>View Source</i></a>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/agent/agentprofile*")
public class AgentProfileController extends BaseFormController {

    private RoleManager roleManager;

    @Autowired
    public void setRoleManager(final RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    public AgentProfileController() {
        setCancelView("redirect:/home");
        setSuccessView("redirect:/admin/users");
    }

    @Override
    @InitBinder
    protected void initBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) {
        super.initBinder(request, binder);
       // binder.setDisallowedFields("password", "confirmPassword");
    }


    /*
    @ModelAttribute("agentuser")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView loadUser(final HttpServletRequest request) {
    	
    	ModelMap m = new ModelMap();
    	User u;
    	
        final String userId = request.getParameter("id");
        
        if (isFormSubmission(request) && StringUtils.isNotBlank(userId)) {
            u = getUserManager().getUser(userId);
        }else {
        	//admin bisa tambah agent baru, so this controller can manage
        	u = new User();
        }
        
        m.put("agentuser", u);
        return new ModelAndView("/agent/agentProfile", m);
    }*/

    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("agentuser") final User user, final BindingResult errors, final HttpServletRequest request,
            final HttpServletResponse response)
            throws Exception {

    	if (request.getParameter("cancel") != null) {
            if (!StringUtils.equals(request.getParameter("from"), "list")) {
                return getCancelView();
            } else {
                return getSuccessView();
            }
        }

        //tolong dites kalau di hack via url
        if (request.getParameter("delete") == null && validator != null) { // validator is null during testing
            validator.validate(user, errors);
            
            /*
            if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
                return "/agent/agentProfile";
            }*/
            
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

    		/* disabled
            if (StringUtils.isEmpty(user.getPassword())) {
                errors.rejectValue("password", "errors.required", new Object[] { getText("user.password", request.getLocale()) },
                        "Password is a required field.");
            }*/

            if (StringUtils.isEmpty(user.getPhoneNumber())) {
            	errors.rejectValue("phoneNumber", "errors.required", new Object[] { getText("user.phoneNumber", request.getLocale()) },
            			"Phone Number is a required field.");
            }

            if (StringUtils.isEmpty(user.getAgent().getPhone2())) {
            	errors.rejectValue("agent.phone2", "errors.required", new Object[] { getText("agent.mobilePhone", request.getLocale()) },
            			"Mobile Phone is a required field.");
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

            if (StringUtils.isEmpty(user.getAgent().getYm_id())) {
            	errors.rejectValue("agent.ym_id", "errors.required", new Object[] { getText("agent.ym", request.getLocale()) },
            			"YM is a required field.");
            }

            /*disabled
            if (user.getAgent().getPackage_code() == null){
            	errors.rejectValue("agent.package_code", "errors.required", new Object[] { getText("agent.package", request.getLocale()) },
            			"Package is a required field.");
            }*/

            if (errors.hasErrors()) {
        		try {
        			setAgentInfo(request, response);
        		} catch (Exception e) {
        			e.printStackTrace();
        			return "redirect:/logout";
        		}

        		return "/agent/agentProfile";
            }
            
        }

        //logo file handler
        String loc = setupDao.getValue(ISetupDao.KEY_LOGO);
        
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
        
        if (file.getSize() > 0) {
        	//ga perlu generate id baru, cukup replace filenya
        	if (!file.getFileItem().getContentType().toLowerCase().startsWith("image")) {
            	errors.rejectValue("agent.logoId", "errors.image", new Object[] { getText("agent.uploadLogo", request.getLocale()) },
            			"Logo is an invalid image.");            		
        	}else {
        		//berhubung path logoId bisa null, bisa ambil dari attribute agent
        		
        		
//        		Agent agent = (Agent)request.getAttribute("agent");
        		
//            	String ext = Utils.includePath(file.getFileItem().getName());
            	String logoFile = agent.getLogoFileName(); //java.util.UUID.randomUUID().toString() + "." + ext;
            	
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

        
        
        log.debug("entering 'onSubmit' method...");

        final Locale locale = request.getLocale();

        if (request.getParameter("delete") != null) {
            getUserManager().removeUser(user.getId().toString());
            saveMessage(request, getText("user.deleted", user.getFullName(), locale));

            return getSuccessView();
        } else {

            // only attempt to change roles if user is admin for other users,
            // showForm() method will handle populating
            if (request.isUserInRole(Constants.ADMIN_ROLE)) {
                final String[] userRoles = request.getParameterValues("userRoles");

                if (userRoles != null) {
                    user.getRoles().clear();
                    for (final String roleName : userRoles) {
                        user.addRole(roleManager.getRole(roleName));
                    }
                }
            } else {
                // if user is not an admin then load roles from the database
                // (or any other user properties that should not be editable
                // by users without admin role)
                final User cleanUser = getUserManager().getUserByUsername(
                        request.getRemoteUser());
                user.setRoles(cleanUser.getRoles());
                
                //perbaiki password, firstname, beberapa yg null karena dihidden
                if (user.getPassword() == null){
                	user.setPassword(cleanUser.getPassword());
                }
                
                if (user.getFirstName() == null){
                	user.setFirstName(cleanUser.getFirstName());
                }
                if (user.getLastName() == null){
                	user.setLastName(cleanUser.getLastName());
                }
                
            }

            final Integer originalVersion = user.getVersion();

            // set a random password if user is added by admin
            if (originalVersion == null && StringUtils.isBlank(user.getPassword())) {
                user.setPassword(UUID.randomUUID().toString()); // XXX review if
                // UUID is a
                // good choice
                // here
            }

            //memastikan tidak false utk account_enabled
            user.setEnabled(true);
            
            try {
                getUserManager().saveUser(user);
            } catch (final AccessDeniedException ade) {
                // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
                log.warn(ade.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return null;
            } catch (final UserExistsException e) {
                errors.rejectValue("username", "errors.existing.user",
                        new Object[] { user.getUsername(), user.getEmail() }, "duplicate user");

                // reset the version # to what was passed in
                user.setVersion(originalVersion);

                return "/agent/agentProfile";
            }

            if (!StringUtils.equals(request.getParameter("from"), "list")) {
                saveMessage(request, getText("user.saved", user.getFullName(), locale));

                // return to main Menu
                return getCancelView();
            } else {
                if (StringUtils.isBlank(request.getParameter("version"))) {
                    saveMessage(request, getText("user.added", user.getFullName(), locale));

                    // Send an account information e-mail
                    message.setSubject(getText("signup.email.subject", locale));

                    try {
                        final String resetPasswordUrl = getUserManager().buildRecoveryPasswordUrl(user,
                                UpdatePasswordController.RECOVERY_PASSWORD_TEMPLATE);
                        sendUserMessage(user, getText("newuser.email.message", user.getFirstName(), locale),
                                RequestUtil.getAppURL(request) + resetPasswordUrl);
                    } catch (final MailException me) {
                        saveError(request, me.getCause().getLocalizedMessage());
                    }

                    return getSuccessView();
                } else {
                    saveMessage(request, getText("user.updated.byAdmin", user.getFullName(), locale));
                }
            }
        }

        return "/agent/agentProfile";
    }

    @ModelAttribute("agentuser")
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showForm(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
    	
    	User user;
    	
        // If not an administrator, make sure user is not trying to add or edit another user
        if (!request.isUserInRole(Constants.ADMIN_ROLE) && !isFormSubmission(request)) {
            if (isAdd(request) || request.getParameter("id") != null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                log.warn("User '" + request.getRemoteUser() + "' is trying to edit user with id '" +
                        request.getParameter("id") + "'");

                throw new AccessDeniedException("You do not have permission to modify other users.");
            }
        }

        if (!isFormSubmission(request)) {
            final String userId = request.getParameter("id");

//            User user;
            if (userId == null && !isAdd(request)) {
                user = getUserManager().getUserByUsername(request.getRemoteUser());
            } else if (!StringUtils.isBlank(userId) && !"".equals(request.getParameter("version"))) {
                user = getUserManager().getUser(userId);
            } else {
                user = new User();
                user.addRole(new Role(Constants.USER_ROLE));
            }

//            return user;
        } else {
            // populate user object from database, so all fields don't need to be hidden fields in form
            user = getUserManager().getUser(request.getParameter("id"));
        }
        
        if (user != null) {
        	user.setAgent(agentManager.getAgentByUserId(user.getId()));
        }
        
        ModelMap m = new ModelMap();
        m.put("agentuser", user);
        
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

		return new ModelAndView("/agent/agentProfile", m);
    }

    private boolean isFormSubmission(final HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("post");
    }

    protected boolean isAdd(final HttpServletRequest request) {
        final String method = request.getParameter("method");
        return (method != null && method.equalsIgnoreCase("add"));
    }
}
