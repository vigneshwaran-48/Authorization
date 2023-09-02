package com.auth.library.dto;

import java.util.Map;

import org.springframework.lang.Nullable;

import com.auth.library.model.CommonUserDetails;

public class UserControllerResponse {

	private Map<String, String> message;
	@Nullable 
	private CommonUserDetails user;
	
	public Map<String, String> getMessage() {
		return message;
	}
	public void setMessage(Map<String, String> message) {
		this.message = message;
	}
	public CommonUserDetails getUser() {
		return user;
	}
	public void setUser(CommonUserDetails user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "UserControllerResponse [message=" + message + ", user=" + user + "]";
	}
	public UserControllerResponse(Map<String, String> message, CommonUserDetails user) {
		super();
		this.message = message;
		this.user = user;
	}
	public UserControllerResponse() {}
}
