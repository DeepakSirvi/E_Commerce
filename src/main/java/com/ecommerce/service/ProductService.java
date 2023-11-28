package com.ecommerce.service;

import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.ProductRequest;
import com.ecommerce.payload.ProductResponse;

public interface ProductService {

	public ApiResponse addProduct(ProductRequest productRequest);
	
}
