package com.ecommerce.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
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
public class SubCategory extends Audit {

	public SubCategory(String id2) {
		this.id=id2;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	private String subCategory;
	
	@ManyToOne
	private Category category;

	
	@OneToMany(mappedBy = "subCategory")
	private List<Product> product;
	
}
