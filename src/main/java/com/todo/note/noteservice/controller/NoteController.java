package com.todo.note.noteservice.controller;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todo.note.dto.NoteDto;
import com.todo.note.dto.UpdateNoteDto;
import com.todo.note.dto.ViewNoteDto;
import com.todo.note.noteservice.model.NoteModel;
import com.todo.note.noteservice.service.NoteService;
import com.todo.note.userservice.model.ResponseModel;
import com.todo.note.utility.exceptions.LoginExceptionHandling;
import com.todo.note.utility.exceptions.ToDoException;
import com.todo.note.utility.exceptions.UserExceptionHandling;

@RestController
public class NoteController {

	@Autowired
	NoteService service;

	public static final Logger logger = LoggerFactory.getLogger(NoteController.class);

	/**
	 * method to create note
	 * 
	 * @param note
	 * @param jwtToken
	 * @return
	 * @throws LoginExceptionHandling
	 * @throws UserExceptionHandling
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/createnote/", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> createNote(@RequestBody NoteDto note, @RequestHeader String jwtToken)
			throws LoginExceptionHandling, UserExceptionHandling, ToDoException {
		// String jwtToken=request.getHeader("Authorization");

		logger.info("creating the note..");
		service.createNote(note, jwtToken);

		ResponseModel response = new ResponseModel();
		response.setMessage("created note Successfull!!");
		response.setStatus(200);

		return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);

	}

	/**
	 * method to update note
	 * 
	 * @param jwtToken
	 * @param title
	 * @param content
	 * @param id
	 * @return
	 * @throws LoginExceptionHandling
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/updatenote", method = RequestMethod.PUT)
	public ResponseEntity<ResponseModel> updateNote(@RequestParam String id, UpdateNoteDto note,
			@RequestHeader String jwtToken) throws LoginExceptionHandling, ToDoException {
		// String jwtToken=request.getHeader("Authorization");

		service.updateNote(jwtToken, id, note);
		ResponseModel response = new ResponseModel();
		response.setMessage("updated note Successfull!!");
		response.setStatus(200);
		return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);

	}

	/**
	 * method to delete note
	 * 
	 * @param title
	 * @param token
	 * @param note
	 * @return
	 * @throws LoginExceptionHandling
	 * @throws ToDoException
	 */

	@RequestMapping(value = "/deletenote", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> deleteNote(@RequestParam String id, @RequestHeader String jwtToken)
			throws LoginExceptionHandling, ToDoException {
		// String jwtToken=request.getHeader("Authorization");

		service.deleteNote(id, jwtToken);
		ResponseModel response = new ResponseModel();
		response.setMessage("deleted note Successfull!!");
		response.setStatus(200);
		return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);

	}

	/**
	 * api to display all the notes
	 * 
	 * @param token
	 * @return
	 * @throws ToDoException
	 */

	@RequestMapping(value = "/viewnotes", method = RequestMethod.PUT)
	public ResponseEntity<List<NoteModel>> showListOfNotes(@RequestHeader String jwtToken) throws ToDoException {
		// String jwtToken=request.getHeader("Authorization");

		List<NoteModel> note = service.viewNotes(jwtToken);

		return new ResponseEntity<List<NoteModel>>(note, HttpStatus.OK);

	}

	/**
	 * api to set pin set the pin
	 * 
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/setpin", method = RequestMethod.PUT)
	public ResponseEntity<ResponseModel> setPin(@RequestParam String noteId) throws ToDoException {

		service.setPin(noteId);
		ResponseModel response = new ResponseModel();
		response.setStatus(200);
		response.setMessage("note pinned");

		return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);

	}

	/**
	 * archive the note
	 * 
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/setarchive", method = RequestMethod.PUT)
	public ResponseEntity<ResponseModel> setArchive(@RequestParam String noteId) throws ToDoException {
		service.archiveNote(noteId);
		ResponseModel response = new ResponseModel();
		response.setStatus(200);
		response.setMessage("note archived");
		return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);

	}

	/**
	 * unpin the note
	 * 
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/unpin", method = RequestMethod.PUT)
	public ResponseEntity<ResponseModel> unPin(@RequestParam String noteId) throws ToDoException {

		service.unPin(noteId);
		ResponseModel response = new ResponseModel();
		response.setStatus(200);
		response.setMessage("note pinned");

		return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);

	}

	/**
	 * unarchive the note
	 * 
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/unarchive", method = RequestMethod.PUT)
	public ResponseEntity<ResponseModel> unArchive(@RequestParam String noteId) throws ToDoException {
		service.unarchiveNote(noteId);
		ResponseModel response = new ResponseModel();
		response.setStatus(200);
		response.setMessage("note archived");
		return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);

	}

	/**
	 * trash the note
	 * 
	 * @param id
	 * @return
	 * @throws ToDoException
	 */

	@RequestMapping(value = "/trashnote", method = RequestMethod.PUT)
	public ResponseEntity<ResponseModel> trashNote(@RequestParam String id) throws ToDoException {

		service.trashNote(id);
		ResponseModel response = new ResponseModel();
		response.setStatus(200);
		response.setMessage("note trashed");
		return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);
	}

	/**
	 * display all trashed notes
	 * 
	 * @param request
	 * @return
	 * @throws ToDoException
	 */

	@RequestMapping(value = "/viewtrashednotes", method = RequestMethod.PUT)
	public ResponseEntity<List<NoteModel>> viewTrashedNotes(@RequestHeader String jwtToken) throws ToDoException {
		// String jwtToken=request.getHeader("Authorization");

		List<NoteModel> note = service.viewTrashNotes(jwtToken);

		return new ResponseEntity<List<NoteModel>>(note, HttpStatus.OK);

	}

	/**
	 * display all archived notes
	 * 
	 * @param request
	 * @return
	 * @throws ToDoException
	 */

	@RequestMapping(value = "/viewarchivednotes", method = RequestMethod.PUT)
	public ResponseEntity<List<NoteModel>> viewArchivedNotes(@RequestHeader String jwtToken) throws ToDoException {
		// String jwtToken=request.getHeader("Authorization");

		List<NoteModel> note = service.viewArchivedNotes(jwtToken);

		return new ResponseEntity<List<NoteModel>>(note, HttpStatus.OK);

	}

	

	@RequestMapping(value = "/viewsinglenote", method = RequestMethod.PUT)
	public ResponseEntity<ViewNoteDto> viewNote(@RequestParam String id, @RequestHeader String jwtToken)
			throws ToDoException {

		// String token=request.getHeader("Authorization");
		ViewNoteDto note = service.viewNote(id, jwtToken);
		logger.info("note is displayed");
		ResponseModel response = new ResponseModel();
		response.setStatus(200);
		response.setMessage("note displayed");
		return new ResponseEntity<ViewNoteDto>(note, HttpStatus.OK);

	}

	/**
	 * restore the trashed note
	 * @param id
	 * @return
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/restoretrashed", method = RequestMethod.PUT)
	public ResponseEntity<ResponseModel> restoreTrashed(@RequestParam String id) throws ToDoException {
		service.restoreTrashedNote(id);
		ResponseModel response = new ResponseModel();
		response.setStatus(200);
		response.setMessage("note restored ");
		return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);

	}

	/**
	 * set reminder
	 * @param id
	 * @param token
	 * @param reminderTime
	 * @return
	 * @throws ToDoException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/reminder", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> setReminder(@RequestParam String id, @RequestHeader String token,
			@RequestParam String reminderTime) throws ToDoException, ParseException {
		service.remainder(token, id, reminderTime);
		ResponseModel response = new ResponseModel();
		response.setStatus(200);
		response.setMessage("remainder set");
		return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);

	}

	/**
	 * create new label
	 * @param labelName
	 * @param token
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/createlabel", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> setLabel(@RequestParam String labelName, @RequestHeader String token,
			@RequestParam String noteId) throws ToDoException {
		service.createNewLabel(labelName, token, noteId);
		ResponseModel response = new ResponseModel();
		response.setStatus(200);
		response.setMessage("label cerated");
		return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);

	}

	/*
	 * @RequestMapping(value = "/demo", method = RequestMethod.POST) public
	 * ResponseEntity<ResponseModel> Label(@RequestParam String
	 * labelName, @RequestHeader String token, @RequestParam String noteId) throws
	 * ToDoException { service.demo(labelName, token, noteId); ResponseModel
	 * response = new ResponseModel(); response.setStatus(200);
	 * response.setMessage("label cerated"); return new
	 * ResponseEntity<ResponseModel>(response, HttpStatus.OK);
	 * 
	 * }
	 */
	/**
	 * add label to note
	 * @param labelId
	 * @param token
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/addlabel", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> addLabel(@RequestParam String labelId, @RequestHeader String token,
			@RequestParam String noteId) throws ToDoException {
		service.addLabel(labelId, token, noteId);
		ResponseModel response = new ResponseModel();
		response.setStatus(200);
		response.setMessage("label added to note");
		return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);

	}
	/**
	 * delete label to note
	 * @param labelId
	 * @param token
	 * @return
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/deletelabel", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> deleteLabel(@RequestParam String labelId, @RequestHeader String token) throws ToDoException {
		service.deleteLabel(labelId, token);
		ResponseModel response = new ResponseModel();
		response.setStatus(200);
		response.setMessage("label deleted");
		return new ResponseEntity<ResponseModel>(response, HttpStatus.OK);

	}

}
