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
	public ResponseEntity<ApiResponse> resolveException(BadRequestException exception) {
		ApiResponse apiResponse = exception.getApiResponse();
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
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
	public ResponseEntity<ApiResponse> resolveException(ResourceNotFoundException exception) {
		ApiResponse apiResponse = exception.getApiResponse();
		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ApiResponse> resolveException(UnauthorizedException exception) {

		ApiResponse apiResponse = exception.getApiResponse();

		return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
	}
}
