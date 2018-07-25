package com.todo.note.utility.rabbitmq;

import javax.mail.MessagingException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.note.userservice.model.MailModel;
import com.todo.note.utility.email.EmailService;

@Service
public class Consumer 
{
	@Autowired
	EmailService emailService;
	
	@RabbitListener(queues="${jsa.rabbitmq.queue}")
	public void reciveMsg(MailModel mail) throws MessagingException
	{
		
//		System.out.println(mail);
		emailService.sendMail(mail);
	}

}
