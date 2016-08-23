/**
 * 
 */
package com.big.web.b2b_big2.booking.exception;

public class IssuedException extends RuntimeException
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5304266842591879718L;
	private Throwable t;
    protected String strErrorCode;

    public IssuedException() {
    }

    public IssuedException(String s) {
        super(s);
    }

    public IssuedException(String s, String errorcode) {
        super(s);
        this.strErrorCode = errorcode;
    }

    public IssuedException(String s, String errorcode, Throwable t) {
        super(s);
        this.strErrorCode = errorcode;
        this.t = t;
    }

    public String getThrowable() {
        return ("Received throwable with Message: "+ t.getMessage());
    }

    public String getErrorCode() {
        return strErrorCode;
    }

    public void setErrorCode(String errorcode) {
        this.strErrorCode = errorcode;
    }
}
