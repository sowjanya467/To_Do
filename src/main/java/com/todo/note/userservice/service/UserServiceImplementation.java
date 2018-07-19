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
import com.todo.note.utility.email.EmailService;
import com.todo.note.utility.email.EmailServiceImplementation;
import com.todo.note.utility.email.SecurityConfig;
import com.todo.note.utility.exceptions.LoginExceptionHandling;
import com.todo.note.utility.exceptions.ToDoException;
import com.todo.note.utility.exceptions.UserExceptionHandling;
import com.todo.note.utility.rabbitmq.Producer;

import io.jsonwebtoken.Claims;

/*************************************************************************************************************
 *
 * purpose:User Service implementation
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/

@Service
public class UserServiceImplementation {

	@Autowired
	private UserRepository repo;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	EmailService emailService;
	@Autowired
	MailModel mailDto;
	@Autowired 
	Producer produce;
	public static final Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);

	Utility utility = new Utility();
	SecurityConfig securityConfig = new SecurityConfig();

	ForgotPasswordModel password = new ForgotPasswordModel();
	EmailServiceImplementation s = new EmailServiceImplementation();

	/**
	 * purpose : method to login in to page
	 * 
	 * @param emailId
	 * @param password
	 * @return User
	 * @throws LoginExceptionHandling
	 * @throws MessagingException
	 * @throws ToDoException 
	 */
	public String login(String emailId, String password) throws LoginExceptionHandling, MessagingException, ToDoException {

		Optional<User> user = repo.findByemailId(emailId);

		if (!user.isPresent()) {
			throw new LoginExceptionHandling("user with this email id doesnot exist");
		} 
		if(user.get().getActivate().equals("false"))
		{
			throw new ToDoException("account is not activated");
		}
		
		else {

			if (encoder.matches(password, user.get().getPassword())) {

				logger.info("looged in sucessfully!! continue your works");
				String jwtToken = utility.createToken(emailId);
				mailDto.setToMailAddress(emailId);
				mailDto.setSubject("login");
				mailDto.setBody(jwtToken);
				produce.produceMail(mailDto);
				//emailService.sendEmail(emailId, jwtToken, "conformation");

			} else {
				return "password incorrect";
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
		logger.info("checking the fields are valid or not");
		if (!utility.isValidateAllFields(userReg)) {
			Optional<User> checkUser = repo.findByemailId(userReg.getEmailId());
			if (checkUser.isPresent()) {
				logger.info("user with this email id already exists!!!");
				throw new UserExceptionHandling("user with this email id is already present");
			}
			User user = new User();
			user.setEmailId(userReg.getEmailId());
			user.setUserName(userReg.getUserName());
			user.setPassword(encoder.encode(userReg.getPassword()));
			user.setPhoneNumber(userReg.getPhoneNumber());
			user.setActivate("false");
			User save = repo.save(user);
            System.out.println(save.getId()+"----------------");
			repo.findById(userReg.getEmailId());
			String token = utility.createTokens(userReg);
		    mailDto.setToMailAddress(userReg.getEmailId());
			mailDto.setSubject("Hi " + userReg.getUserName());
		     mailDto.setBody(
					"Activate your accout click on this link:" + "http://192.168.0.9:8080/activateaccount/?" + token);

			produce.produceMail(mailDto);
			//emailService.sendEmail(to, subject, body);

		}
	}

	/**
	 * method to activate the account
	 * 
	 * @param jwt
	 * @return
	 */
	public boolean activateAc(String jwt) {

		Claims claims = utility.parseJwt(jwt);
		Optional<User> user = repo.findByemailId(claims.getSubject());
		user.get().setActivate("true");
		System.out.println("activate");
		repo.save(user.get());

		return true;

	}

	/**
	 * method to set the password
	 * 
	 * @param f
	 * @param tokenJwt
	 * @throws ToDoException
	 */

	public void setPassword(ForgotPasswordModel f, String tokenJwt) throws ToDoException {
		System.out.println(tokenJwt);
		if (!f.getNewPassword().equals(f.getNewPassword())) {
			throw new ToDoException("passwords mismatch");
		}
		Claims claims = utility.parseJwt(tokenJwt);

		Optional<User> checkUser = repo.findByemailId(claims.getId());
		System.out.println(claims.getSubject());
		User user = checkUser.get();
		user.setPassword(encoder.encode(f.getConfirmPassword()));
		repo.save(user);
	}

	/**
	 * method to send link to reset password if user forgot password
	 * 
	 * @param emailId
	 * @return
	 * @throws MessagingException
	 */
	public boolean forgotPassword(String emailId) throws MessagingException {
		String jwtToken = utility.createToken(emailId);
		//String to = emailId;
		String subject = "recover your password";
		String body = "recover your password by clicking" + "http://192.168.0.8:8080/resetPassword/?" + jwtToken;
		mailDto.setToMailAddress(emailId);
		mailDto.setSubject(subject);
		mailDto.setBody(body);
		produce.produceMail(mailDto);
		
		//emailService.sendEmail(to, subject, body);
		return false;
	}
}
