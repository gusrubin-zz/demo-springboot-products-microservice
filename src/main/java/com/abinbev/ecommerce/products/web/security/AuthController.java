package com.abinbev.ecommerce.products.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final WebAuthenticationProvider webAuthenticationProvider;
	private final JwtProvider jwtProvider;

	@Autowired
	public AuthController(WebAuthenticationProvider webAuthenticationProvider, JwtProvider jwtProvider) {
		this.webAuthenticationProvider = webAuthenticationProvider;
		this.jwtProvider = jwtProvider;
	}

	@GetMapping("/login")
	public ResponseEntity<Object> getJwt(@RequestHeader("Authorization") String authorizationHeader) {

		BasicCredential basicCredential = webAuthenticationProvider.getBasicCredentialFromHeader(authorizationHeader);

		Authentication authentication = webAuthenticationProvider.authenticate(
				new UsernamePasswordAuthenticationToken(basicCredential.getUsername(), basicCredential.getPassword()));

		return ResponseEntity.ok(new LoginResponse(jwtProvider.generateJwt(authentication)));
	}

}
