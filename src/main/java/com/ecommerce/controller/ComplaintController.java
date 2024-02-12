package com.ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	 public ResponseEntity<Map<String, Object>> addComplaintDetails(@RequestBody ComplaintRequest  complaintRequest,@RequestPart(value = "file", required = false) MultipartFile multipartFiles){
	       
		 ObjectMapper mapper = new ObjectMapper();
		 
		 Map<String, Object> response = complaintService .addComplaintDetails(complaintRequest, multipartFiles);
		 
		 return ResponseEntity.ok(response);
   }
	 
	 @PutMapping("/Complaints")
	    public ResponseEntity<Map<String, Object>> updateComplaintById(@RequestBody  ComplaintRequest  complaintRequest ) {
	        Map<String, Object> response = complaintService.updateComplaintById(complaintRequest);
	        return  ResponseEntity.ok(response);
	    }
	 
	 @GetMapping("/{complaintById}")
		public ResponseEntity<Map<String, Object>> getComplaintById(@PathVariable(value = "complaintById") String complaintId) {
			return new ResponseEntity<Map<String, Object>>(complaintService. getComplaintById(complaintId), HttpStatus.OK);
		}
	 

}
