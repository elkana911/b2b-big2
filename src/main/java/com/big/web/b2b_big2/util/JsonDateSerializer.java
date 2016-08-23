package com.big.web.b2b_big2.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

/**
 * Nganggur untuk sementara. sebelumnya dipakai utk mengatasi format date yg bentuknya malah milliseconds saat ditarik oleh ajax
 * @author Administrator
 *
 */
public class JsonDateSerializer extends JsonSerializer<Date> {
//	Locale locale = LocaleContextHolder.getLocale();
//	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
/*
	private MessageSourceAccessor messages;

	@Autowired
    public void setMessages(MessageSource messageSource) {
        messages = new MessageSourceAccessor(messageSource);
    }
*/	
	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		String formattedDate = dateFormat.format(date);
	
//		String s = messages.getMessage("date.format", locale);
		gen.writeString(formattedDate);
	}
	
}
