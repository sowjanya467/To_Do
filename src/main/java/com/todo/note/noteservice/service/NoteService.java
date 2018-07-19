package com.todo.note.noteservice.service;

import java.util.List;

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

public interface NoteService 
{
	public void createNote(NoteModel note, String jwtToken) throws LoginExceptionHandling, UserExceptionHandling;

	void deleteNote(String title, String token) throws LoginExceptionHandling, ToDoException;

	List<NoteModel> viewNotes(String token) throws ToDoException;

	void updateNote(String jwtToken, String title, String content, String id)
			throws LoginExceptionHandling, ToDoException;

}
