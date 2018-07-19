package com.todo.note.utility.rabbitmq;

import com.todo.note.userservice.model.MailModel;

public interface Producer 
{
	void produceMail(MailModel model);

}
