package com.ecommerce.service;


import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.payload.BrandRequest;



 public interface BrandService {

	  public Map<String, Object> addBrandDetails(BrandRequest brandRequest, MultipartFile multipartFiles);
	 
	  public Map<String, Object> updateStatusById(String  brandId);
	 
      public Map<String, Object> getBrandById(String brandId );
	 
	  public Map<String , Object> getAllBrandById(String userId);
	 
	   public Map<String , Object > getAllBrand(Integer page, Integer size, String sortDir);

	   public Map<String , Object > getVerfiedBrandById(String  brandId);
	   
	  public Map<String, Object> getAllVerfiedBrand(Integer page, Integer size, String sortDir);
	   
	  
	
	  
	   
	 
	 

	 
	 
	
	
	
}
