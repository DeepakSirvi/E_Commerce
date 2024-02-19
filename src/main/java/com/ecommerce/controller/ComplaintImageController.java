package com.ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.payload.ComplaintRequest;
import com.ecommerce.service.ComplaintImageService;


@RestController
@RequestMapping("/ecommerce/complaintImage")
@CrossOrigin
public class ComplaintImageController {

	
	 @Autowired
	 
	 private ComplaintImageService   complaintImageService ;
	 
	 @PostMapping("/addComplaintImage")
	 public ResponseEntity<Map<String, Object>> createComplaintImage(@RequestParam  ("complaintId" )String complaintId,@RequestParam(value = "multipartFiles", required = false) MultipartFile multipartFiles){
	      
		 Map<String, Object> response = complaintImageService.createComplaintImage( complaintId  , multipartFiles);
		 
		 return ResponseEntity.ok(response);
   }
	 
	 @PutMapping("/UpdateComplaintImage")
	    public ResponseEntity<Map<String, Object>> updateComplaintImage(@RequestParam ("complaintImageId") String  complaintImageId, @RequestParam(value = "multipartFiles", required = false) MultipartFile multipartFiles){ 
		 
	        Map<String, Object> response = complaintImageService.updateComplaintImage(complaintImageId, multipartFiles);
	        
	        return  ResponseEntity.ok(response);
	    }
	   
	 @DeleteMapping("/remove")
		public ResponseEntity<Map<String, Object>> removeComplaintImageById(@RequestParam String complaintImageId) {
			Map<String, Object> response = complaintImageService.removeComplaintImageById(complaintImageId);
			return ResponseEntity.ok(response);

		}
}
