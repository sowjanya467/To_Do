package com.todo.note.noteservice.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.todo.note.noteservice.model.NoteModel;

/*************************************************************************************************************
 *
 * purpose:Note repository that extends Mongo repository Mongo repository
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 18-07-18
 *
 **************************************************************************************************/

@Repository
public interface NoteMongoRepository extends MongoRepository<NoteModel, String> {
	public List<NoteModel> findByemailId(String id);

	public Optional<NoteModel> findBytitle(String title);

	public Optional<NoteModel> deleteBytitle(String title);


}
