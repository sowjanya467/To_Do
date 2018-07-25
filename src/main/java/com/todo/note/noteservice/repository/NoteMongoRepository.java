package com.todo.note.noteservice.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
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


	public List<NoteModel> findByUserId(String id);

	public Optional<NoteModel> findBytitle(String title);

	public Optional<NoteModel> deleteBytitle(String title);

	Optional<NoteModel> findById(String id);


	NoteModel findByUserIdAndId(String UserId, String noteId);


	void save(Optional<NoteModel> note);

	

	@Query(value = "{ 'userId' : ?0, 'label.labelName' : ?1 }"/*, fields = "{ 'label.labelName' : 1 }"*/)
	public List<NoteModel> findByUserBuy(String id,String name);


}
