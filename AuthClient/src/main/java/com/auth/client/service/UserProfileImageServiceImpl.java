package com.auth.client.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.auth.library.model.CommonUserProfileImage;
import com.auth.library.service.UserProfileImageService;

import reactor.core.publisher.Mono;

@Service
public class UserProfileImageServiceImpl implements UserProfileImageService {

	@Value("${resource.server.baseurl}")
	private String resourceServerDomain;
	
	@Autowired
	private WebClient webClient;
	
	private static final Logger LOG = LoggerFactory.getLogger(UserProfileImageServiceImpl.class);
	
	@Override
	public byte[] getImage(String userId, String imageName) {
		Mono<byte[]> userImageBytesMono = webClient.get()
												   .uri(resourceServerDomain + 
														   "/api/user/me/profile-image")
												   .retrieve()
												   .bodyToMono(byte[].class);
		return userImageBytesMono.block();
	}

	@Override
	public void uploadImage(CommonUserProfileImage userImage) {
		MultipartBodyBuilder builder = 	new MultipartBodyBuilder();
		builder.part("profileImage", userImage.getImageBytes()).filename("profile.png");
				
		String endpoint = "/api/user/" + userImage.getUserDetails().getId() + "/profile-image";
		
		Mono<Map> response =  webClient.put()
									 .uri(resourceServerDomain + endpoint)
									 .contentType(MediaType.MULTIPART_FORM_DATA)
									 .body(BodyInserters.fromMultipartData(builder.build()))
									 .retrieve()
									 .bodyToMono(Map.class);
		
		LOG.info("response got from resource server => " + response.block());
	}

}
