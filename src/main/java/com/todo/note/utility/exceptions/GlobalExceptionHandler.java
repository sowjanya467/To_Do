package com.todo.note.utility.exceptions;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.todo.note.userservice.model.ResponseModel;




/*************************************************************************************************************
 *
 * purpose:Global exception handling
 *
 * @author sowjanya467
 * @version 1.0
 * @since -05-18
 *
 **************************************************************************************************/
@ControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(UserExceptionHandling.class)
	public ResponseEntity<ResponseModel> handleRegistrationException(UserExceptionHandling exception) {
		logger.info("Error occured for: " + exception.getMessage(), exception);
		ResponseModel response = new ResponseModel();
		response.setMessage(exception.getMessage());
		response.setStatus(-3);
		System.out.println("global exception");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LoginExceptionHandling.class)
	public ResponseEntity<ResponseModel> handleLoginException(UserExceptionHandling exception) {
		logger.info("Error occured for: " + exception.getMessage(), exception);
		ResponseModel response = new ResponseModel();
		response.setMessage(exception.getMessage());
		response.setStatus(-3);
		System.out.println("global exception");
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ToDoException.class)
	public ResponseEntity<ResponseModel> handlesetPasswordException(ToDoException exception) {
		logger.info("Error occured: " + exception.getMessage(), exception);
		ResponseModel response = new ResponseModel();
		response.setMessage(exception.getMessage());
		response.setStatus(-3);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 * @param exception
	 * @param request
	 * @param reqId
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseModel> handleException(Exception exception, HttpServletRequest request) {
		logger.error("Error occured for: " + exception.getMessage(), exception);
		ResponseModel response = new ResponseModel();
		response.setMessage("Something went wrong");
		response.setStatus(-1);

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}