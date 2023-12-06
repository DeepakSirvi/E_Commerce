package com.ecommerce.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.BadRequestException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.model.Varient;
import com.ecommerce.payload.CartResponse;
import com.ecommerce.payload.ProductResponse;
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
				Set<Cart> cartList = cartRepo.findByUser(new User(appUtils.getUserId()));
				Set<CartResponse> cartResponses = cartList.stream().map(carts -> cartToCartResponse(carts)).collect(Collectors.toSet());		    
			    response.put("cart", cartResponses);
				response.put("message", AppConstant.PRODUCT_ADD_TO_CART +" " + cart2.getVarient().getStock());
			return response;
	}

	private CartResponse cartToCartResponse(Cart cart) {
		CartResponse response = new CartResponse();
		response.setId(cart.getId());
		response.setQuantity(cart.getQuantity());
		return response;
	}
}
