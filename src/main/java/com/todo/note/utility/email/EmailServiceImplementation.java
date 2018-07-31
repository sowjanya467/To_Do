package com.todo.note.utility.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.todo.note.userservice.model.MailModel;

/*************************************************************************************************************
 *
 * purpose:To send Email using JavaMailSEnder
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/

@Component
public class EmailServiceImplementation implements EmailService 
{

	@Autowired
	private JavaMailSender emailSender;

	

	@Override
	public void sendMail(MailModel mail) throws MessagingException {
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

		message.setTo(mail.getToMailAddress());
		message.setSubject(mail.getSubject());
		message.setText(mail.getBody());
		emailSender.send(mimeMessage);
	}

}