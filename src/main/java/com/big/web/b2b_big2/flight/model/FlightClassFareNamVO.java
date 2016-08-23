package com.big.web.b2b_big2.flight.model;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.big.web.b2b_big2.flight.pojo.FareInfo;

@Entity
@Table(name="flight_class_fare_nam")
public class FlightClassFareNamVO extends AbstractFlightClassFare implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Transient
	@Override
	public FareInfo getFareInfo(FareInfo data) {
		FareInfo data_ = super.getFareInfo(data);
		//fix rate
		if (data_.getRate() != null){
			
			int multiplier = 1000;	//multiply
			
			String strValue = data_.getRate().toPlainString().replaceAll("[,. ]", "");
			if (strValue.length() < 5){
				data_.setRate(new BigDecimal(multiplier).multiply(new BigDecimal(strValue)));
			}
		}
		if (data_.getChildFare() != null){
			
			int multiplier = 1000;	//multiply
			
			String strValue = data_.getChildFare().toPlainString().replaceAll("[,. ]", "");
			if (strValue.length() < 5){
				data_.setChildFare(new BigDecimal(multiplier).multiply(new BigDecimal(strValue)));
			}
		}
		/*13 mei 2015 ditemukan kasus di basicrate 494, tp infant fare 49. harusnya 49.4
		if (data_.getInfantFare() != null){
			
			int multiplier = 1000;	//multiply
			
			String strValue = data_.getInfantFare().toPlainString().replaceAll("[,. ]", "");
			if (strValue.length() < 5){
				data_.setInfantFare(new BigDecimal(multiplier).multiply(new BigDecimal(strValue)));
			}
		}
		*/
		if (data_.getInfantFare() != null){
			
			data_.setInfantFare(data_.getRate().multiply(new BigDecimal((double)0.1)).setScale(0, RoundingMode.HALF_UP));
			
		}
		
		//fix tax
		if (data_.getTax_adult() != null){
			data_.setTax_adult(data_.getRate().multiply(new BigDecimal((double)0.1)).setScale(0, RoundingMode.HALF_UP));
		}
		
		if (data_.getTax_child() != null){
			data_.setTax_child(data_.getChildFare().multiply(new BigDecimal((double)0.1)).setScale(0, RoundingMode.HALF_UP));
		}
		
		if (data_.getTax_infant() != null){
			data_.setTax_infant(data_.getInfantFare().multiply(new BigDecimal((double)0.1)).setScale(0, RoundingMode.HALF_UP));
		}
		return data_;
	}
}
