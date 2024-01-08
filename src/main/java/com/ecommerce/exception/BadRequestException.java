package com.ecommerce.exception;

import org.springframework.http.HttpStatus;

import com.ecommerce.payload.ApiResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BadRequestException extends RuntimeException {
	
	private ExceptionResponse exceptionResponse;
	
	
	public BadRequestException(String message)
	{
		exceptionResponse=new ExceptionResponse();
		exceptionResponse.setSuccess(Boolean.FALSE);
		exceptionResponse.setMessage(message);
		exceptionResponse.setStatus(HttpStatus.BAD_REQUEST);
	}
}
