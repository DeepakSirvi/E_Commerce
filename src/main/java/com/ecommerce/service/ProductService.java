package com.ecommerce.service;

import java.util.Map;

import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.PageResponse;
import com.ecommerce.payload.ProductRequest;
import com.ecommerce.payload.ProductResponse;

public interface ProductService {

	public Map<String, Object> addProduct(ProductRequest productRequest);

	public PageResponse<ProductResponse> getProductBySubCategory(Long id, Long subId, Integer page, Integer size);

	public PageResponse<ProductResponse> getAllProduct(Integer page, Integer size,ProductRequest productRequest);

	public ProductResponse getProduct(Long productId);
	
}
