package com.abinbev.ecommerce.products.web.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class ResponseError {

	private HttpStatus status;
	private String timestamp;
	private String message;
	@JsonInclude(Include.NON_NULL)
	private List<String> errors = new ArrayList<>();

	private ResponseError() {
		timestamp = LocalDateTime.now().toString();
	}

	public ResponseError(HttpStatus status) {
		this();
		this.status = status;
		this.message = "Unexpected error";
		this.errors = null;
	}

	public ResponseError(String message) {
		this();
		this.status = null;
		this.message = message;
		this.errors = null;
	}

	public ResponseError(HttpStatus status, String message) {
		this();
		this.status = status;
		this.message = message;
		this.errors = null;
	}

	public ResponseError(HttpStatus status, String message, List<String> errors) {
		this();
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

}
