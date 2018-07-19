package com.todo.note.utility;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.todo.note.userservice.model.RegistrationModel;
import com.todo.note.utility.exceptions.UserExceptionHandling;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*************************************************************************************************************
 *
 * purpose:Utility class
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/

public class Utility {
	static final String KEY = "sowjanya";

	public Utility() {

	}

	/**
	 * method validate the fields when registering
	 * 
	 * @param register
	 * @return
	 * @throws UserExceptionHandling
	 */

	public boolean isValidateAllFields(RegistrationModel register) throws UserExceptionHandling {
		if (!validateEmailAddress(register.getEmailId())) {
			throw new UserExceptionHandling("emailid not valid  Exception");
		} else if (!isValidUserName(register.getUserName())) {
			throw new UserExceptionHandling("UserName Not valid  Exception");
		} else if (!validatePassword(register.getPassword())) {
			throw new UserExceptionHandling("password not valid Exception");
		} else if (!isValidMobileNumber(register.getPhoneNumber())) {
			throw new UserExceptionHandling("mobilenumber not valid  Exception");
		}
		return false;
	}

	public static boolean validateEmailAddress(String emailId) {
		Pattern emailNamePtrn = Pattern
				.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mtch = emailNamePtrn.matcher(emailId);
		return mtch.matches();

	}

	public static boolean validatePassword(String password) {
		Pattern pattern = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}");
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();

	}

	public static boolean isValidUserName(String userName) {
		Pattern userNamePattern = Pattern.compile("^[a-z0-9_-]{6,14}$");
		Matcher matcher = userNamePattern.matcher(userName);
		return matcher.matches();

	}

	public static boolean isValidMobileNumber(String mobileNumber) {
		Pattern mobileNumberPattern = Pattern.compile("\\d{10}");
		Matcher matcher = mobileNumberPattern.matcher(mobileNumber);
		return matcher.matches();
	}

	/**
	 * to create a jwt token
	 * 
	 * @param id
	 * @return
	 */
	public String createToken(String id) {

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		Date date = new Date();

		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(date).signWith(signatureAlgorithm, KEY);
		return builder.compact();

	}

	/**
	 * to decode the jwt token
	 * 
	 * @param jwt
	 * @return
	 */
	public Claims parseJwt(String jwt) {
		return Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwt).getBody();

	}

	public String createTokens(RegistrationModel user) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		String subject = user.getEmailId();
		String issuer = user.getUserName();
		Date date = new Date();
		JwtBuilder builder = Jwts.builder().setSubject(subject).setIssuedAt(date).setIssuer(issuer)
				.signWith(signatureAlgorithm, KEY);
		return builder.compact();

	}

}
