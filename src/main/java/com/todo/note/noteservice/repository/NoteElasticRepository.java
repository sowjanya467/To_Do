package com.todo.note.noteservice.repository;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.todo.note.noteservice.model.NoteModel;
public interface NoteElasticRepository extends ElasticsearchRepository<NoteModel, String> {

	
}
