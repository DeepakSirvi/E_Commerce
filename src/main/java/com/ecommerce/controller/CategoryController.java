package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.CategoryRequest;
import com.ecommerce.payload.SubCategoryRequest;
import com.ecommerce.service.CategoryService;

@RestController
@RequestMapping("/ecommerce/category")
@CrossOrigin
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
		
	@PostMapping("/admin")
	public ResponseEntity<ApiResponse> createCategory(@RequestBody CategoryRequest categoryRequest)
	{
		return new ResponseEntity<ApiResponse>(categoryService.addCategory(categoryRequest),HttpStatus.CREATED);
	}
	
	@PostMapping("/admin/subcategory")
	public ResponseEntity<ApiResponse> createSubCategory(@RequestBody SubCategoryRequest subCategoryRequest)
	{
		return new ResponseEntity<ApiResponse>(categoryService.addSubCategory(subCategoryRequest),HttpStatus.CREATED);
	}
	
}
