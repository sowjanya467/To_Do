package com.todo.note.userservice.model;

/*************************************************************************************************************
*
* purpose:POJO class
* @author sowjanya467
* @version 1.0
* @since 10-07-18
*
* **************************************************************************************************/
import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "logindb")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private ObjectId id;

	private String emailId;
	private String userName;
	private String password;
	private String phoneNumber;
	private String activate;

	/**
	 * @return the activate
	 */
	public String getActivate() {
		return activate;
	}

	/**
	 * @param activate
	 *            the activate to set
	 */
	public void setActivate(String activate) {
		this.activate = activate;
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [emailId=" + emailId + ", userName=" + userName + ", password=" + password
				+ ", phoneNumber=" + phoneNumber + ", activate=" + activate + "]";
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

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

}
