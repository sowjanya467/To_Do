package com.todo.note.utility.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.todo.note.userservice.model.MailModel;

@Service
public class ProducerImplementation implements Producer {

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Value("${jsa.rabbitmq.exchange}")
	private String exchange;

	@Value("${jsa.rabbitmq.routingkey}")
	private String routingKey;

	@Override
	public void produceMail(MailModel mail) {
		amqpTemplate.convertAndSend(exchange, routingKey, mail);
		System.out.println("Send mail = " + mail);
	}
}
