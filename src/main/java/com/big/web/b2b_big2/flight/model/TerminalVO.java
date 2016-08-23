package com.big.web.b2b_big2.flight.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mst_terminals")
public class TerminalVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -458885104875853402L;
	public Integer terminal_id;
	public String terminal_Name;
	public String airport_id;
	
	
	@Id
	public Integer getTerminal_id() {
		return terminal_id;
	}
	public void setTerminal_id(Integer terminal_id) {
		this.terminal_id = terminal_id;
	}
	
	@Column(length=30)
	public String getTerminal_Name() {
		return terminal_Name;
	}
	public void setTerminal_Name(String terminal_Name) {
		this.terminal_Name = terminal_Name;
	}
	
	@Column(length=40)
	public String getAirport_id() {
		return airport_id;
	}
	public void setAirport_id(String airport_id) {
		this.airport_id = airport_id;
	}
	
	
}
