package com.todo.note.noteservice.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.note.dto.LabelDto;
import com.todo.note.dto.NoteDto;
import com.todo.note.dto.UpdateNoteDto;
import com.todo.note.dto.ViewNoteDto;
import com.todo.note.noteservice.model.NoteModel;
import com.todo.note.noteservice.preconditions.PreConditions;
import com.todo.note.noteservice.repository.LabelRepository;
import com.todo.note.noteservice.repository.NoteMongoRepository;
import com.todo.note.userservice.model.User;
import com.todo.note.userservice.repository.UserRepository;
import com.todo.note.utility.Utility;
import com.todo.note.utility.email.SecurityConfig;
import com.todo.note.utility.exceptions.LoginExceptionHandling;
import com.todo.note.utility.exceptions.ToDoException;
import com.todo.note.utility.exceptions.UserExceptionHandling;

/**
 * @author bridgelabz
 *
 */
@Service
public class NoteServiceImplementation implements NoteService {
	@Autowired
	NoteMongoRepository noteRepository;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	ModelMapper mapper;

	@Autowired
	private LabelRepository labelRepository;

	Timer timer;
	Utility utility = new Utility();
	SecurityConfig security = new SecurityConfig();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	LocalDateTime now = LocalDateTime.now();
	public static final Logger logger = LoggerFactory.getLogger(NoteServiceImplementation.class);

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
	@Override
	public void createNote(NoteDto note, String jwtToken)
			throws LoginExceptionHandling, UserExceptionHandling, ToDoException {
		logger.info("creating note");
		String userId = utility.parseJwt(jwtToken).getSubject();
		Optional<User> user = userRepository.findById(userId);
		PreConditions.isPresentInDb(user.isPresent(), "user does not exist");
		PreConditions.checkNotNull(note.getTitle(), "title should not be empty");
		PreConditions.checkNotNull(note.getContent(), "content should not be empty");
		// PreConditions.checkNotNull(note);

		NoteModel newNote = mapper.map(note, NoteModel.class);
		newNote.setUserId(userId);

		newNote.setId(sdf.format(new Date()));
		newNote.setCreatedAt(dtf.format(now));
		newNote.setEditedAt(dtf.format(now));
		newNote = noteRepository.save(newNote);
		List<NoteModel> listV = noteRepository.findAll();
		/*
		 * if(newNote.getLabel()==null) {
		 * 
		 * }
		 * 
		 * System.out.println(listV); Stream<List<LabelDto>> labels =
		 * listV.stream().map(p -> p.getLabel()); Map<String, List<LabelDto>> x =
		 * labels.flatMap(Collection<LabelDto>::stream).collect(Collectors.groupingBy(p-
		 * >p.getLabelName())) ;
		 * 
		 * 
		 * // Map<String, List<LabelDto>> x =
		 * labels.stream().collect(Collectors.groupingBy(p -> p.getLabelName())); //
		 * Iterator<String> it = x.keySet().iterator(); while (it.hasNext()) { String
		 * type = it.next();
		 * 
		 * List<LabelDto> value = x.get(type); System.out.println(value);
		 * System.out.println(type);
		 * 
		 * System.out.println("-------------------------------");
		 */
		// }
		// labelRepository.save(x);
		logger.info("note created");

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
	@Override
	public void updateNote(String jwtToken, String id, UpdateNoteDto notes)
			throws LoginExceptionHandling, ToDoException {
		logger.info("updating the note");

		String userId = utility.parseJwt(jwtToken).getSubject();
		Optional<User> user = userRepository.findById(userId);
		PreConditions.isPresentInDb(user.isPresent(), "user does not exist");
		Optional<NoteModel> note = noteRepository.findById(id);
		PreConditions.isPresentInDb(note.isPresent(), "note does not exist");
		NoteModel note1 = mapper.map(notes, NoteModel.class);

		note1.setContent(notes.getContent());
		note1.setTitle(notes.getContent());
		note1.setEditedAt(dtf.format(now));

		noteRepository.save(note1);

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
	public void deleteNote(String id, String token) throws LoginExceptionHandling, ToDoException {
		logger.info("deleting the note");
		String userId = utility.parseJwt(token).getSubject();
		Optional<User> user = userRepository.findById(userId);
		Optional<NoteModel> note = noteRepository.findById(id);

		PreConditions.isPresentInDb(user.isPresent(), "user does not exist");
		PreConditions.isPresentInDb(note.isPresent(), "note does not exist");

		if (note.get().isTrash() == false) {

			throw new ToDoException("note cant be deleted permanently as it is not in trash");
		}
		noteRepository.deleteById(id);

	}

	/**
	 * archive the note
	 * 
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void archiveNote(String id) throws ToDoException {

		Optional<NoteModel> note = noteRepository.findById(id);
		PreConditions.isPresentInDb(note.isPresent(), "note does not exist");
		PreConditions.isPresentInDb(!note.get().isArchive(), "note is already archived");

		NoteModel notes = note.get();
		notes.setArchive(true);
		noteRepository.save(notes);
		logger.info("note archived");
	}

	/**
	 * method to set pin set the pin
	 * 
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void setPin(String id) throws ToDoException {
		Optional<NoteModel> note = noteRepository.findById(id);

		PreConditions.isPresentInDb(note.isPresent(), "note does not exist");
		PreConditions.isPresentInDb(!note.get().isPin(), "note is already pinned");
		NoteModel notes = note.get();
		notes.setPin(true);
		noteRepository.save(notes);
		logger.info("note is pinned");

	}

	/**
	 * trash the note
	 * 
	 * @param id
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void trashNote(String id) throws ToDoException {
		Optional<NoteModel> note = noteRepository.findById(id);

		PreConditions.isPresentInDb(note.isPresent(), "note does not exist");

		PreConditions.isPresentInDb(!note.get().isTrash(), "note is already trashed");

		NoteModel notes = note.get();
		notes.setTrash(true);
		noteRepository.save(notes);
	}

	/**
	 * restore the trashed note
	 * 
	 * @param id
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void restoreTrashedNote(String id) throws ToDoException {
		Optional<NoteModel> note = noteRepository.findById(id);

		PreConditions.isPresentInDb(note.isPresent(), "note does not exist");
		PreConditions.isPresentInDb(note.get().isTrash(),
				"note does not exist in trash it might have been restored or deleted ");

		NoteModel notes = note.get();
		notes.setTrash(false);
		noteRepository.save(notes);
	}

	@Override
	public void deleteTrashedNote(String id) {
		// TODO Auto-generated method stub

	}

	/**
	 * display all trashed notes
	 * 
	 * @param request
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public List<NoteModel> viewTrashNotes(String token) throws ToDoException {
		String userId = utility.parseJwt(token).getSubject();
		Optional<User> user = userRepository.findById(userId);
		PreConditions.isPresentInDb(user.isPresent(), "user does not exist");

		List<NoteModel> noteList = noteRepository.findByUserId(userId);
		List<NoteModel> viewNotes = new ArrayList<>();
		for (NoteModel note : noteList) {
			if (note.isTrash() == true) {
				viewNotes.add(note);
			}
		}

		return viewNotes;

	}

	/**
	 * display all archived notes
	 * 
	 * @param request
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public List<NoteModel> viewArchivedNotes(String token) throws ToDoException {
		String userId = utility.parseJwt(token).getSubject();
		Optional<User> user = userRepository.findById(userId);
		PreConditions.isPresentInDb(user.isPresent(), "user does not exist");

		List<NoteModel> noteList = noteRepository.findByUserId(userId);
		List<NoteModel> viewNotes = new ArrayList<>();
		for (NoteModel note : noteList) {
			if (note.isArchive() == true) {
				viewNotes.add(note);
			}
		}

		return viewNotes;

	}

	/**
	 * method to display all the notes
	 * 
	 * @param token
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public List<NoteModel> viewNotes(String token) throws ToDoException {

		String userId = utility.parseJwt(token).getSubject();
		Optional<User> user = userRepository.findById(userId);
		PreConditions.isPresentInDb(user.isPresent(), "user does not exist");

		List<NoteModel> noteList = noteRepository.findByUserId(userId);

		return noteList;

	}

	/**
	 * 
	 * @param noteId
	 * @param token
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public ViewNoteDto viewNote(String noteId, String token) throws ToDoException {
		Optional<NoteModel> note = validateNoteAndUser(noteId, utility.parseJwt(token).getSubject());
		if (note.get().isTrash()) {
			throw new ToDoException(
					"Note with given id could not be found or note you are looking for might have been trashed");
		}
		return mapper.map(note.get(), ViewNoteDto.class);

	}

	/**
	 * 
	 * @param id
	 * @param label
	 * @throws ToDoException
	 */
	public void addLabel(String id, String label) throws ToDoException {

		Optional<NoteModel> notes = noteRepository.findById(id);
		PreConditions.isPresentInDb(notes.isPresent(), "note does not exist");

		NoteModel note = new NoteModel();
		// note.setLabel(label);
		note.setEditedAt(dtf.format(now));
		noteRepository.save(note);
	}

	private Optional<NoteModel> validateNoteAndUser(String noteId, String id) throws ToDoException {

		Optional<User> user = userRepository.findById(id);
		PreConditions.isPresentInDb(user.isPresent(), "user does not exist");

		Optional<NoteModel> note = noteRepository.findById(id);
		PreConditions.isPresentInDb(note.isPresent(), "note does not exist");

		return note;
	}

	/**
	 * unarchive the note
	 * 
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void unarchiveNote(String id) throws ToDoException {

		Optional<NoteModel> note = noteRepository.findById(id);
		PreConditions.isPresentInDb(note.isPresent(), "note does not exist");
		PreConditions.isPresentInDb(note.get().isArchive(), "note is already unarchived");

		NoteModel notes = note.get();
		notes.setArchive(false);
		noteRepository.save(notes);
		logger.info("note unarchived");
	}

	/**
	 * unpin the note
	 * 
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void unPin(String id) throws ToDoException {

		Optional<NoteModel> note = noteRepository.findById(id);

		PreConditions.isPresentInDb(note.isPresent(), "note does not exist");
		PreConditions.isPresentInDb(note.get().isPin(), "note is already unpinned");
		NoteModel notes = note.get();
		notes.setPin(false);
		noteRepository.save(notes);
		logger.info("note is pinned");
	}

	/**
	 * set reminder
	 * 
	 * @param id
	 * @param token
	 * @param reminderTime
	 * @return
	 * @throws ToDoException
	 * @throws ParseException
	 */
	@Override
	public void remainder(String token, String noteId, String reminderTime) throws ToDoException, ParseException {
		String userId = utility.parseJwt(token).getSubject();
		Optional<User> user = userRepository.findById(userId);
		PreConditions.isPresentInDb(user.isPresent(), "user does not exist");

		List<NoteModel> noteList = noteRepository.findByUserId(userId);

		for (NoteModel note : noteList) {
			if (note.getId().equals(noteId)) {

				Date reminder = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(reminderTime);
				long timeDifference = reminder.getTime() - new Date().getTime();
				timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						logger.info("Reminder :" + note.getId().toString());
					}
				}, timeDifference);
			}
		}
	}

	/**
	 * create new label
	 * 
	 * @param labelName
	 * @param token
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void createNewLabel(String labelName, String token, String noteId) throws ToDoException {
		String userId = utility.parseJwt(token).getSubject();

		logger.info("creating label");
		Optional<User> user = userRepository.findById(userId);
		PreConditions.isPresentInDb(user.isPresent(), "user does not exist");

		Optional<NoteModel> note = noteRepository.findById(noteId);
		PreConditions.isPresentInDb(note.isPresent(), "note does not exist");
		List<NoteModel> noteList = noteRepository.findByUserId(userId);
		LabelDto label = new LabelDto();

		for (NoteModel n : noteList) {
			if (n.getId().equals(noteId)) {
				logger.info("label created");
				label.setLabelName(labelName);
				labelRepository.save(label);
				NoteModel notelabel = mapper.map(label, NoteModel.class);
				n.getLabel().add(label);
				noteRepository.save(n);

				System.out.println(notelabel);
				logger.info("label mapped to note");

			}

		}

	}

	public Iterable<LabelDto> getNotesOfLabel(String labelId, String userId) throws ToDoException {
		if (!userRepository.findById(userId).isPresent()) {
			throw new ToDoException("User not found");
		}
		LabelDto labelFound = labelRepository.findBy_id(labelId);
		if (labelFound == null) {
			throw new ToDoException("Label not found");
		}

		return null;

	}

	/**
	 * add new label to note
	 * 
	 * @param labelName
	 * @param token
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void addLabel(String labelId, String token, String noteId) throws ToDoException {
		String userId = utility.parseJwt(token).getSubject();

		logger.info("adding label");
		Optional<User> user = userRepository.findById(userId);
		PreConditions.isPresentInDb(user.isPresent(), "user does not exist");

		Optional<NoteModel> note = noteRepository.findById(noteId);
		PreConditions.isPresentInDb(note.isPresent(), "note does not exist");
		List<NoteModel> noteList = noteRepository.findByUserId(userId);

		// Check if note has a list of labels or not, if not ,then create a new List
		if (note.get().getLabel() == null) {
			System.out.println("----------------");
			List<LabelDto> newLabelList = new ArrayList<>();
			note.get().setLabel(newLabelList);
		}

		// check if label is present in labelRepository
		LabelDto labelFound = labelRepository.findBy_id(labelId);
		LabelDto label = new LabelDto();

		for (int i = 0; i < note.get().getLabel().size(); i++) {
			if (labelId.equals(note.get().getLabel().get(i).get_id())) {

				throw new ToDoException("Label already present");
			}
		}

		label.set_id(labelFound.get_id());
		label.setLabelName(labelFound.getLabelName());
		note.get().getLabel().add(label);
		noteRepository.save(note.get());

	}

	/**
	 * delete label
	 * 
	 * @param labelName
	 * @param token
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void deleteLabel(String labelId, String token) throws ToDoException {

		String userId = utility.parseJwt(token).getSubject();

		logger.info("adding label");
		Optional<User> user = userRepository.findById(userId);
		PreConditions.isPresentInDb(user.isPresent(), "user does not exist");
		LabelDto labelFound = labelRepository.findBy_id(labelId);
		if (labelFound == null) {
			throw new ToDoException("Label not found");
		}
		labelRepository.deleteById(labelId);
		List<NoteModel> noteList = noteRepository.findByUserBuy(userId, labelId);

		for (int i = 0; i < noteList.size(); i++) {

			for (int j = 0; j < noteList.get(i).getLabel().size(); j++) {

				if (labelId.equals(noteList.get(i).getLabel().get(j).get_id())) {
					noteList.get(i).getLabel().remove(j);
					NoteModel note = noteList.get(i);
					noteRepository.save(note);
					break;
				}

			}

		}
	}

	@Override
	public void demo(String labelName, String token, String noteId) {
		String userId = utility.parseJwt(token).getSubject();
		List<NoteModel> x = noteRepository.findByUserBuy(userId, "label");
		System.out.println(x);

	}
}
