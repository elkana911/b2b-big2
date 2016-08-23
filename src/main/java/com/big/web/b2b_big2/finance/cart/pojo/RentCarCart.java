package com.big.web.b2b_big2.finance.cart.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class RentCarCart implements Serializable, ICart {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public BigDecimal getAmount() {
		return BigDecimal.ZERO;
	}

}
