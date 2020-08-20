package com.abinbev.ecommerce.products.web.security;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthUser implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String username;
	private String password;
	private String name;
	private String role;
	Boolean isEnabled;

}
