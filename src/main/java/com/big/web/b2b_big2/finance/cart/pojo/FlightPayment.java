package com.big.web.b2b_big2.finance.cart.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.big.web.b2b_big2.util.Utils;

/**
 * Diisi setelah booking.
 * Related with: FlightSearchB2BController.java
 *  tapi kayaknya bisa deprecated nih
 * @author Administrator
 *
 */
public class FlightPayment implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3516712826856939272L;

	private final double commissionPercent = 0.05;
	
	private BigDecimal basicFare;
	private BigDecimal insurance;
	private BigDecimal service;
	
	//basic fare, tanpa PPN dan IWJR
	private BigDecimal commission;
	
	public FlightPayment() {
		basicFare = BigDecimal.ZERO;
		insurance = BigDecimal.ZERO;
		service = BigDecimal.ZERO;
	}
	
	
	public void addBasicFare(BigDecimal value){
		if (value == null) value = BigDecimal.ZERO;
		basicFare = basicFare.add(value);
	}
	public void addInsurance(BigDecimal value){
		if (value == null) value = BigDecimal.ZERO;
		insurance = insurance.add(value);
	}
	public void addService(BigDecimal value){
		if (value == null) value = BigDecimal.ZERO;
		service = service.add(value);
	}
	
	/**
	 * Seharusnya berisi total harga tiket tanpa asuransi tambahan dan service. harga net sudah termasuk asuransipun hanya diisi oleh ODR    
	 * @return
	 */
	public BigDecimal getBasicFare() {
		return basicFare;
	}

	public void setBasicFare(BigDecimal basicFare) {
		this.basicFare = basicFare;
	}

	public BigDecimal getInsurance() {
		return insurance;
	}
	public void setInsurance(BigDecimal insurance) {
		this.insurance = insurance;
	}
	public BigDecimal getService() {
		return service;
	}
	public void setService(BigDecimal service) {
		this.service = service;
	}
	
	public BigDecimal getAmount(){
		return Utils.amount(basicFare, insurance, service);
	}
}
