package com.big.web.b2b_big2.webapp.controller.flight.b2b.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.big.web.b2b_big2.flight.service.IFlightManager;

@Controller
public class FlightBookReportController {
	@Autowired
	private IFlightManager flightManager;

    @RequestMapping(value = "/flight/b2b/cancel", method = RequestMethod.GET, params = "cancelTicket")	
	public String onCancelTicket() {
    	System.out.println("cancel ticket");
		return "/flight/flightBookB2B";
	}
    
    @RequestMapping(value = "/flight/b2b/print", method = RequestMethod.GET, params = "printTicket")	
    public String onPrintTicket() {
    	System.out.println("print ticket");
    	return "/flight/flightBookB2B";
    }
    
    @RequestMapping(value = "/flight/b2b/issue", method = RequestMethod.GET, params = "issueTicket")	
    public String onIssueTicket() {
    	System.out.println("issue ticket");
    	return "/flight/flightBookB2B";
    }
}
