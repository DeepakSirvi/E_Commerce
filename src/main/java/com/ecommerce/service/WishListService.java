package com.ecommerce.service;


import java.util.List;
import java.util.Map;

import com.ecommerce.model.WishListProduct;

public interface WishListService {
	
	 public  Map<String, Object> addToWishList(Long variantId, Long userId); 
	
	
	 public Map<String, Object> removeFromWishList(Long wishlistId);  
	 
	 public List<WishListProduct> getWishlistByUserId(Long userId);
	 
	 
	 
	
	 
	
	
	
}


