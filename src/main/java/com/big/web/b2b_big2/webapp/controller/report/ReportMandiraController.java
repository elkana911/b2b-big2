package com.big.web.b2b_big2.webapp.controller.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.big.web.b2b_big2.agent.service.IAgentManager;
import com.big.web.b2b_big2.booking.service.IBookingManager;
import com.big.web.b2b_big2.webapp.controller.BaseFormController;

@Controller
public class ReportMandiraController extends BaseFormController{

	private static final String REPORT_MANDIRA_SALES_PAGE = "/report/mandira/sales";
	private static final String REPORT_MANDIRA_SUBAGENTS_PAGE = "/report/mandira/subagents";
	private static final String REPORT_MANDIRA_SUBAGENTS_PERFORMANCE_PAGE = "/report/mandira/subagentsPerformance";
	private static final String REPORT_MANDIRA_COMMISSION_PAGE = "/report/mandira/commission";

	@Autowired
	private IAgentManager agentManager; 

	@Autowired
	private IBookingManager bookingManager; 

	@RequestMapping(method = RequestMethod.GET, value = "/report/mandira/sales")
	public ModelAndView showSales(@RequestParam(value = "from", required = false) String dateFrom
			,@RequestParam(value = "to", required = false) String dateTo
			,final HttpServletRequest request
			,final HttpServletResponse response){
		
		try {
			setAgentInfo(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/logout");
		}
		
		return new ModelAndView(REPORT_MANDIRA_SALES_PAGE)
		;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/report/mandira/subagents")
	public ModelAndView showSubAgents(@RequestParam(value = "from", required = false) String dateFrom
			,@RequestParam(value = "to", required = false) String dateTo
			,final HttpServletRequest request
			,final HttpServletResponse response){
		
		try {
			setAgentInfo(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/logout");
		}
		

		return new ModelAndView(REPORT_MANDIRA_SUBAGENTS_PAGE)
			;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/report/mandira/subagentsperform")
	public ModelAndView showSubAgentsPerformance(@RequestParam(value = "from", required = false) String dateFrom
			,@RequestParam(value = "to", required = false) String dateTo
			,final HttpServletRequest request
			,final HttpServletResponse response){
		
		try {
			setAgentInfo(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/logout");
		}
		
		
		return new ModelAndView(REPORT_MANDIRA_SUBAGENTS_PERFORMANCE_PAGE)
		;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/report/mandira/commission")
	public ModelAndView showCommission(@RequestParam(value = "from", required = false) String dateFrom
			,@RequestParam(value = "to", required = false) String dateTo
			,final HttpServletRequest request
			,final HttpServletResponse response) throws Exception{

		try {
			setAgentInfo(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/logout");
		}
		

		return new ModelAndView(REPORT_MANDIRA_COMMISSION_PAGE)
				;
	}
	
}
