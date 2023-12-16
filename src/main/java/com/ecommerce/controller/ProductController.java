package com.ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.PageResponse;
import com.ecommerce.payload.ProductRequest;
import com.ecommerce.payload.ProductResponse;
import com.ecommerce.service.ProductService;
import com.ecommerce.util.AppConstant;

@RestController
@RequestMapping("/ecommerce/product")
@CrossOrigin("*")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@PostMapping("/admin")
	public ResponseEntity<Map<String, Object>> createProduct(@RequestBody ProductRequest productRequest ){
		return new ResponseEntity<Map<String, Object>>(productService.addProduct(productRequest),HttpStatus.CREATED);
	}
	
	@GetMapping("/ByCategory/{categoryId}/{subCategoryId}")
	public ResponseEntity<PageResponse<ProductResponse>> getAllProductByCategory(
			@PathVariable(value = "categoryId") Long id,@PathVariable(value = "subCategoryId") Long subId,
			@RequestParam(value = "page", required = false, defaultValue =  AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size)
	{
		
		PageResponse<ProductResponse> pageResponse=productService.getProductBySubCategory(id,subId,page,size);
		return new ResponseEntity<>(pageResponse,HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<PageResponse<ProductResponse>> getAllProduct(@RequestBody ProductRequest productRequest,
			@RequestParam(value = "page", required = false, defaultValue =  AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size)
	{
		
		PageResponse<ProductResponse> pageResponse=productService.getAllProduct(page,size,productRequest);
		return new ResponseEntity<>(pageResponse,HttpStatus.OK);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable(value = "productId") Long productId){
		return new ResponseEntity<ProductResponse>(productService.getProduct(productId) ,HttpStatus.OK);
	}
	
	@GetMapping("/vendor/{vendorId}")
	public ResponseEntity<PageResponse<ProductResponse>> getAllProductByCategory(
			@PathVariable(value = "vendorId") Long vendorId,
			@RequestParam(value = "page", required = false, defaultValue =  AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
			@RequestParam(value = "size", required = false, defaultValue = AppConstant.DEFAULT_PAGE_SIZE) Integer size)
	{
		PageResponse<ProductResponse> pageResponse=productService.getProductByVendorId(vendorId,page,size);
		return new ResponseEntity<>(pageResponse,HttpStatus.OK);
	}
	
	
}
