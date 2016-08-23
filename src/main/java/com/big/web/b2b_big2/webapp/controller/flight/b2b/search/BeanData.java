package com.big.web.b2b_big2.webapp.controller.flight.b2b.search;

import java.io.Serializable;

public class BeanData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5053625663452419020L;
	
	private String show;
	private String name;
	private String episode;
	private String airDate;
	public String getShow() {
		return show;
	}
	public void setShow(String show) {
		this.show = show;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEpisode() {
		return episode;
	}
	public void setEpisode(String episode) {
		this.episode = episode;
	}
	public String getAirDate() {
		return airDate;
	}
	public void setAirDate(String airDate) {
		this.airDate = airDate;
	}
	@Override
	public String toString() {
		return "BeanData [show=" + show + ", episode=" + episode + ", airDate="
				+ airDate + "]";
	}
	
	
}
