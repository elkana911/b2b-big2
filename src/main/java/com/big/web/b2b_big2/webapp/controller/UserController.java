package com.big.web.b2b_big2.webapp.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.big.web.Constants;
import com.big.web.b2b_big2.hotel.mandira.pojo.RequestAgentData;
import com.big.web.b2b_big2.hotel.mandira.pojo.ResponseAgentData;
import com.big.web.b2b_big2.util.ResponseClient;
import com.big.web.b2b_big2.util.StopWatch;
import com.big.web.dao.SearchException;
import com.big.web.model.Role;
import com.big.web.model.User;
import com.big.web.service.LookupManager;
import com.big.web.service.UserManager;


/**
 * Simple class to retrieve a list of users from the database.
 * <p/>
 * <p>
 * <a href="UserController.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
//@RequestMapping("/admin/users*")
public class UserController {
	protected final transient Log log = LogFactory.getLog(getClass());
	
    private UserManager userManager = null;

    @Autowired
    LookupManager lookUpManager;
    
    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * bisa dites di browser http://localhost:7070/b2b-big2/mandira?user=eric&token=abc123
     * bisa skip login page karena:
     * di security.xml dan decorators.xml
     * selengkapnya di evernote appfuse "how to bypass login when accessed by 3rd party ?"
     */ 
//	@RequestMapping(value = "/mandira", method = RequestMethod.GET, headers = "Accept=*/*")	//return as text
	@RequestMapping(value = "/mandira", method = RequestMethod.GET, produces = "application/json")	//return as json
	public @ResponseBody ResponseClient handleRequestFromMandira(
			@RequestParam(required = false, value = "user") String user
			,@RequestParam(required = false, value = "token") String token) throws Exception{
		
		/*
Link:
A. https://www.berleha.com:8443/mandira?user=wahyu&token=abc123 (json response)
B. http://www.mandira.in/validate.php?user=wahyu&token=abc123 (xml response)
C. http://www.mandira.in/agentdata.php?user=wahyu&token=abc123 (xml response)

Login Phase
1. mandira kirim link A berisi user beserta token. 
2. berleha terima link A, kirim balik link B ke mandira.
3. mandira validasi user dan token, jika valid berleha skip proses login dan kirim link C untuk request data yg diperlukan. 

fitur logo jd ga kepake di signup.kepake wkt edit profile.
		 */
		String data = "Hello " + user;
        
        String url = "http://103.11.133.138/~dummyapi/ma_xml_response_bookingregular.php";

        //You should create Request class and Response class. contoh di 
        RequestAgentData request = new RequestAgentData();
        
        HttpURLConnection con = null ;
        InputStream is = null;
        StopWatch sw = StopWatch.AutoStart();
        try {

            con = (HttpURLConnection) ( new URL(url)).openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/xml");

            //send query
            OutputStream os = con.getOutputStream();
            JAXBContext jaxbContextRequest = JAXBContext.newInstance(request.getClass()); 
            jaxbContextRequest.createMarshaller().marshal(request, os);
            os.flush();
            os.close();
            
            if (con.getResponseCode() != 200) {
            	throw new RuntimeException("Message sent failed");
            }
            
//            ResponseHotelSearch data = (ResponseHotelSearch)getFromServer(city, ResponseHotelSearch.class);
//            BigEngine-Xml/src/main/java/com/big/engine/xml/client/mandira/hotel/allotment/RequestAllotment.java
            
            //unmarshall
            is = con.getInputStream();
            JAXBContext jaxbContextResponse= JAXBContext.newInstance(ResponseAgentData.class); 
            Object obj = jaxbContextResponse.createUnmarshaller().unmarshal(is);
            
            //read obj here
            log.debug("receive " + ((ResponseAgentData)obj));

            sw.stopAndPrint(null);
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }
	
        ResponseClient resp = new ResponseClient();
        
        if (true){
        	resp.setData(data);
        	resp.setErrCode("0");
        }
		 
		return resp;	//{"errCode":"0","data":"Hello eric"}
	}

    @RequestMapping(method = RequestMethod.GET, value = "/admin/users")
    public ModelAndView handleRequest(@RequestParam(required = false, value = "q") String query, @RequestParam(required = false, value = "roleId") Long roleId) throws Exception {
        Model model = new ExtendedModelMap();
        
        List<User> list;
        try {
        	list = userManager.search(query);
        	
        	List<User> newList = new ArrayList<User>();
        	
        	if (roleId == null)
        		newList.addAll(list);
        	else
	        	for (int i = 0; i < list.size(); i++){
	        		User element = list.get(i);
	        		
	        		Set<Role> _roles = element.getRoles();
	        		for (Iterator iterator = _roles.iterator(); iterator.hasNext();) {
						Role _role = (Role) iterator.next();
						
						if (_role.getId() == roleId){
							newList.add(element);
						}
					}
	        	}

            model.addAttribute(Constants.USER_LIST, newList);
        } catch (SearchException se) {
            model.addAttribute("searchError", se.getMessage());
            model.addAttribute(userManager.getUsers());
        }

        List<Role> listRoles = lookUpManager.getRoles();
    	model.addAttribute("roles", listRoles);
    	
        return new ModelAndView("admin/userList", model.asMap());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/preferences")
    public ModelAndView handlePreferences(@RequestParam(required = false, value = "userId") Long userId) throws Exception {
    	Model model = new ExtendedModelMap();

    	return new ModelAndView("userPref", model.asMap());
    }
}
