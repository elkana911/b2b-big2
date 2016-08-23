/**
 * 
 */
package com.big.web.b2b_big2.finance.exception;

public class CartEmptyException extends RuntimeException
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1334509666780338751L;
	private Throwable t;
    protected String strErrorCode;

    public CartEmptyException(String accountNo) {
        super(accountNo);
    }

    public CartEmptyException(String accountNo, String errorcode) {
        super(accountNo);
        this.strErrorCode = errorcode;
    }

    public CartEmptyException(String accountNo, String errorcode, Throwable t) {
        super(accountNo);
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
