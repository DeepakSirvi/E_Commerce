package com.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class OrderItem {
	
	@Id
	private String id;
	
	@ManyToOne
	private Orders order;
	
	@ManyToOne
	private Varient product;
	private Float price;
	private Integer quantity;
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ManyToOne
	private TaxCode taxCode;
	
	@ManyToOne
	private Shipping shipping;
		
}
