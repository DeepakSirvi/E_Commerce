package com.ecommerce.payload;

import com.ecommerce.model.Cart;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CartResponse {
	
	private String id;
	
	private VarientResponse product;
	
	private Integer quantity;
	
	private UserResponse user;
	
	public CartResponse cartToCartResponse(Cart cart) {
		this.setId(cart.getId());
		this.setQuantity(cart.getQuantity());
		this.setProduct(new VarientResponse().varientToVarientResponseForCard(cart.getVarient()));
		return this;
	}
}
