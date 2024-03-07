package com.ecommerce.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	private List<OrderItem> orderItem;
	
	@Enumerated(EnumType.STRING)
	private Status status; 
	
	@ManyToOne
	private Address address;
	
}
