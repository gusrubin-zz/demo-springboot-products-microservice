package com.abinbev.ecommerce.products.web.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebAuthenticationProvider implements AuthenticationProvider {

	private final AuthUserDetailsService authUserDetailsService;
	private final PasswordEncoder passwordEncoder;
	private static final String BASIC_AUTHORIZATION_PREFIX = "basic";

	@Autowired
	public WebAuthenticationProvider(AuthUserDetailsService authUserDetailsService, PasswordEncoder passwordEncoder) {
		this.authUserDetailsService = authUserDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		log.info("[authentication]=(username=" + username + ", password=" + password + ")");

		UserDetails user = authUserDetailsService.loadUserByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid Login and/or Password");
		}

		if (!user.isEnabled()) {
			throw new DisabledException("The user is disabled");
		}

		if (!passwordEncoder.matches(password, user.getPassword())) {

			throw new UsernameNotFoundException("Invalid Login and/or Password");
		}

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		return new UsernamePasswordAuthenticationToken(username, password, authorities);
	}

	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}

	public BasicCredential getBasicCredentialFromHeader(String authorizationHeader) {
		String base64Credentials = authorizationHeader.substring(BASIC_AUTHORIZATION_PREFIX.length()).strip();
		byte[] decodedCredentials = Base64.getDecoder().decode(base64Credentials);
		String[] credentials = new String(decodedCredentials, StandardCharsets.UTF_8).split(":", 2);
		return BasicCredential.builder().username(credentials[0]).password(credentials[1]).build();

	}

}
