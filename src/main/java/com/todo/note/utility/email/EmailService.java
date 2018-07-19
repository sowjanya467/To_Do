package com.todo.note.utility.email;

import javax.mail.MessagingException;

import com.todo.note.userservice.model.MailModel;

/*************************************************************************************************************
 *
 * purpose:Email Service
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/

public interface EmailService {
	
	public void sendEmail(String to, String subject, String body) throws MessagingException, MessagingException;

	void sendMail(MailModel mail) throws MessagingException;
}