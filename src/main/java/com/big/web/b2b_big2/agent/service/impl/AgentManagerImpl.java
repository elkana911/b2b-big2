package com.big.web.b2b_big2.agent.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.big.web.b2b_big2.agent.dao.IAgentDao;
import com.big.web.b2b_big2.agent.model.AgentTrxUsdVO;
import com.big.web.b2b_big2.agent.model.AgentTrxVO;
import com.big.web.b2b_big2.agent.model.AgentVO;
import com.big.web.b2b_big2.agent.model.Whole_SalerVO;
import com.big.web.b2b_big2.agent.pojo.ACCOUNT_STATUS;
import com.big.web.b2b_big2.agent.pojo.Agent;
import com.big.web.b2b_big2.agent.pojo.WHOLESALER;
import com.big.web.b2b_big2.agent.service.IAgentManager;
import com.big.web.b2b_big2.booking.model.BookingFlightVO;
import com.big.web.b2b_big2.booking.model.BookingHotelVO;
import com.big.web.b2b_big2.email.dao.IEmailDao;
import com.big.web.b2b_big2.finance.bank.BankCode;
import com.big.web.b2b_big2.finance.bank.TOPUP_STATUS;
import com.big.web.b2b_big2.finance.bank.TopUpPayment;
import com.big.web.b2b_big2.finance.bank.model.TopUpVO;
import com.big.web.b2b_big2.finance.cart.pojo.Cart;
import com.big.web.b2b_big2.finance.dao.IFinanceDao;
import com.big.web.b2b_big2.report.pojo.Revenue;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.AppInfo;
import com.big.web.b2b_big2.util.Rupiah;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.model.User;
import com.big.web.model.UserAccountVO;

@Service("agentManager")
@Transactional
public class AgentManagerImpl implements IAgentManager{

	@Autowired
	IAgentDao agentDao;
	
	@Autowired
	IFinanceDao financeDao;

	@Autowired
	ISetupDao setupDao;
	
	@Autowired
	IEmailDao emailDao;

	@Override
	public void saveAgent(AgentVO agent) {
		agentDao.saveAgent(agent);
	}

	@Override
	public void removeAgent(AgentVO agent) {
		agentDao.removeAgent(agent.getAgent_id());
	}

	@Override
	public void updateAgent(AgentVO agent) {
		agentDao.update(agent);
	}
	
	@Override
	public List<AgentVO> getAgents() {
		return agentDao.getAgents();
	}

	@Override
	public List<AgentVO> getSubAgents() {
		return agentDao.getSubAgents();
	}

	@Override
	public List<AgentVO> searchAll(String searchTerm) {
        if (searchTerm == null || "".equals(searchTerm.trim())) {
            return getAgents();
        }

        return agentDao.searchAll(searchTerm);
        
//		return super.search(searchTerm, AgentVO.class);
	}

	@Override
	public List<AgentVO> searchAgents(String searchTerm) {
        return agentDao.searchAgents(searchTerm);
	}

	@Override
	public List<AgentVO> searchSubAgents(String searchTerm) {
        return agentDao.searchSubAgents(searchTerm);
	}

	@Override
	public AgentVO getAgentByAgentId(Long id) {
		return id == null ? null : agentDao.getAgentByAgentId(id);
	}

	@Override
	public AgentVO getAgentByUserId(Long userId) {
		return userId == null ? null : agentDao.getAgentByUserId(userId);
	}

	@Override
	public AgentVO getAgentByUser(String userName) {
		return userName == null ? null : agentDao.getAgentByUser(userName);
	}

	@Override
	public AgentVO getAgentById(String uid) {
		return agentDao.getAgent(uid);
	}

	/*
	private String generateVAN(BankCode bankCode, Long userId) {
		
		VANGroupVO van = financeDao.getVANLast(bankCode);
		
		//logic copied from  Utils.incrementVAN(Integer counter1, Integer counter2, Integer counter3, Integer counter4)
		//dengan asumsi counter1 adalah default, tidak bisa diincrement lagi karena ketentuan dari bank
		//penggunaan "1" adalah trik utk 4 digit yg susunan 0XXX (depannya 0) supaya bisa ditambah 1
		BigDecimal d = new BigDecimal("1" + StringUtils.leftPad(van.getCounter2().toString(), 4, '0')
				+ StringUtils.leftPad(van.getCounter3().toString(), 4, '0')
				+ StringUtils.leftPad(van.getCounter4().toString(), 4, '0')
				
				);
		
		//increment
		d = d.add(new BigDecimal(1));
		
		//get full string
		String s = StringUtils.leftPad(van.getCounter1().toString(), 4, '0')
				+ d.toPlainString().substring(1);	//dipotong angka "1" paling depan
		
		//pecah lagi jadi empat
		String[] splitLine = new String[s.length()/4];
		for (int index = 0; index < splitLine.length; index++)
			splitLine[index] = s.substring(index*4,index*4 + 4);
		
		van.setCounter1(new Integer(splitLine[0]));
		van.setCounter2(new Integer(splitLine[1]));
		van.setCounter3(new Integer(splitLine[2]));
		van.setCounter4(new Integer(splitLine[3]));
		
		van.setUser_id(userId);
		van.setLastUpdate(new Date());
		agentDao.saveVAN(van);
		
		return s;
		
	}
	*/

	/*
	@Override
	public UserAccountVO generateTopUp(BankCode bank, TopUpPayment paymentMethod, String userName, BigDecimal amount) {
		
		//logikanya setiap agen sudah pasti punya virtual account
		UserAccountVO acc = financeDao.getUserAccount(userName);

		//utk kasus topup via atm, virtual account dpt dipakai utk topup berkali2, jd jangan generate VAN lagi
		
//		if (acc == null) {
			//create account
//			acc = new UserAccountVO();
			acc.setCurrency(Const.CURR_IDR);
			acc.setUser(agentDao.getUser(userName));

			acc.setBank_code(bank.getBankCode());
			acc.setAccountType(paymentMethod.getId());
			if (paymentMethod == TopUpPayment.TRANSFER_ATM){
//				acc.setNo(generateVAN(bank, acc.getUser().getId()));
			}else 
			if (paymentMethod == TopUpPayment.VIRTUAL_ACCOUNT){
//				acc.setNo(generateVAN(bank, acc.getUser().getId()));
			}

			acc.setTopup_amout(amount);
			acc.setTopup_status(TOPUP_STATUS.PENDING.getFlag());
			acc.setLastUpdate(new Date());

			financeDao.saveAccount(acc);

		//dont ever think to put into accounttrx, need confirm from bank first
		
		return acc;
	}

	public UserAccountVO generateTopUpOld(String userName, BigDecimal amount) {
		
		UserAccountVO acc = null; //financeDao.getUserAccount(userName);
		
		if (acc == null) {
			//create account
			acc = new UserAccountVO();
			acc.setCurrency(Const.CURR_IDR);
			acc.setUser(agentDao.getUser(userName));
			
			acc.setBank_code(BankCode.CIMBNIAGA.getBankCode());
			acc.setAccountType(Const.ACCOUNT_VIRTUAL);
			
			acc.setNo(generateVAN(BankCode.CIMBNIAGA, acc.getUser().getId()));
			
			acc.setTopup_amout(amount);
			acc.setTopup_status(TOPUP_STATUS.PENDING.getFlag());
			acc.setLastUpdate(new Date());
			
			financeDao.saveAccount(acc);
			
		}else {
			
			
		}
		
		//dont ever think to put into accounttrx, need confirm from bank first
		
		return acc;
	}
	*/

	@Override
	public AgentTrxVO getAgentAccountByUser(String userName) {
		return financeDao.getLastAgentTransaction(userName);
	}

	@Override
	public AgentTrxUsdVO getAgentAccountUsdByUser(String userName) {
		return financeDao.getAgentAccoutUsdByUser(userName);
	}

	@Override
	public List<Revenue> getReportRevenue(String dateFrom, String dateTo) throws Exception{
		
		//harusnya gabungan ga cuma flight aja
		Date from = Utils.fixDate(dateFrom);
		Date to = Utils.fixDate(dateTo);
		List<Revenue> list = new ArrayList<Revenue>();

		List<BookingFlightVO> flightRevenue = financeDao.getRevenueFlight(from, to);
		
		for (BookingFlightVO bookingFlightVO : flightRevenue) {
			Revenue revenue = new Revenue();

			revenue.setAirline(bookingFlightVO.getAirlines_iata());
			revenue.setBookingCode(bookingFlightVO.getCode());
			revenue.setDateIssued(bookingFlightVO.getIssuedDate());
			revenue.setCommFare(Rupiah.format(bookingFlightVO.getNtaCommission().doubleValue(), false));
			revenue.setCommInsurance(Rupiah.format(bookingFlightVO.getInsuranceCommission().doubleValue(), false));
			revenue.setFeeService(Rupiah.format(bookingFlightVO.getServiceFee().doubleValue(), false));
			revenue.setTotal(Rupiah.format(bookingFlightVO.getAmountNTAServiceInsurance().doubleValue(), false));

			list.add(revenue);
		}
		
		List<BookingHotelVO> hotelRevenue = financeDao.getRevenueHotel(from, to);
		for (BookingHotelVO bookingHotelVO : hotelRevenue){
			
		}

		return list;
		/*
		
		Revenue data1 = new Revenue();
		data1.setAirline("QG");
		data1.setBookingCode("J9GCQT");
		data1.setDateIssued("26-Nov-14");
		data1.setCommFare("19,000");
		data1.setCommInsurance("0");
		data1.setFeeService("0");
		data1.setTotal("19,000");
		
		list.add(data1);
		return list;*/
	}

	@Override
	public List<TopUpVO> getReportTopUp(String dateFrom, String dateTo, TOPUP_STATUS[] topUpStatus) throws Exception {
		Date from = Utils.fixDate(dateFrom);
		Date to = Utils.fixDate(dateTo);
		
		return financeDao.getTopUps(from, to, topUpStatus);
	}

	/**
	 * Only check if saldo above minimum
	 * @param userName
	 * @return
	 */
	@Override
	public boolean isPermitToBuy(String userName, Cart cart) {
		
		AgentTrxVO account = financeDao.getLastAgentTransaction(userName);
		
		//1. udah pernah topup ?
		if (account == null || account.getSaldo() == null) return false;

		//2. lebih besar dari deposit yg ditentukan ?
		double minimumDeposit = 100000;  
		String val = setupDao.getValue(ISetupDao.KEY_DEPOSIT);
		if (!StringUtils.isEmpty(val))
			minimumDeposit = Double.parseDouble(val);
		
		if (account.getSaldo().doubleValue() <= minimumDeposit) return false;
		
		//3.jumlahkan semua cart harus diatasnya deposit
		if (account.getSaldo().doubleValue() < cart.getAmount().doubleValue()) return false;
		if (account.getSaldo().compareTo(cart.getAmount()) == -1 ) return false;
		
		return true;
	}

	@Override
	public boolean isPermitToBuy(Agent agent) {
		return isPermitToBuy(agent.getUserName(), agent.getCart());
	}

	@Override
	public List<Whole_SalerVO> getWholeSalers() {
		return agentDao.getWholeSalers();
	}

	@Override
	public Long getWholeSalerAgentId(String uid) {
		return agentDao.getWholeSalerAgentId(uid);
	}

	@Override
	public void signUpSubAgent(User user, WHOLESALER wholeSaler) throws Exception{
		AgentVO agent = new AgentVO(user);
		
        //this form is about sub-agent, so user must have affiliate id
        AgentVO wholeSalerAgent = getAgentByAgentId(wholeSaler.getAgentId());
        
        agent.setAff_id(wholeSalerAgent.getId());
        
        //create virtual account
        UserAccountVO account = financeDao.createAccount(agent);

		BigDecimal firstTopUpAmount = financeDao.getFirstTopUpAmount(agent, wholeSaler);

		financeDao.topUp(BankCode.getCode(account.getBank_code()), TopUpPayment.VIRTUAL_ACCOUNT, user.getUsername(), firstTopUpAmount);
		
		saveAgent(agent);
		
		//email?
		
		/*
		TopUpVO topUp = new TopUpVO(account, financeDao, TopUpPayment.VIRTUAL_ACCOUNT, firstTopUpAmount);
		financeDao.saveTopUp(topUp);
		financeDao.newTopUp(account, TopUpPayment.VIRTUAL_ACCOUNT, firstTopUpAmount);
		*/

	}

	@Override
	public List<AppInfo> getAppInfo() {
		List<AppInfo> list = new ArrayList<AppInfo>();
		
		list.add(new AppInfo("Pending Registration", searchSubAgents(null, ACCOUNT_STATUS.PENDING, null).size()));
		list.add(new AppInfo("Current Wholesalers", getWholeSalers().size()));
		list.add(new AppInfo("Current Agencies", getAgents().size()));
		list.add(new AppInfo("Mandira Sub-Agents",-1));
		list.add(new AppInfo("Berjaya Sub-Agents", -1));
		list.add(new AppInfo("Inactive Sub-Agents", -1));
		list.add(new AppInfo("Most active Sub-Agents", -1));
		list.add(new AppInfo("Most Issued Sub-Agents", "Jack"));
		list.add(new AppInfo("Most Booked Sub-Agents", "Jenny"));
		list.add(new AppInfo("Most Canceled Sub-Agents", "Mark"));

		return list;
	}

	@Override
	public List<AgentVO> searchSubAgents(String searchTerm,
			ACCOUNT_STATUS accountStatus, Whole_SalerVO wholeSaler) {
		return agentDao.searchSubAgents(searchTerm, accountStatus, wholeSaler);
	}

	@Override
	public Whole_SalerVO getWholeSaler(Long userId) {
		return agentDao.getWholeSaler(userId);
	}

	@Override
	public Whole_SalerVO getWholeSalerByAgency(Long agent_id) {
		return agentDao.getWholeSalerByAgency(agent_id);
	}

}
