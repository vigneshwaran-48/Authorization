package com.auth.library.service;

import com.auth.library.exception.UserExistsException;
import com.auth.library.exception.UserNotFoundException;
import com.auth.library.model.CommonUserDetails;

public interface AppUserService {

	String createUser(CommonUserDetails appUser) throws UserExistsException;
	
	void deleteUser(String userId);
	
	void updateUser(CommonUserDetails appUser) throws UserNotFoundException, Exception;
	
	CommonUserDetails findByUserId(String id) throws UserNotFoundException;
	
	CommonUserDetails findByUserName(String name) throws UserNotFoundException;
	
	CommonUserDetails findByUserEmail(String email) throws UserNotFoundException;
}
