package com.todo.note.noteservice.model;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection = "notedb")
@JsonIgnoreProperties(value = { "createdAt", "editedAt" }, allowGetters = true)
public class NoteModel {

	@Id
	private String id;

	@NotBlank
	private String emailId;

	@NotBlank
	private String title;
	@NotBlank
	private String content;
	@CreatedDate
	private String createdAt;
	@LastModifiedDate
	private String editedAt;
	
	private String pin;
	private String label;
	private String archive;
	

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getArchive() {
		return archive;
	}

	public void setArchive(String archive) {
		this.archive = archive;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
