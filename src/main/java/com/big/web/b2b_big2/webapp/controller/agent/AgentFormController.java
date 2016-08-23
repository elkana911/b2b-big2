package com.big.web.b2b_big2.webapp.controller.agent;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.big.web.b2b_big2.agent.model.AgentVO;
import com.big.web.b2b_big2.webapp.controller.BaseFormController;
import com.big.web.model.User;

@Controller
public class AgentFormController extends BaseFormController{

	private PasswordEncoder passwordEncoder;

	public AgentFormController() {
		setCancelView("redirect:/home");
		setSuccessView("redirect:/admin/agents");
	}
	
	@Autowired
	@Qualifier("passwordEncoder")
	public void setPasswordEncoder(final PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	@InitBinder
	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		super.initBinder(request, binder);
//		binder.setDisallowedFields("password", "confirmPassword");		

	}
	
	private boolean isFormSubmission(final HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}
	
    protected boolean isAdd(final HttpServletRequest request) {
        final String method = request.getParameter("method");
        return (method != null && method.equalsIgnoreCase("add"));
    }

    @ModelAttribute
    @RequestMapping(value = "/agent/agentform", method = RequestMethod.GET)
    protected ModelAndView showForm(HttpServletRequest request)
    throws Exception {
    	System.out.println("AgentFormController.showForm");

        final String idParam = request.getParameter("idParam");
 
        // maksudnya formsubmission itu kalo lagi post(menyerahkan form)
        if (!isFormSubmission(request)) {

            AgentVO agent = null;
            
            if (!StringUtils.isBlank(idParam)) {
                agent = agentManager.getAgentById(idParam);            	
            } else {
            	//berarti subagent karena agent_id = null
                agent = new AgentVO();
            }

            return new ModelAndView("/agent/agentForm", "agentBean", agent);
//            return hotel;
        } else {
        	//jika GET, ambil aja dr database
            // populate user object from database, so all fields don't need to be hidden fields in form
            return new ModelAndView("/agent/agentForm", "agentBean", agentManager.getAgentByAgentId(Long.parseLong(idParam)));
        }
    }
    
    @RequestMapping(value = "/agent/agentform", method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("agentBean") final AgentVO agent, @RequestParam(value = "passwd", required = false) String password, final BindingResult errors, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
    	System.out.println("AgentFormController.onSubmit, isAdd=" + isAdd(request));
    	
        if (request.getParameter("cancel") != null) {
            if (!StringUtils.equals(request.getParameter("from"), "list")) {
                return getCancelView();
            } else {
                return getSuccessView();
            }
        }
        
//        karena ga pake backingbean, validasi manual saja
        
        if (validator != null) {
        	validator.validate(agent, errors);
        	
            // validate a file was entered
            if (StringUtils.isBlank(agent.getAgent_Name())) {
                Object[] args =
                        new Object[]{getText("agent.name", request.getLocale())};
                errors.rejectValue("agent_Name", "errors.required", args, "Agent Name");
            }
        	
        	//dont validate when deleting
        	if (errors.hasErrors() && request.getParameter("delete") == null) {
        		return "/agent/agentForm";
        	}
        	
        }
        
        Locale locale = request.getLocale();
        if (request.getParameter("delete") != null) {
        	
        	User loggedUser = this.getUserManager().getUserByUsername(request.getRemoteUser());
        	boolean cocok = passwordEncoder.matches(password, loggedUser.getPassword());
        	if (!cocok){
        		request.setAttribute("errPwd", getText("errors.password.notmatch", request.getLocale()));
        		return "/agent/agentForm";
        	}
        	/*
        	if (!isAllowDelete()){
        		
        		saveError(request, getText("errors.admin.forbidDelete", locale));

                return "/agent/agentForm";
        	}
        	*/
        	agentManager.removeAgent(agent);
        	saveMessage(request, getText("agent.deleted", agent.getAgent_Name(), locale));
        }else {
        	String key = null;
        	if (isAdd(request)) {
        		key = "agent.added";
        		agentManager.saveAgent(agent);
        	}else {
        		key = "agent.updated.byAdmin";
        		agentManager.updateAgent(agent);
        	}
            saveMessage(request, getText(key, agent.getAgent_Name(), locale));
        }
        
        return getSuccessView();
    }

	private boolean isAllowDelete() {
		// TODO Auto-generated method stub
		return false;
	}


    
}
