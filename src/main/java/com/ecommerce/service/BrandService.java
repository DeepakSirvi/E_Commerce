package com.ecommerce.service;


import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.payload.BrandRequest;
import com.ecommerce.payload.BrandResponse;



 public interface BrandService {

	  public Map<String, Object> addBrandDetails(BrandRequest brandRequest, MultipartFile multipartFiles);
	 
	  public Map<String, Object> updateStatusById(String  brandId);
	 
      public Map<String, Object> getBrandById(String brandId );
	 
	  public Map<String , Object> getAllBrandById(String userId);
	 
	   public Map<String , List<BrandResponse> > getAllBrand(Integer page, Integer size, String sortDir);

	   public Map<String , Object > getVerfiedBrandById(String  brandId);
	   
	  public Map<String, Object> getAllVerfiedBrand(Integer page, Integer size, String sortDir);
	  
	  public boolean deleteAdress(String id);
	   
	  
	
	  
	   
	 
	 

	 
	 
	
	
	
}
