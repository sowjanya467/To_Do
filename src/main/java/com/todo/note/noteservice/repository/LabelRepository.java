package com.todo.note.noteservice.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.todo.note.dto.LabelDto;

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
public interface LabelRepository extends MongoRepository<LabelDto, String> {

	//List<LabelDto> findAllByUserId(String userId);

	LabelDto findByLabelName(String labelName);

	LabelDto findBy_id(String id);


//	LabelDto findByLabelNameAndUserId(String labelName, String userId);

}
