package com.ecommerce.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.payload.IdentityRequest;

public interface IdentityService {
	
	
	 public  Map<String , Object > addIdentityDetails(IdentityRequest request ,MultipartFile multipartFiles );

	 public Map<String, Object> updateStatusById(String identityId);
	 
     public Map<String , Object>  getAllIdentityById(String userId);

	 public Map<String , Object> getIdentityById(String identityId);
	 
	 public Map<String ,Object> getAllActiveIdentity(Integer page , Integer size , String sortDir);
	 
	 public Map<String , Object > getAllIdentity(Integer page, Integer size, String sortDir);
	 
	 
	
  }
