package com.big.web.b2b_big2.email.dao.hibernate;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.big.web.b2b_big2.email.dao.IEmailDao;
import com.big.web.b2b_big2.email.model.EmailVO;
import com.big.web.hibernate.BasicHibernate;

@Repository("emailDao")
public class EmailDaoHibernate extends BasicHibernate implements IEmailDao{

	@Override
	public void save(EmailVO obj) {
		getSession().save(obj);		
	}

	@Override
	public void saveOrUpdate(EmailVO obj) {
		getSession().saveOrUpdate(obj);		
		
	}

	@Override
	public String getEmailCacheDir() {
        SQLQuery setupQ = getSession().createSQLQuery("select value from setup where name='email.cache'");
        
        if (setupQ.list().size() > 0)
        	return (String)setupQ.list().get(0);
        else
        	return null;
	}

}
