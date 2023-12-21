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
@RequiredArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ApiResponse {

	@NonNull
	private Boolean success;
	@NonNull
	private String message;
	private HttpStatus status;
}
