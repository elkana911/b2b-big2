package com.big.web.b2b_big2.flight.decorator;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.displaytag.decorator.TableDecorator;

import com.big.web.b2b_big2.finance.bank.BankCode;
import com.big.web.b2b_big2.finance.bank.model.TopUpVO;

public class ReportTopUpDecorator extends TableDecorator {
	
	public String getRowNum() {
		 final StringBuilder out = new StringBuilder(8);
		    out.append((getListIndex() + 1))
		       .append('.');
		    return out.toString();

	}
	
	public String getBankName(){
		TopUpVO row = (TopUpVO)getCurrentRowObject();
		return BankCode.getCode(row.getBankCode()).getBankName();
	}
	
	protected String timeToString(Date time) {
		return new SimpleDateFormat("EEE HH:mm, dd-MMM-yyyy").format(time);	
	}	

}
