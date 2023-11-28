package com.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.ecommerce.payload.ApiResponse;

@ControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiResponse> resolveException(BadRequestException exception) {
		ApiResponse apiResponse = exception.getResponse();
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

}
