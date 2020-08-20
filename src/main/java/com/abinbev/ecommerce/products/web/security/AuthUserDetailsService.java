package com.abinbev.ecommerce.products.web.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsService implements UserDetailsService {

//	@Override
//	public UserDetails loadUserByUsername(final String username) {
//
//		// Hard coded user just for demonstration.
//		// Password is 'password', it's BCrypt encoded in object
//
//		AuthUser authUser = AuthUser.builder().id("1").username("admin")
//				.password("$2a$10$is4KgbSeRcJQJ4G3SuMP1u3otYfUECRtAChR.c2Vm6VR.tcg4Rkg.").role("ADMIN").isEnabled(true)
//				.build();
//
//		return new AuthUserDetails(authUser);
//	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("javainuse".equals(username)) {
			return new User("javainuse", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}
