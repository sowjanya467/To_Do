package com.todo.note.dto;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.elasticsearch.annotations.Document;

import io.swagger.annotations.ApiModelProperty;

//@Document(collection = "label")
@Document(indexName="labelelasticsearch",type="labels")

public class Label implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@ApiModelProperty(hidden=true)
	private String labelId;
	private String labelName = "";
	@ApiModelProperty(hidden=true)
	private String userid;

	

	

	

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}


	@Override
	public String toString() {
		return "LabelDto [_id=" + labelId + ", labelName=" + labelName + "]";
	}

}
