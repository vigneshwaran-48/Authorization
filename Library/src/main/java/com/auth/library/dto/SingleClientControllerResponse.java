package com.auth.library.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.auth.library.model.CommonClientDetails;

public class SingleClientControllerResponse {
	
	private int status;
	private String message;
	private LocalDateTime timestamp;
	private String path;
	private CommonClientDetails client;

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
	public CommonClientDetails getClient() {
		return client;
	}

	public void setClient(CommonClientDetails client) {
		this.client = client;
	}
	
	public SingleClientControllerResponse(int status, String message, LocalDateTime timestamp, CommonClientDetails client, String path) {
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
		this.client = client;
		this.path = path;
	}
	public SingleClientControllerResponse() {}

	@Override
	public String toString() {
		return "SingleClientControllerResponse{" +
				"status=" + status +
				", message='" + message + '\'' +
				", timestamp=" + timestamp +
				", path='" + path + '\'' +
				", client=" + client +
				'}';
	}
}
