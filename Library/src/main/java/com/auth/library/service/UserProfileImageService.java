package com.auth.library.service;

import org.springframework.stereotype.Service;

import com.auth.library.model.CommonUserProfileImage;

@Service
public interface UserProfileImageService {

	byte[] getImage(String userId, String imageName);
	
	void uploadImage(CommonUserProfileImage userImage) throws Exception;
}
