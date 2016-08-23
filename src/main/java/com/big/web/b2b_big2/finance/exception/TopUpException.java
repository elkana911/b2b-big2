/**
 * 
 */
package com.big.web.b2b_big2.finance.exception;

public class TopUpException extends RuntimeException
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5304266842591879718L;
	private Throwable t;
    protected String strErrorCode;

    public TopUpException() {
    }

    public TopUpException(String s) {
        super(s);
    }

    public TopUpException(String s, String errorcode) {
        super(s);
        this.strErrorCode = errorcode;
    }

    public TopUpException(String s, String errorcode, Throwable t) {
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
