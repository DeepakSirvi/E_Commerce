package com.ecommerce.payload;

import com.ecommerce.model.SubCategory;
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
public class SubCategoryResponse {

    private String id;
	private String subCategory;
	private CategoryResponse category;
	private ProductResponse product;
	
	public SubCategoryResponse subCategoryToResponseWithCategory(SubCategory subCategory2) {
		this.setId(subCategory2.getId());
		this.setSubCategory(subCategory2.getSubCategory());
		CategoryResponse response=new CategoryResponse();
		response.categoryToResponse(subCategory2.getCategory());
		this.setCategory(response);
		return this;
	}
	
	public SubCategoryResponse subCategoryToSubCategoryResponse(SubCategory s) {
		this.setId(s.getId());
		this.setSubCategory(s.getSubCategory());
		return this;
	}
	
	

}
