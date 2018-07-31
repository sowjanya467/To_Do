package com.todo.note.userservice.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.note.userservice.model.ForgotPasswordDto;
import com.todo.note.userservice.model.LoginDto;
import com.todo.note.userservice.model.RegistrationDto;
import com.todo.note.userservice.model.ResponseDto;
import com.todo.note.userservice.model.User;
import com.todo.note.userservice.service.RedisRepository;
import com.todo.note.userservice.service.UserService;
import com.todo.note.utility.Utility;
import com.todo.note.utility.exceptions.LoginExceptionHandling;
import com.todo.note.utility.exceptions.ToDoException;
import com.todo.note.utility.exceptions.UserExceptionHandling;
import com.todo.note.utility.messages.Messages;

/*************************************************************************************************************
 *
 * purpose:Controller class for register and sign up using MongoDB
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 10-07-18
 *
 **************************************************************************************************/
@RestController
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;
	@Autowired
	RedisRepository<String, User> redisrepo;
	@Autowired 
	Messages messages;
	Utility utility = new Utility();

	/**
	 * purpose:method to login
	 * 
	 * @param checkUser
	 * @return response
	 * @throws LoginExceptionHandling
	 * @throws MessagingException
	 * @throws ToDoException
	 */

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<ResponseDto> login(@RequestBody LoginDto login, HttpServletResponse response)
			throws LoginExceptionHandling, MessagingException, ToDoException {
		String token = userService.login(login.getMailId(), login.getPassword());
		// logger.info("Logging User : {}", checkUser);

		response.setHeader("token", token);
		redisrepo.setToken(token);
		ResponseDto response1 = new ResponseDto();
		response1.setMessage(messages.get("251"));
		response1.setStatus(200);

		return new ResponseEntity<>(response1, HttpStatus.CREATED);

	}

	/**
	 * purpose:method to register
	 * 
	 * @param checkUser
	 * @return response
	 * @throws UserExceptionHandling
	 * @throws MessagingException
	 * @throws ToDoException
	 */

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<ResponseDto> register(@RequestBody RegistrationDto checkUser)
			throws UserExceptionHandling, MessagingException, ToDoException {
		logger.info("Register user : {}", checkUser);

		userService.registerUser(checkUser);
		ResponseDto response = new ResponseDto();
		response.setMessage(messages.get("252"));
		response.setStatus(200);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/**
	 * purpose:method to activate the account once registered
	 * 
	 * @param hsr
	 * @return
	 **/

	@RequestMapping(value = "/activateaccount", method = RequestMethod.GET)
	public ResponseEntity<String> activateAccount(HttpServletRequest request) {
		logger.info("activate the account");
		String jwtToken = request.getQueryString();
		System.out.println(jwtToken);

		if (!userService.activateAc("   " + jwtToken)) {

			return new ResponseEntity<String>(messages.get("254"), HttpStatus.NOT_FOUND);
		}
		String message = messages.get("253");
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	/**
	 * method to send link if user forgot the password
	 * 
	 * @param CheckUser
	 * @return
	 * @throws MessagingException
	 * @throws ToDoException 
	 */
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public ResponseEntity<ResponseDto> forgotPassword(@RequestParam String emailId) throws MessagingException, ToDoException {
		if (userService.forgotPassword(emailId)) {
			ResponseDto response = new ResponseDto();
			response.setMessage(messages.get("256"));
			response.setStatus(-3);
			return new ResponseEntity<ResponseDto>(response,HttpStatus.NOT_FOUND);
		}
		ResponseDto response = new ResponseDto();
		response.setMessage(messages.get("255"));
		response.setStatus(200);
		return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);

	}

	/**
	 * method to reset the password
	 * 
	 * @param model
	 * @param request
	 * @return response
	 * @throws ToDoException
	 */

	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public ResponseEntity<ResponseDto> resetPassword(@RequestBody ForgotPasswordDto model, HttpServletRequest req)
			throws ToDoException {
		String token = req.getQueryString();

		userService.setPassword(model, token);
		ResponseDto response = new ResponseDto();
		response.setStatus(200);
		response.setMessage(messages.get("257"));
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}
}
