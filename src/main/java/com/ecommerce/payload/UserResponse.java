package com.ecommerce.payload;

import java.util.Set;

import com.ecommerce.model.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class UserResponse {

	private Long id;
	private String userMobile;

	private String userEmail;
	
	private String firstName;
	
	private String LastName;
	
	private String gender;
	
	private Status status;
	
	@JsonIgnoreProperties(value = {"user"})
	private Set<UserRoleResponse> userRole;
	
	private String token;
	
	public UserResponse(Long id) {
		this.id=id;
	}
}
