package com.ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.CategoryRequest;
import com.ecommerce.payload.CategoryResponse;
import com.ecommerce.payload.SubCategoryRequest;
import com.ecommerce.payload.SubCategoryResponse;
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
	
	@GetMapping("/admin/{categoryId}")
	public ResponseEntity<CategoryResponse> getCategory(@PathVariable(value = "categoryId") Long id )
	{
		return new ResponseEntity<CategoryResponse>(categoryService.getCategoryById(id),HttpStatus.OK);
	}
	
	@GetMapping("/admin/subcategory/{subCategoryId}")
	public ResponseEntity<SubCategoryResponse> getSubCategory(@PathVariable(value = "subCategoryId") Long id)
	{
		return new ResponseEntity<SubCategoryResponse>(categoryService.getSubCategoryById(id),HttpStatus.OK);
	}
	
	@GetMapping("/admin")
	public ResponseEntity<Map<String, Object>> getAllCategory()
	{
		return new ResponseEntity<Map<String,Object>>(categoryService.getCategory(),HttpStatus.OK);
	}
	
	@PutMapping("/admin")
	public ResponseEntity<ApiResponse> updateCategory(@RequestBody CategoryRequest categoryRequest)
	{
		return new ResponseEntity<ApiResponse>(categoryService.updateCategory(categoryRequest),HttpStatus.OK);
	}
	
	@PutMapping("/admin/subcategory")
	public ResponseEntity<ApiResponse> updateSubCategory(@RequestBody SubCategoryRequest subCategoryRequest)
	{
		return new ResponseEntity<ApiResponse>(categoryService.updateSubCategory(subCategoryRequest),HttpStatus.OK);
	}
	
	@DeleteMapping("/admin/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable(value = "categoryId") Long id)
	{
		return new ResponseEntity<ApiResponse>(categoryService.deleteCategoryById(id),HttpStatus.OK);
	}
	
	@DeleteMapping("/admin/subcategory/{subCategoryId}")
	public ResponseEntity<ApiResponse> deleteSubCategory(@PathVariable(value = "subCategoryId") Long id)
	{
		return new ResponseEntity<ApiResponse>(categoryService.deleteSubCategoryById(id),HttpStatus.OK);
	}
	
}
