package com.ecommerce.service.impl;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Brand;
import com.ecommerce.model.Status;
import com.ecommerce.model.User;
import com.ecommerce.payload.BrandRequest;
import com.ecommerce.payload.BrandResponse;
import com.ecommerce.repository.BrandRepo;
import com.ecommerce.repository.UserRepo;
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
	
	@Autowired
	private UserRepo  userRepo;
	
	
	
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
	          response.put("response", AppConstant.BRAND_ADD_SUCCES);
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
		        
		        if (optionalBrand.isPresent()) {
		        	 Brand brand1 =  optionalBrand.get();
		        	 
		        	 brand1.setStatus(Status.UNVERIFIED);
		        }
		        brandRepo.save(brand);
		        
		        response.put("response", AppConstant.UPDATE_STATUS);
		        
		        response.put(AppConstant.STATUS, brand.getStatus().toString());
		        
		 } else {
	           
	           
	            throw new ResourceNotFoundException(BRAND , ID , brandId);
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
	@Override
	public Map<String, Object> getAllBrandById(String userId) {
		
		 Map<String , Object > response = new HashMap<>();
		 
		 List<Brand> brands = brandRepo.findAllByUserId(userId);
		 
		  List<BrandResponse> brandResponse= brands.stream()
				  .map(this::brandToBrandResponse)
				  .collect(Collectors.toList());
		  
		   response.put("brand", brandResponse);
		return response;
		
	}
	@Override
	public Map<String, Object> getAllBrand(Integer page, Integer size, String sortDir) {
		
		
		
		Map<String, Object> response = new HashMap<>();
		AppUtils.validatePageAndSize(page, size);
		
		Sort sort1 = null;
		if (sortDir.equals("DESC")) {
			sort1 = Sort.by(Sort.Order.desc("updatedAt"));
		} else {
			sort1 = Sort.by(Sort.Order.asc("updatedAt"));
		}
		Pageable pageable = PageRequest.of(page, size, sort1);
		Page <Brand> brandSet =null;
		brandSet = brandRepo.findAll(pageable);
		if (!brandSet.isEmpty()) {
            List<BrandResponse> brandResponses = brandSet.stream()
                    .map(this::brandToBrandResponse)
                    .collect(Collectors.toList());
		 response.put("AllBrand", brandResponses);
    } else {
         throw new ResourceNotFoundException(); 
	}
    return response;
  }
	
	@Override
	public Map<String, Object> getVerfiedBrandById(String brandId) {
		
		Map<String, Object> response = new HashMap<>();
		

		Optional<Brand> optionalBrand = brandRepo.findById(brandId);
		
		if (!optionalBrand.isPresent()) {
			throw new  ResourceNotFoundException("Brand Not Found");
         
            
		}
		 if(optionalBrand.get().getStatus().toString().equals("VERIFIED")) {
			 response.put("response",AppConstant.BRAND_VERIFIED );
			 response.put("data",optionalBrand.get() );
		 }else {
			 response.put("message" , AppConstant.BRAND_NOT_VERIFIED);
		 }  
		 
		return response ;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getAllVerfiedBrand(Integer pageIndex, Integer pagesize, String sortDir) {
		Map<String, Object> response = new HashMap<>();
		AppUtils.validatePageAndSize(pageIndex, pagesize);
		Sort sort1 = null;
		if (sortDir.equals("DESC")) {
			sort1 = Sort.by(Sort.Order.desc("updatedAt"));
		} else {
			sort1 = Sort.by(Sort.Order.asc("updatedAt"));
		}
		Pageable pageable = PageRequest.of(pageIndex, pagesize,sort1);
		 Page<Brand> findAllVerfiedBrand = brandRepo.findAllVerfiedBrand(pageable);
		 response.put("response",findAllVerfiedBrand.getContent().stream().map(obj->brandFilter(obj)).collect(Collectors.toList()));
		 
		 response.put(AppConstant.MESSAGE,AppConstant.ALL_VERFIED_BRAND);
		
		
		return response;
	}
	
 public BrandResponse brandFilter(Brand obj){
	 BrandResponse  brandResponse = new  BrandResponse();
	    brandResponse.setId(obj.getId());
	    brandResponse.setBrandDescription(obj.getBrandDescription());
	    brandResponse.setBrandImage(obj.getBrandImage());
	    brandResponse.setBrandName(obj.getBrandName());
	    return brandResponse;
    
	    
 }
	

}	
	

	
	


  
	
		 
	
	

	

			 
			 
			 
		 
		
		
		
	
	
		
		
		
	
	
		   
  
	
	
	


	

