package com.ecommerce.model;

import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
//@Entity

public class MapProductDescription {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long id;
//	
//	private String title;
//	
//	@ElementCollection
//	@CollectionTable(name = "Product_Feature")
//    private Map<String, String> details;
//	
//	
//	@ManyToOne(fetch = FetchType.EAGER)
//	private ProductDescription productDescription;
}
