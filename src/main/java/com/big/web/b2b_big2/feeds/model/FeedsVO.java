package com.big.web.b2b_big2.feeds.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Indexed
@Entity
@Table(name="feeds")
public class FeedsVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4311334482459488484L;
	public static final int MAX_LEN_REMARKS = 450;
	
	private String id;
	private String title;
	private String remarks;
	private Date lastUpdate;
	private String headline;
	private String externalUrl;
	private String thumbId;
	
	public FeedsVO() {
		setId(java.util.UUID.randomUUID().toString());
	}
	
	@Id
	@Column(length=40)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Field
	@Column(length=100)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRemarks() {
		return remarks;
	}
	@Column(length = MAX_LEN_REMARKS)
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@XmlTransient
	@JsonIgnore
	@Column(length=1)
	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	@Column(length=50)
	public String getThumbId() {
		return thumbId;
	}

	public void setThumbId(String thumbId) {
		this.thumbId = thumbId;
	}

	@Column(length = 255)
	public String getExternalUrl() {
		return externalUrl;
	}

	public void setExternalUrl(String externalUrl) {
		this.externalUrl = externalUrl;
	}

	
}
