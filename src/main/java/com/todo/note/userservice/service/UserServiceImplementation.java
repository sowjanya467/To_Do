package com.todo.note.userservice.service;

import java.util.Optional;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.note.userservice.model.ForgotPasswordModel;
import com.todo.note.userservice.model.MailModel;
import com.todo.note.userservice.model.RegistrationModel;
import com.todo.note.userservice.model.User;
import com.todo.note.userservice.repository.UserRepository;
import com.todo.note.utility.Utility;
import com.todo.note.utility.exceptions.LoginExceptionHandling;
import com.todo.note.utility.exceptions.ToDoException;
import com.todo.note.utility.exceptions.UserExceptionHandling;
import com.todo.note.utilityemail.EmailService;
import com.todo.note.utilityemail.EmailServiceImplementation;
import com.todo.note.utilityemail.SecurityConfig;

import io.jsonwebtoken.Claims;

@Service
public class UserServiceImplementation 
{

	@Autowired
	private UserRepository repo;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	EmailService emailService;
	@Autowired 
	MailModel mailDto;
	public static final Logger logger=LoggerFactory.getLogger(UserServiceImplementation.class);

	Utility utility=new Utility();
	SecurityConfig securityConfig = new SecurityConfig();

	ForgotPasswordModel password = new ForgotPasswordModel();
	//CreateTokens token = new CreateTokens();
	EmailServiceImplementation s = new EmailServiceImplementation();
	SecurityConfig sc = new SecurityConfig();

	/**
	 * purpose : method to login in to page
	 * 
	 * @param emailId
	 * @param password
	 * @return User
	 * @throws LoginExceptionHandling
	 */
	public String login(String emailId, String password) throws LoginExceptionHandling {

	Optional<User> user=repo.findById(emailId);

		if (!user.isPresent()) {
			throw new LoginExceptionHandling("entered password incorrect");

		} else {

			if (encoder.encode(user.get().getPassword()).equals(password)) {
				logger.info("looged in sucessfully!! continue your works");

			}
		}
		String message = "HI " + user.get().getUserName() + "    you can continue your works";
		return message;

	}

	/**
	 * purpose:method to register in to a page
	 * 
	 * @param user
	 * @return boolean
	 * @throws UserExceptionHandling
	 * @throws MessagingException
	 */

	public void registerUser(RegistrationModel userReg) throws UserExceptionHandling, MessagingException {
		Optional<User> checkUser = repo.findById(userReg.getEmailId());
		if (checkUser.isPresent()) {
			logger.info("user with this email id already exists!!!");
			throw new UserExceptionHandling("user with this email id is already present");
		}
		User user = new User();
		user.setEmailId(userReg.getEmailId());
		//user.setUserId(userReg.getUserId());
		user.setUserName(userReg.getUserName());
		user.setPassword(encoder.encode(userReg.getPassword()));
		user.setPhoneNumber(userReg.getPhoneNumber());
		user.setActivate("false");
		repo.save(user);

		repo.findById(userReg.getEmailId());
		String token = securityConfig.createTokens(userReg);
		System.out.println(token);
		String to=mailDto.setToMailAddress(userReg.getEmailId());
		String subject=mailDto.setSubject("Hi " + userReg.getUserName());
		String body=mailDto.setBody("Activate your accout click on this link:"+"http://192.168.0.8:8080/activateaccount/?" + token);
		//mailDTO.setMailSign("\nThank you \n Sowjanya M \n Bridge Labz \n Mumbai");

		emailService.sendEmail(to,subject,body);

		

	}

	/**
	 * method to activate the account
	 * 
	 * @param jwt
	 * @return
	 */
	public boolean activateAc(String jwt) {

		Claims claims = sc.parseJwt(jwt);
		Optional<User> user = repo.findById(claims.getSubject());
		// System.out.println("dsd");
		user.get().setActivate("true");
		System.out.println("activate");
		repo.save(user.get());

		return true;

	}

	public void setPassword(ForgotPasswordModel f, String tokenJwt) throws ToDoException {
		System.out.println(tokenJwt);
		if (!f.getNewPassword().equals(f.getNewPassword())) {
			throw new ToDoException("passwords mismatch");
		}
		Claims claims = sc.parseJwt(tokenJwt);

		Optional<User> checkUser = repo.findById(claims.getId());
		System.out.println(claims.getSubject());
		User user = checkUser.get();
		user.setPassword(encoder.encode(f.getConfirmPassword()));
		repo.save(user);
	}

	public boolean forgotPassword(String emailId) throws MessagingException {
		String jwtToken=securityConfig.createToken(emailId);
		String to=mailDto.setToMailAddress(emailId);
		String subject=mailDto.setSubject("recover your password" );
		String body=mailDto.setBody("recover your password by clicking"+"http://192.168.0.8:8080/resetPassword/?"+jwtToken);
		emailService.sendEmail(to, subject, body);
		return false;
	}
}
