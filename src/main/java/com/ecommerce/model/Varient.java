package com.ecommerce.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public class Varient extends Audit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;
	private String varientName;
	private Float price;
	private Integer stock;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ManyToOne
	private Product product;
	
	@OneToMany(mappedBy = "varient")
	private Set<VarientCategoryJoin> categoryJoins;
	
	@OneToMany(mappedBy = "varientImage")
	private Set<ProductImage> productImage;
	

}
