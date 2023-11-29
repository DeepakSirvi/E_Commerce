package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.ProductRequest;
import com.ecommerce.service.ProductService;

@RestController
@RequestMapping("/ecommerce/product")
@CrossOrigin
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping("/admin")
	public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductRequest productRequest ){
		return new ResponseEntity<ApiResponse>(productService.addProduct(productRequest),HttpStatus.CREATED);
	}
}
