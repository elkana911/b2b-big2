package com.big.web.b2b_big2.flight.decorator;

import java.math.BigDecimal;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import com.big.web.b2b_big2.util.Rupiah;

public class CurrencyColumnDecorator implements DisplaytagColumnDecorator {

	@Override
	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
		double value = ((BigDecimal)columnValue).doubleValue();
		
		return Rupiah.format(value, false);

	}

}
