package com.ecommerce.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.model.Status;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.PageResponse;
import com.ecommerce.payload.ProductRequest;
import com.ecommerce.payload.ProductResponse;
import com.ecommerce.payload.UpdateStatusBooleanRequest;
import com.ecommerce.payload.UpdateStatusRequest;

public interface ProductService {

	public Map<String, Object> addProduct(ProductRequest productRequest, MultipartFile multipartFiles);
	
	public Map<String, Object> getProductBySubCategory(String id, Integer page, Integer size,String sortDir);

	public Map<String, Object> getProduct(String productId);

	public PageResponse<ProductResponse> getProductByVendorId(String vendorId, Integer page, Integer size);

	public Map<String, Object> getAllProduct(String search, Integer pageIndex, Integer pageSize,
			String sortDir);

	public Map<String, Object> getProductListBasedOnStatus(String search, Integer pageIndex, Integer pageSize, String sortDir);

	public Map<String, Object> updateStatusProduct(UpdateStatusBooleanRequest statusRequest);

	public Map<String, Object> getProductByCategory(String id, Integer pageIndex, Integer pageSize,
			String sortDir);

	
	
}
