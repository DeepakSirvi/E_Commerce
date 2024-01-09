package com.ecommerce.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.ecommerce.payload.BrandRequest;
import com.ecommerce.service.BrandService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/ecommerce/brand")
@CrossOrigin
public class BrandController {
	
	@Autowired
	private  BrandService   brandService;
	
   
	 @PostMapping("/addBrand")
	 public ResponseEntity<Map<String, Object>> addBrandDetails(@RequestPart String brandRequest,@RequestPart(value = "file", required = false) MultipartFile multipartFiles){
	       
		 ObjectMapper mapper = new ObjectMapper();
		 BrandRequest request = null;
		 
		 try {
			request = mapper.readValue(brandRequest, BrandRequest.class);
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		 Map<String, Object> response = brandService.addBrandDetails(request, multipartFiles);
		 
		 return ResponseEntity.ok(response);
}
	 
	 @PostMapping("/updateStatus/{brandId}")
	    public ResponseEntity<Map<String, Object>> updateStatus(@PathVariable String brandId) {
	        Map<String, Object> response = brandService.updateStatusById(brandId);
	        return  ResponseEntity.ok(response);
	    }
	 
	
	 
	 @GetMapping("/brands/{userId}")
	 public ResponseEntity<Map<String, Object>> getBrandById(@PathVariable String userId) {
		 Map<String, Object> response = brandService.getBrandById(userId);
	        return ResponseEntity.ok(response);
	    }
		 
	
	 
	 }
	   
	 
	 
	 
	 
		 
		 
		 
	 
	 
	
	 
	 
