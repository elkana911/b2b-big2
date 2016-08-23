package com.big.web.b2b_big2.customer.service;

import java.util.List;

import com.big.web.b2b_big2.customer.model.QCustomer;
import com.big.web.b2b_big2.flight.pojo.ContactCustomer;
import com.big.web.b2b_big2.flight.pojo.PNR;

public interface ICustomerManager {
	List<QCustomer> getQCustomers();
	List<QCustomer> getQCustomers(String agentId);
	List<QCustomer> getQCustomerAdults(String agentId);
	List<QCustomer> getQCustomerChildren(String agentId);
	List<QCustomer> getQCustomerInfants(String agentId);
	List<QCustomer> getQCustomerContacts(String agentId);

	void addHistory(ContactCustomer contactCustomer, String agentId);
	void addHistory(PNR pnr, String agentId);
	void flushHistory();
}
