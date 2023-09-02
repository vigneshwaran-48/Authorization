package com.auth.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.library.model.CommonUserProfileImage;
import com.auth.library.service.UserProfileImageService;
import com.auth.resource.model.AppUser;
import com.auth.resource.model.UserProfileImage;
import com.auth.resource.repository.UserProfileImageRepository;
import com.google.common.base.Preconditions;

@Service
public class UserProfileImageServiceImpl implements UserProfileImageService {

	@Autowired
	private UserProfileImageRepository userProfileImageRepository;
	private static final String PROFILE_IMAGE = "profile.png";
	
	@Override
	public byte[] getImage(String userId, String imageName) {
		byte[] profileImageBytes = null;
		UserProfileImage profileImage = userProfileImageRepository
				.findByImageNameAndAppUserId(imageName, userId).orElse(getDefaultImage());
		
		profileImageBytes = profileImage.getImageBytes();
		return profileImageBytes;
	}
	
	@Override
	public void uploadImage(CommonUserProfileImage userImage) throws Exception {
		
		Preconditions.checkArgument(userImage != null, "profile image details is null");
		Preconditions.checkArgument(userImage.getUserDetails() != null, "user details is null");
		
		UserProfileImage userProfileImage = getUserImage(userImage.getUserDetails().getId());
		
		if(userProfileImage == null) {
			userProfileImage = new UserProfileImage();
			userProfileImage.setUser(AppUser.toAppUser(userImage.getUserDetails()));
		}
		userProfileImage.setImageBytes(userImage.getImageBytes());
		userProfileImage.setImageName(userImage.getImageName());
		userProfileImage.setType(userImage.getType());
		
		UserProfileImage uploadedImage = userProfileImageRepository.save(userProfileImage);
		if(uploadedImage == null) {
			throw new Exception("Error while uploading image to DB");
		}
	}
	private UserProfileImage getUserImage(String id) {
		
		return userProfileImageRepository.findByImageNameAndAppUserId(PROFILE_IMAGE, id)
											.orElse(null);
	}
	private UserProfileImage getDefaultImage() {
		System.out.println("Getting default image");
		UserProfileImage userProfileImage = userProfileImageRepository.findById(-1L).orElseThrow();
		return userProfileImage;
	}
}	
