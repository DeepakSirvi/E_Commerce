package com.ecommerce.payload;

import java.util.List;

import com.ecommerce.model.Category;
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
public class CategoryResponse {
	
	private String id;
	private String categoryName;
	private List<SubCategoryResponse> subCategory;
	private UserResponse user;
	public CategoryResponse categoryToResponse(Category category) {
		this.setId(category.getId());
		this.setCategoryName(category.getCategoryName());
		return this;
	}
}
