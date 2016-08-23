package com.big.web.b2b_big2.util;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;


public class Phone implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//ga usah dibuat di DB, disini bisa diatur sesuai favorit
	//jika berdasar http://id.wikipedia.org/wiki/Daftar_kode_telepon_di_Indonesia terlalu banyak, maka diambil yang 2 digit saja setelah angka 0
	public static final String[][] areaPhone = { {"21", "Jakarta, Bekasi, Tangerang, Depok"},
												 {"31", "Surabaya, Gresik, Sidoarjo, Bangkalan"},
												 {"22", "Bandung, Cimahi"},
												 {"24", "Semarang, Ungaran, Demak"},
												 {"61", "Medan"}
												}; 
	

	private String raw;
	private String countryCode;
	
	public Phone(String raw) {
		this.raw = raw;
		this.countryCode = "ID";
	}

	public Phone(String raw, String countryCode) {
		this.raw = raw;
		this.countryCode = countryCode;
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * 081-23456
	 * or
	 * (0812)-23456
	 * @return 08123456
	 * @throws NumberParseException 
	 */
	public String getNumbersOnly() throws NumberParseException{
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		  
		PhoneNumber phoneNum = phoneUtil.parse(raw, this.countryCode);
		
		return "0" + String.valueOf(phoneNum.getNationalNumber());
			
	}
	
	/**
	 * Using ID as Indonesia as default region.
	 * @param phoneNumber
	 * @return
	 */
	public String getRegion(){
		return getRegionFromPhone(raw, "ID");
	}

	/**
	 * 
	 * @param phoneNumber
	 * @return 3 digits, such as 210 for jakarta, 778 for batam, 310 for surabay
	 */
	public static String getRegionFromPhone(String phoneNumber, String countryCode){
		
		String areaCode = null; 
		//must handle region, not mobile number
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		try {
			  PhoneNumber phoneNum = phoneUtil.parse(phoneNumber, countryCode);
			  
			  //default take 3
			  areaCode = String.valueOf(phoneNum.getNationalNumber()).substring(0, 3); 
					  
			  for (String[] s: areaPhone){
				  
				  //cocokkan 2 digit pertama
				  String twoDigits = String.valueOf(phoneNum.getNationalNumber()).substring(0, 2);
				  
				  if (s[0].equals(twoDigits)){
					  areaCode = twoDigits;
					  break;
				  }
			  }
			  
			} catch (NumberParseException e) {
			  System.err.println("NumberParseException was thrown: " + e.toString());
			}
		return areaCode;
	}

	public static boolean isValid(String pPhoneNumber) {

	    Pattern pattern = Pattern
	            .compile("((\\+[1-9]{3,4}|0[1-9]{4}|00[1-9]{3})\\-?)?\\d{8,20}");
	    Matcher matcher = pattern.matcher(pPhoneNumber);

	    if (matcher.matches()) return true;


	    return false;
	}
	

	@Override
	public String toString() {
		return "Phone [raw=" + raw + ", countryCode=" + countryCode + "]";
	}

	public static void main(String[] args) throws NumberParseException {
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		  
		PhoneNumber phoneNum = phoneUtil.parse(null, "ID");
//		PhoneNumber phoneNum = phoneUtil.parse("(+6221) 678-769", "ID");
		
		System.err.println(phoneNum.getCountryCode());	//62
		System.err.println(phoneNum.getExtension());
		System.err.println(phoneNum.getNationalNumber());	//21434545
		System.err.println(phoneNum.getNumberOfLeadingZeros());	//1
		System.err.println(phoneNum.getPreferredDomesticCarrierCode());
		System.err.println(phoneNum.getRawInput());

    	/*
//    	System.out.println(getRegionFromPhone("01"));
    	System.out.println(getRegionFromPhone("87678769"));
    	System.out.println(getRegionFromPhone("021678769"));
    	System.out.println(getRegionFromPhone("(021)678769"));
    	System.out.println(getRegionFromPhone("(021) 678 769"));
    	System.out.println(getRegionFromPhone("(+6221) 678-769"));
    	System.out.println(getRegionFromPhone("+6221678769"));
    	System.out.println(getRegionFromPhone("07782133775"));
    	System.out.println(getRegionFromPhone("+6277824234"));
    	*/

	}
}
