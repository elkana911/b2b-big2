package com.big.web.b2b_big2.mgmt.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.big.web.b2b_big2.mgmt.dao.ITaskDao;
import com.big.web.b2b_big2.mgmt.model.TaskVO;
import com.big.web.hibernate.BasicHibernate;

@Repository("taskDao")
public class TaskDaoHibernate extends BasicHibernate implements ITaskDao{

	@Override
	public void save(TaskVO obj) {
		getSession().save(obj);
	}

	@Override
	public void saveOrUpdate(TaskVO obj) {
		getSession().saveOrUpdate(obj);
	}

}
