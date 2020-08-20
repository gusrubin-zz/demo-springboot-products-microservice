package com.abinbev.ecommerce.products.web.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(final String username) {

		// The user 'admin' is hard coded only for demonstration.
		// Then clean password is 'password', it's BCrypt encoded in object

		if (username.equals("admin")) {
			return new AuthUserDetails(AuthUser.builder().id("1").username("admin")
					.password("$2a$10$is4KgbSeRcJQJ4G3SuMP1u3otYfUECRtAChR.c2Vm6VR.tcg4Rkg.").role("ADMIN")
					.isEnabled(true).build());
		} else {
			log.error("User not found with username");
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

	}

}
