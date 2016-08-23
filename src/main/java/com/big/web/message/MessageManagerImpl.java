package com.big.web.message;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.big.web.b2b_big2.agent.pojo.Agent;
import com.big.web.b2b_big2.util.Const;
import com.big.web.dao.IMessageDao;
import com.big.web.model.ErrorLogVO;

@Service("messageManager")
@Transactional
public class MessageManagerImpl implements IMessageManager{

	@Autowired IMessageDao messageDao;

	@Override
	public void saveError(ErrorLogVO error) {
		error.setCreateddate(new Date());
		messageDao.saveError(error);		
	}

	@Override
	public void saveError(String msg, PROCESS_TYPE processType, HttpServletRequest request, Exception exception) {
		ErrorLogVO error = new ErrorLogVO();
		error.setMessage(msg);
		error.setProcess_type(processType);
		
		if (request != null){
			error.setClient_ip(request.getRemoteAddr());
			error.setUser(request.getRemoteUser());
		}
		if (exception != null){
			// ambil aja error yg depannya com
			for (StackTraceElement ste : exception.getStackTrace()){
				if (ste.getClassName().startsWith(Const.BASE_PACKAGE)){
					error.setRemarks(ste.getClassName() + "." + ste.getMethodName() + "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")");
					break;
				}
			}
			
		}
		
		saveError(error);
	}

	@Override
	public void saveError(String msg, PROCESS_TYPE processType) {
		ErrorLogVO error = new ErrorLogVO();
		error.setMessage(msg);
		error.setProcess_type(processType);
		
		saveError(error);
	}
	
	
	
}
