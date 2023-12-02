package com.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.ecommerce.payload.ApiResponse;

@ControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> resolveException(BadRequestException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getResponse(),HttpStatus.BAD_REQUEST);
		return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.BAD_REQUEST);
	}

}
