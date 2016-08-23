package com.big.web.b2b_big2.mgmt.dao;

import com.big.web.b2b_big2.mgmt.model.TaskVO;

public interface ITaskDao {
	void save(TaskVO obj);
	void saveOrUpdate(TaskVO obj);
}
