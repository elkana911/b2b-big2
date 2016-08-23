package com.big.web.b2b_big2.webapp.controller;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.big.web.Constants;
import com.big.web.b2b_big2.util.SimpleLogin;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.model.Role;
import com.big.web.model.User;
import com.big.web.service.RoleManager;
import com.big.web.service.UserExistsException;
import com.big.web.service.UserManager;

/**
 * Implementation of <strong>SimpleFormController</strong> that interacts with
 * the {@link UserManager} to retrieve/persist values to the database.
 * 
 * RULES:
 * Pembuatan user admin, tidak akan ada email konfirmasi yg akan dikirim
 * admin tidak bisa dihapus
 * 
 *
 * <p><a href="UserFormController.java.html"><i>View Source</i></a>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/userform*")
public class UserFormController extends BaseFormController {

	private PasswordEncoder passwordEncoder;
	
    private RoleManager roleManager;

    @Autowired
    public void setRoleManager(final RoleManager roleManager) {
        this.roleManager = roleManager;
    }

    @Autowired
    @Qualifier("passwordEncoder")
    public void setPasswordEncoder(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    public UserFormController() {
        setCancelView("redirect:/home");
        setSuccessView("redirect:/admin/users");
    }

    @Override
    @InitBinder
    protected void initBinder(final HttpServletRequest request, final ServletRequestDataBinder binder) {
        super.initBinder(request, binder);
//        binder.setDisallowedFields("password", "confirmPassword");
    }

    /**
     * Load user object from db before web data binding in order to keep properties not populated from web post.
     * 
     * @param request
     * @return
     */
    @ModelAttribute("user")
    protected User loadUser(final HttpServletRequest request) {
        final String userId = request.getParameter("id");
        if (isFormSubmission(request) && StringUtils.isNotBlank(userId)) {
            return getUserManager().getUser(userId);
        }
        return new User();
    }

    /**
     * 
     * @param user
     * @param pwd hanya berlaku utk proses delete
     * @param errors
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("user") final User user, @RequestParam(value = "passwd", required = false) String password, final BindingResult errors, HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {
        if (request.getParameter("cancel") != null) {
            if (!StringUtils.equals(request.getParameter("from"), "list")) {
                return getCancelView();
            } else {
                return getSuccessView();
            }
        }

        if (validator != null) { // validator is null during testing
            validator.validate(user, errors);

            if (request.getParameter("delete") == null){

            	validateField(User.class, "username", user.getUsername(), errors, false);
            	validateField(User.class, "email", user.getEmail(), errors, false);
            	validateField(User.class, "password", user.getPassword(), errors, false);
            	validateField(User.class, "passwordHint", user.getPasswordHint(), errors, true);
            	
            	if (!Utils.isEmpty(user.getPassword()) && isAdd(request))
	            	if (!user.getPassword().equals(user.getConfirmPassword())){
	            		errors.rejectValue("password", "errors.password.notmatch");
	            		errors.rejectValue("confirmPassword", "errors.password.notmatch");
	            	}

            	validateField(User.class, "firstName", user.getFirstName(), errors, false);
            	validateField(User.class, "lastName", user.getLastName(), errors, false);
            	validateField(User.class, "website", user.getWebsite(), errors, true);
            	validateField(User.class, "address.address", user.getAddress().getAddress(), errors, true);
            	validateField(User.class, "address.city", user.getAddress().getCity(), errors, true);
            	validateField(User.class, "address.postalCode", user.getAddress().getPostalCode(), errors, true);

            	if (isAdd(request)){
            		//check existence
            		boolean userExists = false;
            		try{
            			User _user = getUserManager().getUserByUsername(user.getUsername().toLowerCase());
            			
            			userExists = _user != null;
            		}catch(Exception e){
            			
            		}
            		boolean emailExists = false;
            		try{
            			User _email = getUserManager().getUserByEmail(user.getEmail().toLowerCase());
            			emailExists = _email != null;
            		}catch(Exception e){
            			
            		}
            		
            		if (userExists || emailExists ){
                		errors.rejectValue("username", "errors.existing.user", new Object[] { user.getUsername(), user.getEmail()}, null);            			
            		}
            	}else{
            		
            	}

            	if (errors.hasErrors())
            		return "userform";
            	
            	user.setCreated_date(new Date());
            	
            }else{
            	//check password dulu
//            	boolean adminRole = request.isUserInRole(Constants.ADMIN_ROLE);

            	User loggedUser = this.getUserManager().getUserByUsername(request.getRemoteUser());
            	
            	boolean cocok = passwordEncoder.matches(password, loggedUser.getPassword());
            	
            	if (!cocok){
            		request.setAttribute("errPwd", getText("errors.password.notmatch", request.getLocale()));
            		return "userform";
            	}
            }
            
//            if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
//                return "userform";
//            }
            
        }

        log.debug("entering 'onSubmit' method...");

        final Locale locale = request.getLocale();

        if (request.getParameter("delete") != null) {
        	
        	//prosedur delete user
        	//1
        	//2. delete from app_user where username='eric'
        	
        	//admin tidak bisa di delete. biasanya idnya di bawah 0
        	if (user.getId() < 0){
        		saveError(request, getText("errors.admin.forbidDelete", request.getLocale()));
        		return "userform"; 
        	}
        	
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
            }

            final Integer originalVersion = user.getVersion();

            // set a random password if user is added by admin
            if (originalVersion == null && StringUtils.isBlank(user.getPassword())) {
                user.setPassword(UUID.randomUUID().toString()); // XXX review if
                // UUID is a
                // good choice
                // here
            }

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

                return "userform";
            }

            if (!StringUtils.equals(request.getParameter("from"), "list")) {
                saveMessage(request, getText("user.saved", user.getFullName(), locale));

                // return to main Menu
                return getCancelView();
            } else {
                if (StringUtils.isBlank(request.getParameter("version"))) {
                    saveMessage(request, getText("user.added", user.getFullName(), locale));

                    /* sengaja paused
                    // Send an account information e-mail
                    message.setSubject(getText("signup.email.subject", locale));

                    try {
                        final String resetPasswordUrl = getUserManager().buildRecoveryPasswordUrl(user,
                                UpdatePasswordController.RECOVERY_PASSWORD_TEMPLATE);
                        sendUserMessage(user, getText("newuser.email.message", user.getFullName(), locale),
                                RequestUtil.getAppURL(request) + resetPasswordUrl);
                    } catch (final MailException me) {
                        saveError(request, me.getCause().getLocalizedMessage());
                    }
                    */

                    return getSuccessView();
                } else {
                    saveMessage(request, getText("user.updated.byAdmin", user.getFullName(), locale));
                }
            }
        }

        return "userform";
    }

    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    protected User showForm(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
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

            User user;
            if (userId == null && !isAdd(request)) {
                user = getUserManager().getUserByUsername(request.getRemoteUser());
            } else if (!StringUtils.isBlank(userId) && !"".equals(request.getParameter("version"))) {
                user = getUserManager().getUser(userId);
            } else {
                user = new User();
                user.addRole(new Role(Constants.USER_ROLE));
            }

            SimpleLogin loginPanel = new SimpleLogin();
            
            request.setAttribute("admin", loginPanel);
            return user;
        } else {
            // populate user object from database, so all fields don't need to be hidden fields in form
            return getUserManager().getUser(request.getParameter("id"));
        }
    }

    private boolean isFormSubmission(final HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("post");
    }

    protected boolean isAdd(final HttpServletRequest request) {
        final String method = request.getParameter("method");
        return (method != null && method.equalsIgnoreCase("add"));
    }
}
