package com.big.web.b2b_big2.customer.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.big.web.b2b_big2.common.CONTACT_TYPE;
import com.big.web.b2b_big2.customer.dao.ICustomerDao;
import com.big.web.b2b_big2.customer.model.QCustomer;
import com.big.web.b2b_big2.flight.pojo.ContactCustomer;
import com.big.web.b2b_big2.flight.pojo.PERSON_TYPE;
import com.big.web.b2b_big2.flight.pojo.PNR;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.hibernate.BasicHibernate;

@Repository("customerDao")
public class CustomerDaoHibernate extends BasicHibernate implements ICustomerDao{

	private List<QCustomer> pnrHistory = new ArrayList<QCustomer>();
	private List<QCustomer> contactHistory = new ArrayList<QCustomer>();
	
	@Override
	public void addHistory(ContactCustomer contactCustomer, String agentId) {
		
		if (Utils.isEmpty(agentId) || contactCustomer == null){
			log.error("Fail to addhistory for " + contactCustomer + ", agentId is " + agentId);
			return;
		}
		QCustomer qCustomer = new QCustomer();
		
		qCustomer.setFullName(contactCustomer.getFullName());
		qCustomer.setPhone1(contactCustomer.getPhone1());
		qCustomer.setPhone2(contactCustomer.getPhone2());
		qCustomer.setContact_Type(CONTACT_TYPE.CONTACT_PERSON.getFlag());
		qCustomer.setAgent_Id(agentId);
		
		contactHistory.add(qCustomer);
	}

	@Override
	public void addHistory(PNR pnr, String agentId) {
		
		if (Utils.isEmpty(agentId) || pnr == null){
			log.error("Fail to addhistory for " + pnr + ", agentId is " + agentId);
			return;
		}

		
		QCustomer qCustomer = new QCustomer();
		
		qCustomer.setFullName(pnr.getFullName());
		qCustomer.setBirthday(pnr.getBirthday());
		qCustomer.setCountryCode(pnr.getCountryCode());
		
		try {
			if (pnr.getPhone() != null)
				qCustomer.setPhone1(pnr.getPhone().getNumbersOnly());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		qCustomer.setEmail(pnr.getemail);
		qCustomer.setContact_Type(CONTACT_TYPE.PASSENGER.getFlag());
		qCustomer.setSpecialRequest(pnr.getSpecialRequest() == null ? "" : pnr.getSpecialRequest().name());
		qCustomer.setAgent_Id(agentId);
		qCustomer.setIdCard(pnr.getIdCard());
		qCustomer.setId_Type(pnr.getIdCardType());
		
		if (pnr.getPersonType() != null)
			qCustomer.setPerson_Type(pnr.getPersonType().getFlag());

		pnrHistory.add(qCustomer);
	}

	@Override
	public void flushHistory() {
		Session session = getSession();

		for (int i = 0; i < pnrHistory.size(); i++) {
			
			Criteria c = session.createCriteria(QCustomer.class);
			
			c.add(Restrictions.eq("agent_Id", pnrHistory.get(i).getAgent_Id()));
			c.add(Restrictions.eq("fullName", pnrHistory.get(i).getFullName().toUpperCase()));	// cuma fullname saja spy lebih terproteksi bahwa tidak akan ada nama double
			c.add(Restrictions.eq("contact_Type", pnrHistory.get(i).getContact_Type()));
			
			QCustomer _obj;
			if (c.list().size() > 0){
				//tambahin counternya
				_obj = (QCustomer)c.list().get(0);
			}else{
				_obj = pnrHistory.get(i);
				_obj.setFullName(_obj.getFullName().toUpperCase());
				
			}
			
			session.saveOrUpdate(_obj);
			
		}

		for (int i = 0; i < contactHistory.size(); i++) {
			Criteria c = session.createCriteria(QCustomer.class);
			
			c.add(Restrictions.eq("agent_Id", contactHistory.get(i).getAgent_Id()));
			c.add(Restrictions.eq("fullName", contactHistory.get(i).getFullName().toUpperCase()));
			c.add(Restrictions.eq("contact_Type", contactHistory.get(i).getContact_Type()));

			QCustomer _obj;
			if (c.list().size() > 0){
				//tambahin counternya
				_obj = (QCustomer)c.list().get(0);
				_obj.setCounter(_obj.getCounter() == null ? 1 : (_obj.getCounter().longValue() +1));
			}else{
				_obj = contactHistory.get(i);
				_obj.setFullName(_obj.getFullName().toUpperCase());
			}
			
			session.saveOrUpdate(_obj);
			
			/*
			if (i % 50 == 0){
				session.flush();
				session.clear();
			}
			*/
		}
		
		pnrHistory.clear();
		contactHistory.clear();
	}

	public void printHistory() {
		System.err.println("pnrHistory.size=" + pnrHistory.size());
		
		for (QCustomer qCustomer : pnrHistory) {
			System.out.println(qCustomer);
		}

		System.err.println("contactHistory.size=" + contactHistory.size());
		for (QCustomer qCustomer : contactHistory) {
			System.out.println(qCustomer);
		}
	}

	@Override
	public List<QCustomer> getQCustomers() {

		Criteria c = getSession().createCriteria(QCustomer.class);

		return c.list();
		
	}

	@Override
	public List<QCustomer> getQCustomers(String agentId) {
		Criteria c = getSession().createCriteria(QCustomer.class);
		
		c.add(Restrictions.eq("agent_Id", agentId));
		
		return c.list();
	}

	@Override
	public List<QCustomer> getQCustomerAdults(String agentId) {
		Criteria c = getSession().createCriteria(QCustomer.class);
		
		c.add(Restrictions.eq("agent_Id", agentId));
		c.add(Restrictions.eq("contact_Type", CONTACT_TYPE.PASSENGER.getFlag()));
		c.add(Restrictions.eq("person_Type", PERSON_TYPE.ADULT.getFlag()));
		
		return c.list();
	}

	@Override
	public List<QCustomer> getQCustomerChildren(String agentId) {
		Criteria c = getSession().createCriteria(QCustomer.class);
		
		c.add(Restrictions.eq("agent_Id", agentId));
		c.add(Restrictions.eq("contact_Type", CONTACT_TYPE.PASSENGER.getFlag()));
		c.add(Restrictions.eq("person_Type", PERSON_TYPE.CHILD.getFlag()));
		
		return c.list();
	}

	@Override
	public List<QCustomer> getQCustomerInfants(String agentId) {
		Criteria c = getSession().createCriteria(QCustomer.class);
		
		c.add(Restrictions.eq("agent_Id", agentId));
		c.add(Restrictions.eq("contact_Type", CONTACT_TYPE.PASSENGER.getFlag()));
		c.add(Restrictions.eq("person_Type", PERSON_TYPE.INFANT.getFlag()));
		
		return c.list();
	}

	@Override
	public List<QCustomer> getQCustomerContacts(String agentId) {
		Criteria c = getSession().createCriteria(QCustomer.class);
		
		c.add(Restrictions.eq("agent_Id", agentId));
		c.add(Restrictions.eq("contact_Type", CONTACT_TYPE.CONTACT_PERSON.getFlag()));
		
		return c.list();
	}
}
