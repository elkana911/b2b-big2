package com.big.web.b2b_big2.finance.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.big.web.b2b_big2.agent.dao.IAgentDao;
import com.big.web.b2b_big2.finance.bank.BankCode;
import com.big.web.b2b_big2.finance.bank.TopUpPayment;
import com.big.web.b2b_big2.finance.bank.model.BankVO;
import com.big.web.b2b_big2.finance.bank.model.PaymentWayVO;
import com.big.web.b2b_big2.finance.bank.model.TopUpVO;
import com.big.web.b2b_big2.finance.dao.IFinanceDao;
import com.big.web.b2b_big2.finance.model.CommissionVO;
import com.big.web.b2b_big2.finance.service.IFinanceManager;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.AppInfo;
import com.big.web.b2b_big2.util.Rupiah;
import com.big.web.model.User;
import com.big.web.model.UserAccountVO;

@Service("financeManager")
@Transactional
public class FinanceManagerImpl implements IFinanceManager{

	@Autowired
	ISetupDao setupDao;
	
	@Autowired
	IAgentDao agentDao;
	
	@Autowired
	IFinanceDao financeDao;
	
	@Override
	public List<PaymentWayVO> getPaymentMethods() {
		return financeDao.getPaymentMethods();
	}

	@Override
	public List<BankVO> getBanks() {
		return financeDao.getBanks();
	}

	@Override
	public List<BankVO> getBankSupportATM() {
		return financeDao.getBankSupportATM();
	}

	@Override
	public List<BankVO> getBankSupportVirtualAccount() {
		return financeDao.getBankSupportVirtualAccount();
	}

	@Override
	public BankVO getBankByCode(String bankCode) {
		return financeDao.getBank(bankCode);
	}

	@Override
	public UserAccountVO getUserAccount(String userName) {
		return financeDao.getUserAccount(userName);
	}

	@Override
	public UserAccountVO getUserAccountById(String id) {
		return financeDao.getUserAccountById(id);
	}

	@Override
	public UserAccountVO getUserAccount(String userName, String currIdr) {
		return financeDao.getUserAccount(userName, currIdr);
	}

	@Override
	public UserAccountVO getUserAccountByAccountNo(String vaNumber) {
		return financeDao.getUserAccountByAccoutNo(vaNumber);
	}

	@Override
	public User getUserByAccountNo(String vaNumber) {
		return financeDao.getUserByAccountNo(vaNumber);
	}
	
	@Override
	public User getUserByAccountID(String id) {
		return financeDao.getUserByAccountId(id);
	}


	@Override
	public TopUpVO topUp(BankCode bank, TopUpPayment paymentMethod, String userName, BigDecimal amount) {
		return financeDao.topUp(bank, paymentMethod, userName, amount);
	}

	@Override
	public List<AppInfo> getAppInfo() {
		List<AppInfo> list = new ArrayList<AppInfo>();
		list.add(new AppInfo("Issued Tickets", Rupiah.format(0, true)));
		list.add(new AppInfo("Canceled Tickets", Rupiah.format(0, true)));
//		list.add(new AppInfo("new accounts", financeDao.getAccounts(from, to)));
//		list.add(new AppInfo("Pending TopUps", financeDao.getTopUps(new Date(), new Date(), TOPUP_STATUS.PENDING)));
		
		return list;
	}

	@Override
	public List<TopUpVO> getTopUps(String bankCode, String status, Long agency) {
		
		List<TopUpVO> list = financeDao.getTopUps(bankCode, status, agency);
		
		return list;
	}

	@Override
	public List<TopUpVO> getTopUps(Long userId) {
		List<TopUpVO> list = financeDao.getTopUps(userId);
		return list;
	}

	@Override
	public BigDecimal getCommission(BigDecimal rate, Airline airline, FareInfo fareInfo) {
		return financeDao.getCommission(rate, airline, fareInfo); 
	}

	@Override
	public BigDecimal getNetCommission(BigDecimal rate, BigDecimal issuedFee,
			Airline airline, FareInfo fareInfo) {
		return financeDao.getNetCommission(rate, issuedFee, airline, fareInfo);
	}

	@Override
	public CommissionVO getCommission(Airline airline, FareInfo fareInfo) {
		return financeDao.getCommission(airline, fareInfo);
	}


	/* moved into financeDao
	private String generateVAN(BankCode bankCode, User user) {
		
		//cari tipe user/consumen dulu utk di kategorikan
		//defaultnya pad 4 digit. tp utk berleha akan menggunakan 3 digit saja. 
		//fungsi ini hanya akan mengatur 01 dan 02 saja karena utk agen	
		String binCode1234 = "0000";// "1019"
		String clientCode56 = "02";	
		String countryCode = user.getAddress().getCountry();
		
		String regionCode789 = StringUtils.leftPad(Phone.getRegionFromPhone(user.getPhoneNumber(), countryCode), 3, '0');
		
		BankVO bank = financeDao.getBank(bankCode.getBankCode());
		binCode1234 = Utils.isEmpty(bank.getVan()) ? binCode1234 : bank.getVan();

		//harus ada pembedanya saat signup. ada di user.getAgent.getAgent_id().
		//user.getAgent.getAff_id() tidak dpt dipakai di fungsi ini krn blm keisi. fyi affid dipakai untuk mengacu sub-agent terdaftar dengan agent yang mana, dgn menggunakan table yg sama MST_AGENTS
		//mengapa di table MST_AGENTS semua nilai AFFID null ? krn dulunya AFFID diset kalau pakai SSO aja.
		Long agentId = user.getAgent().getAgent_id();
		AgentVO wholeSalerAgentVO = agentDao.getAgent(user.getAgent().getAff_id());
		
		clientCode56 = agentDao.getWholeSalerVA_Code(agentId);

		if (clientCode56 == null) throw new NullPointerException("ClientCode56 should not null");
		
		CIMBVANCounterVO counter = financeDao.getLastCounterFromCIMB(clientCode56, regionCode789);
		
		BigDecimal newNumber = new BigDecimal(1);
		if (counter == null){
			counter = new CIMBVANCounterVO();
			counter.setClientCode(clientCode56);
			counter.setRegionCode(regionCode789);
		}else{
			boolean found = true;			
			//cari sampai available
			while (found){
				newNumber = newNumber.add(counter.getCounter());
			
				StringBuffer sb = new StringBuffer(StringUtils.leftPad(binCode1234, 4, '0'));
				sb.append(clientCode56)
					.append(regionCode789)
					.append(StringUtils.leftPad(counter.getCounter().toPlainString(), 7, '0'))
				;
				
				//cek dulu dengan userAccount apakah sudah terpakai ?
				found = financeDao.isAccountExists(sb.toString()) != null;
			}
			
		}

		counter.setCounter(newNumber);
		counter.setLastUpdate(new Date());
		counter.setLastUpdateBy(user.getId());
		agentDao.saveVAN(counter);
			
    	//get full string
    	String s = StringUtils.leftPad(binCode1234, 4, '0')
        		+ clientCode56 
        		+ regionCode789 
        		+ StringUtils.leftPad(counter.getCounter().toPlainString(), 7, '0');
		
    	return s;

	}

	/*
	@Override
	public UserAccountVO createVirtualAccount(AgentVO agent, WHOLESALER wholeSaler) {
		
		BigDecimal mandiraFirstTopUp = setupDao.getValueAsBigDecimal(ISetupDao.KEY_MANDIRA_TOPUP_FIRST);
		
		UserAccountVO acc = new UserAccountVO();
		acc.setCurrency(Const.CURR_IDR);
		acc.setUser(agent.getUserLogin()
		acc.setBank_code(BankCode.CIMBNIAGA.getBankCode());
//		acc.setAccountType(Const.ACCOUNT_VIRTUAL);
		acc.setNo(generateVAN(BankCode.CIMBNIAGA, acc.getUser()));
//		acc.setTopup_status(TOPUP_STATUS.PENDING.getFlag());

		acc.setStatus(ACCOUNT_STATUS.PENDING.getFlag());
		acc.setLastUpdate(new Date());

		financeDao.saveAccount(acc);
		
		BigDecimal firstTopUpAmount;
		
		if (wholeSaler == WHOLESALER.MANDIRA_TRAVEL){
			firstTopUpAmount = mandiraFirstTopUp;
		}else{
			if (agent.getPackage_code() == 101){
				firstTopUpAmount = new BigDecimal(1000000);
			}else if (agent.getPackage_code() == 102){
				firstTopUpAmount = new BigDecimal(5000000);
			}else if (agent.getPackage_code() == 103){
				firstTopUpAmount = new BigDecimal(10000000);
			}else		
				firstTopUpAmount = new BigDecimal(1000000);
		}
		
		TopUpVO topUp = new TopUpVO(acc, financeDao, TopUpPayment.VIRTUAL_ACCOUNT, firstTopUpAmount);
		financeDao.saveTopUp(topUp);

		return acc;

	}
	*/
}
