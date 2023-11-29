package com.ecommerce.service;

import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.CategoryRequest;
import com.ecommerce.payload.SubCategoryRequest;

public interface CategoryService {

   public ApiResponse addCategory(CategoryRequest categoryRequest);

   public ApiResponse addSubCategory(SubCategoryRequest subCategoryRequest);

}
