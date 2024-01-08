package com.ecommerce.service;

import java.util.Map;

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
	
	public PageResponse<ProductResponse> getProductBySubCategory(String id, String subId, Integer page, Integer size,String sortDir);

	public Map<String, Object> getProduct(String productId);

	public PageResponse<ProductResponse> getProductByVendorId(String vendorId, Integer page, Integer size);

	public Map<String, Object> getAllProduct(String search, Integer pageIndex, Integer pageSize,
			String sortDir);

	public Map<String, Object> getProductListBasedOnStatus(String search, Integer pageIndex, Integer pageSize, String sortDir,boolean listingStatus,Status status);

	public Map<String, Object> updateStatusProduct(UpdateStatusBooleanRequest statusRequest);

	
	
}
