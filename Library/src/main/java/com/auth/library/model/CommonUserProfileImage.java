package com.auth.library.model;

import java.util.Arrays;

public class CommonUserProfileImage {
	
	private Long id;
	
	private CommonUserDetails userDetails;
	
	private String imageName;
	
	private String type;
	
	private byte[] imageBytes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CommonUserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(CommonUserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getImageBytes() {
		return imageBytes;
	}

	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}

	@Override
	public String toString() {
		return "CommonUserProfileImage [id=" + id + ", userDetails=" + userDetails + ", imageName=" + imageName
				+ ", type=" + type + ", imageBytes=" + Arrays.toString(imageBytes) + "]";
	}
	
}
