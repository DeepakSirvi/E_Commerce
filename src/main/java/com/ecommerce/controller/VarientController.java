package com.ecommerce.controller;

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
import com.ecommerce.payload.VarientCategoryAttributeRequest;
import com.ecommerce.payload.VarientCategoryAttributeResponse;
import com.ecommerce.payload.VarientCategoryReponse;
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
	public ResponseEntity<ApiResponse> createVarientCategory(@RequestBody VarientCategoryRequest varientCategory)
	{
		return new ResponseEntity<ApiResponse>(varientCategoryService.addVarientCategory(varientCategory),HttpStatus.CREATED);
	}
	
	@PostMapping("/admin/varientAttributeibute")
	public ResponseEntity<ApiResponse> createVarientAttribute(@RequestBody VarientCategoryAttributeRequest varientCategoryAttribute)
	{
		return new ResponseEntity<ApiResponse>(varientCategoryService.addVarientCategoryAttribute(varientCategoryAttribute),HttpStatus.CREATED);
	}
	
	@GetMapping("/admin/{id}")
	public ResponseEntity<VarientCategoryReponse> getVarientCategory(@PathVariable(value = "id") Long id)
	{
		return new ResponseEntity<VarientCategoryReponse>(varientCategoryService.getVarientCategoryById(id),HttpStatus.OK);
	}
	
	@GetMapping("/admin/varientAttributeibute/{id}")
	public ResponseEntity<VarientCategoryAttributeResponse> getVarientAttribute(@PathVariable(value = "id") Long id)
	{
		return new ResponseEntity<VarientCategoryAttributeResponse>(varientCategoryService.getVarientCategoryAttributeById(id),HttpStatus.OK);
	}
	
	@PutMapping("/admin")
	public ResponseEntity<ApiResponse> updateVarientCategory(@RequestBody VarientCategoryRequest varientCategory)
	{
		return new ResponseEntity<ApiResponse>(varientCategoryService.updateVarientCategory(varientCategory),HttpStatus.OK);
	}
	
	@PutMapping("/admin/varientAttributeibute")
	public ResponseEntity<ApiResponse> updateVarientAttribute(@RequestBody VarientCategoryAttributeRequest varientCategoryAttribute)
	{
		return new ResponseEntity<ApiResponse>(varientCategoryService.updateVarientCategoryAttribute(varientCategoryAttribute),HttpStatus.OK);
	}
	
	@DeleteMapping("/admin/{id}")
	public ResponseEntity<ApiResponse> deleteVarientCategory(@PathVariable(value = "id") Long id)
	{
		return new ResponseEntity<ApiResponse>(varientCategoryService.deleteVarientCategoryById(id),HttpStatus.OK);
	}
	
	@DeleteMapping("/admin/varientAttributeibute/{id}")
	public ResponseEntity<ApiResponse> deleteVarientAttribute(@PathVariable(value = "id") Long id)
	{
		return new ResponseEntity<ApiResponse>(varientCategoryService.deleteVarientCategoryAttributeById(id),HttpStatus.OK);
	}
}
