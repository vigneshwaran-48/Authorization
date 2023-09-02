package com.auth.resource.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.library.exception.UserExistsException;
import com.auth.library.exception.UserNotFoundException;
import com.auth.library.model.CommonUserDetails;
import com.auth.library.service.AppUserService;
import com.auth.library.service.UserProfileImageService;
import com.auth.resource.model.AppUser;
import com.auth.resource.model.Authority;
import com.auth.resource.repository.UserRepository;
import com.google.common.base.Preconditions;

@Service
public class AppUserServiceImpl implements AppUserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Value("${app.baseurl}")
	private String appbaseUrl;

	@Value("${app.default.profile_image}")
	private String defaultProfileImage;
	
	private String controllerEndpoint = "/api/user";
	
	private static final String USERNOTFOUND = "User Not Found for ";
	private static final Logger LOG = LoggerFactory.getLogger(AppUserServiceImpl.class);
	
	@Override
	public String createUser(CommonUserDetails user) throws UserExistsException {
		Preconditions.checkArgument(user != null, "User data can't be empty");
		AppUser appUser = AppUser.toAppUser(user);
		
		try {
			if(findByUserName(appUser.getUserName()) != null) {
				throw new UserExistsException("User name exists");
			}
		} catch (UserNotFoundException e) {
			LOG.info("User not exist with given details, Proceeding to create a user.");
		}
		if(appUser.getId() == null) {
			UUID uuid = UUID.randomUUID();
			appUser.setId(uuid.toString());
		}
		appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
		if(appUser.getDob() == null) {
			appUser.setDob(LocalDate.now());
		}
		if(appUser.getProfileImage() == null || appUser.getProfileImage().isBlank()) {
			appUser.setProfileImage(appbaseUrl + "/api/user/" + appUser.getId() + "/profile-image");
		}
		AppUser createdUser = userRepository.save(appUser);
		
		if(createdUser == null) {
			throw new InternalError("Error in creating the user");
		}
		return createdUser.getId();
	}
	
	@Override
	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}

	@Override
	public void updateUser(CommonUserDetails user) throws UserNotFoundException {
		Preconditions.checkArgument(user != null, "User data can't be empty");
		
		AppUser appUser = AppUser.toAppUser(user);
		CommonUserDetails prevUserData = findByUserId(appUser.getId());
		
		if(prevUserData == null ) {
			throw new UserNotFoundException("User not found");
		}
		
		checkAndUpdateUser(AppUser.toAppUser(prevUserData), appUser);
		
		userRepository.save(appUser);
	}

	@Override
	public CommonUserDetails findByUserId(String id) throws UserNotFoundException {
		Preconditions.checkArgument(id != null && !id.isBlank(), "User id can't be empty");
		AppUser user = userRepository.findById(id)
									 .orElseThrow(() -> new UserNotFoundException(USERNOTFOUND + id));

		if(user.getProfileImage() == null) {
			user.setProfileImage(appbaseUrl + controllerEndpoint + "/me/profile-image");
		}
		
		return AppUser.toCommonUserDetails(user);
	}

	@Override
	public CommonUserDetails findByUserName(String name) throws UserNotFoundException {
		Preconditions.checkArgument(name != null && !name.isBlank(), "User name can't be empty");
		AppUser user = userRepository.findByUserName(name);
		if(user == null) {
			throw new UserNotFoundException(USERNOTFOUND);
		}
		if(user.getProfileImage() == null) {
			user.setProfileImage(appbaseUrl + controllerEndpoint + "/me/profile-image");
		}
		return AppUser.toCommonUserDetails(user);
	}
	
	@Override
	public CommonUserDetails findByUserEmail(String email) throws UserNotFoundException {
		AppUser user = userRepository.findByEmail(email)
								.orElseThrow(() -> new UserNotFoundException("Email not found"));
		
		CommonUserDetails userDetails = null;
		if(user != null) {
			userDetails = AppUser.toCommonUserDetails(user);
		}
		if(user.getProfileImage() == null) {
			user.setProfileImage(appbaseUrl + controllerEndpoint + "/me/profile-image");
		}
		return userDetails;
	}

	private void checkAndUpdateUser(AppUser oldData, AppUser newData) {
		if(newData.getUserName() == null || newData.getUserName().isBlank()) {
			newData.setUserName(oldData.getUserName());
		}
		if(newData.getFirstName() == null || newData.getFirstName().isBlank()) {
			newData.setFirstName(oldData.getFirstName());
		}
		if(newData.getLastName() == null || newData.getLastName().isBlank()) {
			newData.setLastName(oldData.getLastName());
		}
		if(newData.getEmail() == null || newData.getEmail().isBlank()) {
			newData.setEmail(oldData.getEmail());
		}
		if(newData.getProfileImage() == null || newData.getProfileImage().isBlank()) {
			newData.setProfileImage(oldData.getProfileImage());
		}
		if(newData.getDob() == null && oldData.getDob() != null) {
			newData.setDob(oldData.getDob());
		}
		if(newData.getAge() < 0) {
			newData.setAge(oldData.getAge());
		}
		if(newData.getMobile() == null || newData.getMobile().isBlank()) {
			newData.setMobile(oldData.getMobile());
		}
		if(newData.getPassword() == null || newData.getPassword().isBlank()) {
			newData.setPassword(oldData.getPassword());
		}
	}
}
