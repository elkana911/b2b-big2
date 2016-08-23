package com.big.web.b2b_big2.flight.api.citilink;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.jfree.util.Log;

import com.big.web.b2b_big2.booking.dao.IBookingFlightDao;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.BasicAirlineHandler;
import com.big.web.b2b_big2.flight.IAirlineHandler;
import com.big.web.b2b_big2.flight.api.citilink.exception.CitilinkServerException;
import com.big.web.b2b_big2.flight.odr.ws.BookingFlight;
import com.big.web.b2b_big2.flight.odr.ws.Service1;
import com.big.web.b2b_big2.flight.odr.ws.Service1Soap;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.Utils;

public class Citilink_Handler extends BasicAirlineHandler implements IAirlineHandler{

	private static Citilink_Handler self = null;

	public static Citilink_Handler getInstance(ISetupDao setupDao){
		if (self == null)
			self = new Citilink_Handler(setupDao);
		
		return self;
	}
	
	private Citilink_Handler(ISetupDao setupDao) {
		super(Airline.CITILINK, setupDao);
	}

	/*
	lets start something simple, such as SessionManager
	SessionManager
	*/
	public static void main(String[] args) {
//		URL url = new URL("http://118.97.213.237:3003/BookingManager.svc?wsdl");
		 
        //1st argument service URI, refer to wsdl document above
	//2nd argument is service name, refer to wsdl document above
//        QName qname = new QName("http://schemas.navitaire.com/WebServices/DataContracts/Booking", "HelloWorldImplService");
 
//        Service service = Service.create(url, qname);
 
//        HelloWorld hello = service.getPort(HelloWorld.class);
 
//        System.out.println(hello.getHelloWorldAsString("mkyong"));
    }

	@Override
	public void issuedTicket(String bookCode, IBookingFlightDao bookingFlightDao) throws Exception{
		Service1 svc = new Service1();
		Service1Soap soap = svc.getService1Soap();

		/*
		String sDepartDate = Utils.Date2String(fas.getDep_time(), "dd/MM/yyyy");
		System.out.println("ODR try to updateSchedule for " + fas.getDeparture() + "-" + fas.getDestination() + "," + sDepartDate + " for " + airline.getAirlineCode());
		
		Object ret = soap.updateSchedule(fas.getDeparture(), fas.getDestination(), sDepartDate, airline.getAirlineCode());
//		String ret = "Schedule Updated";
		 */
		BookingFlight obj = soap.confirmBooking(bookCode);
		
		if (!obj.getErrorCode().equals("0")) 
				throw new CitilinkServerException(obj.getErrorCode(), "Unable to confirm/issued Ticket.");
		
		Log.info("ODR confirmBooking return message:" + obj);
		
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

	/*
		return "FareInfo [id=" + id + ", radioId=" + radioId + ", ref_ClassFareId=" + ref_ClassFareId + ", className="
				+ className + ", baseFare=" + baseFare + ", flightNo=" + flightNo + ", fuelSurcharge=" + fuelSurcharge
				+ ", currency=" + currency + ", rate=" + rate + ", tax=" + tax + ", amount=" + amount + ", insurance="
				+ insurance + ", psc=" + psc + ", iwjr=" + iwjr + ", route=" + route + ", routeMode=" + routeMode
				+ ", airline=" + airline + ", airlineIata=" + airlineIata + ", agentId=" + agentId + ", personType="
				+ personType + ", timeLimit=" + timeLimit + ", bookCode=" + bookCode + ", bookStatus=" + bookStatus
				+ ", cheapest=" + cheapest + "]";
	 */
	
	private int convertToInt(BigDecimal value){
		return Utils.convertToInt(value, Airline.CITILINK);
	}
	/**
	 * Mengkalkulasi ulang apakah suatu harga utk Children ditentukan oleh suatu diskon atau tidak.
	 */
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

		sumBasicFare += convertToInt(fareInfo2.getRate()) * adultCount;
		
		if (childCount > 0)
			sumBasicFare += ((convertToInt(fareInfo2.getChildFare()) * childCount) - convertToInt(fareInfo2.getChildDiscount()));
		
		sumBasicFare += convertToInt(fareInfo2.getInfantFare()) * infantCount;
		
		sumFuelSurcharge += convertToInt(fareInfo2.getFuelSurcharge()) * adultCount;
		sumFuelSurcharge += convertToInt(fareInfo2.getFuelSurcharge()) * childCount;
		sumFuelSurcharge += convertToInt(fareInfo2.getFuelSurcharge()) * infantCount * 0;	//free
		
		sumInsurance += convertToInt(fareInfo2.getInsurance()) * adultCount;
		sumInsurance += convertToInt(fareInfo2.getInsurance()) * childCount;
		sumInsurance += convertToInt(fareInfo2.getInsurance()) * infantCount;
		
		sumVatAdult += convertToInt(fareInfo2.getTax_adult()) * adultCount;
		
		//hitung tax anak
		if (childCount > 0){
			double dChildTax = (convertToInt(fareInfo2.getChildFare()) - convertToInt(fareInfo2.getChildDiscount())) * 0.1;
			BigDecimal childTax = new BigDecimal(dChildTax);
			childTax = childTax.setScale(0, RoundingMode.HALF_DOWN);
			sumVatChild += convertToInt(childTax) * childCount;
		}
		
		//hitung tax bayi
		if (infantCount > 0){
			double dInfantTax = convertToInt(fareInfo2.getInfantFare()) * 0.1;
			BigDecimal infantTax = new BigDecimal(dInfantTax);
			infantTax = infantTax.setScale(0, RoundingMode.HALF_DOWN);
			sumVatInfant += convertToInt(infantTax) * infantCount;
//			sumVatInfant += convertToInt(fareInfo2.getTax_infant()) * infantCount;
		}
		
		sumPsc += convertToInt(fareInfo2.getPsc()) * adultCount;
		sumPsc += convertToInt(fareInfo2.getPsc()) * childCount;
		sumPsc += convertToInt(fareInfo2.getPsc()) * infantCount * 0;	//free

		sumIwjr += convertToInt(fareInfo2.getIwjr()) * adultCount;
		sumIwjr += convertToInt(fareInfo2.getIwjr()) * childCount;
		sumIwjr += convertToInt(fareInfo2.getIwjr()) * infantCount;
		
		sumTotal += convertToInt(fareInfo2.getAmount()) * adultCount;
		sumTotal += convertToInt(fareInfo2.getAmount()) * childCount;
		sumTotal += convertToInt(fareInfo2.getAmount()) * infantCount;

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
