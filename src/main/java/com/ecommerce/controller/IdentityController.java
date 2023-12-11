package com.ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payload.IdentityRequest;
import com.ecommerce.service.IdentityService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("ecommerce/identity")
@CrossOrigin
public class IdentityController {

	@Autowired
	private IdentityService identityService;
	
	@PostMapping("/")
	public ResponseEntity<Map<String, Object>> addUserIdentity(@RequestBody IdentityRequest IdentityRequest){
		
		return new ResponseEntity<Map<String,Object>>(identityService.createIdentity(IdentityRequest),HttpStatus.CREATED);
	}
	
	@PutMapping("/")
	public ResponseEntity<Map<String, Object>> updateUserIdentity(@RequestBody IdentityRequest IdentityRequest){
		
		return new ResponseEntity<Map<String,Object>>(identityService.updateIdentity(IdentityRequest),HttpStatus.CREATED);
	}
	
	@GetMapping("/{identityId}")
	public ResponseEntity<Map<String, Object>> getUserIdentity(@PathParam(value = "identityId") Long id){
		
		return new ResponseEntity<Map<String,Object>>(identityService.getIdentity(id),HttpStatus.CREATED);
	}
	
	@GetMapping("/allIdentity/{userId}")
	public ResponseEntity<Map<String, Object>> getAllUserIdentity(@PathParam(value = "userId") Long id){
		
		return new ResponseEntity<Map<String,Object>>(identityService.getAllIdentityByUserId(id),HttpStatus.CREATED);
	}
	
	@PatchMapping("/{identityId}")
	public ResponseEntity<Map<String, Object>> updateIdentityStatus(@PathParam(value = "identityId") Long id){
		
		return new ResponseEntity<Map<String,Object>>(identityService.updateIdentityStatus(id),HttpStatus.CREATED);
	}

}
