package com.ecommerce.exception;

import com.ecommerce.payload.ApiResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
	
	
	private ApiResponse apiResponse;
	@NonNull
	private String resourceName;
	@NonNull
	private String fieldName;
	@NonNull
	private Object fieldValue;
	


	
	
	private void setApiResponse() {
		String message = String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue);

		apiResponse = new ApiResponse(Boolean.FALSE, message);
	}

}
