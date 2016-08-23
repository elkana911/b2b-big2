package com.big.web.b2b_big2.webapp.controller.flight.b2b.booking;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.big.web.b2b_big2.agent.pojo.Agent;
import com.big.web.b2b_big2.booking.model.BookingFlightVO;
import com.big.web.b2b_big2.booking.service.IBookingManager;
import com.big.web.b2b_big2.customer.model.QCustomer;
import com.big.web.b2b_big2.customer.service.ICustomerManager;
import com.big.web.b2b_big2.email.service.IEmailManager;
import com.big.web.b2b_big2.finance.exception.TopUpIncompleteRegException;
import com.big.web.b2b_big2.flight.pojo.ContactCustomer;
import com.big.web.b2b_big2.flight.pojo.PERSON_TYPE;
import com.big.web.b2b_big2.flight.pojo.PNR;
import com.big.web.b2b_big2.report.dao.IReportDao;
import com.big.web.b2b_big2.report.pojo.REPORT_TYPE;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.AppInfo;
import com.big.web.b2b_big2.util.NetUtils;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.b2b_big2.webapp.controller.BaseFormController;
import com.big.web.b2b_big2.webapp.controller.agent.TopUpInfo;

@Controller
@SessionAttributes({ "listBooks" })
public class FlightBookingController extends BaseFormController {
	private static final String BOOK_LIST_PAGE = "/flight/flightBookListB2B"; // for

	@Autowired
	private IBookingManager bookingManager;

	@Autowired
	private ICustomerManager customerManager;

	@Autowired
	private IEmailManager emailManager;

	@Autowired
	private ISetupDao setupDao;

	@Autowired
	private IReportDao reportDao;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/flight/b2b/bookings", method = RequestMethod.GET)
	protected ModelAndView showForm(ModelMap model, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes ra) {

		log.debug("showForm FlightBookingController," + model);
		/*
		 * showForm FlightBookingController,{ listBooks=[BookingFlightVO
		 * [uid=81456498-1182-4aa1-82b5-4c42e73ae11e, code=null, status=null,
		 * groupCode=null, ccy=null, insurance=8000.00,
		 * insuranceCommission=2000.00, insurance_flag=Y, serviceFee=null,
		 * serviceFeeCommission=null, service_flag=null, nta=75500,
		 * ntaCommission=2265.00, createdDate=Wed Mar 18 14:50:05 WIB 2015,
		 * lastUpdate=null, issuedDate=null,
		 * user=com.big.web.model.User@22871dc3
		 * [username=elkana,enabled=true,accountExpired
		 * =false,credentialsExpired=false,accountLocked=false,Granted
		 * Authorities: ,ROLE_USER], originIata=CGK, destinationIata=UPG,
		 * departure_date=Thu Mar 19 00:00:00 WIB 2015, return_date=Wed Mar 18
		 * 14:50:05 WIB 2015, return_flag=0, agentId=null,
		 * radio_id=0~N~~N~RGFR~~1~X, airlines_iata=QG, cust_name=Agung Pambudi,
		 * cust_phone1=087894532, cust_phone2=02154544, timeToPay=null,
		 * book_balance=null, amount=null, itineraries=[FareInfo
		 * [id=68754419-3f21-47ba-8212-edab52b74f73, radioId=0~N~~N~RGFR~~1~X,
		 * ref_ClassFareId=11575625, className=N, baseFare=null, flightNo=QG
		 * 712, fuelSurcharge=100000, currency=IDR , agentDiscount=null,
		 * rate=755000, tax=75500, amount=75500, insurance=0, psc=40000,
		 * iwjr=5000, childFare=755000, infantFare=755000, childDiscount=0,
		 * promoDiscount=null, pph=null, route=Route [from=Airport
		 * [name=Soekarno Hatta Intl, city=Jakarta, iataCode=CGK, icaoCode=null,
		 * countryId=62, schedule=2015-03-19 05:55:00.0], to=Airport
		 * [name=Hasanuddin, city=Makassar-Ujung Pandang, iataCode=UPG,
		 * icaoCode=null, countryId=62, schedule=2015-03-19 09:25:00.0]],
		 * routeMode=DEPART, airline=Citilink, airlineIata=QG, agentId=null,
		 * personType=null, timeLimit=null, bookCode=null, bookStatus=null,
		 * cheapest=false]], pnr=[BasicContact [id=null, idCard=221515152,
		 * title=MR, fullName=AGUNG PAMBUDI, birthday=null, specialRequest=null,
		 * phone=null, countryCode=null]]]], agent=Agent
		 * [id=db8d0ee0-7b84-4c44-9d69-b2d9572baf4e, name=elkana travel,
		 * email=elkana911@yahoo.com, balanceIdr=0, balanceUsd=0,
		 * packageType=BASIC,
		 * logoFileName=52bc3108-d6b5-4d54-a261-1463c5912ae0.png,
		 * travelName=Elkana Travel, userName=elkana,
		 * cart=com.big.web.b2b_big2.finance.cart.pojo.Cart@16a53fa]}
		 */
		List<BookingFlightVO> listBooks = (List<BookingFlightVO>) model.get("listBooks"); // berisi
																							// detil
																							// booking
																							// yang
																							// diisi
																							// sewaktu
																							// proses
																							// booking
		if (listBooks == null)
			return new ModelAndView("redirect:/logout");

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

		//terkadang beberapa field tdk update so i refresh here
		for (int i = 0; i < listBooks.size(); i++){
			String _id = listBooks.get(i).getId();
			
			listBooks.set(i, bookingManager.getBookingFlight(_id));
		}
		listBooks = bookingManager.prettyList(listBooks);

		return new ModelAndView(BOOK_LIST_PAGE).addObject("listBooks", listBooks);

	}

	@RequestMapping(value = "/flight/b2b/booking/{bookId}", method = RequestMethod.GET)
	protected ModelAndView getBookingDetails(@PathVariable String bookId, final HttpServletRequest request,
			final HttpServletResponse response) {

		if (bookId == null) {
			// better throw for security reason
			// / throw new BookingNotFoundException("bookId is null");
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

		/*
		List<BookingFlightVO> listBooks = new ArrayList<BookingFlightVO>();
		
		BookingFlightVO b = bookingManager.getBookingFlight(bookId);
		listBooks.add(b);
		*/
		
		//cari pasangannya based on groupCode
		List<BookingFlightVO> listBooks = bookingManager.getBookingFlights(bookId);

		listBooks = bookingManager.prettyList(listBooks);

		return new ModelAndView(BOOK_LIST_PAGE).addObject("listBooks", listBooks);

	}

	/**
	 * 
	 * @param bookId
	 * @param model
	 * @param request
	 * @param response
	 * @param ra
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/flight/b2b/booking/{bookId}", method = RequestMethod.POST, params = "issuedpnr")
	protected ModelAndView issuedPnr(@ModelAttribute("bookDtl") BookingFlightVO bookDtl, @PathVariable String bookId,
			ModelMap model, final HttpServletRequest request, final HttpServletResponse response, RedirectAttributes ra) {

		// ternyata modelmap menyimpan banyak value, just use it
		// BookingFlightVO paymentForm = (BookingFlightVO)model.get("bookDtl");
		// //mengacu pada form payment, hanya menyimpan data payment saja.
		// sisanya akan null
		List<BookingFlightVO> listBooks = (List<BookingFlightVO>) model.get("listBooks"); // berisi
																							// detil
																							// booking
																							// yang
																							// diisi
																							// sewaktu
																							// proses
																							// booking

		if (listBooks == null)
			return new ModelAndView("redirect:/logout");

		// if
		// (bookingDetail.getStatus().equalsIgnoreCase(BOOK_STATUS.BOOKED.name())){
		// throw new IssuedException("Cant issued non booked for " +
		// bookingDetail.getCode());
		// }

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

		// sampai disini harusnya harga tiket sudah tidak mungkin berubah lagi
		// karena statusnya sudah book

		// bookingForm hanya berisi service_flag dan insurance, jadi jangan
		// expect other value. use model.get(listBooks) for details
		boolean useService = Utils.nvl(bookDtl.getService_flag(), "").equalsIgnoreCase("Y");
		BigDecimal serviceFee = BigDecimal.ZERO;
		if (useService) {
			serviceFee = bookDtl.getServiceFeeCommission() == null ? BigDecimal.ZERO : bookDtl
					.getServiceFeeCommission();
		}

		boolean useInsurance = bookDtl.isInsuranced();
		BigDecimal insuranceFee = BigDecimal.ZERO;
		BigDecimal insuranceCommission = BigDecimal.ZERO;
		if (useInsurance) {
			insuranceFee = bookDtl.getInsurance() == null ? BigDecimal.ZERO : bookDtl.getInsurance();
			insuranceCommission = bookDtl.getInsuranceCommission() == null ? BigDecimal.ZERO : bookDtl
					.getInsuranceCommission();
		}

		try {
			bookingManager.issuedTicket(bookDtl.getCode());

			// do something with serviceFee, insuranceFee and
			// insuranceCommission

			/*
			 * //no need to update listBooks, redirect view will refreshed from
			 * database for (int i = 0; i < listBooks.size(); i++){
			 * BookingFlightVO bf = listBooks.get(i); if
			 * (bf.getCode().equalsIgnoreCase(bookDtl.getCode())){
			 * bf.setStatus(BOOK_STATUS.OK.name()); listBooks.set(i, bf); break;
			 * } }
			 */

			/*
			 * belum perlu, lihat kebutuhan nanti boolean sendEmailSuccess =
			 * false;
			 * 
			 * try { bookingManager.sendTicket(bookDtl.getCode(),
			 * bookDtl.getUser().getEmail()); sendEmailSuccess = true;
			 * 
			 * } catch (Exception e) { sendEmailSuccess = false;
			 * e.printStackTrace();
			 * 
			 * } if (sendEmailSuccess) saveMessage(request,
			 * getText("message.issuedTicketAndEmail", new
			 * String[]{bookDtl.getCode(), bookDtl.getUser().getEmail() },
			 * request.getLocale())); else saveMessage(request,
			 * getText("message.issuedTicket", bookDtl.getCode(),
			 * request.getLocale()));
			 */
		} catch (Exception e) {
			e.printStackTrace();

			saveError(request, getText("errors.general", request.getLocale()));
			saveError(
					request,
					getText("errors.detail", NetUtils.protectImportantMessageFromUser(e.getMessage()), request.getLocale()));

			return new ModelAndView("redirect:/flight/b2b/booking/" + bookId);
		}

		return new ModelAndView("redirect:/flight/b2b/booking/" + bookId);

	}

	/**
	 * 
	 * @param bookDtl
	 *            mengacu pada form payment, hanya menyimpan bookcode dan data
	 *            payment saja. sisanya akan null. if you need other value, use
	 *            path within flightBookB2B.jsp
	 * @param bookId
	 * @param model
	 * @param request
	 * @param response
	 * @param ra
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/flight/b2b/booking/{bookId}", method = RequestMethod.POST, params = "cancelpnr")
	protected ModelAndView cancelTicket(@ModelAttribute("bookDtl") BookingFlightVO bookDtl,
			@PathVariable String bookId, ModelMap model, final HttpServletRequest request,
			final HttpServletResponse response, RedirectAttributes ra) {

		List<BookingFlightVO> listBooks = (List<BookingFlightVO>) model.get("listBooks"); // berisi
																							// detil
																							// booking
																							// yang
																							// diisi
																							// sewaktu
																							// proses
																							// booking
		if (listBooks == null)
			return new ModelAndView("redirect:/logout");

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

		// if status already cancel ?
		if (bookDtl.isCanceled()) {
			saveError(request, getText("errors.general", request.getLocale()));
			saveError(
					request,
					getText("errors.detail", "Book Code " + bookDtl.getCode() + " already canceled",
							request.getLocale()));

			return new ModelAndView(BOOK_LIST_PAGE);// .addObject("listBooks",
													// listBooks);
		}

		try {
			bookingManager.cancelTicket(bookDtl.getCode());

			/*
			 * //no need to update listBooks, redirect view will refreshed from
			 * database for (int i = 0; i < listBooks.size(); i++){
			 * BookingFlightVO bf = listBooks.get(i); if
			 * (bf.getCode().equalsIgnoreCase(bookDtl.getCode())){
			 * bf.setStatus(BOOK_STATUS.XX.name()); listBooks.set(i, bf); break;
			 * } }
			 */
			return new ModelAndView("redirect:/flight/b2b/booking/" + bookId);
			// return new ModelAndView(BOOK_LIST_PAGE).addObject("listBooks",
			// listBooks);
		} catch (Exception e) {
			e.printStackTrace();

			saveError(request, getText("errors.general", request.getLocale()));
			saveError(request, getText("errors.detail", e.getMessage(), request.getLocale()));

			return new ModelAndView("redirect:/flight/b2b/booking/" + bookId);
		}
		// return new ModelAndView("redirect:/flight/b2b/booking", model);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/flight/b2b/booking/{bookId}", method = RequestMethod.POST, params = "printpnr")
	protected ModelAndView printTicket(@ModelAttribute("bookDtl") BookingFlightVO bookDtl, @PathVariable String bookId,
			ModelMap model, final HttpServletRequest request, final HttpServletResponse response, RedirectAttributes ra) {

		List<BookingFlightVO> listBooks = (List<BookingFlightVO>) model.get("listBooks"); // berisi
																							// detil
																							// booking
																							// yang
																							// diisi
																							// sewaktu
																							// proses
																							// booking
		if (listBooks == null)
			return new ModelAndView("redirect:/logout");

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

		BookingFlightVO tmp = null;
		for (BookingFlightVO bookingFlightVO : listBooks) {
			if (bookingFlightVO.getId().equalsIgnoreCase(bookId)) {
				tmp = bookingFlightVO;
				break;
			}
		}

		try {

			JasperPrint jasperPrint = reportDao.printTicketFlight(tmp, setupDao.getValue(ISetupDao.KEY_REPORT_PATH), REPORT_TYPE.SUBAGENT);

			response.setContentType("application/x-pdf");
			response.setHeader("Content-disposition", "inline; filename=eticket_" + tmp.getCode() + ".pdf");

			final OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

			return null;

		} catch (Exception e) {
			e.printStackTrace();

			saveError(request, getText("errors.general", request.getLocale()));
			saveError(request, getText("errors.detail", e.getMessage(), request.getLocale()));

			return new ModelAndView("redirect:/flight/b2b/booking/" + bookId);
		}

	}

	/**
	 * Mengirimkan email ticket ke customer. Report yang digunakan berbeda dengan agen punya. 
	 */ 
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/flight/b2b/booking/email", method = RequestMethod.GET, headers = "Accept=*/*")
	protected @ResponseBody String emailTicket(@RequestParam(value = "idx") String listIndex,
			@RequestParam(value = "to") String emailTo, @RequestParam(value = "msg") String msg, ModelMap model,
			final HttpServletRequest request, final HttpServletResponse response, RedirectAttributes ra)
			throws Exception {

		List<BookingFlightVO> listBooks = (List<BookingFlightVO>) model.get("listBooks"); // berisi
																							// detil
																							// booking
																							// yang
																							// diisi
																							// sewaktu
																							// proses
																							// booking

		BookingFlightVO tmp = listBooks.get(Integer.parseInt(listIndex));

		try {

			log.debug("try to email to " + emailTo);

			// 1. generate pdf from jasper report
			JasperPrint jasperPrint = reportDao.printTicketFlight(tmp, setupDao.getValue(ISetupDao.KEY_REPORT_PATH), REPORT_TYPE.CUSTOMER);

			// 2. save pdf to cache
			File pdfFile = new File(setupDao.getValue(ISetupDao.KEY_REPORT_PATH) + "output/" + tmp.getCode() + ".pdf");// File.createTempFile("output.",
																														// ".pdf");
			FileOutputStream _fos = new FileOutputStream(pdfFile);
			JasperExportManager.exportReportToPdfStream(jasperPrint, _fos);
			_fos.close();
			_fos = null;

			// kalo email error ya jangan dikirim lagi, notify ke support utk di
			// delete atau gimana
			// 3. tell emailManager to send later
			// issued atau booking tergantung dari status ticket !
			// emailManager.sendFile(true, "Booking Ticket " + tmp.getCode(),
			// emailTo, msg, pdfFile);
			List<File> pdfFiles = new ArrayList<File>();

			pdfFiles.add(pdfFile);

			emailManager.sendFiles(true, "Booking Ticket " + tmp.getCode(), emailTo, msg, pdfFiles);

			return null;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	@RequestMapping(value = "/getFormAdults", method = RequestMethod.GET, headers = "Accept=*/*", produces={"application/json", "application/xml"})
	public @ResponseBody List<PNR> getFormAdults(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			setAgentInfo(request, response);

			Agent agent = (Agent) request.getAttribute("agent");
			
			List<PNR> list = bookingManager.getPnrList(PERSON_TYPE.ADULT, agent.getUserId());
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@RequestMapping(value = "/getFormChildren", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody List<PNR> getFormChildren(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			setAgentInfo(request, response);
			
			Agent agent = (Agent) request.getAttribute("agent");

			return bookingManager.getPnrList(PERSON_TYPE.CHILD, agent.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@RequestMapping(value = "/getFormInfants", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody List<PNR> getFormInfants(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			setAgentInfo(request, response);
			
			Agent agent = (Agent) request.getAttribute("agent");

			return bookingManager.getPnrList(PERSON_TYPE.INFANT, agent.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@RequestMapping(value = "/getQFormAdults", method = RequestMethod.GET, headers = "Accept=*/*", produces={"application/json", "application/xml"})
	public @ResponseBody List<QCustomer> getQFormAdults(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			setAgentInfo(request, response);
			
			Agent agent = (Agent) request.getAttribute("agent");
			List<QCustomer> list = customerManager.getQCustomerAdults(agent.getId());
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@RequestMapping(value = "/getQFormChildren", method = RequestMethod.GET, headers = "Accept=*/*", produces={"application/json", "application/xml"})
	public @ResponseBody List<QCustomer> getQFormChildren(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			setAgentInfo(request, response);
			
			Agent agent = (Agent) request.getAttribute("agent");
			List<QCustomer> list = customerManager.getQCustomerChildren(agent.getId());
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@RequestMapping(value = "/getQFormInfants", method = RequestMethod.GET, headers = "Accept=*/*", produces={"application/json", "application/xml"})
	public @ResponseBody List<QCustomer> getQFormInfants(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			setAgentInfo(request, response);
			
			Agent agent = (Agent) request.getAttribute("agent");
			List<QCustomer> list = customerManager.getQCustomerInfants(agent.getId());
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@RequestMapping(value = "/getQFormContacts", method = RequestMethod.GET, headers = "Accept=*/*", produces={"application/json", "application/xml"})
	public @ResponseBody List<QCustomer> getQFormContacts(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			setAgentInfo(request, response);
			
			Agent agent = (Agent) request.getAttribute("agent");
			List<QCustomer> list = customerManager.getQCustomerContacts(agent.getId());
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * alasan penamaan CCust spy tidak mudah dikenal orang berhubung di panggil via javascript
	 */ 
	@RequestMapping(value = "/getFormCCust", method = RequestMethod.GET, headers = "Accept=*/*")
	public @ResponseBody List<ContactCustomer> getFormContactCustomer(final HttpServletRequest request, final HttpServletResponse response) {
		try {
			setAgentInfo(request, response);
			
			Agent agent = (Agent) request.getAttribute("agent");
			
			return bookingManager.getContactCustomer(agent.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

    @RequestMapping(value = "/flight/b2b/bookingInfo", method = RequestMethod.GET)
	public @ResponseBody List<AppInfo> getBookingInfo(){
    	return bookingManager.getAppInfo();
    }

}
