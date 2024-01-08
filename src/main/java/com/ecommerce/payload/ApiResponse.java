package com.ecommerce.payload;

import org.springframework.http.HttpStatus;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
	
	public ApiResponse(String message) {
		this.message=message;
		this.success=Boolean.TRUE;
		this.status=HttpStatus.OK;
	}
}
