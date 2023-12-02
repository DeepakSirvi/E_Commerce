package com.ecommerce.service;

import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.VarientCategoryAttributeRequest;
import com.ecommerce.payload.VarientCategoryAttributeResponse;
import com.ecommerce.payload.VarientCategoryReponse;
import com.ecommerce.payload.VarientCategoryRequest;

public interface VarientCategoryService {

	ApiResponse addVarientCategory(VarientCategoryRequest varientCategory);

	ApiResponse addVarientCategoryAttribute(VarientCategoryAttributeRequest varientCategoryAttribute);

	ApiResponse deleteVarientCategoryAttributeById(Long id);

	ApiResponse updateVarientCategoryAttribute(VarientCategoryAttributeRequest varientCategoryAttribute);

	ApiResponse deleteVarientCategoryById(Long id);

	ApiResponse updateVarientCategory(VarientCategoryRequest varientCategory);

	VarientCategoryAttributeResponse getVarientCategoryAttributeById(Long id);

	VarientCategoryReponse getVarientCategoryById(Long id);

}
