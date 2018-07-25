package com.todo.note.dto;

import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;

@Document(collection = "label")
public class LabelDto {

	@ApiModelProperty(hidden=true)
	private String _id;
	private String labelName = "";

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}


	@Override
	public String toString() {
		return "LabelDto [_id=" + _id + ", labelName=" + labelName + "]";
	}

}
