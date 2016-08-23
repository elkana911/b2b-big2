/**
 * 
 */
package com.big.web.b2b_big2.flight.api.citilink.exception;

public class CitilinkServerException extends RuntimeException
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1334509666780338751L;
	private Throwable t;
    protected String errorCode;
    
    public CitilinkServerException(String msg){
    	super(msg);
    }

    public CitilinkServerException(String errorCode, String msg) {
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
