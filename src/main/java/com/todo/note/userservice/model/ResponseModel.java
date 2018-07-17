package com.todo.note.userservice.model;

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
