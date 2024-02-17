package com.ecommerce.payload;

import java.util.List;

import com.ecommerce.model.VarientCategory;
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
public class VarientCategoryReponse {

	private String id;
	private String name;
	private List<VarientCategoryAttributeResponse> categoryAttributes;
	private UserResponse user;

	public VarientCategoryReponse vatCatToResponse(VarientCategory varientCategory) {
		this.setId(varientCategory.getId());
		this.setName(varientCategory.getName());
		return this;
	}

}
