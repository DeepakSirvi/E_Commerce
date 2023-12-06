package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.User;
import com.ecommerce.model.Varient;
import com.ecommerce.repository.CartRepo;
import com.ecommerce.service.CartService;
import com.ecommerce.util.AppConstant;
import com.ecommerce.util.AppUtils;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private AppUtils appUtils;
	
	@Override
	public Map<String, Object> addProductToCart(Long id, short quantity) {
		Optional<Cart> cart = cartRepo.findByUserAndVarient(new User(appUtils.getUserId()),new Varient(id));
		Cart cart2 = new Cart();
		if(cart.isPresent()) {
		    cart2 = cart.get();
			Integer q = cart2.getQuantity()+quantity;
			
			if(q>cart2.getVarient().getStock())
			throw new BadRequestException(AppConstant.AVAILABLE_STOCK +" "+ cart2.getVarient().getStock());
			
			cart2.setQuantity(q);
			 cart2 = cartRepo.save(cart2);
		}	
		else {
			cart2.setQuantity((int) quantity);
			cart2.setUser(new User(appUtils.getUserId()));
			cart2.setVarient(new Varient(id));			
			 cart2 = cartRepo.save(cart2);
		}
		Map<String, Object> response = new HashMap<>();
		response.put("message", AppConstant.PRODUCT_ADD_TO_CART +" " + cart2.getVarient().getStock());
		return response;
	}

}