package com.big.web.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.big.web.message.PROCESS_TYPE;

@Entity
@Table(name="error_log")
public class ErrorLogVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1634153562965749297L;
	
	private String id;
	private Date createddate;
	private PROCESS_TYPE process_type;
	private String message;
	private String client_ip;
	private String user;
	private String remarks;
	
	public ErrorLogVO() {
		setId(java.util.UUID.randomUUID().toString());
	}
	
	@Id
	@Column(length = 40)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	@Enumerated(EnumType.STRING)
	@Column(length = 50)
	public PROCESS_TYPE getProcess_type() {
		return process_type;
	}
	public void setProcess_type(PROCESS_TYPE process_type) {
		this.process_type = process_type;
	}
	
	@Column(length = 255)
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Column(length = 20)
	public String getClient_ip() {
		return client_ip;
	}
	
	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}

	@Column(length = 50, name = "user_id")
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Column(length = 500)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "ErrorLogVO [id=" + id + ", createddate=" + createddate
				+ ", process_type=" + process_type + ", message=" + message
				+ ", client_ip=" + client_ip + ", user=" + user + ", remarks="
				+ remarks + "]";
	}

	
}
