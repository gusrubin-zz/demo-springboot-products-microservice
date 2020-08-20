package com.abinbev.ecommerce.products.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.abinbev.ecommerce.products.web.security.WebAuthenticationEntryPoint;
import com.abinbev.ecommerce.products.web.security.WebRequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final WebAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final UserDetailsService jwtUserDetailsService;

	@Autowired
	private WebRequestFilter webRequestFilter;

	@Autowired
	public WebSecurityConfig(WebAuthenticationEntryPoint jwtAuthenticationEntryPoint,
			UserDetailsService jwtUserDetailsService) {
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtUserDetailsService = jwtUserDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(jwtUserDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(final HttpSecurity httpSecurity) throws Exception {

		httpSecurity.cors()

				.and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)

				.and().authorizeRequests().antMatchers("/auth/login").permitAll().anyRequest().authenticated();

		httpSecurity.addFilterBefore(webRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
