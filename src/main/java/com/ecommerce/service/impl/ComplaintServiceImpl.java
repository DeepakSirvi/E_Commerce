package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.model.Complaint;

import com.ecommerce.model.User;
import com.ecommerce.payload.ComplaintRequest;
import com.ecommerce.repository.ComplaintRepo;
import com.ecommerce.service.ComplaintService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@Service
public class ComplaintServiceImpl implements ComplaintService {
	
	
	@Autowired
	private  ComplaintRepo   complaintRepo;
	
	@Autowired
    public ModelMapper modelMapper;
    
    @Autowired
    private AppUtils appUtils;
    
	@Override
	public Map<String, Object> addComplaintDetails(ComplaintRequest complaintRequest, MultipartFile multipartFile) {
		 
		 Map<String, Object> response = new HashMap<>();
		 
		 Complaint complaint = this.compliantRequestToComplaint(complaintRequest);
		
         if(multipartFile != null) {
       	  String uploadImage= appUtils.uploadImage(multipartFile ,AppConstant.COMPLAINT_IMAGE_PATH, null);
       	complaint.setComplaintImage(uploadImage); 	
         }	 
          
         complaint.setUser(new User(appUtils.getUserId()));
         
         complaintRepo.save(complaint);
         
         response.put("response",AppConstant.COMPLAINT_ADD_SUCCES);
		
		return response ;
	}


	private Complaint compliantRequestToComplaint(ComplaintRequest complaintRequest) {
		
		return null;
	}

}
