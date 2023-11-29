package com.ecommerce.service;

import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.VarientCategoryAttributeRequest;
import com.ecommerce.payload.VarientCategoryRequest;

public interface VarientCategoryService {

	ApiResponse addVarientCategory(VarientCategoryRequest varientCategory);

	ApiResponse addVarientCategoryAttribute(VarientCategoryAttributeRequest varientCategoryAttribute);

}
