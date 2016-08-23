package com.big.web.b2b_big2.flight.odr;

import java.math.BigDecimal;

import com.big.web.b2b_big2.booking.dao.IBookingFlightDao;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.BasicAirlineHandler;
import com.big.web.b2b_big2.flight.IAirlineHandler;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.Utils;

public class Garuda_Handler extends BasicAirlineHandler implements IAirlineHandler {

	private static Garuda_Handler self = null;

	public static Garuda_Handler getInstance(ISetupDao setupDao){
		if (self == null)
			self = new Garuda_Handler(setupDao);
		
		return self;
	}
	
	private Garuda_Handler(ISetupDao setupDao) {
		super(Airline.GARUDA, setupDao);
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
		return Utils.convertToInt(value, Airline.GARUDA);
	}
	/**
	 * Harusnya Garuda tidak bisa calculate infantCount > 0
	 */
	@Override
	public FareInfo calculateFareInfo(FareInfo fareInfo2, int adultCount, int childCount, int infantCount) {
		
		//override
//		infantCount = 0;	//mulai 1 april udah bisa beli infant
		
		int sumBasicFare = 0;
		int sumFuelSurcharge = 0;
		int sumInsurance = 0;
		int sumVatAdult = 0;
		int sumVatChild = 0;
		int sumVatInfant = 0;
		int sumPsc = 0;
		int sumIwjr = 0;
		int sumPph = 0;
		int sumAgentDiscount = 0;	// biasa utk perhitungan ke agent setelah booking sehingga bisa lebih murah waktu display harga ke agent
		int sumTotal = 0;

		sumBasicFare += convertToInt(fareInfo2.getRate()) * adultCount;
		sumBasicFare += (convertToInt(fareInfo2.getChildFare()) * childCount);
		sumBasicFare += convertToInt(fareInfo2.getInfantFare()) * infantCount;
		
		sumFuelSurcharge += convertToInt(fareInfo2.getFuelSurcharge()) * adultCount;
		sumFuelSurcharge += convertToInt(fareInfo2.getFuelSurcharge()) * childCount;
		sumFuelSurcharge += convertToInt(fareInfo2.getFuelSurcharge()) * infantCount * 0;	//free
		
		sumInsurance += convertToInt(fareInfo2.getInsurance()) * adultCount;
		sumInsurance += convertToInt(fareInfo2.getInsurance()) * childCount;
		sumInsurance += convertToInt(fareInfo2.getInsurance()) * infantCount;

		sumAgentDiscount += convertToInt(fareInfo2.getAgentDiscount()) * adultCount;
		sumAgentDiscount += convertToInt(fareInfo2.getAgentDiscount()) * childCount;
		sumAgentDiscount += convertToInt(fareInfo2.getAgentDiscount()) * infantCount;
		
		sumVatAdult += convertToInt(fareInfo2.getTax_adult()) * adultCount;
		sumVatChild += convertToInt(fareInfo2.getTax_child()) * childCount;
		sumVatInfant += convertToInt(fareInfo2.getTax_infant()) * infantCount;
		
		sumPsc += convertToInt(fareInfo2.getPsc()) * adultCount;
		sumPsc += convertToInt(fareInfo2.getPsc()) * childCount;
		sumPsc += convertToInt(fareInfo2.getPsc()) * infantCount * 0;	//free

		sumIwjr += convertToInt(fareInfo2.getIwjr()) * adultCount;
		sumIwjr += convertToInt(fareInfo2.getIwjr()) * childCount;
		sumIwjr += convertToInt(fareInfo2.getIwjr()) * infantCount;

		sumPph += convertToInt(fareInfo2.getPph()) * adultCount;
		sumPph += convertToInt(fareInfo2.getPph()) * childCount;
		sumPph += convertToInt(fareInfo2.getPph()) * infantCount;
		
		sumTotal += convertToInt(fareInfo2.getAmount()) * adultCount;
		sumTotal += convertToInt(fareInfo2.getAmount()) * childCount;
		sumTotal += convertToInt(fareInfo2.getAmount()) * infantCount;

		fareInfo2.setRate(new BigDecimal(sumBasicFare));
		fareInfo2.setPsc(new BigDecimal(sumPsc));
		fareInfo2.setAgentDiscount(new BigDecimal(sumAgentDiscount));
		fareInfo2.setFuelSurcharge(new BigDecimal(sumFuelSurcharge));
		fareInfo2.setTax_adult(new BigDecimal(sumVatAdult));
		fareInfo2.setTax_child(new BigDecimal(sumVatChild));
		fareInfo2.setTax_infant(new BigDecimal(sumVatInfant));
		fareInfo2.setInsurance(new BigDecimal(sumInsurance));
		fareInfo2.setIwjr(new BigDecimal(sumIwjr));
		fareInfo2.setPph(new BigDecimal(sumPph));
		
		fareInfo2.setAmount(new BigDecimal(sumTotal));
		
		return fareInfo2;
	}

}
