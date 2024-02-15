package com.ecommerce.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;



public interface ProductSaveForLaterService {
	
	    public Map<String, String> addProductSaveforLater(String vid);
	    
		public Map<String, Object>  getAllSaveForLaterByUserId();
		
		public ResponseEntity<?>  deleteSaveForLater(String id);
	
}
