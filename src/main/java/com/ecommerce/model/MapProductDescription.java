package com.ecommerce.model;

import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MapProductDescription {
	
	@Id
	private Integer id;
	
	private String title;
	
	@ElementCollection
	@CollectionTable
    private Map<String, String> details;
	
	
	@ManyToOne
	private ProductDescription product;
}
