package com.todo.note.userservice.model;
/*************************************************************************************************************
*
* purpose:Methode to create a JWT token and decode the token
* @author sowjanya467
* @version 1.0
* @since 10-07-18
*
* **************************************************************************************************/

public class ResponseModel 
{
	int status;
	String message;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String response) {
		this.message = response;
	}
	
}
