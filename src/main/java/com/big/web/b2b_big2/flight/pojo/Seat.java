package com.big.web.b2b_big2.flight.pojo;

import java.io.Serializable;

public class Seat implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7735952545762119311L;
	private double rate;
	private String kelas;
	private int available;	// -1 N/A, 0 habis
	
	public Seat(){
		
	}
	
	public Seat(double rate, String kelas, int available) {
		this.rate = rate;
		this.kelas = kelas;
		this.available = available;
	}
	
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getKelas() {
		return kelas;
	}
	public void setKelas(String kelas) {
		this.kelas = kelas;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "Seat [rate=" + rate + ", kelas=" + kelas + ", available=" + available + "]";
	}
	
}
