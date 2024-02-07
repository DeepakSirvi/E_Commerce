package com.ecommerce.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnauthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ExceptionResponse exceptionResponse;

	public UnauthorizedException(String message) {
		exceptionResponse = new ExceptionResponse();
		exceptionResponse.setSuccess(Boolean.FALSE);
		exceptionResponse.setMessage(message);
		exceptionResponse.setStatus(HttpStatus.BAD_REQUEST);
	}
}
