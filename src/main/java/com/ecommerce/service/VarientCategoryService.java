package com.ecommerce.service;

import java.util.Map;

import com.ecommerce.payload.VarientCategoryAttributeRequest;
import com.ecommerce.payload.VarientCategoryRequest;

public interface VarientCategoryService {

	public Map<String,Object> addVarientCategory(VarientCategoryRequest varientCategory);

	public Map<String,Object> addVarientCategoryAttribute(VarientCategoryAttributeRequest varientCategoryAttribute);

	public Map<String,Object> deleteVarientCategoryAttributeById(Long id);

	public Map<String,Object> updateVarientCategoryAttribute(VarientCategoryAttributeRequest varientCategoryAttribute);

	public Map<String,Object> deleteVarientCategoryById(Long id);

	public Map<String,Object> updateVarientCategory(VarientCategoryRequest varientCategory);

	public Map<String,Object> getVarientCategoryAttributeById(Long id);

	public Map<String,Object> getVarientCategoryById(Long id);

	public Map<String, Object> getAllVarient();

}
