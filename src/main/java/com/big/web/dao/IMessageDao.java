package com.big.web.dao;

import com.big.web.model.ErrorLogVO;

public interface IMessageDao {
	void saveError(ErrorLogVO error);
	
}
