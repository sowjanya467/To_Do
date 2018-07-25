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

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "logindb")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _id;
	private String emailId;
	private String userName;
	private String password;
	private String phoneNumber;
	private String activate;


	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getActivate() {
		return activate;
	}

	public void setActivate(String activate) {
		this.activate = activate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	@Override
	public String toString() {
		return "User [_id=" + _id + ", emailId=" + emailId + ", userName=" + userName + ", password=" + password
				+ ", phoneNumber=" + phoneNumber + ", activate=" + activate + "]";
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

}
