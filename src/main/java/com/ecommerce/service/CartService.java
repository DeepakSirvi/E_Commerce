package com.ecommerce.service;

import java.util.Map;

public interface CartService {

	public Map<String, Object> addProductToCart(String id, short quantity);

	public Map<String, Object> getCartByUserId(String userId);

	public Map<String, Object> deleteCartById(String cartId);

	public Map<String, Object> getCount();


}
