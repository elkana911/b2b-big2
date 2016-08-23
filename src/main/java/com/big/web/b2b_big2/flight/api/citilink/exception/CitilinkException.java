package com.big.web.b2b_big2.flight.api.citilink.exception;

/**
 *
 */
public class CitilinkException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CitilinkException(Throwable ex) {
        super(ex);
    }

    public CitilinkException(String message){
    	super(message);
    }
}
