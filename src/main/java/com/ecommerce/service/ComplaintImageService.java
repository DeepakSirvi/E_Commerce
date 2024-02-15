package com.ecommerce.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface ComplaintImageService {
	
	public Map<String , Object> createComplaintImage(String complaintId, MultipartFile multipartFiles);
	
	public Map<String ,Object> updateComplaintImage(String  complaintImageId  ,  MultipartFile multipartFiles );
	
	//public Map<String , Object> getComplaintImageById(String complaintImageId , MultipartFile multipartFiles ) ;
	
	
	  public Map<String ,Object >  removeComplaintImageById(String complaintImageId );
	 

}
