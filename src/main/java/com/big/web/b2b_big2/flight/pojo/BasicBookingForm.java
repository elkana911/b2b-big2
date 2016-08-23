package com.big.web.b2b_big2.flight.pojo;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.model.User;



public class BasicBookingForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4371608641416356914L;
	private SearchForm searchForm;
	private List<BasicContact> adult;
	private List<BasicContact> child;
	private List<ContactInfant> infant;
	private ContactCustomer customer;
	private boolean agreeInsurance;
	
	/*
	dont activate this. huge memory and unable to spring bind because its a list. displaytag unable to bind them
	private List<FlightSearchB2B> listDepart;
	private List<FlightSearchB2B> listReturn;
	*/
	private String depId;	//isinya flightAvailId TERFORMAT, contoh 9ba5efd2-8152-4905-9bb9-9a695ff25fe1SJL0000000. to retrieve only flightavailid,
	private String depTrans2Id;	//isinya flightavailid TERFORMAT utk transit kedua
	private String depTrans3Id;	//isinya flightavailid TERFORMAT utk transit ketiga
	private String retId;
	private String retTrans2Id;	//isinya flightavailid TERFORMAT utk transit kedua
	private String retTrans3Id;	//isinya flightavailid TERFORMAT utk transit ketiga
	
	private String code;
	private String status;
	private Date transactionTime;
	
	public BasicBookingForm() {
		searchForm = new SearchForm();
		customer = new ContactCustomer();
//		agreeInsurance = true;
		
		adult = new ArrayList<BasicContact>();
		child = new ArrayList<BasicContact>();
		infant = new ArrayList<ContactInfant>();
		/*
		listDepart = new ArrayList<FlightSearchB2B>();
		listReturn = new ArrayList<FlightSearchB2B>();
		
		searchForm.setSearchPast(true);
		searchForm.setTripMode(0);
		searchForm.setDepartDate("18/02/2015");
		searchForm.setReturnDate("19/02/2015");
		searchForm.setDepartCity("Jakarta (CGK)");
		searchForm.setDestCity("UPG");
		searchForm.setAdult(2);
		searchForm.setInfants(2);
		 */
		searchForm.setAirasia(false);
		searchForm.setGaruda(false);
		searchForm.setCitilink(false);
		searchForm.setLion(false);
		searchForm.setWings(false);
		searchForm.setBatik(false);
		searchForm.setSriwijaya(false);
		searchForm.setNam(false);
		searchForm.setKalstar(false);
		searchForm.setAviastar(false);
		searchForm.setExpress(false);
		searchForm.setTrigana(false);
		searchForm.setSusi(false);
		searchForm.setMalindo(false);
		/*
		searchForm.setAirasia(true);
		searchForm.setGaruda(true);
		searchForm.setCitilink(true);
		searchForm.setLion(true);
		searchForm.setWings(true);
		searchForm.setBatik(true);
		searchForm.setSriwijaya(true);
		searchForm.setNam(true);
		searchForm.setKalstar(true);
		searchForm.setAviastar(true);
		searchForm.setExpress(true);
		searchForm.setTrigana(true);
		searchForm.setSusi(true);
		searchForm.setMalindo(true);
	*/
	}

	public void addAdult() {
		adult.add(new BasicContact());
	}
	public void addChild() {
		child.add(new BasicContact());
	}
	public void addInfant() {
		infant.add(new ContactInfant());
	}
	
	public SearchForm getSearchForm() {
		return searchForm;
	}
	public void setSearchForm(SearchForm searchForm) {
		this.searchForm = searchForm;
	}
	public List<BasicContact> getAdult() {
		return adult;
	}
	public void setAdult(List<BasicContact> adult) {
		this.adult = adult;
	}
	public List<BasicContact> getChild() {
		return child;
	}
	public void setChild(List<BasicContact> child) {
		this.child = child;
	}
	public List<ContactInfant> getInfant() {
		return infant;
	}
	public void setInfant(List<ContactInfant> infant) {
		this.infant = infant;
	}
	public ContactCustomer getCustomer() {
		return customer;
	}
	public void setCustomer(ContactCustomer customer) {
		this.customer = customer;
	}
	public boolean isAgreeInsurance() {
		return agreeInsurance;
	}
	public void setAgreeInsurance(boolean agreeInsurance) {
		this.agreeInsurance = agreeInsurance;
	}

	public String getDepId() {
		return depId;
	}

	public void setDepId(String depId) {
		this.depId = depId;
	}

	public String getRetId() {
		return retId;
	}

	public void setRetId(String retId) {
		this.retId = retId;
	}
	
	
	public String getDepTrans2Id() {
		return depTrans2Id;
	}

	public void setDepTrans2Id(String depTrans2Id) {
		this.depTrans2Id = depTrans2Id;
	}

	public String getDepTrans3Id() {
		return depTrans3Id;
	}

	public void setDepTrans3Id(String depTrans3Id) {
		this.depTrans3Id = depTrans3Id;
	}

	public String getRetTrans2Id() {
		return retTrans2Id;
	}

	public void setRetTrans2Id(String retTrans2Id) {
		this.retTrans2Id = retTrans2Id;
	}

	public String getRetTrans3Id() {
		return retTrans3Id;
	}

	public void setRetTrans3Id(String retTrans3Id) {
		this.retTrans3Id = retTrans3Id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	public boolean isIDCardRequired(){
		List<Airline> list = searchForm.getAirlines();
		
		if (list.size() < 1) return false;
		
		boolean b = false;
		for (Airline airline : list){
			b = b || Airline.isIDCardRequired(airline, searchForm.getAdult() > 0, searchForm.getChildren() > 0, searchForm.getInfants() > 0);
		}
		
		return b;
	}
	
	public boolean isBirthdayRequired(){
		boolean b_depId = Utils.isEmpty(depId) ? false : Airline.isAdultBirthdayRequired(FlightSelect.parse(depId).getAirline()); 
		boolean b_depTrans2Id = Utils.isEmpty(depTrans2Id) ? false : Airline.isAdultBirthdayRequired(FlightSelect.parse(depTrans2Id).getAirline()); 
		boolean b_depTrans3Id = Utils.isEmpty(depTrans3Id) ? false : Airline.isAdultBirthdayRequired(FlightSelect.parse(depTrans3Id).getAirline()); 
		boolean b_retId = Utils.isEmpty(retId) ? false : Airline.isAdultBirthdayRequired(FlightSelect.parse(retId).getAirline()); 
		boolean b_retTrans2Id = Utils.isEmpty(retTrans2Id) ? false : Airline.isAdultBirthdayRequired(FlightSelect.parse(retTrans2Id).getAirline()); 
		boolean b_retTrans3Id = Utils.isEmpty(retTrans3Id) ? false : Airline.isAdultBirthdayRequired(FlightSelect.parse(retTrans3Id).getAirline()); 
		/*
		depId;	//isinya flightAvailId
		depTrans2Id;	//isinya flightavailid utk transit kedua
		depTrans3Id;	//isinya flightavailid utk transit ketiga
		retId;
		retTrans2Id;	//isinya flightavailid utk transit kedua
		retTrans3Id;	//isinya flightavailid utk transit ketiga

		return (airline == Airline.CITILINK || airline == Airline.GARUDA || airline == Airline.AIRASIA)
				;
			*/
		return b_depId || b_depTrans2Id || b_depTrans3Id || b_retId || b_retTrans2Id || b_retTrans3Id;
	}
	
	public void prepareContacts(User userAgent) {
		if (searchForm.getAdult() != adult.size()) {
			adult.clear();
			for (int i = 0; i < searchForm.getAdult(); i++) {
				addAdult();
			}
		}
		
		if (searchForm.getChildren() != child.size()) {
			child.clear();
			for (int i = 0; i < searchForm.getChildren(); i++) {
				addChild();
			}
			
		}
		
		if (searchForm.getInfants() != infant.size()) {
			infant.clear();
			for (int i = 0; i < searchForm.getInfants(); i++) {
				addInfant();
			}
			
		}
		
		customer.setAgentName(userAgent.getFirstName());
		customer.setAgentEmail(userAgent.getEmail());
		customer.setAgentPhone1(userAgent.getPhoneNumber());
		
	}

	
	public String getDepartTitle(){
		try {
			String departIata = searchForm.getDepartIata();
			String destinationIata = searchForm.getDestinationIata();

			return (StringUtils.isEmpty(departIata) ? "All" : departIata) + " to "
							+ (StringUtils.isEmpty(destinationIata) ? "All" : destinationIata) + " on "
							+ Utils.prettyDate(searchForm.parseDepartDate());

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return "-";
	}

	public String getReturnTitle(){
		try {
			String departIata = searchForm.getDepartIata();
			String destinationIata = searchForm.getDestinationIata();
			return (StringUtils.isEmpty(destinationIata) ? "All" : destinationIata) + " to "
					+ (StringUtils.isEmpty(departIata) ? "All" : departIata) + " on "
					+ Utils.prettyDate(searchForm.parseReturnDate());

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "-";
	}
	
	public int getPax(){
		return (adult == null ? 0 : adult.size())
			+ (child == null ? 0 : child.size())
			+ (infant == null ? 0 : infant.size())
		;
	}
	
	public boolean isDuplicateContacts(){

		Set<String> listFullName = new LinkedHashSet<String>();
		Set<String> listIDCard = new LinkedHashSet<String>();
		
		boolean failToAddIDCard = false;
		if (!Utils.isEmpty(adult)){
			for (int i = 0; i < adult.size(); i++) {
				listFullName.add(adult.get(i).getFullName().trim());

				if (!Utils.isEmpty(adult.get(i).getIdCard()))
					failToAddIDCard = !listIDCard.add(adult.get(i).getIdCard().trim());
					
			}
		}
		
		if (!Utils.isEmpty(child)){
			for (int i = 0; i < child.size(); i++) {
				listFullName.add(child.get(i).getFullName().trim());
				
				if (!Utils.isEmpty(child.get(i).getIdCard()))
					failToAddIDCard = failToAddIDCard || !listIDCard.add(child.get(i).getIdCard().trim());
			}
		}
		
		if (!Utils.isEmpty(infant)){
			for (int i = 0; i < infant.size(); i++) {
				listFullName.add(infant.get(i).getFullName().trim());
				
				if (!Utils.isEmpty(infant.get(i).getIdCard()))
						failToAddIDCard = failToAddIDCard || !listIDCard.add(infant.get(i).getIdCard().trim());
			}
		}
		
		if (listFullName.size() != getPax()){
			return true;
		}
		
		if (listFullName.size() < 2) return false;
		listFullName.clear();	//jobs done
		
		//check duplicate idcard
		return failToAddIDCard;
	}
	
	public boolean isEmptyPnr(){
		return adult.size() < 1 && child.size() < 1 && infant.size() < 1;
	}

	public static void main(String[] args) {
		BasicBookingForm form = new BasicBookingForm();
		List<BasicContact> adult = new ArrayList<BasicContact>();
		BasicContact adult1 = new BasicContact();
		adult1.setIdCard("123ABCs");
		adult1.setFullName("eric elkana1");
		adult.add(adult1);
		
		List<BasicContact> child = new ArrayList<BasicContact>();
		BasicContact child1 = new BasicContact();
		child1.setFullName("eric elkana ");
		child.add(child1);
		
		List<ContactInfant> infant = new ArrayList<ContactInfant>();
		ContactInfant infant1 = new ContactInfant();
		infant1.setIdCard("123ABCs");
		infant1.setFullName("eric elkanacc");
		infant.add(infant1);

		
		form.setAdult(adult);
		form.setChild(child);
		form.setInfant(infant);
		
		System.err.println(form.isDuplicateContacts() ? "duplikat !" : "tidak ada yg duplikat");
	}
	
	@Override
	public String toString() {
		return "BasicBookingForm [searchForm=" + searchForm + ", adult=" + adult + ", child=" + child + ", infant="
				+ infant + ", customer=" + customer + ", agreeInsurance=" + agreeInsurance + ", depId=" + depId
				+ ", depTrans2Id=" + depTrans2Id + ", depTrans3Id=" + depTrans3Id + ", retId=" + retId
				+ ", retTrans2Id=" + retTrans2Id + ", retTrans3Id=" + retTrans3Id + ", code=" + code + ", status="
				+ status + ", transactionTime=" + transactionTime + "]";
	}

	
}
