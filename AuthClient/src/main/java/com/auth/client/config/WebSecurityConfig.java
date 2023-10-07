package com.auth.client.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SimpleSavedRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.auth.client.controller.CsrfFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private static final Long MAX_AGE = 3600L;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.cors()
					.configurationSource(corsConfigurationSource())
				.and()
				.csrf((csrf) -> csrf
		                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
		            )
				.addFilterAfter(new CsrfFilter(), BasicAuthenticationFilter.class)
				.authorizeHttpRequests(request -> {
					try {
						request
							.requestMatchers("/static/**",
			                    "/*.ico", "/*.json", "/*.png", "/ping",
									"/logout-success")
							.permitAll()
							.anyRequest()
							.authenticated()
							.and()
							.oauth2Login()							
							.permitAll()
								.and()
								.logout()
								.clearAuthentication(true)
								.deleteCookies()
								.invalidateHttpSession(true)
								.logoutSuccessUrl("/logout-success");
					} catch (Exception e) {
						e.printStackTrace();
					}
			   })				
			.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(Arrays.asList("http://127.0.0.1:3000", "http://localhost:9090"));
		config.setAllowedHeaders(Arrays.asList(
					HttpHeaders.AUTHORIZATION,
					HttpHeaders.CONTENT_TYPE,
					HttpHeaders.ACCEPT,
					"X-XSRF-TOKEN"
				));
		config.setAllowedMethods(Arrays.asList(
					HttpMethod.GET.name(),
					HttpMethod.POST.name(),
					HttpMethod.PUT.name(),
					HttpMethod.DELETE.name(),
					HttpMethod.OPTIONS.name()
				));
		config.setMaxAge(MAX_AGE);
		source.registerCorsConfiguration("/**", config);	
		
		return source;
	}

	@Bean
    WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        
        int size = 16 * 1024 * 1024 * 2;
        ExchangeStrategies strategies = ExchangeStrategies.builder()
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
            .build();
        
        oauth2Client.setDefaultOAuth2AuthorizedClient(true);
        return WebClient.builder()
        		.filter(oauth2Client)
        		.exchangeStrategies(strategies)
                .build();
    }

    @Bean
    OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .authorizationCode()
                        .refreshToken()
                        .build();
        DefaultOAuth2AuthorizedClientManager authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

    @Bean
    RequestCache refererRequestCache() {
        return new HttpSessionRequestCache() {
            @Override
            public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
                String referrer = request.getHeader("referer");
                if (referrer == null) {
                    referrer = request.getRequestURL().toString();
					System.out.println("Setting referrer as => " + referrer);
                }
                request.getSession().setAttribute("SPRING_SECURITY_SAVED_REQUEST",
                    new SimpleSavedRequest(referrer));
            }
        };
    }
}