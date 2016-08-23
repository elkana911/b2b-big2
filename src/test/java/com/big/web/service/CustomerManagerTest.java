package com.big.web.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.big.web.b2b_big2.customer.model.QCustomer;
import com.big.web.b2b_big2.customer.service.ICustomerManager;
import com.big.web.b2b_big2.flight.pojo.BasicContact.SpecialRequest;
import com.big.web.b2b_big2.flight.pojo.ContactCustomer;
import com.big.web.b2b_big2.flight.pojo.PNR;
import com.big.web.b2b_big2.flight.pojo.Title;

public class CustomerManagerTest extends BaseManagerTestCase {
    private Log log = LogFactory.getLog(CustomerManagerTest.class);
    
    @Autowired
    private ICustomerManager mgr;

    @Test
    public void testGetQCustomers() throws Exception {
//    	QCustomer cust = mgr.getCustomerByAgentId("1");
//    	assertNotNull(cust);

    	List<QCustomer> list = mgr.getQCustomers();
    	log.debug("list of quick customer is " + list.size());
    	
    	/*
        user = mgr.getUserByUsername("user");
        assertNotNull(user);
        
        log.debug(user);
        assertEquals(1, user.getRoles().size());
        */
    }
    
    @Test
    public void testAddCustomer() throws Exception{
    	
		ContactCustomer c1 = new ContactCustomer();
		c1.setAgentEmail("berleha@gmail.com");
		c1.setAgentName("Berleha Agency");
		c1.setAgentPhone1("021423244");
		c1.setFullName("Felix karyadi");
		c1.setPhone1("087886283377");
		mgr.addHistory(c1, "ABC123");
		
		ContactCustomer c2 = new ContactCustomer();
		c2.setAgentEmail("berleha@gmail.com2");
		c2.setAgentName("Berleha Agency");
		c2.setAgentPhone1("0214232442");
		c2.setFullName("Felix karyadi");
		c2.setPhone1("0878862833771");
		c2.setPhone2("08788628337");
		mgr.addHistory(c2, "ABC123");
		
		PNR pnr = new PNR();
		pnr.setFullName("Eric Elkana T");
		pnr.setIdCard("1234567890123");
		pnr.setTitle(Title.MR);
		pnr.setSpecialRequest(SpecialRequest.WHEELCHAIR);
		
		mgr.addHistory(pnr, "ABC123");
		
		mgr.flushHistory();

    	List<QCustomer> list = mgr.getQCustomers();
    	log.debug("list of quick customer after add is " + list.size());
    	for (QCustomer qCustomer : list) {
			log.debug(qCustomer);
		}

		ContactCustomer c3 = new ContactCustomer();
		c3.setAgentEmail("berleha@gmail.com2");
		c3.setAgentName("Berleha Agency");
		c3.setAgentPhone1("0214232442");
		c3.setFullName("Felix karyadi");
		c3.setPhone1("0878862833771");
		mgr.addHistory(c3, "ABC123");
		
		mgr.addHistory(c3, "ABC123");
		
		mgr.flushHistory();

    	List<QCustomer> list2 = mgr.getQCustomers();
    	log.debug("2. list of quick customer after add is " + list2.size());
    	for (QCustomer qCustomer : list2) {
			log.debug(qCustomer);
		}

    	/*
        user = new User();

        // call populate method in super class to populate test data
        // from a properties file matching this class name
        user = (User) populate(user);

        user.addRole(roleManager.getRole(Constants.USER_ROLE));

        user = mgr.saveUser(user);
        assertEquals("john", user.getUsername());
        assertEquals(1, user.getRoles().size());

        log.debug("removing user...");

        mgr.removeUser(user.getId().toString());

        try {
            user = mgr.getUserByUsername("john");
            fail("Expected 'Exception' not thrown");
        } catch (Exception e) {
            log.debug(e);
            assertNotNull(e);
        }
    	*/
    }
    
    @Test
    public void testGetAdults() throws Exception{
    	
    }
    
    @Test
    public void testGetChildren() throws Exception{
    	
    }
    
    @Test
    public void testGetInfants() throws Exception{
    	
    }
    
    @Test
    public void testGetContacts() throws Exception{
    	String agentId = "56b6d14b-42b8-4dad-9e52-a248803ef336";
    	List<QCustomer> qCustomerContacts = mgr.getQCustomerContacts(agentId);
    	log.debug("list of quick customer contact is " + qCustomerContacts.size());
    	
    }
/*
    @Test
    public void testSaveUser() throws Exception {
        user = mgr.getUserByUsername("user");
        user.setPhoneNumber("303-555-1212");

        log.debug("saving user with updated phone number: " + user);

        user = mgr.saveUser(user);
        assertEquals("303-555-1212", user.getPhoneNumber());
        assertEquals(1, user.getRoles().size());
    }

    @Test
    public void testAddAndRemoveUser() throws Exception {
        user = new User();

        // call populate method in super class to populate test data
        // from a properties file matching this class name
        user = (User) populate(user);

        user.addRole(roleManager.getRole(Constants.USER_ROLE));

        user = mgr.saveUser(user);
        assertEquals("john", user.getUsername());
        assertEquals(1, user.getRoles().size());

        log.debug("removing user...");

        mgr.removeUser(user.getId().toString());

        try {
            user = mgr.getUserByUsername("john");
            fail("Expected 'Exception' not thrown");
        } catch (Exception e) {
            log.debug(e);
            assertNotNull(e);
        }
    }
    
    @Test
    public void testGetAll() throws Exception {
        List<User> found = mgr.getAll();
        log.debug("Users found: " + found);
        assertEquals(3, found.size());
    }

*/
}
