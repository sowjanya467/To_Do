package com.todo.note.userservice.service;

import javax.mail.MessagingException;

import com.todo.note.userservice.model.ForgotPasswordDto;
import com.todo.note.userservice.model.RegistrationDto;
import com.todo.note.utility.exceptions.LoginExceptionHandling;
import com.todo.note.utility.exceptions.ToDoException;
import com.todo.note.utility.exceptions.UserExceptionHandling;

/*************************************************************************************************************
 *
 * purpose:User Service implementation
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/

public interface UserService {

	public String login(String emailId, String password)
			throws LoginExceptionHandling, MessagingException, ToDoException;

	public void registerUser(RegistrationDto userReg) throws UserExceptionHandling, MessagingException, ToDoException;

	public boolean activateAc(String jwt);

	public void setPassword(ForgotPasswordDto f, String tokenJwt) throws ToDoException;

	public boolean forgotPassword(String emailId) throws MessagingException, ToDoException;

}
