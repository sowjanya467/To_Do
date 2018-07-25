package com.todo.note.noteservice.service;

import java.text.ParseException;
import java.util.List;

import com.todo.note.dto.LabelDto;
import com.todo.note.dto.NoteDto;
import com.todo.note.dto.UpdateNoteDto;
import com.todo.note.dto.ViewNoteDto;
import com.todo.note.noteservice.model.NoteModel;
import com.todo.note.utility.exceptions.LoginExceptionHandling;
import com.todo.note.utility.exceptions.ToDoException;
import com.todo.note.utility.exceptions.UserExceptionHandling;

/*************************************************************************************************************
 *
 * purpose:interface of note service
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 19-07-18
 *
 **************************************************************************************************/

public interface NoteService {
	public void createNote(NoteDto note, String jwttoken)
			throws LoginExceptionHandling, UserExceptionHandling, ToDoException;

	void deleteNote(String title, String token) throws LoginExceptionHandling, ToDoException;

	List<NoteModel> viewNotes(String token) throws ToDoException;

	void updateNote(String jwtToken, String id, UpdateNoteDto note) throws LoginExceptionHandling, ToDoException;

	void archiveNote(String id) throws ToDoException;

	void unarchiveNote(String id) throws ToDoException;

	public void setPin(String id) throws ToDoException;

	public void unPin(String id) throws ToDoException;

	public void trashNote(String id) throws ToDoException;
	public void restoreTrashedNote(String id) throws ToDoException;

	public void deleteTrashedNote(String id);

	public List<NoteModel> viewTrashNotes(String token) throws ToDoException;

	public List<NoteModel> viewArchivedNotes(String token) throws ToDoException;

	public ViewNoteDto viewNote(String noteId, String token) throws ToDoException;
	
	public  void remainder(String token, String noteId, String reminderTime) throws ToDoException, ParseException;

	void createNewLabel(String labelName,String token,String noteId) throws ToDoException;
	
	void demo(String labelName,String token,String noteId);

	public void addLabel(String labelId, String token, String noteId) throws ToDoException;

	public void deleteLabel(String labelId, String token) throws ToDoException;

}
