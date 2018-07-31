package com.todo.note.noteservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.todo.note.dto.Label;

/*************************************************************************************************************
 *
 * purpose: label repository that extends Mongo repository Mongo repository
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 23-07-18
 *
 **************************************************************************************************/

@Repository
public interface LabelRepository extends MongoRepository<Label, String> {

	 List<Label> findAllByUserid(String userId);

	Optional<Label> findByLabelName(String name);

	Label findByLabelId(String id);

	void save(Optional<Label> labelFound);



}
