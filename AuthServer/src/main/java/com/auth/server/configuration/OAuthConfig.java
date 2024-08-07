package com.auth.server.configuration;

import static org.springframework.security.config.Customizer.withDefaults;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.auth.server.filter.PreAuthFilter;
import com.auth.server.repository.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class OAuthConfig {

	@Autowired
	private CustomOAuth2UserService userService;
	@Bean
	@Order(1)
	SecurityFilterChain asSecurityFilterChain(HttpSecurity http) throws Exception {

		OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

		return http
				.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
				.oidc(withDefaults())
				.and()
				.exceptionHandling(e -> e
						.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/oauth")))
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
				.build();

	}

	@Bean
	@Order(2)
	SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
		return http
				.addFilterBefore(new PreAuthFilter(), AbstractPreAuthenticatedProcessingFilter.class)
				.authorizeHttpRequests(authorize ->
					{
						try {
							authorize
								.requestMatchers("/", "/index.html", "/static/**",
										"/*.ico", "/*.json", "/*.png", "/*.jpg",
										"/*jpeg", "/*.html", "/authenticate"
										)
								.permitAll()
								.anyRequest().authenticated()
								.and()
								.formLogin()
									.loginPage("/oauth")
									.loginProcessingUrl("/authenticate")
									.usernameParameter("name")
									.passwordParameter("password")
									// .successHandler((req, resp, auth) -> {
									// 	System.out.println(req.getHeader("Referer"));
									// 	System.out.println(req.getHeaderNames());
									// 	System.out.println(auth.getDetails());
									// 	System.out.println(auth.isAuthenticated());
									// 	System.out.println(auth.getPrincipal());
									// 	for(int i = 0; i < 10; i ++) {
									// 		System.out.println("Test");
									// 	}

									// 	System.out.println(auth.getCredentials());

									// 	resp.sendRedirect("https://google.com");
									// })
									.permitAll()
								.and()
									.csrf().disable()
									.logout(l -> l
											.invalidateHttpSession(true)
											.deleteCookies()
											.logoutSuccessUrl("/"));
						} catch (Exception e) {
							e.printStackTrace();
						}
					})
				.oauth2Login()
					.loginPage("/oauth")
					.userInfoEndpoint()
						.userService(userService)
					.and()
					.and()
					.csrf().disable()
				.build();

	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public TokenSettings tokenSettings() {
		return TokenSettings.builder()
				.accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
				.accessTokenTimeToLive(Duration.ofDays(1))
				.build();
	}

	@Bean
	public ClientSettings clientSettings() {
		return ClientSettings.builder()
				.requireProofKey(false)
				.requireAuthorizationConsent(true)
				.build();
	}

	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();
	}

	@Bean
	JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	@Bean
	JWKSource<SecurityContext> jwkSource() {
		RSAKey rsaKey = generateRsa();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
	}

	public static RSAKey generateRsa() {
		KeyPair keyPair = generateRsaKey();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		return new RSAKey.Builder(publicKey).privateKey(privateKey)
						.keyID(UUID.randomUUID().toString()).build();
	}

	static KeyPair generateRsaKey() {
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}

	@Bean
	OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
		return context -> {
			Authentication principal = context.getPrincipal();
			if (context.getTokenType().getValue().equals("id_token")) {
				context.getClaims().claim("Test", "Test Id Token");
			}
			if (context.getTokenType().getValue().equals("access_token")) {
				context.getClaims().claim("Test", "Test Access Token");
				Set<String> authorities = principal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
                context.getClaims().claim("authorities", authorities)
                        .claim("user", principal.getName());
			}

		};
	}
}