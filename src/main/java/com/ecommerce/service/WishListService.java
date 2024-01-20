package com.ecommerce.service;


import java.util.List;
import java.util.Map;

import com.ecommerce.model.WishListProduct;

public interface WishListService {
	
	 public  Map<String, Object> addToWishList(String variantId); 
	
	 public Map<String, Object> removeFromWishList(String wishlistId);  
	 
	 public List<WishListProduct> getWishlistByUserId(String userId);

	public Map<String, Object> isVarientExist(String varientId);

	public Map<String, Object> dislikeFromWishList(String varientId); 
	 
	
	 
	
	
	
}


