package com.todo.note.userservice.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

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

import com.todo.note.userservice.model.ForgotPasswordModel;
import com.todo.note.userservice.model.RegistrationModel;
import com.todo.note.userservice.model.ResponseModel;
import com.todo.note.userservice.service.UserServiceImplementation;
import com.todo.note.utility.Utility;
import com.todo.note.utility.exceptions.LoginExceptionHandling;
import com.todo.note.utility.exceptions.ToDoException;
import com.todo.note.utility.exceptions.UserExceptionHandling;

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
	UserServiceImplementation userService = new UserServiceImplementation();
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

	@RequestMapping(value = "/login/", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> login(@RequestParam String mail, @RequestParam String password)
			throws LoginExceptionHandling, MessagingException, ToDoException {
		String message = userService.login(mail, password);
		// logger.info("Logging User : {}", checkUser);

		ResponseModel response = new ResponseModel();
		response.setMessage(message);
		response.setStatus(200);

		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	/**
	 * purpose:method to register
	 * 
	 * @param checkUser
	 * @return response
	 * @throws UserExceptionHandling
	 * @throws MessagingException
	 */

	@RequestMapping(value = "/register/", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> register(@RequestBody RegistrationModel checkUser)
			throws UserExceptionHandling, MessagingException {
		logger.info("Register user : {}", checkUser);

		userService.registerUser(checkUser);
		ResponseModel response = new ResponseModel();
		response.setMessage("Registeration Successfull!!");
		response.setStatus(200);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/**
	 * purpose:method to activate the account once registered
	 * 
	 * @param hsr
	 * @return
	 **/

	@RequestMapping(value = "/activateaccount/", method = RequestMethod.GET)
	public ResponseEntity<String> activateAccount(HttpServletRequest request) {
		logger.info("activate the account");
		String jwtToken = request.getQueryString();
		System.out.println(jwtToken);

		if (!userService.activateAc("   " + jwtToken)) {

			return new ResponseEntity<String>("Account not activated ", HttpStatus.NOT_FOUND);
		}
		String message = "account avtivated successfully";
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	/**
	 * method to send link if user forgot the password
	 * 
	 * @param CheckUser
	 * @return
	 * @throws MessagingException
	 */
	@RequestMapping(value = "/forgotpassword/", method = RequestMethod.POST)
	public ResponseEntity<String> forgotPassword(@RequestParam String emailId) throws MessagingException {
		if (userService.forgotPassword(emailId)) {
			return new ResponseEntity<String>("invalid", HttpStatus.NOT_FOUND);
		}
		String message = "link to set your password has been sent successfully";
		return new ResponseEntity<String>(message, HttpStatus.OK);

	}

	/**
	 * method to reset the password
	 * 
	 * @param model
	 * @param request
	 * @return response
	 * @throws ToDoException
	 */

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> resetPassword(@RequestBody ForgotPasswordModel model, HttpServletRequest req)
			throws ToDoException {
		String token = req.getQueryString();

		userService.setPassword(model, token);
		ResponseModel response = new ResponseModel();
		response.setStatus(200);
		response.setMessage("password changed successfully!!!");
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}
}
