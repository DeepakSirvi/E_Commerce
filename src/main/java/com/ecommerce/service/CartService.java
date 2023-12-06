package com.ecommerce.service;

import java.util.Map;

public interface CartService {

	public Map<String, Object> addProductToCart(Long id, short quantity);

}
