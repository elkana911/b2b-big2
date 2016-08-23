package com.big.web.b2b_big2.flight.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotEmpty;

import com.big.web.b2b_big2.util.Phone;

public class BasicContact implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String idCard;
	private String idCardType;
	private Title title;
	@NotEmpty
	private String fullName;
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date birthday;
	private SpecialRequest specialRequest;
	private Phone phone;
	private String countryCode;	//ID, SG

	public enum SpecialRequest{
		WHEELCHAIR
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public SpecialRequest getSpecialRequest() {
		return specialRequest;
	}

	public void setSpecialRequest(SpecialRequest specialRequest) {
		this.specialRequest = specialRequest;
	}

	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	public String getFirstName(){
		String[] s = org.apache.commons.lang.StringUtils.split(fullName, ' ');
		if (s.length < 1) return "";

		return s[0];
	}
	
	public String getLastName(){
		String[] s = org.apache.commons.lang.StringUtils.split(fullName, ' ');
		if (s.length < 2) return "";
		
		return s[s.length-1];
	}
	
	public String getFirstMiddleName(){
		String[] s = org.apache.commons.lang.StringUtils.split(fullName, ' ');
		if (s.length < 3) return getFirstName();
		
		StringBuffer sb = new StringBuffer(s[0]);
		for (int i = 1; i < s.length-1; i++){
			sb.append(" ").append(s[i]);
		}
		
		return sb.toString();
	}

	public String getMiddleName(){
		String[] s = org.apache.commons.lang.StringUtils.split(fullName, ' ');
		if (s.length < 3) return "";
		
		StringBuffer sb = new StringBuffer(s[1]);
		for (int i = 2; i < s.length-1; i++){
			sb.append(" ").append(s[i]);
		}
		return sb.toString();
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	/* not safe
	public static int getDuplicateContact(BasicContact contact, List<?> contacts){
		if (contacts == null || contacts.size() < 2)
			return -1;
		
		for (int j = 1; j < contacts.size(); j++){
			if ( ((BasicContact)contacts.get(j)).getFullName().equalsIgnoreCase(contact.getFullName())){
				return j;
			}
		}
		
		return -1;
	}*/

	public String getIdCardType() {
		return idCardType;
	}

	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}

	@Override
	public String toString() {
		return "BasicContact [id=" + id + ", idCard=" + idCard
				+ ", idCardType=" + idCardType + ", title=" + title
				+ ", fullName=" + fullName + ", birthday=" + birthday
				+ ", specialRequest=" + specialRequest + ", phone=" + phone
				+ ", countryCode=" + countryCode + "]";
	}

	public static void main(String[] args) {
		BasicContact c = new BasicContact();
		
		c.setFullName("eric elkana tarigan");
		System.out.println(c.getFirstMiddleName());
		c.setFullName("eric elkana tarigan semesta");
		System.out.println(c.getFirstMiddleName());
		c.setFullName("eric elkana tarigan yyyha maknyus");
		System.out.println(c.getFirstMiddleName());
		c.setFullName("eric tarigan");
		System.out.println(c.getFirstMiddleName());
		c.setFullName("eric");
		System.out.println(c.getFirstMiddleName());
	}
}
