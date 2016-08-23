package com.big.web.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="propinsi")
public class PropinsiVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2876100276488851778L;

	private String kodeBps;
	private String namaPropinsi;
	private String kodeIso;
	private String ibukotaPropinsi;
	private String pulau;
	
	@Id
	@Column(length=3)
	public String getKodeBps() {
		return kodeBps;
	}
	public void setKodeBps(String kodeBps) {
		this.kodeBps = kodeBps;
	}
	
	@Column(length=50)
	public String getNamaPropinsi() {
		return namaPropinsi;
	}
	public void setNamaPropinsi(String namaPropinsi) {
		this.namaPropinsi = namaPropinsi;
	}
	
	@Column(length=10)
	public String getKodeIso() {
		return kodeIso;
	}
	public void setKodeIso(String kodeIso) {
		this.kodeIso = kodeIso;
	}
	
	@Column(length=50)
	public String getIbukotaPropinsi() {
		return ibukotaPropinsi;
	}
	public void setIbukotaPropinsi(String ibukotaPropinsi) {
		this.ibukotaPropinsi = ibukotaPropinsi;
	}
	
	@Column(length=50)
	public String getPulau() {
		return pulau;
	}
	public void setPulau(String pulau) {
		this.pulau = pulau;
	}
	
}
