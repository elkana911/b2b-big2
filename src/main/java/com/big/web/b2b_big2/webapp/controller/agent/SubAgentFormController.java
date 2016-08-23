package com.big.web.b2b_big2.webapp.controller.agent;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.big.web.b2b_big2.agent.model.AgentVO;
import com.big.web.b2b_big2.agent.model.Whole_SalerVO;
import com.big.web.b2b_big2.webapp.controller.BaseFormController;

@Controller
public class SubAgentFormController extends BaseFormController{

	public SubAgentFormController() {
		setCancelView("redirect:/home");
		setSuccessView("redirect:/admin/subagents");
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
    @RequestMapping(value = "/agent/subagentform", method = RequestMethod.GET)
    protected ModelAndView showForm(HttpServletRequest request)
    throws Exception {
    	System.out.println("SubAgentFormController.showForm");

        final String idParam = request.getParameter("idParam");
 
    	List<Whole_SalerVO> listWholeSaler = agentManager.getWholeSalers();

        // maksudnya formsubmission itu kalo lagi post(menyerahkan form)
        if (!isFormSubmission(request)) {

            AgentVO agent = null;
            
            if (!StringUtils.isBlank(idParam)) {
                agent = agentManager.getAgentById(idParam);    
                
                AgentVO wholeSalerAgent = agentManager.getAgentById(agent.getAff_id());
                
                //remove others to avoid mistake by changing the wholesaler
                for (Whole_SalerVO wholeSalerVO : listWholeSaler) {
    				if (wholeSalerVO.getAgent_id().compareTo(wholeSalerAgent.getAgent_id()) == 0){
    					
    					Whole_SalerVO _buffer = wholeSalerVO;
    					
    					listWholeSaler.clear();
    					listWholeSaler.add(_buffer);
    					break;
    				}
    			} 
            } else {
            	//berarti subagent karena agent_id = null
                agent = new AgentVO();
            }

            return new ModelAndView("/agent/subAgentForm", "agentBean", agent).addObject("wholesaler", listWholeSaler);
//            return hotel;
        } else {
        	//jika GET, ambil aja dr database
            // populate user object from database, so all fields don't need to be hidden fields in form
            return new ModelAndView("/agent/subAgentForm", "agentBean", agentManager.getAgentByAgentId(Long.parseLong(idParam))).addObject("wholesaler", listWholeSaler);
        }
    }
    
    @RequestMapping(value = "/agent/subagentform", method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("agentBean") final AgentVO agent, final BindingResult errors, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
    	System.out.println("SubAgentFormController.onSubmit, isAdd=" + isAdd(request));
    	
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
        		return "/agent/subAgentForm";
        	}
        	
        }
        
        Locale locale = request.getLocale();
        if (request.getParameter("delete") != null) {
        	if (!isAllowDelete()){
        		
        		saveError(request, getText("errors.admin.forbidDelete", locale));

                return "/agent/subAgentForm";
        	}
        	
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
