package com.auth.server.repository;

import java.util.Map;

import com.auth.server.model.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.auth.library.exception.UserExistsException;
import com.auth.library.exception.UserNotFoundException;
import com.auth.server.model.AppUser;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private UserRepository userRepository;

    @Autowired
    private UserProviderRepository userProviderRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oauth2User = super.loadUser(userRequest);

		try {
			handleOAuth2User(oauth2User, userRequest.getClientRegistration().getRegistrationId());
		} catch (UserNotFoundException | UserExistsException e) {
			e.printStackTrace();

			throw new OAuth2AuthenticationException("Error while handling pauth user");
		}

		return oauth2User;
	}

	private void handleOAuth2User(OAuth2User user, String providerId) throws UserNotFoundException, UserExistsException {
		System.out.println("---------------------------------");
		System.out.println("Got oauth2 user => " + user);

		String id = user.getAttributes().get("id").toString();

		if(userRepository == null) {
			System.out.println("REPOSITORY IS NULL --------------");
			return;
		}
		if(userRepository.findById(id).isEmpty()) {
			AppUser appUser = getUserFromOAuth2User(user.getAttributes());
			System.out.println("Data to be added => " + appUser.getUserId());

			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			userRepository.save(appUser);

            UserProvider userProvider = new UserProvider();
            userProvider.setProviderId(providerId);
            userProvider.setUser(appUser);
            userProviderRepository.save(userProvider);

			System.out.println("Created user ................ from social login");

		}
		else {
			System.out.println("User with " + id + " already present");
		}
	}

	private AppUser getUserFromOAuth2User(Map<String, Object> attributes) {
		AppUser userDetails = new AppUser();

        //Handling only for github users ...
		userDetails.setUserId(attributes.get("id").toString());
		userDetails.setUserName(attributes.get("login").toString());
		userDetails.setFirstName(attributes.get("login").toString());
		userDetails.setLastName(attributes.get("login").toString().substring(0, 1));
		userDetails.setEmail(attributes.get("email") != null ? attributes.get("email").toString() : "");
		userDetails.setPassword("****(Other service account)");
		userDetails.setProfileImage(attributes.get("avatar_url").toString());

		return userDetails;
	}
}
