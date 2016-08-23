/**
 * 
 */
package com.big.web.b2b_big2.booking.exception;

public class BookStatusException extends RuntimeException
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Throwable t;

    public BookStatusException() {
    }

    public BookStatusException(String s) {
        super(s);
    }

    public BookStatusException(String s, Throwable t) {
        super(s);
        this.t = t;
    }

    public String getThrowable() {
        return ("Received throwable with Message: "+ t.getMessage());
    }

}
