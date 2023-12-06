package com.ecommerce.payload;

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

}
