package com.big.web.b2b_big2.booking.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jfree.util.Log;
import org.springframework.util.StringUtils;

import com.big.web.b2b_big2.booking.BOOK_STATUS;
import com.big.web.b2b_big2.finance.cart.pojo.FlightCart;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.flight.pojo.PNR;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.model.User;

@Entity
@Table(name="booking_flight")
public class BookingFlightVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4028860626048504341L;

	public static final String TABLE_NAME = "booking_flight";
	
//	public static final int INSURANCE_COMMISSION_IDR = 8000;
//	public static final int INSURANCE_FARE_IDR = 32000;
	
	private String id;
	private String code;
	private String status;	//BOOK_STATUS.java
	private String groupCode;	//gabungan code-code

	private String ccy;	//currency
	private BigDecimal insurance;
	private BigDecimal insuranceCommission;
	private String insurance_flag;	//Y or N/Null

	private BigDecimal serviceFee;	//bisa plus bisa minus, sangat tergantung dr promo. biasanya null
	private BigDecimal serviceFeeCommission;	//sub agent bisa menambah sesukanya
	private String service_flag;	//Y or N/Null

	private BigDecimal nta;	//disini harga tiket sudah termasuk pajak
	private BigDecimal ntaCommission;
	
	private Date createdDate;
	private Date lastUpdate;
	private Date issuedDate;
	
	private User user;
	private String originIata;
	private String destinationIata;
	private Date departure_date;
	private Date return_date;
	private String return_flag;
	private String agentId;		// agent dr airline bersangkutan. bukan agent yg login aplikasi berleha.
	private String radio_id;
	
	private String airlines_iata;
	private String cust_name;
	private String cust_phone1;
	private String cust_phone2;
	
	private Date timeToPay;	//waktu remaining utk pembeli melakukan pembayaran 
	private BigDecimal book_balance;	//kayanya: jika book_balance=normal_sales itu brarti belum dibayar sama sekali,
	private BigDecimal amount;	//diisi SETELAH booking via airline
	private String promo_code;
	private String journey_sell_key;
	private String updateCode;
	private String reff_no;
	private String radioid_return;
	
	/* dipindah ke booking_flight_trans
	private BigDecimal total_basefare;
	private BigDecimal total_commission;
	private BigDecimal total_tax;
	private BigDecimal total_issued;
	private BigDecimal total_psc;
	private BigDecimal total_iwjr;
	private BigDecimal total_surchargefee;
*/
	
	//dipakai utk display saja
	//private FlightItinerary itineraries;
	private List<FareInfo> itineraries;
	private List<PNR> pnr;
	private BookingFlightTransVO detailTransaction;	//transient, for report only
	
	public BookingFlightVO() {
		setId(java.util.UUID.randomUUID().toString());
	}

	public BookingFlightVO(FlightCart cart) {
		this();
		setOriginIata(cart.getOriginIata());
		setDestinationIata(cart.getDestinationIata());
		setReturn_flag(String.valueOf(cart.getTripMode()));
		setDeparture_date(cart.getDepartDate());
		setReturn_date(cart.getTripMode() == 0 ? null : cart.getReturnDate());
		
		/*
		 * terkait bu fini minta dipisahin radioid yg return
		if (cart.getRadioIds() != null){
			try {
				setRadio_id(StringUtils.arrayToCommaDelimitedString(cart.getDepartFlightItinerary().getRadioIds()));
			} catch (Exception e) {
				Log.error(e.getMessage(), e);
			}
			try {
				setRadioid_return(StringUtils.arrayToCommaDelimitedString(cart.getReturnFlightItinerary().getRadioIds()));
			} catch (Exception e) {
				Log.error(e.getMessage(), e);
			}
		}
		*/
		
		if (cart.getDepartFlightItinerary() != null){
			try {
				String[] _b1 = cart.getDepartFlightItinerary().getRadioIds();
				
				if (!Utils.isEmpty(_b1))
					setRadio_id(StringUtils.arrayToCommaDelimitedString(_b1));
			} catch (Exception e) {
				Log.error(e.getMessage(), e);
			}
		}
		if (cart.getReturnFlightItinerary() != null){
			try {
				String[] _b1 = cart.getReturnFlightItinerary().getRadioIds();
				
				if (!Utils.isEmpty(_b1))
					setRadioid_return(StringUtils.arrayToCommaDelimitedString(_b1));
			} catch (Exception e) {
				Log.error(e.getMessage(), e);
			}
		}

		setInsurance_flag(cart.isUseInsurance() ? "Y" : "N");

		setServiceFee(BigDecimal.ZERO);
		setServiceFeeCommission(BigDecimal.ZERO);
		setInsurance(BigDecimal.ZERO);
		setInsuranceCommission(BigDecimal.ZERO);
		
	}


	@Id
	@XmlTransient
	@JsonIgnore
	@Column(name="ID", length=40, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(length=10)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(length=15)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(length=20)
	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Column(length=3)
	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	
	public BigDecimal getInsurance() {
		return insurance;
	}

	public void setInsurance(BigDecimal insurance) {
		this.insurance = insurance;
	}

	
	@Column(name = "ins_commission")
	public BigDecimal getInsuranceCommission(){
		return insuranceCommission;// new BigDecimal(INSURANCE_COMMISSION_IDR);
	}

	public void setInsuranceCommission(BigDecimal insuranceCommission) {
		this.insuranceCommission = insuranceCommission;
	}


	@Column(length=1)
	public String getInsurance_flag() {
		return insurance_flag;
	}

	public void setInsurance_flag(String insurance_flag) {
		this.insurance_flag = insurance_flag;
	}

	@Transient
	public boolean isInsuranced(){
		return insurance_flag == null ? false : insurance_flag.equalsIgnoreCase("Y");
	}

	@Transient
	public boolean isService(){
		return service_flag == null ? false : service_flag.equalsIgnoreCase("Y");
	}
	
	public BigDecimal getNta() {
		return nta;
	}

	public void setNta(BigDecimal nta) {
		this.nta = nta;
	}

	@Column(name="nta_commission")
	public BigDecimal getNtaCommission() {
		return ntaCommission;
	}

	public void setNtaCommission(BigDecimal ntaCommission) {
		this.ntaCommission = ntaCommission;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	@Column(name="svc_commission")
	public BigDecimal getServiceFeeCommission() {
		return serviceFeeCommission;
	}

	public void setServiceFeeCommission(BigDecimal serviceFeeCommission) {
		this.serviceFeeCommission = serviceFeeCommission;
	}

	@Column(length=1)
	public String getService_flag() {
		return service_flag;
	}

	public void setService_flag(String service_flag) {
		this.service_flag = service_flag;
	}


	/* table descriptions of amunts
				NTSA		COMMISSION		PAX PAID
	TICKET		9,643,250	269,250			9,912,500				
	INSURANCE	32,000		8,000			40,000				
	SERVICE FEE		0		10,000			10,000				
	TOTAL		9,675,250	287,250			9,962,500
	*/
	@Transient
	public BigDecimal getAmountNTAServiceInsurance() {	//9,962,500
		BigDecimal sum = BigDecimal.ZERO;
		
		return sum.add(getAmountNTA())
				.add(serviceFee == null ? BigDecimal.ZERO : serviceFee)
				.add(getInsurancePax());
	}

	@Transient
	public BigDecimal getNtaAmount(){
		BigDecimal sum = BigDecimal.ZERO;
		
		return sum.add(getNta() == null ? BigDecimal.ZERO : getNta())
				.add(serviceFee == null ? BigDecimal.ZERO : serviceFee)
				.add(insurance == null ? BigDecimal.ZERO : insurance);
	}
	
	/**
	 * harga NTSA plus Commission
	 * @return
	 */
	@Transient
	public BigDecimal getAmountNTA(){	//9,912,500
		BigDecimal sum = BigDecimal.ZERO;
		
		return sum.add(nta == null ? BigDecimal.ZERO : nta)
				.add(ntaCommission == null ? BigDecimal.ZERO : ntaCommission)
				;
		
	}
	
	/**
	 * harga insurance utk NTSA plus harga insurance utk commission
	 * @return
	 */
	@Transient
	public BigDecimal getInsurancePax(){	//40,000
		BigDecimal sum = BigDecimal.ZERO;
		
		if (isInsuranced()){
			sum = sum.add(getInsurance() == null ? BigDecimal.ZERO : getInsurance())
					.add(getInsuranceCommission() == null ? BigDecimal.ZERO : getInsuranceCommission() )
					;
		}
		
		return sum;
	}
	
	@Transient
	public BigDecimal getServicePax(){	//40,000
		BigDecimal sum = BigDecimal.ZERO;
		
		if (isService()){
			sum = sum.add(getServiceFee() == null ? BigDecimal.ZERO : getServiceFee() )
					.add( getServiceFeeCommission() == null ? BigDecimal.ZERO : getServiceFeeCommission())
					;
		}
		
		return sum;
	}

	@ManyToOne
	@JoinColumn(name="userid")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Column(name = "issued_date")
	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}


	@Column(name="origin", length=3)
	public String getOriginIata() {
		return originIata;
	}

	public void setOriginIata(String originIata) {
		this.originIata = originIata;
	}

	@Column(name="destination", length=3)
	public String getDestinationIata() {
		return destinationIata;
	}

	public void setDestinationIata(String destinationIata) {
		this.destinationIata = destinationIata;
	}

	public Date getDeparture_date() {
		return departure_date;
	}

	public void setDeparture_date(Date departure_date) {
		this.departure_date = departure_date;
	}	
	
	public Date getReturn_date() {
		return return_date;
	}

	public void setReturn_date(Date return_date) {
		this.return_date = return_date;
	}

	@Column(length=1)
	public String getReturn_flag() {
		return return_flag;
	}

	public void setReturn_flag(String return_flag) {
		this.return_flag = return_flag;
	}

	@Column(length=40)
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	@Column(length = 700)
	public String getRadio_id() {
		return radio_id;
	}

	public void setRadio_id(String radio_id) {
		this.radio_id = radio_id;
	}

	@Column(length=30)
	public String getAirlines_iata() {
		return airlines_iata;
	}

	public void setAirlines_iata(String airlines_iata) {
		this.airlines_iata = airlines_iata;
	}
	
	@Transient public Airline[] getAirlines(){
		String[] s = org.apache.commons.lang.StringUtils.split(airlines_iata, ',');
		
		Airline[] a = new Airline[s.length];
		for (int i = 0; i < s.length; i++){
			a[i] = Airline.getAirlineByCode(s[i]);
		}
		return a;
	}
	
	/**
	 * @see #getAirlines()
	 * @return
	 */
	@Transient public Airline getFirstAirline(){
		String[] s = org.apache.commons.lang.StringUtils.split(airlines_iata, ',');
		
		Airline[] a = new Airline[s.length];
		for (int i = 0; i < s.length; i++){
			a[i] = Airline.getAirlineByCode(s[i]);
			
			return a[i];
		}
		
		return null;		
	}
	
	@Transient public boolean showBirthday(){
		Airline[] airlines = Airline.getAirlinesByCode(airlines_iata);
				
		boolean sum = false;
		for (int i = 0; i < airlines.length; i++){
			sum = sum || Airline.isAdultBirthdayRequired(airlines[i]); 
		}
		
		return sum;
	}
	
	
	/*
	@Transient
	public FlightItinerary getItineraries() {
		return itineraries;
	}

	public void setItineraries(FlightItinerary itineraries) {
		this.itineraries = itineraries;
	}*/

	@Column(length = 100)
	public String getCust_name() {
		return cust_name;
	}

	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	@Column(length = 20)
	public String getCust_phone1() {
		return cust_phone1;
	}

	public void setCust_phone1(String cust_phone1) {
		this.cust_phone1 = cust_phone1;
	}

	@Column(length = 20)
	public String getCust_phone2() {
		return cust_phone2;
	}

	public void setCust_phone2(String cust_phone2) {
		this.cust_phone2 = cust_phone2;
	}
	
	public Date getTimeToPay() {
		return timeToPay;
	}

	public void setTimeToPay(Date timeToPay) {
		this.timeToPay = timeToPay;
	}

	public BigDecimal getBook_balance() {
		return book_balance;
	}

	public void setBook_balance(BigDecimal book_balance) {
		this.book_balance = book_balance;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@Column(length = 10)
	public String getPromo_code() {
		return promo_code;
	}

	public void setPromo_code(String promo_code) {
		this.promo_code = promo_code;
	}

	@Column(length = 255)
	public String getJourney_sell_key() {
		return journey_sell_key;
	}

	public void setJourney_sell_key(String journey_sell_key) {
		this.journey_sell_key = journey_sell_key;
	}
	
//	@Column(length = 20) 15 jun 2015
	@Column(length = 40)
	public String getUpdateCode() {
		return updateCode;
	}

	public void setUpdateCode(String updateCode) {
		this.updateCode = updateCode;
	}
	
	
	@Column(length = 40)
	public String getReff_no() {
		return reff_no;
	}

	public void setReff_no(String reff_no) {
		this.reff_no = reff_no;
	}

	@Column(length = 700)
	public String getRadioid_return() {
		return radioid_return;
	}

	public void setRadioid_return(String radioid_return) {
		this.radioid_return = radioid_return;
	}

	@Transient
	public List<FareInfo> getItineraries() {
		return itineraries;
	}

	public void setItineraries(List<FareInfo> itineraries) {
		this.itineraries = itineraries;
	}
	
	@Transient
	public BookingFlightTransVO getDetailTransaction() {
		return detailTransaction;
	}

	public void setDetailTransaction(BookingFlightTransVO detailTransaction) {
		this.detailTransaction = detailTransaction;
	}

	@Transient
	public List<PNR> getPnr() {
		return pnr;
	}

	public void setPnr(List<PNR> pnr) {
		this.pnr = pnr;
	}

	@Transient
	public String getPaxName(){
		return "";
	}
	
	@Transient
	public String getBookedBy(){
		String usr = "";
		if (user != null){
			usr = user.getUsername();
		}
		return usr;
	}
	
	@Transient
	public String getRoute(){
		return originIata + " - " + destinationIata;
	}

	@Transient
	public boolean isCanceled() {

		String _status;
		
		//for connecting flight, such as XX, XX -> take XX
		//such as XX, HK	-> false by difference, not because of XX
		String[] buf = Utils.splitBookStatus(status);
		if (buf.length > 1){
			String s = buf[0].trim();
			for (int i = 1; i < buf.length; i++){
				//jika ketemu XX,HK
				if (!s.equalsIgnoreCase(buf[i].trim())){
					return false;
				}
			}
			_status = s;
			
		}else
			_status = status;

		return _status.toLowerCase().startsWith("cancel")
				|| BOOK_STATUS.getStatus(_status) == BOOK_STATUS.XX
				;
	}

	@Override
	public String toString() {
		return "BookingFlightVO [id=" + id + ", code=" + code + ", status="
				+ status + ", groupCode=" + groupCode + ", ccy=" + ccy
				+ ", insurance=" + insurance + ", insuranceCommission="
				+ insuranceCommission + ", insurance_flag=" + insurance_flag
				+ ", serviceFee=" + serviceFee + ", serviceFeeCommission="
				+ serviceFeeCommission + ", service_flag=" + service_flag
				+ ", nta=" + nta + ", ntaCommission=" + ntaCommission
				+ ", createdDate=" + createdDate + ", lastUpdate=" + lastUpdate
				+ ", issuedDate=" + issuedDate + ", user=" + user
				+ ", originIata=" + originIata + ", destinationIata="
				+ destinationIata + ", departure_date=" + departure_date
				+ ", return_date=" + return_date + ", return_flag="
				+ return_flag + ", agentId=" + agentId + ", radio_id="
				+ radio_id + ", airlines_iata=" + airlines_iata
				+ ", cust_name=" + cust_name + ", cust_phone1=" + cust_phone1
				+ ", cust_phone2=" + cust_phone2 + ", timeToPay=" + timeToPay
				+ ", book_balance=" + book_balance + ", amount=" + amount
				+ ", promo_code=" + promo_code + ", journey_sell_key="
				+ journey_sell_key + ", updateCode=" + updateCode
				+ ", reff_no=" + reff_no + ", radioid_return=" + radioid_return
				+ ", itineraries=" + itineraries + ", pnr=" + pnr
				+ ", detailTransaction=" + detailTransaction + "]";
	}

	/*
	 (BaseFare * PaxCount * Commission) - IssuedFee
	@Transient
	public BigDecimal getSum_Commission(int paxCount){
		BigDecimal a = getTotal_basefare().multiply(new BigDecimal(paxCount)).multiply(getTotal_commission());
		return a.subtract(getTotal_issued());
	}

	@Transient
	public BigDecimal getTicketNtsa(int paxCount) {
		return getAmount().subtract(getSum_Commission(paxCount));
	}

	@Transient
	public BigDecimal getTicketCommission(int paxCount) {
		return getSum_Commission(paxCount);
	}
	*/
	
	
}
