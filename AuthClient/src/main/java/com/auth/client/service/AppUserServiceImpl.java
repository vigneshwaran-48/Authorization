package com.auth.client.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.auth.library.dto.UserControllerResponse;
import com.auth.library.exception.UserExistsException;
import com.auth.library.exception.UserNotFoundException;
import com.auth.library.model.CommonUserDetails;
import com.auth.library.service.AppUserService;

import reactor.core.publisher.Mono;

@Service
public class AppUserServiceImpl implements AppUserService {

	@Value("${resource.server.baseurl}")
	private String resourceServerDomain;
	
	@Value("${app.baseurl}")
	private String appBaseUrl;
	
	@Autowired
	private WebClient webClient;
	
	private static final Logger LOG = LoggerFactory.getLogger(AppUserServiceImpl.class);

	@Override
	public String createUser(CommonUserDetails appUser) throws UserExistsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(CommonUserDetails appUser) throws Exception {
		WebClient.ResponseSpec response = webClient.put()
									.uri(resourceServerDomain + "/api/user")
									.body(Mono.just(appUser), CommonUserDetails.class)
									.retrieve();

		Mono<Map> data = response.bodyToMono(Map.class);
		
		HttpStatusCode status = response.toBodilessEntity().block().getStatusCode();
		
		if(status == HttpStatus.BAD_REQUEST) {
			throw new IllegalArgumentException(data.block().get("message").toString());
		}
		else if(status == HttpStatus.INTERNAL_SERVER_ERROR) {
			throw new Exception(data.block().get("message").toString());
		}
		else {
			LOG.info("updated user succesfully via REST API call");
		}
	}

	@Override
	public CommonUserDetails findByUserId(String id) {

		 Mono<UserControllerResponse> response = webClient.get()
													.uri(resourceServerDomain + "/api/user/me")
													.retrieve()
													.bodyToMono(UserControllerResponse.class);
		 
		 CommonUserDetails commonUserDetails = response.block().getUser();
		
		 return commonUserDetails;
	}

	@Override
	public CommonUserDetails findByUserName(String name) {
		Mono<UserControllerResponse> response = webClient.get()
				.uri(resourceServerDomain + "/api/user/me")
				.retrieve()
				.bodyToMono(UserControllerResponse.class);
		
		CommonUserDetails commonUserDetails = response.block().getUser();
		
		 return commonUserDetails;
	}
	
	@Override
	public CommonUserDetails findByUserEmail(String email) throws UserNotFoundException {
		//TODO should implement as RESt API call.
		return null;
	}

}
