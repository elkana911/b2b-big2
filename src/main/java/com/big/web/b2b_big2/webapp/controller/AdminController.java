package com.big.web.b2b_big2.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.search.SearchException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.big.web.b2b_big2.agent.model.AgentVO;
import com.big.web.b2b_big2.agent.model.Whole_SalerVO;
import com.big.web.b2b_big2.agent.pojo.ACCOUNT_STATUS;
import com.big.web.b2b_big2.booking.service.IBookingManager;
import com.big.web.b2b_big2.finance.bank.model.TopUpVO;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.model.AirlineVO;
import com.big.web.b2b_big2.flight.model.AirportVO;
import com.big.web.b2b_big2.flight.pojo.AirlineRoute;
import com.big.web.b2b_big2.flight.pojo.Route;
import com.big.web.b2b_big2.flight.service.IAirlineManager;
import com.big.web.b2b_big2.flight.service.IAirportManager;
import com.big.web.b2b_big2.flight.service.IFlightManager;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.AppInfo;
import com.big.web.b2b_big2.util.Utils;
import com.sun.management.OperatingSystemMXBean;


@Controller
public class AdminController extends BaseFormController{
	
//	private transient final Log log = LogFactory.getLog(AdminController.class);

	@Autowired IFlightManager flightManager;
	@Autowired IAirlineManager airlineManager;
	@Autowired IBookingManager bookingManager;
	@Autowired IAirportManager airportMgr;

	/* no need jika extend dari BaseFormController
	private MessageSourceAccessor messages;
    @Autowired
    public void setMessages(MessageSource messageSource) {
        messages = new MessageSourceAccessor(messageSource);
    }
    */
    
    @RequestMapping(method = RequestMethod.GET, value = "/admin/activeusers")
    public String handleRequest(HttpServletRequest request,
                                      HttpServletResponse response)
    throws Exception {

        return "/admin/activeUsers";
    }
    
    /*
    @SuppressWarnings("unchecked")
    public void saveError(HttpServletRequest request, String error) {
        List errors = (List) request.getSession().getAttribute("errors");
        if (errors == null) {
            errors = new ArrayList();
        }
        errors.add(error);
        request.getSession().setAttribute("errors", errors);
    }
*/
    
	@RequestMapping(value = "/admin/agents", method = RequestMethod.GET)
	public ModelAndView showAgents(@RequestParam(required = false, value="q")String query
									, HttpServletRequest request
									, HttpServletResponse response) throws Exception{
		
		try {
			setAgentInfo(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/logout");
		}

		Model model = new ExtendedModelMap();
		
		try {
			
			List<AgentVO> list = agentManager.searchAgents(query);
			
			model.addAttribute("agentList", list);
			
		}catch (SearchException se) {
			model.addAttribute("searchError", se.getMessage());
			model.addAttribute(agentManager.getAgents());
		}
		
		return new ModelAndView("/agent/agentList", model.asMap());
	}
	
	@RequestMapping(value = "/admin/subagents", method = RequestMethod.GET)
	public ModelAndView showSubAgents(@RequestParam(required = false, value="q")String query 
									 ,@RequestParam(required = false, value="status")String status
									 ,@RequestParam(required = false, value="agency")Long agency
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception{
		try {
			setAgentInfo(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/logout");
		}

		try {
			
			ACCOUNT_STATUS accStatus = status == null ? null : ACCOUNT_STATUS.getAccountStatus(status);
			
			//get current login by wholesaler
	    	Whole_SalerVO wholeSaler = agency == null ? agentManager.getWholeSaler(agent.getUserId()) : agentManager.getWholeSalerByAgency(agency);
//	    	Whole_SalerVO wholeSaler = agentManager.getWholeSaler(agent.getUserId());
			
			List<AgentVO> list = agentManager.searchSubAgents(query, accStatus, wholeSaler);
			
			//karena agentVO.accountVO transient maka diisi manual
			for (int i = 0; i < list.size(); i++){
				AgentVO element = list.get(i);
				
//				element.setAccount(financeManager.getUserAccount(element.getUserLogin().getUsername()));
				element.setAccountNo(financeManager.getUserAccount(element.getUserLogin().getUsername()).getNo());
				
				list.set(i, element);
			}
			
			model.addAttribute("hideSelectAgent", wholeSaler);
			model.addAttribute("agencies", agentManager.getWholeSalers());
			model.addAttribute("subAgentList", list);
			model.addAttribute("agency", wholeSaler != null ? wholeSaler.getAgent_id() : null);
		}catch (SearchException se) {
			model.addAttribute("searchError", se.getMessage());
			model.addAttribute(agentManager.getSubAgents());
		}
		
		return new ModelAndView("/agent/subAgentList", model);
	}

	@RequestMapping(value = "/admin/exportsubagents", method = RequestMethod.GET)
	public void exportToCSV(@RequestParam(required = false, value="q")String query, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.debug("------------AgentController.exportSubAgents");
		
		FileWriter fw = null;
		try {
			String cacheDir = Utils.includeTrailingPathDelimiter(setupDao.getValue(ISetupDao.KEY_CIMB_EXPORT_PATH));

			File file = new File(cacheDir + "uploadVA_" +new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + ".csv");
			fw = new FileWriter(file);
			
	    	Whole_SalerVO wholeSaler = agentManager.getWholeSaler(agent.getUserId());
			List<AgentVO> list = agentManager.searchSubAgents(query, ACCOUNT_STATUS.PENDING, wholeSaler);

			if (Utils.isEmpty(list))
				throw new RuntimeException(getText("errors.account.nothingToExport", request.getLocale()));
			
			try {
				//karena agentVO.accountVO transient maka diisi manual
				for (AgentVO agentVO : list) {
					
					if (agentVO.getAccountNo() == null)
						agentVO.setAccountNo(financeManager.getUserAccount(agentVO.getUserLogin().getUsername()).getNo());
//					if (agentVO.getAccount() == null)
//						agentVO.setAccount(financeManager.getUserAccount(agentVO.getUserLogin().getUsername()));

					/*
					fw.append("VANUmber_16");
					fw.append("Name_40");
					fw.append(",").append("StartDate_6");
					fw.append(",").append("ExpiryDate_6");
					fw.append(",").append("RecurringAmount_17");
					fw.append(",").append("Description_40");
					fw.append(",").append("DeleteRecord_1");
					fw.append(",").append("Status_1");
					fw.append(",").append("Reserved_10");
					*/

					fw.append(agentVO.getAccountNo());
//					fw.append(agentVO.getAccount().getNo());
					fw.append(",").append(String.format("%-40s", agentVO.getAgent_Name().toUpperCase()));
					fw.append(",");//.append(Utils.Date2String(agentVO.getUserLogin().getCreated_date(), "yyMMdd"));
					fw.append(",");//.append(Utils.Date2String(agentVO.getUserLogin().getCreated_date(), "yyMMdd"));
					fw.append(",").append(String.format("%-17s", ""));
					fw.append(",").append(String.format("%-40s", ""));
					fw.append(",").append(String.format("%-1s", ""));
					fw.append(",").append(String.format("%-1s", ""));
					fw.append(",").append(String.format("%-10s", ""));

					fw.append("\n");
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}finally{
				if (fw != null){
					fw.flush();
					fw.close();
				}
			}
			
			FileInputStream fis = new FileInputStream(file);
			
			try {
				response.setContentType("text/csv;charset=utf-8");
				response.setHeader("content-disposition", "attachment;filename=" + file.getName());
				org.apache.commons.io.IOUtils.copy(fis, response.getOutputStream());
				response.flushBuffer();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}finally{
				fis.close();
			}
			
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			saveError(request, "Export Failed: " + e.getMessage());
			
			response.sendRedirect(request.getContextPath() + "/admin/subagents");
		}finally{
			if (fw != null)
				fw.close();
		}

	}

	/**
	 * 
	 * @param query can accept multi airports such as CGK,BPS,KNO
	 * @param countryCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/admin/airports")
	public ModelAndView showAirports(@RequestParam(required = false, value = "q")String query, @RequestParam(required = false, value = "country")String countryCode) throws Exception{

		System.out.println("AirportController.handleRequest");

		Model model = new ExtendedModelMap();
		
		try {
			List<AirportVO> list = airportMgr.searchByCountry(query, countryCode);
			
			model.addAttribute("airportList", list);
			
		} catch (SearchException se) {
			model.addAttribute("searchError", se.getMessage());
			model.addAttribute(airportMgr.getAirports());
		}
		model.addAttribute("country", countryCode);
		
		return new ModelAndView("admin/airportList", model.asMap());
	}
	

	@RequestMapping(method = RequestMethod.GET, value = "/admin/airlines")
	public ModelAndView showAirlines(@RequestParam(required = false, value = "q")String query
										, HttpServletRequest request
										, HttpServletResponse response
										) throws Exception{

		System.out.println("AirlineController.handleRequest");

		try {
			setAgentInfo(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/logout");
		}

		
		Model model = new ExtendedModelMap();
		
		try {
			List<AirlineVO> list = airlineManager.search(query);
			
			model.addAttribute("airlineList", list);
			
		} catch (SearchException se) {
			model.addAttribute("searchError", se.getMessage());
			model.addAttribute(airlineManager.getAirlines());
		}
		
		return new ModelAndView("admin/airlineList", model.asMap());
	}
	
    @ModelAttribute
    @RequestMapping(value = "/admin/airlineroutes", method = RequestMethod.GET)
    protected ModelAndView showRoutes(@RequestParam(required = false, value = "code")String airlineCode, HttpServletRequest request
			, HttpServletResponse response)
    throws Exception{
    	
    	log.debug("AirlineController.showRoutes");
    	
		try {
			setAgentInfo(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/logout");
		}

    	
    	List<Airline> listAirline = new ArrayList<Airline>();

    	if (!Utils.isEmpty(airlineCode))
    		
    		if (airlineCode.equals("all")){
    			
        		List<AirlineVO> _a = airlineManager.getAirlines();
        		for (AirlineVO airlineVO : _a) {
        			listAirline.add( Airline.getAirlineByCode(airlineVO.getCode()) );
    			}
    			
    		}else
    			listAirline.add(Airline.getAirlineByCode(airlineCode));
    	else{
    		
    	}

    	List<AirlineRoute> listAirlineRoute = new ArrayList<AirlineRoute>();
    	for (Airline airline : listAirline) {
    		List<Route> _routes = airlineManager.getRoutes(airline, new Date(), new Date());
    		
    		for (Route _r : _routes) {
				
    			listAirlineRoute.add( new AirlineRoute(airline, _r)  );
			}
    	}

    	ModelAndView mav = new ModelAndView("/admin/airlineRoutes");
    	mav.addObject("routes", listAirlineRoute);    	

    	List<AirlineVO> airlineList = airlineManager.getAirlines();
    	
    	//sort by name
    	Collections.sort(airlineList,  new Comparator<AirlineVO>() {
    		@Override
    		public int compare(AirlineVO s1, AirlineVO s2) {
    			return s1.getName().compareTo(s2.getName());
    		}
    	});
    			
    	mav.addObject("airlines", airlineList);
    	
    	return mav; 
    	
    }
    
    public static int getCPUCount(){
    	
		try {
			OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
			        .getOperatingSystemMXBean();
			
			return bean.getAvailableProcessors();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
    }
    // hidden feature in java 7, katanya bisa disabled di masa mendatang
	public static double getCPULoad(int intervalSeconds){
	    double val = -1;
		try {
			OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
			        .getOperatingSystemMXBean();

			long time0, time1;
			time0 = System.currentTimeMillis();
			do{
				time1 = System.currentTimeMillis();
				
				val = bean.getSystemCpuLoad();
//				System.out.println(val);
			}while ((time1 - time0) < intervalSeconds * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return val; 
		
	}
    

    @RequestMapping(value = "/b2b/sysInfo", method = RequestMethod.GET)
	public @ResponseBody List<AppInfo> getSystemInfo(){
		double max = Runtime.getRuntime().maxMemory() / 1024;
		double used = (Runtime.getRuntime().totalMemory() / 1024)
				- (Runtime.getRuntime().freeMemory() / 1024);
		DecimalFormat df = new DecimalFormat(" (0.0000'%')");
		DecimalFormat df2 = new DecimalFormat(" # 'KB'");

		List<AppInfo> sysInfo = new ArrayList<AppInfo>();
		sysInfo.add(new AppInfo("Used Memory", df2.format(used) + df.format(used / max * 100)));
		sysInfo.add(new AppInfo("Usable Memory", df2.format(max - used) + df.format((max - used) / max * 100)));
		
		int sec = setupDao.getValueAsInt(ISetupDao.KEY_SYS_CPU_WAIT_SEC);
		if (sec > 5) sec = 5;
			
		BigDecimal cpu = new BigDecimal(getCPULoad(sec) * 100).setScale(0, RoundingMode.HALF_UP);
		sysInfo.add(new AppInfo("CPU", cpu.toString() + " %"));
		sysInfo.add(new AppInfo("CPU Core", getCPUCount()));
		return sysInfo;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/monitor")    
    public String showMonitoring(HttpServletRequest request,
                                      HttpServletResponse response)throws Exception{
		try {
			setAgentInfo(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/logout";
		}

//    	request.setAttribute("agency", agentManager.getAppInfo());
//    	request.setAttribute("flight", flightManager.getAppInfo());
//    	request.setAttribute("airline", airlineManager.getAppInfo());
//    	request.setAttribute("booking", bookingManager.getAppInfo());
//    	request.setAttribute("finance", financeManager.getAppInfo());
//		request.setAttribute("system", sysInfo);

    	//TODO : hotel part
    	
		
    	
    	return "/admin/monitor";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/topups")    
    public String showTopUpAgents(@RequestParam(required = false, value="bank")String bankCode
    		,@RequestParam(required = false, value="status")String status
    		,@RequestParam(required = false, value="agency")Long agency
    		,HttpServletRequest request
    		,HttpServletResponse response)throws Exception{
    	
		try {
			setAgentInfo(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/logout";
		}

		List<TopUpVO> list = financeManager.getTopUps(bankCode, status, agency);
    	
    	request.setAttribute("topuplist", list);
    	request.setAttribute("banks", financeManager.getBanks());
//    	request.setAttribute("status", list);
    	request.setAttribute("agencies", agentManager.getWholeSalers());
    	return "/admin/topUpList";
    }
    
	@RequestMapping(value = "/admin/tasks", method = RequestMethod.GET)
	public ModelAndView showTasks(@RequestParam(required = false, value="q")String query 
									 ,@RequestParam(required = false, value="status")String status
									 ,@RequestParam(required = false, value="agency")Long agency
									 , ModelMap model
									 , HttpServletRequest request
									 , HttpServletResponse response) throws Exception{
		
//		Model model = new ExtendedModelMap();
		try {
			setAgentInfo(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/logout");
		}
		return new ModelAndView("/admin/taskList", model);
	}

}
