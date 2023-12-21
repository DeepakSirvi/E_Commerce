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

	public CategoryResponse getCategoryById(Long id);

	public SubCategoryResponse getSubCategoryById(Long id);

	public ApiResponse deleteSubCategoryById(Long id);

	public ApiResponse updateCategory(CategoryRequest categoryRequest);

	public ApiResponse updateSubCategory(SubCategoryRequest subCategoryRequest);

	public ApiResponse deleteCategoryById(Long id);

	public Map<String, Object> getAllCategory();

	public Map<String, Object> getCategory(String search, Integer pageIndex, Integer pageSize, String sortDir);


}
