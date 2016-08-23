package com.big.web.b2b_big2.finance.dao.hibernate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.big.web.b2b_big2.agent.dao.IAgentDao;
import com.big.web.b2b_big2.agent.model.AccTrxDtlVO;
import com.big.web.b2b_big2.agent.model.AgentTrxUsdVO;
import com.big.web.b2b_big2.agent.model.AgentTrxVO;
import com.big.web.b2b_big2.agent.model.AgentVO;
import com.big.web.b2b_big2.agent.pojo.ACCOUNT_STATUS;
import com.big.web.b2b_big2.agent.pojo.WHOLESALER;
import com.big.web.b2b_big2.booking.model.BookingFlightVO;
import com.big.web.b2b_big2.booking.model.BookingHotelVO;
import com.big.web.b2b_big2.finance.bank.BankCode;
import com.big.web.b2b_big2.finance.bank.TOPUP_STATUS;
import com.big.web.b2b_big2.finance.bank.TopUpPayment;
import com.big.web.b2b_big2.finance.bank.model.BankVO;
import com.big.web.b2b_big2.finance.bank.model.CIMBVANCounterVO;
import com.big.web.b2b_big2.finance.bank.model.PaymentWayVO;
import com.big.web.b2b_big2.finance.bank.model.TopUpCounterVO;
import com.big.web.b2b_big2.finance.bank.model.TopUpVO;
import com.big.web.b2b_big2.finance.dao.IFinanceDao;
import com.big.web.b2b_big2.finance.exception.AccountCreditException;
import com.big.web.b2b_big2.finance.exception.PendingTopUpException;
import com.big.web.b2b_big2.finance.exception.TopUpException;
import com.big.web.b2b_big2.finance.model.CommissionRangeVO;
import com.big.web.b2b_big2.finance.model.CommissionVO;
import com.big.web.b2b_big2.flight.Airline;
import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.flight.pojo.ROUTE_MODE;
import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.Currency;
import com.big.web.b2b_big2.util.Phone;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.hibernate.BasicHibernate;
import com.big.web.model.User;
import com.big.web.model.UserAccountVO;

@Repository("financeDao")
public class FinanceDaoHibernate extends BasicHibernate implements IFinanceDao {

	@Autowired
	ISetupDao setupDao;

	@Autowired
	IAgentDao agentDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public AgentTrxVO getLastAgentTransaction(String userName) {
		if (userName == null) return null;

		SQLQuery q = getSession().createSQLQuery("select a.* from agent_trx a where a.account_id=(select id from user_account where user_id=(select id from app_user where lower(username)=lower(:username))) order by createddate desc");
		q.addEntity("a", AgentTrxVO.class);
		q.setParameter("username", userName);
				
		for (Iterator iterator = q.list().iterator(); iterator.hasNext();) {
			AgentTrxVO obj = (AgentTrxVO) iterator.next();
			
			return obj;
		}
		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public AgentTrxUsdVO getAgentAccoutUsdByUser(String userName) {
		if (userName == null) return null;

		SQLQuery q = getSession().createSQLQuery("select a.* from agent_trx_usd a where a.account_id=(select id from user_account where user_id=(select id from app_user where lower(username)=lower(:username))) order by createddate desc");
		q.addEntity("a", AgentTrxUsdVO.class);
		q.setParameter("username", userName);
		
		for (Iterator iterator = q.list().iterator(); iterator.hasNext();) {
			AgentTrxUsdVO obj = (AgentTrxUsdVO) iterator.next();
			
			return obj;
		}
		
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public AgentTrxVO getAgentAccoutIdrByUser(String userName) {
		if (userName == null) return null;

		SQLQuery q = getSession().createSQLQuery("select a.* from agent_trx a where a.account_id=(select id from user_account where user_id=(select id from app_user where lower(username)=lower(:username))) order by createddate desc");
		q.addEntity("a", AgentTrxVO.class);
		q.setParameter("username", userName);
		
		for (Iterator iterator = q.list().iterator(); iterator.hasNext();) {
			AgentTrxVO obj = (AgentTrxVO) iterator.next();
			
			return obj;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BankVO getBank(String bankCode) {
		Criteria c = getSession().createCriteria(BankVO.class);
		
		c.add(Restrictions.eq("code", bankCode));

		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			return (BankVO) iterator.next();
		}
		return null;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankVO> getBanks() {
		Criteria c = getSession().createCriteria(BankVO.class);
		
		c.addOrder(Order.asc("name"));
		
		return c.list();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public UserAccountVO getUserAccount(String userName) {
		return getUserAccount(userName, Currency.CURR_IDR);
	}

	@Override
	public UserAccountVO getUserAccountById(String id) {
		Criteria c = getSession().createCriteria(UserAccountVO.class);
		
		c.add(Restrictions.eq("id", id));

		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			return (UserAccountVO) iterator.next();
		}
		return null;
	}

	@Override
	public UserAccountVO getUserAccount(String userName, String currIdr) {
		Criteria c = getSession().createCriteria(UserAccountVO.class);
		
		c.createAlias("user", "usr");
		
		c.add(Restrictions.eq("usr.username", userName));
		
		if (currIdr == null) currIdr = Currency.CURR_IDR;
		
		c.add(Restrictions.eq("currency", currIdr.toUpperCase()));

		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			return (UserAccountVO) iterator.next();
		}
		return null;
	}


	@Override
	public UserAccountVO getUserAccountByAccoutNo(String vaNumber) {
		Criteria c = getSession().createCriteria(UserAccountVO.class);
		
		c.add(Restrictions.eq("no", vaNumber));
		
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			return (UserAccountVO) iterator.next();
		}
		return null;
	}

	@Override
	public User getUserByAccountNo(String vaNumber) {
		Criteria c = getSession().createCriteria(UserAccountVO.class);
		
		c.createAlias("user", "usr");
		
		c.add(Restrictions.eq("no", vaNumber));

		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			UserAccountVO acc = (UserAccountVO) iterator.next();
			
			return acc.getUser();
		}
		return null;
	}


	@Override
	public User getUserByAccountId(String id) {
		Criteria c = getSession().createCriteria(UserAccountVO.class);
		
		c.createAlias("user", "usr");
		
		c.add(Restrictions.eq("id", id));

		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			UserAccountVO acc = (UserAccountVO) iterator.next();
			
			return acc.getUser();
		}
		return null;
	}


	/*
	@Override
	public VANGroupVO getVANLast(BankCode bankCode) {
		Criteria c = getSession().createCriteria(VANGroupVO.class);
		
		c.add(Restrictions.eq("bankCode", bankCode.getBankCode()));

		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			return (VANGroupVO) iterator.next();
		}
		return null;
	}
*/
	@Override
	public void saveAccount(UserAccountVO obj) {
		getSession().saveOrUpdate(obj);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public CIMBVANCounterVO getLastCounterFromCIMB(String clientCode56, String regionCode789) {
		Criteria c = getSession().createCriteria(CIMBVANCounterVO.class);
		
		c.add(Restrictions.eq("clientCode", clientCode56));
		c.add(Restrictions.eq("regionCode", regionCode789));

		c.addOrder(Order.desc("counter"));

		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			return (CIMBVANCounterVO) iterator.next();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public UserAccountVO isAccountExists(String no) {
		Criteria c = getSession().createCriteria(UserAccountVO.class);
		
		c.add(Restrictions.eq("no", no));
		
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			return (UserAccountVO) iterator.next();
		}
		return null;
	}

	@Override
	public AgentTrxVO debetAccount(String userName, BigDecimal debetAmount) {
		AgentTrxVO lastTrx = getLastAgentTransaction(userName);
		
		if (lastTrx == null){
			throw new RuntimeException("Please Topup First !");
		}
		
		BigDecimal newSaldo = lastTrx == null ? debetAmount : lastTrx.getSaldo().subtract(debetAmount);
		
		//5.2 insert new data, calculate saldo
		AgentTrxVO newTrx = new AgentTrxVO();
		newTrx.setAccount(getUserAccount(userName));
		
		newTrx.setDebet(debetAmount);
		newTrx.setCredit(BigDecimal.ZERO);
		newTrx.setSaldo(newSaldo);
		newTrx.setCreatedDate(new Date());
		
		getSession().saveOrUpdate(newTrx);
		
		return newTrx;
	}

	@Override
	public AgentTrxVO creditAccount(String userName, BigDecimal creditAmount) throws AccountCreditException{
		AgentTrxVO lastTrx = getLastAgentTransaction(userName);
		
		if (lastTrx == null){
			throw new AccountCreditException("Please Topup First !");
		}
		
		BigDecimal newSaldo = lastTrx == null ? creditAmount : lastTrx.getSaldo().add(creditAmount);
		
		//5.2 insert new data, calculate saldo
		AgentTrxVO newTrx = new AgentTrxVO();
		newTrx.setAccount(getUserAccount(userName));
		
		newTrx.setDebet(BigDecimal.ZERO);
		newTrx.setCredit(creditAmount);
		newTrx.setSaldo(newSaldo);
		newTrx.setCreatedDate(new Date());
		
		getSession().saveOrUpdate(newTrx);
		
		return newTrx;
	}

	@Override
	public AccTrxDtlVO creditAccountTrx(String userName, BigDecimal creditAmount, String currency) {
		
		UserAccountVO ua = getUserAccount(userName, currency);
		
		BigDecimal lastBalance = ua.getBalance() == null ? BigDecimal.ZERO : ua.getBalance();
		
		BigDecimal newBalance = lastBalance.add(creditAmount);
		
		//5.2 insert new data, calculate saldo
		AccTrxDtlVO new_Trx = new AccTrxDtlVO();
		new_Trx.setAccount(getUserAccount(userName));
		new_Trx.setBalance(newBalance);
		new_Trx.setBankTransDate(null);
		new_Trx.setCreatedDate(new Date());
		new_Trx.setCredit(creditAmount);
		new_Trx.setDebet(BigDecimal.ZERO);
		new_Trx.setRemarks(null);

		getSession().saveOrUpdate(new_Trx);
		
		return new_Trx;
	}

	@Override
	public AccTrxDtlVO debetAccountTrx(String userName, BigDecimal debetAmount, String currency, String bookingFlightId) {
		/*
		waktu beli tiket, maka engine:
			1. kurangi balance dengan credit (useraccount.balance-credit) di table useraccount,
			2. insert debet ke acctrxdtl, dengan balance diperoleh dari proses 1
	*/
		UserAccountVO ua = getUserAccount(userName, currency);
		
		BigDecimal lastBalance = ua.getBalance() == null ? BigDecimal.ZERO : ua.getBalance();
		
		BigDecimal newBalance = lastBalance.subtract(debetAmount);
		//TODO harusnya bisa wait lock record. 
		//ua update first soalnya dipakai juga oleh mas andri
		ua.setBalance(newBalance);
		getSession().saveOrUpdate(ua);
		
		//5.2 insert new data, calculate saldo
		AccTrxDtlVO new_Trx = new AccTrxDtlVO();
		new_Trx.setAccount(getUserAccount(userName));
		new_Trx.setBalance(newBalance);
		new_Trx.setBankTransDate(null);
		new_Trx.setCreatedDate(new Date());
		new_Trx.setCredit(debetAmount);
		new_Trx.setDebet(BigDecimal.ZERO);
		new_Trx.setRemarks(null);
		new_Trx.setRef_bookingFlight(bookingFlightId);

		getSession().saveOrUpdate(new_Trx);
		
		return new_Trx;
	}


	
	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentWayVO> getPaymentMethods() {
		Criteria c = getSession().createCriteria(PaymentWayVO.class);
		
		c.add(Restrictions.isNull("disabled"));
		c.addOrder(Order.asc("orderId"));
		
		return c.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankVO> getBankSupportATM() {
		SQLQuery q = getSession().createSQLQuery("select a.* from mst_bank a where a.atmsupport = 'Y'");
		q.addEntity("a", BankVO.class);
		
		return q.list();
		/*
		for (Iterator iterator = q.list().iterator(); iterator.hasNext();) {
			AgentTrxVO obj = (AgentTrxVO) iterator.next();
			
			return obj;
		}

		Criteria c = getSession().createCriteria(BankVO.class);
		
		c.add(Restrictions.eq("atmSupport", "Y"));

		return c.list();
		*/		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankVO> getBankSupportVirtualAccount() {
		SQLQuery q = getSession().createSQLQuery("select a.* from mst_bank a where a.vasupport = 'Y'");
		q.addEntity("a", BankVO.class);
		
		return q.list();
	}

	@Override
	public void saveTopUp(TopUpVO topup) {
		getSession().saveOrUpdate(topup);
	}

	@Override
	public TopUpVO getPendingTopUp(UserAccountVO acc, boolean cleanUp){
		
		if (cleanUp){
			SQLQuery q1 = getSession().createSQLQuery("update topup set status = :status where sysdate >= timetopay");
			q1.setParameter("status", TOPUP_STATUS.EXPIRED.getFlag());
			q1.executeUpdate();
		}
		
		SQLQuery q2 = getSession().createSQLQuery("select a.* from topup a where a.account_no = :accountNo and a.status = :status and sysdate < a.timetopay");
		q2.addEntity(TopUpVO.class);
		q2.setParameter("accountNo", acc.getNo());
		q2.setParameter("status", TOPUP_STATUS.PENDING.getFlag());
		
		if (q2.list().size() > 0)		
			return (TopUpVO) q2.list().get(0);
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TopUpVO> getTopUps(Date from, Date to, TOPUP_STATUS[] topUpStatus) {
		Criteria c = getSession().createCriteria(TopUpVO.class);

		if(from!=null){
			c.add(Restrictions.ge("lastUpdate", from)); 
		}
		if (to != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(to);
			cal.add(Calendar.DATE, 1); 
			
			c.add(Restrictions.lt("lastUpdate", cal.getTime()));
			
		}
		
		if (!Utils.isEmpty(topUpStatus)){
			
			String[] _topUps = new String[topUpStatus.length];
			for (int i = 0; i < _topUps.length; i++) {
				_topUps[i] = topUpStatus[i].getFlag();
			}
			
			c.add(Restrictions.in("status", _topUps));
		}
//		c.add(Restrictions.between("createdDate", from, to));	JANGAN PAKE BETWEEN

		return c.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TopUpVO> getTopUps(String bankCode, String status, Long agency) {

		StringBuffer sb = new StringBuffer("select a.* from topup a inner join mst_agents b on a.user_id = b.user_id where 1=1");
		
		if (agency != null)
			sb.append(" and b.aff_id=(select id from mst_agents where agent_id = :agencyId)");
		
		if (!Utils.isEmpty(bankCode))
			sb.append(" and a.bankcode=:bankCode");

		if (!Utils.isEmpty(status))
			sb.append(" and a.status=:status");
		
		SQLQuery q = getSession().createSQLQuery(sb.toString());
		q.addEntity(TopUpVO.class);
		
		if (agency != null)
			q.setParameter("agencyId", agency);

		if (!Utils.isEmpty(bankCode))
			q.setParameter("bankCode", bankCode);

		if (!Utils.isEmpty(status))
			q.setParameter("status", status);
		
//		if (userId != null)
//			c.add(Restrictions.eq("user_id", userId));
		
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TopUpVO> getTopUps(Long userId) {
//		SQLQuery q = getSession().createSQLQuery("select a.* from topup a inner join mst_agents b on a.user_id = b.user_id where a.user_id=:userId");
		
		Criteria c = getSession().createCriteria(TopUpVO.class);
		
		if (userId != null)
			c.add(Restrictions.eq("userId", userId));
		
		return c.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BookingFlightVO> getRevenueFlight(Date from, Date to){
		Criteria c = getSession().createCriteria(BookingFlightVO.class);
		
		return c.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookingHotelVO> getRevenueHotel(Date from, Date to){
		Criteria c = getSession().createCriteria(BookingHotelVO.class);
		
		return c.list();
	}


	/**
	 * increment and automatically update table TOPUP_COUNTER everyday. The number will RESET to 1 on the next day.
	 * <p>To get the incremented number use {@link TopUpCounterVO#getLastCounter()} 
	 * 
	 * @param account nullable. just add-on info. not mandatory for any queries.
	 * @return
	 */
	/*
	 * Tujuannya utk menaikkan angka increment yg terakhir terdaftar dimulai dari hari ini.
	 * Pada table TOPUP_COUNTER akan terlihat dalam satu hari ada berapa counter terakhir.
	 * 
	 * (non-Javadoc)
	 * @see com.big.web.b2b_big2.finance.bank.dao.IFinanceDao#incrementLastCounterTopUp(com.big.web.model.UserAccountVO)
	 */
	private TopUpCounterVO incrementLastCounterTopUp(UserAccountVO account) {

		//ambil data terakhir
		SQLQuery q = getSession().createSQLQuery("select a.* from topup_counter a where to_char(a.lastdate, 'rrrrmmdd') = to_char(sysdate, 'rrrrmmdd')");
		q.addEntity(TopUpCounterVO.class);
		
		//jika ada, ambil nilai 
		if (q.list().size() > 0){
			
			TopUpCounterVO obj =  (TopUpCounterVO) q.list().get(0);
			Long lastCounter = obj.getLastCounter() +1;

			obj.setLastCounter(lastCounter.longValue());
			
			getSession().saveOrUpdate(obj);
			
			return obj;
		}else{
			//buat baru
			
			TopUpCounterVO obj = new TopUpCounterVO();
			obj.setLastAccount(account.getNo());
			obj.setLastCounter(new Long(1));
			obj.setLastDate(new Date());
//			obj.setLastUpdate(new Date());	buat apa ya ?
			
			getSession().save(obj);
			
			return obj;
			
		}

	}

	@Override
	public boolean isTopUpExists(String transCode) {
		Criteria c = getSession().createCriteria(TopUpVO.class);

		c.add(Restrictions.eq("trans_code", transCode).ignoreCase()); 

		return c.list().size() > 0;
	}

	@Override
	public String generateTransactionCode() {
		
		int length = 5;
		
		String val = setupDao.getValue(ISetupDao.KEY_TRANS_CODE_LENGTH);
		if (!Utils.isNumeric(val))
			length = Integer.parseInt(val);

		String _transCode = "";
		
		
		while (true){
			_transCode = Utils.generateUniqueCode(length);
			if (!isTopUpExists(_transCode)){
				break;
			}
		}
		
		return _transCode;
		
	}

	@Override
	public long generateCounterTopUp(UserAccountVO acc) {
		TopUpCounterVO counterTopUpVO = incrementLastCounterTopUp(acc);
		
		long lastUniqueCode = counterTopUpVO.getLastCounter();
		long offsetCounter = setupDao.getValueAsLong(ISetupDao.KEY_TOPUP_ATM_COUNTER_OFFSET);	//100
		long moneyCounter = offsetCounter + lastUniqueCode; //112 or 12341
		return moneyCounter;
	}

	/**
	 * 
	 * @param bankCode
	 * @return 16 digits: 1234567890123456
	 * <p>cara baca:
	 * <br>1234 adalah kode BIN (Bank Identification Number) dari bank
	 * <br>56 adalah tipe consumen: 01 mandira, 02 wholesale, 03 b2c and 00 berleha not handled yet
	 * <br>789 adalah kode wilayah based on phonenumber. contoh: 
		     0214567828=021
		     +62214567828=021
		     08778567828=877
		     021-4567828=021
	 * <br>0123456 increment. jadi utk b2c maksimal pemakai 9.999.999 saja, jadi harus direlease/reset utk digunakan kembali.
	 */
	private String generateVAN(BankCode bankCode, User user) {
		
		//cari tipe user/consumen dulu utk di kategorikan
		//defaultnya pad 4 digit. tp utk berleha akan menggunakan 3 digit saja. 
		//fungsi ini hanya akan mengatur 01 dan 02 saja karena utk agen	
		String binCode1234 = "0000";// "1019"
		String clientCode56 = "02";	
		String countryCode = user.getAddress().getCountry();
		
		String regionCode789 = StringUtils.leftPad(Phone.getRegionFromPhone(user.getPhoneNumber(), countryCode), 3, '0');
		
		BankVO bank = getBank(bankCode.getBankCode());
		binCode1234 = Utils.isEmpty(bank.getVan()) ? binCode1234 : bank.getVan();

		//harus ada pembedanya saat signup. ada di user.getAgent.getAgent_id().
		//user.getAgent.getAff_id() tidak dpt dipakai di fungsi ini krn blm keisi. fyi affid dipakai untuk mengacu sub-agent terdaftar dengan agent yang mana, dgn menggunakan table yg sama MST_AGENTS
		//mengapa di table MST_AGENTS semua nilai AFFID null ? krn dulunya AFFID diset kalau pakai SSO aja.
		Long agentId = user.getAgent().getAgent_id();
		AgentVO wholeSalerAgentVO = agentDao.getAgent(user.getAgent().getAff_id());
		
		clientCode56 = agentDao.getWholeSalerVA_Code(agentId);

		if (clientCode56 == null) throw new NullPointerException("ClientCode56 should not null");
		
		CIMBVANCounterVO counter = getLastCounterFromCIMB(clientCode56, regionCode789);
		
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
				found = isAccountExists(sb.toString()) != null;
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

	@Override
	public UserAccountVO createAccount(AgentVO agent) {
		
		UserAccountVO acc = new UserAccountVO();
		acc.setCurrency(Currency.CURR_IDR);
		acc.setUser(agent.getUserLogin()/* agentDao.getUser(username) user*/);
		acc.setBank_code(BankCode.CIMBNIAGA.getBankCode());
//		acc.setAccountType(Const.ACCOUNT_VIRTUAL);
		acc.setNo(generateVAN(BankCode.CIMBNIAGA, acc.getUser()));
//		acc.setTopup_status(TOPUP_STATUS.PENDING.getFlag());

		acc.setStatus(ACCOUNT_STATUS.PENDING.getFlag());//should be activated once payment is made
		acc.setLastUpdate(new Date());

		saveAccount(acc);

		return acc;

	}

	@Override
	public TopUpVO topUp(BankCode bank, TopUpPayment paymentMethod, String userName, BigDecimal amount) {
		//logikanya setiap agen sudah pasti punya virtual account
		UserAccountVO acc = getUserAccount(userName);
		
		//any pending payment ?
		TopUpVO pendingTopUp = getPendingTopUp(acc, false);
		if (pendingTopUp != null)
			throw new PendingTopUpException("", pendingTopUp);

		//utk b2b, virtual account dpt dipakai utk topup berkali2, jd jangan generate VAN lagi
		TopUpVO topup = new TopUpVO();
		topup.setAccount_no(acc.getNo());
		topup.setUser_id(acc.getUser().getId());
		topup.setPayment_type(Long.parseLong(String.valueOf(paymentMethod.getId())));
		topup.setStatus(TOPUP_STATUS.PENDING.getFlag());
		topup.setBankCode(bank.getBankCode());
		topup.setAmount(amount);

		topup.setTrans_code( generateTransactionCode());

		long uniqueCode = generateCounterTopUp(acc);
		/*
		TopUpCounterVO counterTopUpVO = financeDao.incrementLastCounterTopUp(acc);
		
		long lastUniqueCode = counterTopUpVO.getLastCounter();
		long offsetCounter = setupDao.getValueAsLong(ISetupDao.KEY_TOPUP_ATM_COUNTER_OFFSET);	//100
		long moneyCounter = offsetCounter + lastUniqueCode;
		*/
		topup.setUniqueCode(new Long(uniqueCode));

		BigDecimal amountToPaid = BigDecimal.ZERO;
		
		amountToPaid = amountToPaid.add(amount);
		
		if (paymentMethod == TopUpPayment.TRANSFER_ATM){
			amountToPaid = amountToPaid.add(new BigDecimal(uniqueCode));
		}
		
//		amountToPaid = amountToPaid.add(amount)
//				.add(new BigDecimal(uniqueCode))
//				;
		
		topup.setAmountToPaid(amountToPaid);
		
		topup.setLastUpdate(new Date());

		topup.setTimeToPay(Utils.addHours(topup.getLastUpdate(), 2));
			
		saveTopUp(topup);
		
		return topup;

	}

	@Override
	public BigDecimal getFirstTopUpAmount(AgentVO agent, WHOLESALER wholeSaler) {
		BigDecimal mandiraFirstTopUp = setupDao.getValueAsBigDecimal(ISetupDao.KEY_MANDIRA_TOPUP_FIRST);

		if (mandiraFirstTopUp.doubleValue() < 1)
			throw new TopUpException("First TopUp amount to small ! (" + mandiraFirstTopUp.intValue() + ")");
		
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

		return firstTopUpAmount;
	}

	@Override
	public BigDecimal getCommission(BigDecimal rate, Airline airline, FareInfo fareInfo) {
		CommissionVO commVO = getCommission(airline, fareInfo);
		
		BigDecimal value = commVO == null ? BigDecimal.ZERO : commVO.getComm_rate();
		
		return rate.multiply(value);
	}

	@Override
	public BigDecimal getCommission(BigDecimal rate, Airline airline, String originIata, String destinationIata, int tripMode, String flightNo, Character seatClass) {
		CommissionVO commVO = getCommission(airline, originIata, destinationIata, tripMode, flightNo, seatClass);
		
		BigDecimal value = commVO == null ? BigDecimal.ZERO : commVO.getComm_rate();
		
		return rate.multiply(value);
	}
	
	@Override
	public BigDecimal getNetCommission(BigDecimal rate, BigDecimal issuedFee, Airline airline, FareInfo fareInfo) {
		
		BigDecimal _comm = getCommission(rate, airline, fareInfo);
		
		return _comm.subtract(issuedFee).setScale(0, RoundingMode.HALF_UP);
	}
	@Override
	public BigDecimal getNetCommission(BigDecimal rate, BigDecimal issuedFee, Airline airline, String originIata, String destinationIata, int tripMode, String flightNo, Character seatClass) {
		
		BigDecimal _comm = getCommission(rate, airline, originIata, destinationIata, tripMode, flightNo, seatClass);
		
		return _comm.subtract(issuedFee).setScale(0, RoundingMode.HALF_UP);
	}

	@Override
	public CommissionVO getCommission(Airline airline, FareInfo fareInfo) {
		return getCommission(airline
							, fareInfo.getRoute().getFrom().getIataCode()
							, fareInfo.getRoute().getTo().getIataCode()
							, fareInfo.getRouteMode() == ROUTE_MODE.RETURN ? 1 : 0
							, fareInfo.getFlightNo()
							,  fareInfo.getClassName().charAt(0));
	}

	@Override
	public CommissionVO getCommission(Airline airline, String originIata, String destinationIata, int tripMode, String flightNo, Character seatClass) {
		Criteria c = getSession().createCriteria(CommissionVO.class);
		
		c.add(Restrictions.eq("airline_code", airline.getAirlineCode()));
		
		CommissionVO commVO = null;
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			commVO = (CommissionVO) iterator.next(); 
			break;
		}
		
		if (commVO == null) return null;
		
		//harus dicek dulu ada juga kah di commission_range ?
		BigDecimal commRangeRate = getCommissionRangeRate(airline, originIata, destinationIata, tripMode, flightNo, seatClass);
		
		// jika ada update commissionnya
		if (commRangeRate != null){
			commVO.setComm_rate(commRangeRate);
		}
		
		return commVO;
	}

	@Override
	public List<CommissionRangeVO> getCommissionRange(Airline airline){
		Criteria c = getSession().createCriteria(CommissionRangeVO.class);
		
		c.add(Restrictions.eq("airline_code", airline.getAirlineCode()));
		
		return c.list();
	}

	@Override
	public CommissionRangeVO getCommissionRange(Airline airline, String originIata, String destinationIata, int tripMode, String flightNo){
		Criteria c = getSession().createCriteria(CommissionRangeVO.class);
		
		c.add(Restrictions.eq("airline_code", airline.getAirlineCode()));
		c.add(Restrictions.eq("origin", originIata));
		c.add(Restrictions.eq("destination", destinationIata));
		c.add(Restrictions.eq("returnTrip", tripMode));
		c.add(Restrictions.eq("flightNum", flightNo));
		
		for (Iterator iterator = c.list().iterator(); iterator.hasNext();) {
			return (CommissionRangeVO) iterator.next();
		}
		
		return null;
		
	}
	
	@Override
	public BigDecimal getCommissionRangeRate(Airline airline, String originIata, String destinationIata, int tripMode, String flightNo, Character seatClass){
		StringBuffer sql = new StringBuffer("select " + seatClass + " from (");
		sql.append("select * from ").append(CommissionRangeVO.tblName).append(" where airline_code=:airline_code");
		sql.append(" and origin = :origin and destination = :destination");
		sql.append(" and return = :return and flightnum = :flightnum");
		sql.append(")");

		SQLQuery sqlQuery = getSession().createSQLQuery(sql.toString());
		sqlQuery.setParameter("airline_code", airline.getAirlineCode());
		sqlQuery.setParameter("origin", originIata);
		sqlQuery.setParameter("destination", destinationIata);
		sqlQuery.setParameter("return", tripMode);
		sqlQuery.setParameter("flightnum", flightNo);
		
		for (Iterator iterator = sqlQuery.list().iterator(); iterator.hasNext();) {
			return (BigDecimal) iterator.next();
		}		
		
		return null;
		
	}

	/*
	@Override
	public BigDecimal getLastSaldo(Agent agent) {
		
		
		SQLQuery q = getSession().createSQLQuery("select a.* from agent_trx a where account_id = (select id from user_account where user_id= :userid)");

		
		SQLQuery q = getSession().createSQLQuery("select a.* from agent_trx_usd a where a.account_id=(select id from user_account where user_id=(select id from app_user where lower(username)=lower(:username)))");
		
		q.addEntity("a", AgentTrxUsdVO.class);
		q.setParameter("username", userName);
		
		for (Iterator iterator = q.list().iterator(); iterator.hasNext();) {
			AgentTrxUsdVO obj = (AgentTrxUsdVO) iterator.next();
			
			return obj;
		}

		Criteria c = getSession().createCriteria(AgentTrxVO.class);
		
		c.add(Restrictions.eq("account.id", value));
		
		
		return null;
	}
	*/

}
