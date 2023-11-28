package com.ecommerce.exception;

import com.ecommerce.payload.ApiResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BadRequestException extends RuntimeException {
	
	private ApiResponse response;
	
	
	
}
