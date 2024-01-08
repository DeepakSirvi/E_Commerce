package com.ecommerce.exception;

import org.springframework.http.HttpStatus;

import com.ecommerce.payload.ApiResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ResourceNotFoundException extends RuntimeException {
	
	
	private ExceptionResponse exceptionResponse;
	
	
	public ResourceNotFoundException(String message)
	{
		exceptionResponse=new ExceptionResponse();
		exceptionResponse.setSuccess(Boolean.FALSE);
		exceptionResponse.setMessage(message);
		exceptionResponse.setStatus(HttpStatus.BAD_REQUEST);
	}
	
	private String resourceName;

	private String fieldName;

	private Object fieldValue;
	
	public ResourceNotFoundException(String name,String field,Object value)
	{
		this.resourceName=name;
		this.fieldName=field;
		this.fieldValue=value;
		setApiResponse();
	}
	
	private void setApiResponse() {
		String message = String.format("%s not found with %s: %s", resourceName, fieldName, fieldValue);
		exceptionResponse = new ExceptionResponse(Boolean.FALSE, message,HttpStatus.NOT_FOUND);
	}

}
