package com.todo.note.noteservice.model;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.LastModifiedDate;

public class NoteDetails 
{
	@NotBlank
	private String title;
	
	@NotBlank
	private String content;
	@LastModifiedDate
	private String updatedAt;
	

	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}