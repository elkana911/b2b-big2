package com.big.web.b2b_big2.mgmt.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="tasks")
public class TaskVO implements Serializable{;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2074630762903738698L;

	private String id;
	private Integer task_id; //refer MST_TASKS
	private Long user_id;
	private Integer approval_role;
	private String status;
	private Date created_date;
	
	
	public TaskVO() {
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
	public Integer getTask_id() {
		return task_id;
	}
	public void setTask_id(Integer task_id) {
		this.task_id = task_id;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Integer getApproval_role() {
		return approval_role;
	}
	public void setApproval_role(Integer approval_role) {
		this.approval_role = approval_role;
	}
	
	@Column(length = 1)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	
	
}
