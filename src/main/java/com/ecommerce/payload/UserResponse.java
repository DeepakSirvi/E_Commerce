package com.ecommerce.payload;

import java.util.Set;
import java.util.stream.Collectors;

import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.ecommerce.model.UserRole;
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

	private String id;
	private String userMobile;

	private String userEmail;
	
	private String firstName;
	
	private String LastName;
	
	private String gender;
	
	private Status status;
	
	@JsonIgnoreProperties(value = {"user"})
	private Set<UserRoleResponse> userRole;
	
	private String token;
	
	public UserResponse(String id) {
		this.id=id;
	}
	
	public UserResponse userToUserResponse(User user) {
		this.setFirstName(user.getFirstName());
		this.setLastName(user.getLastName());
		this.setGender(user.getGender());
		this.setId(user.getId());
		this.setUserMobile(user.getUserMobile());
		this.setUserEmail(user.getUserEmail());
		this.setStatus(user.getStatus());
		return this;
	}
}
