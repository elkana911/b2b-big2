package com.big.web.b2b_big2.webapp.controller.flight.b2b.search;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.big.web.b2b_big2.booking.model.BookingFlightVO;
import com.big.web.b2b_big2.finance.exception.TopUpIncompleteRegException;
import com.big.web.b2b_big2.flight.pojo.BookingFormB2B;
import com.big.web.b2b_big2.flight.pojo.FlightSearchB2B;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.b2b_big2.webapp.controller.BaseFormController;
import com.big.web.b2b_big2.webapp.controller.agent.TopUpInfo;

/** 
 * 
 * @author Administrator
 * @deprecated
 * @see FlightSearchByODRController
 *
 */
@Controller
@RequestMapping("/flight/b2b/odr")
public class FlightSearchByRequestController extends BaseFormController {

	private static final String SEARCH_PAGE = "/flight/odr/flightSearchODR";
	private static final String BOOK_LIST_PAGE = "/flight/flightBookListB2B"; // for

	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		super.initBinder(request, binder);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	/**
	 * 
	 * @param departIata
	 * @param destIata
	 * @param airlineIata	all to select airline, KD for kalstar only
	 * @param action	0 no action, 1 act of searching
	 * @param model
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	protected ModelAndView loadForm(@RequestParam(value = "dep", required = false) String departIata
			,@RequestParam(value = "dest", required = false) String destIata
			,@RequestParam(value = "airline", required = false) String airlineIata
			,@RequestParam(value = "action", required = false, defaultValue = "0") Integer action
			,ModelMap model
			,HttpSession session
			,final HttpServletRequest request
			,final HttpServletResponse response) {

		String referer = request.getHeader("Referer");
		
		String from = request.getParameter("from");
		if (from != null && from.equals("menu")){
			//manual clean up
			cleanSession(model, session, "listDepart");
			cleanSession(model, session, "listReturn");
			cleanSession(model, session, "maBookingForm");
		}

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

		ModelAndView mav = null;
		// may accept from another form, no validate, just display
		if (model.get("maBookingFormFromOtherPage") != null) {
			mav = new ModelAndView(SEARCH_PAGE, "maBookingForm", model.get("maBookingFormFromOtherPage"));
		} else {

			// to handle double submit/f5 button
			Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
			if (map != null) {
				if (map.get("listDepart") != null || map.get("listReturn") != null) {
					
					mav = new ModelAndView(SEARCH_PAGE);
					
				} else if (map.get("listBooks") != null) {
					
					List<BookingFlightVO> bookFlight = (List<BookingFlightVO>) map.get("listBooks");
					return new ModelAndView(BOOK_LIST_PAGE).addObject("listBooks", bookFlight);	//.addObject("agent", map.get("agent")); kayanya udah dihandle di setAgentInfo

				}else
					mav = new ModelAndView(SEARCH_PAGE, model);
				
			} else {
				
				mav = new ModelAndView(SEARCH_PAGE);
				
				BookingFormB2B _backingBean;
				
				if (model.get("maBookingForm") != null){
					_backingBean = (BookingFormB2B) model.get("maBookingForm");
				}else{
					_backingBean = new BookingFormB2B();
					
					if (!Utils.isEmpty(departIata) 
							&& !Utils.isEmpty(destIata)							
							){
						
						_backingBean.getSearchForm().setDepartCity(departIata);
						_backingBean.getSearchForm().setDestCity(destIata);
						
						if (!Utils.isEmpty(airlineIata)){
							_backingBean.getSearchForm().setAirlines(new String[]{airlineIata});
						}
						if (action == 0){
							
						}
					}
				}
				
				mav.addObject("maBookingForm", _backingBean);
				
				if (model.get("listDepart") != null){
					mav.addObject("listDepart", model.get("listDepart"));
					mav.addObject("seatclasses_dep", Utils.displayClasses(false, (List<FlightSearchB2B>) model.get("listDepart")));
				}
				if (model.get("listReturn") != null){
					mav.addObject("listReturn", model.get("listReturn"));
					mav.addObject("seatclasses_ret", Utils.displayClasses(false, (List<FlightSearchB2B>) model.get("listReturn")));
				}

			}
		}
		return mav;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "searchParam")
	public ModelAndView onSearchTicket(@ModelAttribute("maBookingForm") BookingFormB2B bookingForm, ModelMap model,  
			final BindingResult errors, HttpServletRequest request) throws Exception {
		
		ModelAndView mav = new ModelAndView(SEARCH_PAGE);
		
		return mav;
	}
	
}
