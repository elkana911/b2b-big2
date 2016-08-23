package com.big.web.b2b_big2.email.dao;

import com.big.web.b2b_big2.email.model.EmailVO;

public interface IEmailDao {
	String getEmailCacheDir();

	void save(EmailVO obj);
	void saveOrUpdate(EmailVO obj);

}
