package com.ecommerce.service;

import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.ProductRequest;

public interface ProductService {

	public ApiResponse addProduct(ProductRequest productRequest);
	
}
