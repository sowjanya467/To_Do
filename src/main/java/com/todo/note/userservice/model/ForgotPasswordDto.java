package com.todo.note.userservice.model;
/*************************************************************************************************************
*
* purpose:Methode to create a JWT token and decode the token
* @author sowjanya467
* @version 1.0
* @since 10-07-18
*
* **************************************************************************************************/

public class ForgotPasswordDto
{
	String newPassword;
	String confirmPassword;
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}


}
