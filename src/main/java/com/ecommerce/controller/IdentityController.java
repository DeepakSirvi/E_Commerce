package com.ecommerce.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.payload.IdentityRequest;
import com.ecommerce.service.IdentityService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/ecommerce/identity")
@CrossOrigin
public class IdentityController {

	@Autowired
	private IdentityService identityService;
	
	 
	 @PostMapping("/addIdentity")
	 public ResponseEntity<Map<String, Object>> addIdentityDetails( @RequestPart String  identityRequest, @RequestPart(value="file",required= false) MultipartFile multipartFile) {
		
		 ObjectMapper mapper = new ObjectMapper();
		 IdentityRequest request=null;
		 try {
		 request=mapper.readValue(identityRequest, IdentityRequest.class);
		 }catch(Exception e) 
		 {
			 e.printStackTrace();
		 }
		 Map<String, Object> response = identityService.addIdentityDetails(request, multipartFile);
	       
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	 
	 @PostMapping("/updateStatus/{identityId}")
	    public ResponseEntity<Map<String, Object>> updateStatusById(@PathVariable String identityId) {
	        Map<String, Object> response =identityService .updateStatusById(identityId);
	        return  ResponseEntity.ok(response);
	    }
	 
	
	  
	
   }
