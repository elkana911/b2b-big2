package com.big.web.b2b_big2.webapp.controller.flight.b2b.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import com.big.web.b2b_big2.webapp.controller.BaseFormController;

@Controller
public class FlightRetrieveBookingController extends BaseFormController {

	@Autowired
	private IBookingManager bookingManager; 

	/**
    * @param username
    * @param token
    * @return
    */
   @RequestMapping(value = "/flight/b2b/retrievebooking", method = RequestMethod.GET)
   public ModelAndView showForm(
           @RequestParam(value = "bookingcode", required = false) String bookingCode
           , HttpServletRequest request
			, HttpServletResponse response)
   {
		try {
			setAgentInfo(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/logout");
		}
//       if (StringUtils.isBlank(username)) {
//           username = request.getRemoteUser();
//       }
//       if (StringUtils.isNotBlank(token) && !getUserManager().isRecoveryTokenValid(username, token)) {
//           saveError(request, getText("updatePassword.invalidToken", request.getLocale()));
//           return new ModelAndView("redirect:/");
//       }

       return new ModelAndView("/flight/flightRetrieveBooking").addObject("bookingcode", bookingCode);
   }
   
   @RequestMapping(value = "/flight/b2b/retrievebooking*", method = RequestMethod.POST)
   public ModelAndView onSubmit(
           @RequestParam(value = "bookingcode", required = true) final String bookingCode, HttpServletRequest request
			, HttpServletResponse response)
           throws Exception
   {
       log.debug("FlightRetrieveBooking onSubmit for bookingcode: " + bookingCode);
       final Locale locale = request.getLocale();
/*
       if (StringUtils.isEmpty(password)) {
           saveError(request, getText("errors.required", getText("updatePassword.newPassword.label", locale), locale));
           return showForm(username, null, request);
       }

       User user = null;
       final boolean usingToken = StringUtils.isNotBlank(token);
       if (usingToken) {
           log.debug("Updating Password for username " + username + ", using reset token");
           user = getUserManager().updatePassword(username, null, token, password,
                   RequestUtil.getAppURL(request));

       } else {
           log.debug("Updating Password for username " + username + ", using current password");
           if (!username.equals(request.getRemoteUser())) {
               throw new AccessDeniedException("You do not have permission to modify other users password.");
           }
           user = getUserManager().updatePassword(username, currentPassword, null, password,
                   RequestUtil.getAppURL(request));
       }

       if (user != null) {
           saveMessage(request, getText("updatePassword.success", new Object[] { username }, locale));
       }
       else {
           if (usingToken) {
               saveError(request, getText("updatePassword.invalidToken", locale));
           }
           else {
               saveError(request, getText("updatePassword.invalidPassword", locale));
               return showForm(username, null, request);
           }
       }
*/
		BookingFlightVO	bf = bookingManager.getBookingFlightByBookingCode(bookingCode);
		if (bf == null){
			saveError(request, getText("pnr.errors.nodata", bookingCode, locale));
			
			return showForm(bookingCode, request, response);
		}
		
		return new ModelAndView("redirect:/flight/b2b/booking/" + bf.getId());
   }
   
   
}
