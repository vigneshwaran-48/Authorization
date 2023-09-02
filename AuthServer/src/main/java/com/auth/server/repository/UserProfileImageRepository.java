package com.auth.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.server.model.UserProfileImage;

public interface UserProfileImageRepository extends JpaRepository<UserProfileImage, Long> {
	
	Optional<UserProfileImage> findByImageName(String imageName);
	
	Optional<UserProfileImage> findByImageNameAndAppUserId(String imageName, String userId);
}
