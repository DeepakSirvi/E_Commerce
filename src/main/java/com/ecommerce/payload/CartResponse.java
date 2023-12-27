package com.ecommerce.payload;

import com.ecommerce.model.Cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
	
	private Long id;
	
	private ProductResponse product;
	
	private Integer quantity;
	
	private UserResponse user;
	
	public CartResponse cartToCartResponse(Cart cart) {
		this.setId(cart.getId());
		this.setQuantity(cart.getQuantity());
		return this;
	}

}
