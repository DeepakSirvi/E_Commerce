package com.ecommerce.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.ecommerce.model.Status;
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
public class UserRequest {
	
	private Long userId;

	@NotBlank
	@NotEmpty
	private String userMobile;
	
	@Email
	@NotBlank
	private String userEmail;
	
	@NotBlank
	private String gender;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String LastName;
	
	private Status status;
}
