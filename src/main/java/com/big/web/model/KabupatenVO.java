package com.big.web.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="kabupaten")
public class KabupatenVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 25478068748154642L;

	private Integer id;
	private String kodeBps;
	private String namakotaKab;
	private String jenis;
	
	@Id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(length=3)
	public String getKodeBps() {
		return kodeBps;
	}
	public void setKodeBps(String kodeBps) {
		this.kodeBps = kodeBps;
	}
	
	@Column(length=50)
	public String getNamakotaKab() {
		return namakotaKab;
	}
	public void setNamakotaKab(String namakotaKab) {
		this.namakotaKab = namakotaKab;
	}
	
	@Column(length=5)
	public String getJenis() {
		return jenis;
	}
	public void setJenis(String jenis) {
		this.jenis = jenis;
	}
	
}
