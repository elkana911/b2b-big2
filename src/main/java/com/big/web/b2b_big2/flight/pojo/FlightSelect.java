package com.big.web.b2b_big2.flight.pojo;

import java.io.Serializable;

import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.util.Utils;

/**
 * Biasa dipakai saat user sudah memilih suatu harga. 
 * @author Administrator
 */
public class FlightSelect implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7825107257172309720L;
	private String flightavailid;	//refer to FlightAvailSeatVO
	private String flightNo;
	private Route route;
	private Seat seat;
	private int tripMode;
	private Airline airline;
	
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	public Seat getSeat() {
		return seat;
	}
	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	public int getTripMode() {
		return tripMode;
	}
	public void setTripMode(int tripMode) {
		this.tripMode = tripMode;
	}
	public String getFlightavailid() {
		return flightavailid;
	}
	public void setFlightavailid(String flightavailid) {
		this.flightavailid = flightavailid;
	}
	
	public Airline getAirline() {
		return airline;
	}
	public void setAirline(Airline airline) {
		this.airline = airline;
	}
	//dibuat utk memudahkan validasi, formatnya adalah <flightavailId><class name 1 char><increment><planecounter ? (0/1/2/3)>
	//<increment 3digit> akan memastikan penerbangan kedua akan sesuai dengan penerbangan pertama
	//pertimbangan 6 digit karena kemampuan sistem menampilkan data sebanyak 999999 international flight	
	/**
	 * 
     * Combine parameters into a single string
     * @param flightavailid 36 chars
     * @param airlineCode	2 chars
     * @param kelas 1 char
     * @param listIdx 6 digit
     * @param transitCounter 1 digit
     * @return &lt;36 chars&gt; &lt;2 chars&gt; &lt;1 char&gt;&lt;6 digits&gt;&lt;1 digit counter&gt;
     * <br>8bb6013c-ea65-471d-9898-972183b54369<b>SJ</b>G<b>000000</b>0
     * <br>8bb6013c-ea65-471d-9898-972183b54369<b>SJ</b>G<b>000000</b>1
     * <p>
     * &lt;36 digits&gt;&lt;1 char&gt;&lt;6 digits&gt;&lt;1 digit counter&gt;  deprecated
     * <br><strike>8bb6013c-ea65-471d-9898-972183b54369G0000000</strike>
     * <br><strike>8bb6013c-ea65-471d-9898-972183b54369G0000001</strike>
	 * @see #parse(String)
	 */
	public static String compile(String flightavailid, Airline airline, String kelas, int listIdx, int transitCounter){
    	//link 920CBAEF564C496F9A89D6E21E0ACD4C    	
    	StringBuffer radioValues = new StringBuffer(flightavailid)
		.append(airline.getAirlineCode())
		.append(kelas)
		.append(Utils.leftPad(String.valueOf(listIdx), 6, '0'))
		.append(transitCounter);
    	
		return radioValues.toString();
    	//kirim last digit dengan tipe kelasnya, contoh  <-- G
    }
	
	/**
	 * 
	 * @param format if length is <= 36, will return as flightavailid only. 
	 * @return
	 * @see #compile(String, char, int, int)
	 */
	public static FlightSelect parse(String format){
		FlightSelect fs = new FlightSelect();
		
		if (format.length() <= 36){
			fs.setFlightavailid(format);
			return fs;
		}
		
    	//link 920CBAEF564C496F9A89D6E21E0ACD4C		
//		8bb6013c-ea65-471d-9898-972183b54369SJG0000000
		fs.setFlightavailid(format.substring(0, format.length() - 10));
		Seat seat  = new Seat();
		seat.setKelas(format.substring(format.length() - 8).substring(0, 1));
		fs.setSeat(seat);
		
		fs.setAirline(Airline.getAirlineByCode(format.substring(format.length() - 10).substring(0, 2)));

		return fs;
	}

}
