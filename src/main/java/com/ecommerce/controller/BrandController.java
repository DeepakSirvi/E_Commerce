package com.ecommerce.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.ecommerce.payload.BrandRequest;
import com.ecommerce.service.BrandService;
import com.ecommerce.util.AppConstant;
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
	 public ResponseEntity<Map<String, Object>> getBrandById(@PathVariable String brandId) {
		 Map<String, Object> response = brandService.getBrandById(brandId);
	        return ResponseEntity.ok(response);
	    }
	 
	 
	 @GetMapping("/AllBrands/{userId}")
	    public ResponseEntity<Map<String, Object>> getAllBrandById(@PathVariable String userId) {
	        Map<String, Object> response = brandService.getAllBrandById(userId);
	        return ResponseEntity.ok(response);
	    }
		 
	 
	 @GetMapping("/AllBrand")
	    public ResponseEntity<Map<String, Object>> getAllBrand(
	    		@RequestParam(value="pageIndex"  , required= false , defaultValue= AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
	    		@RequestParam(value= "pagesize" , required= false , defaultValue= AppConstant.DEFAULT_PAGE_SIZE)   Integer size,
	    		@RequestParam(value= "sortDir" , required= false , defaultValue= AppConstant.DEFAULT_SORT_DIR) String SortDir ){
	        return  new  ResponseEntity<Map <String, Object>>(brandService.getAllBrand(page, size, SortDir),HttpStatus.OK);
	    }
	/* @GetMapping("/verified/{brandId}")
	 public ResponseEntity<Map<String, Object>> getAllVerifiedBrandsById(
			 @PathVariable(value = "brandId")String brandId ,
			 @RequestParam(value= "pageIndex" , required=false , defaultValue= AppConstant.DEFAULT_PAGE_NUMBER) Integer pageIndex,
			 @RequestParam(value= "pagesize" , required= false ,defaultValue= AppConstant.DEFAULT_PAGE_SIZE)  Integer pageSize ,
			 
			 @RequestParam(value= "sortDir" , required = false , defaultValue= AppConstant.DEFAULT_SORT_DIR)  String SortDir) {
		 
		  return  new  ResponseEntity<Map <String, Object>>(brandService.getAllVerfiedBrandById(brandId, pageIndex, pageSize, SortDir),HttpStatus.OK);
	 }*/
	 
	  
	 @GetMapping("/Verified/{brandId}")
	 public ResponseEntity<Map<String, Object >> getVerfiedBrandById(@PathVariable String brandId ) {
		 Map<String, Object> response = brandService.getVerfiedBrandById(brandId);
	        return ResponseEntity.ok(response);
	 }
	 
	 
	 }
	   
	 
	 
	 
	 
		 
		 
		 
	 
	 
	
	 
	 
