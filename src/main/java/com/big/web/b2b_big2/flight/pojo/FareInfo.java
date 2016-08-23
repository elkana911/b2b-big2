package com.big.web.b2b_big2.flight.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.big.web.b2b_big2.flight.api.kalstar.action.fare.FareDetail;
import com.big.web.b2b_big2.flight.model.AbstractFlightClassFare;

/**
 * Generic bean as used by cart system to store information about a selected fare.
 * Dont confuse with {@link FareDetail} and {@link AbstractFlightClassFare}
 * 
 * @author Administrator
 *
 */
public class FareInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String radioId;
	private String isTransit;
	private Long ref_ClassFareId;
	private String className;
	private String baseFare;	//biasanya utk Kalstar
	
	private String flightNo;
//	private String timeTravel;
	private BigDecimal fuelSurcharge;
	private String currency;
	private BigDecimal agentDiscount;
	private BigDecimal rate;
	private BigDecimal tax_adult;
	private BigDecimal tax_child;
	private BigDecimal tax_infant;
	private BigDecimal amount;
	private BigDecimal insurance;
	private BigDecimal psc;	//passenger service charge
	private BigDecimal iwjr;
	private BigDecimal childFare;
	private BigDecimal infantFare;
	private BigDecimal childDiscount;
	private BigDecimal promoDiscount;
	private BigDecimal pph;
	private BigDecimal issuedFee;
	
	private Route route;
	private ROUTE_MODE routeMode;
	private String airline;
	private String airlineIata;
	private String agentId;
	private String updateCode;
	
	private PERSON_TYPE personType;
	
	/**
	 * Batas waktu yang diberikan oleh airline utk mempertahankan harga
	 */
	private Date timeLimit;
	private String bookCode;
	private String bookStatus;
	private boolean cheapest;
	
	//dipakai utk pembelian via scraping. ask bu fini
	private String journeyKey;
	
	//diperlukan utk mengetahui apakah harga summary termasuk adult/child/infant
	private int adultPax;
	private int childrenPax;
	private int infantPax;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRadioId() {
		return radioId;
	}

	public void setRadioId(String radioId) {
		this.radioId = radioId;
	}

	public Long getRef_ClassFareId() {
		return ref_ClassFareId;
	}

	public void setRef_ClassFareId(Long ref_ClassFareId) {
		this.ref_ClassFareId = ref_ClassFareId;
	}

	public String getIsTransit() {
		return isTransit;
	}

	public void setIsTransit(String isTransit) {
		this.isTransit = isTransit;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(String baseFare) {
		this.baseFare = baseFare;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public BigDecimal getFuelSurcharge() {
		return fuelSurcharge;
	}

	public void setFuelSurcharge(BigDecimal fuelSurcharge) {
		this.fuelSurcharge = fuelSurcharge;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAgentDiscount() {
		return agentDiscount;
	}

	public void setAgentDiscount(BigDecimal agentDiscount) {
		this.agentDiscount = agentDiscount;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getTax_adult() {
		return tax_adult;
	}

	public void setTax_adult(BigDecimal tax_adult) {
		this.tax_adult = tax_adult;
	}

	public BigDecimal getTax_child() {
		return tax_child;
	}

	public void setTax_child(BigDecimal tax_child) {
		this.tax_child = tax_child;
	}

	public BigDecimal getTax_infant() {
		return tax_infant;
	}

	public void setTax_infant(BigDecimal tax_infant) {
		this.tax_infant = tax_infant;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		if (amount == null){
			amount = BigDecimal.ZERO;
			
			//kalau tax = 0 brarti rate sudah termasuk pajak, kalau null belum termasuk pajak
			BigDecimal _tax = BigDecimal.ZERO;
			if (tax_adult != null){
				_tax = _tax.add(tax_adult);
			}
			amount = amount.add(_tax)
					.add(rate == null ? BigDecimal.ZERO : rate)
					.add(psc == null ? BigDecimal.ZERO : psc)
					.add(iwjr == null ? BigDecimal.ZERO : iwjr)
					.add(fuelSurcharge == null ? BigDecimal.ZERO : fuelSurcharge)
					;
			
		}

		this.amount = amount;
	}

	public BigDecimal getInsurance() {
		return insurance;
	}

	public void setInsurance(BigDecimal insurance) {
		this.insurance = insurance;
	}

	public BigDecimal getPsc() {
		return psc;
	}

	public void setPsc(BigDecimal psc) {
		this.psc = psc;
	}

	public BigDecimal getIwjr() {
		return iwjr;
	}

	public void setIwjr(BigDecimal iwjr) {
		this.iwjr = iwjr;
	}

	public BigDecimal getChildFare() {
		return childFare;
	}

	public void setChildFare(BigDecimal childFare) {
		this.childFare = childFare;
	}

	public BigDecimal getInfantFare() {
		return infantFare;
	}

	public void setInfantFare(BigDecimal infantFare) {
		this.infantFare = infantFare;
	}

	public BigDecimal getChildDiscount() {
		return childDiscount;
	}

	public void setChildDiscount(BigDecimal childDiscount) {
		this.childDiscount = childDiscount;
	}

	public BigDecimal getPromoDiscount() {
		return promoDiscount;
	}

	public void setPromoDiscount(BigDecimal promoDiscount) {
		this.promoDiscount = promoDiscount;
	}

	public BigDecimal getPph() {
		return pph;
	}

	public void setPph(BigDecimal pph) {
		this.pph = pph;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public ROUTE_MODE getRouteMode() {
		return routeMode;
	}

	public void setRouteMode(ROUTE_MODE routeMode) {
		this.routeMode = routeMode;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getAirlineIata() {
		return airlineIata;
	}

	public void setAirlineIata(String airlineIata) {
		this.airlineIata = airlineIata;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public PERSON_TYPE getPersonType() {
		return personType;
	}

	public void setPersonType(PERSON_TYPE personType) {
		this.personType = personType;
	}

	public Date getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Date timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getBookCode() {
		return bookCode;
	}

	public void setBookCode(String bookCode) {
		this.bookCode = bookCode;
	}

	public String getBookStatus() {
		return bookStatus;
	}

	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}

	public boolean isCheapest() {
		return cheapest;
	}

	public void setCheapest(boolean cheapest) {
		this.cheapest = cheapest;
	}

	public String getJourneyKey() {
		return journeyKey;
	}

	public void setJourneyKey(String journeyKey) {
		this.journeyKey = journeyKey;
	}

	public String getUpdateCode() {
		return updateCode;
	}

	public void setUpdateCode(String updateCode) {
		this.updateCode = updateCode;
	}

	// TODO this method is dangerous because not a property. please put outside
	public String getTimeTravel() {

		return new StringBuffer(new SimpleDateFormat("HHmm").format(route.getFrom().getSchedule()))
					.append("-")
					.append(new SimpleDateFormat("HHmm").format(route.getTo().getSchedule()))
					.toString()
				;
	}
	
	public int getAdultPax() {
		return adultPax;
	}

	public void setAdultPax(int adultPax) {
		this.adultPax = adultPax;
	}

	public int getChildrenPax() {
		return childrenPax;
	}

	public void setChildrenPax(int childrenPax) {
		this.childrenPax = childrenPax;
	}

	public int getInfantPax() {
		return infantPax;
	}

	public void setInfantPax(int infantPax) {
		this.infantPax = infantPax;
	}
	
	public BigDecimal getIssuedFee() {
		return issuedFee;
	}

	public void setIssuedFee(BigDecimal issuedFee) {
		this.issuedFee = issuedFee;
	}

	// TODO this is dangerous, please put outside
	public void resetAllFare(){
			
		setFuelSurcharge(BigDecimal.ZERO);
		setAgentDiscount(BigDecimal.ZERO);
		setRate(BigDecimal.ZERO);
		setTax_adult(BigDecimal.ZERO);
		setTax_child(BigDecimal.ZERO);
		setTax_infant(BigDecimal.ZERO);
		setAmount(BigDecimal.ZERO);
		setInsurance(BigDecimal.ZERO);
		setPsc(BigDecimal.ZERO);
		setIwjr(BigDecimal.ZERO);
		setChildFare(BigDecimal.ZERO);
		setInfantFare(BigDecimal.ZERO);
		setChildDiscount(BigDecimal.ZERO);
		setPromoDiscount(BigDecimal.ZERO);
		setPph(BigDecimal.ZERO);
		
	}

	@Override
	public String toString() {
		return "FareInfo [id=" + id + ", radioId=" + radioId + ", isTransit="
				+ isTransit + ", ref_ClassFareId=" + ref_ClassFareId
				+ ", className=" + className + ", baseFare=" + baseFare
				+ ", flightNo=" + flightNo + ", fuelSurcharge=" + fuelSurcharge
				+ ", currency=" + currency + ", agentDiscount=" + agentDiscount
				+ ", rate=" + rate + ", tax_adult=" + tax_adult
				+ ", tax_child=" + tax_child + ", tax_infant=" + tax_infant
				+ ", amount=" + amount + ", insurance=" + insurance + ", psc="
				+ psc + ", iwjr=" + iwjr + ", childFare=" + childFare
				+ ", infantFare=" + infantFare + ", childDiscount="
				+ childDiscount + ", promoDiscount=" + promoDiscount + ", pph="
				+ pph + ", issuedFee=" + issuedFee + ", route=" + route
				+ ", routeMode=" + routeMode + ", airline=" + airline
				+ ", airlineIata=" + airlineIata + ", agentId=" + agentId
				+ ", updateCode=" + updateCode + ", personType=" + personType
				+ ", timeLimit=" + timeLimit + ", bookCode=" + bookCode
				+ ", bookStatus=" + bookStatus + ", cheapest=" + cheapest
				+ ", journeyKey=" + journeyKey + ", adultPax=" + adultPax
				+ ", childrenPax=" + childrenPax + ", infantPax=" + infantPax
				+ "]";
	}
	
	
}
