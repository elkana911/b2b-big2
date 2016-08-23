package com.big.web.b2b_big2.finance.cart.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.ListUtils;

import com.big.web.b2b_big2.flight.pojo.BasicContact;
import com.big.web.b2b_big2.flight.pojo.BookingFormB2B;
import com.big.web.b2b_big2.flight.pojo.ContactCustomer;
import com.big.web.b2b_big2.flight.pojo.ContactInfant;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.flight.pojo.PNR;
import com.big.web.b2b_big2.flight.pojo.PERSON_TYPE;
import com.big.web.b2b_big2.util.Utils;

public class FlightCart implements Serializable, ICart{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private List<PNR> pnr;
//	private List<BasicContact> customers;
	private String bookingCode;
	private long userId;
	private Date createdDate;
	private FlightItinerary departFlightItinerary;
	private FlightItinerary returnFlightItinerary;
	private String promoId;
	private int tripMode;
	private String originIata;
	private String destinationIata;
	private Date departDate;
	private Date returnDate;
	private ContactCustomer customer;

	private boolean useInsurance;
	
	public void add2Cart(BookingFormB2B bookingForm, List<FareInfo> list) throws Exception{
		setId(UUID.randomUUID().toString());
		
		setUserId(-1);
		setBookingCode(null);
		setPromoId(null);
		setTripMode(bookingForm.getSearchForm().getTripMode());
		setOriginIata(bookingForm.getSearchForm().getDepartIata());
		setDestinationIata(bookingForm.getSearchForm().getDestinationIata());
		setDepartDate(bookingForm.getSearchForm().parseDepartDate());
		if (bookingForm.getSearchForm().getTripMode() == 1){
			setReturnDate(bookingForm.getSearchForm().parseReturnDate());
		}

		List<PNR> _pnrList = new ArrayList<PNR>();
		for (BasicContact contact : bookingForm.getAdult()){
			_pnrList.add(new PNR(contact, PERSON_TYPE.ADULT));
		}
		for (BasicContact contact : bookingForm.getChild()){
			_pnrList.add(new PNR(contact, PERSON_TYPE.CHILD));
		}
		for (ContactInfant contact : bookingForm.getInfant()){
			_pnrList.add(new PNR(contact, PERSON_TYPE.INFANT));
		}
		setPnr(_pnrList);
		
		FlightItinerary _departFlightItinerary = new FlightItinerary();
		
		FlightItinerary _returnFlightItinerary = new FlightItinerary();

		List<FareInfo> _departTickets = new ArrayList<FareInfo>();
		List<FareInfo> _returnTickets = new ArrayList<FareInfo>();
		for (FareInfo fareInfo : list) {
			//question: why not use bookingForm...getTripMode ? answer: same thing 
			switch (fareInfo.getRouteMode()){
				case DEPART:
					_departTickets.add(fareInfo);
					break;
				case RETURN:
					_returnTickets.add(fareInfo);
					break;
			}

		}

		_departFlightItinerary.setFareInfos(_departTickets);
		_returnFlightItinerary.setFareInfos(_returnTickets);
		
		setDepartFlightItinerary(_departFlightItinerary);
		setReturnFlightItinerary(_returnFlightItinerary);

		setUseInsurance(bookingForm.isAgreeInsurance());
		setCustomer(bookingForm.getCustomer());
		
		setCreatedDate(new Date());
		
	}
	
	/**
	 * gabungan dari departFlightItinerary dan returnFlightItinerary
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<FareInfo> getItineraries(){
		List<FareInfo> list = ListUtils.union(departFlightItinerary.getFareInfos(), returnFlightItinerary.getFareInfos());
		
		return list;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<PNR> getPnr() {
		return pnr;
	}
	public void setPnr(List<PNR> pnr) {
		this.pnr = pnr;
	}
	
	public List<PNR> getPnr(PERSON_TYPE personType){
		List<PNR> list = new ArrayList<>();
		
		for (int i = 0; i < pnr.size(); i++){
			if (pnr.get(i).getPersonType() == personType){
				list.add(pnr.get(i));
			}
		}
		
		return list;
	}

	public String getBookingCode() {
		return bookingCode;
	}
	public void setBookingCode(String bookingCode) {
		this.bookingCode = bookingCode;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getPromoId() {
		return promoId;
	}

	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}

	public int getTripMode() {
		return tripMode;
	}

	public void setTripMode(int tripMode) {
		this.tripMode = tripMode;
	}

	public FlightItinerary getDepartFlightItinerary() {
		return departFlightItinerary;
	}
	public void setDepartFlightItinerary(FlightItinerary departFlightItinerary) {
		this.departFlightItinerary = departFlightItinerary;
	}
	public FlightItinerary getReturnFlightItinerary() {
		return returnFlightItinerary;
	}
	public void setReturnFlightItinerary(FlightItinerary returnFlightItinerary) {
		this.returnFlightItinerary = returnFlightItinerary;
	}

	
	public String getOriginIata() {
		return originIata;
	}

	public void setOriginIata(String originIata) {
		this.originIata = originIata;
	}

	public String getDestinationIata() {
		return destinationIata;
	}

	public void setDestinationIata(String destinationIata) {
		this.destinationIata = destinationIata;
	}

	public Date getDepartDate() {
		return departDate;
	}

	public void setDepartDate(Date departDate) {
		this.departDate = departDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public boolean isUseInsurance() {
		return useInsurance;
	}

	public void setUseInsurance(boolean useInsurance) {
		this.useInsurance = useInsurance;
	}

	public ContactCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(ContactCustomer customer) {
		this.customer = customer;
	}

	@Override
	public BigDecimal getAmount() {
		return Utils.amount(departFlightItinerary.getAmount(), returnFlightItinerary.getAmount());
//		departFlightItinerary.getAmount().add(returnFlightItinerary.getAmount());
	}
	
	public String[] getClasses(){
		List<String> classes = new ArrayList<String>();

		List<FareInfo> listA = getItineraries();
		for (int i = 0; i < listA.size(); i++) {
			classes.add(listA.get(i).getClassName());
		}

		return classes.toArray(new String[classes.size()]);
	}
	
	public String[] getAllRadioIds(){
		List<String> radios = new ArrayList<String>();

		List<FareInfo> listA = getItineraries();
		for (int i = 0; i < listA.size(); i++) {
			radios.add(listA.get(i).getRadioId());
		}

		return radios.toArray(new String[radios.size()]);
	}
	
	public boolean isDifferentAgency(){
		if (departFlightItinerary == null || departFlightItinerary.getFareInfos().size() < 1)
			return false;
		//sama aja kalo ga ada return ya ga bisa dibandingin
		if (returnFlightItinerary == null || returnFlightItinerary.getFareInfos().size() < 1)
			return false;
		
		//krn dlm 1 route bisa ada 3 pesawat/agency, maka ambil yg pertama dulu
		Set<String> agentIds = new LinkedHashSet<String>();
		for (FareInfo fareInfo : departFlightItinerary.getFareInfos()){
			agentIds.add(fareInfo.getAgentId());
		}
		
		String departAgentId = agentIds.toArray(new String[agentIds.size()])[0];
		
		//tinggal dicari jika agencynya tidak sama dengan depart 
		for (FareInfo fareInfo : returnFlightItinerary.getFareInfos()){
			if (!departAgentId.equalsIgnoreCase(fareInfo.getAgentId())){
				return true;
			}
		}
		
		return false;
	}
	
	public int getPaxCount(){
		return getPnr().size(); 
	}
	
	public int getPaxCountWithoutInfant(){
		int count = 0;
		for (PNR pnr : getPnr()){
			if (pnr.getPersonType() != PERSON_TYPE.INFANT)
				count = count +1;
		}
		
		return count;
	}
}
