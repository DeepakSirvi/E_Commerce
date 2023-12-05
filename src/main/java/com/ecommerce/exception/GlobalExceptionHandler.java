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

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> resolveException(BadRequestException exception) {
		ErrorResponse errorResponse = new ErrorResponse(exception.getResponse(),HttpStatus.BAD_REQUEST);
		return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.BAD_REQUEST);
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
	
}
