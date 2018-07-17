package com.todo.note.utilityemail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class EmailServiceImplementation implements EmailService {

	 @Autowired
	    private JavaMailSender emailSender;
	 	@Override
	    public void sendEmail(String to,String subject,String body) throws MessagingException  {
	    
	    MimeMessage mimeMessage=emailSender.createMimeMessage();
	    MimeMessageHelper message=new MimeMessageHelper(mimeMessage);
	    
	        message.setTo(to);
	        message.setSubject(subject);
	        message.setText(body);
	        emailSender.send(mimeMessage);      
	    }

}