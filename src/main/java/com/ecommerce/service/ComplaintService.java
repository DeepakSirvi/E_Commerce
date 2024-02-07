package com.ecommerce.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.payload.ComplaintRequest;

public interface ComplaintService {
	
	 public Map<String , Object> addComplaintDetails(ComplaintRequest complaintRequest , MultipartFile multipartFile);
	 
	
	

}
