package com.big.web.b2b_big2.email;

public class EmailSimple {
	private String recipient;
	private String message;
	
	
	public EmailSimple(String recipient, String message) {
		super();
		this.recipient = recipient;
		this.message = message;
	}
	
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "EmailSimple [recipient=" + recipient + ", message=" + message + "]";
	}
	
	
}
