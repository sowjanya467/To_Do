package com.todo.note.noteservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.note.noteservice.model.NoteDetails;
import com.todo.note.noteservice.model.NoteModel;
import com.todo.note.noteservice.service.NoteServiceImplementation;
import com.todo.note.userservice.model.ResponseModel;
import com.todo.note.utility.exceptions.LoginExceptionHandling;
import com.todo.note.utility.exceptions.ToDoException;
import com.todo.note.utility.exceptions.UserExceptionHandling;

/*************************************************************************************************************
*
* purpose:Note controller
* @author sowjanya467
* @version 1.0
* @since 18-07-18
*
* **************************************************************************************************/

@RestController
public class NoteController {

	@Autowired
	NoteServiceImplementation service = new NoteServiceImplementation();

	/**
	 * To create a note for user
	 * @param note
	 * @param jwtToken
	 * @return
	 * @throws LoginExceptionHandling
	 * @throws UserExceptionHandling
	 */
	@RequestMapping(value = "/createnote/", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> createNote(@RequestBody NoteModel note, String jwtToken)
			throws LoginExceptionHandling, UserExceptionHandling {
		service.createNote(note, jwtToken);

		ResponseModel response = new ResponseModel();
		response.setMessage("created note Successfull!!");
		response.setStatus(200);

		return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);

	}
	/**
	 * Method to update the note
	 * @param jwtToken
	 * @param title
	 * @param content
	 * @param id
	 * @return
	 * @throws LoginExceptionHandling
	 * @throws ToDoException
	 */

	@RequestMapping(value = "/updateNote", method = RequestMethod.PUT)
	public ResponseEntity<ResponseModel> updateNote(@RequestParam String jwtToken, @RequestParam String title,
			String content,String id) throws LoginExceptionHandling, ToDoException {

		service.updateNote(jwtToken, title, content,id);
		ResponseModel response = new ResponseModel();
		response.setMessage("updated note Successfull!!");
		response.setStatus(200);
		return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);

	}

	/**
	 * Method to delete the note
	 * @param title
	 * @param token
	 * @param note
	 * @return
	 * @throws LoginExceptionHandling
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/deleteNote", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> deleteNote(@RequestParam String title, @RequestParam String token,NoteDetails note)
			throws LoginExceptionHandling, ToDoException {
		service.deleteNote(title, token);
		ResponseModel response = new ResponseModel();
		response.setMessage("updated note Successfull!!");
		response.setStatus(200);
		return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);

	}
	/**
	 * Method to display all the notes
	 * @param token
	 * @return
	 * @throws ToDoException
	 */

	@RequestMapping(value = "/viewNotes", method = RequestMethod.GET)
	public ResponseEntity<List<NoteModel>> showListOfNotes(@RequestParam String token) throws ToDoException {
		List<NoteModel> note=service.viewNotes(token);
		
		return new ResponseEntity<List<NoteModel>>(note,HttpStatus.OK);

	}
	
	

}
