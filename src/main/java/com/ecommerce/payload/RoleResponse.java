package com.ecommerce.payload;

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
public class RoleResponse extends Audit {
	
	private Integer id;
	private RoleName roleName;
	private String description;
}
