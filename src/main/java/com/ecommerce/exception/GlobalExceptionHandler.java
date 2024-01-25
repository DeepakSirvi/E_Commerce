package com.ecommerce.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ecommerce.payload.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ExceptionResponse> resolveException(BadRequestException exception) {
		ExceptionResponse exceptionResponse = exception.getExceptionResponse();
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	
	@ExceptionHandler(MethodArgumentNotValidException.class) 
	public ResponseEntity<Map<String,String>> handlerMethodArgsNotVAlidException(MethodArgumentNotValidException ex)
	{
		Map<String,String> resp = new HashMap<>();
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();   // Return a list of error 
	    allErrors.forEach((error)->{
	    	String field = ((FieldError)error).getField();
	    	String defaultMessage = error.getDefaultMessage();
	    	resp.put(field,defaultMessage);
	    });
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> resolveException(ResourceNotFoundException exception) {
		ExceptionResponse exceptionResponse = exception.getExceptionResponse();
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ExceptionResponse> resolveException(UnauthorizedException exception) {

		ExceptionResponse exceptionResponse = exception.getExceptionResponse();

		return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
	}
}
