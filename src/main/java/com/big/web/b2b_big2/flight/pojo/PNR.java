package com.big.web.b2b_big2.flight.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;

import com.big.web.b2b_big2.booking.model.BookingFlightDtlVO;
import com.big.web.b2b_big2.util.Phone;
import com.big.web.b2b_big2.util.Utils;

public class PNR extends BasicContact implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6766170884355215367L;

	EnumSet<Title> adultTitles = EnumSet.of(Title.MR, Title.MRS, Title.MS);
	EnumSet<Title> childTitles = EnumSet.of(Title.MSTR, Title.MISS);
	EnumSet<Title> infantsTitles = EnumSet.of(Title.MSTR, Title.MISS);

	private PERSON_TYPE personType;
	private String passportId;
	private Date passportExpired;
	private int parentId;	//biasa dipakai oleh infant 
	private String ticketNo;
	
	public PNR(){
		
	}
	public PNR(BasicContact contact, PERSON_TYPE personType) {
		setId(contact.getId());
		setBirthday(contact.getBirthday());
		setFullName(contact.getFullName().toUpperCase());
		setIdCard(contact.getIdCard());
		setPhone(contact.getPhone());
		setSpecialRequest(contact.getSpecialRequest());
		setTitle(contact.getTitle());
		
		if (contact instanceof ContactInfant){
			setParentId(((ContactInfant)contact).getNum());
		}
		
		setPersonType(personType);
	}
	
	public EnumSet<Title> getTitles(){

		switch (personType){
		case ADULT:
			return EnumSet.of(Title.MR, Title.MRS, Title.MS);
		case CHILD:
			return EnumSet.of(Title.MSTR, Title.MISS);
		case INFANT:
			return EnumSet.of(Title.MSTR, Title.MISS);
		}
		return null;
	}
	
	public PNR(BookingFlightDtlVO obj){
		if (obj == null) return;
		
		setId(obj.getId());
		
		setFullName(obj.getFullName());
		
		setIdCard(obj.getPassenger_id());
		
		if (obj.getPassenger_phone() != null)
			setPhone(new Phone(obj.getPassenger_phone()));
		
		setSpecialRequest(obj.getSpec_request());
		setTitle(Title.valueOf(obj.getPassenger_title()));
		
		String _pType = Utils.nvl(obj.getPassenger_type(), "");
		
		setPersonType(PERSON_TYPE.getPersonType(_pType));
		
		//fix timestamp problem, could danger spring mvc date form
		/*
		if (obj.getPassenger_bday() instanceof Timestamp){
			Date dt = new Date(obj.getPassenger_bday().getTime());
			setBirthday(dt);
		}else{*/
			setBirthday(obj.getPassenger_bday());
//		}
		
		setTicketNo(obj.getPassenger_ticket_no());
		
	}

	public PERSON_TYPE getPersonType() {
		return personType;
	}

	public void setPersonType(PERSON_TYPE personType) {
		this.personType = personType;
	}
	public String getPassportId() {
		return passportId;
	}
	public void setPassportId(String passportId) {
		this.passportId = passportId;
	}
	public Date getPassportExpired() {
		return passportExpired;
	}
	public void setPassportExpired(Date passportExpired) {
		this.passportExpired = passportExpired;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

}
