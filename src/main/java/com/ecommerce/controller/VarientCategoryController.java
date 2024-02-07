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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payload.VarientCategoryAttributeRequest;
import com.ecommerce.payload.VarientCategoryRequest;
import com.ecommerce.service.VarientCategoryService;
import com.ecommerce.util.AppConstant;

@RestController
@RequestMapping("ecommerce/varientCategory")
@CrossOrigin
public class VarientCategoryController {

	@Autowired
	private VarientCategoryService varientCategoryService;

	@PostMapping("/admin")
	public ResponseEntity<Map<String, Object>> createVarientCategory(
			@RequestBody VarientCategoryRequest varientCategory) {
		return new ResponseEntity<Map<String, Object>>(varientCategoryService.addVarientCategory(varientCategory),
				HttpStatus.CREATED);
	}

	@PostMapping("/admin/varientAttribute")
	public ResponseEntity<Map<String, Object>> createVarientAttribute(
			@RequestBody VarientCategoryAttributeRequest varientCategoryAttribute) {
		return new ResponseEntity<Map<String, Object>>(
				varientCategoryService.addVarientCategoryAttribute(varientCategoryAttribute), HttpStatus.CREATED);
	}

	@GetMapping("/admin")
	public ResponseEntity<Map<String, Object>> getAllVarientCategory() {
		return new ResponseEntity<>(varientCategoryService.getAllVarient(), HttpStatus.OK);
	}

	@GetMapping("/admin/pagination")
	public ResponseEntity<Map<String, Object>> getAllVarientCategory(
			@RequestParam(value = "varientSearch", required = false) String search,
			@RequestParam(value = "pageIndex", required = false, defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) Integer pageIndex,
			@RequestParam(value = "pageSize", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer pageSize,
			@RequestParam(value = "sortDir", required = false, defaultValue = AppConstant.DEFAULT_SORT_DIR) String sortDir) {
		System.out.println(search);
		return new ResponseEntity<Map<String, Object>>(
				varientCategoryService.getAllVarientCategory(search, pageIndex, pageSize, sortDir), HttpStatus.OK);
	}

	@GetMapping("/admin/{id}")
	public ResponseEntity<Map<String, Object>> getVarientCategory(@PathVariable(value = "id") String id) {
		return new ResponseEntity<Map<String, Object>>(varientCategoryService.getVarientCategoryById(id),
				HttpStatus.OK);
	}

	@GetMapping("/admin/varientAttributeibute/{id}")
	public ResponseEntity<Map<String, Object>> getVarientAttribute(@PathVariable(value = "id") String id) {
		return new ResponseEntity<Map<String, Object>>(varientCategoryService.getVarientCategoryAttributeById(id),
				HttpStatus.OK);
	}

	@PutMapping("/admin")
	public ResponseEntity<Map<String, Object>> updateVarientCategory(
			@RequestBody VarientCategoryRequest varientCategory) {
		return new ResponseEntity<Map<String, Object>>(varientCategoryService.updateVarientCategory(varientCategory),
				HttpStatus.OK);
	}

	@PutMapping("/admin/varientAttribute")
	public ResponseEntity<Map<String, Object>> updateVarientAttribute(
			@RequestBody VarientCategoryAttributeRequest varientCategoryAttribute) {
		return new ResponseEntity<Map<String, Object>>(
				varientCategoryService.updateVarientCategoryAttribute(varientCategoryAttribute), HttpStatus.OK);
	}

	@DeleteMapping("/admin/{id}")
	public ResponseEntity<Map<String, Object>> deleteVarientCategory(@PathVariable(value = "id") String id) {
		return new ResponseEntity<Map<String, Object>>(varientCategoryService.deleteVarientCategoryById(id),
				HttpStatus.OK);
	}

	@DeleteMapping("/admin/varientAttribute/{id}")
	public ResponseEntity<Map<String, Object>> deleteVarientAttribute(@PathVariable(value = "id") String id) {

		return new ResponseEntity<Map<String, Object>>(varientCategoryService.deleteVarientCategoryAttributeById(id),
				HttpStatus.OK);
	}
}
