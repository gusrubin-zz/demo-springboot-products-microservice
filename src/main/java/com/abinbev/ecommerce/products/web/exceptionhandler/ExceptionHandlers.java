package com.abinbev.ecommerce.products.web.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlers {

	@ExceptionHandler(AuthenticationException.class)
	protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException authenticationException) {
		log.error("[handleAuthenticationException] " + authenticationException.getMessage());
		return buildResponseEntity(new ResponseError(HttpStatus.UNAUTHORIZED, authenticationException.getMessage()));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
		log.error("[handleIllegalArgumentException] " + illegalArgumentException.getMessage());
		return buildResponseEntity(
				new ResponseError(HttpStatus.PRECONDITION_FAILED, illegalArgumentException.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException methodArgumentNotValidException) {
		log.error("[handleMethodArgumentNotValidException] " + methodArgumentNotValidException.getMessage());
		BindingResult result = methodArgumentNotValidException.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		List<String> errorMessages = new ArrayList<>();
		for (FieldError fieldError : fieldErrors) {
			errorMessages.add(fieldError.getDefaultMessage());
		}
		ResponseError responseError = new ResponseError(HttpStatus.PRECONDITION_FAILED, "Argument Not Valid",
				errorMessages);
		return new ResponseEntity<>(responseError, responseError.getStatus());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<Object> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException httpMessageNotReadableException) {
		log.error("[handleHttpMessageNotReadableException] " + httpMessageNotReadableException.getMessage());
		return buildResponseEntity(
				new ResponseError(HttpStatus.PRECONDITION_FAILED, "Wrong JSON format or field type"));
	}

	@ExceptionHandler(IllegalStateException.class)
	protected ResponseEntity<Object> handleIllegalStateException(IllegalStateException illegalStateException) {
		log.error("[handleIllegalStateException] " + illegalStateException.getMessage());
		return buildResponseEntity(
				new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY, illegalStateException.getMessage()));
	}

	@ExceptionHandler(DataAccessException.class)
	protected ResponseEntity<Object> handleDataAccessException(DataAccessException dataAccessException) {
		log.error("[handleDataAccessException] " + dataAccessException.getMessage());
		return buildResponseEntity(new ResponseError(HttpStatus.SERVICE_UNAVAILABLE, dataAccessException.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleException(Exception exception) {
		log.error("[handleException] " + exception.getMessage());
		return buildResponseEntity(new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()));
	}

	private ResponseEntity<Object> buildResponseEntity(ResponseError responseError) {
		return new ResponseEntity<>(responseError, responseError.getStatus());
	}
}
