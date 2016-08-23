package com.big.web.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.big.web.dao.IMessageDao;
import com.big.web.hibernate.BasicHibernate;
import com.big.web.model.ErrorLogVO;

@Repository("messageDao")
public class MessageDaoHibernate extends BasicHibernate implements IMessageDao {

	@Override
	public void saveError(ErrorLogVO error) {
		getSession().save(error);
	}

}
