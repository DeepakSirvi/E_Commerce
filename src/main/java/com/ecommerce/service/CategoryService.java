package com.ecommerce.service;

import java.util.Map;

import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.CategoryRequest;
import com.ecommerce.payload.CategoryResponse;
import com.ecommerce.payload.SubCategoryRequest;
import com.ecommerce.payload.SubCategoryResponse;

public interface CategoryService {

	public ApiResponse addCategory(CategoryRequest categoryRequest);

	public ApiResponse addSubCategory(SubCategoryRequest subCategoryRequest);

	public CategoryResponse getCategoryById(String id);

	public SubCategoryResponse getSubCategoryById(String id);

	public ApiResponse deleteSubCategoryById(String id);

	public ApiResponse updateCategory(CategoryRequest categoryRequest);

	public ApiResponse updateSubCategory(SubCategoryRequest subCategoryRequest);

	public ApiResponse deleteCategoryById(String id);

	public Map<String, Object> getAllCategory();

	public Map<String, Object> getCategory(String search, Integer pageIndex, Integer pageSize, String sortDir);

}
