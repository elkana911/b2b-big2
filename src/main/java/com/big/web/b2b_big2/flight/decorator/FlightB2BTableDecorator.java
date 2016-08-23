package com.big.web.b2b_big2.flight.decorator;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.displaytag.decorator.TableDecorator;
import org.displaytag.properties.MediaTypeEnum;

import com.big.web.b2b_big2.flight.pojo.FlightSearchB2B;
import com.big.web.b2b_big2.flight.pojo.FlightSelect;
import com.big.web.b2b_big2.flight.pojo.Seat;
import com.big.web.b2b_big2.util.Rupiah;
import com.big.web.b2b_big2.util.Utils;

/*
 * tidak terpakai karena tidak ada gunanya, cenderung memperlambat koding di jsp.
 * mending kuasai displaytag di jsp karena sudah cukup mumpuni
 */
public abstract class FlightB2BTableDecorator extends TableDecorator {
	
	abstract String getUniqueParam();

	public String getRowNum() {
		 final StringBuilder out = new StringBuilder(8);
		    out.append((getListIndex() + 1))
		       .append('.');
		    return out.toString();

	}

	protected String timeToString(Date time) {
		return new SimpleDateFormat("EEE HH:mm").format(time);	
	}	
	
    public String getOrigin() {
    	FlightSearchB2B row = (FlightSearchB2B)getCurrentRowObject();
    	FlightSearchB2B secondFlight = row.getSecondFlight();
    	FlightSearchB2B thirdFlight = secondFlight == null ? null : secondFlight.getSecondFlight();

    	if (tableModel.getMedia() == MediaTypeEnum.HTML){
    		StringBuffer sb = new StringBuffer("<div class='flightInfoCell' title='").append(row.getDepartureDescription()).append("'>") 
    							.append(row.getDepartureIata()).append("<br>").append(timeToString(row.getDepartTime())).append("</div>")
    		;
    		if (secondFlight != null){
    			sb.append("<div class='flightInfoCell' title='").append(secondFlight.getDepartureDescription()).append("'>")
				.append(secondFlight.getDepartureIata()).append("<br>").append(timeToString(secondFlight.getDepartTime())).append("</div>") 
				;
    		}
    		
    		if (thirdFlight != null){
    			sb.append("<div class='flightInfoCell' title='").append(thirdFlight.getDepartureDescription()).append("'>")
				.append(thirdFlight.getDepartureIata()).append("<br>").append(timeToString(thirdFlight.getDepartTime())).append("</div>") 
				;
    		}
    		
    		return sb.toString();
    	}
		else{
			return row.getOriginIata()
					+ (secondFlight == null ? "" : "\n" + secondFlight.getDepartureIata())
					+ (thirdFlight == null ? "" : "\n" + thirdFlight.getDepartureIata())
			;
		}
/*
    	if (secondFlight == null){
        	if (tableModel.getMedia() == MediaTypeEnum.HTML)
    			return "<div class='flightInfoCell' title='" + row.getOriginDescription() + "'>" 
        				+ row.getOriginIata() 
        				+ "<br>" + timeToString(row.getDepartTime()) 
        				+ "</div>";
    		else
    			return row.getOriginIata();
    	}else{
        	if (tableModel.getMedia() == MediaTypeEnum.HTML)
        	
			return "<div class='flightInfoCell' title='" + row.getDepartureDescription() + "'>" 
					+ row.getDepartureIata() 
					+ "<br>" + timeToString(row.getDepartTime()) + "</div><br>" 
					+ "<div class='flightInfoCell' title='" + row.getDestinationDescription() + "'>"
					+ secondFlight.getDepartureIata()
					+ "<br>" + timeToString(secondFlight.getDepartTime())+ "</div>" 
					;

    		else
    			return row.getOriginIata();
    		
    	}
    	*/
    }

    public String getDestination() {
    	FlightSearchB2B row = (FlightSearchB2B)getCurrentRowObject();    	
    	FlightSearchB2B secondFlight = row.getSecondFlight();
    	FlightSearchB2B thirdFlight = secondFlight == null ? null : secondFlight.getSecondFlight();

    	if (tableModel.getMedia() == MediaTypeEnum.HTML){
    		StringBuffer sb = new StringBuffer("<div class='flightInfoCell' title='").append(row.getArrivalDescription()).append("'>") 
    							.append(row.getArrivalIata()).append("<br>").append(timeToString(row.getArrivalTime())).append("</div>")
    		;
    		if (secondFlight != null){
    			sb.append("<div class='flightInfoCell' title='").append(secondFlight.getArrivalDescription()).append("'>")
				.append(secondFlight.getArrivalIata()).append("<br>").append(timeToString(secondFlight.getArrivalTime())).append("</div>") 
				;
    		}
    		
    		if (thirdFlight != null){
    			sb.append("<div class='flightInfoCell' title='").append(thirdFlight.getArrivalDescription()).append("'>")
				.append(thirdFlight.getArrivalIata()).append("<br>").append(timeToString(thirdFlight.getArrivalTime())).append("</div>") 
				;
    		}
    		
    		return sb.toString();
    	}
		else{
			return row.getDestinationIata()
					+ (secondFlight == null ? "" : "\n" + secondFlight.getArrivalIata())
					+ (thirdFlight == null ? "" : "\n" + thirdFlight.getArrivalIata())
			;
		}
    	
    	/*
    	if (secondFlight == null){
        	if (tableModel.getMedia() == MediaTypeEnum.HTML)
        		return "<div class='flightInfoCell' title='" + row.getDestinationDescription() +"'>" + row.getDestinationIata() + "<br>" + timeToString(row.getArrivalTime()) + "</div>";
        	else
        		return row.getDestinationIata();
    		
    	}else{
        	if (tableModel.getMedia() == MediaTypeEnum.HTML)
//        		return "<div class='flightInfoCell' title='" + row.getDestinationDescription() +"'>" + row.getDestinationIata() + "<br>" + timeToString(row.getArrivalTime()) + "</div>";
			return "<div class='flightInfoCell' title='" + row.getArrivalDescription() +"'>" 
					+ row.getArrivalIata() 
					+ "<br>" + timeToString(row.getArrivalTime()) + "</div><br>"
					+ "<div class='flightInfoCell' title='" + row.getArrivalDescription() + "'>"
					+ secondFlight.getArrivalIata()
					+ "<br>" + timeToString(secondFlight.getArrivalTime())+ "</div>" 
					;
        	else
        		return row.getDestinationIata();

    	}*/
    }
    /*
	private String minutesToString(long time) {
		int minutes = (int) (time / 60000L % 60L);
		int hours = (int) (time / 3600000L % 24L);
		
		return new String((hours <1 ? "" : hours + "h ") + (minutes <1 ? "" : minutes + "m"));
	}
	*/
    public String getFlightnum() {
    	FlightSearchB2B row = (FlightSearchB2B)getCurrentRowObject();
    	FlightSearchB2B secondFlight = row.getSecondFlight();
    	
    	if (secondFlight == null) {
    		if (tableModel.getMedia() == MediaTypeEnum.HTML)
    			return "<div class='flightInfoCell'>" 
    				+ row.getFlightNumber() 
    				+ "</div>";
    		else
    			return row.getFlightNumber();
    	}else {
    		if (tableModel.getMedia() == MediaTypeEnum.HTML)
    			return "<div class='flightInfoCell'>" 
    				+ row.getFlightNumber() 
    				+ "<br><br><br>"
					+ secondFlight.getFlightNumber()
					+ "</div>" 
					;
    		else
    			return row.getFlightNumber();
    	}
    	
    }
    
    public String getAirline() {
    	FlightSearchB2B row = (FlightSearchB2B)getCurrentRowObject();
    	FlightSearchB2B secondFlight = row.getSecondFlight();
    	FlightSearchB2B thirdFlight = secondFlight == null ? null : secondFlight.getSecondFlight();

		if (tableModel.getMedia() == MediaTypeEnum.HTML){

			StringBuffer sb = new StringBuffer("<div class='flightInfoCell " + Utils.getCSSAirlineColor(row.getAirline())).append("'>").append(row.getAirline())
								.append("<br>").append(row.getFlightNumber())
								.append("</div>")
								;
			
			if (secondFlight != null){
				sb.append("<div class='flightInfoCell " + Utils.getCSSAirlineColor(secondFlight.getAirline())).append("'>").append(secondFlight.getAirline())
					.append("<br>").append(secondFlight.getFlightNumber())
					.append("</div>")
				;
			}
			if (thirdFlight != null){
				sb.append("<div class='flightInfoCell " + Utils.getCSSAirlineColor(thirdFlight.getAirline())).append("'>").append(thirdFlight.getAirline())
				.append("<br>").append(thirdFlight.getFlightNumber())
				.append("</div>")
				;
			}
			return sb. toString();
			/*
			return "<div class='flightInfoCell " + Utils.getCSSAirlineColor(row.getAirline()) + "'>" 
					+ row.getAirline() + "<br>" + row.getFlightNumber()
					+ (secondFlight == null ? "" : "<br>" + secondFlight.getAirline() + "<br>" + secondFlight.getFlightNumber())
					+ (thirdFlight == null ? "" : "<br>" + thirdFlight.getAirline() + "<br>" + thirdFlight.getFlightNumber())
					+ "</div>" 
					;
					*/
		}
		else
			return row.getAirline()
					+ (secondFlight == null ? "" : "\n" + secondFlight.getAirline())
					+ (thirdFlight == null ? "" : "\n" + thirdFlight.getAirline())
					;

    	/*
    	if (secondFlight == null) {
    		if (tableModel.getMedia() == MediaTypeEnum.HTML)
    			return "<div class='flightInfoCell'>" 
    				+ row.getAirline() 
    				+ "<br>" + row.getFlightNumber()
    				+ "</div>";
    		else
    			return row.getAirline();
    	}else {
    		if (tableModel.getMedia() == MediaTypeEnum.HTML)
    			return "<div class='flightInfoCell'>" 
    				+ row.getAirline()
    				+ "<br>" + row.getFlightNumber()
    				+ "<br><br>"
					+ secondFlight.getAirline()
					+ "<br>" + secondFlight.getFlightNumber()
					+ "</div>" 
					;
    		else
    			return row.getAirline();
    		
    	}
    	*/
    }
    
    private String buildDivClassFlight(String seat, FlightSearchB2B row, boolean hidden, String timeGroup, String... radioGroupId){
    	
    	if (row == null) return null;
    	
    	Seat[] seats = row.getSeats();
    	
    	int index = -1;
    	boolean found = false;
    	for (Seat s : seats) {
    		index++;
    		if (s.getKelas().equalsIgnoreCase(seat)) {
    			found = true;
    			break;
    		}
    			
    	}
    	
    	int listIdx = getListIndex();	//bisa sampai index terakhir dari list
    	int viewIdx = getViewIndex();	//hanya yg terlihat saja
    	
    	
    	if (!found) return "<div class='flightInfoCell' style='height:3.2em;'></div>";	//jgn kosong, jd diisi height krn yg dibawahnya bisa naik ke atasnya campur ke departrate
    	
    	if (index < 0 || index > seats.length-1)
    		return "<div class='flightInfoCell'>0</div>";
    	

    	boolean radio = true;
    	if (seats[index].getRate() < 1 || seats[index].getAvailable() < 1) {
    		radio = false;
    		return new StringBuffer("<div class='flightInfoCell notavail' style='height:3.2em;'>")
    					.append("N/A")
    					.append("</div>").toString();
    	}
		//tujuannya utk membedakan group yg transit/non transit. kuncinya ada di 'name'
    	String groupRadio = getUniqueParam() + (radioGroupId.length > 0 ? radioGroupId[0] : "");

    	//supaya bisa di sort di sisi client
    	int planeCounter = 1;
    	if (radioGroupId.length > 0) {
    		if (radioGroupId[0].equals("transit2")) planeCounter = 2;
    		else if (radioGroupId[0].equals("transit3")) planeCounter = 3;
    	}else{
    		planeCounter = row.getSecondFlight() == null ? 0 : 1;	//non transit
    	}	
    	
    	//override jika hidden


    	String _radioTag = radio ? new StringBuffer("<input type='radio' name='").append(groupRadio)
    								.append("' id='").append(groupRadio).append(Utils.leftPad(String.valueOf(listIdx), 6, '0')).append(seat).append(timeGroup)
    								.append("' value='").append(
    															//link 920CBAEF564C496F9A89D6E21E0ACD4C
    															FlightSelect.compile(row.getFlightAvailId(), row.getAirline(), seats[index].getKelas(), listIdx, planeCounter)
    															)
    								.append("' onmousedown='click").append(groupRadio).append("();'")
    								.append(hidden ? "class='hidden'" : "")	//tujuan dihidden supaya user ga nge-klik radio yg lain. utk itu planeCounter harus 0
    								.append(" />").toString() : "";
    	boolean _isCheap = false;
    	for (String c : row.getCheapestClass()){
    		if (c.equals(seat)){
    			_isCheap = true;
    			break;
    		}
    	}

    	if (tableModel.getMedia() == MediaTypeEnum.HTML)
        	return new StringBuffer("<div class='flightInfoCell ")
    				.append(Utils.getCssSeat(seat)).append(" ")
        			.append(Utils.getCSSAirlineColor(row.getAirline())).append(" ")
        			.append(_isCheap ? "cheapest" : "")
        			.append("'>")
        			.append(seats[index].getKelas())
        			.append(_radioTag).append("(").append(seats[index].getAvailable()).append(")<br>")
        			.append(Rupiah.format(seats[index].getRate(), false))
        			.append("</div>").toString();
    	else
    		return new StringBuffer(_radioTag)
    				.append("(").append(seats[index].getKelas()).append(")")
    				.append(seats[index].getRate()).toString();		
    }
/*
    private String buildDivClassFlight(char seat, FlightSearchB2B row, String... radioGroupId){
    	
    	if (row == null) return null;
    	
    	String[] clsSeat = StringUtils.delimitedListToStringArray(row.getClass_seat(), ",");//[T, G, Q, N, K, M, B, Y, D, C, J]
    	
    	int index = -1;
    	boolean found = false;
    	for (String s : clsSeat) {
    		index++;
    		if (s.charAt(0) == seat) {
    			found = true;
    			break;
    		}
    			
    	}
    	
    	int listIdx = getListIndex();	//bisa sampai index terakhir dari list
    	int viewIdx = getViewIndex();	//hanya yg terlihat saja
    	
    	
    	if (!found) return "<div class='flightInfoCell' style='height:3.2em;'></div>";	//jgn kosong, jd diisi height krn yg dibawahnya bisa naik ke atasnya campur ke departrate
    	
    	if (index < 0 || index > clsSeat.length-1)
    		return "<div class='flightInfoCell'>0</div>";
    	
    	String[] rates = StringUtils.delimitedListToStringArray(row.getClass_rate(), ",");//[1109.4, 0, 1248, 1386.6, 1426.2, 1463.6, 1501, 1538.4, 3771.4, 3962.8, 4353.3]
    	String[] seats = StringUtils.delimitedListToStringArray(row.getClass_avail_seat(), ",");//[9, 0, 9, 9, 9, 9, 9, 9, 1, 9, 9]

    	boolean radio = true;
    	if (seats[index].trim().equals("0") || rates[index].trim().equals("0")) {
    		radio = false;
    		return "<div class='flightInfoCell' style='height:3.2em;'></div>";
    	}
		//tujuannya utk membedakan group yg transit/non transit. kuncinya ada di 'name'
    	String groupRadio = getUniqueParam() + (radioGroupId.length > 0 ? radioGroupId[0] : "");

    	//supaya bisa di sort di sisi client
    	int planeCounter = 1;
    	if (radioGroupId.length > 0) {
    		if (radioGroupId[0].equals("transit2")) planeCounter = 2;
    		else if (radioGroupId[0].equals("transit3")) planeCounter = 3;
    	}else{
    		planeCounter = row.getSecondFlight() == null ? 0 : 1;	//non transit
    	}	

    	//dibuat utk memudahkan validasi, formatnya adalah <flightavailId><class name 1 char><increment><planecounter ? (0/1/2/3)>
    	//<increment 3digit> akan memastikan penerbangan kedua akan sesuai dengan penerbangan pertama
    	//pertimbangan 6 digit karena kemampuan sistem menampilkan data sebanyak 999999 international flight
    	String radioValue = row.getFlightAvailId() + clsSeat[index] + Utils.leftPad(String.valueOf(listIdx), 6, '0') + planeCounter;
    	
    	//kirim last digit dengan tipe kelasnya, contoh 8bb6013c-ea65-471d-9898-972183b54369G <-- G
    	String _radioTag = radio ? ("<input type='radio' name='" + groupRadio + "' value='" + radioValue + "' onClick='click" + groupRadio + "();' />") : "";
//    	String _radioTag = radio ? "<form:radiobutton path='depId' value='" + row.getFlightAvailId() + "'/>" : "";
//    	String _radioTag = radio ? "<form:radiobutton path='depId' value='" + (UUID.randomUUID().toString()) + "'/>" : "";

    	if (tableModel.getMedia() == MediaTypeEnum.HTML)
        	return "<div class='flightInfoCell " + Utils.getCssSeat(seat) + " " + Utils.getCSSAirlineColor(row.getAirline()) + "'>" 
        			+ _radioTag + "(" + seats[index].trim() + ")<br>" + Rupiah.format(rates[index], false)         			
        			+ "</div>";
    	else
    		return _radioTag + "(" + seats[index].trim() + ")" + Rupiah.format(rates[index], false);		
    }
*/    
	private String classSeat(String seat) {
    	FlightSearchB2B row = (FlightSearchB2B)getCurrentRowObject();
    	FlightSearchB2B secondFlight = row.getSecondFlight();
    	FlightSearchB2B thirdFlight = secondFlight == null ? null : secondFlight.getSecondFlight();

    	/*
    	 * 7 may 2015, utk airasia dan sriwijaya ? utk sementara berlaku utk semua penerbangan yg sraping
    	 * if airline == scraping, 
    	 * 	check all flight have same radioID ?
    	 * 		if yes, show only one
    	 * 
    	 * dalam 1 penerbangan, aku perlu mengelompokkan radio id dari pesawat pertama sampai terakhir.
    	 * format: <getUniqueParam><listIndex 6 digits><class><jam terbang pertama formatnya HHmm>
    	 * contoh:
    	 * departrate000000H2145
    	 * departratetransit2000000H2145
    	 * departratetransit3000000H2145
    	 * pengelompokan 000000H2145 akan memudahkan javascript auto select group utk price yg sama

    	 * returnrate0000000650
    	 * returnratetransit20000000650
    	 * returnratetransit30000000650
    	 * 
    	 * TUJUAN HIDDEN ? utk kasus transit tp harganya udah total, jd yg ditampilin yg pertama saja
    	 */
    	boolean hideSecondFlight = secondFlight == null ? false : secondFlight.getRadioId().equalsIgnoreCase(row.getRadioId());

    	boolean hideThirdFlight = thirdFlight == null ? false : thirdFlight.getRadioId().equalsIgnoreCase(secondFlight.getRadioId());

    	String timeGroup = Utils.Date2String(row.getDepartTime(), "HHmm");
    	String s1 = buildDivClassFlight(seat, row, false, timeGroup);
    	String s2 = buildDivClassFlight(seat, secondFlight, hideSecondFlight, timeGroup, "transit2");
    	String s3 = buildDivClassFlight(seat, thirdFlight, hideThirdFlight, timeGroup, "transit3");
    	/*
    	String s1 = buildDivClassFlight(seat, row, false, timeGroup);
    	String s2 = secondFlight == null ? null : buildDivClassFlight(seat, secondFlight, hideSecondFlight, timeGroup, "transit2");
    	String s3 = thirdFlight == null ? null : buildDivClassFlight(seat, thirdFlight, hideThirdFlight, timeGroup, "transit3");
    	*/
    	
    	StringBuffer sb = new StringBuffer(s1);
    	
    	if (s2 != null) sb.append("").append(s2);
    	
    	if (s3 != null) sb.append(s3);
    		
    	return sb.toString();
	}
	
	public String getO() {
		return classSeat("O");
	}

	public String getU() {
		return classSeat("U");
	}
	public String getX() {
		return classSeat("X");
	}
	public String getE() {
		return classSeat("E");
	}
	public String getG() {
		return classSeat("G");
	}
	public String getV() {
		return classSeat("V");
	}
	public String getT() {
		return classSeat("T");
	}
	public String getQ() {
		return classSeat("Q");
	}
	public String getN() {
		return classSeat("N");
	}
	public String getM() {
		return classSeat("M");
	}
	public String getL() {
		return classSeat("L");
	}
	public String getK() {
		return classSeat("K");
	}
	public String getH() {
		return classSeat("H");
	}
	public String getB() {
		return classSeat("B");
	}
	public String getW() {
		return classSeat("W");
	}
	public String getS() {
		return classSeat("S");
	}
	public String getY() {
		return classSeat("Y");
	}
	public String getI() {
		return classSeat("I");
	}
	public String getD() {
		return classSeat("D");
	}
	public String getC() {
		return classSeat("C");
	}
	public String getP() {
		return classSeat("P");
	}
	public String getA() {
		return classSeat("A");
	}
	public String getF() {
		return classSeat("F");
	}
	public String getJ() {
		return classSeat("J");
	}
	public String getR() {
		return classSeat("R");
	}
	public String getZ() {
		return classSeat("Z");
	}
}
