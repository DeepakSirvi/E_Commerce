package com.ecommerce.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Orders extends Audit{
	@Id
	private String id;

	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "order")
	private Set<OrderItem> orderItem;
	
	@ManyToOne
	private Address address;
	
}
