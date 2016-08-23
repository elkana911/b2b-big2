package com.big.web.b2b_big2.audit;

import javax.persistence.Entity;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@RevisionEntity(RevListener.class)
public class CustomRevInfoEntity extends DefaultRevisionEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6521835123071713636L;
	private String username;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
