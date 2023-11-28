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
public class VarientCategoryAttribute {
	
	@Id
	private Long id;

	@ManyToOne
	private VarientCategory varientCategory;
	
	private String attributeName;
	
	@OneToMany(mappedBy = "varAttribute")
	private Set<VarientCategoryJoin> categoryJoins;
}
