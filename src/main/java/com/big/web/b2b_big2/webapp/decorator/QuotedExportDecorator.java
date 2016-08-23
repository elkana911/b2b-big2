package com.big.web.b2b_big2.webapp.decorator;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.properties.MediaTypeEnum;

/**
 * When excel sees a value that looks like a number then it attempts to format it as a number.
 * This class fix by adding quotes the values when exporting to Excel
 * 
 * H2U:
 * belum tau dimana, sementara aq baru pake SearchHotelDecorator utk check medianya
 * @author 3r1c8
 *
 */
public class QuotedExportDecorator implements DisplaytagColumnDecorator {
	
	@Override
    public Object decorate(Object value, PageContext pageContext, MediaTypeEnum media) {
        if (media.equals(MediaTypeEnum.EXCEL) || media.equals(MediaTypeEnum.CSV)) {
            value = "=\"" + value + "\"";
        }
        return value;
    }
}