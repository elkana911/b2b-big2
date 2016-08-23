package com.big.web.b2b_big2.flight.api.kalstar.exception;

/**
 *
 */
public class SqivaException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SqivaException(Throwable ex) {
        super(ex);
    }

    public SqivaException(String message){
    	super(message);
    }
}
