package com.big.web.b2b_big2.webapp.util;

/**
 * Awalnya kupakai buat quick contact lov
 * @author Administrator
 *
 */
public class LovSimple {
	private String uid;
	private String title;
	
	public LovSimple(String uid, String title) {
		super();
		this.uid = uid;
		this.title = title;
	}

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "LovSimple [uid=" + uid + ", title=" + title + "]";
	}
	
	
}
