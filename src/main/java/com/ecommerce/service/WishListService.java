package com.ecommerce.service;


import java.util.List;
import java.util.Map;

import com.ecommerce.model.WishListProduct;

public interface WishListService {
	
	 public  Map<String, Object> addToWishList(String variantId, String userId); 
	
	 public Map<String, Object> removeFromWishList(String wishlistId);  
	 
	 public List<WishListProduct> getWishlistByUserId(String userId); 
	 
	
	 
	
	
	
}


