package com.ecommerce.service.impl;



import java.util.HashMap;

import java.util.Map;
import java.util.Optional;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Brand;
import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.ecommerce.payload.BrandRequest;
import com.ecommerce.payload.BrandResponse;
import com.ecommerce.repository.BrandRepo;

import com.ecommerce.service.BrandService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@Service
public class BrandServiceImpl  implements BrandService{
	
	private static final String ID = null;
	private static final String USER = null;
	private static final String BRAND = null;
	@Autowired
	private BrandRepo  brandRepo;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private AppUtils  appUtils;
	
	
	
	private Map<String, Object> response;

	@Override
	public Map<String, Object> addBrandDetails(BrandRequest brandRequest, MultipartFile multipartFiles) {
	     	 Map<String, Object> response = new HashMap<>();
	     	 Brand brand = this.brandRequestToBrand(brandRequest);
			 brand.setStatus(Status.UNVERIFIED);
	          if(multipartFiles != null) {
	        	  String uploadImage= appUtils.uploadImage(multipartFiles ,AppConstant.BRAND_IMAGE_PATH, null);
	        	   brand.setBrandImage(uploadImage);  
	          }	 
	          
	          brand.setUser(new User(appUtils.getUserId()));
	          
	        		  
	          brandRepo.save(brand);
	          response.put("message", AppConstant.BRAND_ADD_SUCCES);
	          return response;        
	}
	private Brand brandRequestToBrand(BrandRequest brandRequest) {
		return this.mapper.map(brandRequest, Brand.class);
	}
	
	
	@Override
	public Map<String, Object> updateStatusById(String brandId) {
		 Map<String, Object> response = new HashMap<>();
		 
		 Optional<Brand> optionalBrand = brandRepo.findById(brandId);
		 
		 if (optionalBrand.isPresent()) {
		        Brand brand = optionalBrand.get();
		        
		        brand.setStatus(Status.VERIFIED);
		        
		        brandRepo.save(brand);
		        
		        response.put("brandId", brandId);
		        
		        response.put(AppConstant.STATUS, brand.getStatus().toString());
		        
		 } else {
	           
	            response.put("error", "Brand not found for id: " + brandId);
	        }
		
		 return response; 	
	}
	
	@Override
	public Map<String, Object> getBrandById(String brandId) {
		
		Map<String, Object> response = new HashMap<>();
		
		Optional<Brand> brandOptional = brandRepo.findById(brandId);
		
		if (brandOptional.isPresent()) {
	        Brand brand = brandOptional.get();
	        
	        BrandResponse  brandResponse= brandToBrandResponse(brand);
	        response.put("brand", brandResponse);
	        
		} else {
	        response.put("message", "Brand not found for userId: " + brandId);
	         throw new ResourceNotFoundException(BRAND ,ID, brandId);  
	         
	    }
	    return response;
	}
	private BrandResponse brandToBrandResponse(Brand brand2) {
		BrandResponse brandResponse= new BrandResponse();
		brandResponse.setId(brand2.getId());
		brandResponse.setBrandName(brand2.getBrandName());
		brandResponse.setBrandDescription(brand2.getBrandDescription());
		return brandResponse;
	}
	
		
	}
	
			 
			 
			 
		 
		
		
		
	
	
		
		
		
	
	
		   
  
	
	
	


	

