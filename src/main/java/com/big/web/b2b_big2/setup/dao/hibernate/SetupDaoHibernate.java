package com.big.web.b2b_big2.setup.dao.hibernate;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.big.web.b2b_big2.setup.dao.ISetupDao;
import com.big.web.b2b_big2.util.Utils;
import com.big.web.hibernate.BasicHibernate;

@Repository("setupDao")
@Transactional
public class SetupDaoHibernate extends BasicHibernate implements ISetupDao {

	@Override
	public String getValue(String key) {
		SQLQuery q = getSession().createSQLQuery("select value from setup where lower(name)= lower(:key)");

		q.setParameter("key", key.trim());
		
		if (q.list().size() > 0)
			return (String)q.list().get(0);

		return null;
	}

	@Override
	public boolean isForbidUser(String userName) {
		if (StringUtils.isEmpty(userName)) return true;
		
		SQLQuery q = getSession().createSQLQuery("select name from mst_forbid_user where lower(name) = lower(:name)");

		q.setParameter("name", userName);

		return q.list().size() > 0;

	}

	@Override
	public long getValueAsLong(String key) {
		String val = getValue(key);
		
		return Long.parseLong( val == null ? "0" : val);
	}

	@Override
	public int getValueAsInt(String key) {
		String val = getValue(key);
		
		return Integer.parseInt(val == null ? "0" : val);
	}

	@Override
	public boolean getValueAsBoolean(String key) {
		String val = getValue(key);
		
		if (Utils.isEmpty(val)) return false;
		
		val = val.trim();
		return val.equals("1") 
				|| val.equalsIgnoreCase("true")
				|| val.equalsIgnoreCase("yes")
				;
	}

	@Override
	public BigDecimal getValueAsBigDecimal(String key) {
		String val = getValue(key);
		
		if (Utils.isEmpty(val)) return BigDecimal.ZERO;
		
		return new BigDecimal(val);
	}

	@Override
	public long getCountFromTable(String fromTable, String where) {
		StringBuffer sb = new StringBuffer("select count(1) from " + fromTable);
		
		if (!Utils.isEmpty(where))
			sb.append(" where " + where);
		
		SQLQuery q = getSession().createSQLQuery(sb.toString());
		
		BigDecimal d = (BigDecimal)q.list().get(0);
		return d.longValue();
	}

	@Override
	public boolean checkToken3rdParty(String token, String ip) {
		
		int minutesTimeout = getValueAsInt(ISetupDao.KEY_TOKEN_TIMEOUT);
		
		StringBuffer sb = new StringBuffer("select key, to_char(created_date, 'yyyymmddhh24miss') from TOKEN where key='" + token + "'");

		SQLQuery q = getSession().createSQLQuery(sb.toString());
		
		if (q.list().size() < 1) return false;
		
		Object[] row = (Object[]) q.list().get(0);
		
		String key = (String)row[0];
		String date = (String)row[1];
		
		Date createdDate = Utils.String2Date(date, "yyyyMMddHHmmss");
		
		Date sessionToEnd = Utils.addMinutes(createdDate, minutesTimeout);
		
		if (sessionToEnd.before(new Date())){
			return false;
		}
		
		return true;
	}

	@Override
	public String generateToken3rdParty(String ip) {
		String key = java.util.UUID.randomUUID().toString();
		
		String sql = "insert into token (key, created_date, ip) values (:key, sysdate, :ip)";
		
		SQLQuery q = getSession().createSQLQuery(sql);
		q.setParameter("key", key);
		q.setParameter("ip", ip);
		
		q.executeUpdate();
		
		return key;
	}


}
