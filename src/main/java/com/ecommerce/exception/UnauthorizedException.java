package com.ecommerce.exception;

import com.ecommerce.payload.ApiResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnauthorizedException extends RuntimeException {

	private ApiResponse apiResponse;

	private String message;
	
	public UnauthorizedException(ApiResponse apiResponse) {
		super();
		this.apiResponse = apiResponse;
	}

	public UnauthorizedException(String message) {
		super(message);
		this.message = message;
	}
}
