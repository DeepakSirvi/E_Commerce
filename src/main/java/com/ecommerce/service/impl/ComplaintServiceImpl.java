package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Complaint;
import com.ecommerce.model.User;
import com.ecommerce.payload.ComplaintRequest;
import com.ecommerce.payload.ComplaintResponse;
import com.ecommerce.repository.ComplaintRepo;
import com.ecommerce.repository.ProductRepo;
import com.ecommerce.service.ComplaintService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@Service
public class ComplaintServiceImpl implements ComplaintService {
	
	
	private static final String COMPLAINT = null;

	private static final String ID = null;

	@Autowired
	private  ComplaintRepo   complaintRepo;
	
	@Autowired
    public ModelMapper modelMapper;
    
    @Autowired
    private AppUtils appUtils;
    
    @Autowired
    private ProductRepo productRepo;
    
	@Override
	public Map<String, Object> addComplaintDetails(ComplaintRequest complaintRequest, MultipartFile multipartFile) {
		
		 Map<String, Object> response = new HashMap<>();
		 
		 Complaint complaint = this.compliantRequestToComplaint(complaintRequest);
		
         if(multipartFile != null) {
        	 
       	  String uploadImage= appUtils.uploadImage(multipartFile ,AppConstant.COMPLAINT_IMAGE_PATH, null);
       	  

       	// complaint.getImage().add(uploadImage); 	
         }
//       	complaint.setComplaintImage(uploadImage); 	
       
          
         complaint.setUser(new User(appUtils.getUserId()));
         
         complaintRepo.save(complaint);
         
         response.put("response",AppConstant.COMPLAINT_ADD_SUCCES);
		
		return response ;
	}


	private Complaint compliantRequestToComplaint(ComplaintRequest complaintRequest) {
		 
		
		Complaint complaint = new Complaint();
		
		complaint.setDescription(complaintRequest.getDescription());
		
		complaint.setTitle(complaintRequest.getTitle());
		
		complaint.setProduct(productRepo.findById(complaintRequest.getProductId()).get());

		return complaint;
		
	}


	@Override
	
	public Map<String, Object> updateComplaintById(ComplaintRequest complaintRequest) {
		
		 Map<String, Object> response = new HashMap<>();
		 
		 Optional<Complaint> OptionalComplaint= complaintRepo.findById(complaintRequest.getId());
		 
		
			Complaint complaint = checkComplaint(complaintRequest.getId());;
			
			if (complaintRequest.getTitle()!= null)
				
			complaint.setTitle(complaintRequest.getTitle());
			
			if(complaintRequest.getDescription()!=null)
				
			complaint.setDescription(complaintRequest.getDescription());
			
			 complaintRepo.save(complaint);
			 
			 response.put("response", AppConstant.UPDATE_COMPLAINT);
		
		
		return response;
	}


	@Override
	public Map<String, Object> getComplaintById(String complaintId) {
		
		 Optional<Complaint> optionalComplaint = complaintRepo.findById(complaintId); 
		 
		 if (optionalComplaint.isPresent()) {
			 
      	   Map<String,Object> response= new HashMap<>();
      	   
      	 Complaint complaint = optionalComplaint.get();
      	 
      	ComplaintResponse complaintResponse = complaintToComplaintResponse(complaint);
      	
      	response.put("ComplaintDetails", complaintResponse);
      	
      	return response ;
		 }
		 
		 else {
			 
			 throw new BadRequestException(AppConstant.COMPLAINT_NOT_FOUND);
		 }
	}
	
	private ComplaintResponse complaintToComplaintResponse(Complaint complaint2) {
		
		ComplaintResponse complaint = new ComplaintResponse();
		
		complaint.setDescription(complaint2.getDescription());
		
		complaint.setTitle(complaint2.getTitle());
		
		complaint.setProductId(complaint2.getProduct().getId());
		
		complaint.setImage(complaint2.getImage());	
		
		return complaint;
	}

	public Complaint checkComplaint (String id ) { 
		
		 return  complaintRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("COMPLAINT_NOT_FOUND"));
		 
		 
		 }
	
}
