package com.ecommerce.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;



public interface ProductSaveForLaterService {
	
	    public Map<String , Object> addProductSaveforLater(String vid);
		public Map<String, Object>  getAllSaveForLaterByUserId(String uid);
		public ResponseEntity<?>  deleteSaveForLater(String id);
	
}
