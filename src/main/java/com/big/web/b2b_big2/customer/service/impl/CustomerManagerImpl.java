package com.big.web.b2b_big2.customer.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.big.web.b2b_big2.customer.dao.ICustomerDao;
import com.big.web.b2b_big2.customer.model.QCustomer;
import com.big.web.b2b_big2.customer.service.ICustomerManager;
import com.big.web.b2b_big2.flight.pojo.ContactCustomer;
import com.big.web.b2b_big2.flight.pojo.PNR;

@Service("customerManager")
//@WebService(serviceName = "CustomerService", endpointInterface = "com.big.web.service.BookingService" )
@Transactional
public class CustomerManagerImpl implements ICustomerManager{

	private static final Log log = LogFactory.getLog(CustomerManagerImpl.class);
	
	@Autowired
	ICustomerDao customerDao;
	
	@Override
	public List<QCustomer> getQCustomers() {
		return customerDao.getQCustomers();
	}

	@Override
	public void addHistory(ContactCustomer contactCustomer, String agentId) {
		customerDao.addHistory(contactCustomer, agentId);
	}

	@Override
	public void addHistory(PNR pnr, String agentId) {
		customerDao.addHistory(pnr, agentId);
	}

	@Override
	public void flushHistory() {
		customerDao.flushHistory();
	}

	@Override
	public List<QCustomer> getQCustomers(String agentId) {
		return customerDao.getQCustomers(agentId);
	}

	@Override
	public List<QCustomer> getQCustomerAdults(String agentId) {
		return customerDao.getQCustomerAdults(agentId);
	}

	@Override
	public List<QCustomer> getQCustomerChildren(String agentId) {
		return customerDao.getQCustomerChildren(agentId);
	}

	@Override
	public List<QCustomer> getQCustomerInfants(String agentId) {
		return customerDao.getQCustomerInfants(agentId);
	}

	@Override
	public List<QCustomer> getQCustomerContacts(String agentId) {
		return customerDao.getQCustomerContacts(agentId);
	}
}
