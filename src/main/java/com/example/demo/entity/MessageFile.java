package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class MessageFile {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	int messageId;
	String message;
	String query;
	String start_line;
	String end_line;
	String description;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getStart_line() {
		return start_line;
	}

	public void setStart_line(String start_line) {
		this.start_line = start_line;
	}

	public String getEnd_line() {
		return end_line;
	}

	public void setEnd_line(String end_line) {
		this.end_line = end_line;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
