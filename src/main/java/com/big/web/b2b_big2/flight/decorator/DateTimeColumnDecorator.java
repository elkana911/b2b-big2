package com.big.web.b2b_big2.flight.decorator;

import java.util.Date;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import com.big.web.b2b_big2.util.Utils;

public class DateTimeColumnDecorator implements DisplaytagColumnDecorator {

	@Override
	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
		
		if (columnValue == null) return "";
		
		Date value = (Date)columnValue;
		
		return Utils.Date2String(value, "dd-MMM-yyyy HH:mm:ss");

	}

}
