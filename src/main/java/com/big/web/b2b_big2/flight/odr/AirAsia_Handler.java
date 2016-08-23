package com.big.web.b2b_big2.flight.odr;

import java.math.BigDecimal;

import com.big.web.b2b_big2.booking.dao.IBookingFlightDao;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.BasicAirlineHandler;
import com.big.web.b2b_big2.flight.IAirlineHandler;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.Utils;

public class AirAsia_Handler extends BasicAirlineHandler implements IAirlineHandler {

	private static AirAsia_Handler self = null;

	public static AirAsia_Handler getInstance(ISetupDao setupDao){
		if (self == null)
			self = new AirAsia_Handler(setupDao);
		
		return self;
	}
	
	private AirAsia_Handler(ISetupDao setupDao) {
		super(Airline.AIRASIA, setupDao);
	}

	@Override
	public char[] getEconomyClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getBusinessClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getPromoClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void issuedTicket(String bookCode, IBookingFlightDao bookingFlightDao) throws Exception {
		// TODO Auto-generated method stub
		//ignored, supported by ODR
	}

	private int convertToInt(BigDecimal value){
		return Utils.convertToInt(value, Airline.AIRASIA);
	}

	@Override
	public FareInfo calculateFareInfo(FareInfo fareInfo2, int adultCount, int childCount, int infantCount) {
		int sumBasicFare = 0;
		int sumFuelSurcharge = 0;
		int sumInsurance = 0;
		int sumVatAdult = 0;
		int sumVatChild = 0;
		int sumVatInfant = 0;
		int sumPsc = 0;
		int sumIwjr = 0;
		int sumTotal = 0;
		int issuedFee = 0;

		sumBasicFare += convertToInt(fareInfo2.getRate()) * adultCount;
		sumBasicFare += convertToInt(fareInfo2.getChildFare()) * childCount;
		sumBasicFare += convertToInt(fareInfo2.getInfantFare()) * infantCount;
		
		sumFuelSurcharge += convertToInt(fareInfo2.getFuelSurcharge()) * adultCount;
		sumFuelSurcharge += convertToInt(fareInfo2.getFuelSurcharge()) * childCount;
		sumFuelSurcharge += convertToInt(fareInfo2.getFuelSurcharge()) * infantCount;
		
		sumInsurance += convertToInt(fareInfo2.getInsurance()) * adultCount;
		sumInsurance += convertToInt(fareInfo2.getInsurance()) * childCount;
		sumInsurance += convertToInt(fareInfo2.getInsurance()) * infantCount;
		
		sumVatAdult += convertToInt(fareInfo2.getTax_adult()) * adultCount;
		sumVatChild += convertToInt(fareInfo2.getTax_child()) * childCount;
		sumVatInfant += convertToInt(fareInfo2.getTax_infant()) * infantCount;
		
		sumPsc += convertToInt(fareInfo2.getPsc()) * adultCount;
		sumPsc += convertToInt(fareInfo2.getPsc()) * childCount;
		sumPsc += convertToInt(fareInfo2.getPsc()) * infantCount;

		sumIwjr += Utils.convertToInt(fareInfo2.getIwjr(), airline) * adultCount;
		sumIwjr += Utils.convertToInt(fareInfo2.getIwjr(), airline) * childCount;
		sumIwjr += Utils.convertToInt(fareInfo2.getIwjr(), airline) * infantCount;
		
		sumTotal += Utils.convertToInt(fareInfo2.getAmount(), airline) * adultCount;
		sumTotal += Utils.convertToInt(fareInfo2.getAmount(), airline) * childCount;
		sumTotal += Utils.convertToInt(fareInfo2.getAmount(), airline) * infantCount;
		
		fareInfo2.setRate(new BigDecimal(sumBasicFare));
		fareInfo2.setPsc(new BigDecimal(sumPsc));
		fareInfo2.setFuelSurcharge(new BigDecimal(sumFuelSurcharge));
		fareInfo2.setTax_adult(new BigDecimal(sumVatAdult));
		fareInfo2.setTax_child(new BigDecimal(sumVatChild));
		fareInfo2.setTax_infant(new BigDecimal(sumVatInfant));
		fareInfo2.setInsurance(new BigDecimal(sumInsurance));
		fareInfo2.setIwjr(new BigDecimal(sumIwjr));
		fareInfo2.setAmount(new BigDecimal(sumTotal));

		return fareInfo2;
	}

}
