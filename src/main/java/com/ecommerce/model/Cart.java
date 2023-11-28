package com.ecommerce.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart extends Audit{

	@Id
	private Long id;
	@OneToOne
	private User user;
	
	@OneToMany(mappedBy = "cart")
	private Set<CartItem> cartItem;
	
	
	
}
