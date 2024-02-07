package com.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
	private String id;

	@Column(length = 10000)
	private String description;

//	@OneToMany(mappedBy = "productDescription",cascade = CascadeType.ALL)
//    private Set<MapProductDescription> mapProductDescriptions=new HashSet<>();

}
