package com.big.web.b2b_big2.flight.api.kalstar.action.payment;

public class TicketUnit {
	private String pax_name;
	private String ticket_no;
	public String getPax_name() {
		return pax_name;
	}
	public void setPax_name(String pax_name) {
		this.pax_name = pax_name;
	}
	public String getTicket_no() {
		return ticket_no;
	}
	public void setTicket_no(String ticket_no) {
		this.ticket_no = ticket_no;
	}
	@Override
	public String toString() {
		return "TicketUnit [pax_name=" + pax_name + ", ticket_no=" + ticket_no + "]";
	}
	
}
