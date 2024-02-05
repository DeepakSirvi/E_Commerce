package com.ecommerce.payload;

import java.util.ArrayList;
import java.util.List;

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
public class VarientCategoryRequest {

	public VarientCategoryRequest(String id) {
		this.id = id;
	}

	private String id;
	private String name;
	private List<VarientCategoryAttributeRequest> categoryAttributes = new ArrayList<>();

}
