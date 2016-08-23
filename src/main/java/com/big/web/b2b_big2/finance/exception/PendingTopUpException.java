package com.big.web.b2b_big2.finance.exception;

import com.big.web.b2b_big2.finance.bank.model.TopUpVO;

public class PendingTopUpException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7063527634260424358L;
	private TopUpVO data;

    public PendingTopUpException() {
    }

    public PendingTopUpException(String s, TopUpVO data) {
        super(s);
        this.data= data;
    }

	public TopUpVO getData() {
		return data;
	}

	public void setData(TopUpVO data) {
		this.data = data;
	}

    
}
