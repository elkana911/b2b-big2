package com.big.web.b2b_big2.agent.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="user_pref")
public class UserPrefVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private Long user_id;
	private Integer theme;	//theme id. 0/null berarti default.. mungkin perlu me-refer pada table CSS
	private String locale;	//defaultnya en_US. saat ini baru support en_US dan in_ID
	private Date last_update;
	
	
	
	public UserPrefVO() {
    	setId(java.util.UUID.randomUUID().toString());
	}
	
	@Id
	@XmlTransient
	@JsonIgnore
	@Column(length=40, nullable = false)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Integer getTheme() {
		return theme;
	}
	public void setTheme(Integer theme) {
		this.theme = theme;
	}

	@Column(length=10)	
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public Date getLast_update() {
		return last_update;
	}
	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}
	
	
}
