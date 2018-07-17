package com.todo.note.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.todo.note.userservice.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> 
{
	

}

