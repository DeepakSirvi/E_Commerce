package com.ecommerce.model;
import java.util.HashSet;
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
public class VarientCategoryAttribute {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id;

	private String attributeName;

	@ManyToOne
	private VarientCategory varientCategory;

	@OneToMany(mappedBy = "varAttribute")
	private Set<VarientCategoryJoin> categoryJoins = new HashSet<>();


}
