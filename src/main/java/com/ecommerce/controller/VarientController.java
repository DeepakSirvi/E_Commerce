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
import com.ecommerce.payload.VarientCategoryAttributeRequest;
import com.ecommerce.payload.VarientCategoryRequest;
import com.ecommerce.service.VarientCategoryService;
import com.ecommerce.service.VarientService;

@RestController
@RequestMapping("ecommerce/varient")
@CrossOrigin
public class VarientController {
	
	@Autowired
	private VarientService varient;
	
	@Autowired
	private  VarientCategoryService varientCategoryService;
	
	
	@PostMapping("/admin")
	public ResponseEntity<ApiResponse> createCategory(@RequestBody VarientCategoryRequest varientCategory)
	{
		return new ResponseEntity<ApiResponse>(varientCategoryService.addVarientCategory(varientCategory),HttpStatus.CREATED);
	}
	
	@PostMapping("/admin/varientAttributeibute")
	public ResponseEntity<ApiResponse> createSubCategory(@RequestBody VarientCategoryAttributeRequest varientCategoryAttribute)
	{
		return new ResponseEntity<ApiResponse>(varientCategoryService.addVarientCategoryAttribute(varientCategoryAttribute),HttpStatus.CREATED);
	}
}
