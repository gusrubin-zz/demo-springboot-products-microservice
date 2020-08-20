package com.abinbev.ecommerce.products.web.security;

import lombok.Getter;

public class LoginResponse {

	@Getter
	private final String jwt;

	public LoginResponse(String jwt) {
		this.jwt = jwt;
	}

}
