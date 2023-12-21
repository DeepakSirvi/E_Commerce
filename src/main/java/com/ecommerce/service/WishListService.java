package com.ecommerce.service;


import java.util.Map;

import com.ecommerce.model.WishListProduct;

public interface WishListService {
	
	 public  Map<String, Object> addToWishList(Long variantId, Long userId); 
	
	
	 public Map<String, Object> removeFromWishList(Long varientId, Long userId);  
	 
	 public Map<String, Object> getActiveVarientInWishlistByUserId(Long userId);
	 
	
	 
	
	
	
}


