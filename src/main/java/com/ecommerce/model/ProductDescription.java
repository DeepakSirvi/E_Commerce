package com.ecommerce.model;

import java.util.List;

import jakarta.persistence.Column;
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
public class ProductDescription {
	
	@Id
	private Long id;

	@Column(length = 10000)
	private String description;
	
	@OneToMany(mappedBy = "product")
    private List<MapProductDescription> desc;
	
	@ManyToOne
	private Product product;
	
	

}
