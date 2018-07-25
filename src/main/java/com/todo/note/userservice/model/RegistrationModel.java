package com.todo.note.userservice.model;

import java.io.Serializable;

/*************************************************************************************************************
*
* purpose:Methode to create a JWT token and decode the token
* @author sowjanya467
* @version 1.0
* @since 10-07-18
*
* **************************************************************************************************/

public class RegistrationModel implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private String emailId;
	private String userName;
	private String password;
	private String phoneNumber;
	
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId
	 *            the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
	
}