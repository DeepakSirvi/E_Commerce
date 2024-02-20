package com.ecommerce.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ExceptionResponse exceptionResponse;

	public ResourceNotFoundException(String message) {
		exceptionResponse = new ExceptionResponse();
		exceptionResponse.setSuccess(Boolean.FALSE);
		exceptionResponse.setMessage(message);
		exceptionResponse.setStatus(HttpStatus.NOT_FOUND);
	}

	private String resourceName;

	private String fieldName;

	private Object fieldValue;

	public ResourceNotFoundException(String name, String field, Object value) {
		this.resourceName = name;
		this.fieldName = field;
		this.fieldValue = value;
		setApiResponse();
	}

	private void setApiResponse() {
		String message = String.format("%s not found with %s: %s", resourceName, fieldName, fieldValue);
		exceptionResponse = new ExceptionResponse(Boolean.FALSE, message, HttpStatus.NOT_FOUND);
	}

}
