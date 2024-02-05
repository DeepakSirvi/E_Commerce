package com.ecommerce.service;

import java.util.Map;

public interface WishListService {

	public Map<String, Object> addToWishList(String variantId);

	public Map<String, Object> removeFromWishList(String wishlistId);

	public Map<String, Object> isVarientExist(String varientId);

	public Map<String, Object> dislikeFromWishList(String varientId);

	public Map<String, Object> getWishlistByUserId();

}
