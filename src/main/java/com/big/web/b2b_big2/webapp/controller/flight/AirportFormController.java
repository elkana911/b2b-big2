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

import com.big.web.b2b_big2.country.service.ICountryManager;
import com.big.web.b2b_big2.flight.model.AirportVO;
import com.big.web.b2b_big2.flight.service.IAirportManager;
import com.big.web.b2b_big2.webapp.controller.BaseFormController;

/**
 * asal mula logic mengikuti HotelFormController
 * @author 3r1c8
 *
 */
@Controller
public class AirportFormController extends BaseFormController{

	@Autowired
	private IAirportManager airportManager;
	
	@Autowired
	private ICountryManager countryManager;

	public AirportFormController() {
		setCancelView("redirect:/home");
		setSuccessView("redirect:/admin/airports");
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
    @RequestMapping(value = "/admin/airportform", method = RequestMethod.GET)
    protected ModelAndView showForm(HttpServletRequest request)
    throws Exception {
    	log.debug("AirportFormController.showForm");

        final String id = request.getParameter("id");
 
        request.setAttribute("countries", countryManager.getCountries());
        
        // maksudnya formsubmission itu kalo lagi post(menyerahkan form)
        if (!isFormSubmission(request)) {

            AirportVO airport = null;
            
            if (!StringUtils.isBlank(id)) {
                airport = airportManager.getAirport(id);            	
            } else {
                airport = new AirportVO();
            }

            return new ModelAndView("/admin/airportForm", "airportBean", airport);
//            return hotel;
        } else {
        	//jika GET, ambil aja dr database
            // populate user object from database, so all fields don't need to be hidden fields in form
            return new ModelAndView("/admin/airportForm", "airportBean", airportManager.getAirport(id));
        }
    }
    
    
    @RequestMapping(value = "/admin/airportform", method = RequestMethod.POST)
    public String onSubmit(@ModelAttribute("airportBean") final AirportVO airport, final BindingResult errors, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
    	log.debug("AirportFormController.onSubmit, isAdd=" + isAdd(request));
    	
        if (request.getParameter("cancel") != null) {
            if (!StringUtils.equals(request.getParameter("from"), "list")) {
                return getCancelView();
            } else {
                return getSuccessView();
            }
        }
        
//        karena ga pake backingbean, validasi manual saja
        
        if (validator != null) {
        	validator.validate(airport, errors);
        	
            // validate a file was entered
            if (StringUtils.isBlank(airport.getAirport_name())) {
                Object[] args =
                        new Object[]{getText("airport.name", request.getLocale())};
                errors.rejectValue("name", "errors.required", args, "Airport Name");
            }
        	
        	//dont validate when deleting
        	if (errors.hasErrors() && request.getParameter("delete") == null) {
        		return "/admin/airportForm";
        	}
        	
        }
        
        Locale locale = request.getLocale();
        if (request.getParameter("delete") != null) {
        	if (!isAllowDelete()){
        		
        		saveError(request, getText("errors.admin.forbidDelete", locale));

                return "/admin/airportForm";
        	}
        	airportManager.remove(airport.getAirport_id());
        	saveMessage(request, getText("airport.deleted", airport.getAirport_name(), locale));
        }else {
        	String key = null;
        	if (isAdd(request)) {
        		key = "airport.added";
        		airportManager.addAirport(airport);
        	}else {
        		key = "airport.updated.byAdmin";
        		airportManager.updateAirport(airport);
        	}
            saveMessage(request, getText(key, airport.getAirport_name(), locale));
        }
        
        return getSuccessView();
    }

	private boolean isAllowDelete() {
		// TODO Auto-generated method stub
		return false;
	}

    @RequestMapping(value = "/getAirports", method = RequestMethod.GET, headers="Accept=*/*")
    public @ResponseBody List<String> getPrettyAirports(){
    	
    	List<String> airports = airportManager.getPrettyAirports();
    	
    	return airports;
    }
    
}
