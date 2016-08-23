package com.big.web.b2b_big2.webapp.controller.flight;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.big.web.b2b_big2.flight.pojo.SearchForm;

@Controller
public class UpdateRouteController {
	
	private final Log log = LogFactory.getLog(UpdateRouteController.class);
	
    @RequestMapping(method = RequestMethod.GET, value = "/admin/flight/updateroute")
    public ModelAndView handleRequest(HttpServletRequest request,
                                      HttpServletResponse response)
    throws Exception {
    	ModelAndView mav = new ModelAndView("/admin/updateRoute");
    	
    	SearchForm backingBean = new SearchForm();
    	
    	mav.addObject("maSearchForm", backingBean);
    	
        return mav;
    }
    
	@RequestMapping(value = "/admin/flight/updateroute", method = RequestMethod.POST)
	public ModelAndView onUpdateRoute(@ModelAttribute("maSearchForm") SearchForm searchForm, ModelMap model,  
			final BindingResult errors, HttpServletRequest request)
			throws Exception{
		
		System.out.println("onUpdateRoute");
		
		ModelAndView mav = new ModelAndView("/admin/updateRouteDisplay");
		
		return mav;
	}
}
