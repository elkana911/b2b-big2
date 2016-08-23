package com.big.web.b2b_big2.hotel.pojo;

import java.util.ArrayList;
import java.util.List;

import com.big.web.b2b_big2.flight.pojo.BasicContact;
import com.big.web.b2b_big2.flight.pojo.ContactCustomer;



public class BasicBookingForm {
	private SearchForm searchForm;
	private List<BasicContact> adult;
	private List<BasicContact> child;
	private ContactCustomer customer;

	public BasicBookingForm() {
		searchForm = new SearchForm();
		customer = new ContactCustomer();
		
		adult = new ArrayList<BasicContact>();
		child = new ArrayList<BasicContact>();

		/*
		searchForm.setSearchPast(true);
		searchForm.setTripMode(1);
		searchForm.setDepartDate("11/02/2015");
		searchForm.setReturnDate("12/02/2015");
		searchForm.setDepartCity("Jakarta (CGK)");
		searchForm.setDestCity("Denpasar (DPS)");
		*/

	}

	public SearchForm getSearchForm() {
		return searchForm;
	}
	public void setSearchForm(SearchForm searchForm) {
		this.searchForm = searchForm;
	}

	public void prepareContacts() {
		if (searchForm.getAdult() != adult.size()) {
			adult.clear();
			for (int i = 0; i < searchForm.getAdult(); i++) {
				addAdult();
			}
		}
		
		if (searchForm.getChildren() != child.size()) {
			child.clear();
			for (int i = 0; i < searchForm.getChildren(); i++) {
				addChild();
			}
			
		}

	}

	public void addAdult() {
		adult.add(new BasicContact());
	}
	public void addChild() {
		child.add(new BasicContact());
	}

	public List<BasicContact> getAdult() {
		return adult;
	}

	public void setAdult(List<BasicContact> adult) {
		this.adult = adult;
	}

	public List<BasicContact> getChild() {
		return child;
	}

	public void setChild(List<BasicContact> child) {
		this.child = child;
	}

	public ContactCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(ContactCustomer customer) {
		this.customer = customer;
	}

	
}
