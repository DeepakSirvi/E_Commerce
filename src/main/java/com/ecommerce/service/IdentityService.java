package com.ecommerce.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.payload.IdentityRequest;

public interface IdentityService {
	
	
	 public  Map<String , Object > addIdentityDetails(IdentityRequest request ,MultipartFile multipartFiles );

	 public Map<String, Object> updateStatusById(String identityId);

	
  }
