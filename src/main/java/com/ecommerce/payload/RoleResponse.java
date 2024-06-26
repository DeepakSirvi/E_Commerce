package com.ecommerce.payload;

import java.io.Serializable;

import com.ecommerce.model.Audit;
import com.ecommerce.model.RoleName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class RoleResponse extends Audit  implements Serializable{

	private Integer id;
	private RoleName roleName;
	private String description;

	public RoleResponse(Integer id, RoleName roleName) {
		this.id = id;
		this.roleName = roleName;
	}
}
