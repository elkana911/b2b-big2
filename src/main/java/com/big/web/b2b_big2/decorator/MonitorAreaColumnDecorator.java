package com.big.web.b2b_big2.decorator;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

import com.big.web.b2b_big2.util.Utils;
import com.big.web.location.IServerLocation;
import com.big.web.location.ServerLocation;
import com.big.web.location.ServerLocationImpl;

public class MonitorAreaColumnDecorator implements DisplaytagColumnDecorator{
	
	
	@Override
	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
		String value = (String)columnValue;
//		String value = "202.62.17.48";
		
		ServerLocation location = null;
		
		if (Utils.isEmpty(value) || value.equals("127.0.0.1") || value.equals("localhost")) {}
		else
		try {
			IServerLocation serverLocation = new ServerLocationImpl();
			location = serverLocation.getLocation(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return location == null ? value : 
			new StringBuffer(location.getCity())
					.append(" / ")
					.append(location.getRegion())
					.append("<span class='flag flag-").append(location.getCountry().toLowerCase()).append("'")
					.append("/>")
					;

	}
}
