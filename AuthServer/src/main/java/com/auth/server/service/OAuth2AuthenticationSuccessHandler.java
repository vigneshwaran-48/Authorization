//package com.auth.server.service;
//
//import java.io.IOException;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.core.oidc.user.OidcUser;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import com.auth.library.exception.UserExistsException;
//import com.auth.library.exception.UserNotFoundException;
//import com.auth.library.model.CommonUserDetails;
//import com.auth.library.service.AppUserService;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
////TODO Need to implement this class in a efficient way by handling exceptions
//public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//	private AuthenticationSuccessHandler delegate = new SavedRequestAwareAuthenticationSuccessHandler();
//
//	@Autowired
//	private AppUserService userService;
//
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//			Authentication authentication) throws IOException, ServletException {
//
//		if(authentication instanceof OAuth2AuthenticationToken) {
//
//			if(authentication.getPrincipal() instanceof OidcUser) {
//				handleOidcUser((OidcUser) authentication.getPrincipal());
//			}
//			else if(authentication.getPrincipal() instanceof OAuth2User) {
//				try {
//					handleOAuth2User((OAuth2User) authentication.getPrincipal());
//				} catch (UserNotFoundException e) {
//					e.printStackTrace();
//				} catch (UserExistsException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//		delegate.onAuthenticationSuccess(request, response, authentication);
//	}
//
//	private void handleOidcUser(OidcUser user) {
//		System.out.println("Got oidc user => " + user);
////		if(userService.findByUserId(user.getI))
//	}
//	private void handleOAuth2User(OAuth2User user) throws UserNotFoundException, UserExistsException {
//		System.out.println("---------------------------------");
//		System.out.println("Got oauth2 user => " + user);
//
//		String id = (String) user.getAttributes().get("id");
////
//		if(userService == null) {
//			System.out.println("SERVICE IS NULL --------------");
//			return;
//		}
//		if(userService.findByUserId(id) == null) {
////			CommonUserDetails userDetails = getUserFromOAuth2User(user.getAttributes());
////			userService.createUser(userDetails);
//
//			System.out.println("Created user ................ from social login");
//			System.exit(0);
//		}
//	}
//
//	private CommonUserDetails getUserFromOAuth2User(Map<String, Object> attributes) {
//		CommonUserDetails userDetails = new CommonUserDetails();
//
//		userDetails.setId((String) attributes.get("id"));
//		userDetails.setUserName(attributes.get("login").toString());
//		userDetails.setFirstName(attributes.get("login").toString());
//		userDetails.setLastName(attributes.get("login").toString().substring(0, 1));
//		userDetails.setEmail(attributes.get("email") != null ? attributes.get("email").toString() : "");
//		userDetails.setPassword("****(Other service account)");
//		userDetails.setProfileImage(attributes.get("avatar_url").toString());
//
//		return userDetails;
//	}
//}
