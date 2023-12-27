package com.ecommerce.service;

import java.util.Map;

import com.ecommerce.payload.CategoryRequest;
import com.ecommerce.payload.VarientCategoryAttributeRequest;
import com.ecommerce.payload.VarientCategoryRequest;

public interface VarientCategoryService {

	public Map<String,Object> addVarientCategory(VarientCategoryRequest varientCategory);

	public Map<String,Object> addVarientCategoryAttribute(VarientCategoryAttributeRequest varientCategoryAttribute);

	public Map<String,Object> deleteVarientCategoryAttributeById(String id);

	public Map<String,Object> updateVarientCategoryAttribute(VarientCategoryAttributeRequest varientCategoryAttribute);

	public Map<String,Object> deleteVarientCategoryById(String id);

	public Map<String,Object> updateVarientCategory(VarientCategoryRequest varientCategory);

	public Map<String,Object> getVarientCategoryAttributeById(String id);

	public Map<String,Object> getVarientCategoryById(String id);

	public Map<String, Object> getAllVarient();

	public Map<String, Object> getAllVarientCategory(String search, Integer pageIndex,
			Integer pageSize, String sortDir);

}
