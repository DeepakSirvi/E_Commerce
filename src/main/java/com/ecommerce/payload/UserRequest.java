package com.ecommerce.payload;

import java.time.LocalDateTime;

import com.ecommerce.model.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UserRequest {
	
	public UserRequest(String userId2) {
		this.userId=userId2;
	}


	private String userId;

	@NotBlank
	private String userMobile;
	
	@Email
	@NotBlank
	private String userEmail;
	
	@NotBlank
	private String gender;
	
	@NotBlank
	@Size(min=4,max=255)
	private String firstName;
	private String LastName;	
	private Status status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
