package com.big.web.b2b_big2.flight.decorator;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import com.big.web.b2b_big2.util.Utils;

public class VANColumnDecorator implements DisplaytagColumnDecorator {

	@Override
	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
		String value = (String)columnValue;
		
		return Utils.prettyVAN(value);

	}

}
