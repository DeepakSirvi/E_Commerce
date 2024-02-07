package com.ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.ecommerce.payload.ComplaintRequest;
import com.ecommerce.service.ComplaintService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/ecommerce/complaint")
@CrossOrigin
public class ComplaintController {
	 
	 @Autowired
	 private ComplaintService   complaintService ;
	 
	 
	 @PostMapping("/addComplaint")
	 public ResponseEntity<Map<String, Object>> addComplaintDetails(@RequestPart String brandRequest,@RequestPart(value = "file", required = false) MultipartFile multipartFiles){
	       
		 ObjectMapper mapper = new ObjectMapper();
		 
		 ComplaintRequest request = null;
		 
		/* try {
			request = mapper.readValue(complaintRequest,ComplaintRequest .class);
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}*/
		 Map<String, Object> response = complaintService .addComplaintDetails(request, multipartFiles);
		 
		 return ResponseEntity.ok(response);
}

}
