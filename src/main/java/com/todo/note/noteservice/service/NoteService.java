package com.todo.note.noteservice.service;

import java.text.ParseException;
import java.util.List;
import com.todo.note.dto.Label;
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
	public String createNote(NoteDto note, String userId)
			throws LoginExceptionHandling, UserExceptionHandling, ToDoException;

	void deleteNote(String title, String userId) throws LoginExceptionHandling, ToDoException;

	List<NoteModel> viewNotes(String userId) throws ToDoException;

	void updateNote(String userId, String noteId, UpdateNoteDto note) throws LoginExceptionHandling, ToDoException;

	void archiveNote(String noteId) throws ToDoException;

	void unarchiveNote(String noteId) throws ToDoException;

	public void setPin(String noteId) throws ToDoException;

	public void unPin(String noteId) throws ToDoException;

	public void trashNote(String noteId) throws ToDoException;

	public void restoreTrashedNote(String noteId) throws ToDoException;

	public List<NoteModel> viewTrashNotes(String userId) throws ToDoException;

	public List<NoteModel> viewArchivedNotes(String userId) throws ToDoException;

	public ViewNoteDto viewNote(String noteId, String userId) throws ToDoException;

	public void remainder(String userId, String noteId, String reminderTime) throws ToDoException, ParseException;

	void createNewLabel(String labelName, String userId, String noteId) throws ToDoException;


	public void addLabel(String labelId, String userId, String noteId) throws ToDoException;

	public void deleteLabel(String labelId, String userId) throws ToDoException;

	void updateLabel(String labelId, String userId, String newLabelName) throws ToDoException;

	List<Label> viewLabels(String userId) throws ToDoException;

}
