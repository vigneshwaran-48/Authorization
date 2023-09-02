package com.auth.library.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.auth.library.model.CommonClientDetails;

public class ClientControllerResponse {

	private int status;
	private String message;
	private LocalDateTime timestamp;
	private String path;
	private List<CommonClientDetails> data;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public List<CommonClientDetails> getData() {
		return data;
	}
	public void setData(List<CommonClientDetails> data) {
		this.data = data;
	}
	public ClientControllerResponse(int status, String message, LocalDateTime timestamp, List<CommonClientDetails> data, String path) {
		super();
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
		this.data = data;
		this.path = path;
	}
	
	public ClientControllerResponse() {}
}
