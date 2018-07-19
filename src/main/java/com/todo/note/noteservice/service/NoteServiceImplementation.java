package com.todo.note.noteservice.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.note.noteservice.model.NoteModel;
import com.todo.note.noteservice.repository.NoteMongoRepository;
import com.todo.note.userservice.model.User;
import com.todo.note.userservice.repository.UserRepository;
import com.todo.note.utility.Utility;
import com.todo.note.utility.email.SecurityConfig;
import com.todo.note.utility.exceptions.LoginExceptionHandling;
import com.todo.note.utility.exceptions.ToDoException;
import com.todo.note.utility.exceptions.UserExceptionHandling;

/*************************************************************************************************************
*
* purpose:Implementation of note service
* @author sowjanya467
* @version 1.0
* @since 10-07-18
*
* **************************************************************************************************/

@Service
public class NoteServiceImplementation implements NoteService {
	@Autowired
	NoteMongoRepository noteRepository;

	@Autowired
	private UserRepository userRepository;

	Utility utility = new Utility();
	SecurityConfig security = new SecurityConfig();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
	LocalDateTime now = LocalDateTime.now();
	public static final Logger logger = LoggerFactory.getLogger(NoteServiceImplementation.class);

	/**
	 * 
	 */
	@Override
	public void createNote(NoteModel note, String jwtToken) throws LoginExceptionHandling, UserExceptionHandling {
		logger.info("creating note");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String mailId = utility.parseJwt(jwtToken).getId();
		Optional<User> user = userRepository.findByemailId(mailId);
		if (!user.isPresent()) {
			throw new LoginExceptionHandling("please login to do your works");
		}
		if (note.getTitle() == null && note.getContent() == null) {
			throw new UserExceptionHandling("title and content cannot be empty");
		}

		NoteModel newNote = new NoteModel();
		newNote.setId(sdf.format(new Date()));
		newNote.setEmailId(note.getEmailId());
		newNote.setTitle(note.getTitle());
		newNote.setContent(note.getContent());
		newNote.setArchive("false");
		newNote.setLabel(note.getLabel());
		newNote.setCreatedAt(dtf.format(now));
		newNote.setEditedAt(dtf.format(now));
		noteRepository.save(newNote);
		logger.info("note created");

	}

	@Override
	public void updateNote(String jwtToken, String title, String content, String id)
			throws LoginExceptionHandling, ToDoException {
		logger.info("updating the note");

		String mailId = utility.parseJwt(jwtToken).getId();
		Optional<User> user = userRepository.findByemailId(mailId);
		if (!user.isPresent()) {
			throw new LoginExceptionHandling("please login to do your works");

		}
		Optional<NoteModel> note = noteRepository.findById(id);
		if (!note.isPresent()) {
			throw new ToDoException("note does not exist");
		}
		NoteModel notes = note.get();
		notes.setContent(content);
		notes.setTitle(title);
		notes.setEditedAt(dtf.format(now));

		noteRepository.save(notes);

	}

	@Override
	public void deleteNote(String title, String token) throws LoginExceptionHandling, ToDoException {
		logger.info("deleting the note");
		String mailId = utility.parseJwt(token).getId();
		Optional<User> user = userRepository.findByemailId(mailId);
		Optional<NoteModel> note = noteRepository.findBytitle(title);

		if (!user.isPresent()) {
			throw new LoginExceptionHandling("please login to do your works");
		}

		if (!note.isPresent()) {
			throw new ToDoException("the note is not present");
		}

		// noteRepository.findBytitle(title);
		noteRepository.deleteBytitle(title);

	}

	/**
	 * 
	 * 
	 */
	@Override
	public List<NoteModel> viewNotes(String token) throws ToDoException {

		String mailId = utility.parseJwt(token).getId();
		Optional<User> user = userRepository.findById(mailId);
		if(!user.isPresent())
		{
			throw new ToDoException("user is not present");
		}
		String userId = user.get().getEmailId();
		List<NoteModel> note = noteRepository.findByemailId(userId);

		return note;

	}
	public void addLabel(String id,String label) throws ToDoException
	{

		Optional<NoteModel> notes=noteRepository.findById(id);
		if(!notes.isPresent())
		{
			throw new ToDoException("note with this id is not present");
			
		}
		
		NoteModel note=new NoteModel();
		note.setLabel(label);
		note.setEditedAt(dtf.format(now));
		noteRepository.save(note);
	}
	public void setArchive(String id)
	{
		Optional<NoteModel> note=noteRepository.findById(id);
		if(note.isPresent())
		{
			NoteModel notes=note.get();
			notes.setArchive("true");
			noteRepository.save(notes);
			logger.info("note archived");
			
		}
	}

	public void setPin(String id) throws ToDoException
	{
		Optional<NoteModel> note=noteRepository.findById(id);
	
		if(!note.isPresent())
		{
			throw new ToDoException("note does not exist");
			
		}
		
	}

}
