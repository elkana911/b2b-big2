package com.big.web.b2b_big2.flight.decorator;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

public class BookStatusColumnDecorator implements DisplaytagColumnDecorator {

	@Override
	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
		String value = (String)columnValue;
		
		return value;
		/*
		 * logic dibawah kusimpan sampai lulus simulasi
		String label = "";
		if (value.indexOf(",") > 0){
			String[] bf = value.split(",");
			String lastS = bf[0].trim();
			
			label = lastS;
			for (int i = 1; i < bf.length; i++){
				if (!lastS.equalsIgnoreCase(bf[i].trim())){
					label = value;
					break;
				}
			}
		}
		else
			label = value;
		
		if (label.equalsIgnoreCase("xx"))
			label = "Canceled";
		else if (label.equalsIgnoreCase("ok"))
			label = "Issued";
		
		return label;
		*/
	}

}
