package com.big.web.b2b_big2.webapp.controller.flight.b2b.search;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.big.web.b2b_big2.booking.model.BookingFlightVO;
import com.big.web.b2b_big2.finance.exception.TopUpIncompleteRegException;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.model.AirportVO;
import com.big.web.b2b_big2.flight.pojo.BasicContact;
import com.big.web.b2b_big2.flight.pojo.BookingFormB2B;
import com.big.web.b2b_big2.flight.pojo.ContactInfant;
import com.big.web.b2b_big2.flight.pojo.FareSummary;
import com.big.web.b2b_big2.flight.pojo.FlightSearchB2B;
import com.big.web.b2b_big2.flight.pojo.FlightSearchResult;
import com.big.web.b2b_big2.flight.pojo.SearchForm;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.Age;
import com.big.web.b2b_big2.util.ErrorHandler;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.b2b_big2.webapp.controller.agent.TopUpInfo;
import com.big.web.message.PROCESS_TYPE;
import com.big.web.model.User;

@Controller
@SessionAttributes({"listDepart", "listReturn", "maBookingForm", "seatclasses_dep", "seatclasses_ret"})	//so when error occured, these variables will exists
//@SessionAttributes({"maBookingForm", "agent", "listBooks", "payments"})
public class FlightSearchByODRController extends FlightSearchController {
	// jika /flight/flightBookB2B hasilnya /travel6/app/flight/flightBookB2B
	// jika request.getContextPath+/flight/flightBookB2B =
	// /travel6/app/travel6/flight/flightBookB2B
	// jika ../../flight/flightBookB2B = /travel6/app/flight/flightBookB2B
	private static final String BOOK_LIST_PAGE = "/flight/flightBookListB2B"; // for
																				// multi
																				// booking
	private static final String SEARCH_ODR_PAGE = "/flight/flightSearchB2B_ODR";
	private static final String SEARCH_ODR_PATH = "/flight/b2b/searchnew";
		
	public FlightSearchByODRController() {
		setCancelView("redirect:/home");
		setSuccessView(SEARCH_ODR_PATH); // tampilkan dihalaman yang sama
	}

	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		super.initBinder(request, binder);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	/**
	 * Check 2 conditions:
	 * <br>1. Empty names
	 * <b2>2. only char and space allowed
	 * @param compName
	 * @param value
	 * @param errors
	 * @param locale
	 * @return
	 */
	private boolean validatePerson(String compName, String value, BindingResult errors, Locale locale){
		if (StringUtils.isEmpty(value)){
			errors.rejectValue(compName, "errors.required",
					new Object[] { getText("ticket.personName", locale) }, "Person Name");
			return false;
		}

		String[] s = org.apache.commons.lang.StringUtils.split(value, ' ');
		for (String string : s) {
			if (string.length() < 2){
				errors.rejectValue(compName, "errors.field.tooShort",
						new Object[] { "personName" }, "Person Name");
				return false;
			}
			
			//4 may 2015 kalstar tidak membolehkan ada titik
			//sebelumnya dot titik diperbolehkan dalam nama
//			if (!StringUtils.isAlphaSpace(string.replaceAll("\\.", ""))){
			if (!StringUtils.isAlphaSpace(string)){
				errors.rejectValue(compName, "errors.alphaspace");
				return false;
			}
		}
		
		return true;
	}

	private void validateBeforeBook(BookingFormB2B bookingForm, BindingResult errors, Locale locale) {

		if (StringUtils.isEmpty(bookingForm.getCustomer().getFullName())) {
			// returnDate adalah nama property di maSearchForm
			errors.rejectValue("customer.fullName", "errors.required",
					new Object[] { getText("ticket.passengerContact", locale) }, "Customer Name");
		}

		boolean checkAdultBirthdayAndNationality = bookingForm.isBirthdayRequired();
		
		boolean checkIdCard = bookingForm.isIDCardRequired();
		for (int i = 0; i < bookingForm.getAdult().size(); i++) {
			BasicContact c = bookingForm.getAdult().get(i);
			
			if (checkAdultBirthdayAndNationality){
				if (c.getBirthday() == null){
					errors.rejectValue("adult[" + i + "].birthday", "errors.required",
							new Object[] { getText("ticket.personBirthday", locale) }, "Adult Birthday");
				}else{
					Age age = Age.calculateAge(c.getBirthday(), bookingForm.getSearchForm().parseDepartDate());
					if (!Age.isAdultAge(age)){
						errors.rejectValue("adult[" + i + "].birthday", "errors.adult.outAge",
								new Object[] { getText("ticket.personBirthday", locale) }, "Adult Birthday");
					}

				}
				if (Utils.isEmpty(c.getCountryCode())){
					errors.rejectValue("adult[" + i + "].countryCode", "errors.required",
							new Object[] { getText("ticket.personNationality", locale) }, "Adult Nationality");
					
				}
			}

			validatePerson("adult[" + i + "].fullName", c.getFullName(), errors, locale);
			
			if (c.getTitle() == null){
				errors.rejectValue("adult[" + i + "].title", "errors.required",
						new Object[] { getText("ticket.personTitle", locale) }, "Person Title");
			}
			
			if (checkIdCard){
				if (Utils.isEmpty(c.getIdCard()) || c.getIdCard().length() < 4){
					errors.rejectValue("adult[" + i + "].idCard", "errors.required",
							new Object[] { getText("ticket.personIdCard", locale) }, "Person Id Card");				
				}
			}
			
			/*
			//check duplicates
			int d = BasicContact.getDuplicateContact(c, bookingForm.getAdult()); 
			if (d > 0){
				errors.rejectValue("adult[" + d + "].fullName", "errors.passenger.duplicate",
						new Object[] { getText("ticket.personName", locale) }, "Person Name");				

			}
			 */
		}
		
		for (int i = 0; i < bookingForm.getChild().size(); i++) {
			BasicContact c = bookingForm.getChild().get(i);
			
			validatePerson("child[" + i + "].fullName", c.getFullName(), errors, locale);

			if (c.getTitle() == null){
				errors.rejectValue("child[" + i + "].title", "errors.required",
						new Object[] { getText("ticket.personTitle", locale) }, "Person Title");
			}
			
			if (c.getBirthday() == null){
				errors.rejectValue("child[" + i + "].birthday", "errors.required",
						new Object[] { getText("ticket.personBirthday", locale) }, "Child Birthday");
			}else{
//				dihitung dari tanggal berangkat apa umurnya di bawah 2 taun
				Age age = Age.calculateAge(c.getBirthday(), bookingForm.getSearchForm().parseDepartDate());
				if (!Age.isChildAge(age)){
					errors.rejectValue("child[" + i + "].birthday", "errors.child.outAge",
							new Object[] { getText("ticket.personBirthday", locale) }, "Child Birthday");
				}
				
			}

			/*
			//check duplicates
			int d = BasicContact.getDuplicateContact(c, bookingForm.getChild()); 
			if (d > 0){
				errors.rejectValue("child[" + d + "].fullName", "errors.passenger.duplicate",
						new Object[] { getText("ticket.personName", locale) }, "Person Name");				

			}
			 */
		}

		for (int i = 0; i < bookingForm.getInfant().size(); i++) {
			ContactInfant infant = bookingForm.getInfant().get(i);
			/*
			if (StringUtils.isEmpty(c.getFullName())){
				errors.rejectValue("infant[" + i + "].fullName", "errors.required",
						new Object[] { getText("ticket.personName", locale) }, "Infant Name");
			}
			*/
			validatePerson("infant[" + i + "].fullName", infant.getFullName(), errors, locale);
			
			if (infant.getBirthday() == null){
				errors.rejectValue("infant[" + i + "].birthday", "errors.required",
						new Object[] { getText("ticket.personBirthday", locale) }, "Infant Birthday");
			}else{
//				dihitung dari tanggal berangkat apa umurnya di bawah 2 taun
				Age age = Age.calculateAge(infant.getBirthday(), bookingForm.getSearchForm().parseDepartDate());
				if (!Age.isInfantAge(age)){
					errors.rejectValue("infant[" + i + "].birthday", "errors.infant.over2years",
							new Object[] { getText("ticket.personBirthday", locale) }, "Infant Birthday");
				}
			}
			/*
			//check duplicates
			int d = BasicContact.getDuplicateContact(infant, bookingForm.getInfant()); 
			if (d > 0){
				errors.rejectValue("infant[" + d + "].fullName", "errors.passenger.duplicate",
						new Object[] { getText("ticket.personName", locale) }, "Person Name");				

			}
			*/
		}
		
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
	 * @throws ExecutionException 
	 * @throws ParseException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = SEARCH_ODR_PATH, method = RequestMethod.GET)
	protected ModelAndView loadForm(@RequestParam(value = "dep", required = false) String departIata
			,@RequestParam(value = "dest", required = false) String destIata
			,@RequestParam(value = "airline", required = false) String airlineIata
			,@RequestParam(value = "action", required = false, defaultValue = "0") Integer action
			,ModelMap model
			,HttpSession session
			,final HttpServletRequest request
			,final HttpServletResponse response) throws IllegalArgumentException, IOException, InterruptedException, ParseException, ExecutionException {

		// String referer = request.getHeader("Referer");
		
		// log.debug("FlightSearchB2BController.loadForm in action.Ref:" + referer);
		
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
			
			BookingFormB2B bForm = (BookingFormB2B) model.get("maBookingFormFromOtherPage");
			mav = new ModelAndView(SEARCH_ODR_PAGE, "maBookingForm", bForm);
			
			searchTicket(mav, bForm.getSearchForm());
			
			return mav;
		} 

		// to handle double submit/f5 button
		Map<String, ?> map = RequestContextUtils.getInputFlashMap(request);
		if (map != null) {
			if (map.get("listDepart") != null || map.get("listReturn") != null) {
				
				mav = new ModelAndView(SEARCH_ODR_PAGE);
				
			} else if (map.get("listBooks") != null) {
				
				List<BookingFlightVO> bookFlight = (List<BookingFlightVO>) map.get("listBooks");
				return new ModelAndView(BOOK_LIST_PAGE).addObject("listBooks", bookFlight);	//.addObject("agent", map.get("agent")); kayanya udah dihandle di setAgentInfo

			}else
				mav = new ModelAndView(SEARCH_ODR_PAGE, model);
			
		} else {
			
			mav = new ModelAndView(SEARCH_ODR_PAGE);
			
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
			
//				mav = new ModelAndView(SEARCH_PAGE, "maBookingForm", new BookingFormB2B());
			//manual clean up
			/*
			cleanSession(model, session, "listDepart");
			cleanSession(model, session, "listReturn");
			cleanSession(model, session, "maBookingForm");
			*/
		}
		return mav;
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
	
	private void searchTicket(ModelAndView mav, SearchForm searchForm) throws IllegalArgumentException, IOException, InterruptedException, ParseException, ExecutionException{
		
		boolean isUseODR = setupDao.getValueAsBoolean(ISetupDao.KEY_ODR_SEARCH_ENABLED);
		
		List<FlightSearchB2B> listDepart = null;
		List<FlightSearchB2B> listReturn = null;
		boolean odrFailed = false;
		if (isUseODR){
			try {
				FlightSearchResult fsr = _callODR(searchForm.getAirlines().toArray(new Airline[searchForm.getAirlines().size()]), 
						searchForm.parseDepartDate(), 
						searchForm.getTripMode() == 1 ? searchForm.parseReturnDate() : null, 
						searchForm.getDepartIata(), 
						searchForm.getDestinationIata(), 
						searchForm.getAdult(), 
						searchForm.getChildren(),
						searchForm.getInfants());
				
				listDepart = fsr.getDepartList();
				listReturn = fsr.getReturnList();
			} catch (Exception e) {
				e.printStackTrace();
				
				odrFailed = true;
			}
		}
		
//		if (!isUseODR || odrFailed){
		//refresh entah pake ODR atau tidak. callODR diatas akan diproses jika umurnya dibawah 10 menit dan akan ditunggu sampai selesai,
		// jadi kode di bawah ini aman dipanggil lagi
			listDepart = flightManager.searchDepartFlightB2B(searchForm);
			if (searchForm.getTripMode() == 1){
				listReturn = flightManager.searchReturnFlightB2B(searchForm);
			}

			//syncseat dijalankan utk H-1 dan H+1
//			flightManager.syncSeat(bookingForm.getSearchForm());

//		}
		
		log.info("ODR STATUS=" + (isUseODR ? "" : "NOT ") + "RUNNING " + (odrFailed ? "WITH FAILURES" : "") );
		
		if (!isUseODR){
			//update diam2
			_callODRBackground(searchForm.getAirlines().toArray(new Airline[searchForm.getAirlines().size()]), 
					searchForm.parseDepartDate(), 
					searchForm.getTripMode() == 1 ? searchForm.parseReturnDate() : null, 
					searchForm.getDepartIata(), 
					searchForm.getDestinationIata(), 
					searchForm.getAdult(), 
					searchForm.getChildren(),
					searchForm.getInfants());
		}
		

		mav.addObject("listDepart", listDepart);
		mav.addObject("seatclasses_dep", Utils.displayClasses(false, listDepart));

		if (listReturn != null){
			mav.addObject("listReturn", listReturn);
			mav.addObject("seatclasses_ret", Utils.displayClasses(false, listReturn));
		}
		
	}

	@RequestMapping(value = SEARCH_ODR_PATH, method = RequestMethod.POST, params = "searchParam")
	public ModelAndView onSearchTicket(@ModelAttribute("maBookingForm") BookingFormB2B bookingForm, ModelMap model,  
			final BindingResult errors, HttpServletRequest request)
			throws Exception {

		User user = getUserManager().getUserByUsername(request.getRemoteUser());
		bookingForm.prepareContacts(user);

		if (!tryFixDepartCityTextBox(bookingForm)){
			errors.rejectValue("searchForm.departCity", "errors.invalid",
					new Object[] { getText("flight.cityFrom", request.getLocale()) }, "Depart City");			
		}

		if (!tryFixDestinationCityTextBox(bookingForm)){
			errors.rejectValue("searchForm.destCity", "errors.invalid",
					new Object[] { getText("flight.cityTo", request.getLocale()) }, "Destination City");			
		}

		if (validator != null) {
			validator.validate(bookingForm, errors);

			if (!bookingForm.getSearchForm().isValidDates()) {
				errors.rejectValue("searchForm.returnDate", "errors.invalid",
						new Object[] { getText("flight.dateReturn", request.getLocale()) }, "Return Date");
			}

			if (errors.hasErrors()) {
				ModelAndView mav = new ModelAndView(SEARCH_ODR_PAGE);
				return mav;
			}
		}
		
		
		ModelAndView mav = new ModelAndView(SEARCH_ODR_PAGE);
		mav.addObject("maBookingForm", bookingForm);
		
		searchTicket(mav, bookingForm.getSearchForm());
		
		return mav;
	}

	@RequestMapping(value = SEARCH_ODR_PATH, method = RequestMethod.POST, params = "book_Param")
	public ModelAndView onBookTicket(@ModelAttribute("maBookingForm") BookingFormB2B bookingForm, ModelMap model, SessionStatus session,
			final BindingResult errors, HttpServletRequest request, RedirectAttributes ra)
			throws Exception {
		
		  log.debug("1--------------onBookTicket :" + bookingForm.toString());
		
		//should repair any modification attempted by user
		if (!tryFixDepartCityTextBox(bookingForm)){
			errors.rejectValue("searchForm.departCity", "errors.invalid",
					new Object[] { getText("flight.cityFrom", request.getLocale()) }, "Depart City");						
		}

		if (!tryFixDestinationCityTextBox(bookingForm)){
			errors.rejectValue("searchForm.destCity", "errors.invalid",
					new Object[] { getText("flight.cityTo", request.getLocale()) }, "Destination City");						
		}
		
		
		// INFO: booking code diperoleh dari agen bersangkutan (tidak generate
		// seperti b2c)
		// 1. collect selected flights
		List<String> depFlights = new ArrayList<String>();
		if (!StringUtils.isEmpty(bookingForm.getDepId())) {
			depFlights.add(bookingForm.getDepId());
			if (!StringUtils.isEmpty(bookingForm.getDepTrans2Id())) {
				depFlights.add(bookingForm.getDepTrans2Id());
				if (!StringUtils.isEmpty(bookingForm.getDepTrans3Id())) {
					depFlights.add(bookingForm.getDepTrans3Id());
				}
			}
		}

		List<String> retFlights = new ArrayList<String>();
		if (!StringUtils.isEmpty(bookingForm.getRetId())) {
			retFlights.add(bookingForm.getRetId());
			if (!StringUtils.isEmpty(bookingForm.getRetTrans2Id())) {
				retFlights.add(bookingForm.getRetTrans2Id());
				if (!StringUtils.isEmpty(bookingForm.getRetTrans3Id())) {
					retFlights.add(bookingForm.getRetTrans3Id());
				}
			}
		}
		
		//2. collect passenger and contact
		//skip for now krn bisa diakses via bookingForm.getAdult()
		
		// 3. get price
		String[] idDep = depFlights.toArray(new String[depFlights.size()]);
		String[] idRet = retFlights.toArray(new String[retFlights.size()]);
		FareSummary fareSum = getFareDetail(idDep, idRet, 
				bookingForm.getSearchForm().getDepartDate(), 
				bookingForm.getSearchForm().getDestCity(), 
				bookingForm.getAdult().size(), 
				bookingForm.getChild().size(), 
				bookingForm.getInfant().size());
		log.debug("2--------------" + fareSum.toString());
		/*
		FareSummary [departDate=Thursday, 30 Apr 2015
			, destinationCity=Banjarmasin
			, fareInfo=[FareInfo [id=294ed73b-3a4a-4d2d-84e2-1dbc0ff067a0
			, radioId=0~M~~M~RGFR~~6~X
			, ref_ClassFareId=18026853
			, className=M, baseFare=null
			, flightNo=QG  874, fuelSurcharge=100000
			, currency=IDR, agentDiscount=null
			, rate=590000, tax_adult=59000
			, tax_child=0, tax_infant=0, amount=59000
			, insurance=0, psc=40000, iwjr=5000
			, childFare=590000, infantFare=590000
			, childDiscount=0, promoDiscount=null
			, pph=null, 
		
		route=Route [from=Airport [name=Soekarno Hatta Intl, city=Jakarta, iataCode=CGK, icaoCode=WIII, countryId=62, schedule=2015-04-30 07:25:00.0], 
		to=Airport [name=Syamsudin Noor, city=Banjarmasin, iataCode=BDJ, icaoCode=WAOO, countryId=62, schedule=2015-04-30 10:05:00.0]]
		
		, routeMode=DEPART
		, airline=Citilink, airlineIata=QG, agentId=49C14970C4234A8EB4B41781ADF1982B, personType=null, timeLimit=null, bookCode=null, bookStatus=null, cheapest=false, journeyKey=QG~ 874~ ~~CGK~04/30/2015 07:25~BDJ~04/30/2015 10:05~
		]]
		, psc=40,000, tax=59,000, iwjr=5,000, surcharge=100,000, total=794,000
		]
		*/
		agent.getCart().clear();
		agent.getCart().getFlightCart().add2Cart(bookingForm, fareSum.getFareInfo());
		
		if (agent.getCart().getFlightCart().getItineraries().size() < 1){
			saveError(request, getText("errors.invalid.schedules", request.getLocale()));
		}
		
		//4. validate, permit to buy ?
		boolean globalErrors = false;
		if (validator != null) {
			log.debug("3--------------Validate " + bookingForm);
			validator.validate(bookingForm, errors);

			validateBeforeBook(bookingForm, errors, request.getLocale());

			//1. check duplicates
			if (bookingForm.isDuplicateContacts()){
				saveError(request, getText("errors.passenger.duplicate", request.getLocale()));
				globalErrors = true;
			}
			
			//2. check double booking
			if (bookingManager.isDoubleBooking(bookingForm)){
				saveError(request, getText("errors.passenger.doubleBook", request.getLocale()));
				globalErrors = true;
				
			}

			// cek apakah saldo cukup ? error ditampilkan di header saja
			/* disabled krn agent msh bisa booking tanpa bayar, jadi bisa bayar belakangan waktu issue ticket
			if (!agentManager.isPermitToBuy(agent)) {
				saveError(request, getText("errors.account.balancetoolow", request.getLocale()));
				globalErrors = true;
			}
			*/
			if (Utils.isForbidAdmin(request)){
				saveError(request, getText("errors.admin.forbidTransaction", request.getLocale()));
				globalErrors = true;
			}
			
			if (globalErrors || errors.hasErrors()) {
//				ra.addFlashAttribute("maBookingForm", bookingForm);
//				ra.addFlashAttribute("listDepart", models.get(""));
//				ra.addFlashAttribute("listReturn", bookingForm);
//				ra.addFlashAttribute("org.springframework.validation.BindingResult.messageForm", errors);
//				ra.addFlashAttribute("errors", errors);
//				Object obj1 = model.get("listDepart");
//				Object obj2 = model.get("listReturn");
				
				if (errors.hasErrors()){
					saveError(request, getText("errors.customer.data", request.getLocale()));
				}
				
				ModelAndView mav =  new ModelAndView(SEARCH_ODR_PAGE);
							//.addObject("maBookingForm", bookingForm);
				mav.addAllObjects(model);
				/*
				if (model.get("listDepart") != null){
					mav.addObject("listDepart", model.get("listDepart"));
					mav.addObject("seatclasses_dep", displayClasses(false, (List<FlightSearchB2B>) model.get("listDepart")));
				}
				if (model.get("listReturn") != null){
					mav.addObject("listReturn", model.get("listReturn"));
					mav.addObject("seatclasses_ret", displayClasses(false, (List<FlightSearchB2B>) model.get("listReturn")));
				}
				*/
				return mav;
//				return new ModelAndView("redirect:/flight/b2b/search");
			}
		}

		List<BookingFlightVO> listBooks;
		try {
			log.debug("4--------------trying to generateBooking for " + agent);
			
			//termasuk menghitung basefare dkk. please refer to commissiondetails.xlsx
			listBooks = bookingManager.generateBooking(agent);
			
			bookingManager.bookingFromAirline(listBooks, agent);
			
			
			//cegah jangan sampai null code bookingnya dua2nya
			//hitung lagi apakah harga total = amount dr airline
			int counter_NoCode = 0;
			for (BookingFlightVO bf : listBooks){
				if (Utils.isEmpty(bf.getCode())){
					
					counter_NoCode++;
					log.error("Booking Code is empty (booking_flight_id=" + bf.getId() + ")");
				}
				
				//re-calculate
//				BigDecimal manual = bookingManager.calculateAmountBeforeBooking(bf);
				
				//bf.getAmount akan dicatat ke calipso sebagai transakis
				/*
				if (bf.getAmount().compareTo(manual) != 0){
					StringBuffer msg = new StringBuffer("After booking different calculation (")
					.append(manual.doubleValue()).append(" != ").append(bf.getAmount()).append(") for ")
					.append(bf.getCode()).append("[").append(bf.getAirlines_iata()).append("]")
					;
					
					log.warn(msg.toString());
				}
				*/
			}
			
			// kalo lebih dr satu berarti udah fatal
			if (counter_NoCode > 1)
				throw new RuntimeException("Booking Failed !");
			// kalo book cuma 1 dan tdk ditemukan no code jg fatal
			if (listBooks.size() == 1 && counter_NoCode == 1)
				throw new RuntimeException("Booking Failed !");
			
		} catch (Exception e) {
			e.printStackTrace();
			
			String errorMsg = ErrorHandler.friendlyErrorMsg(e);
			
			saveError(request, getText("errors.general", request.getLocale()));
			saveError(request, PROCESS_TYPE.BOOKING_FLIGHT, getText("errors.detail", errorMsg, request.getLocale()), e);

			ModelAndView mav = new ModelAndView(SEARCH_ODR_PAGE);
			/*
			mav.addObject("maBookingForm", bookingForm);

			if (model.get("listDepart") != null){
				mav.addObject("listDepart", model.get("listDepart"));
				mav.addObject("seatclasses_dep", displayClasses(false, (List<FlightSearchB2B>) model.get("listDepart")));
			}
			if (model.get("listReturn") != null){
				mav.addObject("listReturn", model.get("listReturn"));
				mav.addObject("seatclasses_ret", displayClasses(false, (List<FlightSearchB2B>) model.get("listReturn")));
			}*/
			mav.addAllObjects(model);
			
			return mav;
		}


		//setup payments
		/*
		FlightPayment payments = new FlightPayment();
		for (BookingFlightVO bookingFlightVO : listBooks) {
			payments.addBasicFare(bookingFlightVO.getNta());
		}*/
		
		//test multi book
		/*
		if (listBooks.size() == 1){
			listBooks.add((BookingFlightVO)SerializationUtils.clone(listBooks.get(0)));
			listBooks.get(1).setInsurance_flag("Y");
		}*/
		// handle error before redirect
		
//		ra.addFlashAttribute("maBookingForm", bookingForm);
//		ra.addFlashAttribute("cart", agent.getCart());	utk cart bisa diperoleh via global agent.getCart
		ra.addFlashAttribute("agent", agent);
		ra.addFlashAttribute("listBooks", listBooks);
		//ra.addFlashAttribute("payments", payments);
	
		//clean up krn udah mau booking
		
		log.debug("trying to clean session attributes");
		session.setComplete();
		
		log.debug("5--------------trying to redirect to booking");
		
		return new ModelAndView("redirect:/flight/b2b/bookings");
//		return new ModelAndView("redirect:/flight/b2b/booklist/" + listBooks.get(0).getUid());
//		return new ModelAndView("redirect:/flight/b2b/search");

		// kalau udah selesai design, u should avoid doublesubmit by using redirect
		// warning: harus diatur halaman khusus yg berisi daftar kode booking yg
		// lebih dari satu, sebelum dilihat detil reportnya
		// kalo mo redirect, urlnya bukan dalam bentuk *.jsp jd tembak ke
		// linknya suatu controller
		// return new ModelAndView(BOOK_PAGE);

	}

	/**
	 * user able to select depart and return in the sametime
	 * 
	 */
	@RequestMapping(value = "/flight/b2b/getFareDtl", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody FareSummary getFareDetail(
			@RequestParam(value = "_idDep[]", required = false) String[] idDepCombination,
			@RequestParam(value = "_idRet[]", required = false) String[] idRetCombination, // enable javascript to sent null values
			@RequestParam(value = "dDate") String departDate, // hanya dipakai utk display hotel, bukan utk pencarian tiket pesawat. tiket pesawat nanti bisa dicari dari id-nya
			@RequestParam(value = "dCity") String destinationCity, 
			@RequestParam(value = "adults") int adults,
			@RequestParam(value = "childs") int children, 
			@RequestParam(value = "infants") int infants) throws Exception{
		return _getFareDetail(idDepCombination, idRetCombination, departDate, destinationCity, adults, children, infants);
	}

}
