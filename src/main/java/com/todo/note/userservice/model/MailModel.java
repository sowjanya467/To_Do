package com.todo.note.userservice.model;

import org.springframework.stereotype.Service;

@Service
public class MailModel 
{
	private String toMailAddress;

	private String subject;


	private String body;

	private String mailSign;

	public String getToMailAddress() {
		return toMailAddress;
	}

	public String setToMailAddress(String toMailAddress) {
		return this.toMailAddress = toMailAddress;
	}

	public String getSubject() {
		return subject;
	}

	public String setSubject(String subject) {
		return this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public String setBody(String body) {
		return this.body = body;
	}

	public String getMailSign() {
		return mailSign;
	}

	public void setMailSign(String mailSign) {
		this.mailSign = mailSign;
	}


}
