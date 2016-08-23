package com.big.web.b2b_big2.webapp.controller.flight;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.big.web.b2b_big2.flight.model.AirlineVO;
import com.big.web.b2b_big2.flight.service.IAirlineManager;
import com.big.web.b2b_big2.util.AppInfo;
import com.big.web.b2b_big2.webapp.controller.BaseFormController;

/**
 * asal mula logic mengikuti HotelFormController
 * @author 3r1c8
 *
 */
@Controller
public class AirlineFormController extends BaseFormController{

	@Autowired
	private IAirlineManager airlineManager;
	
	public AirlineFormController() {
		setCancelView("redirect:/home");
		setSuccessView("redirect:/admin/airlines");
	}
	
	@Override
	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		super.initBinder(request, binder);
//		binder.setDisallowedFields("password", "confirmPassword");		
	};
	
	private boolean isFormSubmission(final HttpServletRequest request) {
		return request.getMethod().equalsIgnoreCase("post");
	}
	
    protected boolean isAdd(final HttpServletRequest request) {
        final String method = request.getParameter("method");
        return (method != null && method.equalsIgnoreCase("add"));
    }

    @ModelAttribute
    @RequestMapping(value = "/admin/airlineform", method = RequestMethod.GET)
    protected ModelAndView showForm(HttpServletRequest request)
    throws Exception {
    	System.out.println("AirlineFormController.showForm");

        final String id = request.getParameter("id");

        // maksudnya formsubmission itu kalo lagi post(menyerahkan form)
        if (!isFormSubmission(request)) {

            AirlineVO airline = null;
            
            if (!StringUtils.isBlank(id)) {
                airline = airlineManager.getAirline(id);            	
            } else {
                airline = new AirlineVO();
            }

            return new ModelAndView("/admin/airlineForm", "airlineBean", airline);
        } else {
        	//jika GET, ambil aja dr database
            // populate user object from database, so all fields don't need to be hidden fields in form
            return new ModelAndView("/admin/airlineForm", "airlineBean", airlineManager.getAirline(id));
        }
    }
    
    
    @RequestMapping(value = "/admin/airlineform", method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("airlineBean") final AirlineVO airline, final BindingResult errors, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
    	System.out.println("AirlineFormController.onSubmit, isAdd=" + isAdd(request));
    	
        if (request.getParameter("cancel") != null) {
            if (!StringUtils.equals(request.getParameter("from"), "list")) {
                return getCancelView();
            } else {
                return getSuccessView();
            }
        }
        
//        karena ga pake backingbean, validasi manual saja
        
        if (validator != null) {
        	validator.validate(airline, errors);
        	
            // validate a file was entered
            if (StringUtils.isBlank(airline.getName())) {
                Object[] args =
                        new Object[]{getText("airline.name", request.getLocale())};
                errors.rejectValue("name", "errors.required", args, "Airline Name");
            }
        	
        	//dont validate when deleting
        	if (errors.hasErrors() && request.getParameter("delete") == null) {
        		return "/admin/airlineForm";
        	}
        	
        }
        
        Locale locale = request.getLocale();
        if (request.getParameter("delete") != null) {
        	if (!isAllowDelete()){
        		
        		saveError(request, getText("errors.admin.forbidDelete", locale));

                return "/admin/airportForm";
        	}
        	airlineManager.remove(airline.getAirline_id());
        	saveMessage(request, getText("airline.deleted", airline.getName(), locale));
        }else {
        	String key = null;
        	if (isAdd(request)) {
        		key = "airline.added";
        		airlineManager.add(airline);
        	}else {
        		key = "airline.updated.byAdmin";
        		airlineManager.update(airline);
        	}
            saveMessage(request, getText(key, airline.getName(), locale));
        }
        
        return getSuccessView();
    }

	private boolean isAllowDelete() {
		// TODO Auto-generated method stub
		return false;
	}

	
    @RequestMapping(value = "/flight/b2b/airlineInfo", method = RequestMethod.GET)
	public @ResponseBody List<AppInfo> getAirlineInfo(){
    	return airlineManager.getAppInfo();
    }
		
}
