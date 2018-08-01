package com.todo.note.userservice.service;

import java.util.Optional;

import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.note.userservice.model.ForgotPasswordDto;
import com.todo.note.userservice.model.MailModel;
import com.todo.note.userservice.model.RegistrationDto;
import com.todo.note.userservice.model.User;
import com.todo.note.userservice.repository.UserRepository;
import com.todo.note.utility.Utility;
import com.todo.note.utility.email.EmailService;
import com.todo.note.utility.email.EmailServiceImplementation;
import com.todo.note.utility.email.SecurityConfig;
import com.todo.note.utility.exceptions.LoginExceptionHandling;
import com.todo.note.utility.exceptions.ToDoException;
import com.todo.note.utility.exceptions.UserExceptionHandling;
import com.todo.note.utility.messages.Messages;
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
public class UserServiceImplementation implements UserService {

	@Autowired
	UserRepository repo;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	EmailService emailService;
	@Autowired
	MailModel mailDto;
	@Autowired
	Producer produce;
	@Autowired
	Environment environment;
	@Autowired
	ModelMapper mapper;
	@Value("${host}")
    private String host;

	@Autowired
	Messages messages;
	public static final Logger logger = LoggerFactory.getLogger(UserServiceImplementation.class);

	Utility utility = new Utility();
	SecurityConfig securityConfig = new SecurityConfig();

	ForgotPasswordDto password = new ForgotPasswordDto();
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
	@Override
	public String login(String emailId, String password)
			throws LoginExceptionHandling, MessagingException, ToDoException {

		logger.info("login");
		Optional<User> user = repo.findByEmailId(emailId);
		User curUser = user.get();
		utility.isPresentInDb(user.isPresent(), messages.get("101"));
		utility.isPresentInDb(user.get().getActivate(), "account is not activated");
		if (encoder.matches(password, user.get().getPassword())) {

			logger.info("looged in sucessfully!! continue your works");
			String jwtToken = utility.createTokens(curUser);
			return jwtToken;

		} else {
			throw new ToDoException(messages.get("102"));
		}

	}

	/**
	 * purpose:method to register in to a page
	 * 
	 * @param user
	 * @return boolean
	 * @throws UserExceptionHandling
	 * @throws MessagingException
	 * @throws ToDoException
	 */

	@Override
	public void registerUser(RegistrationDto userReg) throws UserExceptionHandling, MessagingException, ToDoException {
		logger.info("checking the fields are valid or not");
		if (!utility.isValidateAllFields(userReg)) {
			Optional<User> checkUser = repo.findByEmailId(userReg.getEmailId());
			utility.isPresentInDb(!checkUser.isPresent(), messages.get("103"));

			userReg.setPassword(encoder.encode(userReg.getPassword()));
			User user = mapper.map(userReg, User.class);
			user = repo.save(user);
			String token = utility.createTokens(user);
			mailDto.setToMailAddress(userReg.getEmailId());
			mailDto.setSubject("Hi " + userReg.getUserName());
			mailDto.setBody(messages.get("activation.link")+host+messages.get("601")+ token);
			produce.produceMail(mailDto);

		}
	}

	/**
	 * method to activate the account
	 * 
	 * @param jwt
	 * @return
	 */
	@Override
	public boolean activateAc(String jwt) {

		Claims claims = utility.parseJwt(jwt);
		Optional<User> user = repo.findById(claims.getSubject());
		user.get().setActivate(true);
		logger.debug("account activated");
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

	@Override
	public void setPassword(ForgotPasswordDto f, String tokenJwt) throws ToDoException {
		System.out.println(tokenJwt);
		if (!f.getNewPassword().equals(f.getNewPassword())) {
			throw new ToDoException(messages.get("104"));
		}
		Claims claims = utility.parseJwt(tokenJwt);

		Optional<User> checkUser = repo.findByEmailId(claims.getId());
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
	 * @throws ToDoException
	 */
	@Override
	public boolean forgotPassword(String emailId) throws MessagingException, ToDoException {
		Optional<User> checkUser = repo.findByEmailId(emailId);
		utility.isPresentInDb(checkUser.isPresent(), messages.get("101"));
		String jwtToken = utility.createToken(emailId);

		mailDto.setToMailAddress(emailId);
		System.out.println(emailId);
		mailDto.setSubject(messages.get("forgotPassword.subject"));
		mailDto.setBody(messages.get("resetPassword.link")+host+messages.get("600")+ jwtToken);
		produce.produceMail(mailDto);

		return false;
	}
}
