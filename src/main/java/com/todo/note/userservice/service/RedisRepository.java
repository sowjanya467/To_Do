package com.todo.note.userservice.service;

@SuppressWarnings("hiding")
public interface RedisRepository<String,User> {

	public void setToken(String jwtToken);

	public String getToken(String userId);


}
