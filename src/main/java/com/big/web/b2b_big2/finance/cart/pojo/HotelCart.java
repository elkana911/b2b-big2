package com.big.web.b2b_big2.finance.cart.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class HotelCart implements Serializable, ICart{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9222137293443783428L;

	@Override
	public BigDecimal getAmount() {
		return BigDecimal.ZERO;
	}

}
