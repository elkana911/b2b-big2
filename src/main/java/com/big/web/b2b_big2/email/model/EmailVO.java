package com.big.web.b2b_big2.email.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="email")
public class EmailVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2068553323070273425L;
	private String id;
	private String subject;
	private String recipient;
	private String template;
	private String status;
	private String remarks;
	private Date lastUpdate;
//	private String cache_id;	//isinya nama file yg berisi message body. <uuid>.txt
	private String cache_attachment1;	//formatnya: <folder cache_id>/nama file1.pdf
	private String cache_attachment2;	//formatnya: <folder cache_id>/nama_file2.pdf
	private String cache_attachment3;	//formatnya: <folder cache_id>/namafile3.pdf
	
	
	public EmailVO() {
		setId(java.util.UUID.randomUUID().toString());
	}
	
	@Id 
	@Column(length = 36)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Column(length = 70)
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Column(length = 50)
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	@Column(length = 30)
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}

	@Column(length = 1)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Column(length = 20)
	public String getCache_attachment1() {
		return cache_attachment1;
	}
	public void setCache_attachment1(String cache_attachment1) {
		this.cache_attachment1 = cache_attachment1;
	}
	@Column(length = 20)
	public String getCache_attachment2() {
		return cache_attachment2;
	}
	public void setCache_attachment2(String cache_attachment2) {
		this.cache_attachment2 = cache_attachment2;
	}
	@Column(length = 20)
	public String getCache_attachment3() {
		return cache_attachment3;
	}
	public void setCache_attachment3(String cache_attachment3) {
		this.cache_attachment3 = cache_attachment3;
	}
	@Column(length = 60)
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
