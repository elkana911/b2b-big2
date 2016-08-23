package com.big.web.b2b_big2.flight.decorator;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.displaytag.decorator.TableDecorator;
import org.displaytag.properties.MediaTypeEnum;

import com.big.web.b2b_big2.flight.pojo.FareInfo;
import com.big.web.b2b_big2.util.Utils;

public class FlightB2BItinerariesTableDecorator extends TableDecorator {

	protected String timeToString(Date time) {
		return new SimpleDateFormat("EEE HH:mm, dd-MMM-yyyy").format(time);	
	}	
	public String getFlightNo(){
		FareInfo row = (FareInfo)getCurrentRowObject();
		if (tableModel.getMedia() == MediaTypeEnum.HTML){
			 StringBuffer sb = new StringBuffer("<div class='flightNoCell'>");
			 sb.append(row.getFlightNo());
			 sb.append("</div>");
			 return sb.toString();
		}else{
			return row.getFlightNo();
		}
	}
	
	public String getClassName(){
		FareInfo row = (FareInfo)getCurrentRowObject();
		if (tableModel.getMedia() == MediaTypeEnum.HTML){
			 StringBuffer sb = new StringBuffer("<div class='classNameCell'>");
			 sb.append(Utils.parseSubClass(row.getClassName()));
			 sb.append("</div>");
			 return sb.toString();
		}else{
			return row.getClassName();
		}
	}
	
	public String getDepart(){
		FareInfo row = (FareInfo)getCurrentRowObject();
		if (tableModel.getMedia() == MediaTypeEnum.HTML){
			 StringBuffer sb = new StringBuffer("<div class=''>");
			 sb.append(row.getRoute().getFrom().getCity()).append(" (").append(row.getRoute().getFrom().getIataCode()).append(")");
			 sb.append("<br><span class='timeCell'>").append(timeToString(row.getRoute().getFrom().getSchedule())).append("</span>");
			 sb.append("</div>");
			 return sb.toString();
		}else{
			return row.getRoute().getFrom().getCity();
		}
	}
	
	public String getArrival(){
		FareInfo row = (FareInfo)getCurrentRowObject();
		if (tableModel.getMedia() == MediaTypeEnum.HTML){
			 StringBuffer sb = new StringBuffer("<div class=''>");
			 sb.append(row.getRoute().getTo().getCity()).append(" (").append(row.getRoute().getTo().getIataCode()).append(")");
			 sb.append("<br><span class='timeCell'>").append(timeToString(row.getRoute().getTo().getSchedule())).append("</span>");
			 sb.append("</div>");
			 return sb.toString();
		}else{
			return row.getRoute().getTo().getCity();
		}
	}


}
