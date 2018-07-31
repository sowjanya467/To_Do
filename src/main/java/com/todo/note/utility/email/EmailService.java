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
	

	void sendMail(MailModel mail) throws MessagingException;
}