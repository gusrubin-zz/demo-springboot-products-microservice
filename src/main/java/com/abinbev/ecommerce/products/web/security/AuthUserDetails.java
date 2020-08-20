package com.abinbev.ecommerce.products.web.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUserDetails implements UserDetails {

	private static final long serialVersionUID = 1269185834135207542L;
	private final AuthUser authUser;

	public AuthUserDetails(final AuthUser authUser) {
		this.authUser = authUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority(authUser.getRole()));
		return authorities;
	}

	public String getName() {
		return authUser.getName();
	}

	@Override
	public String getUsername() {
		return authUser.getUsername();
	}

	@Override
	public String getPassword() {
		return authUser.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public AuthUser getAuthUser() {
		return authUser;
	}

	@Override
	public boolean isEnabled() {
		return authUser.getIsEnabled();
	}

}
