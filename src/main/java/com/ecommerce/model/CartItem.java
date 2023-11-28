package com.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem extends Audit {
	
	@Id
	private  Long id;
	
	@ManyToOne
	private Product product;
	
	private Integer quantity;

	@ManyToOne
	private Cart cart;
}
