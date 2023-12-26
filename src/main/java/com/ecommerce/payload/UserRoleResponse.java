package com.ecommerce.payload;

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
public class UserRoleResponse {
	

	private Long id;
	

	private RoleResponse role;
	
	@JsonIgnoreProperties(value= {"userRole"})
	private UserResponse user;
	
	public UserRoleResponse userRoleToUserRoleResponse(UserRole userRoles) {
		
		this.setId(userRoles.getId());
		this.setRole(new RoleResponse(userRoles.getRole().getId(), userRoles.getRole().getRoleName(),
				userRoles.getRole().getDescription()));
		return this;
	}

}
