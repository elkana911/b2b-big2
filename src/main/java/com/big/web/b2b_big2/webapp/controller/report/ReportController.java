package com.big.web.b2b_big2.webapp.controller.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.big.web.b2b_big2.booking.model.BookingFlightVO;
import com.big.web.b2b_big2.booking.service.IBookingManager;
import com.big.web.b2b_big2.finance.bank.TOPUP_STATUS;
import com.big.web.b2b_big2.finance.bank.model.TopUpVO;
import com.big.web.b2b_big2.finance.exception.TopUpIncompleteRegException;
import com.big.web.b2b_big2.flight.model.AirlineVO;
import com.big.web.b2b_big2.flight.service.IAirlineManager;
import com.big.web.b2b_big2.report.pojo.Revenue;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.b2b_big2.webapp.controller.BaseFormController;
import com.big.web.b2b_big2.webapp.controller.agent.TopUpInfo;

@Controller
public class ReportController extends BaseFormController{

	private static final String REPORT_REVENUE_PAGE = "/report/revenue";
	private static final String REPORT_TOPUP_PAGE = "/report/topup";
	private static final String REPORT_BOOKING_PAGE = "/report/booking";

	@Autowired
	private IAirlineManager airlineManager; 

	@Autowired
	private IBookingManager bookingManager; 

	@RequestMapping(method = RequestMethod.GET, value = "/report/revenue")
	public ModelAndView showRevenue(@RequestParam(value = "from", required = false) String dateFrom
			,@RequestParam(value = "to", required = false) String dateTo
			,final HttpServletRequest request
			,final HttpServletResponse response){
		
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
		
		if (dateFrom == null && dateTo == null){
			dateFrom = Utils.today();
			dateTo = Utils.today();
		}

		List<Revenue> list;
		try {
			list = agentManager.getReportRevenue(dateFrom, dateTo);
		} catch (Exception e) {
			e.printStackTrace();
			list = new ArrayList<Revenue>();
		}
		

		return new ModelAndView(REPORT_REVENUE_PAGE)
				.addObject("revenueList", list)
				.addObject("fromdate", dateFrom)
				.addObject("todate", dateTo);

	}

	@RequestMapping(method = RequestMethod.GET, value = "/report/topuphist")
	public ModelAndView showTopUp(@RequestParam(value = "from", required = false) String dateFrom
			,@RequestParam(value = "to", required = false) String dateTo
			,@RequestParam(value = "status", required = false) String statusFilter
			,final HttpServletRequest request
			,final HttpServletResponse response){
		
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
		
		if (dateFrom == null && dateTo == null){
			dateFrom = Utils.today();
			dateTo = Utils.today();
		}
		
		List<TopUpVO> list;
		try {
			/*
				<option value="">ALL</option>
				<option value="NOTPAID">NOT PAID</option>
				<option value="PROCESSING">PROCESSING</option>
				<option value="PROCESSED">PROCESSED</option>
			 */
			TOPUP_STATUS[] topUpStatus = null;
			
			if (statusFilter != null){
				if (statusFilter.equalsIgnoreCase("NOTPAID")){
					topUpStatus = new TOPUP_STATUS[2];
					topUpStatus[0] = TOPUP_STATUS.REJECT;
					topUpStatus[1] = TOPUP_STATUS.PENDING;
				}else if (statusFilter.equalsIgnoreCase("PROCESSING")){
					topUpStatus = new TOPUP_STATUS[1];
					topUpStatus[0] = TOPUP_STATUS.PENDING;
				}else if (statusFilter.equalsIgnoreCase("PROCESSED")){
					topUpStatus = new TOPUP_STATUS[2];
					topUpStatus[0] = TOPUP_STATUS.APPROVE;
					topUpStatus[1] = TOPUP_STATUS.OK;
				}
			}
//			list = bookingManager.getBookingFlight(dateFrom, dateTo, agent.getUserName());
//			list = agentManager.getReportTopUp(dateFrom, dateTo, status);
			list = agentManager.getReportTopUp(dateFrom, dateTo, topUpStatus);
			
			//update for expired
			for (int i = 0; i < list.size(); i++){
				TopUpVO element = list.get(i);
				
				if (element.getTopUpStatus() == TOPUP_STATUS.PENDING){
					if (new Date().after(element.getTimeToPay())){
						element.setStatus(TOPUP_STATUS.EXPIRED.getFlag());
						list.set(i, element);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			list = new ArrayList<TopUpVO>();
		}
		
		String virtualaccount = null;
		if (list.size() > 0)
			virtualaccount = list.get(0).getAccount_no();
			
		return new ModelAndView(REPORT_TOPUP_PAGE)
				.addObject("topuplist", list)
				.addObject("fromdate", dateFrom)
				.addObject("todate", dateTo)
				.addObject("status", statusFilter)
				.addObject("virtualaccount", virtualaccount)
				;
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/report/bookinghist")
	public ModelAndView showBooking(@RequestParam(value = "from", required = false) String dateFrom
			,@RequestParam(value = "to", required = false) String dateTo
			,@RequestParam(value = "airline", required = false) String airlineCode
			,@RequestParam(value = "status", required = false) String statusFilter
			,final HttpServletRequest request
			,final HttpServletResponse response) throws Exception{

//		bookingManager.startSlowProcess();
		
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
		
		if (dateFrom == null && dateTo == null){
			dateFrom = Utils.today();
			dateTo = Utils.today();
		}
		
		List<BookingFlightVO> list2;
		try {
			String[] bookStatus = null;	//default ALL
			
			//harus hardcode disesuaikan dgn masing2 airline, menggunakan kemungkinan
			if (statusFilter != null)
			if (statusFilter.equalsIgnoreCase("HOLD") 
					|| statusFilter.equalsIgnoreCase("EXPIRED")) {
				bookStatus = new String[5];
				bookStatus[0] = "HOLD";	//airasia
				bookStatus[1] = "HK";
				bookStatus[2] = "HK,HK";
				bookStatus[3] = "HK,HK,HK";
				bookStatus[4] = "EXPIRED";	//bisa jadi memang expired
			}else if (statusFilter.equalsIgnoreCase("ISSUED")){
				bookStatus = new String[5];
				bookStatus[0] = "OK";
				bookStatus[1] = "OK,OK";
				bookStatus[2] = "OK,OK,OK";
				bookStatus[3] = "CLOSED";
				bookStatus[4] = "TKT";	//garuda
			}else if (statusFilter.equalsIgnoreCase("CANCEL")){
				bookStatus = new String[1];
				bookStatus[0] = "HOLDCANCELED";
				bookStatus[1] = "TLX";	//garuda
			}
				
	    	List<String> selectedAirlines = new ArrayList<String>();
	    	if (!Utils.isEmpty(airlineCode)){
	    		if (!airlineCode.equals("all"))
	    			selectedAirlines.add(airlineCode);
	    	}

	    	list2 = bookingManager.prettyList(bookingManager.getBookingFlight(dateFrom
												, dateTo
												, selectedAirlines.size() < 1 
														? null 
														: Utils.collectionToArray(selectedAirlines)
												, agent.getUserName()
												, bookStatus));
		} catch (Exception e) {
			e.printStackTrace();
			list2 = new ArrayList<BookingFlightVO>();
		}
		
		//TODO check all expired, lagi mikir apa perlu back job utk update otomatis
		List<AirlineVO> airlines = airlineManager.getAirlines();
    	//sort by name
    	Collections.sort(airlines,  new Comparator<AirlineVO>() {
    		@Override
    		public int compare(AirlineVO s1, AirlineVO s2) {
    			return s1.getName().compareTo(s2.getName());
    		}
    	});
    			
		return new ModelAndView(REPORT_BOOKING_PAGE)
				.addObject("bookinglist", list2)
				.addObject("fromdate", dateFrom)
				.addObject("todate", dateTo)
				.addObject("status", statusFilter)
				.addObject("airlines", airlines)
				;
	}
	
}
