package com.ecommerce.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.ecommerce.payload.BrandRequest;
import com.ecommerce.payload.BrandResponse;
import com.ecommerce.service.BrandService;
import com.ecommerce.util.AppConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/ecommerce/brand")
@CrossOrigin("*")
public class BrandController {
	
	@Autowired
	private  BrandService   brandService;
	
   
	 @PostMapping("/addBrand")
	 public ResponseEntity<Map<String, Object>> addBrandDetails(@RequestPart  String brandRequest,@RequestPart(value = "brandImage", required = false) MultipartFile brandImage){
	        
		 ObjectMapper mapper = new ObjectMapper();
		 BrandRequest request = null;
		 
		 try {
			request = mapper.readValue(brandRequest, BrandRequest.class);
		} catch (JsonProcessingException e) {
			
			e.printStackTrace();
		}
		 Map<String, Object> response = brandService.addBrandDetails(request, brandImage);
		 
		 return ResponseEntity.ok(response);
	 
}
	 
	 @PutMapping("/updateStatus/{brandId}")
	    public ResponseEntity<Map<String, Object>> updateStatus(@PathVariable String brandId) {
	        Map<String, Object> response = brandService.updateStatusById(brandId);
	        return  ResponseEntity.ok(response);
	    }
	 
	
	 
	 @GetMapping("/brands/{brandId}")
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
	    public ResponseEntity<Map<String, List<BrandResponse>>> getAllBrand(
	    		@RequestParam(value="pageIndex"  , required= false , defaultValue= AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
	    		@RequestParam(value= "pagesize" , required= false , defaultValue= AppConstant.DEFAULT_PAGE_SIZE)   Integer size,
	    		@RequestParam(value= "sortDir" , required= false , defaultValue= AppConstant.DEFAULT_SORT_DIR) String SortDir ){
	        return  new  ResponseEntity<Map <String, List<BrandResponse>>>(brandService.getAllBrand(page, size, SortDir),HttpStatus.OK);
	    }
	
	 @GetMapping("/Verified/{brandId}")
	 public ResponseEntity<Map<String, Object >> getVerfiedBrandById(@PathVariable String brandId ) {
		 Map<String, Object> response = brandService.getVerfiedBrandById(brandId);
	        return ResponseEntity.ok(response);
	 }
	 
	 @GetMapping("/AllVerifiedBrand")
	 public ResponseEntity<Map<String, Object >>getAllVerfiedBrand ( 
			 @RequestParam(value="pageIndex"  , required= false , defaultValue= AppConstant.DEFAULT_PAGE_NUMBER) Integer page,
	    		@RequestParam(value= "pagesize" , required= false , defaultValue= AppConstant.DEFAULT_PAGE_SIZE)   Integer size,
	    		@RequestParam(value= "sortDir" , required= false , defaultValue= AppConstant.DEFAULT_SORT_DIR) String SortDir ){
			 
		  Map<String, Object> response = brandService.getAllVerfiedBrand(page , size, SortDir  );
	        return ResponseEntity.ok(response);
	 }
	   
	 @DeleteMapping("/delete/{id}")
	 public ResponseEntity<Boolean> deleteById(@PathVariable String id) {
			return ResponseEntity.ok(this.brandService.deleteAdress(id));
	 
	 }
	   
} 
	 
	 
	 
		 
		 
		 
	 
	 
	
	 
	 
