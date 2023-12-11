package com.ecommerce.payload;

import org.springframework.http.HttpStatus;


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
@JsonInclude(Include.NON_NULL)
public class ApiResponse {

	private Boolean success;
	private String message;
	private HttpStatus status;
	
	public ApiResponse(Boolean success, String message) {
		this.success=success;
		this.message=message;
	}
}
