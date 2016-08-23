package com.big.web.b2b_big2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import com.big.web.Constants;
import com.big.web.b2b_big2.finance.exception.PendingTopUpException;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.pojo.FlightSearchB2B;
import com.big.web.b2b_big2.flight.pojo.Seat;

public class Utils {
	public static final String TODAY = "today";
	public static final String[] todayWords = { TODAY, "hari ini", "hariini",
			"sekarang" };
	public static final String[] tommorrowWords = { "tommorrow", "besok" };
	
	//26 booking codes are possible for price discrimination
	public static final char[] class_business = {'c', 'j', 'd', 'i', 'z'};
	public static final char[] class_economy = {'y', 'b', 'm', 'h', 'n', 'g', 'k', 'l', 'o', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x'};
	public static final char[] class_first = {'f', 'a', 'p', 'r'};
//	public static final String[] class_business = {"c", "j", "d", "i", "z"};
//	public static final String[] class_economy = {"y", "b", "m", "h", "n", "g", "k", "l", "o", "q", "r", "s", "t", "u", "v", "w", "x"};
//	public static final String[] class_first = {"f", "a", "p", "r"};
	
	//must lowercase							  1                   5						  10					   15   		            20						 25
	public static final String[] seatClasses = { "o", "u", "x", "e", "g", "v", "t", "q", "n", "m", "l", "k", "h", "b", "w", "s", "y", "i", "d", "c", "p", "a", "f", "j", "r", "z"};

	public static void createDirectory(String path){
        File dirPath = new File(path);

        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
	}
	
	public static int countChar(String param){
	     return param.length() - param.replace("(", "").length();     //jadi lbh simple ga perlu pake split/loop2an
	}
	
	public static String getLastWord(String param) {
		return param.substring(param.lastIndexOf(" ")+1);
	}

	/**
	 * 
	 * @return 30/12/2014
	 */
	public static String today() {
		return new SimpleDateFormat("dd/MM/yyyy").format(new Date());
	}
	
	public static String prettyDate(Date date) {
		return new SimpleDateFormat("EEEE, dd MMM yyyy").format(date);
	}
	
	/**
	 * 25-Nov-14 jangan gunakan fungsi ini utk digabung dengan email content
	 * @param value
	 * @return
	 */
	public static String prettyRupiah(double value) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(value);
	}
	
	public static String prettyDollar(double value) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("$ ");
        formatRp.setMonetaryDecimalSeparator('.');
        formatRp.setGroupingSeparator(',');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        return kursIndonesia.format(value);

	}	
	
	/**
	 * 
	 * @param value
	 * @return true if TODAY, 08/12/2014
	 */
	public static boolean isToday(String value) {
		if (StringUtils.isEmpty(value)) return false;
		
		if (value.equalsIgnoreCase(TODAY) || value.equals(today())) return true;
		return false;
	}

	public static boolean isSameMinutes(Date date1, Date date2){
		return TimeUnit.MILLISECONDS.toMinutes(date1.getTime()) ==
				TimeUnit.MILLISECONDS.toMinutes(date2.getTime());
	}
	
	/**
	 * ada kebutuhan utk pencarian waktu sekarang ditambah 2 jam
	 * @param offsetHours
	 * @param offsetMinutes
	 * @return
	 */
	public static String getCurrentHoursMinutes(int offsetHours, int offsetMinutes) {
		Date oldDate = new Date();
		long newTime = oldDate.getTime() + TimeUnit.HOURS.toMillis(offsetHours) + TimeUnit.MINUTES.toMillis(offsetMinutes);
		
		return new SimpleDateFormat("HH:mm").format(new Date(newTime));
	}
	
	public static Date getTommorrow(Date startFrom) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startFrom);
		cal.add(Calendar.DAY_OF_YEAR, 1); // <--
		return cal.getTime();
	}

	public static void delay(long millis) {
    	try {
    	    Thread.sleep(millis);                 //1000 milliseconds is one second.
    	} catch(InterruptedException ex) {
    	    Thread.currentThread().interrupt();
    	}
		
	}
	
	public static String getBetween(String begin, String end, String value) {
		if (StringUtils.isEmpty(value)) return "";
			
		int startPosition = value.indexOf(begin) + begin.length();  
		int endPosition = value.indexOf(end, startPosition);
		return value.substring(startPosition, endPosition);
	}

	public static String cleanRate(String rate) {
		//clean up first utk menjamin datanya angka (tanpa cent tentunya)
		if (StringUtils.isEmpty(rate)) rate = "0";

		//buang titik di 2.044.900
		rate = rate.trim().replaceAll("\\.", "");;
		
		if (rate.equalsIgnoreCase("n/a") || rate.toLowerCase().indexOf("habis") > -1) rate= "0";

		return rate;
	}

	public static boolean isNumeric(String s) {  
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	}

	/**
	 * 
	 * @param locale Locale userLocale = pageContext.getRequest().getLocale();
	 * @param isoCode ID  to get Indonesia
	 * @return
	 */
    public static String getCountryNameByIso(Locale locale, String isoCode) {
        final Locale[] available = Locale.getAvailableLocales();

        for (int i = 0; i < available.length; i++) {
        	
        	if (available[i].getCountry().equals(isoCode)) {
        		return available[i].getDisplayCountry(locale);
        	}

        }

        return null;
    }

    public static String generateRandomNumbers(int len) {
    	  if (len > 18)
    	        throw new IllegalStateException("To many digits");
    	    long tLen = (long) Math.pow(10, len - 1) * 9;

    	    long number = (long) (Math.random() * tLen) + (long) Math.pow(10, len - 1) * 1;

    	    String tVal = number + "";
    	    if (tVal.length() != len) {
    	        throw new IllegalStateException("The random number '" + tVal + "' is not '" + len + "' digits");
    	    }
    	    return tVal;
    }
    
    /**
     * Manipulasi string, misal jakarta (cgk) akan return cgk. <br>Jakarta, Cengkareng (CGK) akan return CGK
     * @param value
     * @return
     * @throws ParseException
     */
	public static String getIataCode(String value) throws ParseException {
		if (StringUtils.isEmpty(value)) return "";
		value = value.trim();
				
		//jika user mengetik iata ?		
		String s = Utils.getLastWord(value);

		if (s.indexOf("(") > -1 && s.indexOf(")") > -1) {
			s = Utils.getBetween("(", ")", s).trim();
		}else if (s.indexOf("(") > -1 && s.indexOf(")") < 0) {
			s = s.substring(s.indexOf("(")+1);
		}else if (s.indexOf("(") < 0 && s.indexOf(")") > -1) {
			s = "";//value.substring(value.indexOf("(")+1);
		}
		
		return s;
	}

	public static boolean search(String[] list, String value){
		for (int i = 0; i < list.length; i++){
			
			if (list[i] == null) continue;
				
			if (list[i].equalsIgnoreCase(value))
				return true;
		}
		
		return false;
	}
	
    public static String[] splitIntoGroup(String value, int charCount){
    	return value.split("(?<=\\G.{" + charCount + "})");
    }

    public static String incrementVAN(Integer counter1, Integer counter2, Integer counter3, Integer counter4){
    	//cukup increment the last 12 digit
    	
    	BigDecimal d = new BigDecimal("1" + org.apache.commons.lang.StringUtils.leftPad(counter2.toString(), 4, '0')
        		+  org.apache.commons.lang.StringUtils.leftPad(counter3.toString(), 4, '0')
        		+  org.apache.commons.lang.StringUtils.leftPad(counter4.toString(), 4, '0')

    			);
    	d = d.add(new BigDecimal(1));

    	String s =  org.apache.commons.lang.StringUtils.leftPad(counter1.toString(), 4, '0')
        		+ d.toPlainString().substring(1);
    	
    	//pecah lagi jadi empat
    	String[] splitLine = new String[s.length()/4];
        for (int index = 0; index < splitLine.length; index++)
            splitLine[index] = s.substring(index*4,index*4 + 4);
    	
    	return s;
    }
    
    public static String leftPad(String str, int size, char padChar){
    	return org.apache.commons.lang.StringUtils.leftPad(str, size, padChar);
    }
    
    public static String rightPad(String str, int size, char padChar){
    	return org.apache.commons.lang.StringUtils.rightPad(str, size, padChar);
    }
    
    public static String prettyVAN(String van){
    	//pecah lagi jadi empat
    	String[] splitLine = new String[van.length()/4];
        for (int index = 0; index < splitLine.length; index++)
            splitLine[index] = van.substring(index*4,index*4 + 4);

        return splitLine[0] + "-" + splitLine[1] + "-" + splitLine[2] + "-" + splitLine[3];
    }
    
    /*
    public static String includePath(String s){
    	return  org.apache.commons.lang.StringUtils.removeEnd( org.apache.commons.lang.StringUtils.trim(s), "/") + "/";
    }*/
    
	public static String includeTrailingPathDelimiter(String path) {
		char lastChar = path.charAt(path.length()-1);
		
		if (lastChar == File.separatorChar || lastChar == '/')
			path = path.substring(0, path.length()-1);
		
		path += File.separator;
		return path;
	}
	public static String includeTrailingWebDelimiter(String path) {
		char lastChar = path.charAt(path.length()-1);
		
		if (lastChar == '/')
			path = path.substring(0, path.length()-1);
		
		path += '/';
		return path;
	}
	
    public static List<String> tesArray(List<String> tmp){
    	List<String> l = new ArrayList<String>();
    	for (String string : tmp) {
    		l.add(string);
		} 

    	return l;
    }
    
    public static <T> T nvl(T a, T b) {
    	return (a == null)?b:a;
    }
    		
	public static String getClassCategory(char seatClass){
		for (char c : class_business) {
			if (c == seatClass)
				return "business";
		}
		
		for (char c : class_economy) {
			if (c == seatClass)
				return "economy";
		}
		
		for (char c : class_first) {
			if (c == seatClass)
				return "first";
		}
		
		return "";
	}
	
	public static String getFirstName(String fullName){
		
		String[] s = org.apache.commons.lang.StringUtils.split(fullName, ' ');
		if (s.length < 1) return "";

		return s[0];
	}
	
	public static String getLastName(String fullName){
		
		String[] s = org.apache.commons.lang.StringUtils.split(fullName, ' ');
		if (s.length < 2) return "";
		
		return s[s.length-1];
	}
	/*
	 * jika bikin bingung, pastikan tidak boleh lebih dari 4 kata. 
	 * jika ada 3 field seperti garuda, caranya masing2 box tidak boleh ada spasi maupun ada titik
	 */
	public static String getMiddleName(String fullName){
		
		String[] s = org.apache.commons.lang.StringUtils.split(fullName, ' ');
		if (s.length < 3) return "";
		
		return s[1];
	}
	public static String getFirstMiddleName(String fullName){
		
		String[] s = org.apache.commons.lang.StringUtils.split(fullName, ' ');
		if (s.length < 3) return getFirstName(fullName);
		
		return s[0] + " " + s[1];
	}
	
	/*
	 * memastikan fullName dipecah menjadi 3 kata.
	 * misal jika 'eric elkana tarigan' maka
	 * [0] = eric
	 * [1] = elkana
	 * [2] = tarigan
	 * 
	 * jika 'eric tarigan' maka
	 * [0] = eric
	 * [1] = <empty string>
	 * [2] = tarigan 
	 *
	 * jika 'eric elkana tarigan santosa' maka
	 * [0] = eric
	 * [1] = elkana
	 * [2] = santosa
	 */
	public static String[] splitPersonNameInto3Words(String fullName){
		String[] s = new String[3];
		String[] split = org.apache.commons.lang.StringUtils.split(fullName, ' ');
		
		s[0] = split[0];
		s[1] = split.length < 3 ? "" : split[1];
		s[2] = split.length < 2 ? "" : split[split.length -1];
				
		return s;
	}
	
	
	public static boolean isValidPersonName(String fullName){
		if (StringUtils.isEmpty(fullName)) return false;
		
		String[] s = org.apache.commons.lang.StringUtils.split(fullName, ' ');
		for (String string : s) {
			if (string.length() == 1){
				return false;
			}
		}
		
		return true;
	}
	
	
	/**
	 * Jika fare=100 dan discount 20% akan return 20
	 * @param fare 100
	 * @param discountPercent 0.2
	 * @return
	 */
	public static BigDecimal getDiscount(double fare, double discountPercent){
		//misal fare=123.456 maka yg belakang akan dibulatkan dulu keatas menjadi 123.46
		BigDecimal subTotal = new BigDecimal(Double.toString(fare));
		subTotal = subTotal.setScale(2, RoundingMode.HALF_UP);
		
		BigDecimal amount = subTotal.multiply(new BigDecimal(Double.toString(discountPercent)));
	    amount = amount.setScale(2, RoundingMode.HALF_UP);
	    
		return amount;
		//http://www.java2s.com/Code/Java/Data-Type/CalculationwithBigDecimal.htm
	}

	/**
	 * Jika fare=100 dan discount 20% akan return 80
	 * @param fare 100
	 * @param discountPercent 0.2
	 * @return
	 */
	public static BigDecimal getDiscountAfter(double fare, double discountPercent){
		//misal fare=123.456 maka yg belakang akan dibulatkan dulu keatas menjadi 123.46
		BigDecimal subTotal = new BigDecimal(Double.toString(fare));
		subTotal = subTotal.setScale(2, RoundingMode.HALF_UP);
		
		BigDecimal amount = subTotal.multiply(new BigDecimal(Double.toString(discountPercent)));
		amount = amount.setScale(2, RoundingMode.HALF_UP);
		
		return subTotal.subtract(amount);
		//http://www.java2s.com/Code/Java/Data-Type/CalculationwithBigDecimal.htm
	}
	
	public static Date fixDate(String ddmmyyyy) throws ParseException{
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.parse(ddmmyyyy);
	}

	public static void getImage(String imageFullPath, HttpServletResponse response) throws IOException {	
		
		File file = new File(imageFullPath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		try {
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				bos.write(buf, 0, readNum);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		byte[] bytes = bos.toByteArray();
		response.setContentType("image/" + StringUtils.getFilenameExtension(imageFullPath));
		response.setContentLength(bytes.length);	     
		try {
			response.getOutputStream().write(bytes);
			response.getOutputStream().flush();
		} catch (IOException e) {
			// Handle the exceptions as per your application.
			e.printStackTrace();
		}
		
		bos.close(); bos = null;
		fis.close(); fis = null;
		
	}
	
    public static String getCssSeat(String seat){
    	seat = seat.toLowerCase();
    	
    	for (char c : Utils.class_business){
    		if (seat.charAt(0) == c){
    			return "seatBusiness";
    		}
    	}
    	for (char c : Utils.class_economy){
    		if (seat.charAt(0) == c){
    			return "seatEconomy";
    		}
    	}
    	for (char c : Utils.class_first){
    		if (seat.charAt(0) == c){
    			return "seatFirst";
    		}
    	}
    	/*
    	for (char c : Utils.class_business){
    		if (seat == c){
    			return "seatBusiness";
    		}
    	}
    	for (char c : Utils.class_economy){
    		if (seat == c){
    			return "seatEconomy";
    		}
    	}
    	for (char c : Utils.class_first){
    		if (seat == c){
    			return "seatFirst";
    		}
    	}*/
    	return "";
    }

    public static String getCSSAirlineColor(String airline){
    	if (airline.equalsIgnoreCase(Airline.LION.getAirlineName())){
    		return "fontLion";
    	}else if (airline.equalsIgnoreCase(Airline.BATIK.getAirlineName())){
    		return "fontLion";
    	}else if (airline.equalsIgnoreCase(Airline.WINGS.getAirlineName())){
    		return "fontLion";
    	}else if (airline.equalsIgnoreCase(Airline.SRIWIJAYA.getAirlineName())){
    		return "fontSriwijaya";
    	}else if (airline.equalsIgnoreCase(Airline.NAM.getAirlineName())){
    		return "fontSriwijaya";
    	}else if (airline.equalsIgnoreCase(Airline.CITILINK.getAirlineName())){
    		return "fontCitilink";
    	}else if (airline.equalsIgnoreCase(Airline.GARUDA.getAirlineName())){
    		return "fontGaruda";
    	}else if (airline.equalsIgnoreCase(Airline.EXPRESS.getAirlineName())){
    		return "fontExpress";
    	}else if (airline.equalsIgnoreCase(Airline.MALINDO.getAirlineName())){
    		return "fontLion";
    	}else if (airline.equalsIgnoreCase(Airline.SUSI.getAirlineName())){
    		return "fontSusi";
    	}
    	return "";
    }
    
    public static String getCSSAirlineColor(Airline airline){
    	
    	switch (airline){
    		case LION:
    		case BATIK: 
    		case MALINDO:
    		case WINGS: return "fontLion";
    		
    		case SRIWIJAYA:
    		case NAM: return "fontSriwijaya";
    		
    		case CITILINK: return "fontCitilink";
    		case GARUDA: return "fontGaruda";
    		case EXPRESS: return "fontExpress";
    		case SUSI: return "fontSusi";
    		default: return "";
    	}

    }
	

	public static boolean isEmpty(String val){
		if (val == null) return true;
		
		if (val.trim().length() < 1) return true;
		
		return org.springframework.util.StringUtils.isEmpty(val);
	}

	public static boolean isEmpty(Object[] val){
		if (val == null) return true;
		
		if (val.length < 1) return true;
		
		return false;
	}

	public static boolean isEmpty(Collection list){
		if (list == null) return true;
		
		if (list.size() < 1) return true;
		
		return false;
	}
	
	public static double[] convertToDoubles(String[] values){
		double[] dd = new double[values.length];
		for (int i = 0; i < values.length; i++) {
			if (values[i] == null || values[i].trim().equalsIgnoreCase("0")){
				dd[i] = 0;
				continue;
			}
			dd[i] = Double.parseDouble(values[i]);
		}
		
		return dd;
	}

	/**
	 * Berhubung tiap airline memperlakukan harga beda2, fungsi ini dibuat utk custom price
	 * @param value
	 * @param airline
	 * @return
	 */
	public static int convertToInt(BigDecimal value, Airline airline){
		if (value == null) return 0;
		/*
		int j = value.intValue();
		long h = value.longValue();
		if (airline == Airline.SRIWIJAYA){
			String strValue = value.toPlainString();
			strValue = strValue.replaceAll("[,. ]", "");
			return Integer.parseInt(strValue) ;
		}
		 */
		
		int i = value.intValueExact();
//		return value.intValueExact();
		return i;
		
	}
	
	/**
	 * 
	 * @param str 616a1dc5-545b-420b-ac91-3d3c8b9b54f6
	 * @return YWodxVRbQguskT08i5tU9g
	 */
	public static String uuidToBase64(String str) {
	    Base64 base64 = new Base64();
	    UUID uuid = UUID.fromString(str);
	    ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
	    bb.putLong(uuid.getMostSignificantBits());
	    bb.putLong(uuid.getLeastSignificantBits());
	    return base64.encodeBase64URLSafeString(bb.array());
	}
	
	/**
	 * 
	 * @param str YWodxVRbQguskT08i5tU9g
	 * @return 616a1dc5-545b-420b-ac91-3d3c8b9b54f6
	 */
	public static String uuidFromBase64(String str) {
	    Base64 base64 = new Base64(); 
	    byte[] bytes = base64.decodeBase64(str);
	    ByteBuffer bb = ByteBuffer.wrap(bytes);
	    UUID uuid = new UUID(bb.getLong(), bb.getLong());
	    return uuid.toString();
	}

	/**
	 * Alternative way from UUID
	 * @return 22 digits length of unique ID, such as
	 */
	public static String getApiKey(){
		UUID uuid = UUID.randomUUID();
		return uuidToBase64(uuid.toString());
	}

	/**
	 * <p>
	 * return empty string if date = null
	 * @param date
	 * @param pattern dd/MM/yyyy
	 * @return 
	 */
	public static String Date2String(Date date, String pattern) {
        if (date == null)
             return "";
        else
             return new SimpleDateFormat(pattern, Locale.US).format(date);
   }

   public static Date String2Date(String date, String pattern) {
        try {
             return new SimpleDateFormat(pattern, Locale.US).parse(date);
        } catch (Exception ignore) {
        }

        return null;
   }

    public static String changeDatePattern(String date, String fromPattern,
            String toPattern) {
       return Date2String(String2Date(date, fromPattern), toPattern);
    }

	/**
	 * MST_AIRPORTS menggunakan timezone offset
	 * @param cal
	 * @return 7 (jika timezonenya Asia/Jakarta)
	 */
	public long getTimeZone(Calendar cal){
		return TimeUnit.HOURS.convert(cal.getTimeZone().getRawOffset(), TimeUnit.MILLISECONDS);
	}
	
	/**
	 * Remove empty class if no seats or no price
	 * @param displayAll true to ignore availability/price check
	 * @param list
	 * @return ["A", "O", "C"]
	 */
	public static String[] displayClasses(boolean displayAll, List<FlightSearchB2B> list){
		if (displayAll){
			return Utils.seatClasses;
		}
		
		//might slow down, please consider as setup
		Set<String> availClasses = new LinkedHashSet<String>();
		for (int i = 0; i < list.size(); i++){
			FlightSearchB2B fs = list.get(i);
			
			for (int j = 0; j < fs.getSeats().length; j++){
				Seat seats = fs.getSeats()[j];
				
				//available ?
				if (seats.getAvailable() < 1 || seats.getRate() < 1){
				}else
					availClasses.add(seats.getKelas());
				
			}
		}
		
		Set<String> filterClasses = new LinkedHashSet<String>();
		for (int i = 0; i < Utils.seatClasses.length; i++){

			for (Iterator<String> iterator = availClasses.iterator(); iterator.hasNext();) {
				String _c = iterator.next();
				if (Utils.seatClasses[i].equalsIgnoreCase(_c)){
					filterClasses.add(Utils.seatClasses[i]);
					
					availClasses.remove(_c);
					break;
				}
			}
				
		}
		
		return filterClasses.toArray(new String[filterClasses.size()]);
	}

	/**
	 * If clashed, recall this function again.
	 * @param length
	 * @return
	 */
	public static String generateUniqueCode(int length){
		return RandomStringUtils.randomAlphanumeric(length).toUpperCase();
	}
	
	/**
	 * To make easy, make sure each object had override toString() method into csv format or any format you want.
	 * for example:
	 * public void toString(){
	 * return new StringBuffer(accountNo).append(",").append(custName);
	 * }
	 *  
	 * @param list
	 * @param file
	 * @throws IOException
	 */
	public static void listToFile(List<?> list, File file) throws IOException{
		PrintWriter out = null;
		try {
		    out = new PrintWriter(new FileWriter(file));
		    for (Object text : list) {
		        out.println(text);
		    }             
		} finally {
		    if (out != null) {
		    	out.flush();
		        out.close();
		    }
		}
	}
	
	public static void stringToFile(String string, File file) throws IOException{
		FileUtils.writeStringToFile(file, string);
	}
	public static String getFileContents(String file) throws IOException{
		return FileUtils.readFileToString(new File(file));
	}
	
	public static void tes() throws PendingTopUpException{
		
		if (true) throw new PendingTopUpException();
	}
	
	public static void tes2() throws Exception{
		if (true) throw new RuntimeException("Haha");
	}
	
	/**
	 * encrypt using {@link BCryptPasswordEncoder}
	 * @param value
	 * @return
	 * @see #checkPassword(String, String)
	 */
	public static String encryptPassword(String value){
	    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
	    String encodedPassword = passwordEncoder.encode(value);  
	    return encodedPassword;
	}
	/**
	 * 
	 * @param password
	 * @param encrypted
	 * @return
	 * @see Utils#encryptPassword(String)
	 */
	public static boolean checkPassword(String password, String encrypted){
	    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
	    return passwordEncoder.matches(password, encrypted);  
	}
			
	public static String encryptSHA256(String value) throws NoSuchAlgorithmException{
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(value.getBytes());
		String encryptedString = new String(messageDigest.digest());
		
		return encryptedString;
	}
	
	public static char stringTOChar(String value){
		return value.charAt(0);
	}
	
	public static void main(String[] args) {

		String groupCode = Date2String(new Date(), "d").toLowerCase();
		
		System.err.println(capitalizeWords(groupCode));

//		System.err.println(checkPassword("eric elkana", "$2a$10$lCTI4PvHDRn3B6ZcR43GCuwwJp5S9g/Opqb6cKRuuzsDYMshorxI."));
			
				/*
		Calendar c = Calendar.getInstance();
//		c.setTime(new Date(2015, 3, 7, 19, 22, 0));
		c.set(Calendar.DAY_OF_MONTH, 7);
		c.set(Calendar.MONTH, Calendar.APRIL);
		c.set(Calendar.YEAR, 2015);
		c.set(Calendar.HOUR_OF_DAY, 19);
		c.set(Calendar.MINUTE, 38);
		c.set(Calendar.SECOND, 0);

		Date tgl = c.getTime();
		
		Date a = new Date();
		
		System.out.println(new Date().after(tgl));
		*/
    	/*
    	String[] s0 = org.apache.commons.lang.StringUtils.split("", ',');
    	String[] s1 = org.apache.commons.lang.StringUtils.split("" + null, ',');
    	String[] s2 = org.apache.commons.lang.StringUtils.split("eric-elkana,lia-dwiyanti", ',');
    	String[] s3 = org.apache.commons.lang.StringUtils.split("eric-elkana,lia, dwiyanti", ',');
//    	String[] s3 = StringUtils.commaDelimitedListToStringArray();
    	
    	System.out.println(s0.length);
    	System.out.println(s1.length);
    	System.out.println(s2.length);
    	System.out.println(s3.length);
    	/*
    	System.out.println( includePath("c:/tmp/"));
    	System.out.println( includePath("/tmp/"));
    	System.out.println( includePath("c:/tmp/ "));
    	System.out.println( includePath("c:/tmp\\"));
    	System.out.println( includePath("tmp"));
    	System.out.println( includePath(""));
    	System.out.println( includePath(null));
    	
    	String s1 = incrementVAN(new Integer(8033), new Integer(22), new Integer(8888), new Integer(9999));
    	String s11 = incrementVAN(new Integer(8033), new Integer(22), new Integer(8888), new Integer(0));
    	String s2 = incrementVAN(new Integer(8033), new Integer(22), new Integer(9999), new Integer(9999));
    	String s3 = incrementVAN(new Integer(8033), new Integer(9999), new Integer(9999), new Integer(9999));
    	String s4 = incrementVAN(new Integer(8033), new Integer(9999), new Integer(9999), new Integer(1));
		System.out.println(s1 + "=" + s1.length());
		System.out.println(s11 + "=" + s1.length());
		System.out.println(s2 + "=" + s2.length());
		System.out.println(s3 + "=" + s3.length());
		System.out.println(s4 + "=" + s4.length());
		*/
	}

	public static BigDecimal amount(BigDecimal... values) {
		BigDecimal sum = BigDecimal.ZERO;
		for (BigDecimal bigDecimal : values) {
			if (bigDecimal == null) bigDecimal = BigDecimal.ZERO;
			
			sum = sum.add(bigDecimal);
		}
		
		return sum;
	}

	/**
	 * Biasanya sqiva menampilkan pola className/basisFare
	 * @param className A/b
	 * @return A
	 */
	public static Object parseSubClass(String className) {
		return className.indexOf("/") > -1 ? className.split("/", 2)[0] : className;
	}
	

	/**
	 * Handle booking status such as HK, HK
	 * <p>WARNING, each value not trimmed.
	 * 
	 * @param csv HK, HK
	 * @return data[0] = "HK"
	 * <br>data[1] = " HK"
	 */
	public static String[] splitBookStatus(String csv){
		String[] buf;
		if (csv.indexOf(",") > 0){
			buf = csv.split(",");
		}else{
			buf = new String[1];
			buf[0] = csv;
		}
		return buf;
	}

	public static Date addDays(Date from, int offset) {
		Calendar cal = Calendar.getInstance();
		
	    cal.setTime(from); 
	    cal.add(Calendar.DATE, offset); // adds two hour
	    return cal.getTime(); 
	}

	public static Date addHours(Date from, int offset) {
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(from); 
		cal.add(Calendar.HOUR_OF_DAY, offset); // adds two hour
		return cal.getTime(); 
	}
	
	public static Date addMinutes(Date from, int offset) {
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(from); 
		cal.add(Calendar.MINUTE, offset); // adds two hour
		return cal.getTime(); 
	}

	/**
	 * 
	 * @param from
	 * @param offsetNegative -1 to subtract 1 minutes
	 * @return
	 */
	public static Date subtractMinutes(Date from, int offsetNegative){
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(from);
		cal.add(Calendar.MINUTE, offsetNegative);
		
		return cal.getTime();
	}

	public static String getFullName(String firstName, String middleName, String lastName){
		StringBuffer sb = new StringBuffer(firstName);
		
		if (!isEmpty(middleName))
			sb.append(" ").append(middleName);

		if (!isEmpty(lastName))
			sb.append(" ").append(lastName);
		
		return sb.toString(); 

	}
	
	public static boolean isForbidAdmin(HttpServletRequest request){
        return request.isUserInRole(Constants.ADMIN_ROLE)
        		|| request.isUserInRole(Constants.MANDIRA_ADMIN_ROLE)
        		|| request.isUserInRole(Constants.MANDIRA_SPV_ROLE)
        		;
	}

	public static String arrayToCsv(Collection<?> collection){
		return StringUtils.collectionToCommaDelimitedString(collection);
	}
	
	public static int compareBigDecimal(BigDecimal one, BigDecimal two){
		
		BigDecimal value1 = one == null ? BigDecimal.ZERO : one;
		BigDecimal value2 = two == null ? BigDecimal.ZERO : two;
		
		return value1.compareTo(value2);
		
	}
	
	public static String[] collectionToArray(Collection<?> collection){
		if (collection == null) return null;
		
		return collection.toArray(new String[collection.size()]);
	}

	public static String[] listToArray(List<String> airlines) {
		return collectionToArray(airlines);
	}

	public static String listToCSV(List<?> list) {
		return StringUtils.collectionToDelimitedString(list, ",");
	}



	public static String[] csvToArray(String csv) {
		// TODO Auto-generated method stub
		return StringUtils.commaDelimitedListToStringArray(csv);
	}
	
	/**
	 * Change 'this is a word' to 'This Is A Word'
	 * @param words
	 * @return
	 */
	public static String capitalizeWords(String words){
		return org.apache.commons.lang.WordUtils.capitalizeFully(words);
	}

	public static String quoteStr(String string) {
		return StringUtils.quote(string);
	}

	
}
