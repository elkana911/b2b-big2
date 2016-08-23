package com.big.web.b2b_big2.webapp.controller.hotel.b2b;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.big.web.b2b_big2.booking.service.IBookingManager;
import com.big.web.b2b_big2.finance.exception.TopUpIncompleteRegException;
import com.big.web.b2b_big2.hotel.model.HotelSearch;
import com.big.web.b2b_big2.hotel.pojo.BookingFormB2B;
import com.big.web.b2b_big2.hotel.service.IHotelManager;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.webapp.controller.BaseFormController;
import com.big.web.b2b_big2.webapp.controller.agent.TopUpInfo;
import com.big.web.model.User;

@Controller
public class HotelSearchB2BController extends BaseFormController {
	private static final String SEARCH_PAGE = "/hotel/hotelSearchB2B";
	private static final String BOOK_LIST_PAGE = "/hotel/flightBookListB2B"; // for
																				// multi
																				// booking
	@Autowired
	private IBookingManager bookingManager;
	
	@Autowired
	private IHotelManager hotelManager;

	public HotelSearchB2BController() {
		setCancelView("redirect:/home");
		setSuccessView("/hotel/b2b/search"); // tampilkan dihalaman yang sama
	}

	// implement
	// http://www.jeerocks.com/2012/06/spring-mvchandling-multiple-submit.html
	// jadi bisa kupisahkan mana yg load pertama kali, search, dan book
	// called first time
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/hotel/b2b/search", method = RequestMethod.GET)
	protected ModelAndView loadForm(ModelMap model
			, HttpSession session
			, final HttpServletRequest request
			, final HttpServletResponse response
			) {

		System.out.println("HotelSearchB2BController.loadForm in action" + model.containsAttribute("maBookingForm"));

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
				if (map.get("listHotel") != null) {
					mav = new ModelAndView(SEARCH_PAGE);
				} else if (map.get("listBooks") != null) {
//					List<BookingFlightVO> bookFlight = (List<BookingFlightVO>) map.get("listBooks");

//					return new ModelAndView(BOOK_LIST_PAGE).addObject("listBooks", bookFlight).addObject("agent", map.get("agent"));

				}
			} else {

				mav = new ModelAndView(SEARCH_PAGE, "maBookingForm", new BookingFormB2B());
			}
		}
		return mav;
	}
	

	@RequestMapping(value = "/hotel/b2b/search", method = RequestMethod.POST, params = "searchParam")
	public ModelAndView onSearchTicket(@ModelAttribute("maBookingForm") BookingFormB2B bookingForm,
			final BindingResult errors, HttpServletRequest request, HttpSession session, RedirectAttributes ra)
			throws Exception {// mau pake HttpServletRequest atau Model sama
								// saja ternyata

		System.out.println("FlightSearchController.showForm in action");

		bookingForm.prepareContacts();

		/*
		 * due to F5 and Enter key impact, radiobutton is not binded to any bean
		 * hence bookingForm.depId still kept selected choice so need to clean
		 * up depId and retId
		 */
//		bookingForm.setDepId(null);

		if (validator != null) {
			validator.validate(bookingForm, errors);

			if (!bookingForm.getSearchForm().isValidDates()) {
				// returnDate adalah nama property di maSearchForm
				errors.rejectValue("searchForm.returnDate", "errors.invalid",
						new Object[] { getText("flight.dateReturn", request.getLocale()) }, "Return Date");
			}

			if (errors.hasErrors()) {
				// sessionStatus.setComplete();
				request.setAttribute("agent", agent);
				return new ModelAndView(SEARCH_PAGE);
			}
		}

		// user information
		User user = getUserManager().getUserByUsername(request.getRemoteUser());
		bookingForm.getCustomer().setAgentName(user.getFirstName());
		bookingForm.getCustomer().setAgentEmail(user.getEmail());
		bookingForm.getCustomer().setAgentPhone1(user.getPhoneNumber());

		ra.addFlashAttribute("maBookingForm", bookingForm);

        List<HotelSearch> list = hotelManager.getCheapest(bookingForm);
		ra.addFlashAttribute("listHotel", list);

		/*
		String departIata = Utils.getIataCode(bookingForm.getSearchForm().getDepartCity());
		String destinationIata = Utils.getIataCode(bookingForm.getSearchForm().getDestCity());
		ra.addFlashAttribute(
				"depart_Title",
				"DEPARTURE (" + (StringUtils.isEmpty(departIata) ? "All" : departIata) + " to "
						+ (StringUtils.isEmpty(destinationIata) ? "All" : destinationIata) + " on "
						+ Utils.prettyDate(bookingForm.getSearchForm().parseDepartDate()) + ")");

		// TODO for faster result, i think depart & return should use one
		// session only, via flightManager
		if (bookingForm.getSearchForm().getTripMode() == 1) {
			// swap city & departDate

//			ra.addFlashAttribute("listReturn", flightManager.searchReturnFlightB2B(bookingForm.getSearchForm()));
			ra.addFlashAttribute(
					"return_Title",
					"RETURN (" + (StringUtils.isEmpty(destinationIata) ? "All" : destinationIata) + " to "
							+ (StringUtils.isEmpty(departIata) ? "All" : departIata) + " on "
							+ Utils.prettyDate(bookingForm.getSearchForm().parseReturnDate()) + ")");
		}
		*/
		return new ModelAndView("redirect:/hotel/b2b/search");
	}

}
