package com.big.web.b2b_big2.webapp.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.big.web.b2b_big2.agent.model.AgentVO;
import com.big.web.b2b_big2.email.service.IEmailManager;
import com.big.web.b2b_big2.feeds.service.IFeedManager;
import com.big.web.b2b_big2.finance.exception.TopUpIncompleteRegException;
import com.big.web.b2b_big2.flight.model.AirportVO;
import com.big.web.b2b_big2.flight.pojo.BookingFormB2B;
import com.big.web.b2b_big2.flight.service.IAirportManager;
import com.big.web.b2b_big2.flight.service.IFlightManager;
import com.big.web.b2b_big2.util.ResponseClient;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.b2b_big2.webapp.controller.agent.TopUpInfo;
import com.big.web.b2b_big2.webapp.util.RequestUtil;
import com.big.web.model.User;

@Controller
@SessionAttributes({"feeds"})
public class HomeController extends BaseFormController{
	
	@Autowired
	private IFeedManager feedManager;
	
	@Autowired
	private IFlightManager flightManager;

	@Autowired
	private IAirportManager airportManager;
	
	@Autowired IEmailManager emailManager;

	@ModelAttribute("maBookingForm")
	@RequestMapping(method = RequestMethod.GET, value = "/home")
	public ModelAndView loadForm(HttpServletRequest request, HttpServletResponse response, RedirectAttributes ra) {

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

		ModelMap m = new ModelMap();
		
		BookingFormB2B backingBean = new BookingFormB2B(); 
		//default is selectall
//		backingBean.getSearchForm().setAllAirlines(true);

		m.put("maBookingForm", backingBean);
		
		m.put("feeds", feedManager.getFeeds(20));
		
		return new ModelAndView("/home", m);
	}
	
	private boolean tryFixDepartCityTextBox(BookingFormB2B bookingForm) throws ParseException{
		String departCity = Utils.getIataCode(bookingForm.getSearchForm().getDepartCity());

		if (departCity.length() > 3) {
			departCity = flightManager.getAirportCode(departCity);	//ambil code iata

			// last attempt, cari manual lwt list
			if (departCity == null) {
				List<String> airports = airportManager.getPrettyAirports();
				for (String s : airports) {
					if (s.toLowerCase().contains(bookingForm.getSearchForm().getDepartCity().toLowerCase())) {
						departCity = Utils.getIataCode(s);
						// auto repair searchform
						bookingForm.getSearchForm().setDepartCity(s);
						break;
					}
				}
			}else{
				//autofix
				AirportVO a = airportManager.getAirportByIata(departCity);
				bookingForm.getSearchForm().setDepartCity(a.getCity() + " (" + a.getIata() + ")");
			}

		} else {
			// validate the iata
			if (StringUtils.isEmpty(flightManager.getAirportName(departCity))) {
				return false;
			}
		}
		
		return true;
	}
	private boolean tryFixDestinationCityTextBox(BookingFormB2B bookingForm) throws ParseException{
		String destinationCity = Utils.getIataCode(bookingForm.getSearchForm().getDestCity());

		if (destinationCity.length() > 3) {
			destinationCity = flightManager.getAirportCode(destinationCity);

			// last attempt, cari manual lwt list
			if (destinationCity == null) {// Meulaboh
				List<String> airports = airportManager.getPrettyAirports();
				for (String s : airports) {
					if (s.toLowerCase().contains(bookingForm.getSearchForm().getDestCity().toLowerCase())) {
						destinationCity = Utils.getIataCode(s);
						// auto repair searchform
						bookingForm.getSearchForm().setDestCity(s);
						break;
					}
				}
			}else{
				//autofix
				AirportVO a = airportManager.getAirportByIata(destinationCity);
				bookingForm.getSearchForm().setDestCity(a.getCity() + " (" + a.getIata() + ")");
			}

		} else {
			// validate the iata
			if (StringUtils.isEmpty(flightManager.getAirportName(destinationCity))) {
				return false;
			}
		}
		
		return true;

	}

	
    @RequestMapping(value = "/home", method = RequestMethod.POST, params = "searchParam")
    public ModelAndView onSearch( @ModelAttribute("maBookingForm")BookingFormB2B bookingForm, final BindingResult errors
    		, HttpServletRequest request
    		, HttpServletResponse response
    		, RedirectAttributes redirectAttr
    		)
            throws Exception{
    	
    	//utk tes doank
    	/*
		SearchForm form = new SearchForm();
		form.setDepartCity("bdo");
		form.setDestCity("sub");
		form.setDepartDate(new Date());
		form.setReturnDate(new Date());
		form.setTripMode(0);
		
		SqivaHandler kalstarHandler = new SqivaHandler(Airline.KALSTAR, setupDao);
		List<FlightSearchB2B> _schedules = (List<FlightSearchB2B>) kalstarHandler.getSchedules(form);
		
		System.err.println(_schedules);
		if (true)
			return new ModelAndView("redirect:/flight/b2b/search");
    	*/
    	
    	//user information
    	User user = getUserManager().getUserByUsername(request.getRemoteUser());
    	bookingForm.prepareContacts(user);

    	if (!tryFixDepartCityTextBox(bookingForm)){
			errors.rejectValue("searchForm.departCity", "errors.invalid", new Object[] { getText("flight.cityFrom", request.getLocale()) },
                    "Depart City");					
    		
    	}
    	if (!tryFixDestinationCityTextBox(bookingForm)){
			errors.rejectValue("searchForm.destCity", "errors.invalid", new Object[] { getText("flight.cityTo", request.getLocale()) },
                    "Destination City");
    	}

    	if (validator != null) {
			validator.validate(bookingForm, errors);
			
			if (!bookingForm.getSearchForm().isValidDates()) {
				//returnDate adalah nama property di maSearchForm
                errors.rejectValue("searchForm.returnDate", "errors.invalid", new Object[] { getText("flight.dateReturn", request.getLocale()) },
                        "Return Date");
			}
			
			if (errors.hasErrors()) {
				
				try {
					setAgentInfo(request, response);
				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/logout");
				}

				return new ModelAndView("/home");
			}
		}
    	
    	// mengapa ga langsung change page ke flight search ?
    	redirectAttr.addFlashAttribute("maBookingFormFromOtherPage", bookingForm);
    	return new ModelAndView("redirect:/flight/b2b/searchnew");
    	/*
    	
		List<FlightSearchB2B> list = flightManager.searchDepartFlightB2B(bookingForm.getSearchForm());

    	redirectAttr.addFlashAttribute("listDepart", list);
    	redirectAttr.addFlashAttribute("seatclasses_dep", Utils.displayClasses(false, list));

    	if (bookingForm.getSearchForm().getTripMode() == 1){
    		//swap city & departDate
    		List<FlightSearchB2B> listRet = flightManager.searchReturnFlightB2B(bookingForm.getSearchForm());
        	redirectAttr.addFlashAttribute("listReturn", listRet);
        	redirectAttr.addFlashAttribute("seatclasses_ret", Utils.displayClasses(false, listRet));
    	}
    	
    	return new ModelAndView("redirect:/flight/b2b/searchnew");
    	*/
    }

    
    //free service for 3rd party collected here
    /**
     * sementara ini dipakai untuk mas andri.
     * email konfirmasi kalau agent telah melakukan topup via atm 
     * @param user
     * @param token
     * @return
     * @throws Exception
     * @see {@link UserController#handleRequestFromMandira(String, String)}
     */
	@RequestMapping(value = "/layanan/emailConfirmTopUpToAgent", method = RequestMethod.GET, produces = "application/json")	//return as json
	public @ResponseBody ResponseClient sendTopUpEmailToAgent(
			HttpServletRequest request
			,@RequestParam(required = false, value = "id") String id
			,@RequestParam(required = false, value = "subject") String subject
			,@RequestParam(required = false, value = "amount") String amount
			,@RequestParam(required = false, value = "ccy") String ccy
			,@RequestParam(required = false, value = "token") String token) throws Exception{
		
		ResponseClient resp = new ResponseClient();

		if (!setupDao.checkToken3rdParty(token, request.getRemoteAddr())){
			resp.setData("Session expired");
			resp.setErrCode("1101");
			return resp;
		}
		
		try {
			User user = financeManager.getUserByAccountID(id);
			AgentVO agentVO = agentManager.getAgentByUserId(user.getId());
			TopUpInfo topUp = new TopUpInfo(financeManager.getUserAccountById(id), setupDao) ;
			emailManager.sendTopUpVAPaidConfirm(true, agentVO, topUp, subject, amount, ccy, RequestUtil.getAppURL(request));

			resp.setData("Email sent");
			resp.setErrCode("0");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			
			resp.setData("Error occurred. Please contact administrator.");
			resp.setErrCode("2102");
		}
		
		return resp;
	}

	@RequestMapping(value = "/layanan/getToken", method = RequestMethod.GET, produces = "application/json")	//return as json
	public @ResponseBody ResponseClient getToken(HttpServletRequest request) throws Exception{
		ResponseClient resp = new ResponseClient();
		try {
			resp.setData(setupDao.generateToken3rdParty(request.getRemoteAddr()));
			resp.setErrCode("0");
		} catch (Exception e) {
			e.printStackTrace();
			resp.setErrCode("2101");
		}
		return resp;
	}
	
	@RequestMapping(value = "/layanan/checkToken", method = RequestMethod.GET, produces = "application/json")	//return as json
	public @ResponseBody ResponseClient checkToken(HttpServletRequest request, @RequestParam(required = false, value = "token") String token) throws Exception{
		ResponseClient resp = new ResponseClient();
		
		if (!setupDao.checkToken3rdParty(token, request.getRemoteAddr())){
			resp.setData("Session expired");
			resp.setErrCode("1101");
			return resp;
		}

		resp.setData("Session valid");
		resp.setErrCode("0");

		return resp;
	}

    //end free servuce
}
