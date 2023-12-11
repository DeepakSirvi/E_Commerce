package com.ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.payload.VarientRequest;
import com.ecommerce.service.VarientService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("ecommerce/productVarient")
@CrossOrigin
public class VarientPController {
	
	@Autowired
	private VarientService varientService;
	
	@PostMapping("/")
	public ResponseEntity<Map<String, Object>> addProductVarient(@Param(value = "varientRequest") VarientRequest varientRequest,MultipartFile[] image){
		
		return new ResponseEntity<Map<String,Object>>(varientService.createVarient(varientRequest,image),HttpStatus.CREATED);
	}
	
	@PutMapping("/")
	public ResponseEntity<Map<String, Object>> updateProductVarient(@RequestBody VarientRequest varientRequest){
		
		return new ResponseEntity<Map<String,Object>>(varientService.updateVarient(varientRequest),HttpStatus.CREATED);
	}
	
	@GetMapping("/{varientId}")
	public ResponseEntity<Map<String, Object>> getProductVarient(@PathParam(value = "varientId") Long id){
		
		return new ResponseEntity<Map<String,Object>>(varientService.getVarient(id),HttpStatus.CREATED);
	}
	
	@GetMapping("/AllVarient/{productId}")
	public ResponseEntity<Map<String, Object>> getAllProductVarient(@PathParam(value = "productId") Long id){
		
		return new ResponseEntity<Map<String,Object>>(varientService.getAllVarientByProductId(id),HttpStatus.CREATED);
	}
	
	@PatchMapping("/{varientId}")
	public ResponseEntity<Map<String, Object>> updateVarientStatus(@PathParam(value = "varientId") Long id){
		
		return new ResponseEntity<Map<String,Object>>(varientService.updateVarientStatus(id),HttpStatus.CREATED);
	}

}
