/**
 * 
 */
package com.big.web.b2b_big2.finance.exception;

import com.big.web.model.UserAccountVO;

public class TopUpIncompleteRegException extends RuntimeException
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5304266842591879718L;
	private Throwable t;
	private UserAccountVO account;
    protected String strErrorCode;

    public TopUpIncompleteRegException() {
    }

    public TopUpIncompleteRegException(UserAccountVO account, String s) {
        super(s);
        this.account = account;
    }

    public TopUpIncompleteRegException(UserAccountVO account,String s, String errorcode) {
        super(s);
        this.strErrorCode = errorcode;
        this.account = account;
    }

    public TopUpIncompleteRegException(UserAccountVO account,String s, String errorcode, Throwable t) {
        super(s);
        this.strErrorCode = errorcode;
        this.t = t;
        this.account = account;
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

	public UserAccountVO getAccount() {
		return account;
	}

	public void setAccount(UserAccountVO account) {
		this.account = account;
	}
    
}
