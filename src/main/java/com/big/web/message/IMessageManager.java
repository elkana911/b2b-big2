package com.big.web.message;

import javax.servlet.http.HttpServletRequest;

import com.big.web.model.ErrorLogVO;

public interface IMessageManager {
	void saveError(ErrorLogVO error);
	
	void saveError(String msg, PROCESS_TYPE processType);
	void saveError(String msg, PROCESS_TYPE processType, HttpServletRequest request, Exception exception);
	
}
