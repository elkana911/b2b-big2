package com.big.web.ws.quote.bean;

import javax.xml.bind.annotation.XmlElement;

public class SQuote {
	private String symbol;
	private String last;
	private String date;
	private String time;
	private String change;
	private String open;
	private String high;
	private String low;
	private String volume;
	private String mktCap;
	private String previousClose;
	private String percentageChange;
	private String annRange;
	private String earns;
	private String pe;
	private String name;
	public String getSymbol() {
		return symbol;
	}
	
	@XmlElement(name="Symbol")
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getLast() {
		return last;
	}
	
	@XmlElement(name="Last")
	public void setLast(String last) {
		this.last = last;
	}
	public String getDate() {
		return date;
	}
	
	@XmlElement(name="Date")
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	
	@XmlElement(name="Time")
	public void setTime(String time) {
		this.time = time;
	}
	public String getChange() {
		return change;
	}
	
	@XmlElement(name="Change")
	public void setChange(String change) {
		this.change = change;
	}
	public String getOpen() {
		return open;
	}
	@XmlElement(name="Open")
	public void setOpen(String open) {
		this.open = open;
	}
	public String getHigh() {
		return high;
	}
	@XmlElement(name="High")
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLow() {
		return low;
	}
	@XmlElement(name="Low")
	public void setLow(String low) {
		this.low = low;
	}
	public String getVolume() {
		return volume;
	}
	@XmlElement(name="Volume")
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getMktCap() {
		return mktCap;
	}
	@XmlElement(name="MktCap")
	public void setMktCap(String mktCap) {
		this.mktCap = mktCap;
	}
	public String getPreviousClose() {
		return previousClose;
	}
	@XmlElement(name="PreviousClose")
	public void setPreviousClose(String previousClose) {
		this.previousClose = previousClose;
	}
	public String getPercentageChange() {
		return percentageChange;
	}
	@XmlElement(name="PercentageChange")
	public void setPercentageChange(String percentageChange) {
		this.percentageChange = percentageChange;
	}
	public String getAnnRange() {
		return annRange;
	}
	@XmlElement(name="AnnRange")
	public void setAnnRange(String annRange) {
		this.annRange = annRange;
	}
	public String getEarns() {
		return earns;
	}
	@XmlElement(name="Earns")
	public void setEarns(String earns) {
		this.earns = earns;
	}
	public String getPe() {
		return pe;
	}
	@XmlElement(name="P-E")
	public void setPe(String pe) {
		this.pe = pe;
	}
	public String getName() {
		return name;
	}
	@XmlElement(name="Name")
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SQuote [symbol=" + symbol + ", last=" + last + ", date=" + date + ", time=" + time + ", change="
				+ change + ", open=" + open + ", high=" + high + ", low=" + low + ", volume=" + volume + ", mktCap="
				+ mktCap + ", previousClose=" + previousClose + ", percentageChange=" + percentageChange
				+ ", annRange=" + annRange + ", earns=" + earns + ", pe=" + pe + ", name=" + name + "]";
	}
	
	
}
