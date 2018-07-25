package com.todo.note.dto;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

public class ViewNoteDto {
	String title;
	String content;
	String noteId;
	@CreatedDate
	private String createdAt;
	@LastModifiedDate
	private String editedAt;

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getEditedAt() {
		return editedAt;
	}

	public void setEditedAt(String editedAt) {
		this.editedAt = editedAt;
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

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
}
