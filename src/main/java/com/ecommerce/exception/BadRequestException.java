package com.ecommerce.exception;

import com.ecommerce.payload.ApiResponse;



public class BadRequestException extends RuntimeException {
	
	private ApiResponse apiResponse;
	
	public BadRequestException(ApiResponse apiResponse) {
		super();
		this.apiResponse = apiResponse;
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiResponse getApiResponse() {
		return apiResponse;
	}

}
