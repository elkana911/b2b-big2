
package com.big.web.b2b_big2.booking;

import com.big.web.b2b_big2.booking.exception.BookStatusException;


/*
 * 
 * Taken from http://www.slideshare.net/crisfreitas/sabre-reservation-manual, page 46 of 89
 * Citilink: Archived, Closed, Confirmed, Default, Hold, HoldCanceled, PendingArchive, Unmapped
 * 
 */
public enum BOOK_STATUS {
	EXPIRED("Expired", "Expired due time limitation")
	,HK("Confirmed", "Holds confirmed")
//	,UN("Flight Cancelled", "Flight not operating. Change segment status to XK and request an alternative")
	,CLOSED("Closed", "Issued and Closed")
	,HOLD("HOLD", "Booked")
	,OK("OK", "Issued and Confirmed")
	,ISSUED("Issued", "Issued")
//	,WL("Waiting List", "Waiting list")
//	,RQ("On Request", "On request")
//	,RR("", "Reconfirmed by passenger")
//	,TK("", "Holds confirmed, schedule change. Advise passenger of new times and change segment status to HK")
//	,NO("No Action", "No action taken, cancel segment and request an alternate")
	//garuda spek
	,CONFIRMED("Confirmed", "Booking Confirmed")
	,CANCELED("Cancelled", "Cancel Booking")
	,XX("Cancelled", "Cancel confirmed space")
	,UNMAPPED("Unmapped", "Unknown book status")
/*
	KL("", "Confirming from waitlist. Segment status must be changed to HK within 72 hours or the segment will automatically be cancelled"),
	KK("", "OA confirming request Change segment status to HK"),
	WK("", "Was Confirmed. AA schedule change. Segment should be cancelled. Will appear in PNR followed by an SC segment "),
	SC("", "AA schedule change. Shows new segment. Change segment status to HK"),
	HL("Holds", "Holds waitlist status"),
	XK("", "Removes segment and no message is generated to the airline"),
	HX("", "Cancelled segment by the airline. Change segment status to XK")
	
	CONFIRMED("HK", "Held Confirmed"),
	REQUEST("RQ", "On Request"),
	SEATSAVED("SS", "Seat Saved"),
	CHECKINVIRTUAL("CV", "Checked In Via Internet"),
	HOLD,
	CANCEL,
	EXPIRED,
	WAIT;	//dipakai utk trigger ke ODR. ke depan akan dibuang

SS = "Seat Saved" (reservation with seat assignment)
RQ = Waitlisted
NN = "Need/Need" (seat requested)
CV = "Checked In/Virtual" (checked in via internet)
OK - Confirmed Space (Unless the flight is overbooked, you may still get bumped with compensation)
WL - Wait-listed
RQ - On request

The others are more commonly seen in airlines and agents systems
HK - Seat confirmed on a flight
HS - Seat sold but booking not ended and re-retireved
SS - Same as above, but depends on the airlines link to the reservation system
NN - Need Need. Again depends on the airlines link and the number of seats available on the flight at time of booking.
RR - Reconfirmed by passenger.
UN - Flight cancelled by airline
TK - Timing changed by the airline on a confirmed sector.
TL - Timing changed by the airline on a waitlisted sector.
*/
	;
	private String label;
	private String remarks;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	private BOOK_STATUS(String label, String remarks){
		this.label = label;
		this.remarks = remarks;
	}
	
	/**
	 * Look for status based on known labels
	 * @param label
	 * @return
	 */
	public static BOOK_STATUS getLabel(String label){
		for (BOOK_STATUS d : BOOK_STATUS.values()) {
			if (label.equalsIgnoreCase(d.getLabel()))
				return d;
		}
		
		//else return its enum, since agent may return custom value ?
		throw new BookStatusException("Unhandled label of " + label);
	}

	public static BOOK_STATUS getStatus(String status) {
		for (BOOK_STATUS d : BOOK_STATUS.values()) {
			if (status.equalsIgnoreCase(d.name()))
				return d;
		}
		
		//else return its enum, since agent may return custom value ?
		throw new BookStatusException("Unhandled status of " + status);
	}
}
