package com.big.web.b2b_big2.report.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.big.web.b2b_big2.booking.model.BookingFlightVO;

/**
 * Disesuaikan dengan di jasper
 * @author Administrator
 *
 */
public class BookingTicket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -349479652212369566L;
	
	private String origin;
	private String origin_name;
	private String destination;
	private String destination_name;
	private String city_name;
	private String id;
	private Long userid;
	private String code;
	private String status;
	private BigDecimal insurance;
	private BigDecimal nta;
	private BigDecimal servicefee;
	private BigDecimal amount;
	private String passenger_first_name;
	private String passenger_phone;
	private String passenger_title;
	private String passenger_middle_name;
	private String passenger_last_name;
	private String spec_request;
	private String passenger_ticket_no;
	private String departure;
	private String arrival;
	private String dep_time;
	private String arrival_time;
	private String flightnum;
	private String class_name;
	private String iata;
	private String airport_name;
	private String city;
	private String agent_name;
	private Long user_id;
	private String address1;
	private String email1;
	private String phone1;
	
	public BookingTicket(BookingFlightVO bookingFlight) {
		/*
		BookingFlightVO [id=46a39bd6-c2c3-49e1-b6aa-dac74d917bf8, 
				code=XYZ120, 
				status=UNMAPPED [BOOKED], 
				groupCode=null, 
				ccy=null, 
				insurance=8000, 
				insuranceCommission=2000, 
				insurance_flag=Y, 
				serviceFee=null, 
				serviceFeeCommission=null, 
				service_flag=null, 
				nta=745000, 
				ntaCommission=22350, 
				createdDate=2015-01-21 17:19:27.0, 
				lastUpdate=null, 
				issuedDate=null, 
				user=com.big.web.model.User@7c0a5d35[username=elkana,enabled=true,accountExpired=false,credentialsExpired=false,accountLocked=false,Granted Authorities: ,ROLE_USER], originIata=CGK, destinationIata=LOP, departure_date=2015-01-31 00:00:00.0, return_date=2015-01-21 17:19:23.0, return_flag=0, agentId=null, radio_id=null, airlines_iata=QG, cust_name=eric elkana, cust_phone1=087886283377, cust_phone2=021234567, timeToPay=null, book_balance=null, amount=null, itineraries=[FareInfo [id=null, radioId=null, ref_ClassFareId=null, className=N, baseFare=null, flightNo=QG  803, fuelSurcharge=null, currency=null, agentDiscount=null, rate=null, tax_adult=null, tax_child=null, tax_infant=null, amount=null, insurance=null, psc=null, iwjr=null, childFare=null, infantFare=null, childDiscount=null, promoDiscount=null, pph=null, route=Route [from=Airport [name=Soekarno Hatta Intl, city=Jakarta, iataCode=CGK, icaoCode=null, countryId=0, schedule=2015-01-31 11:35:00.0], to=Airport [name=Juanda, city=Surabaya, iataCode=SUB, icaoCode=null, countryId=0, schedule=2015-01-31 12:55:00.0]], routeMode=null, airline=null, airlineIata=null, agentId=null, personType=null, timeLimit=null, bookCode=null, bookStatus=null, cheapest=false], FareInfo [id=null, radioId=null, ref_ClassFareId=null, className=N, baseFare=null, flightNo=QG  660, fuelSurcharge=null, currency=null, agentDiscount=null, rate=null, tax_adult=null, tax_child=null, tax_infant=null, amount=null, insurance=null, psc=null, iwjr=null, childFare=null, infantFare=null, childDiscount=null, promoDiscount=null, pph=null, route=Route [from=Airport [name=Juanda, city=Surabaya, iataCode=SUB, icaoCode=null, countryId=0, schedule=2015-01-31 15:25:00.0], to=Airport [name=Lombok International Airport, city=Praya, iataCode=LOP, icaoCode=null, countryId=0, schedule=2015-01-31 17:30:00.0]], routeMode=null, airline=null, airlineIata=null, agentId=null, personType=null, timeLimit=null, bookCode=null, bookStatus=null, cheapest=false]], pnr=[BasicContact [id=341ef34f-1921-4116-ad18-02b07907c05e, idCard=null, title=MRS, fullName=LIA, birthday=null, specialRequest=null, phone=null, countryCode=null]]]
				*/
		setOrigin(bookingFlight.getOriginIata());
		setOrigin_name(bookingFlight.getOriginIata());
		
		setStatus(bookingFlight.getStatus());
		setCity_name("Eric Elkana");
	}
	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getOrigin_name() {
		return origin_name;
	}
	public void setOrigin_name(String origin_name) {
		this.origin_name = origin_name;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDestination_name() {
		return destination_name;
	}

	public void setDestination_name(String destination_name) {
		this.destination_name = destination_name;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
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

	public BigDecimal getInsurance() {
		return insurance;
	}

	public void setInsurance(BigDecimal insurance) {
		this.insurance = insurance;
	}

	public BigDecimal getNta() {
		return nta;
	}

	public void setNta(BigDecimal nta) {
		this.nta = nta;
	}

	public BigDecimal getServicefee() {
		return servicefee;
	}

	public void setServicefee(BigDecimal servicefee) {
		this.servicefee = servicefee;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPassenger_first_name() {
		return passenger_first_name;
	}

	public void setPassenger_first_name(String passenger_first_name) {
		this.passenger_first_name = passenger_first_name;
	}

	public String getPassenger_phone() {
		return passenger_phone;
	}

	public void setPassenger_phone(String passenger_phone) {
		this.passenger_phone = passenger_phone;
	}

	public String getPassenger_title() {
		return passenger_title;
	}

	public void setPassenger_title(String passenger_title) {
		this.passenger_title = passenger_title;
	}

	public String getPassenger_middle_name() {
		return passenger_middle_name;
	}

	public void setPassenger_middle_name(String passenger_middle_name) {
		this.passenger_middle_name = passenger_middle_name;
	}

	public String getPassenger_last_name() {
		return passenger_last_name;
	}

	public void setPassenger_last_name(String passenger_last_name) {
		this.passenger_last_name = passenger_last_name;
	}

	public String getSpec_request() {
		return spec_request;
	}

	public void setSpec_request(String spec_request) {
		this.spec_request = spec_request;
	}

	public String getPassenger_ticket_no() {
		return passenger_ticket_no;
	}

	public void setPassenger_ticket_no(String passenger_ticket_no) {
		this.passenger_ticket_no = passenger_ticket_no;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getDep_time() {
		return dep_time;
	}

	public void setDep_time(String dep_time) {
		this.dep_time = dep_time;
	}

	public String getArrival_time() {
		return arrival_time;
	}

	public void setArrival_time(String arrival_time) {
		this.arrival_time = arrival_time;
	}

	public String getFlightnum() {
		return flightnum;
	}

	public void setFlightnum(String flightnum) {
		this.flightnum = flightnum;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getIata() {
		return iata;
	}

	public void setIata(String iata) {
		this.iata = iata;
	}

	public String getAirport_name() {
		return airport_name;
	}

	public void setAirport_name(String airport_name) {
		this.airport_name = airport_name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	@Override
	public String toString() {
		return "BookingTicket [origin=" + origin + ", origin_name=" + origin_name + ", destination=" + destination
				+ ", destination_name=" + destination_name + ", city_name=" + city_name + ", id=" + id + ", userid="
				+ userid + ", code=" + code + ", status=" + status + ", insurance=" + insurance + ", nta=" + nta
				+ ", servicefee=" + servicefee + ", amount=" + amount + ", passenger_first_name="
				+ passenger_first_name + ", passenger_phone=" + passenger_phone + ", passenger_title="
				+ passenger_title + ", passenger_middle_name=" + passenger_middle_name + ", passenger_last_name="
				+ passenger_last_name + ", spec_request=" + spec_request + ", passenger_ticket_no="
				+ passenger_ticket_no + ", departure=" + departure + ", arrival=" + arrival + ", dep_time=" + dep_time
				+ ", arrival_time=" + arrival_time + ", flightnum=" + flightnum + ", class_name=" + class_name
				+ ", iata=" + iata + ", airport_name=" + airport_name + ", city=" + city + ", agent_name=" + agent_name
				+ ", user_id=" + user_id + ", address1=" + address1 + ", email1=" + email1 + ", phone1=" + phone1 + "]";
	}
	
	

}
