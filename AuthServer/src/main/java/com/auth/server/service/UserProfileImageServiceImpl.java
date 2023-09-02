package com.auth.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.library.model.CommonUserProfileImage;
import com.auth.library.service.UserProfileImageService;
import com.auth.server.model.UserProfileImage;
import com.auth.server.repository.UserProfileImageRepository;

@Service
public class UserProfileImageServiceImpl implements UserProfileImageService {

	@Autowired
	private UserProfileImageRepository userProfileImageRepository;
	
	@Override
	public byte[] getImage(String userId, String imageName) {
		byte[] profileImageBytes = null;
		UserProfileImage profileImage = userProfileImageRepository
				.findByImageNameAndAppUserId(imageName, userId).orElse(getDefaultImage());
		
		profileImageBytes = profileImage.getImageBytes();
		return profileImageBytes;
	}
	
	@Override
	public void uploadImage(CommonUserProfileImage userImage) {
		// TODO Auto-generated method stub
		
	}
	private UserProfileImage getDefaultImage() {
		UserProfileImage userProfileImage = userProfileImageRepository.findById(-1L).orElseThrow();
		return userProfileImage;
	}
}	
