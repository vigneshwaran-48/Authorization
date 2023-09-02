package com.auth.server.service;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.auth.library.exception.UserExistsException;
import com.auth.library.exception.UserNotFoundException;
import com.auth.library.model.CommonUserDetails;
import com.auth.library.service.AppUserService;
import com.auth.server.model.AppUser;
import com.auth.server.model.UserPrincipal;
import com.auth.server.repository.UserRepository;

@Service
public class UserDetailsManagerService implements UserDetailsManager, AppUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Loading user ..................");
		AppUser appUser = userRepository.findByUserName(username);
		
		if(appUser == null) {
			appUser = userRepository.findByEmail(username)
									.orElseThrow(
											() -> new UsernameNotFoundException("User not found"));
		}
		
		Collection<GrantedAuthority> authorities = new HashSet<>();

		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		
		String name = appUser.getUserId() != null ? appUser.getUserId().toString() : String.valueOf(-1);
		
		UserPrincipal user = new UserPrincipal();
		user.setUserName(name);
		user.setAuthorities(authorities);
		user.setPassword(appUser.getPassword());
		return user;
	}

	@Override
	public void createUser(UserDetails user) {
		AppUser appUser = toAppUser(user);
		userRepository.save(appUser);
	}

	@Override
	public void updateUser(UserDetails user) {
		AppUser appUser = toAppUser(user);
		userRepository.save(appUser);
	}

	@Override
	public void deleteUser(String username) {
		userRepository.deleteByUserName(username);
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean userExists(String username) {
		System.out.println("finding user ......................");
		AppUser appUser = userRepository.findByUserName(username);
		if(appUser != null && appUser.getUserName().equals(username)) {
			return true;
		}
		return false;
	}

	private AppUser toAppUser(UserDetails user) {
//		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
//		
//		Set<Authority> appAuthorities = authorities.stream().map(auth -> {
//											String authStr = auth.getAuthority();
//											return authorityRepository
//													.findByAuthority(authStr);
//									 	 })
//										 .collect(Collectors.toSet());
//		
		AppUser appUser = new AppUser();
		
		appUser.setUserName(user.getUsername());
		appUser.setPassword(user.getPassword());		
		
		return appUser;
	}

	@Override
	public String createUser(CommonUserDetails user) throws UserExistsException {
		AppUser appUser = AppUser.toAppUser(user);
		
		if(userExists(appUser.getUserName())) {
			throw new UserExistsException("User name exists");
		}
		appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
//		appUser.setAccountNonExpired(true);
//		appUser.setAccountNonLocked(true);
//		appUser.setEnabled(true);
//		appUser.setAuthorities(Set.of(new Authority("1", "ADMIN")));
//		appUser.setCredentialsNonExpired(true);
		AppUser createdUser = userRepository.save(appUser);
		
		if(createdUser == null) {
			throw new InternalError("Error in creating the user");
		}
		return createdUser.getUserId();
	}


	@Override
	public void updateUser(CommonUserDetails user) {
		AppUser appUser = AppUser.toAppUser(user);
		
		if(!userExists(appUser.getUserName()) && findByUserId(appUser.getUserId()) == null) {
			throw new UsernameNotFoundException("User not found");
		}
		userRepository.save(appUser);
	}

	@Override
	public CommonUserDetails findByUserId(String id) {
		AppUser user = userRepository.findById(id)
									 .orElse(null);
		return user != null ? AppUser.toCommonUserDetails(user) : null;
	}

	@Override
	public CommonUserDetails findByUserName(String name) {
		AppUser user = userRepository.findByUserName(name);
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
		return userDetails;
	}
}
