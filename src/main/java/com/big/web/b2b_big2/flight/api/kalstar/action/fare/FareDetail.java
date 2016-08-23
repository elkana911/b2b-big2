package com.big.web.b2b_big2.flight.api.kalstar.action.fare;

import com.big.web.b2b_big2.flight.pojo.FareInfo;

/**
 * Store information about an airline/Kalstar's fare. this bean not store how many adults and children book, use {@link FareInfo}
 * 
 * @author Administrator
 * @see FareInfo
 */
public class FareDetail {
	
	private String flightNo;
	private String className;
	private String baseFare;
	
	private PersonFare adultFare;
	private PersonFare childFare;
	private PersonFare infantFare;

	private PersonFare adultReturnFare;
	private PersonFare childReturnFare;
	private PersonFare infantReturnFare;
	
	private String note;
	private String is_ibook;
	private String min_stay;
	private String max_stay;
	private String ccy;
	
	private int agentAdultFare;
	private int agentAdultReturnFare;
	private int agentChildFare;
	private int agentChildReturnFare;
	
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
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
	public PersonFare getAdultFare() {
		return adultFare;
	}
	public void setAdultFare(PersonFare adultFare) {
		this.adultFare = adultFare;
	}
	public PersonFare getChildFare() {
		return childFare;
	}
	public void setChildFare(PersonFare childFare) {
		this.childFare = childFare;
	}
	public PersonFare getInfantFare() {
		return infantFare;
	}
	public void setInfantFare(PersonFare infantFare) {
		this.infantFare = infantFare;
	}
	
	
	public PersonFare getAdultReturnFare() {
		return adultReturnFare;
	}
	public void setAdultReturnFare(PersonFare adultReturnFare) {
		this.adultReturnFare = adultReturnFare;
	}
	public PersonFare getChildReturnFare() {
		return childReturnFare;
	}
	public void setChildReturnFare(PersonFare childReturnFare) {
		this.childReturnFare = childReturnFare;
	}
	public PersonFare getInfantReturnFare() {
		return infantReturnFare;
	}
	public void setInfantReturnFare(PersonFare infantReturnFare) {
		this.infantReturnFare = infantReturnFare;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getIs_ibook() {
		return is_ibook;
	}
	public void setIs_ibook(String is_ibook) {
		this.is_ibook = is_ibook;
	}
	public String getMin_stay() {
		return min_stay;
	}
	public void setMin_stay(String min_stay) {
		this.min_stay = min_stay;
	}
	public String getMax_stay() {
		return max_stay;
	}
	public void setMax_stay(String max_stay) {
		this.max_stay = max_stay;
	}
	public String getCcy() {
		return ccy;
	}
	public void setCcy(String ccy) {
		this.ccy = ccy;
	}
	public int getAgentAdultFare() {
		return agentAdultFare;
	}
	public void setAgentAdultFare(int agentAdultFare) {
		this.agentAdultFare = agentAdultFare;
	}
	public int getAgentAdultReturnFare() {
		return agentAdultReturnFare;
	}
	public void setAgentAdultReturnFare(int agentAdultReturnFare) {
		this.agentAdultReturnFare = agentAdultReturnFare;
	}
	public int getAgentChildFare() {
		return agentChildFare;
	}
	public void setAgentChildFare(int agentChildFare) {
		this.agentChildFare = agentChildFare;
	}
	public int getAgentChildReturnFare() {
		return agentChildReturnFare;
	}
	public void setAgentChildReturnFare(int agentChildReturnFare) {
		this.agentChildReturnFare = agentChildReturnFare;
	}
	@Override
	public String toString() {
		return "FareDetail [flightNo=" + flightNo + ", className=" + className + ", baseFare=" + baseFare
				+ ", adultFare=" + adultFare + ", childFare=" + childFare + ", infantFare=" + infantFare
				+ ", adultReturnFare=" + adultReturnFare + ", childReturnFare=" + childReturnFare
				+ ", infantReturnFare=" + infantReturnFare + ", note=" + note + ", is_ibook=" + is_ibook
				+ ", min_stay=" + min_stay + ", max_stay=" + max_stay + ", ccy=" + ccy + ", agentAdultFare="
				+ agentAdultFare + ", agentAdultReturnFare=" + agentAdultReturnFare + ", agentChildFare="
				+ agentChildFare + ", agentChildReturnFare=" + agentChildReturnFare + "]";
	}
	
	
}
