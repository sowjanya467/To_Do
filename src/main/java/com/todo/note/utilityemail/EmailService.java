package com.todo.note.utilityemail;

import javax.mail.MessagingException;


public interface EmailService 
{
	public void sendEmail(String to,String subject,String body) throws MessagingException, MessagingException; 

}