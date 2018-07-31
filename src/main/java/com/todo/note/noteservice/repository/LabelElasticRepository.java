package com.todo.note.noteservice.repository;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.todo.note.dto.Label;
public interface LabelElasticRepository extends ElasticsearchRepository<Label, String> {


}
