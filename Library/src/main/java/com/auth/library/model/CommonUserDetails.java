package com.auth.library.model;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class CommonUserDetails {

	@NonNull 
	private String userName; 
	
	@Nullable 
	private String id;
	
	@NonNull 
	private String password;
	
	@Nullable 
	@DateTimeFormat(iso = ISO.DATE) 
	private LocalDate dob;
	
	@Nullable
	private String profileImage;
	
	@NonNull
	private String email;
	
	@Nullable
	private String mobile;
	
	@Nullable
	private String firstName;
	
	@Nullable
	private String lastName;

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "CommonUserDetails [userName=" + userName + ", id=" + id + ", password=" + password + ", dob=" + dob
				+ ", profileImage=" + profileImage + ", email=" + email + ", mobile=" + mobile + ", firstName="
				+ firstName + ", lastName=" + lastName + "]";
	}

}
