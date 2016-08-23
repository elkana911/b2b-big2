package com.big.web.b2b_big2.scraping.exception;

/**
 *
 */
public class InvalidRadioIdException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidRadioIdException(Throwable ex) {
        super(ex);
    }

    public InvalidRadioIdException(String message){
    	super(message);
    }
}
