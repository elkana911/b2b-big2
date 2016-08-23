package com.big.web.b2b_big2.flight;

/**
 * One airline many PriceRule 
 * @author Administrator
 *
 */
public class PriceRule {
	private Airline airline;
	private double minimumPrice;
	private String currency;
	
	private double factor;
	
	public PriceRule() {
		super();
		factor = 1;
		minimumPrice = 0;	//or -1. jadi tidak berlaku utk price < 1
		currency = "IDR";
	}
	public Airline getAirline() {
		return airline;
	}
	public void setAirline(Airline airline) {
		this.airline = airline;
	}
	
	public double getMinimumPrice() {
		return minimumPrice;
	}
	public void setMinimumPrice(double minimumPrice) {
		this.minimumPrice = minimumPrice;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getFactor() {
		return factor;
	}
	public void setFactor(double factor) {
		this.factor = factor;
	}
	public static PriceRule getPriceRule(PriceRule[] rules, Airline airline){
		
		for (int i = 0; i < rules.length; i++){
			if (rules[i].getAirline() == airline){
				return rules[i];
			}
		}
		
		return null;
	}
	
}
