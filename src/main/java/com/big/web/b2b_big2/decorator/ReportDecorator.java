package com.big.web.b2b_big2.decorator;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.displaytag.decorator.TableDecorator;

public class ReportDecorator extends TableDecorator {
	
	public String getRowNum() {
		 final StringBuilder out = new StringBuilder(8);
		    out.append((getListIndex() + 1))
		       .append('.');
		    return out.toString();

	}
	
	protected String timeToString(Date time) {
		return new SimpleDateFormat("EEE HH:mm, dd-MMM-yyyy").format(time);	
	}	

}
