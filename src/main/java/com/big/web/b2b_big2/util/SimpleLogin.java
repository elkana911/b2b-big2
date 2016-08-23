package com.big.web.b2b_big2.util;

import java.io.Serializable;

/**
 * sebelumnya dipakai untuk supply username/password utk validasi beberapa proses penting.
 * Tapi untuk saat ini cukup diminta password saja, jd bean ini belum berguna
 * @author Administrator
 *
 */
public class SimpleLogin implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String password;
	
	public SimpleLogin() {
		super();
	}
	public SimpleLogin(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
