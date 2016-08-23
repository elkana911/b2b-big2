/**
 * 
 */
package com.big.web.b2b_big2.flight.api.kalstar.exception;

import com.big.web.b2b_big2.flight.api.kalstar.action.BasicResponse;

public class SqivaServerException extends RuntimeException
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1334509666780338751L;
	private Throwable t;
    protected String errorCode;

    public SqivaServerException(BasicResponse resp){
    	this(resp.getErrCode(), resp.getErrMsg());
    }
    
    public SqivaServerException(String msg){
    	super(msg);
    }

    public SqivaServerException(String errorCode, String msg) {
    	super(msg);
    	this.errorCode = errorCode;
    	
    }

    public String getThrowable() {
        return ("Received throwable with Message: "+ t.getMessage());
    }

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

    
}
